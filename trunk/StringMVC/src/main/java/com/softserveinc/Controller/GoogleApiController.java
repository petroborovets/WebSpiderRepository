package com.softserveinc.Controller;

import com.softserveinc.DTO.GoogleOutputLocationDTO;
import com.softserveinc.DTO.JobolizerResourceDTO;
import com.softserveinc.DTO.SpiderResultDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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




        model.addAttribute("spiderResultsDTO",new  SpiderResultDTO()); //TODO change
        model.addAttribute("googleOutputLocationDTO", new GoogleOutputLocationDTO());
        return "googleApi";
    }
}
