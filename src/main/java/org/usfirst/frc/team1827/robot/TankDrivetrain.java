package org.usfirst.frc.team1827.robot;

public class TankDrivetrain
{
    public TankDrivetrain()
    {

    }

    public void tankDriveControl(double leftJoystickValue, double rightJoystickValue)
	{
		if(Math.abs(leftJoystickValue) > 0.15)
		{
			frontLeft.set(-leftJoystickValue);
			rearLeft.set(-leftJoystickValue);
		}
		else
		{
			frontLeft.set(0);
			rearLeft.set(0);
		}
		
		if(Math.abs(rightJoystickValue) > 0.15)
		{
			frontRight.set(rightJoystickValue);
			rearRight.set(rightJoystickValue);
		}
		else
		{
			frontRight.set(0);
			rearRight.set(0);
		}
	}
}