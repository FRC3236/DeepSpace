package org.team3236.contours;
/**
 * @author: Eric Bernard
 * 
 */


public class Contour {
	private double x, y, width, height, area, ratio;

	public Contour(double x, double y, double width, double height, double area, double ratio) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.area = area;
		this.ratio = ratio;
	}

	public double get(int index) {
		double valToReturn = 0;
		switch(index) {
			case 0: {
				valToReturn = this.x;
				break;
			}
			case 1: {
				valToReturn = this.y;
				break;
			}
			case 2: {
				valToReturn = this.width;
				break;
			}
			case 3: {
				valToReturn = this.height;
				break;
			}
			case 4: {
				valToReturn = this.area;
				break;
			}
			case 5: {
				valToReturn = this.ratio;
				break;
			}
		}

		return valToReturn;
	}

	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
	}
	public double getWidth() {
		return this.width;
	}
	public double getHeight() {
		return this.height;
	}
	public double getArea() {
		return this.area;
	}
	public double getRatio() {
		return this.ratio;
	}
}