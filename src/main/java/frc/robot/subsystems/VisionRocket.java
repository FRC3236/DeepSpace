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

		int smallestLen = 1000;
		if (widths.length < smallestLen) { smallestLen = widths.length; }
		if (heights.length < smallestLen) { smallestLen = heights.length; }
		if (xs.length < smallestLen) { smallestLen = xs.length; }
		if (ys.length < smallestLen) { smallestLen = ys.length; }
		if (ratios.length < smallestLen) { smallestLen = ratios.length; }
		if (areas.length < smallestLen) { smallestLen = areas.length; }

		ArrayList<ArrayList<Double>> contours = new ArrayList<ArrayList<Double>>();

		if (smallestLen > 0) {
			for (int i = 0; i < smallestLen; i++) {
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

					pairs.add(newPair);
				}

			}
		}
		return pairs;
	}

	public ArrayList<Double> DriveToPair(int mode, double speed) {
		// mode 0 is cargo (center), mode 1 is the hatch panel (sides)
		
		// speeds.get(0) is the left side of the drive train, speeds.get(1) is the right side
		ArrayList<Double> speeds = new ArrayList<Double>();

		ArrayList<ArrayList<ArrayList<Double>>> pairs = GetContourPairs();
		if (pairs.size() > 0) {

			if (pairs.size() > 1) {
				//
			} else {
				ArrayList<ArrayList<Double>> pair = SortContours(pairs.get(0), 0);
				ArrayList<Double> contourA = pair.get(0);
				ArrayList<Double> contourB = pair.get(1);

				double leftX = contourA.get(0) + (double)(contourA.get(2)/2);
				double leftY = contourA.get(1) + (double)(contourA.get(3)/2);
				
				double rightX = contourB.get(0) + (double)(contourB.get(2)/2);
				double rightY = contourB.get(1) + (double)(contourB.get(3)/2);

				NetworkTableEntry ntCamWidth = visionTable.getEntry("camWidth");
				NetworkTableEntry ntCamHeight = visionTable.getEntry("camHeight");
				double camWidth = ntCamWidth.getDouble(0);
				double camHeight = ntCamHeight.getDouble(0);

				double cameraX = camWidth/2;
				double pairX = leftX + (rightX-leftX)/2;

				double offset = pairX - cameraX;

				// Make speedScaleConstant bigger if you want robot to slow down over a longer distance, and smaller if you want it to slow down over a shorter distance
				double speedScaleConstant = 8;
				double speedScale = Math.min(speedScaleConstant / (contourA.get(2) + contourB.get(2))/2, 1.0);
				double scaledSpeed = Math.min(speed * speedScale, 1.0);

				// Increase subScale to make adjustment less extreme //
				double subScale = 3;
			
				if (Math.abs(offset) > 5) {

					if (offset > 0) {
						// Angled too far to left
						speeds.add(scaledSpeed);
						
						// Slow down right side
						double scale = Math.min(1, subScale / Math.abs(offset));
						speeds.add(-(scaledSpeed * scale));
					} else {
						// Angled too far to right
						// Slow down left side
						/*double scale = (Math.abs(offset) / (camWidth/2));
						speeds.add(scaledSpeed - scaledSpeed * scale);*/
						double scale = Math.min(1, subScale / Math.abs(offset));
						speeds.add(scaledSpeed * scale);

						speeds.add(-scaledSpeed);
						
					}

				} else {
					speeds.add(scaledSpeed);
					speeds.add(-scaledSpeed);
				}


			}

		} else {
			// Set both sides to 0
			speeds.add(0.0);
			speeds.add(0.0);
			
		}


		if (speeds.size() != 2) {
			speeds = new ArrayList<Double>();
			speeds.add(0.0);
			speeds.add(0.0);
		}
		return speeds;
	}
}
