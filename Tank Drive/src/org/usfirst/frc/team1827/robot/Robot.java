/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1827.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends IterativeRobot {
	private static final int kFrontLeftChannel = 2;
	private static final int kRearLeftChannel = 3;
	private static final int kFrontRightChannel = 1;
	private static final int kRearRightChannel = 4;
	
	SpeedController frontLeft = new VictorSP(kFrontLeftChannel);
	SpeedController rearLeft = new VictorSP(kRearLeftChannel);
	SpeedController frontRight = new VictorSP(kFrontRightChannel);
	SpeedController rearRight = new VictorSP(kRearRightChannel);
	XboxController joystick = new XboxController(0);

	@Override
	public void robotInit() {
		frontLeft.setInverted(true);
		rearLeft.setInverted(true);
	}

	@Override
	public void teleopPeriodic() {
		double speed = 1;
		//System.out.println(joystick.getY(Hand.kLeft));

		if(joystick.getY(Hand.kLeft)>0) {
			frontLeft.set(speed);
			frontRight.set(speed);
			rearLeft.set(speed);
			rearRight.set(speed);
		}
		
		
	}
	
	@Override
	public void autonomousPeriodic() {
		System.out.println("Test");
		double speed = .1;
		Timer time = new Timer();
		while(time.get()<3) {
			frontLeft.set(speed);
			frontRight.set(speed);
			rearLeft.set(speed);
			rearRight.set(speed);
		}
	}
}
