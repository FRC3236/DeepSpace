/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
/**
 * Add your docs here.
 */
public class BallShooter extends Subsystem {
  private WPI_TalonSRX LeftBallShooter = new WPI_TalonSRX(RobotMap.LEFTBALLMOTOR);
  private WPI_TalonSRX RightBallShooter = new WPI_TalonSRX(RobotMap.RIGHTBALLMOTOR);

public void setBallMotors(double motorSpeed){

LeftBallShooter.set(motorSpeed);
RightBallShooter.set(-motorSpeed);
}

public void ShootBall(double shootSpeed){

LeftBallShooter.set(shootSpeed);

RightBallShooter.set(-shootSpeed);

}

public void CatchBall(double catchSpeed){

  LeftBallShooter.set(catchSpeed);

RightBallShooter.set(-catchSpeed);

}
  
   
public void stopMotors(){

  LeftBallShooter.set(0);

RightBallShooter.set(0);

}


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}