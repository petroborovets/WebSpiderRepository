package com.softserveinc.Controller;

import com.softserveinc.Component.Jobolizer.JobolizerUtilities;
import com.softserveinc.Component.Jobolizer.JobolizerComponent;
import com.softserveinc.DAO.CommonUrlTableDAO;
import com.softserveinc.DAO.JobolizerDAO;
import com.softserveinc.DTO.JobolizerResourceDTO;
import com.softserveinc.DTO.SpiderResultDTO;
import com.softserveinc.DTO.SpiderResultsDTO;
import com.softserveinc.Entity.JobolizerEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by petroborovets on 10/10/14.
 */
@Controller
@RequestMapping("/jobolizer")
public class JobolizerController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getWelcomePage(ModelMap model) {

        ArrayList<JobolizerEntity> jobolizerEntities = new JobolizerUtilities().getAllJobolizerVacanciesFromDB();

        if (jobolizerEntities != null)
            model.addAttribute("jobolizerBundles", jobolizerEntities);

        // Jobolizer result
        SpiderResultsDTO spiderResultsDTO = new SpiderResultsDTO();
        spiderResultsDTO.setError(false);
        spiderResultsDTO.setNumberOfURLsSaved(5);
        spiderResultsDTO.setNumberOfURLsSendToJobolizer(5);

        SpiderResultDTO spiderResultDTO = new SpiderResultDTO();
        spiderResultDTO.setId(5);
        spiderResultDTO.setError(false);
        spiderResultDTO.setVacancyURL("apple.com");
        ArrayList<SpiderResultDTO> spiderResultDTOs = new ArrayList<SpiderResultDTO>();
        spiderResultDTOs.add(spiderResultDTO);

        spiderResultsDTO.setUrlResultList(spiderResultDTOs);

        //model.addAttribute("jobolizerResultsDTO", jobolizerResultsDTO);
        model.addAttribute("jobolizerBundle", new JobolizerEntity());
        model.addAttribute("jobolizerResourceDTO", new JobolizerResourceDTO());
        if (jobolizerEntities != null && jobolizerEntities.size() != 0)
            model.addAttribute("message", "Found " + jobolizerEntities.size() + " vacancies");
        else model.addAttribute("message", "No vacancies found...");

        return "jobolizer";
    }

    @RequestMapping(value = "/startJobolizer", method = RequestMethod.POST)
    public String startJobolizer(@ModelAttribute("jobolizerResourceDTO") JobolizerResourceDTO jobolizerResourceDTO
            , ModelMap model) {

        // Result dtos to display info on page
        SpiderResultsDTO spiderResultsDTO = new SpiderResultsDTO();
        ArrayList<SpiderResultDTO> spiderResultDTOs = new ArrayList<SpiderResultDTO>();

        // Getting Data to jobolize
        String tableName = jobolizerResourceDTO.getTableName();
        String urlFieldName = jobolizerResourceDTO.getUrlFieldName();
        String idFieldName = jobolizerResourceDTO.getIdFieldName();
        long idFrom = jobolizerResourceDTO.getIdFrom();
        long idTo = jobolizerResourceDTO.getIdTo();
        spiderResultsDTO.setNumberOfURLsSendToJobolizer(idTo - idFrom);

        JobolizerComponent jobolizerComponent = new JobolizerComponent();
        CommonUrlTableDAO commonUrlTableDAO = new CommonUrlTableDAO(tableName, idFieldName, urlFieldName);

        // Final urls for jobolizer to analyze
        ArrayList<String> urlsToJobolize = null;
        try {
            urlsToJobolize = commonUrlTableDAO.getCompanyURLs(idFrom, idTo);
            if (urlsToJobolize.size() == 0)
                throw new NullPointerException("Error: Jobolizer failed getting urls to analyze (urls.size = 0 || urls = null).");
        } catch (SQLException e) {
            spiderResultsDTO.setError(true);
            spiderResultsDTO.setErrorDescription("SQLException, cant get urls from table:" + tableName);
            System.out.println("Error: Jobolizer failed getting urls to analyze (SQLException).");
            e.printStackTrace();
        } catch (NullPointerException e) {
            spiderResultsDTO.setError(true);
            spiderResultsDTO.setErrorDescription("URLs in table "+tableName+" with that id does not exist!");
        }

        int num = 0; // Olegs change
        for (String url : urlsToJobolize) {
            SpiderResultDTO spiderResultDTO = new SpiderResultDTO();
            spiderResultDTO.setVacancyURL(url);
            spiderResultDTO.setError(false);

            url = url.replaceAll("jobbörse", "xn--jobbrse-d1a");
            try {
                jobolizerComponent.collectURLData(url, num, spiderResultDTO);
            } catch (Exception e) {
                spiderResultDTO.setError(true);
                spiderResultDTO.setErrorDescription(e.getMessage());
                System.out.println("Error: Jobolizer can't analyze url:" + url);
                e.printStackTrace();
            }
            //Adding to array
            spiderResultDTOs.add(spiderResultDTO);
        }
        // Count successful
        spiderResultsDTO.setNumberOfURLsSaved(new JobolizerUtilities().getNumberOfSuccessfulResults(spiderResultDTOs));

        // Add data of each resoult
        spiderResultsDTO.setUrlResultList(spiderResultDTOs);

        // Getting analyzed data from DB
        ArrayList<JobolizerEntity> jobolizerEntities = new JobolizerUtilities().getAllJobolizerVacanciesFromDB();

        if (jobolizerEntities != null)
            model.addAttribute("jobolizerBundles", jobolizerEntities);

        model.addAttribute("jobolizerResultsDTO", spiderResultsDTO);
        model.addAttribute("jobolizerBundle", new JobolizerEntity());
        model.addAttribute("jobolizerResourceDTO", new JobolizerResourceDTO());
        if (jobolizerEntities != null && jobolizerEntities.size() != 0)
            model.addAttribute("message", "Found " + jobolizerEntities.size() + " vacancies");
        else model.addAttribute("message", "No vacancies found...");

        return "jobolizer";
    }

    @RequestMapping(value = "/showBundle/{id}", method = RequestMethod.GET)
    public String getBundleDetails(@PathVariable(value = "id") Long id, ModelMap model) {

        JobolizerDAO jobolizerDAO = new JobolizerDAO();
        JobolizerEntity jobolizerEntity = null;
        try {
            jobolizerEntity = jobolizerDAO.getElementById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        model.addAttribute("jobolizerBundle", jobolizerEntity);

        return "jobolizerBundleDetails";
    }

    @RequestMapping(value = "/addBundle", method = RequestMethod.POST)
    public String addBundle(@ModelAttribute("jobolizerBundle") JobolizerEntity jobolizerEntity, ModelMap model) {

        JobolizerDAO jobolizerDAO = new JobolizerDAO();
        try {
            jobolizerDAO.addElement(jobolizerEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<JobolizerEntity> jobolizerEntities = new JobolizerUtilities().getAllJobolizerVacanciesFromDB();

        if (jobolizerEntities != null)
            model.addAttribute("jobolizerBundles", jobolizerEntities);

        model.addAttribute("message", "Vacancy(" + jobolizerEntity.getId() + ") added successfully");
        model.addAttribute("jobolizerBundle", new JobolizerEntity());

        return "jobolizer";
    }

    @RequestMapping(value = "/deleteBundle/{id}", method = RequestMethod.GET)
    public String deleteBundle(@PathVariable(value = "id") Long id, ModelMap model) {

        JobolizerDAO jobolizerDAO = new JobolizerDAO();
        try {
            jobolizerDAO.deleteElementById(id);
        } catch (SQLException e) {
            System.out.println("Can't delete this element!");
            e.printStackTrace();
        }

        ArrayList<JobolizerEntity> jobolizerEntities = new JobolizerUtilities().getAllJobolizerVacanciesFromDB();

        if (jobolizerEntities != null)
            model.addAttribute("jobolizerBundles", jobolizerEntities);

        model.addAttribute("jobolizerResourceDTO", new JobolizerResourceDTO());
        model.addAttribute("message", "Vacancy(" + id + ") deleted successfully");
        model.addAttribute("jobolizerBundle", new JobolizerEntity());

        return "jobolizer";
    }
}
