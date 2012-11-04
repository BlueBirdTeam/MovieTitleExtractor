package titleextractor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TitleExtractor {
    
    private static File file;                                        //file containing the titles to treat
    private static ArrayList<String> titlesList;            //array containing titles to treat -> will be the final array

    public static void main(String[] args) {
       
        //Change here the file you want to test
        file = new File("TestFiles/test1.txt");
        
        //Extract titles from file and fill the array
        titlesList = getTitlesFromFile(file);
        
        //DO EVERYTHING YOU NEED WITH THE TITLES
        if(titlesList != null) {
            //Remove files extensions
            titlesList = ExtensionsRemover.removeExtensions(titlesList);
            
            //Replace the characters you want by invoking the CharReplacer.replace() method
            titlesList = CharReplacer.replace(titlesList, ".", " ");
            titlesList = CharReplacer.replace(titlesList, "_", " ");

            //Remove brackets and text inside those
            titlesList = BracketsAreSick.removeBracketsAndText(titlesList);
            
            //Remove double/triple spaces
            titlesList = CharReplacer.replace(titlesList, "  ", " ");
            titlesList = CharReplacer.replace(titlesList, "   ", " ");
            
        }
        
        for(int i = 0 ; i < titlesList.size() ; i++) {
            System.out.println(titlesList.get(i));
        }
        
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------Read file and extract titles from it, constructing the array of titles
    public static ArrayList<String> getTitlesFromFile(File file) {
        FileInputStream fis;
        DataInputStream dis;
        InputStreamReader isr;
        BufferedReader br;
        
        String line;
        ArrayList<String> list = new ArrayList<>();
        
        try {
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);
            isr = new InputStreamReader(dis);
            br = new  BufferedReader(isr);
            
            //Read file line by line and add titles to the titles list
            while((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException ex) {
            System.out.println("ERROR : " + ex.getMessage());
            return null;
        }
        
        return list;
    }

}
