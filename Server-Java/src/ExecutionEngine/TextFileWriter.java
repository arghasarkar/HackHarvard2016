package ExecutionEngine;

import java.io.*;

/*
 * @author arghasarkar
 */

public class TextFileWriter {

    private String path;                                                        //PATH OF THE FILE TO BE WRITTEN TO
    BufferedWriter writer;                                                      //MAIN WRITER
    boolean connectionInitialised = false;                                      //FLAG TO SEE IF THE CONNECTION HAS BEEN INITIALISED

    public TextFileWriter(String textFilePath) {                                       //CONSTRUCTOR
        path = textFilePath;
    }

    public void writeToFile(String message) {
        try {
            initialiseWriter();
            writer.write(message);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public void writeLineToFile(String message) {
        try {
            initialiseWriter();
            writer.write("\n" + message);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void initialiseWriter() {
        if (connectionInitialised == false) {
            try {
                writer = new BufferedWriter(new FileWriter(path));
                connectionInitialised = true;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }
    }

    public void closeWriter() {
        try {
            connectionInitialised = false;
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

}