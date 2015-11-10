/**
 * InventoryReport.java
 * 
 * @author Sad'e N. Smith
 *
 * A JDialog that allows the user to view data on the inventory, and allows them to export the data.
 *
 */
package iTracker;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

public class InventoryReport extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private String[] table_headers;
	private JButton export_button ;
	private JRadioButton postage_radio_button;
	private JRadioButton stock_radio_button;
	
	/**
	 * Create the dialog.
	 * @param Inventory Database for postage inventory
	 * @param Inventory  Database for stock inventory
	 */
	public InventoryReport(final InventoryDatabaseAccessor postage_db, final InventoryDatabaseAccessor stock_db) {
		setTitle("Inventory Report");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(InventoryReport.class.getResource("/iTracker/logo.png")));
		setBounds(100, 100, 470, 389);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JScrollPane scrollPane = new JScrollPane();
		
		/*Export Button*/
		export_button = new JButton("Export");
		export_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createPdf();
			}
		});
		export_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		/*Postage Radio Button*/
		postage_radio_button = new JRadioButton("Postage");
		postage_radio_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				populateTable(postage_db);
			}
		});
		postage_radio_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(arg0.getKeyChar() == KeyEvent.VK_ENTER){
					postage_radio_button.setSelected(true);
					stock_radio_button.requestFocus();
					populateTable(postage_db);
				}
			}
		});
		postage_radio_button.setSelected(true);
		postage_radio_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		/*Stock Radio Button*/
		stock_radio_button = new JRadioButton("Stock");
		stock_radio_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				populateTable(stock_db);
			}
		});
		stock_radio_button.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(arg0.getKeyChar() == KeyEvent.VK_ENTER){
					stock_radio_button.setSelected(true);
					populateTable(stock_db);
				}
			}
		});
		stock_radio_button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		/*ButtonGroup for the Postage Radio Button and Stock Radio Button*/
		ButtonGroup button_group = new ButtonGroup();
		button_group.add(postage_radio_button);
		button_group.add(stock_radio_button);
		
		/*Table and Table Model*/
		table_headers = new String[] {"Inventory Item", "Count", "Total Value ($)"};
		model = new DefaultTableModel(new String[][]{}, table_headers) {
			private static final long serialVersionUID = 1L;
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		
		table = new JTable();
		table.setFont(new Font("Tahoma", table.getFont().getStyle(), table.getFont().getSize()));
		table.setEnabled(false);
		table.setRowSelectionAllowed(false);
		table.setModel(model);
		table.getColumnModel().getColumn(1).setMaxWidth(100);
		scrollPane.setViewportView(table);
		
		/*Populates the table on the open of the dialog, determining what database to use*/
		if(postage_radio_button.isSelected()){
			populateTable(postage_db);
		}
		else{
			populateTable(stock_db);
		}
		
		/*Layout*/
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(37)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 293, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(stock_radio_button, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
						.addComponent(postage_radio_button)
						.addComponent(export_button))
					.addContainerGap(46, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(95)
							.addComponent(postage_radio_button)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(stock_radio_button)
							.addGap(89)
							.addComponent(export_button))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(25)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(38, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
	}
	
	/**
	 * Populates the table with inventory data based on its database
	 * @param An inventory database
	 */
	public void populateTable(InventoryDatabaseAccessor db){
		model.getDataVector().removeAllElements();
		ArrayList<String> items_string_list = new ArrayList<String>(Arrays.asList(db.getOnlyInventoryItemList(db.getInventory())));
		Collections.sort(items_string_list);
		int number_items = items_string_list.size();
		for(int i= 0;  i < number_items ;i++){
			String[]new_data = new String[3];
			Inventory cur_inventory = db.searchInventoryListByItem(items_string_list.get(i), db.getInventory());
			new_data[0] =  cur_inventory.getInventoryItem();
			new_data[1] =  cur_inventory.getInventoryCount();
			Integer count =  Integer.parseInt(cur_inventory.getInventoryCount());
			float price = Float.parseFloat(cur_inventory.getInventoryPrice());
			double total =  Math.round((price* count)* 100.0)/100.0;
			String total_price = Double.toString(total);
			if(total_price.startsWith(".")){
				total_price = "0"+ total_price;
			}
			
			if((total_price.startsWith(".") && total_price.length() == 2)
					|| (total_price.length() == 3 && total_price.charAt(1) == '.')
					|| (total_price.length() == 4 && total_price.charAt(2) == '.')){
				total_price = total_price + "0"; 
			}
			if(!total_price.startsWith(".") && !total_price.contains(".")){
				total_price = total_price + ".00"; 
			}
			new_data[2] = total_price; 
			model.addRow(new_data);
		}
		model.getDataVector().setSize(number_items);
		model.fireTableDataChanged();
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
				       PdfWriter.getInstance(document,new FileOutputStream(System.getProperty("user.dir").replaceAll("\\/", "/") + "/Postage Inventory Report.pdf"));
				   }else{
					   PdfWriter.getInstance(document,new FileOutputStream(System.getProperty("user.dir").replaceAll("\\/", "/") + "/Stock Inventory Report.pdf"));
				   }
				       document.open();
				   
					
			       if(postage_radio_button.isSelected()){
						document.add(new Paragraph("Inventory Report: Postage"));
					}else{
						document.add(new Paragraph("Inventory Report: Postage"));
					}
			       
			       document.add(new Paragraph(" "));
			       
			       PdfPTable tab=new PdfPTable(3);
			       tab.addCell("Inventory Item");
			       tab.addCell("Count");
			       tab.addCell("Total Value ($)");
			for(int i=0;i<count;i++){
			Object obj1 = GetData(table, i, 0);
			Object obj2 = GetData(table, i, 1);
			Object obj3 = GetData(table, i, 2);
			String value1=obj1.toString();
			String value2=obj2.toString();
			String value3=obj3.toString();
			tab.addCell(value1);
			tab.addCell(value2);
			tab.addCell(value3);
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
	    	    		myFile = new File(System.getProperty("user.dir").replaceAll("\\/", "/")  + "/Postage Inventory Report.pdf");
	    	    	}else{
	    	    		myFile = new File(System.getProperty("user.dir").replaceAll("\\/", "/")  + "/Stock Inventory Report.pdf");
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