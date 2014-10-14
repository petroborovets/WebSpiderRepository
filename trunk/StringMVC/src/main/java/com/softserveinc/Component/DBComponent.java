package com.softserveinc.Component;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by petroborovets on 9/3/14.
 */
public class DBComponent {
    // JDBC driver name and database URL
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String dbUrl = "jdbc:mysql://euve33184.vserver.de/";
    //    public static final String dbUrl = "jdbc:mysql://euve33184.vserver.de/";
    //    public static final String dbUrl = "jdbc:mysql://localhost";

    //  Database credentials
    public static String databaseName = "arztdata";
    public static String username = "user_arztdata";
    public static String password = "user_arztdata83";

    public static final String selectDatabase = "use " + databaseName + ";";

    // New tables
    // Email table
    public static final String tbl_email = "tbl_email";
    public static final String emailIdFieldName = "id";
    public static final String emailCompanyIdFieldName = "company_id";
    public static final String emailEmailFieldName = "email";
    public static final String emailUrlFieldName = "url";
    public static final String emailLeftContextListFieldName = "context_left_list";
    public static final String emailRightContextListFieldName = "context_right_list";
    public static final String emailJobApplyTypeFieldName = "job_apply_type";
    public static final String emailDoctorTypeFieldName = "doctor_type";
    public static final String emailManagementTypeFieldName = "management_type";
    public static final String emailProgrammingDevTypeFieldName = "programming_dev_type";
    public static final String emailITTypeFieldName = "it_type";
    public static final String emailHRTypeFieldName = "hr_type";
    public static final String emailSalesTypeFieldName = "sales_type";
    public static final String emailInfoTypeFieldName = "info_type";
    // Company table
    public static final String tbl_company = "tbl_company";
    public static final String companyIdFieldName = "id";
    public static final String companyDomainURLFieldName = "domain_url";
    public static final String companyZipFieldName = "zip";
    public static final String companyTypeFieldName = "type";
    public static final String companyTelephoneFieldName = "telephone";
    // EmailContext table
    public static final String tbl_email_context = "tbl_email_context";
    public static final String emailContextIdFieldName = "id";
    public static final String emailContextEmailIdFieldName = "email_id";
    public static final String emailContextLeftContext = "left_context";
    public static final String emailContextRightContext = "right_context";
    // Jobolizer table
    // General Data
    public static final String tbl_jobolizer = "tbl_jobolizer";
    public static final String jobolizerIdFieldName = "id";
    public static final String jobolizerJobTitle1FieldName = "job_title1";
    public static final String jobolizerJobTitle2FieldName = "job_title2";
    public static final String jobolizerPositionFieldName = "position";
    public static final String jobolizerEmploymentTypeFieldName = "employment_type";
    public static final String jobolizerLimitedInTimeFieldName = "limited_in_time";
    public static final String jobolizerCompanyNameFieldName = "company_name";
    public static final String jobolizerIndustriesAndBrunchesFieldName = "industries_brunches";
    // Requirements
    public static final String jobolizerTravelingFieldName = "traveling_required";
    public static final String jobolizerMilitaryFieldName = "military_required";
    public static final String jobolizerWorkExpFieldName = "work_experience_required";
    public static final String jobolizerEducationalLevelFieldName = "education_level_required";
    public static final String jobolizerEducationalTypeFieldName = "education_tyoe_required";
    public static final String jobolizerSkillsNeededFieldName = "skills_required";
    // Languages
    public static final String jobolizerLanguageFieldName = "language";
    public static final String jobolizerLanguageLevelFieldName = "language_level";
    // Operation Areas
    public static final String jobolizerOperationalAreaFieldName = "operational1_area";
    public static final String jobolizerOperationalAreaRelevanceFieldName = "operational_area1_relevance";
    public static final String jobolizerOperationalArea2FieldName = "operational2_area";
    public static final String jobolizerOperationalAreaRelevance2FieldName = "operational2_area_relevance";
    // Working place
    public static final String jobolizerWorkingCountryFieldName = "working_country";
    // Contact details
    public static final String jobolizerEmailsFieldName = "contact_emails";
    public static final String jobolizerCityFieldName = "contact_city";
    public static final String jobolizerPostCodeFieldName = "contact_post_code";
    public static final String jobolizerPhoneNumbersFieldName = "contact_phones";
    public static final String jobolizerSalutationFieldName = "contact_salutation";
    public static final String jobolizerContactNameFieldName = "contact_name";
    // Other
    public static final String jobolizerCompanyUrlFieldName = "company_url";
    public static final String jobolizerCompanyAddressFieldName = "company_address";
    public static final String jobolizerCompanyStateFieldName = "company_state";
    public static final String jobolizerLatitudeFieldName = "latitude";
    public static final String jobolizerLongitudeFieldName = "longitude";
    public static final String jobolizerVacancyURL = "vacancy_url";
    // GoogleApi table
    public static final String tbl_google_api = "tbl_google_api";
    public static final String googleApiIdFieldName = "id";
    public static final String googleApiUrlFieldName = "url";
    public static final String googleApiUrlDescriptionFieldName = "url_description";

    public DBComponent() {

    }

    public void createDatabase() {

        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            //conn = DriverManager.getConnection(dbUrl, username, password);
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection("jdbc:mysql://euve33184.vserver.de/?" + "user=user_arztdata&password=user_arztdata83");

            //STEP 4: Execute a query
            System.out.println("Creating database...");
            stmt = conn.createStatement();

            String sql = "CREATE DATABASE IF NOT EXISTS " + databaseName;
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");
        } catch (SQLException se) {
            System.out.println("Error, can't exec sql for creating db.");
            se.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error, can't find JDBC driver.");
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end try
        }//end finally
    }//end createDatabase()

    public void createGoogleApiTable() {
        String createTblGoogleApi =
                "CREATE TABLE IF NOT EXISTS " + tbl_google_api + " (" +
                        "  " + googleApiIdFieldName + " INTEGER NOT NULL AUTO_INCREMENT," +
                        "  " + googleApiUrlFieldName + " VARCHAR(850) DEFAULT NULL," +
                        "  " + googleApiUrlDescriptionFieldName + " VARCHAR(200) DEFAULT NULL," +
                        "  PRIMARY KEY (" + googleApiIdFieldName + ")" +
                        ");";
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();

            statement.execute(selectDatabase);
            System.out.println("Creating google table...");
            statement.execute(createTblGoogleApi);

            System.out.println("Google table created successfully...");

        } catch (SQLException e) {
            System.out.println("Cant execute sql for google table creation. ");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Cant get JDBC driver. ");
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException se2) {
                System.out.println("Problems with closing statement with google sql. ");
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                System.out.println("Problems with closing connection with google sql. ");
                se.printStackTrace();
            }//end try
        }
    }

    public void createJobolizerTables() {
        String createTblJobolizer =
                "CREATE TABLE IF NOT EXISTS " + tbl_jobolizer + " (" +
                        "  " + jobolizerIdFieldName + " INTEGER NOT NULL AUTO_INCREMENT," +
                        "  " + jobolizerJobTitle1FieldName + " VARCHAR(100) DEFAULT NULL," +
                        "  " + jobolizerJobTitle2FieldName + " VARCHAR(100) DEFAULT NULL," +
                        "  " + jobolizerPositionFieldName + " VARCHAR(100) DEFAULT NULL," +
                        "  " + jobolizerEmploymentTypeFieldName + " VARCHAR(100) DEFAULT NULL," +
                        "  " + jobolizerLimitedInTimeFieldName + " TINYINT(1) DEFAULT NULL," +
                        "  " + jobolizerCompanyNameFieldName + " VARCHAR(100) DEFAULT NULL," +
                        "  " + jobolizerIndustriesAndBrunchesFieldName + " VARCHAR(200) DEFAULT NULL," +
                        "  " + jobolizerTravelingFieldName + " TINYINT(1) DEFAULT NULL," +
                        "  " + jobolizerMilitaryFieldName + " TINYINT(1) DEFAULT NULL," +
                        "  " + jobolizerLanguageFieldName + " VARCHAR(100) DEFAULT NULL," +
                        "  " + jobolizerWorkExpFieldName + " VARCHAR(100) DEFAULT NULL," +
                        "  " + jobolizerEducationalLevelFieldName + " VARCHAR(100) DEFAULT NULL," +
                        "  " + jobolizerEducationalTypeFieldName + " VARCHAR(100) DEFAULT NULL," +
                        "  " + jobolizerSkillsNeededFieldName + " VARCHAR(100) DEFAULT NULL," +
                        "  " + jobolizerLanguageLevelFieldName + " VARCHAR(100) DEFAULT NULL," +
                        "  " + jobolizerOperationalAreaFieldName + " VARCHAR(100) DEFAULT NULL," +
                        "  " + jobolizerOperationalAreaRelevanceFieldName + " TINYINT(1) DEFAULT NULL," +
                        "  " + jobolizerOperationalArea2FieldName + " VARCHAR(100) DEFAULT NULL," +
                        "  " + jobolizerOperationalAreaRelevance2FieldName + " TINYINT(1) DEFAULT NULL," +
                        "  " + jobolizerWorkingCountryFieldName + " VARCHAR(100) DEFAULT NULL," +
                        "  " + jobolizerEmailsFieldName + " VARCHAR(300) DEFAULT NULL," +
                        "  " + jobolizerCityFieldName + " VARCHAR(50) DEFAULT NULL," +
                        "  " + jobolizerPostCodeFieldName + " VARCHAR(20) DEFAULT NULL," +
                        "  " + jobolizerPhoneNumbersFieldName + " VARCHAR(100) DEFAULT NULL," +
                        "  " + jobolizerSalutationFieldName + " VARCHAR(6) DEFAULT NULL," +
                        "  " + jobolizerContactNameFieldName + " VARCHAR(30) DEFAULT NULL," +
                        "  " + jobolizerCompanyUrlFieldName + " VARCHAR(200) DEFAULT NULL," +
                        "  " + jobolizerCompanyAddressFieldName + " VARCHAR(100) DEFAULT NULL," +
                        "  " + jobolizerCompanyStateFieldName + " VARCHAR(30) DEFAULT NULL," +
                        "  " + jobolizerLatitudeFieldName + " VARCHAR(20) DEFAULT NULL," +
                        "  " + jobolizerLongitudeFieldName + " VARCHAR(20) DEFAULT NULL," +
                        "  " + jobolizerVacancyURL + " VARCHAR(850) DEFAULT NULL," +
                        "  PRIMARY KEY (" + jobolizerIdFieldName + ")" +
                        ");";
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();

            statement.execute(selectDatabase);
            System.out.println("Creating jobolizer table...");
            statement.execute(createTblJobolizer);

            System.out.println("Jobolizer tables created successfully...");

        } catch (SQLException e) {
            System.out.println("Cant execute sql's for tables creation");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Cant get JDBC driver");
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException se2) {
                System.out.println("Problems with closing statement");
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                System.out.println("Problems with closing connection");
                se.printStackTrace();
            }//end try
        }
    }

    public void createCrawlerTables() {

        String createTblEmail =
                "CREATE TABLE IF NOT EXISTS " + tbl_email + " (" +
                        "  " + emailIdFieldName + " INTEGER NOT NULL AUTO_INCREMENT," +
                        "  " + emailCompanyIdFieldName + " INTEGER," +
                        "  " + emailEmailFieldName + " VARCHAR(50) ," +
                        "  " + emailUrlFieldName + " VARCHAR(100) ," +
                        "  " + emailLeftContextListFieldName + " VARCHAR(10000) ," +
                        "  " + emailRightContextListFieldName + " VARCHAR(10000) ," +
                        "  " + emailJobApplyTypeFieldName + " TINYINT(1) DEFAULT NULL," +
                        "  " + emailDoctorTypeFieldName + " TINYINT(1) DEFAULT NULL," +
                        "  " + emailManagementTypeFieldName + " TINYINT(1) DEFAULT NULL," +
                        "  " + emailProgrammingDevTypeFieldName + " TINYINT(1) DEFAULT NULL," +
                        "  " + emailITTypeFieldName + " TINYINT(1) DEFAULT NULL," +
                        "  " + emailHRTypeFieldName + " TINYINT(1) DEFAULT NULL," +
                        "  " + emailSalesTypeFieldName + " TINYINT(1) DEFAULT NULL," +
                        "  " + emailInfoTypeFieldName + " TINYINT(1) DEFAULT NULL," +
                        "  PRIMARY KEY (" + emailIdFieldName + ")" +
                        ");";
        String createTblCompany =
                "CREATE TABLE IF NOT EXISTS " + tbl_company + " (" +
                        "  " + companyIdFieldName + " INTEGER NOT NULL AUTO_INCREMENT," +
                        "  " + companyDomainURLFieldName + " VARCHAR(100) DEFAULT NULL," +
                        "  " + companyZipFieldName + " INTEGER DEFAULT NULL," +
                        "  " + companyTypeFieldName + " VARCHAR(30) DEFAULT NULL," +
                        "  " + companyTelephoneFieldName + " INTEGER DEFAULT NULL," +
                        "  PRIMARY KEY (" + companyIdFieldName + ")" +
                        ");";
        String createTblEmailContext =
                "CREATE TABLE IF NOT EXISTS " + tbl_email_context + " (" +
                        "  " + emailContextIdFieldName + " INTEGER NOT NULL AUTO_INCREMENT," +
                        "  " + emailContextEmailIdFieldName + " INTEGER NOT NULL," +
                        "  " + emailContextLeftContext + " VARCHAR(10000)," +
                        "  " + emailContextRightContext + " VARCHAR(10000)," +
                        "  PRIMARY KEY (" + emailContextIdFieldName + ")" +
                        ");";

        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();

            statement.execute(selectDatabase);
            System.out.println("Creating database...");
            statement.execute(createTblEmail);
            statement.execute(createTblCompany);
            statement.execute(createTblEmailContext);

            System.out.println("Database tables created successfully...");

        } catch (SQLException e) {
            System.out.println("Cant execute sql's for tables creation");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Cant get JDBC driver");
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException se2) {
                System.out.println("Problems with closing statement");
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                System.out.println("Problems with closing connection");
                se.printStackTrace();
            }//end try
        }
    }

    public Connection getConnection() throws SQLException {
        //return DriverManager.getConnection(dbUrl, username, password);
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Cant find jdbc driver");
        }
        return DriverManager.getConnection("jdbc:mysql://euve33184.vserver.de/?" + "user=user_arztdata&password=user_arztdata83");
    }
}
