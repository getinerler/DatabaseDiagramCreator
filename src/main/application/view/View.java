package main.application.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import main.application.model.Database;
import main.data.models.TablesRequest;

public class View extends JPanel {
	private MenuPanel menuPanel;
	private DrawingPanel drawingPanel;
	private JFrame mainFrame;

	public View() {  
		menuPanel = new MenuPanel();
		drawingPanel = new DrawingPanel();

		mainFrame = new JFrame("Database Diagram Creator");
		mainFrame.setSize(1200, 600);
		mainFrame.setPreferredSize(new Dimension(1200, 600));
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainFrame.addWindowListener(new WindowAdapter() {	    	  
			public void windowClosing(WindowEvent windowEvent) {      	 
				System.exit(0);
			}         
		});    

		mainFrame.setLocationRelativeTo(null); 
		mainFrame.add(menuPanel, BorderLayout.LINE_START);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(drawingPanel);

		mainFrame.add(scrollPane, BorderLayout.CENTER);

		mainFrame.setVisible(true);  
		mainFrame.pack();

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				drawingPanel.repaint();
			}
		});
	}

	public TablesRequest getTableRequest() {
		return menuPanel.getTableRequest();
	}

	public DrawingPanel getDrawingPanel() {
		return drawingPanel;
	}

	public void setTableList(Database model) {
		menuPanel.setTableList(model);
	}

	public void setDatabases(List<String> databases) {
		menuPanel.setDatabases(databases);
	}

	public ParametersPanel getPropertiesPanel() {
		return menuPanel.getConnectionInfoPanel();
	}

	public TableList getCheckboxList() {
		return menuPanel.getTableListPanel();
	}

	public JButton getDatabaseButton() {
		return menuPanel.getDatabaseButton();
	}

	public JButton getSelectAllButton() {
		return menuPanel.getSelectAllButton();
	}

	public JButton getSelectNoneButton() {
		return menuPanel.getSelectNoneButton();
	}

	public List<String> getCheckedTableList() {
		return menuPanel.getCheckedTableList();
	}

	public void selectAllTables() {
		menuPanel.selectAllTables();
	}

	public void deselectAllTables() {
		menuPanel.deselectAllTables();
	}

	public void drawDatabase(Database model) {
		drawingPanel.drawDatabase(model);
	}

	public void showLegend() {
		menuPanel.showLegend();
	}

	public void hideLegend() {
		menuPanel.hideLegend();
	}

	public void showTableList() {
		menuPanel.showTableList();
	}

	public void hideTableList() {
		menuPanel.hideTableList();
	}

	public void showDatabaseList() {
		menuPanel.showDatabaseList();
	}

	public void hideDatabaseList() {
		menuPanel.hideDatabaseList();
	}

	public void showCreateDiagramButton() {
		menuPanel.showCreateDiagramButton();
	}

	public void hideCreateDiagramButton() {
		menuPanel.hideCreateDiagramButton();
	}

	public void setBusyCursor() {
		mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}

	public void setDefaultCursor() {
		mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
}
