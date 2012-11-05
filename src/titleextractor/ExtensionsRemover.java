package titleextractor;

import java.util.ArrayList;

public class ExtensionsRemover {
    
    //=======================================================================================//
    //                                                                           VARIABLES                                                                                 //
    //=======================================================================================//
    
    private static ArrayList<String> extensions = new ArrayList<>();
        
    //=======================================================================================//
    //                                                                              METHODS                                                                                //
    //=======================================================================================//
    
    
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //---------- Return complete list of all extensions found
    public static ArrayList<String> getAllExtensions() {
        return extensions;
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------Remove extensions and add those to the extensions list
    public static ArrayList<String> removeExtensions(ArrayList<String> list) {
        char[] chars;
        int index, place;
        String temp, ext;
        ArrayList<String> result = new ArrayList<>();
        
        for(int i = 0 ; i < list.size() ; i++) {
            //Transform string into a char array
            chars = list.get(i).toLowerCase().toCharArray();
            
            //Extract extension
            index = chars.length - 1;
            ext = "";
            while(chars[index] != '.') {
                ext += chars[index];
                index--;
            }
            
            //Add extension to extensions list
            ext = reverseExt(ext).toLowerCase();
            if(extensions.indexOf(ext) == -1) {
                extensions.add(ext);
            }
            
            //Remove extension from string
            temp = list.get(i).toLowerCase();
            temp = temp.substring(0, temp.indexOf(ext) - 1);
            result.add(temp);    
        }
        
        //Return resulted list (without extensions)
        return result;
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------Util method to reverse extracted extension
    private static String reverseExt(String ext) {
        char[] temp = ext.toCharArray();
        ext = "";
        
        for(int i = temp.length - 1 ; i >= 0 ; i--) {
            ext += temp[i];
        }
        
        return ext;
    }

}
