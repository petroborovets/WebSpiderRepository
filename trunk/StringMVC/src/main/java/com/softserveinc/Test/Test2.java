package com.softserveinc.Test;



import com.softserveinc.Component.JobolizerComponent;
import com.softserveinc.DTO.JobolizerResultDTO;
import com.softserveinc.Entity.JobolizerEntity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by petroborovets on 10/4/14.
 */
public class Test2 {
    public static void main(String[] args) throws IOException {
        JobolizerEntity jobolizerEntity = new JobolizerEntity();

        JobolizerComponent jobolizerComponent = new JobolizerComponent();
        try {
            jobolizerComponent.collectURLData("http://www.xn--jobbrse-d1a.com/jobdetail.php?rid=73615082&qid=36120&fid=6&type=premium&from=Lz9hY3Rpb249c3RlbGxlbmFuZ2Vib3RlLWpvYnMmaj1waHAmcz0mY291bnRyeT0=&m=d41d8cd98f00b204e9800998ecf8427e",0,new JobolizerResultDTO());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();
    }
}
