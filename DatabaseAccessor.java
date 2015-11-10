/**
 * DatabaseAccessor.java
 * 
 * @author Sad'e N. Smith
 *
 * Class is that reads from database text files and serializes objects
 * based on information read from the database.
 * 
 * 
 */
package iTracker;

import java.util.*;
import java.awt.Toolkit;
import java.io.*;
import javax.swing.JOptionPane;


public class DatabaseAccessor{

    protected String file_name;
    
    /** 
     * Sole Constructor 
     */
    public DatabaseAccessor(String file_name) {
        this.file_name = file_name;
    }
    
    /** 
     * Check if the specified file is in the current directory; 
     * @return A file upon success, otherwise a NULL
     */
    public File openDatabaseFile(){
        try{
            File file = new File(file_name);
            if(!file.exists()){
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Program could not open database files. Report problem towards smithsade13@gmail.com.");
                return null;
            }
            return file;
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    /**
     * Read through each line of the database file, turning each line into an ArrayList of Strings. 
     * @param An int which is the number of fields in the database file 
     * @return All ArrayLists of Strings contained in a single ArrayList
     */
    public ArrayList<ArrayList<String>> scanDatabaseFile(int numberOfFields){
        
        try{
            ArrayList<String> object_tempList;
            ArrayList<ArrayList<String>> object_list = new ArrayList<ArrayList<String>>();
            Scanner fileScanner = new Scanner(new File(this.file_name));
            
            while (fileScanner.hasNextLine()) {   
                //reading in single line of text in database file
                String line = fileScanner.nextLine();
                
                // Sanitize the text of the line
                if(line.endsWith("/n")){
                    line = line.substring(0,line.length()-2);
                }

                // getting the contents of the line, separated by the delimiter
                String[] arrayOfStringData = line.split("[|]");
                object_tempList = new ArrayList<String>(Arrays.asList(arrayOfStringData));

                //checking for validity of line, adding to object list if valid
                if(!line.startsWith("/n") || !line.startsWith("") || !line.startsWith(" ") ){
                    object_list.add(object_tempList);  
                 }    
            }
            return object_list;
        }
        catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

  
}