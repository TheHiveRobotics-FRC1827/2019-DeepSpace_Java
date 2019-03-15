package org.usfirst.frc.team1827.robot;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

public class Lifter
{
    SpeedController armLiftMotorRight;
    SpeedController armLiftMotorLeft;

    private static final int rightArmMotorPWMChannel = 8;
    private static final int leftArmMotorPWMChannel = 7;
    private static final double armUpSpeed = 0.75;
	private static final double armDownSpeed = 0.25;
    
    // TODO constructor with parameters for the two PWM channels
    public Lifter()
    {
        armLiftMotorRight = new VictorSP(rightArmMotorPWMChannel);
        armLiftMotorLeft = new VictorSP(leftArmMotorPWMChannel);
        stop();
    }

    public void stop()
    {
        armLiftMotorLeft.set(0);
        armLiftMotorRight.set(0);
    }

    public void lift()
    {
        armLiftMotorLeft.set(-armUpSpeed);
        armLiftMotorRight.set(armUpSpeed);
    }

    public void goDown()
    {
        armLiftMotorLeft.set(armDownSpeed);
        armLiftMotorRight.set(-armDownSpeed);
    }
}