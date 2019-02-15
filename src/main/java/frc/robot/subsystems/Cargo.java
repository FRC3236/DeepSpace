/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Add your docs here.
 */
public class Cargo extends Subsystem {
	
	private WPI_TalonSRX intake, actuator;
	// 2 is fully extended, 1 is the middle, 0 is fully retracted //
	private DigitalInput hallSensor0, hallSensor1, hallSensor2;

	private boolean isRunning = false;
	private int previousSensor = 0;

	private double DEFAULTACTUATORSPEED = 0.7;

	public Cargo() {
		actuator = new WPI_TalonSRX(RobotMap.ACTUATOR);
		intake = new WPI_TalonSRX(RobotMap.INTAKE);

		hallSensor0 = new DigitalInput(RobotMap.HALLSENSOR0);
		hallSensor1 = new DigitalInput(RobotMap.HALLSENSOR1);
		hallSensor2 = new DigitalInput(RobotMap.HALLSENSOR2);
	}

	private void toggleActivity() {
		isRunning = !isRunning;
	}

	public boolean getActivity() {
		return isRunning;
	}

	public boolean getSensor(int sensor) {
		if (sensor == 0) {
			return !hallSensor0.get();
		} else if (sensor == 1) {
			return !hallSensor1.get();
		} else {
			return !hallSensor2.get();
		}
	}

	public void setIntake(double speed) {
		intake.set(speed);
	}

	public void pullIntake() {
		intake.set(DEFAULTINTAKESPEED);
	}

	public void 

	public void setArm(double speed) {
		boolean isTooFar = getSensor(2);
		boolean isTooClose = getSensor(0);

		if ((speed < 0 && isTooFar) || (speed > 0 && isTooClose)) {
			actuator.set(speed);
		} else if (!isTooFar && !isTooClose) {
			actuator.set(speed);
		} else {
			actuator.set(0);
		}
	}

	private void setArm(double speed, int destination) {
		toggleActivity();
		while(!getSensor(destination)) {
			setArm(speed);
		}
		previousSensor = destination;
		toggleActivity();
	}
	
	public void goToSensor(int destination) {
		if (previousSensor == 2 && destination != 2) {
			setArm(-DEFAULTACTUATORSPEED, destination);
		} else if (previousSensor == 1) {
			if (destination == 0) {
				setArm(-DEFAULTACTUATORSPEED, destination);
			} else if (destination == 2) {
				setArm(DEFAULTACTUATORSPEED, destination);
			}
		} else if (previousSensor == 0 && destination != 0) {
			setArm(DEFAULTACTUATORSPEED, destination);
		} else {
			setArm(0);
		}
	}

	@Override
	public void initDefaultCommand() {}
}
