package org.team3236.contours;
/**
 * @author: Eric Bernard
 * 
 */

import java.util.ArrayList;

import org.team3236.contours.Contour;

public class ContourPair {
	private ArrayList<Contour> pair = new ArrayList<Contour>();

	public ContourPair() {}
	public ContourPair(Contour... contours) {
		for (int i=0; i<contours.length; i++) {
			this.pair.add(contours[i]);
		}
	}

	public void add(Contour contour) {
		pair.add(contour);
	}

	public Contour getContour(int index) {
		return pair.get(index);
	}
	public ArrayList<Contour> getContours() {
		return pair;
	}
}