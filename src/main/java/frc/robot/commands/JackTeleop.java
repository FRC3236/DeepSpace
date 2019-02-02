/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.CommandBase;

public class JackTeleop extends Command {
  public JackTeleop() {
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
    double forwardSpeed,lateralSpeed,triggerLeft,triggerRight;
    forwardSpeed = CommandBase.controls.Driver.getY(Hand.kLeft);
    lateralSpeed = CommandBase.controls.Driver.getX(Hand.kLeft);
    triggerLeft = CommandBase.controls.Driver.getTriggerAxis(Hand.kLeft);
    triggerRight = CommandBase.controls.Driver.getTriggerAxis(Hand.kRight);
    //Sets the driving controls to the left joystick
    if(Math.abs(lateralSpeed) <= 0.2){
      lateralSpeed = 0;
    }
    if(Math.abs(forwardSpeed) <= 0.2){
      forwardSpeed = 0;
    //Set deadzones to prevent unwanted jerks
    }
    double triggerRight2 = (triggerRight * -1);
    double trigHybrid = (triggerRight2 + triggerLeft);
    CommandBase.drivetrain.Drive((lateralSpeed)-trigHybrid,(lateralSpeed)+trigHybrid);
    //Implements trigger control
    SmartDashboard.putNumber("Forward Speed", forwardSpeed);
    SmartDashboard.putNumber("Lateral Speed", lateralSpeed);
    SmartDashboard.putNumber("Lateral Speed", lateralSpeed);
    SmartDashboard.putNumber("Left Trigger", triggerLeft);
    SmartDashboard.putNumber("Right Trigger", triggerRight * -1);
    SmartDashboard.putNumber("Triggers combined", trigHybrid);
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