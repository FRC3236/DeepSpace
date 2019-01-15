/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.wpi.first.networktables.*;

/**
 * Add your docs here.
 */
public class VisionRocket extends Subsystem {
	@Override
	public void initDefaultCommand() {

	}

	private static NetworkTableInstance ntInst = NetworkTableInstance.getDefault();
	private static NetworkTable visionTable = ntInst.getTable("contours");

	public ArrayList<ArrayList<Double>> GetContours() {
		NetworkTableEntry ntwidths = visionTable.getEntry("width");
		NetworkTableEntry ntheights = visionTable.getEntry("height");
		NetworkTableEntry ntxs = visionTable.getEntry("x");
		NetworkTableEntry ntys = visionTable.getEntry("y");
		NetworkTableEntry ntratios = visionTable.getEntry("ratio");
		NetworkTableEntry ntareas = visionTable.getEntry("area");

		Number[] def = new Number[0];

		Number[] widths = ntwidths.getNumberArray(def);
		Number[] heights = ntheights.getNumberArray(def);
		Number[] xs = ntxs.getNumberArray(def);
		Number[] ys = ntys.getNumberArray(def);
		Number[] ratios = ntratios.getNumberArray(def);
		Number[] areas = ntareas.getNumberArray(def);

		ArrayList<ArrayList<Double>> contours = new ArrayList<ArrayList<Double>>();

		if (widths.length > 0) {
			for (int i = 0; i < widths.length; i++) {
				ArrayList<Double> newContour = new ArrayList<Double>();
				newContour.add((double)xs[i]);
				newContour.add((double)ys[i]);
				newContour.add((double)widths[i]);
				newContour.add((double)heights[i]);
				newContour.add((double)areas[i]);
				newContour.add((double)ratios[i]);

				// Add this contour to the main list of contours //
				contours.add(newContour);
			}
		}
		return contours;
	}

	public ArrayList<ArrayList<Double>> SortContours(ArrayList<ArrayList<Double>> contours, int index) {
		Collections.sort(contours, new Comparator<ArrayList<Double>>() {
            @Override
            public int compare(ArrayList<Double> a1, ArrayList<Double> a2) {
                return a1.get(index).compareTo(a2.get(index));
            }
		});
		return contours;
	}

	public ArrayList<ArrayList<ArrayList<Double>>> GetContourPairs() {
		ArrayList<ArrayList<Double>> contours = GetContours();
		
		// Sort the contours! //
		contours = SortContours(contours, 1);

		ArrayList<ArrayList<ArrayList<Double>>> pairs = new ArrayList<ArrayList<ArrayList<Double>>>();

		for (int i = 0; i < contours.size()-1; i++) {
			for (int j = i+1; j < contours.size(); j++) {
				double firstY = contours.get(i).get(1);
				double secondY = contours.get(j).get(1);

				if ((firstY/secondY) >= 0.9) {
					ArrayList<ArrayList<Double>> newPair = new ArrayList<ArrayList<Double>>();
					newPair.add(contours.get(i));
					newPair.add(contours.get(j));

					SmartDashboard.putNumber("ContourPair1", contours.get(i).get(0));
					SmartDashboard.putNumber("ContourPair2", contours.get(j).get(0));

					pairs.add(newPair);
				}

			}
		}
		return pairs;
	}
}
