package com.softserveinc.Test;

import com.softserveinc.Component.DBComponent;
import com.softserveinc.Component.Jobolizer.JobolizerComponent;
import com.softserveinc.DAO.CommonUrlTableDAO;
import com.softserveinc.DTO.SpiderResultDTO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by petroborovets on 9/19/14.
 */
public class JobolizerMain {

    public static void main(String[] args) throws SQLException {

        String tableName = args[0]; // Jobboerse_Anaesthesiologie_stellen_links
        String idFieldName = args[1]; // stellen_links_key
        String urlFieldName = args[2]; // stelle_url
        int from = Integer.parseInt(args[3]); // 530
        int to = Integer.parseInt(args[4]); // 571

        if (args.length>5)
            DBComponent.databaseName = args[5]; // arztdata
        if (args.length>6)
            DBComponent.username = args[6]; // user_arztdata
        if (args.length>7)
            DBComponent.password = args[7]; // user_arztdata83

        int num = 0;

        JobolizerComponent jobolizerComponent = new JobolizerComponent();

        CommonUrlTableDAO commonUrlTableDAO = new CommonUrlTableDAO(tableName, idFieldName, urlFieldName);
        //ArrayList<String> urlsToJobolize = commonUrlTableDAO.getCompanyURLs(from, to);

        ArrayList<String> urlsToJobolize = new ArrayList<String>();
        urlsToJobolize.add("http://www.jobbörse.com/jobdetail.php?rid=73615082&qid=36120&fid=6&type=premium&from=Lz9hY3Rpb249c3RlbGxlbmFuZ2Vib3RlLWpvYnMmaj1waHAmcz0mY291bnRyeT0=&m=d41d8cd98f00b204e9800998ecf8427e");

        for (String url : urlsToJobolize) {
            num++;
            url = url.replaceAll("jobbörse", "xn--jobbrse-d1a");
            try {
                jobolizerComponent.collectURLData(url, num, new SpiderResultDTO());
            } catch (Exception e) {
                System.out.println("Failed");
            }
        }
    }
}
