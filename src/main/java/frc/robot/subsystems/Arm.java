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
	static int previousPosition = -1;
	

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
			System.out.println("Sensors (0):");
			System.out.println(sensor0Status);
			System.out.println(sensor2Status);

			System.out.println(";");
			actuator.set(speed);
		}
		else if (!sensor0Status && !sensor2Status){
			/*
			1 & 0 = 0 = False 
			*/
			System.out.println("Sensors (1):");
			System.out.println(!sensor0Status);
			System.out.println(!sensor2Status);

			System.out.println(";");
			actuator.set(speed);
		}
		else{
			System.out.println("Sensors (2):");
			System.out.println(sensor0Status);
			System.out.println(sensor2Status);

			System.out.println(";");
			actuator.set(0);
		}
		
		SmartDashboard.putBoolean("Hall Sensor 0:", sensor0Status);
		SmartDashboard.putBoolean("Hall Sensor 1:", sensor1Status);
		SmartDashboard.putBoolean("Hall Sensor 2:", sensor2Status);
	}


	@Override
	public void initDefaultCommand() {}
}
