package titleextractor;

import java.util.ArrayList;

public class CharReplacer {
    
    //=======================================================================================//
    //                                                                           VARIABLES                                                                                 //
    //=======================================================================================//
    
    
    
    //=======================================================================================//
    //                                                                              METHODS                                                                                //
    //=======================================================================================//
    
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

}
