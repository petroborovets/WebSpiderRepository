package com.softserveinc.DTO;

/**
 * Created by petroborovets on 10/14/14.
 */
public class GoogleUrlDTO {
    private long id;
    private String url;
    private String urlInfo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlInfo() {
        return urlInfo;
    }

    public void setUrlInfo(String urlInfo) {
        this.urlInfo = urlInfo;
    }
}
