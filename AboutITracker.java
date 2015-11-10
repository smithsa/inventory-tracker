/**
 * AboutITtacker.java
 * 
 * @author Sad'e N. Smith
 *
 * A JDialog that displays information on ITracker.
 *
 */

package iTracker;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class AboutITracker extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public AboutITracker() {
		setTitle("About ITracker");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(AboutITracker.class.getResource("/iTracker/logo.png")));
		setBounds(100, 100, 339, 292);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel title_label = new JLabel("ITracker");
		title_label.setFont(new Font("Tahoma", Font.BOLD, 17));
		
		JLabel logo_label = new JLabel("");
		logo_label.setIcon(new ImageIcon(AboutITracker.class.getResource("/iTracker/logo.png")));
		
		JLabel lversion_labelblNewLabel = new JLabel("Version 1.0");
		lversion_labelblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel author_label = new JLabel("Author: Sad'e N. Smith");
		author_label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel email_label = new JLabel("smithsade13@gmail.com");
		email_label.setFont(new Font("Tahoma", Font.PLAIN, 10));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(122)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(title_label)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGap(10)
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(logo_label)
										.addComponent(lversion_labelblNewLabel, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)))))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(39)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(100)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(email_label)
								.addComponent(author_label))))
					.addContainerGap(32, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(title_label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lversion_labelblNewLabel)
					.addGap(1)
					.addComponent(logo_label, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(author_label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(email_label)
					.addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JTextPane txtpnThisProgramIs = new JTextPane();
		txtpnThisProgramIs.setText("This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.");
		txtpnThisProgramIs.setEditable(false);
		scrollPane.setViewportView(txtpnThisProgramIs);
		contentPanel.setLayout(gl_contentPanel);
	}
}
