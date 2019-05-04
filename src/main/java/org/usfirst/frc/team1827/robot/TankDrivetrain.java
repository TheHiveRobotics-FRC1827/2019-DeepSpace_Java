package org.usfirst.frc.team1827.robot;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.PWMVictorSPX;

public class TankDrivetrain
{
    private static final int kFrontLeftChannel = 6;
    private static final int kRearLeftChannel = 5;
    private static final int kFrontRightChannel = 4;
    private static final int kRearRightChannel = 3;
    private static final double joystickDeadbandValue = 0.15;

    private SpeedController frontLeft;
    private SpeedController rearLeft;
    private SpeedController frontRight;
    private SpeedController rearRight;

    public TankDrivetrain()
    {        
        frontLeft = new PWMVictorSPX(kFrontLeftChannel);
        rearLeft = new PWMVictorSPX(kRearLeftChannel);
        frontRight = new VictorSP(kFrontRightChannel);
        rearRight = new VictorSP(kRearRightChannel);
    }

    public void controlInput(double leftJoystickValue, double rightJoystickValue)
	{
		if(Math.abs(leftJoystickValue) > joystickDeadbandValue)
		{
            leftDrive(leftJoystickValue);
		}
		else
		{			
            stopLeft();
		}
		
		if(Math.abs(rightJoystickValue) > joystickDeadbandValue)
		{			
            rightDrive(rightJoystickValue);
		}
		else
		{			
            stopRight();
		}
    }

    private void leftDrive(double leftJoystickValue)
    {
        frontLeft.set(-leftJoystickValue);
		rearLeft.set(-leftJoystickValue);
    }

    private void rightDrive(double rightJoystickValue)
    {
        frontRight.set(rightJoystickValue);
        rearRight.set(rightJoystickValue);
    }
    
    public void stopLeft()
    {
        frontLeft.set(0);
        rearLeft.set(0);
    }

    public void stopRight()
    {
        frontRight.set(0);
        rearRight.set(0);
    }

    // TODO will this work or stop one side before the other and cause a "turn"?
    public void stopAll()
    {
        stopLeft();
        stopRight();
    }
}