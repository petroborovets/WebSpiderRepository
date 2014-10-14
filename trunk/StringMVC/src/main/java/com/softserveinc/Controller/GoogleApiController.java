package com.softserveinc.Controller;

import com.softserveinc.Component.Google.GoogleComponent;
import com.softserveinc.Component.Google.GoogleUtils;
import com.softserveinc.DAO.GoogleDAO;
import com.softserveinc.DTO.*;
import com.softserveinc.Entity.GoogleEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by petroborovets on 10/13/14.
 */
@Controller
@RequestMapping("/googleApi")
public class GoogleApiController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getGoogleApiPage(ModelMap model) {


        model.addAttribute("googleOutputLocationDTO", new GoogleOutputLocationDTO());
        return "googleApi";
    }

    @RequestMapping(value = "/startGoogle", method = RequestMethod.POST)
    public String startGoogle(@ModelAttribute("googleOutputLocationDTO") GoogleOutputLocationDTO googleOutputLocationDTO,
                              ModelMap model) {

        SpiderResultsDTO spiderResultsDTO = new SpiderResultsDTO();

        String searchString = googleOutputLocationDTO.getSearchString();
        // Setting up googleComponent
        GoogleComponent googleComponent = new GoogleComponent();
        googleComponent.setSearchString(searchString);
        googleComponent.setNumberOfResults(10);

        ArrayList<GoogleEntity> googleEntities = null;
        // Starting google component
        try {
            googleEntities = googleComponent.startSearch();
        } catch (IOException e) {
            spiderResultsDTO.setError(true);
            spiderResultsDTO.setErrorDescription("Google search IOException");
            e.printStackTrace();
        }
        if (googleEntities.size() == 0) {
            spiderResultsDTO.setError(true);
            spiderResultsDTO.setErrorDescription("Google search found 0 results");

            model.addAttribute("spiderResultsDTO", spiderResultsDTO);
            model.addAttribute("googleOutputLocationDTO", new GoogleOutputLocationDTO());
            return "googleApi";
        }
        // Adding all results to temp table
        GoogleDAO googleDAO = new GoogleDAO();

        try {
            googleDAO.addAllElements(googleEntities);
        } catch (SQLException e) {
            spiderResultsDTO.setError(true);
            String errorDescription = spiderResultsDTO.getErrorDescription();
            spiderResultsDTO.setErrorDescription(errorDescription + " SQL Error, can't add elements to DB");
            e.printStackTrace();
        }

        ArrayList<GoogleUrlDTO> googleUrlDTOs = GoogleUtils.getDTOsFromEntity(googleEntities);

        // Log fill
        ArrayList<SpiderResultDTO> spiderResultDTOs = GoogleUtils.getLogDTOs(googleEntities);
        spiderResultsDTO.setUrlResultList(spiderResultDTOs);

        // Calculate successful savings
        int numberOfSavedUrl=0;
        for (SpiderResultDTO resultDTO : spiderResultsDTO.getUrlResultList()) {
            if (!resultDTO.isError())
                numberOfSavedUrl++;
        }
        spiderResultsDTO.setNumberOfURLsSaved(numberOfSavedUrl);
        spiderResultsDTO.setNumberOfURLsSendToJobolizer(googleEntities.size());

        model.addAttribute("googleUrlDTOs", googleUrlDTOs);
        model.addAttribute("spiderResultsDTO", spiderResultsDTO);
        model.addAttribute("googleOutputLocationDTO", new GoogleOutputLocationDTO());

        return "googleApi";
    }
}
