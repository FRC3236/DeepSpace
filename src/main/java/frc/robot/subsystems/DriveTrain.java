/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.*;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.*;

import org.team3236.DriveTrainMode;
import org.team3236.Conversion;

public class DriveTrain extends Subsystem {
	private WPI_TalonSRX LeftTalonA = new WPI_TalonSRX(RobotMap.LEFTTALONA);
	private WPI_TalonSRX LeftTalonB = new WPI_TalonSRX(RobotMap.LEFTTALONB);
	private WPI_TalonSRX RightTalonA = new WPI_TalonSRX(RobotMap.RIGHTTALONA);
	private WPI_TalonSRX RightTalonB = new WPI_TalonSRX(RobotMap.RIGHTTALONB);

	private static AHRS NavX = new AHRS(SPI.Port.kMXP);

	private DriveTrainMode driveMode = DriveTrainMode.CARGO;

	public DriveTrain() {

		// Reset the Gyro to 0 degrees //
		this.resetGyro();

		// Put the drivemode on the display //
		this.updateSmartDashboard();
	}

	public void updateSmartDashboard() {
		if (this.driveMode == DriveTrainMode.CARGO) {
			SmartDashboard.putString("Drive Mode", "CARGO");
		} else {
			SmartDashboard.putString("Drive Mode", "HATCH");
		}
	}

	public double getAngle() {
		return NavX.getAngle();
	}

	public double getPitch() {
		return NavX.getPitch();
	}

	public void resetGyro() {
		NavX.reset();
	}

	public void setDriveMode(DriveTrainMode newMode) {
		this.driveMode = newMode;
		this.updateSmartDashboard();
	}

	public void switchDriveMode() {
		if (this.driveMode == DriveTrainMode.HATCH) {
			this.driveMode = DriveTrainMode.CARGO;
		} else {
			this.driveMode = DriveTrainMode.HATCH;
		}
		this.updateSmartDashboard();
	}

	public DriveTrainMode getDriveMode() {
		return this.driveMode;
	}

	public void setLeft(double speed) {
		if (driveMode == DriveTrainMode.HATCH) {
			LeftTalonA.set(speed);
			LeftTalonB.set(speed);
		} else {
			LeftTalonA.set(-speed);
			LeftTalonB.set(-speed);
		}
	}

	public void setRight(double speed) {
		if (driveMode == DriveTrainMode.HATCH) {
			RightTalonA.set(-speed);
			RightTalonB.set(-speed);
		} else {
			RightTalonA.set(speed);
			RightTalonB.set(speed);
		}
	}

	public void drive(double leftSpeed, double rightSpeed){
		setLeft(leftSpeed);
		setRight(rightSpeed);

	}

	@Override
	public void initDefaultCommand() {}
	
	
}
