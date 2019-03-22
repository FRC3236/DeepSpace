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

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.*;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.*;

import org.team3236.DriveTrainMode;
import org.team3236.Conversion;

public class DriveTrain extends Subsystem {
	private WPI_VictorSPX LeftVictorA = new WPI_VictorSPX(RobotMap.LEFTVICTORA);
	private WPI_VictorSPX LeftVictorB = new WPI_VictorSPX(RobotMap.LEFTVICTORB);
	private WPI_VictorSPX RightVictorA = new WPI_VictorSPX(RobotMap.RIGHTVICTORA);
	private WPI_VictorSPX RightVictorB = new WPI_VictorSPX(RobotMap.RIGHTVICTORB);

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
			SmartDashboard.putString("Drive Mode", "Cargo");
		} 
		else if (this.driveMode == DriveTrainMode.HATCH)  {
			SmartDashboard.putString("Drive Mode", "Hatch");
		}
		else if (this.driveMode == DriveTrainMode.ENDGAME){
			SmartDashboard.putString("Drive Mode", "Endgame");

		}
	}

	public double getAngle() {
		// return NavX.getAngle();
		return 0.00;
	}

	public double getPitch() {
		//return NavX.getPitch();
		return 0.00;
	}

	public void resetGyro() {
		// NavX.reset();
		return;
	}

	public void setDriveMode(DriveTrainMode newMode) {
		this.driveMode = newMode;
		this.updateSmartDashboard();
	}

	public void toggleEndGameMode(){
		System.out.println("!!");
		this.driveMode = DriveTrainMode.ENDGAME;
		this.updateSmartDashboard();
	}
	
	public void switchDriveMode() {
		if (this.driveMode == DriveTrainMode.HATCH || this.driveMode == DriveTrainMode.ENDGAME) {
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
			LeftVictorA.set(speed);
			LeftVictorB.set(speed);
		} else {
			LeftVictorA.set(-speed);
			LeftVictorB.set(-speed);
		}
	}

	public void setRight(double speed) {
		if (driveMode == DriveTrainMode.HATCH) {
			RightVictorA.set(-speed);
			RightVictorB.set(-speed);
		} else {
			RightVictorA.set(speed);
			RightVictorB.set(speed);
		}
	}

	public void drive(double leftSpeed, double rightSpeed){
		setLeft(leftSpeed);
		setRight(rightSpeed);

	}

	@Override
	public void initDefaultCommand() {}
	
	
}
