package com.softserveinc.Component.Google;

import com.google.gson.Gson;
import com.softserveinc.Entity.GoogleEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by petroborovets on 10/9/14.
 */
@Component
public class GoogleComponent {
    private String searchString;
    private int numberOfResults;

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public int getNumberOfResults() {
        return numberOfResults;
    }

    public void setNumberOfResults(int numberOfResults) {
        this.numberOfResults = numberOfResults;
    }

    GoogleComponent() {
    }

    public GoogleComponent(String searchString, int numberOfResults) {
        this.searchString = searchString;
        this.numberOfResults = numberOfResults * 4;
    }

    public ArrayList<GoogleEntity> startSearch() throws IOException {
        ArrayList<GoogleEntity> listOfSearchResults = new ArrayList<GoogleEntity>();

        if (searchString.equals("") || searchString == null)
            throw new NullPointerException();
        if (numberOfResults <= 0)
            throw new IOException();

        StartSearchThread searchThread = new StartSearchThread(listOfSearchResults, searchString, numberOfResults);
        searchThread.start();
        try {
            searchThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return listOfSearchResults;
    }

    class StartSearchThread extends Thread {

        ArrayList<GoogleEntity> googleEntities;
        String searchString;
        int numberOfResults;

        StartSearchThread(ArrayList<GoogleEntity> googleEntities, String searchString, int numberOfResults) {
            this.googleEntities = googleEntities;
            this.searchString = searchString;
            this.numberOfResults = numberOfResults;
        }

        @Override
        public void run() {

            for (int i = 0; i < this.numberOfResults; i = i + 4) {
                String google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&start=" + i + "&q=";
                String search = this.searchString;
                String charset = "UTF-8";

                // Getting final URL
                URL url = null;
                try {
                    url = new URL(google + URLEncoder.encode(search, charset));
                } catch (MalformedURLException e) {
                    System.out.println("Thrown to indicate that a malformed URL has occurred. Either no legal protocol could be found in a specification string or the string could not be parsed.");
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    System.out.println("UnsupportedEncoding");
                    e.printStackTrace();
                }

                // Creating reader from URL
                Reader reader = null;
                try {
                    reader = new InputStreamReader(url.openStream(), charset);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Getting results from response
                GoogleResultsComponent results = new Gson().fromJson(reader, GoogleResultsComponent.class);

                // Response contains up to 4 results
                if (results.getResponseData() != null)
                    for (int j = 0; j < results.getResponseData().getResults().size(); j++) {
                        GoogleEntity googleEntity = new GoogleEntity();
                        googleEntity.setUrl(results.getResponseData().getResults().get(j).getUrl());
                        googleEntity.setUrlInfo(results.getResponseData().getResults().get(j).getTitle());
                        // Adding found url
                        googleEntities.add(googleEntity);
                    }
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
