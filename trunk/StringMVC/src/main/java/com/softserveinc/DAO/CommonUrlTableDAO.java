package com.softserveinc.DAO;

import com.softserveinc.Component.DBComponent;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by petroborovets on 9/20/14.
 */
public class CommonUrlTableDAO {

    // Table and fields
    private String urlTableName;
    private String idFieldName;
    private String urlFieldName;
    // Connection
    private Connection connection;
    // SQL
    private String selectDatabaseString = "use " + DBComponent.databaseName + ";";

    public CommonUrlTableDAO(String tableName, String idFieldName, String urlFieldName) {
        this.urlTableName = tableName;
        this.idFieldName = idFieldName;
        this.urlFieldName = urlFieldName;
    }

    public ArrayList<String> getCompanyURLs(long from, long to) throws SQLException {
        //HashSet<String> urlSet = new HashSet<String>();
        //OLEG change
        ArrayList<String> urlList=new ArrayList<String>();
        String getUrlsFromTable = "SELECT " + urlFieldName + " FROM " + urlTableName
                + " WHERE " + idFieldName + ">=" + from + " AND "
                + idFieldName + "<" + to;
        System.out.println("<- Getting url strings from table " + urlTableName);
        connection = new DBComponent().getConnection();

        Statement statement = connection.createStatement();
        statement.execute(selectDatabaseString);

        ResultSet resultSet = statement.executeQuery(getUrlsFromTable);

        while (resultSet.next()) {
            String url = resultSet.getString(urlFieldName);
            //urlSet.add(url);
            urlList.add(url);
        }

        resultSet.close();
        connection.close();

        //return urlSet;
        return urlList;
    }

}
