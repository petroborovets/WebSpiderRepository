package com.softserveinc.Controller;

import com.softserveinc.Component.Jobolizer.JobolizerUtilities;
import com.softserveinc.Component.JobolizerComponent;
import com.softserveinc.DAO.CommonUrlTableDAO;
import com.softserveinc.DAO.JobolizerDAO;
import org.apache.http.HttpEntity;
import com.softserveinc.DTO.JobolizerResourceDTO;
import com.softserveinc.DTO.JobolizerResultDTO;
import com.softserveinc.DTO.JobolizerResultsDTO;
import com.softserveinc.Entity.JobolizerEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
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

        // Getting analyzed data from DB
        JobolizerDAO jobolizerDAO = new JobolizerDAO();
        ArrayList<JobolizerEntity> jobolizerEntities = null;
        try {
            jobolizerEntities = jobolizerDAO.getAllElements();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (jobolizerEntities != null)
            model.addAttribute("jobolizerBundles", jobolizerEntities);

        // Jobolizer result
        JobolizerResultsDTO jobolizerResultsDTO = new JobolizerResultsDTO();
        jobolizerResultsDTO.setError(false);
        jobolizerResultsDTO.setNumberOfURLsSaved(5);
        jobolizerResultsDTO.setNumberOfURLsSendToJobolizer(5);

        JobolizerResultDTO jobolizerResultDTO = new JobolizerResultDTO();
        jobolizerResultDTO.setId(5);
        jobolizerResultDTO.setError(false);
        jobolizerResultDTO.setVacancyURL("apple.com");
        ArrayList<JobolizerResultDTO> jobolizerResultDTOs = new ArrayList<JobolizerResultDTO>();
        jobolizerResultDTOs.add(jobolizerResultDTO);

        jobolizerResultsDTO.setUrlResultList(jobolizerResultDTOs);

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
        JobolizerResultsDTO jobolizerResultsDTO = new JobolizerResultsDTO();
        ArrayList<JobolizerResultDTO> jobolizerResultDTOs = new ArrayList<JobolizerResultDTO>();

        // Getting Data to jobolize
        String tableName = jobolizerResourceDTO.getTableName();
        String urlFieldName = jobolizerResourceDTO.getUrlFieldName();
        String idFieldName = jobolizerResourceDTO.getIdFieldName();
        long idFrom = jobolizerResourceDTO.getIdFrom();
        long idTo = jobolizerResourceDTO.getIdTo();
        jobolizerResultsDTO.setNumberOfURLsSendToJobolizer(idTo - idFrom);

        JobolizerComponent jobolizerComponent = new JobolizerComponent();
        CommonUrlTableDAO commonUrlTableDAO = new CommonUrlTableDAO(tableName, idFieldName, urlFieldName);

        // Final urls for jobolizer to analyze
        ArrayList<String> urlsToJobolize = null;
        try {
            urlsToJobolize = commonUrlTableDAO.getCompanyURLs(idFrom, idTo);
            if (urlsToJobolize.size() == 0)
                throw new NullPointerException("Error: Jobolizer failed getting urls to analyze (urls.size = 0 || urls = null).");
        } catch (SQLException e) {
            jobolizerResultsDTO.setError(true);
            jobolizerResultsDTO.setErrorDescription(e.getMessage());
            System.out.println("Error: Jobolizer failed getting urls to analyze (SQLException).");
            e.printStackTrace();
        } catch (NullPointerException e) {
            jobolizerResultsDTO.setError(true);
            jobolizerResultsDTO.setErrorDescription(e.getMessage());
        }

        int num = 0; // Olegs change
        for (String url : urlsToJobolize) {
            JobolizerResultDTO jobolizerResultDTO = new JobolizerResultDTO();
            jobolizerResultDTO.setVacancyURL(url);
            jobolizerResultDTO.setError(false);

            url = url.replaceAll("jobbörse", "xn--jobbrse-d1a");
            try {
                jobolizerComponent.collectURLData(url, num, jobolizerResultDTO);
            } catch (Exception e) {
                jobolizerResultDTO.setError(true);
                jobolizerResultDTO.setErrorDescription(e.getMessage());
                System.out.println("Error: Jobolizer can't analyze url:" + url);
                e.printStackTrace();
            }
            //Adding to array
            jobolizerResultDTOs.add(jobolizerResultDTO);
        }
        // Count successful
        jobolizerResultsDTO.setNumberOfURLsSaved(new JobolizerUtilities().getNumberOfSuccessfulResults(jobolizerResultDTOs));

        // Add data of each resoult
        jobolizerResultsDTO.setUrlResultList(jobolizerResultDTOs);

        // Getting analyzed data from DB
        JobolizerDAO jobolizerDAO = new JobolizerDAO();
        ArrayList<JobolizerEntity> jobolizerEntities = null;
        try {
            jobolizerEntities = jobolizerDAO.getAllElements();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (jobolizerEntities != null)
            model.addAttribute("jobolizerBundles", jobolizerEntities);

        model.addAttribute("jobolizerResultsDTO", jobolizerResultsDTO);
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
        ArrayList<JobolizerEntity> jobolizerEntities = null;
        try {
            jobolizerEntities = jobolizerDAO.getAllElements();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        ArrayList<JobolizerEntity> jobolizerEntities = null;
        try {
            jobolizerEntities = jobolizerDAO.getAllElements();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (jobolizerEntities != null)
            model.addAttribute("jobolizerBundles", jobolizerEntities);

        model.addAttribute("message", "Vacancy(" + id + ") deleted successfully");
        model.addAttribute("jobolizerBundle", new JobolizerEntity());

        return "jobolizer";
    }
}
