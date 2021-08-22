package main.application.drawing;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class PointArrayCreator {
	private List<Point> points;
	private int tempX;
	private int tempY;
	
	public PointArrayCreator() {
		points = new ArrayList<Point>();
		tempX = 0;
		tempY = 0;
	}
	
	public int getCurrentX() {
		return this.tempX;
	}
	
	public int getCurrentY() {
		return this.tempY;
	}
	
	public void addWithNewX(int x) {
		tempX = x;
		points.add(new Point(x, tempY));
	}
	
	public void addWithAddedX(int x) {
		tempX += x;
		points.add(new Point(tempX, tempY));
	}
	
	public void addWithNewY(int y) {
		tempY = y;
		points.add(new Point(tempX, tempY));
	}
	
	public void addWithAddedY(int y) {
		tempY += y;
		points.add(new Point(tempX, tempY));
	}
	
	public void add(int x, int y) {
		tempX = x;
		tempY = y;
		points.add(new Point(x, y));
	}
	
	public Point[] getPoints() {
		Point[] array = new Point[points.size()];
		points.toArray(array);
		return array;
	}
}