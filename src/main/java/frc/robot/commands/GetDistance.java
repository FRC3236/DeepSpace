/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Ultrasonic;
import frc.robot.CommandBase;



public class GetDistance extends Command {
  public GetDistance() {}

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    CommandBase.drivetrain.Initialize();
    //CommandBase.drivetrain.resetUltrasonic();
    
  } 

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    System.out.print("Hi!"); 
    CommandBase.drivetrain.getUltrasonic();
    
    //data = data.replaceAll("R", "");
    //Double doubleData = Double.parseDouble(data) /25.4; //25.4 is the conversion from MM to IN
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
