/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.CommandBase;

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
    boolean forward_acceleration = CommandBase.controls.Driver.getXButtonPressed();
    boolean backward_acceleration = CommandBase.controls.Driver.getAButtonPressed();
    boolean forward_stop = CommandBase.controls.Driver.getXButtonReleased();
    boolean backward_stop = CommandBase.controls.Driver.getAButtonReleased();
    double multiplier = CommandBase.controls.Driver.getY(Hand.kRight);


    if(!forward_stop){
      if (forward_acceleration){
          CommandBase.drivetrain.Drive(multiplier, -multiplier);
        }
      }

    if(!backward_stop){
      if (backward_acceleration){
        CommandBase.drivetrain.Drive(-multiplier, multiplier);
          }
      }
    
    if (forward_stop || backward_stop){
      CommandBase.drivetrain.Drive(0, 0);
    }

    //CommandBase.drivetrain.Drive(leftSpeed, rightSpeed);
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
