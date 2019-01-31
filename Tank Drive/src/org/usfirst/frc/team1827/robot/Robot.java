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
	private static final int kFrontLeftChannel = 1;
	private static final int kRearLeftChannel = 2;
	private static final int kFrontRightChannel = 6;
	private static final int kRearRightChannel = 4;
	
	
	
	SpeedController frontLeft = new VictorSP(kFrontLeftChannel);
	SpeedController rearLeft = new VictorSP(kRearLeftChannel);
	SpeedController frontRight = new VictorSP(kFrontRightChannel);
	SpeedController rearRight = new VictorSP(kRearRightChannel);
	XboxController joystick = new XboxController(0);
	SpeedController arm = new VictorSP(3);
	@Override
	public void robotInit() {
		
		
	}

	@Override
	public void teleopPeriodic() {
		double speed = 1;
		System.out.println(joystick.getY(Hand.kRight));

		if(joystick.getY(Hand.kLeft)*-1>.3) {
			frontLeft.set(speed);
			//frontRight.set(speed);
			rearLeft.set(speed);
			//rearRight.set(speed);
		}
		else if(joystick.getY(Hand.kLeft)*-1<-.3) {
			frontLeft.set(-1*speed);
			//frontRight.set(-1*speed);
			rearLeft.set(-1*speed);
			//rearRight.set(-1*speed);
			
		}
		else {
			frontLeft.set(0);
			//frontRight.set(0);
			rearLeft.set(0);
			//rearRight.set(0);
		}
		
		
		if(joystick.getY(Hand.kRight)*-1>.3) {
			//frontLeft.set(speed);
			frontRight.set(speed);
			//rearLeft.set(speed);
			rearRight.set(speed);
		}
		else if(joystick.getY(Hand.kRight)*-1<-.3) {
			//frontLeft.set(-1*speed);
			frontRight.set(-1*speed);
			//rearLeft.set(-1*speed);
			rearRight.set(-1*speed);
			
		}
		else {
			//frontLeft.set(0);
			frontRight.set(0);
			//rearLeft.set(0);
			rearRight.set(0);
		}
		if (joystick.getTriggerAxis(Hand.kRight)==1) {
			arm.set(speed);
			
		}
		else if (joystick.getTriggerAxis(Hand.kLeft)==1) {
			arm.set(-1*speed);
		}
		else {
			arm.set(0);
		}
		
		/*if(joystick.getY(Hand.kLeft)*-1>.3) {
			frontLeft.set(speed);
			frontRight.set(speed);
			rearLeft.set(speed);
			rearRight.set(speed);
		}
		else if(joystick.getY(Hand.kLeft)*-1<-.3) {
			frontLeft.set(-1*speed);
			frontRight.set(-1*speed);
			rearLeft.set(-1*speed);
			rearRight.set(-1*speed);
			
		}
		else if(joystick.getX(Hand.kRight)*-1>.3) {
			frontLeft.set(speed);
			frontRight.set(-1*speed);
			rearLeft.set(speed);
			rearRight.set(-1*speed);
		}
		else if(joystick.getX(Hand.kRight)*-1>.3) {
			frontLeft.set(-1*speed);
			frontRight.set(speed);
			rearLeft.set(-1*speed);
			rearRight.set(speed);
		}
		else {
			frontLeft.set(0);
			frontRight.set(0);
			rearLeft.set(0);
			rearRight.set(0);
		}*/
	}
	
	@Override
	public void autonomousPeriodic() {
		
		System.out.println("Test");
		double speed = 1;
		Timer time = new Timer();
		while(time.get()<3) {
			frontLeft.set(speed);
			frontRight.set(speed);
			rearLeft.set(speed);
			rearRight.set(speed);
		}
	}
}
