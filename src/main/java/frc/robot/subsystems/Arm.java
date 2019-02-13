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
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;

/**
 * Add your docs here.
 */

public class Arm extends Subsystem {
	private WPI_TalonSRX actuator;
	private AnalogPotentiometer potentiometer;
	private AnalogInput potPort = new AnalogInput(0);
	private DigitalInput hallSensor0 = new DigitalInput(7);
	private DigitalInput hallSensor1 = new DigitalInput(8);
	private DigitalInput hallSensor2 = new DigitalInput(9);
	static int currentPosition = 0;

	public Arm() {
		//potPort.setGlobalSampleRate(samplesPerSecond);

		actuator = new WPI_TalonSRX(RobotMap.ACTUATOR);
		AnalogInput.setGlobalSampleRate(75);

		potPort.setAverageBits(6);
	}

	public double get() {
		return Math.ceil(potPort.getAverageVoltage() * 1000)/10;
	}
	public boolean getSensor(int sensor){
		switch(sensor){
			default:
				//
			case 0:
				return !hallSensor0.get();
			case 1:
				return !hallSensor1.get();
			case 2:
				return !hallSensor2.get();
		}
	}

	public double getRate() {
		return potPort.getAverageBits();
	}

	public void gotoSensor(int sensor){
		boolean sensorStatus = getSensor(sensor);
		if(sensorStatus == false){
			/* if we are behind or on front of the sensor */

			switch(currentPosition){
				default:
					//
				case(0):
					// Extend sensor to destination
					setArm(0.5);
				case(1):
					if(sensor == 2){
						/* Extend arm */
						setArm(0.5);
					}
					else{
						setArm(-0.5);
					}
				case(2):
					// Retract sensor to destination
					setArm(-0.5);
			}
			/*We are behind destination sensor*/
			
			setArm(0.5); // extend arm

		}
		else if(sensorStatus == true){
			currentPosition = sensor;
			return;
		}
	}

	public void setArm(double speed){
		/* Negative values retract the actuator;
		   Positive values extend the actuator; */
		actuator.set(speed);
	}


	@Override
	public void initDefaultCommand() {}
}
