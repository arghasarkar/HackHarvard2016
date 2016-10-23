package ExecutionEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Executor {

    private static String UPLOAD_DIRECTORY = "C:\\xampp\\htdocs\\uploads\\";
    private static String WEB_PATH = "http://ef3b0513.ngrok.io/uploads/";

    private static String LAST_FILE_NAME = "";

    public static void main(String[] args) throws Exception {
        String pythonPath = "C:\\Users\\argha_sarkar1994\\Documents\\GitHub\\HackHarvard2016\\Python\\";

        /**
         * Infinite loop.
         * 1) Check for new files that have been uploaded.
         * 2) Get the URL of the new image
         * 3) Execute the python script
         */
        while (true) {
            try {
                String fileName = newFileUploaded();
                if (!fileName.equals("")) {
                    // New file has been uploaded
                    System.out.println("New file: " + fileName);

                    String newWebPath = WEB_PATH + fileName;
                    System.out.println(newWebPath);

                    try {
                        Runtime rt = Runtime.getRuntime();
                        //String[] commands = {"C:\\Python27\\python", " " + pythonPath + "\\imagetoimages.py " + newWebPath};
                        String[] commands = {"C:\\Python27\\python", "C:\\Users\\argha_sarkar1994\\Documents\\GitHub\\HackHarvard2016\\Python\\imagetoimages.py", newWebPath};
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
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }

    }

    private static String newFileUploaded() {
        //define a folder root
        Path myDir = Paths.get(UPLOAD_DIRECTORY);

        try {
            WatchService watcher = myDir.getFileSystem().newWatchService();
            myDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

            WatchKey watckKey = watcher.take();

            List<WatchEvent<?>> events = watckKey.pollEvents();
            for (WatchEvent event : events) {
                if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                    //System.out.println("Created: " + event.context().toString());
                    return event.context().toString();
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
        return "";
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
