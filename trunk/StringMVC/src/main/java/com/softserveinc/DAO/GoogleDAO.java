package com.softserveinc.DAO;

import com.softserveinc.Component.DBComponent;
import com.softserveinc.DTO.GoogleOutputLocationDTO;
import com.softserveinc.Entity.GoogleEntity;

import java.sql.*;

/**
 * Created by petroborovets on 10/13/14.
 */
public class GoogleDAO {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private GoogleOutputLocationDTO googleOutputLocationDTO;
    private String selectDatabase = DBComponent.selectDatabase;

    public GoogleDAO() {

    }

    GoogleDAO(GoogleOutputLocationDTO googleOutputLocationDTO) {
        this.googleOutputLocationDTO = googleOutputLocationDTO;
    }

    public void addElement(GoogleEntity googleEntity) throws NullPointerException, SQLException {
        if (this.googleOutputLocationDTO == null)
            throw new NullPointerException();

        String insertGoogleElementString = "INSERT INTO " + googleOutputLocationDTO.getTableName() +
                "(" + googleOutputLocationDTO.getUrlFieldName() + ") VALUES (?) ";

        System.out.println("Trying to add google element to db:" + googleEntity.getUrl());
        connection = new DBComponent().getConnection();
        statement = connection.createStatement();
        statement.execute(selectDatabase);

        preparedStatement = connection.prepareStatement(insertGoogleElementString, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, googleEntity.getUrl());
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

    }
}
