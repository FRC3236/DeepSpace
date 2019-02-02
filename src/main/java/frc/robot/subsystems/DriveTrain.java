/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.TeleopMikeSmith;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */

public class DriveTrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private WPI_TalonSRX LeftTalonA = new WPI_TalonSRX(RobotMap.LEFTTALONA);
  private WPI_TalonSRX LeftTalonB = new WPI_TalonSRX(RobotMap.LEFTTALONB);
  private WPI_TalonSRX RightTalonA = new WPI_TalonSRX(RobotMap.RIGHTTALONA);
  private WPI_TalonSRX RightTalonB = new WPI_TalonSRX(RobotMap.RIGHTTALONB);

  public void InvertTalons()
  {
    RightTalonA.setInverted(true);
    RightTalonB.setInverted(true);
  }

  public void SetLeft(double speed){
    LeftTalonA.set(speed);
    LeftTalonB.set(speed);
  }

  public void SetRight(double speed){
    RightTalonA.set(speed);
    RightTalonB.set(speed);
  }

  public void Drive(double leftSpeed, double rightSpeed){
    SetLeft(leftSpeed);
    SetRight(rightSpeed);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
  
  
}
