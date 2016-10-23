package ExecutionEngine;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String pythonPath = "C:\\Users\\argha_sarkar1994\\Documents\\GitHub\\HackHarvard2016\\Python\\";
        String url = new String("http://il3.picdn.net/shutterstock/videos/12627623/thumb/11.jpg");
        try {
            Runtime rt = Runtime.getRuntime();
            //String[] commands = {"C:\\Python27\\python", " " + pythonPath + "\\imagetoimages.py " + url};
            String[] commands = {"C:\\Python27\\python", "C:\\Users\\argha_sarkar1994\\Documents\\GitHub\\HackHarvard2016\\Python\\imagetoimages.py", url};
            Arrays.toString(commands);
            Process proc = rt.exec(commands);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(proc.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            String s = null;
            boolean firstPass = true;
            while ((s = stdInput.readLine()) != null) {
                if (!firstPass) {
                    extractUrl(s);
                }
                firstPass = false;
            }

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }


    }

    private static String extractUrl(String rawUrl) {
        try {
            String decoded = java.net.URLDecoder.decode(rawUrl, "UTF-8");
            decoded = decoded.replace("&p=DevEx,5008.1", "");
            decoded = decoded.substring(0);
            System.out.println(decoded.lastIndexOf("http"));

            decoded = decoded.substring(decoded.lastIndexOf("http"));
            System.out.println(decoded);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return "";
    }
}
