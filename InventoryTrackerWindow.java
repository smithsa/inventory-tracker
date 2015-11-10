/**
 * DatabaseAccessor.java
 * 
 * @author Sad'e N. Smith
 *
 *Contains the meat of the GUI. Essentially, contains all the interactive components.
 * 
 * 
 */

package iTracker;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JMenuBar;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class InventoryTrackerWindow {

	private JFrame inventory_tracker_window;
	private JTextField postage_quantity_textfield;
	private JTextField postage_account_number_textfield;
	private JTextField postage_other_textfield;
	private JRadioButton postage_department_charge_radio_button;
	private JRadioButton postage_personal_charge_radio_button;
	private JComboBox<String> postage_item_combobox;
	private DefaultComboBoxModel<String> postage_model;
	private JButton postage_submit_button;
	protected String[] postage_inventory_items;
	protected Inventory postage_selected_inventory_item;
	private JLabel postage_price;
	private InventoryDatabaseAccessor postage_inventory_database;
	private ArrayList<Inventory> postage_inventory_list;
	private SaleDatabaseAccessor postage_sales_database;
	private ArrayList<Sale> postage_sales_list;
	private JTextField stock_other_item_name_textfield;
	private JTextField stock_other_item_price_textfield;
	private JTextField stock_account_number_textfield;
	private JTextField stock_quantity_textfield;
	private String[] stock_inventory_items;
	private Inventory stock_selected_inventory_item;
	private JLabel stock_price;
	private JComboBox<String> stock_item_combobox;
	private DefaultComboBoxModel<String> stock_model;
	private JRadioButton stock_personal_charge_radio_button;
	private JRadioButton stock_department_charge_radio_button;
	private JButton stock_submit_button;
	private JButton stock_clear_button;
	private InventoryDatabaseAccessor stock_inventory_database;
	private ArrayList<Inventory> stock_inventory_list;
	private SaleDatabaseAccessor stock_sales_database;
	private ArrayList<Sale> stock_sales_list;
	private DateFormat date_format;
	private Date date;
	
	  
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InventoryTrackerWindow window = new InventoryTrackerWindow();
					window.inventory_tracker_window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InventoryTrackerWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
				
		/*The JFrame Container*/
		inventory_tracker_window = new JFrame();
		inventory_tracker_window.setIconImage(Toolkit.getDefaultToolkit().getImage(InventoryTrackerWindow.class.getResource("/iTracker/logo.png")));
		inventory_tracker_window.setTitle("ITracker: Carleton College Mail Services ");
		inventory_tracker_window.setResizable(false);
		inventory_tracker_window.setBounds(100, 100, 593, 363);
		inventory_tracker_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*Creation of JTabbed pane*/
		JTabbedPane tabbed_pane = new JTabbedPane(JTabbedPane.TOP);
		tabbed_pane.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		/* Date variable for a field of a sale object*/
		date_format = new SimpleDateFormat("MM/dd/yyyy");
		date = new Date();
		
		/*Loading the databases*/
		postage_inventory_database = new InventoryDatabaseAccessor(System.getProperty("user.dir").replaceAll("\\/", "/") + "/Postage Inventory"); //path need to change eventually
		ArrayList<ArrayList<String>> postage_inventory_list_as_string = postage_inventory_database.scanDatabaseFile(3);
		postage_inventory_list = postage_inventory_database.serializeObjects(postage_inventory_list_as_string);
		postage_inventory_database.setInventory(postage_inventory_list);
		
		postage_sales_database = new SaleDatabaseAccessor(System.getProperty("user.dir").replaceAll("\\/", "/") +  "/Postage Sales");
		ArrayList<ArrayList<String>> postage_sale_list_as_string = postage_sales_database.scanDatabaseFile(6);
		postage_sales_list = postage_sales_database.serializeObjects(postage_sale_list_as_string);
		postage_sales_database.setSales(postage_sales_list);

		stock_inventory_database = new InventoryDatabaseAccessor(System.getProperty("user.dir").replaceAll("\\/", "/") +  "/Stock Inventory"); //path need to change eventually
		ArrayList<ArrayList<String>> stock_inventory_list_as_string = stock_inventory_database.scanDatabaseFile(3);
		stock_inventory_list = stock_inventory_database.serializeObjects(stock_inventory_list_as_string);
		stock_inventory_database.setInventory(stock_inventory_list);
		
		stock_sales_database = new SaleDatabaseAccessor(System.getProperty("user.dir").replaceAll("\\/", "/") +  "/Stock Sales");
		ArrayList<ArrayList<String>> stock_sale_list_as_string = stock_sales_database.scanDatabaseFile(6);
		stock_sales_list = stock_sales_database.serializeObjects(stock_sale_list_as_string);
		stock_sales_database.setSales(stock_sales_list);	
		
		/* Layout for the container*/
		GroupLayout groupLayout = new GroupLayout(inventory_tracker_window.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(17, Short.MAX_VALUE)
					.addComponent(tabbed_pane, GroupLayout.PREFERRED_SIZE, 560, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbed_pane, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(19, Short.MAX_VALUE))
		);

		/*********** Postage Sales Tabbed Panel ***********/
		
		JPanel postage_sales_panel = new JPanel();
		tabbed_pane.addTab("Postage Sale", null, postage_sales_panel, null);
			
		/* Postage selection item comboBox */	
		JLabel postage_item_label = new JLabel("Postage Item");
		postage_item_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		postage_item_combobox = new JComboBox<String>();
		postage_item_combobox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				updateDropDowns();
			}
		});
		
		postage_inventory_items = postage_inventory_database.getInventoryItemList(postage_inventory_database.getInventory());
		postage_model = new DefaultComboBoxModel<String>(postage_inventory_items);
		postage_item_combobox.setModel(postage_model);
		
		postage_item_combobox.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				String selected_item = (String) ((JComboBox<String>) e.getSource()).getSelectedItem();
				
				if(selected_item.equals(postage_inventory_items[0])){
					postage_price.setText("0.00");
				}
				
				if(selected_item.equals("Other")){
					postage_other_textfield.setEnabled(true);
				}
				else{
					postage_other_textfield.setEnabled(false);
					postage_other_textfield.setText("0.00");
				}
				
				postage_selected_inventory_item = postage_inventory_database.searchInventoryListByItem(selected_item, postage_inventory_list);
				if(postage_selected_inventory_item != null && !selected_item.equals("Other") 
						&& !postage_quantity_textfield.getText().equals("")
						&& !postage_quantity_textfield.getText().contains(" ") ){
					float item_price = Float.parseFloat(postage_selected_inventory_item.getInventoryPrice());
					int quantity = Integer.parseInt(postage_quantity_textfield.getText());
					float price_f = item_price * quantity;
					double price = Math.round(price_f * 100.0)/100.0;
					String string_price = Double.toString(price);
					
					if(string_price.length()-2 == string_price.indexOf(".")){
						string_price = Double.toString(price) + "0";
					}
			
					postage_price.setText(string_price);	
				}
				
				
				if(selected_item.equals("Other") && !postage_quantity_textfield.getText().equals("")
						&& !postage_quantity_textfield.getText().contains(" ") && !postage_other_textfield.getText().equals("") 
						&& !postage_other_textfield.getText().contains(" ") && !postage_other_textfield.getText().equals(".")){
							float item_price = Float.parseFloat(postage_other_textfield.getText());
							int quantity = Integer.parseInt(postage_quantity_textfield.getText());
							float price_f = item_price * quantity;
							double price = Math.round(price_f * 100.0)/100.0;
							String string_price = Double.toString(price);
							
							if(string_price.length()-2 == string_price.indexOf(".")){
								string_price = Double.toString(price) + "0";
							}
					
							postage_price.setText(string_price);
				}
			}
		});
		
		postage_item_combobox.setAutoscrolls(true);
		postage_item_combobox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		
		/*Postage quantity textField*/
		JLabel postage_quantity_label = new JLabel("Quantity");
		postage_quantity_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		postage_quantity_textfield = new JTextField();
		postage_quantity_textfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				if(postage_quantity_textfield.getText().equals("")){
					postage_price.setText("0.00");
				}
				
				if(postage_selected_inventory_item != null && !postage_quantity_textfield.getText().equals("")
						&& !postage_quantity_textfield.getText().contains(" ")  
						){
			
						float item_price = Float.parseFloat(postage_selected_inventory_item.getInventoryPrice());
						int quantity = Integer.parseInt(postage_quantity_textfield.getText());
						float price_f = item_price * quantity;
						double price = Math.round(price_f * 100.0)/100.0;
						String string_price = Double.toString(price);
						
						if(string_price.length()-2 == string_price.indexOf(".")){
							string_price = Double.toString(price) + "0";
						}
				
						postage_price.setText(string_price);
					
				}
				
				else if(postage_other_textfield.isEditable() && !postage_quantity_textfield.getText().equals("")
						&& !postage_quantity_textfield.getText().contains(" ") 
						&& !postage_other_textfield.getText().equals("") && !postage_other_textfield.getText().contains(" ")  
						){
			
						float item_price = Float.parseFloat(postage_other_textfield.getText());
						int quantity = Integer.parseInt(postage_quantity_textfield.getText());
						float price_f = item_price * quantity;
						double price = Math.round(price_f * 100.0)/100.0;
						String string_price = Double.toString(price);
						
						if(string_price.length()-2 == string_price.indexOf(".")){
							string_price = Double.toString(price) + "0";
						}
				
						postage_price.setText(string_price);
					
				}
				
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
				if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) || postage_quantity_textfield.getText().length() > 2) { //limiting length of input
			         arg0.consume();  
			      }
				
				if(c == KeyEvent.VK_ENTER){
					if(postage_other_textfield.isEnabled()){
						postage_other_textfield.requestFocus();
						
					}
					else{
						postage_personal_charge_radio_button.requestFocus();
					}
				}
			}
		});
		postage_quantity_textfield.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				postage_quantity_textfield.selectAll();
			}
		});
			
		postage_quantity_textfield.setText("1");
		postage_quantity_textfield.setColumns(10);
		
		
		/*Postage other specification textField*/
		postage_other_textfield = new JTextField();
		postage_other_textfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(postage_quantity_textfield.getText().equals("") || postage_quantity_textfield.getText().equals(".")){
					postage_price.setText("0.00");
				}
				
				if(!postage_other_textfield.getText().equals("") && !postage_other_textfield.getText().contains(" ") 
						&& !postage_other_textfield.getText().equals(".")
						&& !postage_quantity_textfield.getText().equals("")
						&& !postage_quantity_textfield.getText().contains(" ")){
		
						float item_price = Float.parseFloat(postage_other_textfield.getText());
						int quantity = Integer.parseInt(postage_quantity_textfield.getText());
						float price_f = item_price * quantity;
						double price = Math.round(price_f * 100.0)/100.0;
						String string_price = Double.toString(price);
						
						if(string_price.length()-2 == string_price.indexOf(".")){
							string_price = Double.toString(price) + "0";
						}
				
						postage_price.setText(string_price);
					
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				if(postage_other_textfield.getText().equals("")){
					postage_price.setText("0.00");
				}
				
				char c = e.getKeyChar();
				if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) &&  (c != KeyEvent.VK_PERIOD) 
						|| postage_other_textfield.getText().length() > 6
						|| (postage_other_textfield.getText().contains(".") && (!postage_other_textfield.getText().equals("0.00"))
							&& (postage_other_textfield.getText().substring(postage_other_textfield.getText().indexOf(".")).length() > 2
								))){ 
			         e.consume();  
			      }
				
				
				if(postage_other_textfield.getText().contains(".") && c == KeyEvent.VK_PERIOD 
						&& !postage_other_textfield.getText().equals("0.00")){
					e.consume();
				}
				if(c == KeyEvent.VK_ENTER){
					postage_personal_charge_radio_button.requestFocus();
				}
			}
		});
		postage_other_textfield.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				postage_other_textfield.selectAll();
			}
		});
		postage_other_textfield.setText("0.00");
		postage_other_textfield.setEnabled(false);
		postage_other_textfield.setColumns(10);
		
		JLabel postage_other_label = new JLabel("Other Item");
		postage_other_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		/* Postage personal charge radio button */	
		postage_personal_charge_radio_button = new JRadioButton("Personal Charge");
		postage_personal_charge_radio_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				postage_account_number_textfield.setText("99999");
				postage_account_number_textfield.setEnabled(false);
			}
		});
		postage_personal_charge_radio_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER){
					postage_personal_charge_radio_button.setSelected(true);
					postage_department_charge_radio_button.requestFocus();
					postage_account_number_textfield.setEnabled(false);
					postage_account_number_textfield.setText("99999");
					
				}
			}
		});
		postage_personal_charge_radio_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		postage_personal_charge_radio_button.setSelected(true);
		
		/* Postage department charge radio button */
		postage_department_charge_radio_button = new JRadioButton("Department Charge");
		postage_department_charge_radio_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		postage_department_charge_radio_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				postage_account_number_textfield.setEnabled(true);
				postage_account_number_textfield.setText("");
				postage_account_number_textfield.requestFocus();
			}
		});
		postage_department_charge_radio_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER){
					postage_department_charge_radio_button.setSelected(true);
					postage_account_number_textfield.setEnabled(true);
					postage_account_number_textfield.setText("");
					postage_account_number_textfield.requestFocus();
				}
			}
		});
		
		ButtonGroup postage_account_selection = new ButtonGroup();
		postage_account_selection.add(postage_personal_charge_radio_button);
		postage_account_selection.add(postage_department_charge_radio_button);
		
		/*Postage account number textField */
		postage_account_number_textfield = new JTextField();
		postage_account_number_textfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) { //limiting length of input
			         e.consume();  
			      }
				
				if(c == KeyEvent.VK_ENTER){
					postage_submit_button.requestFocus();
				}
			}
		});
		
		
		postage_account_number_textfield.setText("99999");
		postage_account_number_textfield.setEnabled(false);
		postage_account_number_textfield.setColumns(10);
		
		JLabel postage_account_number_label = new JLabel("Account Number");
		postage_account_number_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		/* Postage submit button */
		postage_submit_button = new JButton("Submit Sale");
		postage_submit_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER){
					writePostageSale();
				}
			}
		});
		postage_submit_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writePostageSale();
			}
		});
				
		/*Postage clear button */
		JButton postage_clear_button = new JButton("Clear");
		postage_clear_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER){
					clearPostagePanel();
				}
			}
		});
		postage_clear_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearPostagePanel();
			}
		});
				
		/* Postage sales price panel*/
		JLabel postage_price_label = new JLabel("Total Price:");
		postage_price_label.setFont(new Font("Tahoma", Font.BOLD, 11));
		postage_price = new JLabel("0.00");
		postage_price.setForeground(Color.BLACK);
		postage_price.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		/*Postage Layout*/
		GroupLayout gl_postage_sales_panel = new GroupLayout(postage_sales_panel);
		gl_postage_sales_panel.setHorizontalGroup(
			gl_postage_sales_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_postage_sales_panel.createSequentialGroup()
					.addGroup(gl_postage_sales_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_postage_sales_panel.createSequentialGroup()
							.addGap(48)
							.addComponent(postage_item_label)
							.addGap(11)
							.addComponent(postage_item_combobox, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)
							.addGap(15)
							.addComponent(postage_quantity_label)
							.addGap(11)
							.addComponent(postage_quantity_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_postage_sales_panel.createSequentialGroup()
							.addGap(48)
							.addComponent(postage_other_label)
							.addGap(23)
							.addComponent(postage_other_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_postage_sales_panel.createSequentialGroup()
							.addGap(48)
							.addComponent(postage_personal_charge_radio_button))
						.addGroup(gl_postage_sales_panel.createSequentialGroup()
							.addGap(48)
							.addComponent(postage_department_charge_radio_button)
							.addGap(40)
							.addComponent(postage_account_number_label)
							.addGap(5)
							.addComponent(postage_account_number_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_postage_sales_panel.createSequentialGroup()
							.addGap(345)
							.addComponent(postage_price_label)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(postage_price, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_postage_sales_panel.createSequentialGroup()
							.addGap(187)
							.addComponent(postage_submit_button)
							.addGap(6)
							.addComponent(postage_clear_button)))
					.addGap(20))
		);
		gl_postage_sales_panel.setVerticalGroup(
			gl_postage_sales_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_postage_sales_panel.createSequentialGroup()
					.addGap(49)
					.addGroup(gl_postage_sales_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_postage_sales_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(postage_item_label))
						.addComponent(postage_item_combobox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_postage_sales_panel.createSequentialGroup()
							.addGap(3)
							.addComponent(postage_quantity_label))
						.addGroup(gl_postage_sales_panel.createSequentialGroup()
							.addGap(1)
							.addComponent(postage_quantity_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_postage_sales_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_postage_sales_panel.createSequentialGroup()
							.addGap(11)
							.addGroup(gl_postage_sales_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_postage_sales_panel.createSequentialGroup()
									.addGap(2)
									.addComponent(postage_other_label))
								.addComponent(postage_other_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(6)
							.addComponent(postage_personal_charge_radio_button)
							.addGap(5)
							.addGroup(gl_postage_sales_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(postage_department_charge_radio_button)
								.addGroup(gl_postage_sales_panel.createSequentialGroup()
									.addGap(4)
									.addComponent(postage_account_number_label))
								.addGroup(gl_postage_sales_panel.createSequentialGroup()
									.addGap(1)
									.addComponent(postage_account_number_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(15)
							.addComponent(postage_price_label)
							.addGap(12)
							.addGroup(gl_postage_sales_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(postage_submit_button)
								.addComponent(postage_clear_button)))
						.addGroup(gl_postage_sales_panel.createSequentialGroup()
							.addGap(99)
							.addComponent(postage_price))))
		);
		postage_sales_panel.setLayout(gl_postage_sales_panel);
		JPanel stock_sales_panel = new JPanel();
		tabbed_pane.addTab("Stock Sale", null, stock_sales_panel, null);
		
		/*********** Stock Sales Tabbed Pane ***********/
		
		/* Sales selection item comboBox */
		JLabel stock_item_label = new JLabel("Stock Item");
		stock_item_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		stock_inventory_items = stock_inventory_database.getInventoryItemList(stock_inventory_list);
		stock_model = new DefaultComboBoxModel<String>(stock_inventory_items);
		stock_item_combobox = new JComboBox<String>();
		stock_item_combobox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				updateDropDowns();
			}
		});
		stock_item_combobox.setModel(stock_model);
		stock_item_combobox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		stock_item_combobox.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				String selected_item = (String) ((JComboBox<String>) e.getSource()).getSelectedItem();
				
				if(selected_item.equals(stock_inventory_items[0])){
					stock_price.setText("0.00");
				}
				
			
				if(selected_item.equals("Other")){
					stock_other_item_name_textfield.setEnabled(true);
					stock_other_item_price_textfield.setEnabled(true);
				}
				else{
					stock_other_item_name_textfield.setEnabled(false);
					stock_other_item_price_textfield.setEnabled(false);
					stock_other_item_price_textfield.setText("0.00");
				}
				
				stock_selected_inventory_item = stock_inventory_database.searchInventoryListByItem(selected_item, stock_inventory_list);
				if(stock_selected_inventory_item != null && !selected_item.equals("Other") 
						&& !stock_quantity_textfield.getText().equals("")
						&& !stock_quantity_textfield.getText().contains(" ") ){
					float item_price = Float.parseFloat(stock_selected_inventory_item.getInventoryPrice());
					int quantity = Integer.parseInt(stock_quantity_textfield.getText());
					float price_f = item_price * quantity;
					double price = Math.round(price_f * 100.0)/100.0;
					String string_price = Double.toString(price);
					
					if(string_price.length()-2 == string_price.indexOf(".")){
						string_price = Double.toString(price) + "0";
					}
			
					stock_price.setText(string_price);	
				}
				
				
				if(selected_item.equals("Other") && !stock_quantity_textfield.getText().equals("")
						&& !stock_quantity_textfield.getText().contains(" ") && !stock_other_item_price_textfield.getText().equals("") 
						&& !stock_other_item_price_textfield.getText().contains(" ") && !stock_other_item_price_textfield.getText().equals(".")){
							float item_price = Float.parseFloat(stock_other_item_price_textfield.getText());
							int quantity = Integer.parseInt(stock_quantity_textfield.getText());
							float price_f = item_price * quantity;
							double price = Math.round(price_f * 100.0)/100.0;
							String string_price = Double.toString(price);
							
							if(string_price.length()-2 == string_price.indexOf(".")){
								string_price = Double.toString(price) + "0";
							}
					
							stock_price.setText(string_price);
				}
			}
		});
		
		/* Stock quantity textField*/
		JLabel stock_quantity_label = new JLabel("Quantity");
		stock_quantity_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		stock_quantity_textfield = new JTextField();
		stock_quantity_textfield.setText("1");
		stock_quantity_textfield.setColumns(10);
		
		stock_quantity_textfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				if(stock_quantity_textfield.getText().equals("")){
					stock_price.setText("0.00");
				}
				
				if(stock_selected_inventory_item != null && !stock_quantity_textfield.getText().equals("")
						&& !stock_quantity_textfield.getText().contains(" ")  
						){
		
						float item_price = Float.parseFloat(stock_selected_inventory_item.getInventoryPrice());
						int quantity = Integer.parseInt(stock_quantity_textfield.getText());
						float price_f = item_price * quantity;
						double price = Math.round(price_f * 100.0)/100.0;
						String string_price = Double.toString(price);
						
						if(string_price.length()-2 == string_price.indexOf(".")){
							string_price = Double.toString(price) + "0";
						}
				
						stock_price.setText(string_price);
					
				}
				
				else if(stock_other_item_price_textfield.isEditable() && !stock_quantity_textfield.getText().equals("")
						&& !stock_quantity_textfield.getText().contains(" ") 
						&& !stock_other_item_price_textfield.getText().equals("") && !stock_other_item_price_textfield.getText().contains(" ")  
						){
			
						float item_price = Float.parseFloat(stock_other_item_price_textfield.getText());
						int quantity = Integer.parseInt(stock_quantity_textfield.getText());
						float price_f = item_price * quantity;
						double price = Math.round(price_f * 100.0)/100.0;
						String string_price = Double.toString(price);
						
						if(string_price.length()-2 == string_price.indexOf(".")){
							string_price = Double.toString(price) + "0";
						}
				
						stock_price.setText(string_price);
					
				}
				
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
				char c = arg0.getKeyChar();
				if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) || stock_quantity_textfield.getText().length() > 2) { //limiting length of input
			         arg0.consume();  
			      }
				
				if(c == KeyEvent.VK_ENTER){
					if(stock_other_item_name_textfield.isEnabled()){
						stock_other_item_name_textfield.requestFocus();
						
					}
					else{
						stock_personal_charge_radio_button.requestFocus();
					}
				}
			}
		});
		stock_quantity_textfield.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				stock_quantity_textfield.selectAll();
			}
		});
		
		/*Stock other specifications*/
		JLabel stock_other_item_name_label = new JLabel("Other Item");
		stock_other_item_name_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		/*Stock other item name*/
		stock_other_item_name_textfield = new JTextField();
		stock_other_item_name_textfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(stock_other_item_name_textfield.getText().length() > 16 || arg0.getKeyChar() == ':' 
						|| arg0.getKeyChar() == '~' || arg0.getKeyChar() == '"' 
						||arg0.getKeyChar() == '\''||arg0.getKeyChar() == '|'){
					arg0.consume();
				} 
				
				if(arg0.getKeyChar() == KeyEvent.VK_ENTER){
					stock_other_item_price_textfield.requestFocus();
				}
			}
		});
		stock_other_item_name_textfield.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				stock_other_item_name_textfield.selectAll(); 
			}
		});
		
		stock_other_item_name_textfield.setEnabled(false);
		stock_other_item_name_textfield.setText("Item Name");
		stock_other_item_name_textfield.setColumns(10);
		
		/*Stock other item price*/
		JLabel stock_other_price_label = new JLabel("Other Price");
		stock_other_price_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		stock_other_item_price_textfield = new JTextField();
		stock_other_item_price_textfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				
				if(stock_other_item_price_textfield.getText().equals("") || stock_other_item_price_textfield.getText().equals(".")){
					stock_price.setText("0.00");
				}
				
				char c = arg0.getKeyChar();
				if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) &&  (c != KeyEvent.VK_PERIOD) 
						|| stock_other_item_price_textfield.getText().length() > 6
						|| (stock_other_item_price_textfield.getText().contains(".") && (!stock_other_item_price_textfield.getText().equals("0.00")))
								&& stock_other_item_price_textfield.getText().substring(stock_other_item_price_textfield.getText().indexOf(".")).length() > 2){ 
					arg0.consume();  
			      }
				
				
				if(stock_other_item_price_textfield.getText().contains(".") && c == KeyEvent.VK_PERIOD
						&& !stock_other_item_price_textfield.getText().equals("0.00")){
					arg0.consume();
				}
				
				if(c == KeyEvent.VK_ENTER){
					stock_personal_charge_radio_button.requestFocus();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(stock_quantity_textfield.getText().equals("")){
					stock_price.setText("0.00");
				}
				
				if(stock_other_item_price_textfield.getText().equals("")){
					stock_price.setText("0.00");
				}
				
				if(!stock_other_item_price_textfield.getText().equals("") && !stock_other_item_price_textfield.getText().contains(" ") 
						&& !stock_other_item_price_textfield.getText().equals(".")
						&& !stock_quantity_textfield.getText().equals("")
						&& !stock_quantity_textfield.getText().contains(" ")){
		
						float item_price = Float.parseFloat(stock_other_item_price_textfield.getText());
						int quantity = Integer.parseInt(stock_quantity_textfield.getText());
						float price_f = item_price * quantity;
						double price = Math.round(price_f * 100.0)/100.0;
						String string_price = Double.toString(price);
						
						if(string_price.length()-2 == string_price.indexOf(".")){
							string_price = Double.toString(price) + "0";
						}
				
						stock_price.setText(string_price);
					
				}
			}
		});
		stock_other_item_price_textfield.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				stock_other_item_price_textfield.selectAll();
			}
		});
		
		stock_other_item_price_textfield.setEnabled(false);
		stock_other_item_price_textfield.setText("0.00");
		stock_other_item_price_textfield.setColumns(10);
		
		/*Stock personal charge radio button*/
		stock_personal_charge_radio_button = new JRadioButton("Personal Charge");
		stock_personal_charge_radio_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(arg0.getKeyChar() == KeyEvent.VK_ENTER){
					stock_personal_charge_radio_button.setSelected(true);
					stock_department_charge_radio_button.requestFocus();
					stock_account_number_textfield.setEnabled(false);
					stock_account_number_textfield.setText("99999");
				}
				
			}
		});
		stock_personal_charge_radio_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stock_account_number_textfield.setEnabled(false);
				stock_account_number_textfield.setText("99999");
			}
		});
		stock_personal_charge_radio_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		stock_personal_charge_radio_button.setSelected(true);
		
		/*Stock department charge radio button*/
		stock_department_charge_radio_button = new JRadioButton("Department Charge");
		stock_department_charge_radio_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stock_account_number_textfield.setEnabled(true);
				stock_account_number_textfield.setText("");
				stock_account_number_textfield.requestFocus();
			}
		});
		stock_department_charge_radio_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(arg0.getKeyChar() == KeyEvent.VK_ENTER){
					stock_department_charge_radio_button.setSelected(true);
					stock_account_number_textfield.setEnabled(true);
					stock_account_number_textfield.setText("");
					stock_account_number_textfield.requestFocus();
				}
			}
		});
		stock_department_charge_radio_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		ButtonGroup stock_account_selection = new ButtonGroup();
		stock_account_selection.add(stock_personal_charge_radio_button);
		stock_account_selection.add(stock_department_charge_radio_button);
			
		/*Stock account number textfield */
		JLabel stock_account_number_label_ = new JLabel("Account Number");
		stock_account_number_label_.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		stock_account_number_textfield = new JTextField();
		stock_account_number_textfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) { //limiting length of input
			         e.consume();  // ignore event
			      }
				
				if(c == KeyEvent.VK_ENTER){
					stock_submit_button.requestFocus();
				}
			}
		});
		
		stock_account_number_textfield.setEnabled(false);
		stock_account_number_textfield.setText("99999");
		stock_account_number_textfield.setColumns(10);
		
			
		/*Stock submit button */
		stock_submit_button = new JButton("Submit Sale");
		stock_submit_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				writeStockSale();
			}
		});
		stock_submit_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				writeStockSale();
			}
		});
		
		/*Stock clear button */
		stock_clear_button = new JButton("Clear");
		stock_clear_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				clearStockPanel();
			}
		});
		stock_clear_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				clearStockPanel();
			}
		});
		JLabel stock_price_label = new JLabel("Total Price:");
		stock_price_label.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		/* Stock price panel */
		stock_price = new JLabel("0.00");
		stock_price.setForeground(Color.BLACK);
		stock_price.setFont(new Font("Tahoma", Font.BOLD, 15));
				
		// Layout for Stock Sales Panel
		GroupLayout gl_stock_sales_panel = new GroupLayout(stock_sales_panel);
		gl_stock_sales_panel.setHorizontalGroup(
			gl_stock_sales_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_stock_sales_panel.createSequentialGroup()
					.addGroup(gl_stock_sales_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_stock_sales_panel.createSequentialGroup()
							.addGap(48)
							.addGroup(gl_stock_sales_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_stock_sales_panel.createSequentialGroup()
									.addGroup(gl_stock_sales_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(stock_item_label)
										.addComponent(stock_other_item_name_label))
									.addGap(18)
									.addGroup(gl_stock_sales_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_stock_sales_panel.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(stock_other_item_name_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGap(29)
											.addComponent(stock_other_price_label, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(stock_other_item_price_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_stock_sales_panel.createSequentialGroup()
											.addComponent(stock_item_combobox, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
											.addGap(24)
											.addComponent(stock_quantity_label)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(stock_quantity_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
								.addComponent(stock_personal_charge_radio_button)
								.addGroup(gl_stock_sales_panel.createSequentialGroup()
									.addComponent(stock_department_charge_radio_button)
									.addGap(37)
									.addComponent(stock_account_number_label_)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(stock_account_number_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED, 133, Short.MAX_VALUE))
						.addGroup(gl_stock_sales_panel.createSequentialGroup()
							.addGap(184)
							.addComponent(stock_submit_button)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(stock_clear_button))
						.addGroup(Alignment.TRAILING, gl_stock_sales_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(stock_price_label, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(stock_price, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
							.addGap(12)))
					.addContainerGap())
		);
		gl_stock_sales_panel.setVerticalGroup(
			gl_stock_sales_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_stock_sales_panel.createSequentialGroup()
					.addGap(49)
					.addGroup(gl_stock_sales_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(stock_quantity_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(stock_item_combobox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(stock_item_label)
						.addComponent(stock_quantity_label))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_stock_sales_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(stock_other_item_name_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(stock_other_price_label, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(stock_other_item_price_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(stock_other_item_name_label, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
					.addGap(7)
					.addComponent(stock_personal_charge_radio_button)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_stock_sales_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(stock_department_charge_radio_button)
						.addComponent(stock_account_number_label_)
						.addComponent(stock_account_number_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_stock_sales_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(stock_price_label)
						.addComponent(stock_price, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_stock_sales_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(stock_submit_button)
						.addComponent(stock_clear_button))
					.addContainerGap(38, Short.MAX_VALUE))
		);
		
		/*Menu Bar*/
		stock_sales_panel.setLayout(gl_stock_sales_panel);
		inventory_tracker_window.getContentPane().setLayout(groupLayout);
		JMenuBar menuBar = new JMenuBar();
		JMenu menu_reports = new JMenu("ITracker Reports");
		menu_reports.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		JMenu menu_inventory = new JMenu("Inventory");

		menu_inventory.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		JMenu menu_sales = new JMenu("Sales");
		menu_sales.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		JMenu menu_help = new JMenu("Help");
		menu_help.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menuBar.add(menu_reports);
		menuBar.add(menu_inventory);
		menuBar.add(menu_sales);
		menuBar.add(menu_help);
		
		
		
		JMenuItem inventory_report =  new  JMenuItem("Inventory Report");
		inventory_report.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					InventoryReport dialog = new InventoryReport(postage_inventory_database, stock_inventory_database);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		inventory_report.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		JMenuItem sale_report_summary =  new  JMenuItem("Sales Report: Summary");
		sale_report_summary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					SalesReportSummary dialog = new SalesReportSummary(postage_sales_database, stock_sales_database);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		sale_report_summary.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menu_reports.add(inventory_report);
		menu_reports.add(sale_report_summary);
	
		JMenuItem update_inventory_items =  new  JMenuItem("Update Inventory Items");
		update_inventory_items.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					UpdateInventoryItem dialog = new UpdateInventoryItem(postage_inventory_database, stock_inventory_database);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		update_inventory_items.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		JMenuItem add_to_inventory_count =  new  JMenuItem("Add to Inventory Count");
		add_to_inventory_count.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					AddToInventoryCount dialog = new AddToInventoryCount(postage_inventory_database, stock_inventory_database);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		add_to_inventory_count.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menu_inventory.add(update_inventory_items);
		menu_inventory.add(add_to_inventory_count);
		
		JMenuItem history =  new  JMenuItem("History");
		history.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ViewHistory dialog = new ViewHistory(postage_sales_database, stock_sales_database);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		history.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menu_sales.add(history);
		
		JMenuItem user_guide =  new  JMenuItem("User Guide");
		user_guide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			String url_string = "https://sites.google.com/site/itrackeruserguide/";
			openWebPage(url_string);
			
			}
		});
		user_guide.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		JMenuItem source_code =  new  JMenuItem("Source Code");
		source_code.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String url_string = "https://bitbucket.org/smithsa/inventory-application";
				openWebPage(url_string);
			}
		});
		source_code.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menu_help.add(user_guide);
		menu_help.add(source_code);
	
		JMenuItem about =  new  JMenuItem("About ITracker");
		about.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					AboutITracker dialog = new AboutITracker();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		menu_help.add(about);
		
		JMenuItem sales_detailed_report =  new  JMenuItem("Sales Report: Detailed");
		sales_detailed_report.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					SalesReportDetailed dialog = new SalesReportDetailed(postage_sales_database, stock_sales_database);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		sales_detailed_report.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menu_reports.add(sales_detailed_report);
		
		inventory_tracker_window.setJMenuBar(menuBar);
	}
	
	/**
	 * Updates the drop downs/comboBox for item selection
	 */
	public void updateDropDowns(){
		String[] postage_new_list = postage_inventory_database.getInventoryItemList(postage_inventory_database.getInventory());
		postage_item_combobox.setModel(new DefaultComboBoxModel<String>(postage_new_list));
		
		String[] stock_new_list = stock_inventory_database.getInventoryItemList(stock_inventory_database.getInventory());
		stock_item_combobox.setModel(new DefaultComboBoxModel<String>(stock_new_list));
	}
	
    /**
     * Clears all field inputs on the 'Postage Sale' JTabbed panel
     */
	public void clearPostagePanel(){
		if(postage_department_charge_radio_button.isSelected()){
			postage_account_number_textfield.setText("99999");
			postage_account_number_textfield.setEnabled(false);
			postage_personal_charge_radio_button.setSelected(true);
		}
		
		postage_other_textfield.setText("0.00");
		postage_other_textfield.setEnabled(false);
		postage_quantity_textfield.setText("1");
		postage_item_combobox.setSelectedItem(postage_inventory_items[0]);
		postage_price.setText("0.00");
	}
	
    /**
     * Clears all field inputs on the 'Stock Sale' JTabbed panel
     */
	public void clearStockPanel(){
		if(stock_department_charge_radio_button.isSelected()){
			stock_account_number_textfield.setText("99999");
			stock_account_number_textfield.setEnabled(false);
			stock_personal_charge_radio_button.setSelected(true);
		}
		
		stock_other_item_name_textfield.setText("Item Name");
		stock_other_item_price_textfield.setText("0.00");
		stock_other_item_name_textfield.setEnabled(false);
		stock_other_item_price_textfield.setEnabled(false);
		stock_quantity_textfield.setText("1");
		stock_item_combobox.setSelectedItem(postage_inventory_items[0]);
		stock_price.setText("0.00");
	}
	
    /**
     * Checks if input on the 'Postage Sale' panel is correct, if so it writes the sale to the database
     * @return True upon successful write conditions, False otherwise
     */
	public boolean writePostageSale(){
		
		if(postage_other_textfield.isEnabled() && (postage_other_textfield.getText().equals("")  
				||containsAllZeros(postage_other_textfield.getText())
				|| postage_other_textfield.getText().contains(" ") || postage_other_textfield.getText().equals("."))){			
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Specify valid other postage item.");
			return false;
		}
		if(postage_other_textfield.getText().contains(".")
				&& postage_other_textfield.getText().substring(postage_other_textfield.getText().indexOf(".")).length() > 3){
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Specify valid other postage item.");
			return false;
		}
		if(postage_item_combobox.getSelectedItem().equals(postage_inventory_items[0])){
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null,"Select a postage item.");
			return false;
		}
		if(postage_account_number_textfield.getText().equals("") || postage_account_number_textfield.getText().contains(" ")){
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Provide valid account number.");
			return false;
		}
		if(postage_quantity_textfield.getText().equals("") || postage_quantity_textfield.getText().contains(" ")
				|| postage_quantity_textfield.getText().equals("0")){
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Provide valid quantity.");
			return false;
		}
		
		
			
		String item_input;
		if(!((String)postage_item_combobox.getSelectedItem()).equals("Other")){
			item_input = (String)postage_item_combobox.getSelectedItem();
		}
		else{
			
			if(postage_other_textfield.getText().startsWith(".")){
				item_input = "Other: $0"+ postage_other_textfield.getText();
			}
			else{
				item_input = "Other: $" + postage_other_textfield.getText();
			}
			
			if((postage_other_textfield.getText().startsWith(".") && postage_other_textfield.getText().length() == 2)
					|| (postage_other_textfield.getText().length() == 3 && postage_other_textfield.getText().charAt(1) == '.')){
				item_input = item_input + "0";
			}
			if(!postage_other_textfield.getText().startsWith(".") && !postage_other_textfield.getText().contains(".")){
				item_input = item_input + ".00";
			}
			
			if(postage_other_textfield.getText().endsWith(".")){
				item_input = item_input + "00";
			}
		}
		
		String quantity_input = postage_quantity_textfield.getText();
		String account_input = postage_account_number_textfield.getText();
		String date_input = date_format.format(date);
		String price_input = postage_price.getText();
		
		Sale new_sale = new Sale(item_input,quantity_input,account_input,date_input, price_input, generateUniqueId() );
		postage_sales_list.add(new_sale);
		postage_sales_database.setSales(postage_sales_list);
		postage_sales_database.writeToFile(postage_sales_list);
		
		if(!postage_other_textfield.isEnabled()){
			Inventory item = postage_inventory_database.searchInventoryListByItem((String) postage_item_combobox.getSelectedItem(), postage_inventory_database.getInventory());
			if(Integer.parseInt(item.getInventoryCount())>0){
				Integer new_count = Integer.parseInt(item.getInventoryCount()) - 1;
				item.setIventoryCount(Integer.toString(new_count));
				postage_inventory_database.getInventory().remove(postage_item_combobox.getSelectedIndex()-1);
				postage_inventory_database.getInventory().add(postage_item_combobox.getSelectedIndex()-1, item);
				
				postage_inventory_database.writeToFile(postage_inventory_database.getInventory());
			}
		}
			
		
		
		
		clearPostagePanel();
			
		
		return true;
	}

	
    /**
     * Checks if input on the 'Stock Sale' panel is correct, if so it writes the sale to the database
     * @return True upon successful write conditions, False otherwise
     */
	public boolean writeStockSale(){
		
		if(stock_other_item_name_textfield.isEnabled() && (stock_other_item_name_textfield.getText().equals("")  
				|| stock_other_item_name_textfield.getText().startsWith(" ") || stock_other_item_name_textfield.getText().equals("Item Name"))){
			
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Specify valid other stock item name.");
			return false;
		}
		if(stock_other_item_price_textfield.isEnabled() && (stock_other_item_price_textfield.getText().equals("")  
				||containsAllZeros(stock_other_item_price_textfield.getText())
				|| stock_other_item_price_textfield.getText().contains(" ") || stock_other_item_price_textfield.getText().equals("."))){
	
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Specify valid other stock item price.");
			return false;
		}
		if(stock_other_item_price_textfield.getText().contains(".")
				&& stock_other_item_price_textfield.getText().substring(stock_other_item_price_textfield.getText().indexOf(".")).length()>3){
			
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Specify valid other stock item price.");
			return false;
		
		}
		if(stock_item_combobox.getSelectedItem().equals(stock_inventory_items[0])){
		
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null,"Select a stock item.");
			return false;
		}
		if(stock_account_number_textfield.getText().equals("") || stock_account_number_textfield.getText().contains(" ")){
		
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Provide valid account number.");
			return false;
		}
		if(stock_quantity_textfield.getText().equals("") || stock_quantity_textfield.getText().contains(" ")
				|| stock_quantity_textfield.getText().equals("0")){
			
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Provide valid quantity.");
			return false;
		}
		
		
		String item_input;
		if(!((String)stock_item_combobox.getSelectedItem()).equals("Other")){
			item_input = (String)stock_item_combobox.getSelectedItem();
		}
		else{
			String price;
			if(stock_other_item_price_textfield.getText().startsWith(".")){
				price = "$0"+ stock_other_item_price_textfield.getText();

			}
			else{
				price = "$" + stock_other_item_price_textfield.getText();
			}
			
			if((stock_other_item_price_textfield.getText().startsWith(".") && stock_other_item_price_textfield.getText().length() == 2)
					|| (stock_other_item_price_textfield.getText().length() == 3 && stock_other_item_price_textfield.getText().charAt(1) == '.')){
				price = price + "0";
			}
			if(!stock_other_item_price_textfield.getText().startsWith(".") && !stock_other_item_price_textfield.getText().contains(".")){
				price= price + ".00";
			}

			item_input="Other: "+ stock_other_item_name_textfield.getText() + "-" + price;
		}
	
		String quantity_input = stock_quantity_textfield.getText();
		String account_input = stock_account_number_textfield.getText();
		String date_input = date_format.format(date);
		String price_input = stock_price.getText();
		
		Sale new_sale = new Sale(item_input,quantity_input,account_input,date_input, price_input, generateUniqueId());
		stock_sales_list.add(new_sale);
		postage_sales_database.setSales(postage_sales_list);
		stock_sales_database.writeToFile(stock_sales_list);
		
		if(!stock_other_item_name_textfield.isEnabled()){
			Inventory item = stock_inventory_database.searchInventoryListByItem((String) stock_item_combobox.getSelectedItem(), stock_inventory_database.getInventory());
			Integer new_count = Integer.parseInt(item.getInventoryCount())- 1;
			item.setIventoryCount(Integer.toString(new_count));
			stock_inventory_database.getInventory().remove(stock_item_combobox.getSelectedIndex()-1);
			stock_inventory_database.getInventory().add(stock_item_combobox.getSelectedIndex()-1, item);
			
			stock_inventory_database.writeToFile(stock_inventory_database.getInventory());
		}
		
		clearStockPanel();
			
		
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
	
	/**
	 * Opens a web page
	 * @param A string URL
	 */
	public void openWebPage(String url_string){
		/* Code Snippet From: http://stackoverflow.com/questions/10967451/open-a-link-in-browser-with-java-button*/
	    try {
	    	URL url = new URL(url_string);
	        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	            try {
	                desktop.browse(url.toURI());
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        }
	    } catch (MalformedURLException X) {
			X.printStackTrace();
		}
	}
	
	/**
	 * Generate a unique id
	 * @return A unique id which is a string
	 */
	public String generateUniqueId() { 
		/* Code Snippet From http://stackoverflow.com/questions/2178992/how-to-generate-unique-id-in-java-integer*/
	        UUID idOne = UUID.randomUUID();
	        String str=""+idOne;        
	        int uid=str.hashCode();
	        String filterStr=""+uid;
	        str=filterStr.replaceAll("-", "");
	        return str;
	}
	
	
}
