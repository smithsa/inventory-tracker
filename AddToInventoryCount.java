/**
 * AddToInventoryCount.java
 * 
 * @author Sad'e N. Smith
 *
 * A JDialog that allows a user to change the count of a Inventory object in a inventory database.
 *
 */

package iTracker;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddToInventoryCount extends JDialog {

	private static final long serialVersionUID = 1L;
	private JComboBox<String> inventory_item_combobox;
	private JTextField add_textfield;
	private JRadioButton postage_radio_button;
	private JRadioButton stock_radio_button;
	private JButton add_to_count_button;
	private JButton cancel_button;
	
	/**
	 * Create the dialog.
	 * @param Inventory Database for postage inventory
	 * @param Inventory  Database for stock inventory
	 */
	public AddToInventoryCount(final InventoryDatabaseAccessor postage_db, final InventoryDatabaseAccessor stock_db) {
		setResizable(false);
		setTitle("Add To Inventory Count");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AddToInventoryCount.class.getResource("/iTracker/logo.png")));
		setBounds(100, 100, 329, 232);
		getContentPane().setLayout(new BorderLayout());
		{
			/*The Panel*/
			JPanel panel = new JPanel();
			panel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(panel, BorderLayout.CENTER);
						
			/*The Combobox/Dropdown of Inventory Items*/
			JLabel inventory_item_label = new JLabel("Inventory Item");
			inventory_item_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			inventory_item_combobox = new JComboBox<String>();
			inventory_item_combobox.setFont(new Font("Tahoma", Font.PLAIN, 11));
			
			/*Postage Radio Button*/
			postage_radio_button = new JRadioButton("Postage");
			postage_radio_button.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					if(e.getKeyChar() == KeyEvent.VK_ENTER){
						postage_radio_button.setSelected(true);
						String[] new_list = postage_db.getOnlyInventoryItemList(postage_db.getInventory());
						inventory_item_combobox.setModel(new DefaultComboBoxModel<String>(new_list));
						
						stock_radio_button.requestFocus();
					}
				}
			});
			postage_radio_button.setSelected(true);
			postage_radio_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String[] new_list = postage_db.getOnlyInventoryItemList(postage_db.getInventory());
					inventory_item_combobox.setModel(new DefaultComboBoxModel<String>(new_list));
				}
			});
			postage_radio_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			/*Stock Radio Button*/
			stock_radio_button = new JRadioButton("Stock");
			stock_radio_button.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					if(e.getKeyChar() == KeyEvent.VK_ENTER){
						stock_radio_button.setSelected(true);
						String[] new_list = stock_db.getOnlyInventoryItemList(stock_db.getInventory());
						inventory_item_combobox.setModel(new DefaultComboBoxModel<String>(new_list));
						add_textfield.requestFocus();
					}
				}
			});
			stock_radio_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String[] new_list = stock_db.getOnlyInventoryItemList(stock_db.getInventory());
					inventory_item_combobox.setModel(new DefaultComboBoxModel<String>(new_list));
				}
			});
			stock_radio_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			/*Button Group for Stock Radio Button and Postage Radio Button*/
			ButtonGroup button_group= new ButtonGroup();
			button_group.add(postage_radio_button);
			button_group.add(stock_radio_button);
			
			/*A Check to determine what inventory items (postage or stock) to show on the Combobox*/
			if(postage_radio_button.isSelected()){
				String[] new_list = postage_db.getOnlyInventoryItemList(postage_db.getInventory());
				inventory_item_combobox.setModel(new DefaultComboBoxModel<String>(new_list));
			}else{
				String[] new_list = stock_db.getOnlyInventoryItemList(stock_db.getInventory());
				inventory_item_combobox.setModel(new DefaultComboBoxModel<String>(new_list));
			}
		
			/*Add TextField*/
			JLabel add_item = new JLabel("Add");
			add_item.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			add_textfield = new JTextField();
			add_textfield.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					
					char c = e.getKeyChar();
					if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) || add_textfield.getText().length() > 2) { //limiting length of input
				         e.consume();  
				    }
					if(e.getKeyChar() == KeyEvent.VK_ENTER){
						add_to_count_button.requestFocus();
					}
				}
			});
			add_textfield.setColumns(10);
			
			/*Add To Count Button*/
			add_to_count_button = new JButton("Add To Count");
			add_to_count_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(postage_radio_button.isSelected()){
						writeChange(postage_db);
					}else{
						writeChange(stock_db);
					}
					
				}
			});
			add_to_count_button.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					if(e.getKeyChar() == KeyEvent.VK_ENTER){
						if(postage_radio_button.isSelected()){
							writeChange(postage_db);
						}else{
							writeChange(stock_db);
						}
					}
				}
			});
			add_to_count_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			/*Cancel Button*/
			cancel_button = new JButton("Cancel");
			cancel_button.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					if(e.getKeyChar() == KeyEvent.VK_ENTER){
						dispose();
					}
				}
			});
			cancel_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			cancel_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
						
			/*Layout*/
			GroupLayout gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(32)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel.createSequentialGroup()
										.addComponent(stock_radio_button, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
										.addGap(19)
										.addComponent(add_item)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(add_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addComponent(postage_radio_button, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
									.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
										.addComponent(inventory_item_label)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(inventory_item_combobox, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
										.addGap(23))))
							.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
								.addGap(54)
								.addComponent(add_to_count_button)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(cancel_button)))
						.addGap(107))
			);
			gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(31)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(inventory_item_label)
							.addComponent(inventory_item_combobox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(10)
						.addComponent(postage_radio_button)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(stock_radio_button)
							.addComponent(add_item)
							.addComponent(add_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(34)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(add_to_count_button)
							.addComponent(cancel_button))
						.addGap(35))
			);
			panel.setLayout(gl_panel);
		}
	}

	/**
	 * Writes the change of the inventory object's count to the given database
	 * @param an inventory database 
	 * @return True if the write is successful, False otherwise
	 */
	public boolean writeChange(InventoryDatabaseAccessor db){
		if( inventory_item_combobox.getSelectedItem() == null ||  ((String)inventory_item_combobox.getSelectedItem()).equals("")){
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null,"Select an inventory item.");
			return false;
		}
		if(add_textfield.getText().equals("")){
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null,"Provide a valid number to add.");
			return false;
		}
		Inventory item = db.searchInventoryListByItem((String) inventory_item_combobox.getSelectedItem(), db.getInventory());
		Integer new_count = Integer.parseInt(item.getInventoryCount()) + Integer.parseInt(add_textfield.getText()) ;
		item.setIventoryCount(Integer.toString(new_count));
		db.getInventory().remove(inventory_item_combobox.getSelectedIndex());
		db.getInventory().add(inventory_item_combobox.getSelectedIndex(), item);
		
		db.writeToFile(db.getInventory());
		dispose();
		return true;
	}
}
