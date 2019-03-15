package org.usfirst.frc.team1827.robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid;


public class KickerPistons
{
    private DoubleSolenoid solenoidContrLeft;
    private DoubleSolenoid solenoidContrRight;
    private Timer timer;

    // TODO constructor with parameters for the two PCM channels
    public KickerPistons()
    {
        solenoidContrLeft = new DoubleSolenoid(0, 1);
        solenoidContrRight = new DoubleSolenoid(2,3);
        timer = new Timer();
    }
    
    public void kick(boolean button)
	{        
		if(button)
		{
			timer.reset();
			timer.start();
			solenoidContrLeft.set(DoubleSolenoid.Value.kForward);
			solenoidContrRight.set(DoubleSolenoid.Value.kForward);
		}

		if(timer.get() > 1)
		{
			solenoidContrLeft.set(DoubleSolenoid.Value.kReverse);
			solenoidContrRight.set(DoubleSolenoid.Value.kReverse);
		}
    }
    
    public void retract()
    {
        solenoidContrLeft.set(DoubleSolenoid.Value.kReverse);
		solenoidContrRight.set(DoubleSolenoid.Value.kReverse);
    }
}