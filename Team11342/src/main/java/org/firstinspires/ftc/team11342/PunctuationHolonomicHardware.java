package org.firstinspires.ftc.team11342;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/*

Layout of the motors:

     Motor 0     Motor 1
         / _____ \
          |    |
          |    |
        \ ------ /
     Motor 3    Motor 2

 */

public class PunctuationHolonomicHardware {
    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor backLeftMotor;
    public DcMotor backRightMotor;

    public DcMotor motor0;
    public DcMotor motor1;
    public DcMotor motor2;
    public DcMotor motor3;

    public DcMotor spinny;

    private HardwareMap hwMap;

    public void init(HardwareMap hMap) {
        hwMap = hMap;

        motor0 = hwMap.dcMotor.get("motor0");
        motor1 = hwMap.dcMotor.get("motor1");
        motor2 = hwMap.dcMotor.get("motor2");
        motor3 = hwMap.dcMotor.get("motor3");

        spinny = hwMap.dcMotor.get("spinny");

        motor0.setPower(0);
        motor1.setPower(0);
        motor2.setPower(0);
        motor3.setPower(0);

        spinny.setPower(0);

        motor0.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        spinny.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        setDirection(0);

//        frontLeftMotor = hwMap.dcMotor.get("frontLeft");
//        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
//
//        frontRightMotor = hwMap.dcMotor.get("frontRight");
//        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
//
//        backLeftMotor = hwMap.dcMotor.get("backLeft");
//        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
//
//        backRightMotor = hwMap.dcMotor.get("backRight");
//        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
//
//        frontRightMotor.setPower(0);
//        frontLeftMotor.setPower(0);
//        backRightMotor.setPower(0);
//        backLeftMotor.setPower(0);
//
//        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    //TODO Change to enums
    public void setDirection(int i) {
         /*
            0-3 in a Counterclockwise direction

            0: Forward
            1: Right
            2: Backward
            3: Left
            4: Spin clockwise
            5: spin counterclockwise
        */

        switch(i) {
            case 0:
                motor0.setDirection(DcMotor.Direction.REVERSE);
                motor1.setDirection(DcMotor.Direction.FORWARD);
                motor2.setDirection(DcMotor.Direction.FORWARD);
                motor3.setDirection(DcMotor.Direction.REVERSE);

                frontLeftMotor  = motor0;
                frontRightMotor = motor1;
                backRightMotor  = motor2;
                backLeftMotor   = motor3;
                break;
            case 1:
                motor0.setDirection(DcMotor.Direction.REVERSE);
                motor1.setDirection(DcMotor.Direction.REVERSE);
                motor2.setDirection(DcMotor.Direction.FORWARD);
                motor3.setDirection(DcMotor.Direction.FORWARD);

                frontLeftMotor  = motor1;
                frontRightMotor = motor2;
                backLeftMotor   = motor0;
                backRightMotor  = motor3;
                break;
            case 2:
                motor0.setDirection(DcMotor.Direction.REVERSE);
                motor1.setDirection(DcMotor.Direction.FORWARD);
                motor2.setDirection(DcMotor.Direction.FORWARD);
                motor3.setDirection(DcMotor.Direction.REVERSE);

                frontLeftMotor  = motor0;
                frontRightMotor = motor1;
                backRightMotor  = motor2;
                backLeftMotor   = motor3;
                break;
            case 3:
                motor0.setDirection(DcMotor.Direction.FORWARD);
                motor1.setDirection(DcMotor.Direction.FORWARD);
                motor2.setDirection(DcMotor.Direction.REVERSE);
                motor3.setDirection(DcMotor.Direction.REVERSE);

                frontLeftMotor  = motor3;
                frontRightMotor = motor0;
                backLeftMotor   = motor2;
                backRightMotor  = motor1;
                break;
            case 4:
                motor0.setDirection(DcMotor.Direction.REVERSE);
                motor1.setDirection(DcMotor.Direction.REVERSE);
                motor2.setDirection(DcMotor.Direction.REVERSE);
                motor3.setDirection(DcMotor.Direction.REVERSE);
                break;
            default:
            case 5:
                motor0.setDirection(DcMotor.Direction.FORWARD);
                motor1.setDirection(DcMotor.Direction.FORWARD);
                motor2.setDirection(DcMotor.Direction.FORWARD);
                motor3.setDirection(DcMotor.Direction.FORWARD);
                break;
        }
    }
}
