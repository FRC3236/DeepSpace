/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.RobotMap;

public class Pneumatics extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  Compressor compressor = new Compressor(0);
  DoubleSolenoid solenoid = new DoubleSolenoid(RobotMap.SOLENOIDPORTONE, RobotMap.SOLENOIDPORTTWO);
  WPI_TalonSRX intakeLeft = new WPI_TalonSRX(RobotMap.INTAKEPORTLEFT);
  WPI_TalonSRX intakeRight = new WPI_TalonSRX(RobotMap.INTAKEPORTRIGHT);


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
  public void retractPistons(){
    solenoid.set(DoubleSolenoid.Value.kOff);
    solenoid.set(DoubleSolenoid.Value.kForward);
  }

  public void extendPistons(){
    solenoid.set(DoubleSolenoid.Value.kOff);
    solenoid.set(DoubleSolenoid.Value.kReverse);

  }
  public void stop(){
    // Manually overwrite solenoid state
    solenoid.set(DoubleSolenoid.Value.kOff);
  }

  boolean isOpen(){
    /* Returns true when solenoid is forwards and false 
     when solenoid is reversed/stopped */
    if(solenoid.get() == DoubleSolenoid.Value.kForward)
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  public void setCompressor(boolean state){
    // Explicitly control power across solenoid
    if(state == true){
      compressor.start();
    }
    else{
      compressor.stop();
    }
  }
}
