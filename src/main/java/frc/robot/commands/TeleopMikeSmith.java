/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
// fdjdahfjkhakfhdhlkdAHJFKHSAKJFHKJHFJHaflkdah
package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.CommandBase;

public class TeleopMikeSmith extends Command {
  public TeleopMikeSmith() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    // Delete me
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

  }

  /*if CommandBase.controls.Driver.getAButtonPressed(true){
    static int controltype = !controltype;

  }*/


  // Called repeatedly when this Command is scheduled to run
/*
  @Override
  protected void execute() {
    double leftsideSpeed, rightsideSpeed; 
    leftsideSpeed = CommandBase.controls.Driver.getY(Hand.kLeft); //leftspeed = Left Xbox Stick Y axis
    rightsideSpeed = CommandBase.controls.Driver.getY(Hand.kRight);//rightside speed = Right Xbox Stick Y axis
    CommandBase.drivetrain.Drive(-leftsideSpeed, rightsideSpeed);
    //elevatorspeed = CommandBase.controls
  } */
  
  @Override
  protected void execute() {
    double backwardSpeed, forwardSpeed, lateralSpeed; 
    backwardSpeed = CommandBase.controls.Driver.getTriggerAxis(Hand.kLeft); //leftspeed = Left Xbox Trigger axis
    forwardSpeed = CommandBase.controls.Driver.getTriggerAxis(Hand.kRight);//rightside speed = Right Xbox trigger
    lateralSpeed = CommandBase.controls.Driver.getX(Hand.kLeft); //lateral speed = Left Xbox Stick X axis
  

    double leftSpeed = -backwardSpeed + forwardSpeed + lateralSpeed;
    double rightSpeed = backwardSpeed - forwardSpeed + lateralSpeed;
    CommandBase.drivetrain.Drive(leftSpeed, rightSpeed); 
    //elevatorspeed = CommandBase.controls
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
