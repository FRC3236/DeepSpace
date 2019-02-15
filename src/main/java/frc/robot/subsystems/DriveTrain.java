/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */

public class DriveTrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
	private WPI_VictorSPX LeftVictorA = new WPI_VictorSPX(RobotMap.LEFTVICTORA);
	private WPI_VictorSPX LeftVictorB = new WPI_VictorSPX(RobotMap.LEFTVICTORB);
	private WPI_VictorSPX RightVictorA = new WPI_VictorSPX(RobotMap.RIGHTVICTORA);
	private WPI_VictorSPX RightVictorB = new WPI_VictorSPX(RobotMap.RIGHTVICTORB);

  public void InvertTalons()
  {
    RightVictorA.setInverted(true);
    RightVictorB.setInverted(true);
  }

  public void SetLeft(double speed){
    LeftVictorA.set(speed);
    LeftVictorB.set(speed);
  }

  public void SetRight(double speed){
    RightVictorA.set(speed);
    RightVictorB.set(speed);
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