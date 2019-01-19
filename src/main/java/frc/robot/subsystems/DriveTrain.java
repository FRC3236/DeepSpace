/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import com.kauailabs.navx.frc.*;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.*;

import org.team3236.DriveTrainMode;
/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */

public class DriveTrain extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private WPI_TalonSRX LeftTalonA = new WPI_TalonSRX(RobotMap.LEFTTALONA);
	private WPI_TalonSRX LeftTalonB = new WPI_TalonSRX(RobotMap.LEFTTALONB);
	private WPI_TalonSRX RightTalonA = new WPI_TalonSRX(RobotMap.RIGHTTALONA);
	private WPI_TalonSRX RightTalonB = new WPI_TalonSRX(RobotMap.RIGHTTALONB);

	SerialPort Ultrasonic;
	private static AHRS NavX = new AHRS(SPI.Port.kMXP);
	private boolean autoLocked = false;

	private DriveTrainMode DriveMode = DriveTrainMode.HATCH;

	public void Initialize() {
		// Set up the ultrasonic sensor //
		try {
			Ultrasonic = new SerialPort(9600, SerialPort.Port.kOnboard, 8, Parity.kNone, StopBits.kOne);
		} catch (RuntimeException ex) {
			throw ex;
		}
		Ultrasonic.setTimeout(2);
		Ultrasonic.setReadBufferSize(6);
	}

	public double GetDistance() {


		return 0.0;
	}

	public double GetAngle() {
		return NavX.getAngle();
	}

	public double GetPitch() {
		return NavX.getPitch();
	}

	public void ResetGyro() {
		NavX.reset();
	}

	public void SetLeft(double speed) {
		if (DriveMode == DriveTrainMode.HATCH) {
			LeftTalonA.set(speed);
			LeftTalonB.set(speed);
		} else {
			LeftTalonA.set(-speed);
			LeftTalonB.set(-speed);
		}
	}

	public void SetRight(double speed) {
		if (DriveMode == DriveTrainMode.HATCH) {
			RightTalonA.set(-speed);
			RightTalonB.set(-speed);
		} else {
			RightTalonA.set(speed);
			RightTalonB.set(speed);
		}
	}

	public void Drive(double leftSpeed, double rightSpeed){
		SetLeft(leftSpeed);
		SetRight(rightSpeed);
	}

	public void LockAuto() {
		autoLocked = true;
	}

	public void UnlockAuto() {
		autoLocked = false;
	}

	public boolean IsAutoLocked() {
		return autoLocked;
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	
}
