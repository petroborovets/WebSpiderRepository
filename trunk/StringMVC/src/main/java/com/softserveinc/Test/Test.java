package com.softserveinc.Test;

import com.softserveinc.Component.Google.GoogleComponent;
import com.softserveinc.Entity.GoogleEntity;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Created by petroborovets on 10/3/14.
 */
public class Test {

    public static void main(String[] args) {

        GoogleComponent googleComponent = new GoogleComponent();
        googleComponent.setSearchString("hello");
        googleComponent.setNumberOfResults(10);
        ArrayList<GoogleEntity> googleEntities = null;
        try {
            googleEntities= googleComponent.startSearch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
