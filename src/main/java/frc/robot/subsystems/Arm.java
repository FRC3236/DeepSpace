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
	
	boolean sensor0Status;
	boolean sensor1Status;
	boolean sensor2Status;
	boolean ActuatorInTransit;

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
	public int gotoSensor(int destinationSensor){
		// Update Sensors
		sensor0Status = getSensor(0);
		sensor1Status = getSensor(1);
		sensor2Status = getSensor(2);
		SmartDashboard.putBoolean("Hall Sensor 0:", sensor0Status);
		SmartDashboard.putBoolean("Hall Sensor 1:", sensor1Status);
		SmartDashboard.putBoolean("Hall Sensor 2:", sensor2Status);
		SmartDashboard.putNumber("Last known sensor value: ", previousPosition);
		boolean thisSensorStatus = getSensor(destinationSensor); // True when we are at given sensor
		System.out.print("Desintation: "+destinationSensor);
		System.out.print("Status" + sensor0Status + sensor1Status + sensor2Status);
		if (thisSensorStatus)
		{
			return 0;
		}

		else
		// Find direction we need to move; Initate movement.

		{
			// If this is first actuator movement and we are not already fully extended
			if(previousPosition == -1 
			&& !((!sensor0Status)
			&& (!sensor1Status)
			&& (!sensor2Status))){
				/* First movement; Actuator is in default position; move forwards to destination */
				setArm(0.5, destinationSensor);
				return 0;
			}
			else if(previousPosition == 0){
				/* Actuator has moved before, but re-initialized at some point;
				 move forwards to destination */

				 if(destinationSensor != 0){
					setArm(0.5, destinationSensor);
					return 0;
				 }
				 
				 else{
					 return 0; // Dont move from sensor 0 to sensor 0; that doesn't make sense.
				 }
			}
			else if(previousPosition == 1){
				/* Actuator has moved before; We are in middle position;
				Move in a direction derived from value of desinationSensor towards the desination. */
				
				if(destinationSensor < previousPosition){
					/* Actuator is moving from sensor1 to sensor0; move backwards to destination. */
					setArm(-0.5, destinationSensor);
					return 0;
				}
				else if(destinationSensor == previousPosition){
					/* Actuator is moving from sensor 1 to sensor 1; Don't move. */
					return 0;	
				}
				else if(destinationSensor > previousPosition){
					/* Actuator is moving from sensor 1 to sensor 2; move forwards to destination */
					setArm(0.5, destinationSensor);
				}
			}
			else if(previousPosition == 2){
				/* Actuator has moved before; Actuator is in extended position;
				Move backwards to destination; */

				if(destinationSensor != 2){
					setArm(-0.5, destinationSensor);
					return 0;
				}
				else{
					// Don't move from sensor 2 to sensor 2; That doesn't make sense
					return 0;
				}
			}
			else if ((!sensor0Status) && (!sensor1Status) && (!sensor2Status)){
				/* (0,0,0) must be fully extended past last encoder */
				// Move backwards to destination
				System.out.print("Gaston is confused... :/");
				setArm(-0.5, destinationSensor);
				return 0;
			}

		}
		/* This code will not be reachable under normal conditions; 
			It serves as a mechanism to detect movement error. */
		return -1; 


	}

	public void setArm(double speed){
		/* Negative values retract the actuator;
		   Positive values extend the actuator; */

		// Update sensors
		sensor0Status = getSensor(0);
		sensor1Status = getSensor(1);
		sensor2Status = getSensor(2);
		SmartDashboard.putBoolean("Hall Sensor 0:", sensor0Status);
		SmartDashboard.putBoolean("Hall Sensor 1:", sensor1Status);
		SmartDashboard.putBoolean("Hall Sensor 2:", sensor2Status);

		actuator.set(-speed);
	}

	public void setArm(double speed, int destinationSensor){
		/* Negative values retract the actuator;
		   Positive values extend the actuator; */

		// Update sensors
		sensor0Status = getSensor(0);
		sensor1Status = getSensor(1);
		sensor2Status = getSensor(2);
		SmartDashboard.putBoolean("Hall Sensor 0:", sensor0Status);
		SmartDashboard.putBoolean("Hall Sensor 1:", sensor1Status);
		SmartDashboard.putBoolean("Hall Sensor 2:", sensor2Status);

		// Move arm in speed calculated by gotoSensor()
		System.out.println("Moving Arm to desination...");
		System.out.println("(Destionation = Sensor "+destinationSensor+")");
		System.out.println("Speed: "+ speed);
		actuator.set(speed);
		System.out.println("Completed Arm Motion;");

		// Update sensors again
		sensor0Status = getSensor(0);
		sensor1Status = getSensor(1);
		sensor2Status = getSensor(2);
		SmartDashboard.putBoolean("Hall Sensor 0:", sensor0Status);
		SmartDashboard.putBoolean("Hall Sensor 1:", sensor1Status);
		SmartDashboard.putBoolean("Hall Sensor 2:", sensor2Status);

		// If we are at destinaiton, return;
		if(getSensor(destinationSensor) == true){
			System.out.print("Arrived at destination!");
			return;
		}
		else{
			System.out.println("Gaston encountered an error... Gaston is sad :(");
		}
	}


	@Override
	public void initDefaultCommand() {}
}
