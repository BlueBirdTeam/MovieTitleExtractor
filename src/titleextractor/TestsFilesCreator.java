package titleextractor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class TestsFilesCreator {
    
    public static void createFiles(ArrayList<String> titlesList) {
        //Remove '
        titlesList = CharsMaster.replace(titlesList, "'", " ");
            
        //Add .AVI extension
        String temp;
        for(int i = 0; i < titlesList.size(); i++) {
            temp = titlesList.get(i);
            titlesList.set(i, temp + ".avi");
        }
        
        //Create files
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        for(int i = 0; i < titlesList.size(); i++) {
            try {
                fos = new FileOutputStream(new File("Movies/" + titlesList.get(i)));
                osw = new OutputStreamWriter(fos, "UTF-8");
                bw = new BufferedWriter(osw);
            } catch(FileNotFoundException | UnsupportedEncodingException e) {
                System.out.println("ERROR creating file : " + e.getMessage());
            } finally {
                try {                
                    bw.close();
                    osw.close();
                    fos.close();
                } catch (IOException ex) {
                    System.out.println("ERROR while closing streams : " + ex.getMessage());
                }
            }
        }
    }

}
