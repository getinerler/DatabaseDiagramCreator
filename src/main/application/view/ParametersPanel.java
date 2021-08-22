package main.application.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import main.data.models.SqlType;
import main.data.models.TablesRequest;

public class ParametersPanel extends JPanel {
	private JTextField serverText;
	private JTextField usernameText;
	private JTextField portText;
	private JTextField passwordText;
	private JComboBox<String> type;
	private JComboBox<String> databases;
	private JButton databaseButton;
	private JButton createButton;
	
	public ParametersPanel() {
		setLayout(new GridBagLayout());
		setSize(new Dimension(300, 200));
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
	    c.weighty = 0;
	    c.gridx = 0;
	    c.gridy = 0;
	    c.ipadx = 0;
	    c.ipady = 0;
		serverText = new JTextField();
		usernameText = new JTextField();
		portText = new JTextField();
		passwordText = new JPasswordField();
		
		String[] types = { "MySql", "MsSql" };
		type = new JComboBox<String>(types);
		
		databaseButton = new JButton("List Databases");
		databaseButton.setPreferredSize(new Dimension(290, 30));
		createButton = new JButton("Create Diagram");
		databases = new JComboBox<String>();
		
		c.gridx = 0;
		c.gridy = 0;
        add(new JLabel("Server"), c);
        
		c.gridx = 1;
		c.gridy = 0;
        add(serverText, c);
        
		c.gridx = 0;
		c.gridy = 1;
        add(new JLabel("Port"), c);
        
		c.gridx = 1;
		c.gridy = 1;
        add(portText, c);
        
		c.gridx = 0;
		c.gridy = 2;
        add(new JLabel("Username"), c);
        
		c.gridx = 1;
		c.gridy = 2;
        add(usernameText, c);
        
		c.gridx = 0;
		c.gridy = 3;
        add(new JLabel("Password"), c);
        
		c.gridx = 1;
		c.gridy = 3;
        add(passwordText, c);
        
		c.gridx = 0;
		c.gridy = 4;
        add(new JLabel("Type"), c);
        
		c.gridx = 1;
		c.gridy = 4;
        add(type, c);
        
        c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
        add(databaseButton, c);
        
        c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2;
        add(databases, c);

		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 2;
        add(createButton, c);
	}
	
	public TablesRequest getTableRequest() {
		TablesRequest request = new TablesRequest();
		request.setServer(serverText.getText());
		request.setUsername(usernameText.getText());
		request.setPassword(passwordText.getText());
		request.setPort(portText.getText());
		request.setType(SqlType.valueOf((String) type.getSelectedItem()));	
		request.setDatabase((String) databases.getSelectedItem());	
		return request;
	}
	
	public void setDatabases(List<String> databasesList) {
		 DefaultComboBoxModel model = 
				 (DefaultComboBoxModel) databases.getModel();
	        model.removeAllElements();
	        for (String item : databasesList) {
	            model.addElement(item);
	        }
	        databases.setModel(model);
	}
	
	public JButton getDatabaseButton() {
		return databaseButton; 
	}
	
	public JButton getCreateButton() {
		return createButton; 
	}
	
	public void showCreateDiagramButton() {
		createButton.setVisible(true);
	}
	
	public void hideCreateDiagramButton() {
		createButton.setVisible(false);
	}
	
	public void showDatabaseList() {
		databases.setVisible(true);
	}
	
	public void hideDatabaseList() {
		databases.setVisible(false);
	}
}
