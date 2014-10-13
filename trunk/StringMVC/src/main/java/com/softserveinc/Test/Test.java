package com.company.startPoint;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.net.SocketException;

/**
 * Created by petroborovets on 10/3/14.
 */
public class Test {

    public static void main(String[] args) {

        String server = "euve33184.vserver.de";
        int port = 22;
        String user = "root";
        String pass = "yuti35_2B";

        ///home/programming_projects/Jobolizer_jar/SavedHtml

        // get an ftpClient object
        FTPClient ftpClient = new FTPClient();

        FileInputStream inputStream = null;

        try {
            // pass directory path on server to connect
            ftpClient.connect(server,port);

            // pass username and password, returned true if authentication is
            // successful
            boolean login = ftpClient.login(user, pass);

            if (login) {
                System.out.println("Connection established...");
                File file = new File("someFile.html");
                createFileFromString(file, "SomeString");

                inputStream = new FileInputStream(file);

                boolean uploaded = ftpClient.storeFile("uploadedFile.txt",
                        inputStream);

                if (uploaded) {
                    System.out.println("File uploaded successfully !");
                } else {
                    System.out.println("Error in uploading file !");
                }

                // logout the user, returned true if logout successfully
                boolean logout = ftpClient.logout();
                if (logout) {
                    System.out.println("Connection close...");
                }
            } else {
                System.out.println("Connection fail...");
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createFileFromString(File file, String htmlString) {

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(htmlString);

        } catch (IOException e) {
            System.out.println("Problem making file from html");
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
            }
        }
    }
}
