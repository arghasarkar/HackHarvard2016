package Downloader;

public class Test_Downloader {

    private static String URL = "http://ef3b0513.ngrok.io/uploads/1477139140.jpg";
    private static String OUTPUT_PATH = System.getProperty("user.dir") + "\\src\\Downloader\\downloads\\1477139140.jpg";

    public static void main(String[] args) {
        try {
            System.out.println(OUTPUT_PATH);
            Downloader.downloadFile(URL, OUTPUT_PATH);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

    }

}
