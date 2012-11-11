package titleextractor;

import java.text.Normalizer;
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
    //----------This method can also remove begining/ending dashes if parameter 'plus' has true value
    //-----> For unknown reasons, a special unicode char (65279) blocks native str.trim() method, it has been recoded
    public static ArrayList<String> trimPlusPlus(ArrayList<String> list, boolean plus) {
        ArrayList<String> results = new ArrayList<>();
        String temp;
        char[] chars;
        int count;
        boolean redo, found;
        
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
        
        if(plus) {
            redo = false;
            for(int i = 0; i < results.size(); i++) {
                temp = results.get(i);
                found = false;
                if(temp.charAt(0) == '-') {
                    found = true;
                    if(!redo) {
                        redo = true;
                    }
                    temp = temp.substring(1);
                }
                if(temp.charAt(temp.length() - 1) == '-') {
                    found = true;
                    if(!redo) {
                        redo = true;
                    }
                    temp = temp.substring(0, temp.length() - 1);
                }
                if(found) {
                    results.set(i, temp);
                }
            }
            if(redo) {
                trimPlusPlus(results, true);
            }
        }
        
        return results;
    }
    
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------Removes/replaces all diacritics
    public static ArrayList<String> removeDiacritics(ArrayList<String> list) {
        ArrayList<String> results = new ArrayList<>();
        String temp;
        char[] chars;
        for(int i = 0; i < list.size(); i++) {
            temp = list.get(i);
            chars = temp.toCharArray();
            
            for(int j = 0; j < chars.length; j++) {
                char[] c = Normalizer.normalize(String.valueOf(chars[j]), Normalizer.Form.NFD).toCharArray();
                if(c.length > 1) {
                    chars[j] = c[0];
                }
            }
            
            temp = new String(chars);
            results.add(temp);
        }
        
        return results;
    }

}
