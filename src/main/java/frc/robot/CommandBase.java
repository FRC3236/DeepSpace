/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import frc.robot.OI;
import frc.robot.subsystems.*;

public class CommandBase {
	public static OI controls = new OI(); 
	public static DriveTrain drivetrain = new DriveTrain();
	public static Elevator elevator = new Elevator();
	public static Cargo cargo = new Cargo();
}

