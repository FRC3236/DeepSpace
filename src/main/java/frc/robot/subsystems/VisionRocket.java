/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.CommandBase;
import frc.robot.CommandBase.*;

import org.team3236.contours.*;
import org.team3236.AssistMode;
import org.team3236.DriveTrainMode;
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
	private boolean drivingAlongArc = false;
	private boolean discoveredDesiredAngle = false;
	private int desiredAngle = 0;
	private double masterRadius = 0;
	private double theta = 0;
	private int normalAngle = 0;

	private static enum RocketSide {
		RIGHT, LEFT
	}

	public ArrayList<Contour> GetContours() {
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

		ArrayList<Contour> contours = new ArrayList<Contour>();

		if (smallestLen > 0) {
			for (int i = 0; i < smallestLen; i++) {
				/*ArrayList<Double> newContour = new ArrayList<Double>();
				newContour.add((double)xs[i]);
				newContour.add((double)ys[i]);
				newContour.add((double)widths[i]);
				newContour.add((double)heights[i]);
				newContour.add((double)areas[i]);
				newContour.add((double)ratios[i]);*/

				Contour newContour = new Contour(
					(double)xs[i],
					(double)ys[i],
					(double)widths[i],
					(double)heights[i],
					(double)areas[i],
					(double)ratios[i]
				);

				// Add this contour to the main list of contours //
				contours.add(newContour);
			}
		}
		return contours;
	}

	public ArrayList<Contour> SortContours(ArrayList<Contour> contours, int index) {
		Collections.sort(contours, new Comparator<Contour>() {
            @Override
            public int compare(Contour a1, Contour a2) {
                return Double.compare(a1.get(index), a2.get(index));
            }
		});
		return contours;
	}

	public ArrayList<ContourPair> GetContourPairs() {
		ArrayList<Contour> contours = GetContours();
		
		// Sort the contours! //
				contours = SortContours(contours, 1);

		ArrayList<ContourPair> pairs = new ArrayList<ContourPair>();

		for (int i = 0; i < contours.size()-1; i++) {
			for (int j = i+1; j < contours.size(); j++) {
				double firstY = contours.get(i).get(1);
				double secondY = contours.get(j).get(1);

				if ((firstY/secondY) >= 0.9) {
					ContourPair newPair = new ContourPair(contours.get(i), contours.get(j));

					pairs.add(newPair);
				}

			}
		} 
		return pairs;
	}

	public ArrayList<Double> DriveToPair(DriveTrainMode mode, double speed) {
		
		// speeds.get(0) is the left side of the drive train, speeds.get(1) is the right side
		ArrayList<Double> speeds = new ArrayList<Double>();
		ArrayList<ContourPair> pairs = GetContourPairs();

		if (pairs.size() > 0) {

			if (pairs.size() > 1) {
				if (mode == DriveTrainMode.CARGO) {

				} 
				else if (mode == DriveTrainMode.HATCH) {

				}
			} else {
				ArrayList<Contour> pair = SortContours(pairs.get(0).getContours(), 0);
				Contour contourA = pair.get(0);
				Contour contourB = pair.get(1);

				double leftX = contourA.getX() + (double)(contourA.getWidth()/2);
				double leftY = contourA.getY() + (double)(contourA.getHeight()/2);
				
				double rightX = contourB.getX() + (double)(contourB.getWidth()/2);
				double rightY = contourB.getY() + (double)(contourB.getHeight()/2);

				NetworkTableEntry ntCamWidth = visionTable.getEntry("camWidth");
				NetworkTableEntry ntCamHeight = visionTable.getEntry("camHeight");
				double camWidth = ntCamWidth.getDouble(0);
				double camHeight = ntCamHeight.getDouble(0);

				double cameraX = camWidth/2;
				double pairX = leftX + (rightX-leftX)/2;

				double offset = pairX - cameraX;

				double width_X = (rightX-leftX); // Use this scale for distance
				SmartDashboard.putNumber("Midpoint Distance:", width_X);


				// Make speedScaleConstant bigger if you want robot to slow down over a longer distance, and smaller if you want it to slow down over a shorter distance
				double speedScaleConstant = 9;
				double speedScale = Math.min(speedScaleConstant / (contourA.getWidth() + contourB.getWidth()/2), 1.0);
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

	public ArrayList<Double> DriveAlongArc(AssistMode mode, double speed) {
		
		ArrayList<Double> speeds = new ArrayList<Double>();


		double distanceToRocket = 72.0; // In inches
		double gyroAngle = CommandBase.drivetrain.getAngle();
		int gyroAngleInt = (int)Math.round(gyroAngle);

		double width = distanceToRocket * Math.cos(gyroAngle);
		double depth = distanceToRocket * Math.sin(gyroAngle);
		double moddedAngle = Math.floorMod(gyroAngleInt, 360);
		double moddedNormal;

		if (mode == AssistMode.CARGOROCKET) {

		} else if (mode == AssistMode.HATCH) {
			
			if (!discoveredDesiredAngle) {
				if (moddedAngle >= 0 && moddedAngle < 90) {
					desiredAngle = 60;
				} else if (moddedAngle >= 90 && moddedAngle < 180) {
					desiredAngle = 120;
				} else if (moddedAngle >= 180 && moddedAngle < 270) {
					desiredAngle = 240;
				} else {
					desiredAngle = 300;
				}
				if (moddedAngle <= desiredAngle) {
					normalAngle = desiredAngle - 90;
					theta = 90 - (moddedAngle-normalAngle);
				} else {
					normalAngle = desiredAngle + 90;
					theta = 90 - (normalAngle-moddedAngle);
				}
				moddedNormal = Math.floorMod(normalAngle, 360);
				discoveredDesiredAngle = true;
			}
			

			//SmartDashboard.putNumber("Desired", desiredAngle);
			//SmartDashboard.putNumber("Modded", moddedAngle);

			// Check if our current angle is within 10 degrees of our normal angle
			System.out.println("Normal: " + normalAngle);
			if (!drivingAlongArc && Math.abs(moddedAngle - normalAngle) > 5) {
				// Tell the drivetrain to spin //
				double turnSpeed = 0.3;
				if (moddedAngle < normalAngle) {
					speeds.add(turnSpeed);
					speeds.add(-turnSpeed);
				} else {
					speeds.add(-turnSpeed);
					speeds.add(turnSpeed);
				}
				//SmartDashboard.putNumber("Speed Left", speeds.get(0));
				//SmartDashboard.putNumber("Speed Right", speeds.get(1));
				return speeds;
			} else {
				if (!drivingAlongArc) {	
					masterRadius = (depth/2) / Math.sin(theta);
				}
				drivingAlongArc = true;
				// We can follow the arc now! //
				double wheelBase = 23.0;
				// New robot 2019 is 21.5;//
				
				double innerRadius = masterRadius - wheelBase;
				double outerRadius = masterRadius + wheelBase;
				double omega = speed/masterRadius;
	
				/*System.out.println("Ultrasonic " + CommandBase.drivetrain.GetDistance());
				System.out.println("Depth: " + depth);
				System.out.println("Theta: " + theta);
				System.out.println("Master rad: " + masterRadius);
				System.out.println("Inner rad: " + innerRadius);
				System.out.println("Outer rad: " + outerRadius);
				System.out.println("Omega: " + omega);*/

				double innerSpeed = -(innerRadius * omega);
				double outerSpeed = -(outerRadius * omega);
	

				if (Math.abs(gyroAngle - normalAngle) < 5) {
					speeds.add(0.0);
					speeds.add(0.0);
					return speeds;
				}
				if (moddedAngle >= 180) {
					// The inside turning circle is on the left //
					speeds.add(innerSpeed);
					speeds.add(outerSpeed);
				} else {
					// The inside turning circle is on the right //
					speeds.add(outerSpeed);
					speeds.add(innerSpeed);
				}

				System.out.println("Speeds: I(" + innerSpeed + ") O(" + outerSpeed +")");
				//SmartDashboard.putNumber("Speed Left", speeds.get(0));
				//SmartDashboard.putNumber("Speed Right", speeds.get(1));
				return speeds;
			}

			
		}

		speeds.add(0.0);
		speeds.add(0.0);
		//SmartDashboard.putNumber("Speed Left", speeds.get(0));
		//SmartDashboard.putNumber("Speed Right", speeds.get(1));
		return speeds;
	}
}
