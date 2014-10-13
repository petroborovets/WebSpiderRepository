package com.softserveinc.Component;

import org.springframework.stereotype.Component;

/**
 * Created by petroborovets on 10/9/14.
 */
public class DatabaseSelector {
    long id;
    String databaseName;
    String databaseUrl;
    String username;
    String password;
    String tableName;
    String idFieldName;
    String urlFieldName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIdFieldName() {
        return idFieldName;
    }

    public void setIdFieldName(String idFieldName) {
        this.idFieldName = idFieldName;
    }

    public String getUrlFieldName() {
        return urlFieldName;
    }

    public void setUrlFieldName(String urlFieldName) {
        this.urlFieldName = urlFieldName;
    }
}
