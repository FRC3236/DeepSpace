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
import frc.robot.subsystems.BallShooter;
import frc.robot.Robot;
import frc.robot.RobotMap;
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
public void DriveConfiguration(){

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




      SmartDashboard.putNumber("leftspeed", leftSpeed);
      SmartDashboard.putNumber("rightspeed", rightSpeed);
      SmartDashboard.putNumber("lateralspeed", lateralSpeed);
    }
}
  public void BallConfiguration(){


 double shootSpeed = CommandBase.controls.Operator.getTriggerAxis(Hand.kRight);
 double catchSpeed = CommandBase.controls.Operator.getTriggerAxis(Hand.kLeft);

if(shootSpeed > 0.1 && catchSpeed < 0.1){
CommandBase.ballshooter.ShootBall(shootSpeed);
}
if(catchSpeed > 0.1 && shootSpeed < 0.1){
CommandBase.ballshooter.CatchBall(catchSpeed);
}
if((catchSpeed > 0.1 && shootSpeed > 0.1) || (catchSpeed < 0.1 && shootSpeed < 0.1)){

CommandBase.ballshooter.stopMotors();

}




  }
  /*int getRawEncoderPos = CommandBase.elevator.getRawEncoderPos();

    double elevatorspeed;
    elevatorspeed = CommandBase.controls.Driver.getY(Hand.kRight)/1.5;
    

    if (getRawEncoderPos < 4000 && (-elevatorspeed > 0.25 ) && CommandBase.controls.Driver.getAButton() != true && CommandBase.controls.Driver.getBButton() != true){

      
    
  CommandBase.elevator.setMotors(elevatorspeed);
    }
    else if(elevatorspeed > 0.05){

CommandBase.elevator.setMotors(0);

    }
  else if(getRawEncoderPos>= 4000 || getRawEncoderPos<= 300){


CommandBase.elevator.setMotors(0);

  }
  else if(CommandBase.controls.Driver.getAButton() == true && getRawEncoderPos <= 4000){


if(getRawEncoderPos < 1500 ){
CommandBase.elevator.setMotors(-0.50);
}
else if(CommandBase.controls.Driver.getAButton() == true){
  CommandBase.elevator.setMotors(-0.26);
}
else{
  CommandBase.elevator.setMotors(0);
}
  }

  else if(CommandBase.controls.Driver.getBButton() == true && CommandBase.controls.Driver.getAButton() != true && getRawEncoderPos <= 4000){


    if(getRawEncoderPos < 2800 ){
    CommandBase.elevator.setMotors(-0.50);
    }
    else if(CommandBase.controls.Driver.getBButton() == true){
      CommandBase.elevator.setMotors(-0.26);
    }
    else if(CommandBase.controls.Driver.getBButton() != true){
      CommandBase.elevator.setMotors(0);
    }
      } 

      SmartDashboard.putNumber("Elevator Speed", elevatorspeed );
      SmartDashboard.putNumber("Encoder Position", getRawEncoderPos);
      SmartDashboard.putBoolean("A button", CommandBase.controls.Driver.getAButton());
      SmartDashboard.putBoolean("B button", CommandBase.controls.Driver.getBButton());
*/ 



  @Override
  protected void execute() {

    DriveConfiguration();
    BallConfiguration();
    





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


  @Override
  protected void interrupted() {
  }
}
