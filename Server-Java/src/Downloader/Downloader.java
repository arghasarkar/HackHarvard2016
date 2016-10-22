package Downloader;

import java.io.*;

import java.net.URL;

public class Downloader {

    private String URL = "";
    private String OUTPUT_PATH = "";

    public Downloader(String url, String outputPath) {
        this.URL = url;
        this.OUTPUT_PATH = outputPath;
    }

    public boolean download() {

        try {
            System.out.println("Downloading File From: " + URL);

            URL url = new URL(URL);
            InputStream inputStream = url.openStream();
            OutputStream outputStream = new FileOutputStream(OUTPUT_PATH);
            byte[] buffer = new byte[2048];

            int length = 0;

            while ((length = inputStream.read(buffer)) != -1) {
                System.out.println("Buffer Read of length: " + length);
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return false;
    }

    public static boolean downloadFile(String URL, String OUTPUT_PATH) throws Exception {
        return new Downloader(URL, OUTPUT_PATH).download();
    }
}