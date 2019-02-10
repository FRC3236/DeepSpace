/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import org.team3236.AssistMode;
import frc.robot.CommandBase;

public class TeleopVision extends Command {
	public TeleopVision() {
		requires(CommandBase.visionRocket);
		requires(CommandBase.drivetrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		System.out.println("Running teleop vision");
		ArrayList<Double> speeds = CommandBase.visionRocket.DriveToPair(AssistMode.CARGOSHIP,
		 0.75);
		System.out.println(speeds);
		SmartDashboard.putNumber("Gyro", CommandBase.drivetrain.getAngle());
		CommandBase.drivetrain.drive(-speeds.get(0), speeds.get(1));
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
