package com.softserveinc.DTO;

import java.util.ArrayList;

/**
 * Created by petroborovets on 10/11/14.
 */
public class SpiderResultsDTO {
    private boolean error;
    private String errorDescription;
    private ArrayList<SpiderResultDTO> urlResultList = new ArrayList<SpiderResultDTO>();
    private long numberOfURLsSendToJobolizer;
    private long numberOfURLsSaved;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public ArrayList<SpiderResultDTO> getUrlResultList() {
        return urlResultList;
    }

    public void setUrlResultList(ArrayList<SpiderResultDTO> urlResultList) {
        this.urlResultList = urlResultList;
    }

    public long getNumberOfURLsSendToJobolizer() {
        return numberOfURLsSendToJobolizer;
    }

    public void setNumberOfURLsSendToJobolizer(long numberOfURLsSendToJobolizer) {
        this.numberOfURLsSendToJobolizer = numberOfURLsSendToJobolizer;
    }

    public long getNumberOfURLsSaved() {
        return numberOfURLsSaved;
    }

    public void setNumberOfURLsSaved(long numberOfURLsSaved) {
        this.numberOfURLsSaved = numberOfURLsSaved;
    }
}
