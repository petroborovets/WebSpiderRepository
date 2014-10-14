package com.softserveinc.DAO;

import com.softserveinc.Component.DBComponent;
import com.softserveinc.DTO.GoogleOutputLocationDTO;
import com.softserveinc.Entity.GoogleEntity;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by petroborovets on 10/13/14.
 */
public class GoogleDAO {

    public static final String tableName = DBComponent.tbl_google_api;
    public static final String idFieldName = DBComponent.googleApiIdFieldName;
    public static final String urlFieldName = DBComponent.googleApiUrlFieldName;
    public static final String urlDescriptionFieldName = DBComponent.googleApiUrlDescriptionFieldName;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private GoogleOutputLocationDTO googleOutputLocationDTO;
    private String selectDatabase = DBComponent.selectDatabase;

    public GoogleDAO() {
        DBComponent dbComponent = new DBComponent();
        dbComponent.createDatabase();
        dbComponent.createGoogleApiTable();
    }

    GoogleDAO(GoogleOutputLocationDTO googleOutputLocationDTO) {
        this.googleOutputLocationDTO = googleOutputLocationDTO;
    }

    public void addElement(GoogleEntity googleEntity) throws NullPointerException, SQLException {

        if (isUnique(googleEntity)) {
            String insertGoogleElementString = "INSERT INTO " + tableName
                    + "(" + urlFieldName + ", "
                    + urlDescriptionFieldName + ") VALUES"
                    + "(?,?)";

            System.out.println("Trying to add google element to db:" + googleEntity.getUrl());
            connection = new DBComponent().getConnection();
            statement = connection.createStatement();
            statement.execute(selectDatabase);

            preparedStatement = connection.prepareStatement(insertGoogleElementString, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, googleEntity.getUrl());
            preparedStatement.setString(2, googleEntity.getUrlInfo());
            int affectedRows = 0;
            try {
                affectedRows = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Cant add Google element to DB " + googleEntity.getUrl());
            }
            if (affectedRows == 0) {
                throw new SQLException("DB: Creating google element failed, no rows affected.");
            }
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            googleEntity.setId(resultSet.getLong(1));

            System.out.println("Google element (" + googleEntity.getId() + ") added to DB");
        } else {
            System.out.println("Element "+googleEntity.getUrl()+" already exists in DB");
        }

    }

    public void addAllElements(ArrayList<GoogleEntity> googleEntities) throws SQLException {
        for (GoogleEntity googleEntity: googleEntities) {
            addElement(googleEntity);
        }

    }

    public ArrayList<GoogleEntity> getAllElements() throws SQLException {
        ArrayList<GoogleEntity> googleEntities = new ArrayList<GoogleEntity>();

        connection = new DBComponent().getConnection();
        statement = connection.createStatement();
        statement.execute(selectDatabase);

        String getAllGoogleEntities = "SELECT * FROM " + tableName;

        ResultSet resultSet = statement.executeQuery(getAllGoogleEntities);

        while (resultSet.next()) {
            GoogleEntity googleEntity = new GoogleEntity();

            long id = resultSet.getLong(idFieldName);
            String url = resultSet.getString(urlFieldName);
            String urlDescription = resultSet.getString(urlDescriptionFieldName);

            googleEntity.setId(id);
            googleEntity.setUrl(url);
            googleEntity.setUrlInfo(urlDescription);

            googleEntities.add(googleEntity);
        }

        if (googleEntities.size() == 0) {
            System.out.println("No data in table googleApi");
        }

        resultSet.close();
        connection.close();

        return googleEntities;
    }

    public GoogleEntity getElementById(long elementId) throws SQLException {
        GoogleEntity googleEntity = new GoogleEntity();

        connection = new DBComponent().getConnection();
        Statement statement = connection.createStatement();
        statement.execute(selectDatabase);

        String getAllGoogleElements = "SELECT * FROM " + tableName +
                " WHERE " + idFieldName + " = " + elementId;

        ResultSet resultSet = statement.executeQuery(getAllGoogleElements);

        while (resultSet.next()) {
            long id = resultSet.getLong(idFieldName);
            String url = resultSet.getString(urlFieldName);
            String urlDescription = resultSet.getString(urlDescriptionFieldName);

            googleEntity.setId(id);
            googleEntity.setUrl(url);
            googleEntity.setUrlInfo(urlDescription);
        }
        resultSet.close();
        connection.close();

        return googleEntity;
    }

    // Using specified table
    public ArrayList<GoogleEntity> getAllElementsFromSpecifiedTable(GoogleOutputLocationDTO googleOutputLocationDTO) throws SQLException {
        ArrayList<GoogleEntity> googleEntities = new ArrayList<GoogleEntity>();

        connection = new DBComponent().getConnection();
        statement = connection.createStatement();
        statement.execute(selectDatabase);

        String specifiedTableName = googleOutputLocationDTO.getTableName();
        String specifiedIdFieldName = googleOutputLocationDTO.getIdFieldName();
        String specifiedUrlFieldName = googleOutputLocationDTO.getUrlFieldName();

        String getAllGoogleEntities = "SELECT * FROM " + specifiedTableName;

        ResultSet resultSet = statement.executeQuery(getAllGoogleEntities);

        while (resultSet.next()) {
            GoogleEntity googleEntity = new GoogleEntity();

            long id = resultSet.getLong(specifiedIdFieldName);
            String url = resultSet.getString(specifiedUrlFieldName);

            googleEntity.setId(id);
            googleEntity.setUrl(url);

            googleEntities.add(googleEntity);
        }

        if (googleEntities.size() == 0) {
            System.out.println("No data in table "+specifiedTableName+" for googleApi.");
        }

        resultSet.close();
        connection.close();

        return googleEntities;
    }

    public void addAllElementsToSpecifiedTable(ArrayList<GoogleEntity> googleEntities, GoogleOutputLocationDTO googleOutputLocationDTO) throws SQLException {
        for (GoogleEntity googleEntity : googleEntities) {
            addElementToSpecifiedTable(googleEntity, googleOutputLocationDTO);
        }
    }
    public void addElementToSpecifiedTable(GoogleEntity googleEntity, GoogleOutputLocationDTO googleOutputLocationDTO) throws SQLException {
        if (googleOutputLocationDTO == null)
            throw new NullPointerException();

        if (isUnique(googleEntity, googleOutputLocationDTO)) {

            String otherTableName = googleOutputLocationDTO.getTableName();
            String otherIdFieldName = googleOutputLocationDTO.getIdFieldName();
            String otherUrlFieldName = googleOutputLocationDTO.getUrlFieldName();

            String insertGoogleElementString = "INSERT INTO " + otherTableName
                    + "(" + otherUrlFieldName + ") VALUES"
                    + "(?)";

            System.out.println("Trying to add google element to db:" + googleEntity.getUrl());
            connection = new DBComponent().getConnection();
            statement = connection.createStatement();
            statement.execute(selectDatabase);

            String autoIncrementSet = "ALTER TABLE " + otherTableName + " MODIFY COLUMN " + otherIdFieldName + " INT auto_increment";
            statement.execute(autoIncrementSet);

            preparedStatement = connection.prepareStatement(insertGoogleElementString, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, googleEntity.getUrl());

            int affectedRows = 0;
            try {
                affectedRows = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Cant add Google element " + googleEntity.getUrl() + " to table " + otherTableName);
            }
            if (affectedRows == 0) {
                throw new SQLException("DB: Creating google element failed, no rows affected.");
            }
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            googleEntity.setId(resultSet.getLong(1));
            System.out.println("Element "+ googleEntity.getUrl() +" successfully added to DB");
        } else {
            System.out.println("Element "+googleEntity.getUrl()+" already exists in DB");
        }
    }

    private boolean isUnique(GoogleEntity checkingGoogleEntity) throws SQLException {
        boolean isUnique = true;

        ArrayList<GoogleEntity> googleEntities = getAllElements();

        for (GoogleEntity existingGoogleEntity: googleEntities) {
            if (existingGoogleEntity.getUrl().equals(checkingGoogleEntity.getUrl()))
                isUnique = false;
        }

        return isUnique;
    }

    private boolean isUnique(GoogleEntity checkingGoogleEntity, GoogleOutputLocationDTO googleOutputLocationDTO) throws SQLException {
        boolean isUnique = true;

        ArrayList<GoogleEntity> googleEntities = getAllElementsFromSpecifiedTable(googleOutputLocationDTO);

        for (GoogleEntity existingGoogleEntity: googleEntities) {
            if (existingGoogleEntity.getUrl().equals(checkingGoogleEntity.getUrl()))
                isUnique = false;
        }

        return isUnique;
    }

}
