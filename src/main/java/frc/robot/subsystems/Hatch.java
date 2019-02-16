package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Hatch extends Subsystem {
    private Compressor compressor;
    private DoubleSolenoid solenoid;
    
    public Hatch() {
        compressor = new Compressor(RobotMap.COMPRESSOR);
        solenoid = new DoubleSolenoid(0, 1);
    
        retractPistons();
    }

    @Override
    public void initDefaultCommand(){}

    public void retractPistons() {
        solenoid.set(Value.kForward);
    }
    
    public void extendPistons() {
        solenoid.set(Value.kReverse);
    }

    public boolean isOpen() {
        return solenoid.get() == Value.kForward;
    }

    public void setCompressor(boolean state) {
        if (state) {
            compressor.start();
        } else {
            compressor.stop();
        }
    }
}