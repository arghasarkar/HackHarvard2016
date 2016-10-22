package ExecutionEngine;

import java.io.File;
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

}
