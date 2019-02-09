/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team3236.DriveTrainMode;
import frc.robot.CommandBase;
import frc.robot.RobotMap;

public class TeleopEric extends Command {
	
	int desiredElevatorLevel = 0;
	boolean canSwitchDriveMode = true;
	public TeleopEric() {
	
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		CommandBase.elevator.zeroEncoder();
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
				CommandBase.drivetrain.switchDriveMode();
				canSwitchDriveMode = false;
			}
		} else {
			// So that the drive mode isn't switching over and over again while both buttons are pressed //
			canSwitchDriveMode = true;
		}

		SmartDashboard.putNumber("elevator", CommandBase.elevator.getRawEncoder());

		//double leftSpeed = CommandBase.controls.Driver.getY(Hand.kLeft);
		double leftSpeed = 0;
		double rightSpeed = CommandBase.controls.Driver.getY(Hand.kRight);

		CommandBase.elevator.set(rightSpeed);


		switch(desiredElevatorLevel){
			default:
			case 0:
				SmartDashboard.putBoolean("Level 1", false);
				SmartDashboard.putBoolean("Level 2", false);
				SmartDashboard.putBoolean("Level 3", false);

				break;
			case 1:

				SmartDashboard.putBoolean("Level 1", true);
				SmartDashboard.putBoolean("Level 2", false);
				SmartDashboard.putBoolean("Level 3", false);				
				break;

			case 2:
				SmartDashboard.putBoolean("Level 1", false);
				SmartDashboard.putBoolean("Level 2", true);
				SmartDashboard.putBoolean("Level 3", false);	
				break;

			case 3:
				SmartDashboard.putBoolean("Level 1", false);
				SmartDashboard.putBoolean("Level 2", false);
				SmartDashboard.putBoolean("Level 3", true);				
				break;
		}

		SmartDashboard.putNumber("Elevator Level", desiredElevatorLevel);

		if(CommandBase.controls.Driver.getYButtonPressed()){
			// Increment Elevator level here
			if(desiredElevatorLevel < 3){
				desiredElevatorLevel++;
			}
		}
		else if(CommandBase.controls.Driver.getBButtonPressed()){
			// Decrement Elevator level here
			if (desiredElevatorLevel >= 1){
				desiredElevatorLevel--;
			}
		}
		

		else if(CommandBase.controls.Driver.getAButton()){
			// Actually move the elevator 
			System.out.println(desiredElevatorLevel);

			switch(desiredElevatorLevel){
				default:
					break;

				case 1:
					CommandBase.elevator.goTo(RobotMap.ELEVATORLEVELONE, 0.8);
					break;

				case 2:
					CommandBase.elevator.goTo(RobotMap.ELEVATORLEVELTWO, 0.8);
					break;

				case 3:
					CommandBase.elevator.goTo(RobotMap.ELEVATORLEVELTHREE, 0.8);
					break;
			}
		}
		
		
		//if (CommandBase.controls.Driver.getAButton()) {
		//	System.out.println("???");
		//	CommandBase.elevator.goTo(1500, 1);
		//}
		
		/* and elif is neccecary here, because we believe that
		flawed logic exists in the XboxController class which
		fails to make GetAButton() false when B button is
		pressed */

		/*(else if (CommandBase.controls.Driver.getBButton()) {
			CommandBase.elevator.goTo(3000, 1);
		}

		else {
			// For safety reasons, we
			// set the elevator motor to -0.
			CommandBase.elevator.set(-leftSpeed); 
		} */

		
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