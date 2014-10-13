package com.softserveinc.Component;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.softserveinc.DAO.JobolizerDAO;
import com.softserveinc.DTO.JobolizerResultDTO;
import com.softserveinc.Entity.JobolizerEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by petroborovets on 9/10/14.
 */
@Component
public class JobolizerComponent {

    private UrlEncodedFormEntity entity;

    public JobolizerComponent() {
        createDBAndTable();
    }

    public String getRealVacancyURL(String vacancyURL) throws IOException {
        String realVacancyUrl;
        boolean notFinished = true;

        final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

        realVacancyUrl = vacancyURL;

        while (notFinished) {
            HtmlPage page = webClient.getPage(realVacancyUrl);
            DomNodeList iFrames = page.getElementsByTagName("iframe");

            if (iFrames.size() == 0) {
                notFinished = false;
            } else {
                DomElement iFrame = null;
                for (Object frameElement : iFrames) {
                    DomElement frame = (DomElement) frameElement;
                    if (frame.getAttribute("class").equals("detail_preview") || frame.getAttribute("class").startsWith("joblisting"))
                        iFrame = frame;
                }
                if (iFrame == null) {
                    notFinished = false;
                    continue;
                }
                realVacancyUrl = iFrame.getAttribute("src");
            }
        }

        return realVacancyUrl;
    }

    public void collectURLData(String vacancyURL, int num, JobolizerResultDTO jobolizerResultDTO) throws IOException, InterruptedException, SQLException {

        if (vacancyURL.startsWith("http://www.jobbörse.com") || vacancyURL.contains("xn--jobbrse-d1a")) {

            vacancyURL = getRealVacancyURL(vacancyURL);
        }
        System.out.println("Checking vacancy " + num + ":" + vacancyURL);
        String htmlPage = getSite(vacancyURL);
        String newLocation = checkRedirect(htmlPage);
        if (newLocation != null) {
            vacancyURL = newLocation;
            htmlPage = getSite(newLocation);

            //another redirect-sometimes there are two
            newLocation = checkRedirect(htmlPage);
            if (newLocation != null) {
                vacancyURL = newLocation;
            }
        }

        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("http://jobolizer.com/phpProxy/getJOBolizerResponse.php");

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("url", vacancyURL));
        httppost.setEntity(entity);

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder builder = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            builder.append(line).append("\n");
        }

        String responsePage = builder.toString();
        extractDataFromPage(responsePage, vacancyURL, jobolizerResultDTO);
    }

    public void extractDataFromPage(String htmlPage, String vacancyURL, JobolizerResultDTO jobolizerResultDTO) throws SQLException, IOException, InterruptedException {
        //WebClient webClient = new WebClient(BrowserVersion.CHROME);
        Document doc = Jsoup.parse(htmlPage);
        Elements leftDivElements = doc.getElementsByClass("left");
        Elements rightDivElements = doc.getElementsByClass("right");

        JobolizerEntity jobolizerEntity = new JobolizerEntity();
        jobolizerEntity.setVacancyURL(vacancyURL);

        for (int i = 0; i < leftDivElements.size(); i++) {

            Element leftDiv = leftDivElements.get(i);
            Element rightDiv = rightDivElements.get(i);

            fillJobolizerBundleWithDiv(jobolizerEntity, leftDiv, rightDiv);
        }
        //Oleg change
        //just search for emails in the vacancy site
        String em = "";//string with all emails concatenated with space
        if (jobolizerEntity.getEmails() == null) {
            //look for emails on the site
            String EmailsAsString = extractEmails(htmlPage);
            jobolizerEntity.setEmails(EmailsAsString);
        }
        System.out.println("Saving jobolizer(" + jobolizerEntity.getId() + ") to DB");
        JobolizerDAO jobolizerDAO = new JobolizerDAO();
        jobolizerDAO.addElement(jobolizerEntity, jobolizerResultDTO);

        if (jobolizerEntity.getId() == 0) {
            //jobolizerResultDTO.setError(true);
            if (jobolizerResultDTO.getErrorDescription()!=null) {
                String errorDescription = jobolizerResultDTO.getErrorDescription();
                jobolizerResultDTO.setErrorDescription(errorDescription+"Jobolizer vacancy was not saved in DB. ");
            } else jobolizerResultDTO.setErrorDescription("Jobolizer vacancy was not saved in DB. ");
        }
    }

    private String extractEmails(String html) {
        String em = "";//all emails together
        HashSet<String> hsEmails = new HashSet<String>();
        final String RE_MAIL = "\\\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,4}\\\\b)";
        String RE_Mail_alter;
        RE_Mail_alter = "\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b";

        Pattern p = Pattern.compile(RE_Mail_alter, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(html);

        while (m.find()) {
            hsEmails.add(m.group());
            //em=em.concat(m.group()) + " ";
        }
        for (String hsEmail : hsEmails) {
            em = em.concat(hsEmail + " ");
        }
        return em;

    }

    //checks with regex a redirect pattern or refresh and returns the new url. otherwise null
    private String checkRedirect(String htmlPage) {
        String newLocation = null;
        //look for the new location with regex
        final String RE_redirect_location = "redirect.+document.location.href=\"(.+?)\"";
        final String RE_refresh = "http\\-equiv=\"REFRESH\" content=\"1;url=(.+?)\"";
        Pattern p = Pattern.compile(RE_redirect_location, Pattern.DOTALL);
        Matcher m = p.matcher(htmlPage);
        if (m.find()) {
            newLocation = m.group(1);
            return newLocation;
        } else {
            //meta http-equiv="REFRESH" content="1;url=http://www.stepstone.de/5/index.cfm?event=off
            p = Pattern.compile(RE_refresh, Pattern.DOTALL);
            m = p.matcher(htmlPage);
            if (m.find()) {
                newLocation = m.group(1);
                return newLocation;
            } else return null;
        }

    }

    private String getSite(String url) throws IOException, InterruptedException {
        HttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpget);


        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder builder = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            builder.append(line).append("\n");
        }
        return builder.toString();
    }

    private void fillJobolizerBundleWithDiv(JobolizerEntity jobolizerEntity, Element leftDiv, Element rightDiv) {
        TextNode textNodeLeft = (TextNode) leftDiv.childNode(0);
        String leftKeyWord = textNodeLeft.text();

        TextNode textNodeRight = (TextNode) rightDiv.childNode(0);
        String rightKeyWord = textNodeRight.text();

        if (leftKeyWord.equals("Jobtitel")) {
            jobolizerEntity.setJobTitle1(rightKeyWord);
        } else if (leftKeyWord.equals("Untertitel")) {
            //No impl
        } else if (leftKeyWord.equals("Berufsbezeichnung")) {
            jobolizerEntity.setJobTitle2(rightKeyWord);
        } else if (leftKeyWord.equals("Arbeitsverh&auml;ltnis")) {
            jobolizerEntity.setEmploymentType(rightKeyWord);
        } else if (leftKeyWord.equals("Position")) {
            jobolizerEntity.setPosition(rightKeyWord);
        } else if (leftKeyWord.equals("Zeitlich befristet")) {
            if (rightKeyWord.equals("Nein"))
                jobolizerEntity.setLimitedInTime(false);
            else jobolizerEntity.setLimitedInTime(true);
        } else if (leftKeyWord.equals("Name d. Arbeitgebers")) {
            jobolizerEntity.setCompanyName(rightKeyWord);
        } else if (leftKeyWord.equals("Industriezweige/Branchen")) {
            jobolizerEntity.setIndustriesAndBrunches(rightKeyWord);
        } else if (leftKeyWord.equals("Reisebereitschaft")) {
            if (rightKeyWord.equals("Nein"))
                jobolizerEntity.setTravelingRequired(false);
            else jobolizerEntity.setTravelingRequired(true);
        } else if (leftKeyWord.equals("Pr&auml;senz-/Zivildienst relevant")) {
            if (rightKeyWord.equals("Nein"))
                jobolizerEntity.setMilitaryRequired(false);
            else jobolizerEntity.setMilitaryRequired(true);
        } else if (leftKeyWord.equals("Erforderlich Berufserfahrung")) {
            jobolizerEntity.setWorkExperience(rightKeyWord);
        } else if (leftKeyWord.equals("Bildungsniveau (ISCED)")) {
            jobolizerEntity.setEducationalLevel(rightKeyWord);
        } else if (leftKeyWord.equals("Ausbildungstyp")) {
            jobolizerEntity.setEducationalType(rightKeyWord);
        } else if (leftKeyWord.equals("Name d. Ausbildungsst&auml;tte")) {

        } else if (leftKeyWord.equals("Erworbene F&auml;higkeit(en)")) {
            jobolizerEntity.setSkillsNeeded(rightKeyWord);
        } else if (leftKeyWord.equals("Sprache")) {
            jobolizerEntity.setLanguage(rightKeyWord);
        } else if (leftKeyWord.equals("Sprachlevel (CERF)")) {
            jobolizerEntity.setLanguageLevel(rightKeyWord);
        } else if (leftKeyWord.equals("T&auml;tigkeitsgebiet")) {//2
            if (jobolizerEntity.getOperationalArea() == null)
                jobolizerEntity.setOperationalArea(rightKeyWord);
            else jobolizerEntity.setOperationalArea2(rightKeyWord);
        } else if (leftKeyWord.equals("Relevanz (0-1)")) {//2
            if (rightKeyWord.equals("1.0"))
                jobolizerEntity.setOperationalAreaRelevance(true);
            else jobolizerEntity.setOperationalAreaRelevance(false);
        } else if (leftKeyWord.equals("L&auml;ngengrad")) {
            jobolizerEntity.setLongitude(rightKeyWord);
        } else if (leftKeyWord.equals("Breitengrad")) {
            jobolizerEntity.setLanguage(rightKeyWord);
        } else if (leftKeyWord.equals("Bundesland")) {
            jobolizerEntity.setCompanyState(rightKeyWord);
        } else if (leftKeyWord.equals("Ort")) {
            jobolizerEntity.setCity(rightKeyWord);
        } else if (leftKeyWord.equals("Land")) {//2
            if (jobolizerEntity.getWorkingCountry() == null)
                jobolizerEntity.setWorkingCountry(rightKeyWord);
            else {
                String country = jobolizerEntity.getWorkingCountry();
                country = country + rightKeyWord;
                jobolizerEntity.setWorkingCountry(country);
            }
        } else if (leftKeyWord.equals("Postleitzahl")) {
            jobolizerEntity.setPostCode(rightKeyWord);
        } else if (leftKeyWord.equals("Adresse")) {
            jobolizerEntity.setCompanyAddress(rightKeyWord);
        } else if (leftKeyWord.equals("Telefonnummer(n)")) {
            jobolizerEntity.setPhoneNumbers(rightKeyWord);
        } else if (leftKeyWord.equals("Anrede")) {
            jobolizerEntity.setSalutation(rightKeyWord);
        } else if (leftKeyWord.equals("E-Mail Adresse(n)")) {
            jobolizerEntity.setEmails(rightKeyWord);
        } else if (leftKeyWord.equals("Ansprechpartner")) {
            jobolizerEntity.setContactName(rightKeyWord);
        } else if (leftKeyWord.equals("URL des Arbeitgebers")) {
            jobolizerEntity.setCompanyUrl(rightKeyWord);
        }
    }

    private void createDBAndTable() {
        DBComponent dbComponent = new DBComponent();
        dbComponent.createDatabase();
        dbComponent.createJobolizerTables();
    }
}
