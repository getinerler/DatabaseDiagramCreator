package main.application.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.application.model.ConstraintPoints;
import main.application.model.Database;
import main.application.view.DrawingPanel;

public class DrawingPanelMouseListener implements MouseListener {
	private DrawingPanel panel;
	private Database model;

	public DrawingPanelMouseListener(
			DrawingPanel panel, 
			Database model) {
		this.panel = panel;
		this.model = model;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		ConstraintPoints newSelected = null;
		for(ConstraintPoints points: model.getConstraintLines()) {
			if(points.isHovered()) {
				newSelected = points;
				points.setSelected(true);
				points.setHovered(false);
			}
		}
		for(ConstraintPoints points: model.getConstraintLines()) {
			if(points != newSelected) {
				points.setSelected(false);
			}		
		}
		panel.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}