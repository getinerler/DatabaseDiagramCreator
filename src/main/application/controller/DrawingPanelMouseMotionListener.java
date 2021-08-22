package main.application.controller;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import main.application.drawing.Constants;
import main.application.model.ConstraintPoints;
import main.application.model.Database;
import main.application.view.DrawingPanel;

public class DrawingPanelMouseMotionListener implements MouseMotionListener {
	private DrawingPanel panel;
	private Database model;
	
	public DrawingPanelMouseMotionListener(
			DrawingPanel panel, 
			Database model) {
		this.panel = panel;
		this.model = model;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(model == null) {
			return;
		}
		if(model.getConstraintLines() == null) {
			return;
		}
		for (ConstraintPoints constraint: model.getConstraintLines()) {
			constraint.setHovered(false);
		}	
		for (ConstraintPoints constraint: model.getConstraintLines()) {
			boolean isOnConstraint = coordinateOnConstraint(
					e.getX(),
					e.getY(), 
					constraint.getPointsList());
			constraint.setHovered(isOnConstraint);
			if(isOnConstraint) {
				break;
			}
		}	
		panel.repaint();
	}

	private boolean coordinateOnConstraint(int x, int y, Point[] points) {
		int length = points.length;
		for(int i = 0; i < length - 1; i++) {
			if(i == length) {
				continue;
			}
			Point p1 = points[i];
			Point p2 = points[i + 1];
			
			int xSmaller = (p1.x >= p2.x ? p2.x : p1.x) - 
					Constants.LINE_HOVER_RANGE;
			int xGreater = (p1.x <= p2.x ? p2.x : p1.x) + 
					Constants.LINE_HOVER_RANGE;		
			int ySmaller = (p1.y >= p2.y ? p2.y : p1.y) - 
					Constants.LINE_HOVER_RANGE;
			int yGreater = (p1.y <= p2.y ? p2.y : p1.y) + 
					Constants.LINE_HOVER_RANGE;
			
			if(x < xSmaller || x > xGreater) {
				continue;
			}		
			if(y < ySmaller || y > yGreater) {
				continue;
			}
			return true;
		}
		return false;
	}
}