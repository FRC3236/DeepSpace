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
  
  private WPI_TalonSRX BallShooter = new WPI_TalonSRX(RobotMap.BALLMOTOR);

public void setBallMotors(double motorSpeed){

BallShooter.set(motorSpeed);
}

public void ShootBall(double shootSpeed){



BallShooter.set(shootSpeed);

}

public void CatchBall(double catchSpeed){

  

BallShooter.set(-catchSpeed);

}
  
   
public void stopMotors(){

BallShooter.set(0);

}


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}