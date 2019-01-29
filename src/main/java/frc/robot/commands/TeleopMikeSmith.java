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
import frc.robot.commands.getDistance;
import frc.robot.subsystems.Elevator;

public class TeleopMikeSmith extends Command {
  public TeleopMikeSmith() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    // Delete me
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    CommandBase.drivetrain.Drive(0,0);
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
  //int getRawEncoderPos = CommandBase.elevator.getRawEncoderPos();



  @Override
  protected void execute() {
    double backwardSpeed, forwardSpeed, lateralSpeed; 
    backwardSpeed = CommandBase.controls.Driver.getTriggerAxis(Hand.kLeft); //leftspeed = Left Xbox Trigger axis
    forwardSpeed = CommandBase.controls.Driver.getTriggerAxis(Hand.kRight);//rightside speed = Right Xbox trigger
    lateralSpeed = CommandBase.controls.Driver.getX(Hand.kLeft); //lateral speed = Left Xbox Stick X axis
  
    double leftSpeed = -backwardSpeed + forwardSpeed + (lateralSpeed/2);
    double rightSpeed = backwardSpeed - forwardSpeed + (lateralSpeed/2);
  
    //if speed isnt greater than 0.125 set motors to 0
    if (Math.abs(rightSpeed) > 0.125 || Math.abs(leftSpeed) > 0.125){ //Possible Deadzone
    CommandBase.drivetrain.Drive(leftSpeed, rightSpeed);
    }
    else{

      CommandBase.drivetrain.Drive(0,0);
    }
    int getRawEncoderPos = CommandBase.elevator.getRawEncoderPos();

    double elevatorspeed;
    elevatorspeed = CommandBase.controls.Driver.getY(Hand.kRight)/1.5;
    

    if (getRawEncoderPos < 4000 && (-elevatorspeed > 0.25 )){

      
    
  CommandBase.elevator.setMotors(elevatorspeed);
    }
    else if(elevatorspeed > 0.05){

CommandBase.elevator.setMotors(0);

    }
  else if(getRawEncoderPos>= 4000 || getRawEncoderPos<= 0){


CommandBase.elevator.setMotors(0);

  }


//SmartDashbard Commands
SmartDashboard.putNumber("Elevator Speed", elevatorspeed );
SmartDashboard.putNumber("leftspeed", leftSpeed);
SmartDashboard.putNumber("rightspeed", rightSpeed);
SmartDashboard.putNumber("Encoder Position", getRawEncoderPos);


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
