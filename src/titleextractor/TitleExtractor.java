package titleextractor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
            
            //Replace the characters you want by invoking the CharsMaster.replace() method
            titlesList = CharsMaster.replace(titlesList, ".", " ");
            titlesList = CharsMaster.replace(titlesList, "_", " ");
            
            //Remove diacritics
            titlesList = CharsMaster.removeDiacritics(titlesList);

            //Remove brackets/parenthesis/dashes and text inside those
            titlesList = BracketsAreSick.remove(titlesList, BracketsAreSick.Brackets.PAR);
            titlesList = BracketsAreSick.remove(titlesList, BracketsAreSick.Brackets.BRACK);                      
            
            //Get KnownWordsRemover instance (load libraries)
            KnownWordsRemover.getInstance();
            
            //Remove commons1
            titlesList = CharsMaster.trimPlusPlus(titlesList, false);
            titlesList = KnownWordsRemover.removeCommons1(titlesList);
            
            //Remove dashes and text inside those
            titlesList = BracketsAreSick.removeDashes(titlesList);
            
            //Remove commons2
            titlesList = CharsMaster.trimPlusPlus(titlesList, false);
            titlesList = KnownWordsRemover.removeCommons2(titlesList);
            
            //Remove commons3
            titlesList = CharsMaster.trimPlusPlus(titlesList, false);
            titlesList = KnownWordsRemover.removeCommons3(titlesList);           
            
            //Trim and remove double/triple... spaces to get proper final results
            titlesList = CharsMaster.trimPlusPlus(titlesList, true);
            
        }
        
        //Write results in a text file
        writeResults(titlesList);
        
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------Reads file and extract titles from it, constructing the array of titles
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
            isr = new InputStreamReader(dis, "UTF-8");
            br = new  BufferedReader(isr);
            
            //Read file line by line and add titles to the titles list
            while((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException ex) {
            System.out.println("ERROR reading file : " + ex.getMessage());
            return null;
        }
        
        return list;
    }
    
    
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------Writes a text file containing the results
    public static void writeResults(ArrayList<String> list) {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        
        try {
            fos = new FileOutputStream(new File("TestFiles/Result1.txt"));
            osw = new OutputStreamWriter(fos, "UTF-8");
            bw = new BufferedWriter(osw);
            for(int i = 0; i < list.size(); i++) {
                //System.out.println("Writing ---> " + list.get(i));
                bw.write(list.get(i) + "\r\n");
            }            
        } catch(Exception e) {
            System.out.println("ERROR writing file : " + e.getMessage());
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
