/**
 * IventoryDatabaseAccessor.java
 * 
 * @author Sad'e N. Smith
 *
 * Class is that writes and reads to the Database text files.
 * Also has some helpful functions that helps manage the inventory, for example
 * filtered searches. In addition, it is a subclass of DatabaseAccessor.java.
 * 
 * 
 */
package iTracker;

import java.util.*;
import java.io.*;

import javax.swing.JOptionPane;
import java.awt.Toolkit;

public class InventoryDatabaseAccessor extends DatabaseAccessor{
    private String inventory_file_name;
    private ArrayList<Inventory> inventory;
    /**
     *  Sole Constructor
     */
    public InventoryDatabaseAccessor(String file_name){
        super(file_name);
        this.inventory_file_name = file_name;
        this.inventory = null;
    }

    /**
     * Instantiate Inventory Objects
     * @param Takes in an arraylist of string arraylists
     * @return An arraylist of Inventory Objects
     */
    public ArrayList<Inventory> serializeObjects(ArrayList<ArrayList<String>> inventory_list_as_string) {
        String item;
        String price;
        String count;
        ArrayList<Inventory> inventory_list = new ArrayList<Inventory>();
        for(int i = 0; i < inventory_list_as_string.size(); i++){
            item  = inventory_list_as_string.get(i).get(0);
            price = inventory_list_as_string.get(i).get(1);
            count = inventory_list_as_string.get(i).get(2);
            Inventory inventory_object = new Inventory(item, price, count);
            inventory_list.add(inventory_object);
        }

        return inventory_list;
    }

     /**
     * Writes Inventory Objects contained in an input ArrayList to the backup file.
     * @param ArrayList of Inventory Object
     * @return True upon successful write, False otherwise
     */
    public boolean writeToFile(ArrayList<Inventory> inventory_list){
        try {
        	FileWriter fstream = new FileWriter(this.inventory_file_name, false);
            BufferedWriter out = new BufferedWriter(fstream);
                        
            String text_to_write = "";
            for(int i = 0; i < inventory_list.size() ; i++) {
                Inventory cur_inventory_object = inventory_list.get(i);
                if(i == inventory_list.size()-1){
                    text_to_write += cur_inventory_object.getInventoryItem() + "|";
                    text_to_write += cur_inventory_object.getInventoryPrice() + "|";
                    text_to_write += cur_inventory_object.getInventoryCount() ;
                }else{
                    text_to_write += cur_inventory_object.getInventoryItem() + "|";
                    text_to_write += cur_inventory_object.getInventoryPrice() + "|";
                    text_to_write += cur_inventory_object.getInventoryCount() + "\n";
                }
                
            }
            
            out.flush();
            out.write(text_to_write);
            out.close();
            return true;

        } catch(IOException e) {
        	e.printStackTrace();
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Program filed to write to database. Report problem to smithsade13@gmail.com.");
            return false;
        } 

    }
    
    /**
     *  Gets a list of the inventory
     *  @return An ArrayList<Inventory> of all inventory items
     */
    public ArrayList<Inventory> getInventory(){
    	return this.inventory;
    }
    
    /**
     *  Sets a new inventory list
     *  @param An ArrayList<Inventory> of the new inventory list
     */
    public void setInventory(ArrayList<Inventory> newInventory){
    	this.inventory = newInventory;
    }
    
    /**
    *  Gets an array of all the inventory item options as well as "Other" option and "Select an option"
    *  @param ArrayList of Inventory Objects
    *  @return A string array (String[]) with the names of all the inventory items item options as well as "Other" option and "Select an option"
    */
    public String[] getInventoryItemList(ArrayList<Inventory> list){
    	int list_size = list.size()+2;
		String[] returnList =  new String[list_size];
		returnList[0] = "Select an option";
    	for(int i = 1; i < list_size-1; i++){
    		returnList[i] = list.get(i-1).getInventoryItem();
    	}
    	returnList[list_size-1] = "Other";
    	return returnList;
    }
    
    /**
     * Gets an array of all the inventory item options
     * @param ArrayList of Inventory Objects
     * @return A string array (String[]) with the names of all the inventory item options
     */
    public String[] getOnlyInventoryItemList(ArrayList<Inventory> list){
    	int list_size = list.size();
		String[] returnList =  new String[list_size];
    	for(int i = 0; i < list_size; i++){
    		returnList[i] = list.get(i).getInventoryItem();
    	}
    	return returnList;
    }
    
    /**
    *  Searches the inventory by the inventory items name
    *  @param A string that is the inventory item's name and an ArrayList of Inventory Object
    *  @return If the item is found it return that inventory item object, otherwise NULL
    */
    public Inventory searchInventoryListByItem(String item, ArrayList<Inventory> list ){
    	
    	for(int i = 0; i < list.size(); i++){
    		if(list.get(i).getInventoryItem().equals(item)){
    			return list.get(i);
    		}
    	}
    	return null;
    }
}