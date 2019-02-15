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
import frc.robot.subsystems.*;



public class TeleopEric extends Command {
	
	int desiredElevatorLevelCargo = 0;
	int desiredElevatorLevelHatch = 0;

	boolean canSwitchDriveMode = true;
	boolean armActive = true;

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
		if (CommandBase.controls.Driver.getBumperPressed(Hand.kLeft))
		{
			CommandBase.drivetrain.switchDriveMode();
		}

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


		if(CommandBase.drivetrain.getDriveMode() == DriveTrainMode.CARGO){
			switch(desiredElevatorLevelCargo){
				default: case 0:
					SmartDashboard.putBoolean("Level 1", false);
					SmartDashboard.putBoolean("Level 2", false);
					SmartDashboard.putBoolean("Level 3", false);
					SmartDashboard.putBoolean("Level 4", false);
					break;
				case 1:
					SmartDashboard.putBoolean("Level 1", true);
					SmartDashboard.putBoolean("Level 2", false);
					SmartDashboard.putBoolean("Level 3", false);
					SmartDashboard.putBoolean("Level 4", false);
					break;
				case 2:
					SmartDashboard.putBoolean("Level 1", false);
					SmartDashboard.putBoolean("Level 2", true);
					SmartDashboard.putBoolean("Level 3", false);
					SmartDashboard.putBoolean("Level 4", false);
					break;
				case 3:
					SmartDashboard.putBoolean("Level 1", false);
					SmartDashboard.putBoolean("Level 2", false);
					SmartDashboard.putBoolean("Level 3", true);	
					SmartDashboard.putBoolean("Level 4", false);			
					break;
				case 4:
					SmartDashboard.putBoolean("Level 1", false);
					SmartDashboard.putBoolean("Level 2", false);
					SmartDashboard.putBoolean("Level 3", false);	
					SmartDashboard.putBoolean("Level 4", true);			
					break;
			}

			if(CommandBase.controls.Driver.getYButtonPressed()) {
				// Increment Elevator level here
				if(desiredElevatorLevelCargo < 4) {
					desiredElevatorLevelCargo++;
				}
			}
			else if(CommandBase.controls.Driver.getBButtonPressed()) {
					// Decrement Elevator level here
				if (desiredElevatorLevelCargo >= 1){
					desiredElevatorLevelCargo--;
				}
			}
			else if(CommandBase.controls.Driver.getAButton()) {
				// Actually move the elevator 
				System.out.println(desiredElevatorLevelCargo);

				switch(desiredElevatorLevelCargo){
					default:
						break;

					case 1:
						CommandBase.elevator.goTo(RobotMap.CARGOLEVELONE, 0.8);
						break;

					case 2:
						CommandBase.elevator.goTo(RobotMap.CARGOLEVELTWO, 0.8);
						break;

					case 3:
						CommandBase.elevator.goTo(RobotMap.CARGOLEVELTHREE, 0.8);
						break;
					case 4:
						CommandBase.elevator.goTo(RobotMap.CARGOLEVELSHIP, 0.8);
						break;
				}
			}
		}
		else if (CommandBase.drivetrain.getDriveMode() == DriveTrainMode.HATCH) {
			switch (desiredElevatorLevelHatch) {
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
				case 4:
					SmartDashboard.putBoolean("Level 1", false);
					SmartDashboard.putBoolean("Level 2", false);
					SmartDashboard.putBoolean("Level 3", false);	
				break;
			}
		

			if (CommandBase.controls.Driver.getYButtonPressed()) {
				// Increment Elevator level here
					if(desiredElevatorLevelHatch < 3){
						desiredElevatorLevelHatch++;
					}
			}
			else if (CommandBase.controls.Driver.getBButtonPressed()) {
				// Decrement Elevator level here
				if (desiredElevatorLevelHatch >= 1){
					desiredElevatorLevelHatch--;
				}
			}
			

			else if (CommandBase.controls.Driver.getAButton()) {
				// Actually move the elevator 
				System.out.println(desiredElevatorLevelHatch);

				switch (desiredElevatorLevelHatch) {
					default:
						break;

					case 1:
						CommandBase.elevator.goTo(RobotMap.HATCHLEVELONE, 0.8);
						break;

					case 2:
						CommandBase.elevator.goTo(RobotMap.HATCHLEVELTWO, 0.8);
						break;

					case 3:
						CommandBase.elevator.goTo(RobotMap.HATCHLEVELTHREE, 0.8);
						break;
				}
			}
		}
		
		double actuatorSpeed = CommandBase.controls.Driver.getTriggerAxis(Hand.kLeft) - CommandBase.controls.Driver.getTriggerAxis(Hand.kRight);
		if (actuatorSpeed > 0.7){
			actuatorSpeed = 0.7;
		}
		if (actuatorSpeed < -0.7){
			actuatorSpeed = -0.7;
		}
		CommandBase.cargo.setArm(actuatorSpeed);

		if (CommandBase.controls.Driver.getBumperPressed(Hand.kRight)){
			CommandBase.elevator.set(1);
		}

		if (CommandBase.cargo.getActivity() == false){
			if (CommandBase.controls.Operator.getYButtonPressed()){
				CommandBase.cargo.goToSensor(2);
			}
			if (CommandBase.controls.Operator.getBButtonPressed()){
				CommandBase.cargo.goToSensor(1);
			}
			if (CommandBase.controls.Operator.getAButtonPressed()){
				CommandBase.cargo.goToSensor(0);
			}
		}
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