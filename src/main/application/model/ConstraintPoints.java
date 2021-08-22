package main.application.model;

import java.awt.Point;

public class ConstraintPoints {
	private Point[] pointsList;
	private boolean hovered;
	private boolean selected;
	
	public Point[] getPointsList() {
		return pointsList;
	}

	public void setPointsList(Point[] pointsList) {
		this.pointsList = pointsList;
	}
	
	public boolean isHovered() {
		return hovered;
	}

	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}