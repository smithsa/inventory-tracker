/**
 * SalesReportDetailed.java
 * 
 * @author Sad'e N. Smith
 *
 * Produces a table that displays the sale history breakdown in a JDialog. User has the option to export
 * the table to by pressing the export button.
 * 
 **/

package iTracker;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SalesReportDetailed extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private String[] table_headers;
	private DefaultTableModel model;
	private JButton search_button;
	private JButton export_button;
	private JCheckBox department_charges_checkbox;
	private JFormattedTextField end_date_textfield;
	private JFormattedTextField start_date_textfield ;
	private JRadioButton postage_radio_button;
	private JRadioButton stock_radio_button;
	private JCheckBox personal_charges_checkbox;

	/**
	 * Create the dialog.
	 * @param Sales Database for postage inventory
	 * @param Sales Database for stock inventory
	 */
	public SalesReportDetailed(final SaleDatabaseAccessor postage_sales_db, final SaleDatabaseAccessor stock_sales_db) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SalesReportDetailed.class.getResource("/iTracker/logo.png")));
		setTitle("Sales Report: Detailed");
		setResizable(false);
		setBounds(100, 100, 686, 398);
		
		/*JFormatted setting for End Date TextField and Start Date TextField*/	
		try {
			MaskFormatter date_format = new MaskFormatter("##/##/####");
			end_date_textfield = new JFormattedTextField(date_format);
			start_date_textfield = new JFormattedTextField(date_format);

		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		/* Start Date TextField*/
		start_date_textfield.requestFocusInWindow();
		start_date_textfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER){
					end_date_textfield.requestFocus();
				}
			}
		});
		
		/*End Date TextField*/
		JLabel end_date_label = new JLabel("End Date");
		end_date_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		end_date_textfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER){
					postage_radio_button.requestFocus();
				}
			}
		});
		
		
		JLabel start_date_label = new JLabel("Start Date");
		start_date_label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		/*Postage Radio Button*/
		postage_radio_button = new JRadioButton("Postage");
		postage_radio_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER){
					postage_radio_button.setSelected(true);
					populateUponActionRadioButton(postage_sales_db);
				}
			}
		});
		postage_radio_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				populateUponActionRadioButton(postage_sales_db);
			}
		});
		postage_radio_button.setSelected(true);
		postage_radio_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		/*Stock Radio Button*/
		stock_radio_button = new JRadioButton("Stock");
		stock_radio_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER){
					stock_radio_button.setSelected(true);
					populateUponActionRadioButton(stock_sales_db);
				}
			}
		});
		stock_radio_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				populateUponActionRadioButton(stock_sales_db);
			}
		});
		stock_radio_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		/*ButtonGroup for Stock Radio Button and Postage Radio Button*/
		ButtonGroup button_group = new ButtonGroup();
		button_group.add(stock_radio_button);
		button_group.add(postage_radio_button);
		
		/*Personal Charges CheckBox*/
		personal_charges_checkbox = new JCheckBox("Personal Charges");
		personal_charges_checkbox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER){
					if(personal_charges_checkbox.isSelected()){
						personal_charges_checkbox.setSelected(false);
					}else{
						personal_charges_checkbox.setSelected(true);
					}
					
					populateUponActionCheckButton(postage_sales_db, stock_sales_db);
				}
			}
		});
		personal_charges_checkbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				populateUponActionCheckButton(postage_sales_db, stock_sales_db);
			}
		});
		personal_charges_checkbox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		/*Department Charges CheckBox*/
		department_charges_checkbox = new JCheckBox("Department Charges");
		department_charges_checkbox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER){
					if(department_charges_checkbox.isSelected()){
						department_charges_checkbox.setSelected(false);
					}else{
						department_charges_checkbox.setSelected(true);
					}
					
					populateUponActionCheckButton(postage_sales_db, stock_sales_db);
				}
			}
		});
		department_charges_checkbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				populateUponActionCheckButton(postage_sales_db, stock_sales_db);
			}
		});
		department_charges_checkbox.setSelected(true);
		department_charges_checkbox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		/*Search Button*/
		search_button = new JButton("Search");
		search_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER){
					if(postage_radio_button.isSelected()){
						populateTableUponSearch(postage_sales_db);
					}else{
						populateTableUponSearch(stock_sales_db);
					}
				}
			}
		});
		search_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(postage_radio_button.isSelected()){
					populateTableUponSearch(postage_sales_db);
				}else{
					populateTableUponSearch(stock_sales_db);
				}
			}
		});
		search_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		/*Export Button*/
		export_button = new JButton("Export");
		export_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER){
					createPdf();
				}
			}
		});
		export_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createPdf();
			}
		});
		export_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		/*JTable for data display*/
		JScrollPane scrollPane = new JScrollPane();		
		table = new JTable();
		table.setEnabled(false);
		table.setFont(new Font("Tahoma", table.getFont().getStyle(), table.getFont().getSize()));
		scrollPane.setViewportView(table);
		
		table_headers = new String[] {"Charge #","Sale Item","Quantity", "Total Charge ($)"};
		model = new DefaultTableModel(new String[][]{}, table_headers) {
			private static final long serialVersionUID = 1L;
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		table.setModel(model);
		
		/*Populates the table, first determines what data to display*/
		if(postage_radio_button.isSelected()){
			populateTable(postage_sales_db.getSales());
		}else{
			populateTable(stock_sales_db.getSales());
		}
		
		/*Layout*/
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(35)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(start_date_label, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(start_date_textfield, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(end_date_label, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(end_date_textfield, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
						.addComponent(postage_radio_button, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
						.addComponent(stock_radio_button, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addComponent(personal_charges_checkbox, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
						.addComponent(department_charges_checkbox, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(search_button, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(export_button, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)))
					.addGap(46))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(50)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(start_date_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(2)
									.addComponent(start_date_label, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(2)
									.addComponent(end_date_label, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
								.addComponent(end_date_textfield, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(postage_radio_button)
							.addGap(3)
							.addComponent(stock_radio_button)
							.addGap(18)
							.addComponent(personal_charges_checkbox)
							.addGap(3)
							.addComponent(department_charges_checkbox)
							.addGap(44)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(export_button)
								.addComponent(search_button)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(37)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 294, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(39, Short.MAX_VALUE))
		);
		
		getContentPane().setLayout(groupLayout);
	}
	
	/**
	 * Populates the table for check box actions performed
	 * @param Sales Database for postage inventory
	 * @param Sales Database for stock inventory
	 */
	public void populateUponActionCheckButton(SaleDatabaseAccessor postage_sales_db, SaleDatabaseAccessor stock_sales_db){
		if(start_date_textfield.getText().contains(" ") || end_date_textfield.getText().contains(" ")){
			if(postage_radio_button.isSelected()){
				populateTable(postage_sales_db.getSales());
			}else{
				populateTable(stock_sales_db.getSales());
			}
		}else{
			if(postage_radio_button.isSelected()){
				populateTableUponSearch(postage_sales_db);
			}else{
				populateTableUponSearch(stock_sales_db);
			}
		}
	}
	
	/**
	 * Populates the table for radio button actions performed
	 * @param Sales Database 
	 */
	public void populateUponActionRadioButton(SaleDatabaseAccessor db){
		if(start_date_textfield.getText().contains(" ") || end_date_textfield.getText().contains(" ")){
			populateTable(db.getSales());
		}else{
			populateTableUponSearch(db);
		}
	}
	
	/**
	 *  Filters given sales based on their type(postage, stock)
	 * @param A ArrayList of Sale Objects
	 * @return A ArrayList of filtered Sale Objects  based on their type(postage, stock)
	 */
	public ArrayList<Sale> getSaleItemList(ArrayList<Sale> list){
		if(personal_charges_checkbox.isSelected() && department_charges_checkbox.isSelected()){
			return list;
		}
		else if(department_charges_checkbox.isSelected()){
			return  getDepartmentChargeSales(list);
		}
		else if(personal_charges_checkbox.isSelected()){
			return getPersonalChargeSales(list);
		}
		else if(!personal_charges_checkbox.isSelected() && !department_charges_checkbox.isSelected()){
			return new ArrayList<Sale>();
		}
		
		return list;
	}
	
	/**
	 * Filters a given list to give all personal charges
	 * @param An ArrayList of Sale objects
	 * @return An ArrayList of Sale Objects with personal charges
	 */
	public ArrayList<Sale> getPersonalChargeSales(ArrayList<Sale> list){
		ArrayList<Sale> personal_charge_sales_list = new ArrayList<Sale>();
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getChargeNumber().equals("99999")){
				 personal_charge_sales_list.add(list.get(i));
			}
		}
		return personal_charge_sales_list;
	}
	
	/**
	 * Filters a given list to give all personal charges
	 * @param An ArrayList of Sale objects
	 * @return An ArrayList of Sale Objects with department charges
	 */
	public ArrayList<Sale> getDepartmentChargeSales(ArrayList<Sale> list){
		ArrayList<Sale> department_charge_sales_list = new ArrayList<Sale>();
		for(int i = 0; i < list.size(); i++){
			if(!list.get(i).getChargeNumber().equals("99999")){
				department_charge_sales_list.add(list.get(i));
			}
		}
		return department_charge_sales_list;
		
	}
	
	/**
	 * Gets a String ArrayList of all charge numbers in a given ArrayList of Sale Objects
	 * @param ArrayList of Sale Objects
	 * @return ArrayList of Strings which are charge numbers
	 */
	public ArrayList<String> getChargeNumberList(ArrayList<Sale> sales_list){
		ArrayList<String> return_list = new ArrayList<String>();
		for(int i=0; i < sales_list.size();i++){
			if(!return_list.contains(sales_list.get(i).getChargeNumber())){
				return_list.add(sales_list.get(i).getChargeNumber());
			}
		}
		return return_list;
	}
	
	/**
	 * Populates the table with sale data based on its database
	 * @param An sales database
	 */
	public void populateTable(ArrayList<Sale> sales_list){ 
		sales_list = getSaleItemList(sales_list);
		int number_items = sales_list.size();
		ArrayList<String> charge_number_list = getChargeNumberList(sales_list);
		ArrayList<String[]> list_of_new_data = new ArrayList<String[]>();
		
		model.getDataVector().removeAllElements();
		for(int i=0;  i < number_items  ;i++){
			Sale cur_item = sales_list.get(i);
			String[] new_data = new String[4];
			new_data[0] = cur_item.getChargeNumber();
			new_data[1] = cur_item.getSaleItem();
			new_data[2] =  cur_item.getSaleQuanity();
			new_data[3] =  cur_item.getTotalPrice();
			list_of_new_data.add(new_data);
		}
		
		for(int j = 0; j < charge_number_list.size(); j++){
			for(int k = 0; k < list_of_new_data.size(); k++){
				if(charge_number_list.get(j).equals(list_of_new_data.get(k)[0])){
					model.addRow(list_of_new_data.get(k));
				}
			}
		}
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
	 * Creates,saves, and opens a pdf based on the information currently in table
	 */
	public void createPdf() {
		/*Code snippet from: http://www.roseindia.net/answers/viewqa/Java-Beginners/27321-CONVERT-JTable-DATA-TO-PDF-FILE.html*/  
		try{
			int count=table.getRowCount();
			Document document=new Document();
				   Date date = new Date();
				   if(postage_radio_button.isSelected()){
				       PdfWriter.getInstance(document,new FileOutputStream(System.getProperty("user.dir").replaceAll("\\/", "/") + "/Postage Sales Report Details.pdf"));
				   }else{
					   PdfWriter.getInstance(document,new FileOutputStream(System.getProperty("user.dir").replaceAll("\\/", "/") + "/Stock Sales Report Details.pdf.pdf"));
				   }
				       document.open();
				   
					
			       if(postage_radio_button.isSelected()){
						document.add(new Paragraph("Sales Report Details: Postage"));
					}else{
						document.add(new Paragraph("Sales Report Details: Stock"));
					}
			       
			       document.add(new Paragraph(" "));
			       
			       if(department_charges_checkbox.isSelected() && personal_charges_checkbox.isSelected()){
			    	   document.add(new Paragraph("Department and Personal Charges"));
			       }else if(department_charges_checkbox.isSelected() && !personal_charges_checkbox.isSelected()){
			    	   document.add(new Paragraph("Department Charges"));
			       }else if(!department_charges_checkbox.isSelected() && personal_charges_checkbox.isSelected()){
			    	   document.add(new Paragraph("Personal Charges"));
			       }
			       
			       document.add(new Paragraph(" "));
			       
			       PdfPTable tab=new PdfPTable(4);
			       tab.addCell("Charge #");
			       tab.addCell("Sale Item");
			       tab.addCell("Quantity");
			       tab.addCell("Total Charge ($)");
			for(int i=0;i<count;i++){
			Object obj1 = GetData(table, i, 0);
			Object obj2 = GetData(table, i, 1);
			Object obj3 = GetData(table, i, 2);
			Object obj4 = GetData(table, i, 3);
			String value1=obj1.toString();
			String value2=obj2.toString();
			String value3=obj3.toString();
			String value4=obj4.toString();
			tab.addCell(value1);
			tab.addCell(value2);
			tab.addCell(value3);
			tab.addCell(value4);
			}
			document.add(tab);
			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));
			document.add(new Paragraph(date.toString()));
			document.close();
		}catch(Exception e){
			    	e.printStackTrace();
			    }
		
		      
	      if (Desktop.isDesktopSupported()) {
	    	    try {
	    	    	File myFile = null;
	    	    	if(postage_radio_button.isSelected()){
	    	    		myFile = new File(System.getProperty("user.dir").replaceAll("\\/", "/")  + "/Postage Sales Report Details.pdf");
	    	    	}else{
	    	    		myFile = new File(System.getProperty("user.dir").replaceAll("\\/", "/")  + "/Stock Sales Report Details.pdf");
	    	    	}
	    	        
	    	        Desktop.getDesktop().open(myFile);
	    	    } catch (IOException ex) {
	    	        ex.printStackTrace();
	    	    }
	      }

	}

	/**
	 * Gets object in given table from given row and index
	 * @param a table
	 * @param an index of a row
	 * @param an index of a column
	 * @return an object that is at the specified indices
	 */
	public Object GetData(JTable table, int row_index, int col_index){
		return table.getModel().getValueAt(row_index, col_index);
	}

}
