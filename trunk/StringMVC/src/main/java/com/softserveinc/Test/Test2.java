package com.softserveinc.Test;



import com.softserveinc.DAO.GoogleDAO;
import com.softserveinc.DTO.GoogleOutputLocationDTO;
import com.softserveinc.Entity.GoogleEntity;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by petroborovets on 10/4/14.
 */
public class Test2 {
    public static void main(String[] args) {

        ArrayList<GoogleEntity> googleEntities = new ArrayList<GoogleEntity>();

        GoogleOutputLocationDTO googleOutputLocationDTO = new GoogleOutputLocationDTO();
        googleOutputLocationDTO.setIdFieldName("stellen_links_key");
        googleOutputLocationDTO.setTableName("Jobboerse_Anaesthesiologie_stellen_links");
        googleOutputLocationDTO.setUrlFieldName("stelle_url");

        GoogleDAO googleDAO = new GoogleDAO();

        try {
            googleEntities = googleDAO.getAllElementsFromSpecifiedTable(googleOutputLocationDTO);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
