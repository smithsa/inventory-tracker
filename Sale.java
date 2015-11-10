/**
 * Sale.java
 * 
 * @author Sad'e N. Smith
 *
 * Class is representative of a sale. Contains all sale information each
 * sale made would have (sale_item_name, quantity_sold, customer_charge_number, and date_of_sale ).
 *
 */
package iTracker;

public class Sale{
    private String sale_item_name;
    private String quantity_sold;
    private String customer_charge_number;
    private String date_of_sale;
    private String total_price;
	private String sale_id;

  	/** 
  	 * Sole Constructor
  	 * @param The name of the item being sold, the quantity being sold, the charge number, and date 
  	 */
    public Sale(String input_item, String input_quanity_sold, String input_charge_number, String input_date, String input_total_price, String input_id) {
    	this.sale_item_name 		   = input_item;
    	this.quantity_sold  		   = input_quanity_sold;
    	this.customer_charge_number    = input_charge_number;
    	this.date_of_sale 		   	   = input_date;
    	this.total_price			   = input_total_price;
    	this.sale_id				   = input_id;
    	
    }


    /** 
     * Gets the sale item's name 
     * @return The name of the sale item
     */
    public String getSaleItem() {
        return this.sale_item_name;
    }

    /**
     *  Sets the sale item's name
     *  @param The updated name of item sold
     */
    public void setIventoryItem(String new_sale_item) {
        this.sale_item_name = new_sale_item;
    }
    
    /** 
     * Gets the sale item's quantity sold 
     *  @return The quantity being sold of the sale item
     */
    public String getSaleQuanity() {
        return this.quantity_sold;
    }
    
    /**
     *  Sets the sale item's quantity sold
     *  @param The updated quantity
     */
    public void setSaleQuanity(String quanity) {
        quantity_sold = quanity;
    }
    
    /**
     *  Sets the sale's charge number
     *  @param The updated charge number
     */
    public void setChargeNumber(String charge_number) {
    	this.customer_charge_number = charge_number;
    }
    
    /** Gets the sale total price
     *  @return The total price for the sale
     */
    public String getChargeNumber() {
        return this.customer_charge_number;
    }
    
    /**
     *  Sets the sale total price
     *  @param The updated total price
     */
    public void setTotalPrice(String price) {
    	this.total_price = price;
    }
    
    public String getTotalPrice() {
        return this.total_price;
    }
  
    
    /**
     *  Sets the sale id
     *  @param The id that is being set
     */
    public void setID(String id) {
    	this.sale_id = id;
    }
    
    public String getID() {
        return this.sale_id;
    }
    
    /** 
     * Gets the sale's id
     * @return The id of of sale
     */

    public String getDateOfSale() {
        return this.date_of_sale;
    }
}