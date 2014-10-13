package com.softserveinc.Component.Jobolizer;

import com.softserveinc.DTO.JobolizerResultDTO;
import com.softserveinc.Entity.JobolizerEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by petroborovets on 10/11/14.
 */
@Component
public class JobolizerUtilities {
    public int getNumberOfSuccessfulResults(ArrayList<JobolizerResultDTO> jobolizerResultDTOs) {
        int numberOfSuccessful = 0;

        for (JobolizerResultDTO jobolizerResultDTO : jobolizerResultDTOs) {
            if (!jobolizerResultDTO.isError())
                numberOfSuccessful++;
        }
        return numberOfSuccessful;
    }

    public boolean isNotEmptyBundle(JobolizerEntity jobolizerEntity) {
        boolean notEmpty = false;

        if (jobolizerEntity.isLimitedInTime())
            notEmpty = true;
        else if (jobolizerEntity.isMilitaryRequired())
            notEmpty = true;
        else if (jobolizerEntity.isOperationalAreaRelevance())
            notEmpty = true;
        else if (jobolizerEntity.isOperationalAreaRelevance2())
            notEmpty = true;
        else if (jobolizerEntity.isTravelingRequired())
            notEmpty = true;
        else if (!(jobolizerEntity.getCity() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getCompanyAddress() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getCompanyName() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getCompanyState() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getCompanyUrl() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getContactName() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getEducationalLevel() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getEducationalType() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getEmails() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getEmploymentType() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getIndustriesAndBrunches() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getJobTitle1() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getJobTitle2() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getLanguage() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getLanguageLevel() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getLatitude() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getLongitude() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getPhoneNumbers() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getPosition() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getPostCode() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getSalutation() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getSkillsNeeded() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getVacancyURL() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getWorkExperience() == null))
            notEmpty = true;
        else if (!(jobolizerEntity.getWorkingCountry() == null))
            notEmpty = true;
        else if (jobolizerEntity.getOperationalArea() == null)
            notEmpty = true;
        else if (jobolizerEntity.getOperationalArea2() == null)
            notEmpty = true;

        return notEmpty;
    }
}
