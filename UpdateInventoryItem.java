/**
 * UpdateInventoryItem.java
 * 
 * @author Sad'e N. Smith
 *
 * Produces a JDialog that allows the user to edit an Inventory object
 * 
 **/

package iTracker;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ListSelectionModel;

public class UpdateInventoryItem extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JRadioButton postage_radio_button;
	private JRadioButton stock_radio_button;
	private JTable table;
	private DefaultTableModel model;
	private JButton add_item_button;
	private JButton edit_item_button;
	private JButton delete_item_button;
	private String[] table_headers;
	
	/**
	 * Create the dialog.
	 * @param An inventory database for postage inventory
	 * @param An inventory database for stock inventory
	 */
	public UpdateInventoryItem(final InventoryDatabaseAccessor postage_inventory, final InventoryDatabaseAccessor stock_inventory) {
		setResizable(false);
		
		/*Window Listener for updating the Jtable*/
		addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent arg0) {
				if(postage_radio_button.isSelected()){
					updateView(postage_inventory);
				}
				else{
					updateView(stock_inventory);
				}
			}
			public void windowLostFocus(WindowEvent e) {
				
			}
		});
		
		setTitle("Update Inventory Items");
		setIconImage(Toolkit.getDefaultToolkit().getImage(UpdateInventoryItem.class.getResource("/iTracker/logo.png")));
		setBounds(100, 100, 536, 296);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		/*JTable for data display*/
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_headers = new String[] {"Inventory Item", "Price","Count"};
		model = new DefaultTableModel(new String[][]{}, table_headers) {
			private static final long serialVersionUID = 1L;
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		table.setModel(model);
		
		/*Postage Radio Button*/
		postage_radio_button = new JRadioButton("Postage");
		postage_radio_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if( e.getKeyChar() == KeyEvent.VK_ENTER){
					postage_radio_button.setSelected(true);
					updateView(postage_inventory);
				}
			}
		});
		postage_radio_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateView(postage_inventory);
			}
		});
		postage_radio_button.setSelected(true);
		postage_radio_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		/*Stock Radio Button*/
		stock_radio_button = new JRadioButton("Stock");
		stock_radio_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if( e.getKeyChar() == KeyEvent.VK_ENTER){
					stock_radio_button.setSelected(true);
					updateView(stock_inventory);
				}
			}
		});
		stock_radio_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateView(stock_inventory);
			}
		});
		stock_radio_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		/*ButtonGroup for Stock Radio Button and Postage Radio Button*/
		ButtonGroup button_group = new ButtonGroup();
		button_group.add(postage_radio_button);
		button_group.add(stock_radio_button);	
		
		if(postage_radio_button.isSelected()){
			updateView(postage_inventory);
		}
		else{
			updateView(stock_inventory);
		}
		
		table.setFont(new Font("Tahoma", table.getFont().getStyle(), table.getFont().getSize()));
		JScrollPane scroll_pane = new JScrollPane(table);
		
		/*Add Item Button*/
		add_item_button = new JButton("Add Item");
		add_item_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER){
					if(postage_radio_button.isSelected()){

						AddInventoryItem dialog = new AddInventoryItem(postage_inventory);
						try {
							dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							dialog.setVisible(true);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					else{
						AddInventoryItem dialog = new AddInventoryItem(stock_inventory);
						try {
							dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							dialog.setVisible(true);						
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						
					}
				}
			}
		});
		add_item_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add_item_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(postage_radio_button.isSelected()){

					AddInventoryItem dialog = new AddInventoryItem(postage_inventory);
					try {
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setVisible(true);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				else{
					AddInventoryItem dialog = new AddInventoryItem(stock_inventory);
					try {
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setVisible(true);						
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					
				}

			}
		});
		
		/*Edit Item Button*/
		edit_item_button = new JButton("Edit Item");
		edit_item_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER){
					if(table.getSelectedRows().length != 0){
						if(postage_radio_button.isSelected()){
							try {
								EditInventoryItem dialog = new EditInventoryItem(postage_inventory, table.getSelectedRow());
								dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
								dialog.setVisible(true);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
						else{
							try {
								EditInventoryItem dialog = new EditInventoryItem(stock_inventory, table.getSelectedRow());
								dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
								dialog.setVisible(true);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
					else{
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, "Select an item to edit.");
					}
					
				}
			}
		});
		edit_item_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRows().length != 0){
					if(postage_radio_button.isSelected()){
						try {
							EditInventoryItem dialog = new EditInventoryItem(postage_inventory, table.getSelectedRow());
							dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							dialog.setVisible(true);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					else{
						try {
							EditInventoryItem dialog = new EditInventoryItem(stock_inventory, table.getSelectedRow());
							dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							dialog.setVisible(true);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
				else{
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "Select an item to edit.");
				}
			}
		});
		edit_item_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		/*Delete Item Button*/
		delete_item_button = new JButton("Delete Item");
		delete_item_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER
						){
					if(table.getSelectedRows().length != 0){
						if(postage_radio_button.isSelected()){
							postage_inventory.getInventory().remove(table.getSelectedRow());
							postage_inventory.setInventory(postage_inventory.getInventory());
							postage_inventory.writeToFile(postage_inventory.getInventory());
							updateView(postage_inventory);
						}
						else{
							stock_inventory.getInventory().remove(table.getSelectedRow());
							stock_inventory.setInventory(stock_inventory.getInventory());
							stock_inventory.writeToFile(stock_inventory.getInventory());
							updateView(stock_inventory);
						}
					}
					else{
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(null, "Select an item to delete.");
					}
				}
			}
		});
		delete_item_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRows().length != 0){
					if(postage_radio_button.isSelected()){
						postage_inventory.getInventory().remove(table.getSelectedRow());
						postage_inventory.setInventory(postage_inventory.getInventory());
						postage_inventory.writeToFile(postage_inventory.getInventory());
						updateView(postage_inventory);
					}
					else{
						stock_inventory.getInventory().remove(table.getSelectedRow());
						stock_inventory.setInventory(stock_inventory.getInventory());
						stock_inventory.writeToFile(stock_inventory.getInventory());
						updateView(stock_inventory);
					}
				}
				else{
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "Select an item to delete.");
				}
			}
		});
		delete_item_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		/*Layout*/
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(23)
					.addComponent(scroll_pane, GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
							.addComponent(edit_item_button, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(delete_item_button, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
							.addComponent(add_item_button, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
							.addComponent(stock_radio_button))
						.addComponent(postage_radio_button))
					.addGap(29))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(36)
							.addComponent(postage_radio_button)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(stock_radio_button)
							.addGap(30)
							.addComponent(add_item_button)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(edit_item_button)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(delete_item_button))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(23)
							.addComponent(scroll_pane, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(25, Short.MAX_VALUE))
		);
		

		contentPanel.setLayout(gl_contentPanel);
	}
	
	/**
	 * Updates the view of the JTable 
	 * @param A inventory database
	 */
	public void updateView(InventoryDatabaseAccessor db){
		int number_items = db.getInventory().size();
		model.getDataVector().removeAllElements();
		for(int i= 0;  i < number_items ;i++){
			String[]new_data = new String[3];
			new_data[0] =  db.getInventory().get(i).getInventoryItem();
			new_data[1] =  db.getInventory().get(i).getInventoryPrice();
			new_data[2] =  db.getInventory().get(i).getInventoryCount();
			model.addRow(new_data);
		}
		model.getDataVector().setSize(number_items);
		model.fireTableDataChanged();
	}
}
