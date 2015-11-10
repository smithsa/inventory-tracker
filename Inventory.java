/**
 * Inventory.java
 * 
 * @author Sad'e N. Smith
 *
 * Class is representative of an inventory object. Contains all inventory information each
 * item would have (inventory_item_name, inventory_price, inventory_count).
 *
 */
package iTracker;

 public class Inventory{
    private String inventory_item_name;
    private String inventory_price;
    private String inventory_count;

  	/**
  	 *  Sole Constructor
  	 *  @param The current name, price, and count of the inventory item 
  	 */
    public Inventory(String input_item, String input_price, String input_count) {
        this.inventory_item_name = input_item;
        this.inventory_price = input_price;
        this.inventory_count = input_count;
    }

    /**
     *  Gets the inventory item's name
     */
    public String getInventoryItem() {
        return inventory_item_name;
    }

    /**
     *  Sets the inventory item's name
     *  @param The new name for the inventory item
     */
    public void setIventoryItem(String new_item) {
        this.inventory_item_name = new_item;
    }

    /** 
     * Gets the inventory item's price 
     * @return The current price of the inventory item
     */
    public String getInventoryPrice() {
        return this.inventory_price;
    }

    /**
     *  Sets the inventory item's price
     *  @param The new price for the inventory item
     */
    public void setIventoryPrice(String new_price) {
        this.inventory_price = new_price;
    }
    
    /** 
     *  Gets the inventory item's count
     *  @return The current count of the inventory item
     */
    public String getInventoryCount() {
        return this.inventory_count;
    }
    
    /**  
     * Sets the inventory item's count
     * @param The new count for the inventory item
     */
    public void setIventoryCount(String new_count) {
        this.inventory_count = new_count;
    }
}