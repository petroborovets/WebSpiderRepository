package com.softserveinc.Test;

import com.softserveinc.Component.Google.GoogleComponent;
import com.softserveinc.Entity.GoogleEntity;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by petroborovets on 10/8/14.
 */
public class GoogleAPITest {
    public static void main(String[] args) throws IOException {
        GoogleComponent googleComponent = new GoogleComponent("привіт", 100);
        ArrayList<GoogleEntity> googleEntities = googleComponent.startSearch();

        System.out.println("Google found:"+googleEntities.size());
        for (GoogleEntity entity : googleEntities) {
            System.out.println(entity.getUrlInfo());
            System.out.println(entity.getUrl());
            System.out.println();
        }
        if (googleEntities.size() == 0)
            System.out.println("Google needs a rest");
    }
}
