package titleextractor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KnownWordsRemover {
    
    //=======================================================================================//
    //                                                                           VARIABLES                                                                                 //
    //=======================================================================================//
    
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------Text Files containing known words to remove
    private static ArrayList<String> commons1;
    private static ArrayList<String> locals1;
                                                    //-----> words that have been recognized as 'ending words' (ex : 'donnie brasco dvdrip fr-dlmania'
                                                    //-----> everything after and including 'dvdrip' can be removed)                                                    
    private static ArrayList<String> commons2;
    private static ArrayList<String> locals2;
                                                    //-----> 'lonely' words (means that these words will only be removed 
                                                    //-----> if they are placed between spaces or any other separator)                                                    
    private static ArrayList<String> commons3;     
    private static ArrayList<String> locals3;     
                                                    //-----> 'special words' that have a special comportement and will be treated with a
                                                    //-----> particular algorithm (ex : 'cd' is often concatenated with a number [1cd, 2cd...])
                                                    //-----> THE CORRESPONDING FILE MUST ONLY CONTAINS REGEXPS   
    
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------    IMPORTANT BEGIN
    // In order to each remover to interact well with others, you should call these in this order :
    // 1. commons1
    // 2. commons2
    // 3. commons3
    //----------    IMPORTANT END
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    
    // This class respects singleton pattern, 'self' is its unique instance
    private static KnownWordsRemover self = null;
    
    //Lists of characters that are considered as separators
    private static char[] separators;
    
    //=======================================================================================//
    //                                                                       CONSTRUCTORS                                                                             //
    //=======================================================================================//
    
    private KnownWordsRemover() {
        loadLibraries();
        
        separators = new char[3];
        separators[0] = ' ';
        separators[1] = '-';
        separators[2] = '/';
    }
    
    public static KnownWordsRemover getInstance() {
        if(self == null) {
            self = new KnownWordsRemover();
        } 
        return self;
    }
    
    
    //=======================================================================================//
    //                                                                              METHODS                                                                                //
    //=======================================================================================//
    
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //---------- The folowing three methods can be called to remove some particular words as explained upper
    
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------
    public static ArrayList<String> removeCommons1(ArrayList<String> list) {
        ArrayList<String> results = new ArrayList<>();
        String temp, comm;
        
        for(int i = 0; i < list.size(); i++) {
            temp = list.get(i);
            for(int j =0; j < commons1.size(); j++) {
                comm = commons1.get(j);
                if(temp.indexOf(comm) > 0) {
                    temp = temp.substring(0, temp.indexOf(comm)); 
                }
            }
            results.add(temp);
        }       
        
        return results;
        
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------
    public static ArrayList<String> removeCommons2(ArrayList<String> list) {
        ArrayList<ArrayList<String>> commons = new ArrayList<>();
        commons.add(commons2);
        commons.add(locals2);
        
        ArrayList<String> results = new ArrayList<>();
        
        String temp, comm, sub1, sub2;
        char[] chars;
        int index;
        boolean found;
        
        //For each title in the list
        for(int i = 0; i < list.size(); i++) {
            temp = list.get(i);
            chars = temp.toCharArray();
            //For each common word in each commons2 libraries
            for(int c = 0; c < commons.size(); c++) {
                for(int j = 0; j < commons.get(c).size(); j++) {
                    comm = commons.get(c).get(j);
                    index = 0;
                    found = false;
                    //If text is found
                    if(temp.indexOf(comm) != -1) {
                        found = true;
                    }
                    //While ocurrences...
                    while(found) {
                        index = temp.indexOf(comm, index);

                        if(index > 0 && index < temp.length() - comm.length()) { //Text is inside
                            if(isSeparator(temp.charAt(index - 1)) && isSeparator(temp.charAt(index + comm.length()))) {
                                sub1 = temp.substring(0, index);
                                sub2 = temp.substring(index + comm.length());
                                temp = sub1 + sub2;
                            }                        
                        } else if(index == 0) { //Text is at begin
                            if(isSeparator(temp.charAt(index + comm.length()))) {
                                temp = temp.substring(index + comm.length());            
                            }
                        } else if(index == temp.length() - comm.length()) { //Text is at end
                            if(isSeparator(temp.charAt(index - 1))) {
                                temp = temp.substring(0, index);
                            }
                        }

                        //If text has next next occurence
                        index = temp.indexOf(comm, index + comm.length());
                        if(index == -1) {
                            found = false;
                        }
                    }
                }
            }
            //Add title to results list, even if it has not been modified
            results.add(temp);
        }
               
        
        
        return results;
        
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------
    public static ArrayList<String> removeCommons3(ArrayList<String> list) {
        ArrayList<String> results = new ArrayList<>();
        String temp;
        
        for(int i = 0; i < list.size(); i++) {
            temp = list.get(i);
            for(int j = 0; j < commons3.size(); j++) {
                String pattern;
                pattern = commons3.get(j);
                temp = temp.replaceAll(pattern, "﻿");
            }
            
            results.add(temp);
        }
        
        
        return results;
        
    }
    
    
    
    
    
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //---------- Loads 'commons' files (if you need to load more files, please change the code to match files count)
    private static void loadLibraries() {
        File[] files = new File[6];
        files[0] = new File("Ressources/commons1.txt");
        files[1] = new File("Ressources/commons2.txt");
        files[2] = new File("Ressources/commons3.txt");
        files[3] = new File("Ressources/LocalCommons1.txt");
        files[4] = new File("Ressources/LocalCommons2.txt");
        files[5] = new File("Ressources/LocalCommons3.txt");
        
        ArrayList<ArrayList<String>> commons = new ArrayList<>();
        
        FileInputStream fis;
        DataInputStream dis;
        InputStreamReader isr;
        BufferedReader br;
        
        for(int i = 0; i < 6; i++) {
            String line;
            ArrayList<String> list = new ArrayList<>();

            try {
                fis = new FileInputStream(files[i]);
                dis = new DataInputStream(fis);
                isr = new InputStreamReader(dis, "UTF-8");
                br = new  BufferedReader(isr);

                //Read file line by line and add string to list
                while((line = br.readLine()) != null) {
                    list.add(line);
                }
            } catch (IOException ex) {
                System.out.println("ERROR reading file : " + ex.getMessage());
            }
            //Add the created list to commons
            commons.add(i, list);
        }
        
        // Define the 'commons' libraries
        commons1 = commons.get(0);
        commons2 = commons.get(1);
        commons3 = commons.get(2);
        locals1 = commons.get(3);
        locals2 = commons.get(4);
        locals3 = commons.get(5);
        
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------Tests if requested char is contained in separators list
    private static boolean isSeparator(char s) {
        for(int i = 0; i < separators.length; i++) {
            if(s == separators[i]) {
                return true;
            }
        }
        return false;
    }
    

}
