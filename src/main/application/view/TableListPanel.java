package main.application.view;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import main.application.model.Database;
import main.application.model.Table;

public class TableListPanel extends JPanel  implements Scrollable {
	private TableList list;
	private DefaultListModel<JCheckBox> items;
	private JPanel buttonPanel;
	private JButton selectAllButton;
	private JButton selectNoneButton;
	
	public TableListPanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setPreferredSize(new Dimension(290, 295));  
		items = new DefaultListModel<JCheckBox>();
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.setPreferredSize(new Dimension(10, 30));
		selectNoneButton = new JButton("None");
		selectAllButton = new JButton("All");
		buttonPanel.add(selectNoneButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(160, 0)));
		buttonPanel.add(selectAllButton);
		    
	    list = new TableList(items);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(list);
	    list.setLayoutOrientation(JList.VERTICAL);
	   
	    add(buttonPanel);
		add(scrollPane);
	}
	
	public void setTableList(Database model) {
		items.removeAllElements();
		for (Table table: model.getTables()) {
			JCheckBox checkBox = new JCheckBox(table.getName());
			checkBox.setSelected(true);
			items.addElement(checkBox);
		}
		revalidate();
	}
	
	public TableList getTableList() {
		return list;
	}

	public JButton getSelectAllButton() {
		return selectAllButton;
	}
	
	public JButton getSelectNoneButton() {
		return selectNoneButton;
	}
	
	public void selectAllTables() {
		List<JCheckBox> list = Arrays.asList(items.toArray())
				.stream()
				.map(checkbox -> (JCheckBox)checkbox)
				.collect(Collectors.toList());
		for (JCheckBox checkbox: list){
			checkbox.setSelected(true);
		}
		repaint();
	}
	
	public void deselectAllTables() {
		List<JCheckBox> list = Arrays.asList(items.toArray())
				.stream()
				.map(checkbox -> (JCheckBox)checkbox)
				.collect(Collectors.toList());
		for (JCheckBox checkbox: list){
			checkbox.setSelected(false);
		}
		repaint();
	}
	
	public List<String> getCheckedTableList() {
		List<String> list = Arrays.asList(items.toArray())
				.stream()
				.filter(checkbox -> ((JCheckBox)checkbox).isSelected())
				.map(checkbox -> ((JCheckBox)checkbox).getText())
				.collect(Collectors.toList());
		return list;
	}

	public Dimension getPreferredScrollableViewportSize() {
	    return getPreferredSize();
	}

	public int getScrollableUnitIncrement(
			Rectangle visibleRect, 
			int orientation, 
			int direction) {
	    return 10;
	}

	public int getScrollableBlockIncrement(
			Rectangle visibleRect, 
			int orientation, 
			int direction) {
		return 
			(orientation == SwingConstants.VERTICAL ? 
				visibleRect.height :
					visibleRect.width
			 )
			-
			10;
	}

	public boolean getScrollableTracksViewportWidth() {
	    return true;
	}

	public boolean getScrollableTracksViewportHeight() {
	    return false;
	}
}
