/**
 * ViewHistory.java
 * 
 * @author Sad'e N. Smith
 *
 * Produces a table that displays the sale history breakdown in a JDialog. User can delete History as well.
 * 
 **/
package iTracker;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Component;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewHistory extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JFormattedTextField end_date_textfield;
	private JFormattedTextField start_date_textfield;
	private JRadioButton postage_radio_button;
	private JRadioButton stock_radio_button;
	private JButton search_button;
	private String[] table_headers;
	private JTable table;
	private DefaultTableModel model;
	private JButton delete_button;
	
	/**
	 * Create the dialog.
	 * @param An sales database for postage inventory
	 * @param An sales database for stock inventory
	 */
	public ViewHistory(final SaleDatabaseAccessor postage_sales_db, final SaleDatabaseAccessor stock_sales_db){
		setIconImage(Toolkit.getDefaultToolkit().getImage(ViewHistory.class.getResource("/iTracker/logo.png")));
		setTitle("View Sales History");
		setResizable(false);
		setBounds(100, 100, 725, 322);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		/*JTable for data display*/
		table_headers = new String[] {"Charge #", "Sale Item","Quantity", "Total Charge ($)", "Sale Date", "ID"};
		model = new DefaultTableModel(new String[][]{}, table_headers) {
			private static final long serialVersionUID = 1L;
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};

		table = new JTable(model);
		table.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		table.setBackground(Color.WHITE);
		table.setFont(new Font("Tahoma", table.getFont().getStyle(), 11));
		//crapping fix but I need to hide the ID column
		table.getColumnModel().getColumn(5).setPreferredWidth(0);
		table.getColumnModel().getColumn(2).setPreferredWidth(30);

		/*JFormatted setting for End Date TextField and Start Date TextField*/	
		MaskFormatter date_format;
		try {
			date_format = new MaskFormatter("##/##/####");
			start_date_textfield = new JFormattedTextField(date_format);
			end_date_textfield = new JFormattedTextField(date_format);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/* Start Date TextField*/
		JLabel start_Date_label = new JLabel("Start Date");
		start_Date_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		start_date_textfield.requestFocusInWindow();
		start_date_textfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(arg0.getKeyChar() == KeyEvent.VK_ENTER){
					end_date_textfield.requestFocus();
				}
			}
		});
				
		/*End Date TextField*/
		JLabel end_date_label = new JLabel("End Date");
		end_date_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		end_date_textfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(arg0.getKeyChar() == KeyEvent.VK_ENTER){
					postage_radio_button.requestFocus();
				}
			}
		});
		
		/*Postage Radio Button*/
		postage_radio_button = new JRadioButton("Postage");
		postage_radio_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(start_date_textfield.getText().contains(" ") || end_date_textfield.getText().contains(" ")){
					populateTable(postage_sales_db.getSales());
				}
				else{
					populateTableUponSearch(postage_sales_db);
				}
			}
		});
		postage_radio_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(arg0.getKeyChar() == KeyEvent.VK_ENTER){
					postage_radio_button.setSelected(true);
					if(start_date_textfield.getText().contains(" ") || end_date_textfield.getText().contains(" ")){
						populateTable(postage_sales_db.getSales());
					}
					else{
						populateTableUponSearch(postage_sales_db);
					}
				
				}
			}
		});
		postage_radio_button.setSelected(true);
		postage_radio_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		/*Stock Radio Button*/
		stock_radio_button = new JRadioButton("Stock");
		stock_radio_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(start_date_textfield.getText().contains(" ") || end_date_textfield.getText().contains(" ")){
					populateTable(stock_sales_db.getSales());
				}
				else{
					populateTableUponSearch(stock_sales_db);
				}
			}
		});
		stock_radio_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(arg0.getKeyChar() == KeyEvent.VK_ENTER){
					stock_radio_button.setSelected(true);
					
					if(start_date_textfield.getText().contains(" ") || end_date_textfield.getText().contains(" ")){
						populateTable(stock_sales_db.getSales());
					}
					else{
						populateTableUponSearch(stock_sales_db);
					}
					
				}				
			}
		});
		stock_radio_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		/*ButtonGroup for Stock Radio Button and Postage Radio Button*/
		ButtonGroup button_group = new ButtonGroup();
		button_group.add(stock_radio_button);
		button_group.add(postage_radio_button);
		
		/*Search Button*/
		search_button = new JButton("Search");
		search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(postage_radio_button.isSelected()){
					populateTableUponSearch(postage_sales_db);
				}
				else{
					populateTableUponSearch(stock_sales_db);
				}

			}
		});
		search_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				
				if(arg0.getKeyChar() == KeyEvent.VK_ENTER){
					if(postage_radio_button.isSelected()){
						populateTableUponSearch(postage_sales_db);
					}
					else{
						populateTableUponSearch(stock_sales_db);
					}
					
				}
		
			}
		});
		search_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JScrollPane scrollPane = new JScrollPane((Component) null);
		
		/*Delete Button*/
		delete_button = new JButton("Delete");
		delete_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(arg0.getKeyChar() == KeyEvent.VK_ENTER){
					if(postage_radio_button.isSelected()){
						if(model.getDataVector().size() < postage_sales_db.getSales().size() ){
							deleteSelectedItems(postage_sales_db);
							populateTableUponSearch(postage_sales_db);
						}
						else{
							deleteSelectedItems(postage_sales_db);
							populateTable(postage_sales_db.getSales());
						}
						
					}
					else{
						if(model.getDataVector().size() < stock_sales_db.getSales().size() ){
							deleteSelectedItems(stock_sales_db);
							populateTableUponSearch(stock_sales_db);
						}
						else{
							deleteSelectedItems(stock_sales_db);
							populateTable(stock_sales_db.getSales());
						}
						
					}
					
				}
			}
		});
		delete_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(postage_radio_button.isSelected()){
					if(model.getDataVector().size() < postage_sales_db.getSales().size() ){
						deleteSelectedItems(postage_sales_db);
						populateTableUponSearch(postage_sales_db);
					}
					else{
						deleteSelectedItems(postage_sales_db);
						populateTable(postage_sales_db.getSales());
					}
					
				}
				else{
					if(model.getDataVector().size() < stock_sales_db.getSales().size() ){
						deleteSelectedItems(stock_sales_db);
						populateTableUponSearch(stock_sales_db);
					}
					else{
						deleteSelectedItems(stock_sales_db);
						populateTable(stock_sales_db.getSales());
					}
					
				}
				
					
			}
		});
		delete_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		/*Populates the table, first determines what data to display*/
		if(postage_radio_button.isSelected()){
			populateTable(postage_sales_db.getSales());
		}
		else{
			populateTable(stock_sales_db.getSales());
		}
		
		/*Layout*/
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(end_date_label, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
								.addComponent(start_Date_label))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(start_date_textfield, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
								.addComponent(end_date_textfield, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(8)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(stock_radio_button)
								.addComponent(postage_radio_button)))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(28)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(delete_button, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(search_button, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))))
					.addGap(32))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(57)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(start_date_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(start_Date_label))
							.addGap(18)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(end_date_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(end_date_label))
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(postage_radio_button)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(stock_radio_button)
							.addGap(18)
							.addComponent(search_button)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(delete_button)
							.addGap(16))
						.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
							.addGap(25)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(46, Short.MAX_VALUE))
		);
		

		contentPanel.setLayout(gl_contentPanel);
		scrollPane.setViewportView(table);
	}
	
	/**
	 * Populates the table with sale data based on its database
	 * @param An sales database
	 */
	public void populateTable(ArrayList<Sale> list){
		int number_items = list.size();
		model.getDataVector().removeAllElements();
		for(int i= number_items-1;  i > -1  ;i--){
			String[]new_data = new String[6];
			new_data[0] =  list.get(i).getChargeNumber();
			new_data[1] =  list.get(i).getSaleItem();
			new_data[2] =  list.get(i).getSaleQuanity();
			new_data[3] =  list.get(i).getTotalPrice();
			new_data[4] =  list.get(i).getDateOfSale();
			new_data[5] =  list.get(i).getID();
			model.addRow(new_data);
		}
		model.getDataVector().setSize(number_items);
		model.fireTableDataChanged();
	}
	
	/**
	 * Populates the table with sale data based on its database upon a search
	 * @param An sales database
	 */
	public boolean populateTableUponSearch(SaleDatabaseAccessor db){
		if(start_date_textfield.getText().length() != 10 || start_date_textfield.getText().contains(" ")){
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Provide valid start date.");
			return false;
		}
		if(end_date_textfield.getText().length() != 10 || end_date_textfield.getText().contains(" ")){
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Provide valid end date.");
			return false;
		}
		
		
		ArrayList<Sale> filtered_list = filterSalesByDate(start_date_textfield.getText(), end_date_textfield.getText(), db);
		for(int i =0; i<filtered_list.size();i++){
			filtered_list.get(i).getChargeNumber();
		}
		populateTable(filtered_list);
		return true;
	}
	
	/**
	 * Takes and ArrayList of Sale Objects and dates, then returns all Sales made within the dates specified 
	 * @param A start date String
	 * @param A end date String
	 * @param A Sales Database
	 * @return A filtered ArrayList of Sale Objects of sales made between the specified dates
	 */
	public ArrayList<Sale> filterSalesByDate(String start_date, String end_date, SaleDatabaseAccessor db){
		String start_day = start_date.substring(0,2);
		String start_month = start_date.substring(3,5);
		String start_year = start_date.substring(6);
		Integer start = Integer.parseInt(start_year+start_month+start_day);
		
		String end_day = end_date.substring(0,2);
		String end_month = end_date.substring(3,5);
		String end_year = end_date.substring(6);
		Integer end = Integer.parseInt(end_year+end_month+end_day);
		
		ArrayList<Sale> return_list = new ArrayList<Sale>();
		ArrayList<Sale> list = db.getSales();
		for(int i=0 ; i<list.size();i++){
			String string_date = list.get(i).getDateOfSale();
			Integer int_date = Integer.parseInt( string_date.substring(6) + string_date.substring(3,5) + string_date.substring(0,2) );
			if(int_date >= start && int_date <= end ){
				return_list.add(list.get(i));
			}
		}
		
		return return_list;
	}

	/**
	 * Deletes the Sales objects selected in the JTable
	 * @param A sales inventory database
	 * @return True if the deletion was successful, False otherwise
	 */
	public boolean deleteSelectedItems(SaleDatabaseAccessor db){
		if(table.getSelectedRows().length == 0){
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Select at least one item to delete.");
			return false;
		}
		
		int[] indices_list = table.getSelectedRows();
		for(int i =0; i<indices_list.length; i++){
			String id_val = (String) table.getValueAt(indices_list[i], 5);
			for(int j = 0; j < db.getSales().size(); j++){
				if(id_val.equals(db.getSales().get(j).getID())){
					db.getSales().remove(j);
				}
			}
			
		}
		
		db.setSales(db.getSales());
		db.writeToFile(db.getSales());
		
		return true;
	}
	
}
