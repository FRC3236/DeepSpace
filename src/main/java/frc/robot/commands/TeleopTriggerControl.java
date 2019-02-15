
package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.CommandBase;
import java.math.*;

public class TeleopTriggerControl extends Command {
  public TeleopTriggerControl() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    // Delete me
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double forwardSpeed,lateralSteeringSpeed,triggerLeft,triggerRight;

    //Configure Driving control keymap
    triggerLeft = CommandBase.controls.Driver.getTriggerAxis(Hand.kLeft);
    triggerRight = CommandBase.controls.Driver.getTriggerAxis(Hand.kRight);

    /* If both triggers are equal in value, allow them to cancel each other out */
    forwardSpeed = (-triggerRight + triggerLeft);

    lateralSteeringSpeed = CommandBase.controls.Driver.getX(Hand.kLeft);

    //Set deadzones to prevent unwanted jerks
    if(Math.abs(lateralSteeringSpeed) <= 0.2){
      lateralSteeringSpeed = 0;
    }

    // One Talon is reversed; reflect that.
    double leftSpeed = (lateralSteeringSpeed-forwardSpeed);
    double rightSpeed = (lateralSteeringSpeed+forwardSpeed);

    CommandBase.drivetrain.Drive(leftSpeed,rightSpeed);

    //Debugging
    SmartDashboard.putNumber("Lateral Steerign Speed", lateralSteeringSpeed);
    SmartDashboard.putNumber("Left Trigger", triggerLeft);
    SmartDashboard.putNumber("Right Trigger", triggerRight * -1);
    SmartDashboard.putNumber("Forward Speed", forwardSpeed);
    //Displays values in the Smart Dashboard
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