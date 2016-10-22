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
        String url = "http://ef3b0513.ngrok.io/uploads/1477160543.jpg";
        try {
            Runtime rt = Runtime.getRuntime();
            //String[] commands = {"C:\\Python27\\python", " " + pythonPath + "\\imagetoimages.py " + url};
            String[] commands = {"C:\\Python27\\python", "C:\\Users\\argha_sarkar1994\\Documents\\GitHub\\HackHarvard2016\\Python\\imagetoimages.py", "http://ef3b0513.ngrok.io/uploads/1477160543.jpg"};
            Arrays.toString(commands);
            Process proc = rt.exec(commands);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(proc.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }


    }
}
