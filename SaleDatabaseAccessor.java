/**
 * SalesDatabaseAccessor.java
 * 
 * @author Sad'e N. Smith
 *
 * Class is that writes and reads to the Database text files.
 * Also has some helpful functions that helps manage the sales made, for example
 * filtered searches. In addition, it is a subclass of DatabaseAccessor.java.
 * 
 * 
 */

package iTracker;

import java.util.*;
import java.io.*;
import java.awt.Toolkit;
import javax.swing.JOptionPane;

public class SaleDatabaseAccessor extends DatabaseAccessor{
    private String sale_file_name;
    private ArrayList<Sale> sales;

    /**
     * Sole Constructor 
     */
    public SaleDatabaseAccessor(String file_name){
        super(file_name);
        this.sale_file_name = file_name;
        this.sales = null;
    }

    /**
     * Instantiate Sale Objects
     * @param Takes in an arraylist of string arraylists
     * @return An arraylist of Sale Objects
     */
    public ArrayList<Sale> serializeObjects(ArrayList<ArrayList<String>> sale_list_as_string) {
        String item;
        String quanity;
        String charge_number;
        String date;
        String total_price;
        String id;
        ArrayList<Sale> sale_list = new ArrayList<Sale>();
        for(int i = 0; i < sale_list_as_string.size(); i++){
            item           = sale_list_as_string.get(i).get(0);
            quanity        = sale_list_as_string.get(i).get(1);
            charge_number  = sale_list_as_string.get(i).get(2);
            date           = sale_list_as_string.get(i).get(3);
            total_price    = sale_list_as_string.get(i).get(4);
            id 			   = sale_list_as_string.get(i).get(5);
            Sale sale_object = new Sale(item, quanity, charge_number, date, total_price, id);
            sale_list.add(sale_object);
        }

        return sale_list;
    }

     /**
      *  Writes Sale Objects contained in an input ArrayList to the backup file.
      *  @param ArrayList of Sale Object
      *  @return True upon successful write, False otherwise
      */
    public boolean writeToFile(ArrayList<Sale> sale_list){
        try {
        
            FileWriter fstream = new FileWriter(sale_file_name, false);
            BufferedWriter out = new BufferedWriter(fstream);
            
            String text_to_write = "";
            for(int i = 0; i < sale_list.size() ; i++) {
                Sale cur_sale_object = sale_list.get(i);
                if(i == sale_list.size()-1){
                    text_to_write += cur_sale_object.getSaleItem() + "|";
                    text_to_write += cur_sale_object.getSaleQuanity() + "|";
                    text_to_write += cur_sale_object.getChargeNumber() + "|";
                    text_to_write += cur_sale_object.getDateOfSale()+ "|";
                    text_to_write += cur_sale_object.getTotalPrice()+ "|"; 
                    text_to_write += cur_sale_object.getID();
                }
                else{
                    text_to_write += cur_sale_object.getSaleItem() + "|";
                    text_to_write += cur_sale_object.getSaleQuanity() + "|";
                    text_to_write += cur_sale_object.getChargeNumber() + "|";
                    text_to_write += cur_sale_object.getDateOfSale() + "|";
                    text_to_write += cur_sale_object.getTotalPrice() + "|";
                    text_to_write += cur_sale_object.getID() + "\n";
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
     *  Gets a list of the sales
     *  @return An ArrayList<Sales> of all sale items
     */
    public ArrayList<Sale> getSales(){
    	return this.sales;
    }
    
    /**
     *  Sets a new inventory list
     *  @param An ArrayList<Sale> of the new inventory list
     */
    public void setSales(ArrayList<Sale> newSales){
    	this.sales = newSales;
    }
}