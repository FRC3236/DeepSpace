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
import java.util.Arrays;
import java.beans.DesignMode;
import java.util.ArrayList;
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
	static int previousSensor = 0;
	static boolean isRunning = false;

	static private boolean ActuatorInTransit;

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
		if(sensor == 0){
			return !hallSensor0.get();
		}
		else if(sensor == 1){
			return !hallSensor1.get();
		}
		else {
			return !hallSensor2.get();
		}
	}

	public double getRate() {
		return potPort.getAverageBits();
	}

	public void toggleActivity(){
		isRunning = !isRunning;
	}

	public boolean getActivity(){
		return isRunning;
	}

	public void setArm(double speed){
		// Update sensors
		boolean sensor0Status = getSensor(0);
		boolean sensor1Status = getSensor(1);
		boolean sensor2Status = getSensor(2);

		if((speed < 0 && sensor2Status) || (speed > 0 && sensor0Status)){
			/* speed = 1; sensor2Status = True;
			(speed < 0 && sensor2Status) = 0 & 1 = 1 = False
			(speed > 0 && sensor0Status) = 1 & 0 = False
			False || False = False
			*/
			actuator.set(speed);
		}
		else if (!sensor0Status && !sensor2Status){
			/*
			1 & 0 = 0 = False 
			*/
			actuator.set(speed);
		}
		else{
			actuator.set(0);
		}
		
		SmartDashboard.putBoolean("Hall Sensor 0:", sensor0Status);
		SmartDashboard.putBoolean("Hall Sensor 1:", sensor1Status);
		SmartDashboard.putBoolean("Hall Sensor 2:", sensor2Status);
	}



	public void setArm(double speed, int destinationSensor){
		toggleActivity();
		while (!getSensor(destinationSensor)){
			setArm(speed);
		}		
		previousSensor = destinationSensor;
		toggleActivity();
	}
	public void goToSensor(int destinationSensor){
		if(previousSensor == 2)
		{
			if (destinationSensor == 2){
				/* do nothing */
			}
			else{
				setArm(-0.7, destinationSensor);
			}
			
		}

		else if (previousSensor == 1){
			if (destinationSensor == 0){
				setArm(-0.7, destinationSensor);
			}
			else if (destinationSensor == 1){
				/* Don't move */
			}
			else if (destinationSensor == 2){
				setArm(+0.7, destinationSensor);
			}

		}
		else if (previousSensor == 0){
			if (destinationSensor == 0){
				/* Don't move */
			}
			else if (destinationSensor == 1){
				setArm(+0.7, destinationSensor);
			}
			else if (destinationSensor == 2){
				setArm(+0.7, destinationSensor);
			}
		}

		

	}


	@Override
	public void initDefaultCommand() {}
}
