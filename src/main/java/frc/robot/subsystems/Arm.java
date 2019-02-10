/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
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

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

/**
 * Add your docs here.
 */

public class Arm extends Subsystem {
	private WPI_TalonSRX actuator;
	private AnalogPotentiometer potentiometer;
	private AnalogInput potPort = new AnalogInput(0);
	
	public Arm() {
		//potPort.setGlobalSampleRate(samplesPerSecond);

		actuator = new WPI_TalonSRX(RobotMap.ACTUATOR);
		AnalogInput.setGlobalSampleRate(75);

		potPort.setAverageBits(6);
	}

	public double get() {
		return Math.ceil(potPort.getAverageVoltage() * 1000)/10;
	}

	public double getRate() {
		return potPort.getAverageBits();
	}

	public void setArm(double speed){
		actuator.set(speed);
	}


	@Override
	public void initDefaultCommand() {}
}
