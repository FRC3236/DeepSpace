package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.CommandBase;
import frc.robot.subsystems.Cargo;
import org.team3236.DriveTrainMode;

public class TeleopWeekZero extends Command {

	private PushHatch pushHatch = new PushHatch();
	public TeleopWeekZero(){
	
	}
	// Create any variables we'll need
	boolean canSwitchDriveMode = true;

	private void switchDriveMode() {
		boolean driveModeSwitch = CommandBase.controls.Driver.getAButtonPressed();
		if (driveModeSwitch){
			CommandBase.drivetrain.switchDriveMode();
		}
	}

	private void toggleAuto(){
		boolean autoTogglePressed = CommandBase.controls.Driver.getBButton();
		if (autoTogglePressed){
			CommandBase.visionRocket.DriveToPair(CommandBase.drivetrain.getDriveMode(), 0.5);
		}
	}

	private void handleElevatorLevel() {
		if (CommandBase.controls.Operator.getYButtonPressed()) {
			CommandBase.elevator.increaseDesiredLevel();
		} else if (CommandBase.controls.Operator.getBButtonPressed()) {
			CommandBase.elevator.decreaseDesiredLevel();
		} else if (CommandBase.controls.Operator.getAButton()) {
            // geta-button, sponsored by boeing.
            CommandBase.elevator.goToDesired();
		}
	}

	private void handleDriving() {
        
		double forwardSpeed, lateralSteeringSpeed, triggerLeft, triggerRight;
		double deadzone = 0.1;

		//Configure Driving control keymap
		triggerLeft = CommandBase.controls.Driver.getTriggerAxis(Hand.kLeft);
		triggerRight = CommandBase.controls.Driver.getTriggerAxis(Hand.kRight);

		/* If both triggers are equal in value, allow them to cancel each other out */
		forwardSpeed = (triggerLeft - triggerRight);

		lateralSteeringSpeed = CommandBase.controls.Driver.getX(Hand.kLeft);

		//Set deadzones to prevent unwanted jerks
		if(Math.abs(lateralSteeringSpeed) <= deadzone){
			lateralSteeringSpeed = 0;
		} else {
			if (lateralSteeringSpeed > 0) {
				lateralSteeringSpeed -= deadzone;
			} else {
				lateralSteeringSpeed += deadzone;
			} 
		}

		if (lateralSteeringSpeed < 0) {
			CommandBase.drivetrain.drive(forwardSpeed, forwardSpeed + lateralSteeringSpeed);
		} else {
			CommandBase.drivetrain.drive(forwardSpeed - lateralSteeringSpeed, forwardSpeed);
		}

		//Debugging
		SmartDashboard.putNumber("Lateral Steerign Speed", lateralSteeringSpeed);
		SmartDashboard.putNumber("Left Trigger", triggerLeft);
		SmartDashboard.putNumber("Right Trigger", triggerRight * -1);
		SmartDashboard.putNumber("Forward Speed", forwardSpeed);
		//Displays values in the Smart Dashboard
	}

	private void handleBallShooter(){
		double rightTrigger = CommandBase.controls.Operator.getTriggerAxis(Hand.kRight);
		double leftTrigger = CommandBase.controls.Operator.getTriggerAxis(Hand.kLeft);
		double motorSpeed = rightTrigger-leftTrigger;
		CommandBase.cargo.setIntake(motorSpeed);
	}

	private void handlePistons() {
		if (CommandBase.controls.Operator.getBumperPressed(Hand.kRight)) {
			pushHatch.cancel();
			pushHatch = new PushHatch();
			pushHatch.start();
		}
	}

	private void handAuto(){
		
	}
	@Override
	protected void initialize() {
		CommandBase.elevator.zeroEncoder();
	}

	@Override
	protected void execute() {
		switchDriveMode();
		handleElevatorLevel();
		handleDriving();
		handleBallShooter();
		handlePistons();
		toggleAuto();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}