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

public class TeleopMultiplierControl extends Command {
  public TeleopMultiplierControl() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    // Delete me
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

  }

  // Called repeatedly when this Command is scheduled to r`un
  @Override
  protected void execute() {
    
    CommandBase.drivetrain.getUltrasonic();
    boolean forward_acceleration = CommandBase.controls.Driver.getXButton();
    boolean backward_acceleration = CommandBase.controls.Driver.getAButton();
    boolean forward_stop = CommandBase.controls.Driver.getXButtonReleased();
    boolean backward_stop = CommandBase.controls.Driver.getAButtonReleased();
    double direction = CommandBase.controls.Driver.getX(Hand.kLeft);
    double multiplier = CommandBase.controls.Driver.getTriggerAxis(Hand.kRight);

    System.out.println("Hello!");
    SmartDashboard.putNumber("Multi", multiplier);
    SmartDashboard.putNumber("Direction:", direction);
    //if (direction) > 0.2:
    System.out.print(forward_acceleration);  
    if (forward_acceleration && multiplier > 0 ){
      SmartDashboard.putNumber("Usable direction:", direction);
      if (direction > 0.25 || direction < -0.25){
        CommandBase.drivetrain.Drive(multiplier, -multiplier+(multiplier*2*direction));

      }
      else{
        CommandBase.drivetrain.Drive(multiplier, -multiplier);
      }
    }
    if (backward_acceleration && multiplier > 0){
      if (direction > 0.25 || direction < -0.25){
        CommandBase.drivetrain.Drive(-multiplier+(multiplier*2*direction), multiplier);
      }
      else{
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
