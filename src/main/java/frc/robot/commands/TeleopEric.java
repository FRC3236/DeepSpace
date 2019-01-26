/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team3236.DriveTrainMode;
import frc.robot.CommandBase;

public class TeleopEric extends Command {
	
	boolean canSwitchDriveMode = true;
	public TeleopEric() {
	
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		CommandBase.drivetrain.Initialize();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// Check to see if we should switch drive modes //
		boolean leftStickDown = CommandBase.controls.Driver.getStickButton(Hand.kLeft);
		boolean rightStickDown = CommandBase.controls.Driver.getStickButton(Hand.kRight);
		int dpad = CommandBase.controls.Driver.getPOV();

		if (leftStickDown && rightStickDown) {
			if (canSwitchDriveMode) {
				CommandBase.drivetrain.SwitchDriveMode();
				canSwitchDriveMode = false;
			}
		} else {
			// So that the drive mode isn't switching over and over again while both buttons are pressed //
			canSwitchDriveMode = true;
		}

		SmartDashboard.putNumber("elevator", CommandBase.elevator.getRawEncoder());
		SmartDashboard.putNumber("Ultrasonic", CommandBase.drivetrain.GetDistance());

		double leftSpeed = CommandBase.controls.Driver.getY(Hand.kLeft);
		double rightSpeed = CommandBase.controls.Driver.getY(Hand.kRight);

		//CommandBase.drivetrain.Drive(leftSpeed, rightSpeed);
		CommandBase.elevator.set(leftSpeed/2);
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
