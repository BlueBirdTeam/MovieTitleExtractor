package titleextractor;

import java.util.ArrayList;

public class BracketsAreSick {
    
    //=======================================================================================//
    //                                                                           VARIABLES                                                                                 //
    //=======================================================================================//
    public enum Brackets {PAR, BRACK}
    private static char open = '(';
    private static char close = ')';
    
    //=======================================================================================//
    //                                                                              METHODS                                                                                //
    //=======================================================================================//
    
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //----------Remove brackets and the text inside
    public static ArrayList<String> remove(ArrayList<String> list, Brackets choice) {
        
        switch (choice) {
            case PAR :
                open = '(';
                close = ')';
                break;
            case BRACK :
                open = '[';
                close = ']';
                break;
            default :
                return null;
        }
        
        ArrayList result = new ArrayList<>();
        char[] chars;
        String temp, rem, sub1, sub2;
        boolean found;
        
        //For each title in the list
        for(int i = 0; i < list.size(); i++) {
            temp = list.get(i).toLowerCase().trim();
            chars = temp.toCharArray();
            //Test each character
            for(int j = 0; j < chars.length; j++) {
                rem = "";
                found = false;
                //If an open bracket is found
                if(chars[j] == open) {
                    //Look for the close bracket
                    for(int k = j; k < chars.length; k++) {
                        rem += chars[k];
                        if(chars[k] == close) {
                            found = true;
                            break;
                        }
                    }
                
                    //If the close backet has been found, then remove the text and the brackets
                    if(found) {
                        sub1 = temp.substring(0, temp.indexOf(rem));
                        sub2 = temp.substring(temp.indexOf(rem) + rem.length());
                        temp = sub1 + " " + sub2;
                    }
                    //Else only remove backets
                    else {
                        temp = temp.replace(open, ' ');
                        temp = temp.replace(close, ' ');
                    }
                }
            }
            //Add the title to the list of results, even if it has not been modified
            result.add(temp);
        }
        
        return result;
    }

}
