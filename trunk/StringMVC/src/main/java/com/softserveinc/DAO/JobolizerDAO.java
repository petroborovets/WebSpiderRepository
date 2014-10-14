package com.softserveinc.DAO;

import com.softserveinc.Component.DBComponent;
import com.softserveinc.Component.Jobolizer.JobolizerUtilities;
import com.softserveinc.DTO.SpiderResultDTO;
import com.softserveinc.Entity.JobolizerEntity;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by petroborovets on 9/19/14.
 */
public class JobolizerDAO {
    // Jobolizer table
    // General Data
    private static final String jobolizerTableName = DBComponent.tbl_jobolizer;
    private static final String idFieldName = DBComponent.jobolizerIdFieldName;
    private static final String jobTitle1FieldName = DBComponent.jobolizerJobTitle1FieldName;
    private static final String jobTitle2FieldName = DBComponent.jobolizerJobTitle2FieldName;
    private static final String positionFieldName = DBComponent.jobolizerPositionFieldName;
    private static final String employmentTypeFieldName = DBComponent.jobolizerEmploymentTypeFieldName;
    private static final String limitedInTimeFieldName = DBComponent.jobolizerLimitedInTimeFieldName;
    private static final String companyNameFieldName = DBComponent.jobolizerCompanyNameFieldName;
    private static final String industriesAndBrunchesFieldName = DBComponent.jobolizerIndustriesAndBrunchesFieldName;
    // Requirements
    private static final String travelingFieldName = DBComponent.jobolizerTravelingFieldName;
    private static final String militaryFieldName = DBComponent.jobolizerMilitaryFieldName;
    private static final String workExpFieldName = DBComponent.jobolizerWorkExpFieldName;
    private static final String educationalLevelFieldName = DBComponent.jobolizerEducationalLevelFieldName;
    private static final String educationalTypeFieldName = DBComponent.jobolizerEducationalTypeFieldName;
    private static final String skillsNeededFieldName = DBComponent.jobolizerSkillsNeededFieldName;
    // Languages
    private static final String languageFieldName = DBComponent.jobolizerLanguageFieldName;
    private static final String languageLevelFieldName = DBComponent.jobolizerLanguageLevelFieldName;
    // Operation Areas
    private static final String operationalAreaFieldName = DBComponent.jobolizerOperationalAreaFieldName;
    private static final String operationalAreaRelevanceFieldName = DBComponent.jobolizerOperationalAreaRelevanceFieldName;
    private static final String operationalArea2FieldName = DBComponent.jobolizerOperationalArea2FieldName;
    private static final String operationalAreaRelevance2FieldName = DBComponent.jobolizerOperationalAreaRelevance2FieldName;
    // Working place
    private static final String workingCountryFieldName = DBComponent.jobolizerWorkingCountryFieldName;
    // Contact details
    private static final String emailsFieldName = DBComponent.jobolizerEmailsFieldName;
    private static final String cityFieldName = DBComponent.jobolizerCityFieldName;
    private static final String postCodeFieldName = DBComponent.jobolizerPostCodeFieldName;
    private static final String phoneNumbersFieldName = DBComponent.jobolizerPhoneNumbersFieldName;
    private static final String salutationFieldName = DBComponent.jobolizerSalutationFieldName;
    private static final String contactNameFieldName = DBComponent.jobolizerContactNameFieldName;
    // Other
    private static final String companyUrlFieldName = DBComponent.jobolizerCompanyUrlFieldName;
    private static final String companyAddressFieldName = DBComponent.jobolizerCompanyAddressFieldName;
    private static final String companyStateFieldName = DBComponent.jobolizerCompanyStateFieldName;
    private static final String latitudeFieldName = DBComponent.jobolizerLatitudeFieldName;
    private static final String longitudeFieldName = DBComponent.jobolizerLongitudeFieldName;
    private static final String vacancyURLFieldName = DBComponent.jobolizerVacancyURL;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;

    // DB sql's
    private String insertEmailContextString = "INSERT INTO " + jobolizerTableName
            + "(" + jobTitle1FieldName + ", "
            + jobTitle2FieldName + ", "
            + positionFieldName + ", "
            + employmentTypeFieldName + ", "
            + limitedInTimeFieldName + ", "
            + companyNameFieldName + ", "
            + industriesAndBrunchesFieldName + ", "
            + travelingFieldName + ", "
            + militaryFieldName + ", "
            + workExpFieldName + ", "
            + educationalLevelFieldName + ", "
            + educationalTypeFieldName + ", "
            + skillsNeededFieldName + ", "
            + languageFieldName + ", "
            + languageLevelFieldName + ", "
            + operationalAreaFieldName + ", "
            + operationalAreaRelevanceFieldName + ", "
            + operationalArea2FieldName + ", "
            + operationalAreaRelevance2FieldName + ", "
            + workingCountryFieldName + ", "
            + emailsFieldName + ", "
            + cityFieldName + ", "
            + postCodeFieldName + ", "
            + phoneNumbersFieldName + ", "
            + salutationFieldName + ", "
            + contactNameFieldName + ", "
            + companyUrlFieldName + ", "
            + companyAddressFieldName + ", "
            + companyStateFieldName + ", "
            + latitudeFieldName + ", "
            + longitudeFieldName + ", "
            + vacancyURLFieldName + ") VALUES"
            + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    String selectDatabase = DBComponent.selectDatabase;

    public void addElement(JobolizerEntity jobolizerEntity) throws SQLException {

        System.out.println("Trying to add jobolizer to db:" + jobolizerEntity.getVacancyURL());

        if (isUnique(jobolizerEntity)) {
            connection = new DBComponent().getConnection();
            statement = connection.createStatement();
            statement.execute(selectDatabase);

            preparedStatement = connection.prepareStatement(insertEmailContextString, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, jobolizerEntity.getJobTitle1());
            preparedStatement.setString(2, jobolizerEntity.getJobTitle2());
            preparedStatement.setString(3, jobolizerEntity.getPosition());
            preparedStatement.setString(4, jobolizerEntity.getEmploymentType());
            preparedStatement.setBoolean(5, jobolizerEntity.isLimitedInTime());
            preparedStatement.setString(6, jobolizerEntity.getCompanyName());
            preparedStatement.setString(7, jobolizerEntity.getIndustriesAndBrunches());
            preparedStatement.setBoolean(8, jobolizerEntity.isTravelingRequired());
            preparedStatement.setBoolean(9, jobolizerEntity.isMilitaryRequired());
            preparedStatement.setString(10, jobolizerEntity.getWorkExperience());
            preparedStatement.setString(11, jobolizerEntity.getEducationalLevel());
            preparedStatement.setString(12, jobolizerEntity.getEmploymentType());
            preparedStatement.setString(13, jobolizerEntity.getSkillsNeeded());
            preparedStatement.setString(14, jobolizerEntity.getLanguage());
            preparedStatement.setString(15, jobolizerEntity.getLanguageLevel());
            preparedStatement.setString(16, jobolizerEntity.getOperationalArea());
            preparedStatement.setBoolean(17, jobolizerEntity.isOperationalAreaRelevance());
            preparedStatement.setString(18, jobolizerEntity.getOperationalArea2());
            preparedStatement.setBoolean(19, jobolizerEntity.isOperationalAreaRelevance2());
            preparedStatement.setString(20, jobolizerEntity.getWorkingCountry());
            preparedStatement.setString(21, jobolizerEntity.getEmails());
            preparedStatement.setString(22, jobolizerEntity.getCity());
            preparedStatement.setString(23, jobolizerEntity.getPostCode());
            preparedStatement.setString(24, jobolizerEntity.getPhoneNumbers());
            preparedStatement.setString(25, jobolizerEntity.getSalutation());
            preparedStatement.setString(26, jobolizerEntity.getContactName());
            preparedStatement.setString(27, jobolizerEntity.getCompanyUrl());
            preparedStatement.setString(28, jobolizerEntity.getCompanyAddress());
            preparedStatement.setString(29, jobolizerEntity.getCompanyState());
            preparedStatement.setString(30, jobolizerEntity.getLatitude());
            preparedStatement.setString(31, jobolizerEntity.getLongitude());
            preparedStatement.setString(32, jobolizerEntity.getVacancyURL());
            int affectedRows = 0;
            try {
                affectedRows = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Cant add jobolizerBundle to DB " + jobolizerEntity.getJobTitle1());
            }

            if (affectedRows == 0) {
                throw new SQLException("DB: Creating email failed, no rows affected.");
            }
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            jobolizerEntity.setId(resultSet.getLong(1));

            System.out.println("jobolizerBundle (" + jobolizerEntity.getId() + ") added to DB");
        } else {
            System.out.println("JobolizerBundle already exists in DB :" + jobolizerEntity.getEmails());
        }
    }

    public void addElement(JobolizerEntity jobolizerEntity, SpiderResultDTO spiderResultDTO) throws SQLException {

        System.out.println("Trying to add jobolizer to db:" + jobolizerEntity.getVacancyURL());
        spiderResultDTO.setVacancyURL(jobolizerEntity.getVacancyURL());

        if (!new JobolizerUtilities().isNotEmptyBundle(jobolizerEntity)) {
            spiderResultDTO.setError(true);
            spiderResultDTO.setErrorDescription("Error: Jobolizer vacancy is empty");
        }


        if (isUnique(jobolizerEntity, spiderResultDTO)) {
            connection = new DBComponent().getConnection();
            statement = connection.createStatement();
            statement.execute(selectDatabase);

            preparedStatement = connection.prepareStatement(insertEmailContextString, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, jobolizerEntity.getJobTitle1());
            preparedStatement.setString(2, jobolizerEntity.getJobTitle2());
            preparedStatement.setString(3, jobolizerEntity.getPosition());
            preparedStatement.setString(4, jobolizerEntity.getEmploymentType());
            preparedStatement.setBoolean(5, jobolizerEntity.isLimitedInTime());
            preparedStatement.setString(6, jobolizerEntity.getCompanyName());
            preparedStatement.setString(7, jobolizerEntity.getIndustriesAndBrunches());
            preparedStatement.setBoolean(8, jobolizerEntity.isTravelingRequired());
            preparedStatement.setBoolean(9, jobolizerEntity.isMilitaryRequired());
            preparedStatement.setString(10, jobolizerEntity.getWorkExperience());
            preparedStatement.setString(11, jobolizerEntity.getEducationalLevel());
            preparedStatement.setString(12, jobolizerEntity.getEmploymentType());
            preparedStatement.setString(13, jobolizerEntity.getSkillsNeeded());
            preparedStatement.setString(14, jobolizerEntity.getLanguage());
            preparedStatement.setString(15, jobolizerEntity.getLanguageLevel());
            preparedStatement.setString(16, jobolizerEntity.getOperationalArea());
            preparedStatement.setBoolean(17, jobolizerEntity.isOperationalAreaRelevance());
            preparedStatement.setString(18, jobolizerEntity.getOperationalArea2());
            preparedStatement.setBoolean(19, jobolizerEntity.isOperationalAreaRelevance2());
            preparedStatement.setString(20, jobolizerEntity.getWorkingCountry());
            preparedStatement.setString(21, jobolizerEntity.getEmails());
            preparedStatement.setString(22, jobolizerEntity.getCity());
            preparedStatement.setString(23, jobolizerEntity.getPostCode());
            preparedStatement.setString(24, jobolizerEntity.getPhoneNumbers());
            preparedStatement.setString(25, jobolizerEntity.getSalutation());
            preparedStatement.setString(26, jobolizerEntity.getContactName());
            preparedStatement.setString(27, jobolizerEntity.getCompanyUrl());
            preparedStatement.setString(28, jobolizerEntity.getCompanyAddress());
            preparedStatement.setString(29, jobolizerEntity.getCompanyState());
            preparedStatement.setString(30, jobolizerEntity.getLatitude());
            preparedStatement.setString(31, jobolizerEntity.getLongitude());
            preparedStatement.setString(32, jobolizerEntity.getVacancyURL());
            int affectedRows = 0;
            try {
                affectedRows = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Cant add jobolizerBundle to DB " + jobolizerEntity.getJobTitle1());
            }

            if (affectedRows == 0) {
                throw new SQLException("DB: Creating email failed, no rows affected.");
            }
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            jobolizerEntity.setId(resultSet.getLong(1));
            spiderResultDTO.setId(jobolizerEntity.getId());

            System.out.println("jobolizerBundle (" + jobolizerEntity.getId() + ") added to DB");
        } else {
            System.out.println("JobolizerBundle already exists in DB :" + jobolizerEntity.getEmails());
        }
    }

    public void addAllElements(ArrayList<JobolizerEntity> jobolizerEntities) throws SQLException {
        for (JobolizerEntity jobolizerEntity : jobolizerEntities) {
            addElement(jobolizerEntity);
        }
    }

    public ArrayList<JobolizerEntity> getAllElements() throws SQLException {
        ArrayList<JobolizerEntity> jobolizerEntities = new ArrayList<JobolizerEntity>();

        connection = new DBComponent().getConnection();
        statement = connection.createStatement();
        statement.execute(selectDatabase);

        String getAllJobolizerBundles = "SELECT * FROM " + jobolizerTableName;

        ResultSet resultSet = statement.executeQuery(getAllJobolizerBundles);

        while (resultSet.next()) {
            JobolizerEntity jobolizerEntity = new JobolizerEntity();

            long id = resultSet.getLong(idFieldName);
            String jobTitle1 = resultSet.getString(jobTitle1FieldName);
            String jobTitle2 = resultSet.getString(jobTitle2FieldName);
            String position = resultSet.getString(positionFieldName);
            String employmentType = resultSet.getString(employmentTypeFieldName);
            boolean limitedInTime = resultSet.getBoolean(limitedInTimeFieldName);
            String companyName = resultSet.getString(companyNameFieldName);
            String industriesAndBrunches = resultSet.getString(industriesAndBrunchesFieldName);
            boolean traveling = resultSet.getBoolean(travelingFieldName);
            boolean military = resultSet.getBoolean(militaryFieldName);
            String workExp = resultSet.getString(workExpFieldName);
            String educationalLevel = resultSet.getString(educationalLevelFieldName);
            String educationalType = resultSet.getString(educationalTypeFieldName);
            String skillsNeeded = resultSet.getString(skillsNeededFieldName);
            String language = resultSet.getString(languageFieldName);
            String languageLevel = resultSet.getString(languageLevelFieldName);
            String operationalArea = resultSet.getString(operationalAreaFieldName);
            boolean operationalAreaRelevance = resultSet.getBoolean(operationalAreaRelevanceFieldName);
            String operationalArea2 = resultSet.getString(operationalArea2FieldName);
            boolean operationalAreaRelevance2 = resultSet.getBoolean(operationalAreaRelevance2FieldName);
            String workingCountry = resultSet.getString(workingCountryFieldName);
            String emails = resultSet.getString(emailsFieldName);
            String city = resultSet.getString(cityFieldName);
            String postCode = resultSet.getString(postCodeFieldName);
            String phoneNumbers = resultSet.getString(phoneNumbersFieldName);
            String salutation = resultSet.getString(salutationFieldName);
            String contactName = resultSet.getString(contactNameFieldName);
            String companyUrl = resultSet.getString(companyUrlFieldName);
            String companyAddress = resultSet.getString(companyAddressFieldName);
            String companyState = resultSet.getString(companyStateFieldName);
            String latitude = resultSet.getString(latitudeFieldName);
            String longitude = resultSet.getString(longitudeFieldName);
            String vacancyURL = resultSet.getString(vacancyURLFieldName);

            jobolizerEntity.setId(id);
            jobolizerEntity.setJobTitle1(jobTitle1);
            jobolizerEntity.setJobTitle2(jobTitle2);
            jobolizerEntity.setPosition(position);
            jobolizerEntity.setEmploymentType(employmentType);
            jobolizerEntity.setLimitedInTime(limitedInTime);
            jobolizerEntity.setCompanyName(companyName);
            jobolizerEntity.setIndustriesAndBrunches(industriesAndBrunches);
            jobolizerEntity.setTravelingRequired(traveling);
            jobolizerEntity.setMilitaryRequired(military);
            jobolizerEntity.setWorkExperience(workExp);
            jobolizerEntity.setEducationalLevel(educationalLevel);
            jobolizerEntity.setEducationalType(educationalType);
            jobolizerEntity.setSkillsNeeded(skillsNeeded);
            jobolizerEntity.setLanguage(language);
            jobolizerEntity.setLanguageLevel(languageLevel);
            jobolizerEntity.setOperationalArea(operationalArea);
            jobolizerEntity.setOperationalAreaRelevance(operationalAreaRelevance);
            jobolizerEntity.setOperationalArea2(operationalArea2);
            jobolizerEntity.setOperationalAreaRelevance2(operationalAreaRelevance2);
            jobolizerEntity.setWorkingCountry(workingCountry);
            jobolizerEntity.setEmails(emails);
            jobolizerEntity.setCity(city);
            jobolizerEntity.setPostCode(postCode);
            jobolizerEntity.setPhoneNumbers(phoneNumbers);
            jobolizerEntity.setSalutation(salutation);
            jobolizerEntity.setContactName(contactName);
            jobolizerEntity.setCompanyUrl(companyUrl);
            jobolizerEntity.setCompanyAddress(companyAddress);
            jobolizerEntity.setCompanyState(companyState);
            jobolizerEntity.setLatitude(latitude);
            jobolizerEntity.setLongitude(longitude);
            jobolizerEntity.setVacancyURL(vacancyURL);

            if (jobolizerEntity.getCompanyUrl() != null)
                jobolizerEntity.getCompanyUrl().replaceAll(" ", "");

            jobolizerEntities.add(jobolizerEntity);
        }

        resultSet.close();
        connection.close();

        return jobolizerEntities;
    }

    public void deleteElementById(long elementId) throws SQLException {
        connection = new DBComponent().getConnection();
        Statement statement = connection.createStatement();
        statement.execute(selectDatabase);

        String deleteBundleById = "DELETE FROM " + jobolizerTableName +
                " WHERE " + idFieldName + " = " + elementId;

        statement.execute(deleteBundleById);
    }

    public JobolizerEntity getElementById(long elementId) throws SQLException {
        JobolizerEntity jobolizerEntityToReturn = new JobolizerEntity();

        connection = new DBComponent().getConnection();
        Statement statement = connection.createStatement();
        statement.execute(selectDatabase);

        String getAllJobolizerBundles = "SELECT * FROM " + jobolizerTableName +
                " WHERE " + idFieldName + " = " + elementId;

        ResultSet resultSet = statement.executeQuery(getAllJobolizerBundles);


        while (resultSet.next()) {
            long id = resultSet.getLong(idFieldName);
            String jobTitle1 = resultSet.getString(jobTitle1FieldName);
            String jobTitle2 = resultSet.getString(jobTitle2FieldName);
            String position = resultSet.getString(positionFieldName);
            String employmentType = resultSet.getString(employmentTypeFieldName);
            boolean limitedInTime = resultSet.getBoolean(limitedInTimeFieldName);
            String companyName = resultSet.getString(companyNameFieldName);
            String industriesAndBrunches = resultSet.getString(industriesAndBrunchesFieldName);
            boolean traveling = resultSet.getBoolean(travelingFieldName);
            boolean military = resultSet.getBoolean(militaryFieldName);
            String workExp = resultSet.getString(workExpFieldName);
            String educationalLevel = resultSet.getString(educationalLevelFieldName);
            String educationalType = resultSet.getString(educationalTypeFieldName);
            String skillsNeeded = resultSet.getString(skillsNeededFieldName);
            String language = resultSet.getString(languageFieldName);
            String languageLevel = resultSet.getString(languageLevelFieldName);
            String operationalArea = resultSet.getString(operationalAreaFieldName);
            boolean operationalAreaRelevance = resultSet.getBoolean(operationalAreaRelevanceFieldName);
            String operationalArea2 = resultSet.getString(operationalArea2FieldName);
            boolean operationalAreaRelevance2 = resultSet.getBoolean(operationalAreaRelevance2FieldName);
            String workingCountry = resultSet.getString(workingCountryFieldName);
            String emails = resultSet.getString(emailsFieldName);
            String city = resultSet.getString(cityFieldName);
            String postCode = resultSet.getString(postCodeFieldName);
            String phoneNumbers = resultSet.getString(phoneNumbersFieldName);
            String salutation = resultSet.getString(salutationFieldName);
            String contactName = resultSet.getString(contactNameFieldName);
            String companyUrl = resultSet.getString(companyUrlFieldName);
            String companyAddress = resultSet.getString(companyAddressFieldName);
            String companyState = resultSet.getString(companyStateFieldName);
            String latitude = resultSet.getString(latitudeFieldName);
            String longitude = resultSet.getString(longitudeFieldName);
            String vacancyURL = resultSet.getString(vacancyURLFieldName);

            jobolizerEntityToReturn.setId(id);
            jobolizerEntityToReturn.setJobTitle1(jobTitle1);
            jobolizerEntityToReturn.setJobTitle2(jobTitle2);
            jobolizerEntityToReturn.setPosition(position);
            jobolizerEntityToReturn.setEmploymentType(employmentType);
            jobolizerEntityToReturn.setLimitedInTime(limitedInTime);
            jobolizerEntityToReturn.setCompanyName(companyName);
            jobolizerEntityToReturn.setIndustriesAndBrunches(industriesAndBrunches);
            jobolizerEntityToReturn.setTravelingRequired(traveling);
            jobolizerEntityToReturn.setMilitaryRequired(military);
            jobolizerEntityToReturn.setWorkExperience(workExp);
            jobolizerEntityToReturn.setEducationalLevel(educationalLevel);
            jobolizerEntityToReturn.setEducationalType(educationalType);
            jobolizerEntityToReturn.setSkillsNeeded(skillsNeeded);
            jobolizerEntityToReturn.setLanguage(language);
            jobolizerEntityToReturn.setLanguageLevel(languageLevel);
            jobolizerEntityToReturn.setOperationalArea(operationalArea);
            jobolizerEntityToReturn.setOperationalAreaRelevance(operationalAreaRelevance);
            jobolizerEntityToReturn.setOperationalArea2(operationalArea2);
            jobolizerEntityToReturn.setOperationalAreaRelevance2(operationalAreaRelevance2);
            jobolizerEntityToReturn.setWorkingCountry(workingCountry);
            jobolizerEntityToReturn.setEmails(emails);
            jobolizerEntityToReturn.setCity(city);
            jobolizerEntityToReturn.setPostCode(postCode);
            jobolizerEntityToReturn.setPhoneNumbers(phoneNumbers);
            jobolizerEntityToReturn.setSalutation(salutation);
            jobolizerEntityToReturn.setContactName(contactName);
            jobolizerEntityToReturn.setCompanyUrl(companyUrl);
            jobolizerEntityToReturn.setCompanyAddress(companyAddress);
            jobolizerEntityToReturn.setCompanyState(companyState);
            jobolizerEntityToReturn.setLatitude(latitude);
            jobolizerEntityToReturn.setLongitude(longitude);
            jobolizerEntityToReturn.setVacancyURL(vacancyURL);
        }
        resultSet.close();
        connection.close();

        return jobolizerEntityToReturn;
    }

    private boolean isUnique(JobolizerEntity jobolizerEntityToAdd, SpiderResultDTO spiderResultDTO) {

        ArrayList<JobolizerEntity> jobolizerBundlesInDB = null;
        try {
            jobolizerBundlesInDB = getAllElements();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (JobolizerEntity jobolizerEntity : jobolizerBundlesInDB) {

            String existingJobolizerEntityUrl = jobolizerEntity.getVacancyURL();

            if (jobolizerEntityToAdd.getVacancyURL().equals(existingJobolizerEntityUrl)) {
                spiderResultDTO.setError(true);
                spiderResultDTO.setErrorDescription("Vacancy already exists in db with id#("+jobolizerEntity.getId()+"). ");

                return false;
            }
        }

        return true;
    }
    private boolean isUnique(JobolizerEntity jobolizerEntityToAdd) {
        ArrayList<JobolizerEntity> jobolizerBundlesInDB = null;
        try {
            jobolizerBundlesInDB = getAllElements();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (JobolizerEntity jobolizerEntity : jobolizerBundlesInDB) {
            String existingJobolizerEntityUrl = jobolizerEntity.getVacancyURL();
            if (jobolizerEntityToAdd.getVacancyURL().equals(existingJobolizerEntityUrl)) {
                return false;
            }
        }
        return true;
    }

}
