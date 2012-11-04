package titleextractor;

import java.util.ArrayList;

public class BracketsAreSick {
    
    //=======================================================================================//
    //                                                                           VARIABLES                                                                                 //
    //=======================================================================================//
    
    
    
    //=======================================================================================//
    //                                                                              METHODS                                                                                //
    //=======================================================================================//
    
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------Remove brackets and the text inside
    public static ArrayList<String> removeBracketsAndText(ArrayList<String> list) {
        ArrayList result = new ArrayList<>();
        char[] chars;
        String temp, rem, sub1, sub2;
        boolean found;
        
        //For each title in the list
        for(int i = 0; i < list.size(); i++) {
            temp = list.get(i).toLowerCase().trim();
            chars = temp.toCharArray();
            //If an open bracket is found
            for(int j = 0; j < chars.length; j++) {
                rem = "";
                found = false;
                //Look for the close bracket
                if(chars[j] == '(') {                    
                    for(int k = j; k < chars.length; k++) {
                        rem += chars[k];
                        if(chars[k] == ')') {
                            found = true;
                            break;
                        }
                    }
                }
                //If the close backet has been found, then remove the text and the brackets
                if(found) {
                    sub1 = temp.substring(0, temp.indexOf(rem));
                    sub2 = temp.substring(temp.indexOf(rem) + rem.length());
                    temp = sub1 + sub2;
                }
                //Else only remove backets
                else {
                    
                }
            }
            //Add the title to the list of results, even if it has not been modified
            result.add(temp.trim());           
            
        }
        
        return result;
    }

}
