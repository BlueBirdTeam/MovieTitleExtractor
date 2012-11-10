package titleextractor;

import java.util.ArrayList;
import java.util.Arrays;

public class CharsMaster {
    
    //=======================================================================================//
    //                                                                              METHODS                                                                                //
    //=======================================================================================//
    
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------Replace one character by another
    public static ArrayList<String> replace(ArrayList<String> list, String to, String by) {
        ArrayList<String> result = new ArrayList<>();
        String title;
        
        for(int i = 0; i < list.size(); i++) {
            title = list.get(i);
            title = title.replace(to, by);
            result.add(title);
        }
        
        return result;
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------Replace String.class trim() method AND also removes multiple whitespaces
    //-----> For unknown reasons, a special unicode char (65279) blocks native str.trim() method, it has been recoded
    public static ArrayList<String> trimPlusPlus(ArrayList<String> list) {
        ArrayList<String> results = new ArrayList<>();
        String temp;
        char[] chars;
        int count;
        
        for(int i = 0; i < list.size(); i++) {
            chars = list.get(i).toCharArray();
            
            //--------------------> First trim the strings
            // Beginning spaces...
            count = 0;
            for(int j = 0; j < chars.length; j++) {
                if((int)chars[j] == 65279 || chars[j] == ' ') {
                    count++;
                } else {
                    break;
                }
            }
            //Create a subArray
            if(count > 0) {
                chars = Arrays.copyOfRange(chars, count, chars.length);
            }
            
            //Ending spaces...
            count = 0;
            for(int j = chars.length - 1; j >= 0; j--) {
                if((int)chars[j] == 65279 || chars[j] == ' ') {
                    count++;
                } else {
                    break;
                }
            }
             //Create a subArray
            if(count > 0) {
                chars = Arrays.copyOfRange(chars, 0, chars.length - count);
            }
            //Create string from chars
            temp = new String(chars);
            
            
            //-------------->Then remove multiple whitespaces
            temp = temp.replaceAll(" +", " ");       
            
            //-------------->Add the result to results list           
            results.add(temp);
        }
        
        return results;
    }

}
