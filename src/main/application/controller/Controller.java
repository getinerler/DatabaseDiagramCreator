package main.application.controller;

import java.awt.Dimension;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import main.application.drawing.Constants;
import main.application.model.Constraint;
import main.application.model.Database;
import main.application.model.Drawing;
import main.application.model.Table;
import main.application.view.View;
import main.data.models.TablesRequest;
import main.service.SqlService;
import main.service.models.GroupedDatabase;

public class Controller {
	private SqlService service;
	private Drawing model;
	private Database filteredModel;
	private View view; 

	public Controller(View view) {
		service = new SqlService();
		this.view = view;
		model = new Drawing();
		filteredModel = new Database();
	} 

	public void initController() {
		try {
			view.setBusyCursor();
			tryInit();
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(
					view.getPropertiesPanel(), exception.getMessage());
		} finally {
			view.setDefaultCursor();
		}
	}

	public void getDatabases() {	
		try {
			view.setBusyCursor();
			tryGetDatabases();
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(
					view.getPropertiesPanel(), exception.getMessage());
		} finally {
			view.setDefaultCursor();
		}
	}	

	public void createTable() {	
		try {
			view.setBusyCursor();
			tryCreatetable();
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(
					view.getPropertiesPanel(), exception.getMessage());
		} finally {
			view.setDefaultCursor();
		}
	}	

	public void updateTable() {	
		try {
			view.setBusyCursor();
			tryUpdateDatabase();
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(
					view.getPropertiesPanel(), exception.getMessage());
		} finally {
			view.setDefaultCursor();
		}
	}

	private void tryCreatetable() throws Exception {
		TablesRequest request = view.getTableRequest();
		checkRequest(request);
		GroupedDatabase database = service.getSqlTableModel(request);
		Database databaseModel = Mapping.mapDatabase(database);

		view.setTableList(databaseModel);
		model.setDatabase(Mapping.mapDatabase(database));
		drawDatabase();	
	}

	private void tryUpdateDatabase() {
		drawDatabase();
	}

	private void tryGetDatabases() throws Exception {
		view.hideLegend();
		view.hideTableList();
		TablesRequest request = view.getTableRequest();
		checkRequest(request);
		List<String> databases = service.getTables(request);
		view.setDatabases(databases);
		view.showDatabaseList();
		view.showCreateDiagramButton();
	}

	private void drawDatabase() {
		List<String> selectedTables = view.getCheckedTableList();

		List<Table> filteredTables = 
				model.getDatabase().getTables()
				.stream()
				.filter(table -> selectedTables.contains(table.getName()))
				.collect(Collectors.toList());  

		List<Constraint> filteredConstraints = 
				model.getDatabase().getConstraints()
				.stream()
				.filter(constraint -> constraint
						.isConnectedToTable(selectedTables))
				.collect(Collectors.toList());

		filteredModel.setTables(filteredTables);
		filteredModel.setConstraints(filteredConstraints);

		view.drawDatabase(filteredModel);	
		view.getDrawingPanel().setPreferredSize(
				new Dimension(Constants.DRAWING_PANEL_WIDTH, 1500));

		view.showLegend();
		view.showTableList();
		//Update scroll
		view.getDrawingPanel().revalidate();
	}

	private void selectAllTables() {
		view.selectAllTables();
		drawDatabase();
	}

	private void deselectAllTables() {
		view.deselectAllTables();
		drawDatabase();
	}

	private void tryInit() {
		view.hideLegend();
		view.hideDatabaseList();
		view.hideTableList();
		view.hideCreateDiagramButton();   
		view.getDrawingPanel()
			.addMouseMotionListener(
				new DrawingPanelMouseMotionListener(
						view.getDrawingPanel(), 
						filteredModel));
		view.getDrawingPanel()
			.addMouseListener(
				new DrawingPanelMouseListener(
						view.getDrawingPanel(), 
						filteredModel));
		view.getPropertiesPanel()
			.getCreateButton()
			.addActionListener(e -> createTable());		
		view.getCheckboxList()
			.addListSelectionListener(e -> updateTable());
		view.getCheckboxList()
			.addPropertyChangeListener(e -> updateTable());
		view.getDatabaseButton()
			.addActionListener(e -> getDatabases());
		view.getSelectAllButton()
			.addActionListener(e -> selectAllTables());
		view.getSelectNoneButton()
			.addActionListener(e -> deselectAllTables());
	}
	
	private void checkRequest(TablesRequest request) throws Exception {
		if(isEmpty(request.getServer())) {
			throw new Exception("Name cannot be empty.");
		}
		if(isEmpty(request.getUsername())) {
			throw new Exception("Username cannot be empty.");
		}
	}
	
	private boolean isEmpty(String s) {
		if(s == null) {
			return true;
		}
		if(s.trim().equals("")) {
			return true;
		}
		return false;
	}
} 