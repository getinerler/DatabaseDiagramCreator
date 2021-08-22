package main.application.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import main.application.model.Database;
import main.data.models.TablesRequest;

public class MenuPanel extends JPanel {

	private ParametersPanel parametersPanel;
	private TableListPanel tableListPanel;
	private LegendPanel legendPanel;
	
	public MenuPanel() {	
		setPreferredSize(new Dimension(300, 600));
		parametersPanel = new ParametersPanel();
		tableListPanel = new TableListPanel();
		legendPanel = new LegendPanel();
		
		add(parametersPanel, BorderLayout.PAGE_START);
		add(tableListPanel, BorderLayout.CENTER);
		add(legendPanel, BorderLayout.PAGE_END);
	}
	
	public TableList getTableListPanel() {
		return tableListPanel.getTableList();
	}
	
	public TablesRequest getTableRequest() {
		return parametersPanel.getTableRequest();
	}

	public JButton getDatabaseButton() {
		return parametersPanel.getDatabaseButton();
	}
	
	public JButton getSelectAllButton() {
		return tableListPanel.getSelectAllButton();
	}
	
	public JButton getSelectNoneButton() {
		return tableListPanel.getSelectNoneButton();
	}
	
	public JButton getCreateButton() {
		return parametersPanel.getCreateButton();
	}
	
	public ParametersPanel getConnectionInfoPanel() {
		return this.parametersPanel;
	}
	
	public void setTableList(Database model) {
		tableListPanel.setTableList(model);
	}	
	
	public void setDatabases(List<String> databases) {
		parametersPanel.setDatabases(databases);
	}

	public List<String> getCheckedTableList() {
		return tableListPanel.getCheckedTableList();
	}
	
	public void selectAllTables() {
		tableListPanel.selectAllTables();
	}
	
	public void deselectAllTables() {
		tableListPanel.deselectAllTables();
	}
	
	public void showLegend() {
		legendPanel.setVisible(true);
	}
	
	public void hideLegend() {
		legendPanel.setVisible(false);
	}
	
	public void showTableList() {
		tableListPanel.setVisible(true);
	}

	public void hideTableList() {
		tableListPanel.setVisible(false);
	}
	
	public void showDatabaseList() {
		parametersPanel.showDatabaseList();
	}
	
	public void hideDatabaseList() {
		parametersPanel.hideDatabaseList();
	}
	
	public void showCreateDiagramButton() {
		parametersPanel.showCreateDiagramButton();
	}
	
	public void hideCreateDiagramButton() {
		parametersPanel.hideCreateDiagramButton();
	}
}
