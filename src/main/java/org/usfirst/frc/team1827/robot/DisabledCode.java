/* This is a holding place for code that's commented out to clean up the main code */

/*
		if(joystick.getBumperReleased(Hand.kLeft)){
			System.out.print("Arm Down");

			timer.reset();
			while(timer.get()<bumperTimerConstant){
				armLiftMotorLeft.set(-.5);
				armLiftMotorRight.set(.5);
			}
		}

		if(joystick.getBumperReleased(Hand.kRight)){
			System.out.print("Arm Up");

			timer.reset();
			while(timer.get()<bumperTimerConstant){
				armLiftMotorLeft.set(.5);
				armLiftMotorRight.set(-.5);
			}
		}*/
		/*
		// if button is pressed
		if(joystick.getAButtonReleased())
		{
			System.out.println("Solenoids OUt");

			if(exampleDoubleBoolean==true){

				timer.reset();

				while(timer.get()<solenoidFliperTimerConstant){
					solenoidContrRight.set(DoubleSolenoid.Value.kForward);
					solenoidContrLeft.set(DoubleSolenoid.Value.kForward);
				}
				solenoidContrRight.set(DoubleSolenoid.Value.kOff);
				solenoidContrLeft.set(DoubleSolenoid.Value.kOff);

				exampleDoubleBoolean = false;

			}else{
				timer.reset();
				
				while(timer.get()<solenoidFliperTimerConstant){
					solenoidContrRight.set(DoubleSolenoid.Value.kReverse);
					solenoidContrLeft.set(DoubleSolenoid.Value.kReverse);
				}

				solenoidContrRight.set(DoubleSolenoid.Value.kOff);
				solenoidContrLeft.set(DoubleSolenoid.Value.kOff);
				exampleDoubleBoolean = true;
			}

		}

		// if button is pressed
		if(joystick.getBButtonReleased())
		{
			System.out.println("Flapper");

			if(pusherSolenoidCheck==true){

				timer.reset();

				while(timer.get()<solenoidPusherTimerConstant){
					pusher.set(DoubleSolenoid.Value.kForward);
					
				}
				pusher.set(DoubleSolenoid.Value.kOff);

				pusherSolenoidCheck = false;

			}else{
				timer.reset();
				
				while(timer.get()<solenoidPusherTimerConstant){
					pusher.set(DoubleSolenoid.Value.kReverse);
				}

				pusher.set(DoubleSolenoid.Value.kOff);
				pusherSolenoidCheck = true;
			}

		}

		// if button is pressed
		
		if(joystick.getXButtonPressed())
		{
			System.out.print("climber");
			climber.set(true);
		}else{
			climber.set(false);
		}

		if(joystick.getYButtonPressed() && joystick.getTriggerAxis(Hand.kRight)>.75){
			bumperTimerConstant=bumperTimerConstant+.25;
			System.out.print(bumperTimerConstant);
		}

		if(joystick.getYButtonPressed() && joystick.getTriggerAxis(Hand.kLeft)>.75){
			bumperTimerConstant=bumperTimerConstant-.25;
			System.out.print(bumperTimerConstant);
		}
        */
        
        /*
		if(joystick.getY(Hand.kLeft)*-1>.15) {
			frontLeft.set(1*Math.pow(Math.abs(joystick.getY(Hand.kLeft)), 1/driveScaling));
			rearLeft.set(1*Math.pow((Math.abs(joystick.getY(Hand.kLeft))), 1/driveScaling));
		}
		else if(joystick.getY(Hand.kLeft)*-1<-.15) {
			frontLeft.set(-1*Math.pow(Math.abs(joystick.getY(Hand.kLeft)), 1/driveScaling));
			rearLeft.set(-1*Math.pow(Math.abs(joystick.getY(Hand.kLeft)), 1/driveScaling));		
		}
		else {
			frontLeft.set(0);
			rearLeft.set(0);
		}
		//joystick.ge
		System.out.println(joystick.getY(Hand.kRight));
		if(joystick.getY(Hand.kRight)*-1>.15) {
			System.out.print("Right Forward?");
			// frontRight.set(1);
			// rearRight.set(1);
			frontRight.set(-1*(Math.pow(joystick.getY(Hand.kRight), 1/driveScaling)));
			rearRight.set(-1*(Math.pow(joystick.getY(Hand.kRight), 1/driveScaling)));
		}
		else if(joystick.getY(Hand.kRight)*-1<-.15) {
			System.out.print("Right Back?");
			frontRight.set(1*(Math.pow((joystick.getY(Hand.kRight)), 1/driveScaling)));
			rearRight.set(1*(Math.pow(joystick.getY(Hand.kRight), 1/driveScaling)));
		}
		else{
			frontRight.set(0);
			rearRight.set(0);
		}*/