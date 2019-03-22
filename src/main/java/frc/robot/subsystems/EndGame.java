package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class EndGame extends Subsystem {
    private DoubleSolenoid climbSolenoid;
    private DoubleSolenoid lockSolenoid;


    
    public EndGame() {
        climbSolenoid = new DoubleSolenoid(2, 3);
        lockSolenoid  = new DoubleSolenoid(4, 5);
        retractLift();
        retractLock();
    }

    @Override
    public void initDefaultCommand(){}

    public void retractLift() {
        climbSolenoid.set(Value.kForward);
    }
    
    public void extendLift() {
        climbSolenoid.set(Value.kReverse);

    }
    public void retractLock() {
        lockSolenoid.set(Value.kForward);
    }
    
    public void extendLock() {
        lockSolenoid.set(Value.kReverse);  
    }

    public boolean isOpen() {
        return climbSolenoid.get() == Value.kForward;
    }
    
}
