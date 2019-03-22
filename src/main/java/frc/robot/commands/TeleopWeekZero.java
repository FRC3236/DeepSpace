package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.CommandBase;
import frc.robot.subsystems.Cargo;
import frc.robot.subsystems.Elevator;

import org.team3236.AssistMode;
import org.team3236.DriveTrainMode;

public class TeleopWeekZero extends Command {

	private PushHatch pushHatch = new PushHatch();
	public TeleopWeekZero(){
	
	}
	// Create any variables we'll need
	boolean canSwitchDriveMode = true;
	boolean limitSwitchPressed = false;
	double lastSpeed;
	double elevatorSpeedConstant = 0;
	double defaultElevatorSpeedConstant = 0.05; // TODO: change me


	
	private void switchDriveMode() {
		System.out.print(CommandBase.drivetrain.getDriveMode());
		if(CommandBase.drivetrain.getDriveMode() != DriveTrainMode.ENDGAME){
			boolean driveModeSwitch = CommandBase.controls.Driver.getAButtonPressed();
			if (driveModeSwitch){
				CommandBase.drivetrain.switchDriveMode();
			}
		}

		boolean climbModeToggle = CommandBase.controls.Driver.getBumperPressed(Hand.kRight);
		if(climbModeToggle){
			CommandBase.drivetrain.toggleEndGameMode();
		}
	}


	private void toggleAuto(){
		boolean autoTogglePressed = CommandBase.controls.Driver.getBButton();
		if (autoTogglePressed){
			CommandBase.visionRocket.DriveToPair(CommandBase.drivetrain.getDriveMode(), 0.5);
		}
	}

// As of week zero, this may over/under-extend the Elevator.
/*
	private void handleElevatorLevel() {
		if (CommandBase.controls.Operator.getYButtonPressed()) {
			CommandBase.elevator.increaseDesiredLevel();
		} else if (CommandBase.controls.Operator.getBButtonPressed()) {
			CommandBase.elevator.decreaseDesiredLevel();
		} else if (CommandBase.controls.Operator.getAButton()) {
            // geta-button, sponsored by Boeing.
            CommandBase.elevator.goToDesired();
		}
	}
*/
	
	public void handleElevatorSlowDown(){
		if(CommandBase.controls.Operator.getAButton()){
			elevatorSpeedConstant = defaultElevatorSpeedConstant;
		}
		else{
			elevatorSpeedConstant = 0;
		}
	}
	public void handleRawElevatorControl() {		
		/*if(elevatorSpeedConstant != 0 && limitSwitchPressed){
			elevatorSpeedConstant = 0;
		}
		else if(!limitSwitchPressed){
			elevatorSpeedConstant = defaultElevatorSpeedConstant; // Arbitrary number
		}*/
				
		double elevatorSpeed = (CommandBase.controls.Operator.getTriggerAxis(Hand.kLeft) 
							
							+ elevatorSpeedConstant);
		
		if (CommandBase.controls.Operator.getTriggerAxis(Hand.kRight) < 0.1){
			//lastSpeed = elevatorSpeed;
			CommandBase.elevator.set(elevatorSpeed);
		}
		else{
			CommandBase.elevator.set(0.25);
			//CommandBase.elevator.set(lastSpeed);
		}
		SmartDashboard.putNumber("e-Lever Speed: ", elevatorSpeed);
		if(CommandBase.drivetrain.getDriveMode() != DriveTrainMode.ENDGAME){
			double actuatorSpeed = -CommandBase.controls.Operator.getY(Hand.kLeft);
			CommandBase.cargo.setArm(actuatorSpeed*0.8);
			CommandBase.cargo.getSensor(1);

		}
		else{
			double actuatorSpeed = -CommandBase.controls.Driver.getY(Hand.kRight);
			CommandBase.cargo.setArm(actuatorSpeed*0.8);
			CommandBase.cargo.getSensor(1);



		}
	}

	private void handleDriving() {
        
		double forwardSpeed, lateralSteeringSpeed, triggerLeft, triggerRight;
		double deadzone = 0.1;

		//Configure Driving control keymap
		triggerLeft = CommandBase.controls.Driver.getTriggerAxis(Hand.kLeft);
		triggerRight = CommandBase.controls.Driver.getTriggerAxis(Hand.kRight);

		/* If both triggers are equal in value, allow them to cancel each other out */
		forwardSpeed = (triggerLeft - triggerRight) * 0.9;

		if(CommandBase.drivetrain.getDriveMode() == DriveTrainMode.CARGO || CommandBase.drivetrain.getDriveMode() == DriveTrainMode.ENDGAME){
			lateralSteeringSpeed = CommandBase.controls.Driver.getX(Hand.kLeft);

		}
		else if(CommandBase.drivetrain.getDriveMode() == DriveTrainMode.HATCH){
			lateralSteeringSpeed = -CommandBase.controls.Driver.getX(Hand.kLeft);

		}
		else{
			// This will never happen
			lateralSteeringSpeed = CommandBase.controls.Driver.getX(Hand.kLeft);
		}

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
		if (Math.abs(forwardSpeed)>0.05) {
			if (lateralSteeringSpeed < 0) {
				CommandBase.drivetrain.drive(forwardSpeed, forwardSpeed + lateralSteeringSpeed);
			} else {
				CommandBase.drivetrain.drive(forwardSpeed - lateralSteeringSpeed, forwardSpeed);
			}
		} else {
			CommandBase.drivetrain.drive(-lateralSteeringSpeed, lateralSteeringSpeed);
		}
		
		//Debugging
		SmartDashboard.putNumber("Lateral Steering Speed", lateralSteeringSpeed);
		SmartDashboard.putNumber("Left Trigger", triggerLeft);
		SmartDashboard.putNumber("Right Trigger", triggerRight * -1);
		SmartDashboard.putNumber("Forward Speed", forwardSpeed);
		SmartDashboard.putNumber("Elevator", CommandBase.elevator.getRawEncoder());
		//Displays values in the Smart Dashboard
	}

	private void handleBallShooter(){
		double shooterSpeed = -CommandBase.controls.Operator.getY(Hand.kRight);
		CommandBase.cargo.setIntake(shooterSpeed);
	}

	private void handlePistons() {
		SmartDashboard.putBoolean("E-Lever Lock State", CommandBase.endgame.isOpen());
		if(CommandBase.drivetrain.getDriveMode() == DriveTrainMode.ENDGAME){
			if(CommandBase.controls.Driver.getAButtonPressed()){
				CommandBase.endgame.retractLift();
			}	
			if(CommandBase.controls.Driver.getYButtonPressed()){
				CommandBase.endgame.extendLift();
			}
			if(CommandBase.controls.Driver.getBButtonPressed()){
				if(CommandBase.endgame.isOpen()){
					CommandBase.endgame.extendLock();
				}
				else{
					CommandBase.endgame.retractLock();
				}
			}
		}
		else{
			if (CommandBase.controls.Operator.getBumperPressed(Hand.kRight)) {
				pushHatch.cancel();
				pushHatch = new PushHatch();
				pushHatch.start();
			}
		}
	}

	private void handAuto(){
		
	}
	@Override
	protected void initialize() {
		CommandBase.elevator.zeroEncoder();
		CommandBase.hatch.setCompressor(true);
	}

	@Override
	protected void execute() {
		boolean limitSwitchPressed = CommandBase.elevator.getLimitSwitch();

		switchDriveMode();
		//handleElevatorLevel();
		handleDriving();
		handleBallShooter();
		handlePistons();
		toggleAuto();
		handleElevatorSlowDown();
		handleRawElevatorControl();

	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}