/**
 * EditInventoryItem.java
 * 
 * @author Sad'e N. Smith
 *
 * A JDialog that allows a user to edit a Inventory object in a inventory database.
 *
 */

package iTracker;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class EditInventoryItem extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField price_textfield;
	private JTextField name_textfield;
	private JTextField count_textfield;
	private JButton edit_item_button;
	private Inventory selected_item;
	private int index;
	
	/**
	 * Create the dialog.
	 * @param Inventory Database for postage inventory
	 * @param Inventory  Database for stock inventory
	 */
	public EditInventoryItem(final InventoryDatabaseAccessor db, int item_index) {
		index = item_index;
		setResizable(false);
		setTitle("Edit Inventory Item");
		setIconImage(Toolkit.getDefaultToolkit().getImage(EditInventoryItem.class.getResource("/iTracker/logo.png")));
		setBounds(100, 100, 283, 252);
		selected_item = db.getInventory().get(item_index);
	
		
		getContentPane().setLayout(new BorderLayout());
		{
			/*The Panel*/
			JPanel panel = new JPanel();
			panel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(panel, BorderLayout.WEST);
			
			/*Edit Item Button*/
			edit_item_button = new JButton("Edit Item");
			edit_item_button.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					if(e.getKeyChar() == KeyEvent.VK_ENTER ){
						writeInventoryAddModification(db);
					}
				}
			});
			edit_item_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					writeInventoryAddModification(db);
				}
			});
			edit_item_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			/*Price TextField*/
			JLabel price_label = new JLabel("Item Price");
			price_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			price_textfield = new JTextField();
			price_textfield.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					price_textfield.selectAll();
				}
			});
			price_textfield.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					char c = e.getKeyChar();
					if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) &&  (c != KeyEvent.VK_PERIOD) 
							|| price_textfield.getText().length() > 6
							|| (price_textfield.getText().contains(".") && (!price_textfield.getText().equals("0.00"))
								&& (price_textfield.getText().substring(price_textfield.getText().indexOf(".")).length() > 2
									))){ 
				         e.consume();  
				      }
					if(price_textfield.getText().contains(".") && c == KeyEvent.VK_PERIOD 
							&& !price_textfield.getText().equals("0.00")){
						e.consume();
					}
					if(c == KeyEvent.VK_ENTER){
						count_textfield.requestFocus();
					}

				}
			});
			price_textfield.setColumns(10);
			
			/*Name TextField*/
			JLabel name_label = new JLabel("Item Name");
			name_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			name_textfield = new JTextField();
			name_textfield.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					name_textfield.selectAll();
				}
			});
			name_textfield.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent arg0) {
					if(name_textfield.getText().length() > 16 || arg0.getKeyChar() == ':' 
							|| arg0.getKeyChar() == '"' 
							||arg0.getKeyChar() == '\'' || arg0.getKeyChar() == '|'){
						arg0.consume();
					} 
					
					if(arg0.getKeyChar() == KeyEvent.VK_ENTER){
						price_textfield.requestFocus();
					}
				}
			});
			name_textfield.setColumns(10);
			
			/*Item Count TextField*/
			JLabel count_label = new JLabel("Item Count");
			count_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			count_textfield = new JTextField();
			count_textfield.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					count_textfield.selectAll();
				}
			});
			count_textfield.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent arg0) {
					char c = arg0.getKeyChar();
					if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) || count_textfield.getText().length() > 2) { //limiting length of input
				         arg0.consume();  
				      }
					
					if(c == KeyEvent.VK_ENTER){
						edit_item_button.requestFocus();
					}
				}
			});
			count_textfield.setColumns(10);
					
			/*Cancel Button*/
			JButton cancel_item_button = new JButton("Cancel");
			cancel_item_button.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					if(e.getKeyChar() == KeyEvent.VK_ENTER){
						dispose();
					}
					
				}
			});
			cancel_item_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			cancel_item_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
			
			/*Sets the fields of the inventory item that is  being edited*/
			name_textfield.setText(selected_item.getInventoryItem());
			price_textfield.setText(selected_item.getInventoryPrice());
			count_textfield.setText(selected_item.getInventoryCount());
			
			/*Layout*/
			GroupLayout gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(37)
						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_panel.createSequentialGroup()
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(price_label, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(count_label, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
									.addComponent(name_label))
								.addGap(18)
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
									.addComponent(price_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(name_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(count_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(edit_item_button)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(cancel_item_button)
								.addGap(16)))
						.addContainerGap(70, Short.MAX_VALUE))
			);
			gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(34)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(name_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(name_label))
						.addGap(18)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(price_label)
							.addComponent(price_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(count_label)
							.addComponent(count_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(29)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(edit_item_button)
							.addComponent(cancel_item_button))
						.addContainerGap(32, Short.MAX_VALUE))
			);
			panel.setLayout(gl_panel);
		}
	}
	
	 /** 
     * Checks to see if a given name already exists as an item 
     * @param A items name (string)
     * @param An inventory database 
     * @return True if the given name already exist, false otherwise.
     */
	public boolean nameExistsInInventory(String input, InventoryDatabaseAccessor db){
		String[] list = db.getInventoryItemList(db.getInventory());
		for(int i=0; i< list.length;i++){
			if(input.toLowerCase().equals(list[i].toLowerCase())){
				return true;
			}
		}
		return false;
	}
	
	 /** 
     * Writes the addition/change of an inventory object in the given inventory database
     * @param An inventory database 
     * @return True if the write is successful, false otherwise.
     */
	public boolean writeInventoryAddModification(InventoryDatabaseAccessor db){
		
		if(nameExistsInInventory(name_textfield.getText(), db) && !name_textfield.getText().equals(selected_item.getInventoryItem())){
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Provide another name, item already exists.");
			return false;
		}
		
		if(name_textfield.getText().equals("")|| name_textfield.getText().equals(" ")||name_textfield.getText().startsWith(" ")){
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Provide valid item name.");
			return false;
		}
		
		if(price_textfield.getText().equals("")|| price_textfield.getText().equals(".")||price_textfield.getText().startsWith(".")
				|| !price_textfield.getText().contains(".") || containsAllZeros(price_textfield.getText())
						|| price_textfield.getText().substring(price_textfield.getText().indexOf(".")).length() != 3){
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Procide valid item price: 0.00");
			return false;
		}
		
		if(count_textfield.getText().equals("")|| count_textfield.getText().equals(" ")){
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Provide valid item count.");
			return false;
		}
		
		
		Inventory new_item = new Inventory(name_textfield.getText(), price_textfield.getText(), count_textfield.getText());
		db.getInventory().remove(index);
		db.getInventory().add(index, new_item);
		db.setInventory(db.getInventory());
		db.writeToFile(db.getInventory());
		dispose();
		return true;
	}
	
	 /**
     * Checks if zeros composes the whole of a string
     * @param A string that needs to be checked if zeros exist in them
     * @return True if the string contains all zeros, False otherwise
     */
	public boolean containsAllZeros(String x){
		for(int i =0; i < x.length(); i++){
			if(x.charAt(i) == '.'){
				continue;
			}
			if(x.charAt(i) != '0'){
				return false;
			}
		}
		return true;
	}

}
