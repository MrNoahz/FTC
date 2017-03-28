package org.firstinspires.ftc.team10940;

/* Hardware class for Apostrophe's team */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ApostropheHardware {

    public DcMotor rightMotor;
    public DcMotor leftMotor;

    public DcMotor spinny;
    public DcMotor launchy;
    public DcMotor lifty;

    HardwareMap hwMap;

    public void init(HardwareMap hwMap) {
        this.hwMap = hwMap;

        rightMotor = hwMap.dcMotor.get("right");
        rightMotor.setDirection(DcMotor.Direction.FORWARD);

        leftMotor = hwMap.dcMotor.get("left");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);

        spinny = hwMap.dcMotor.get("spinner");
        spinny.setDirection(DcMotorSimple.Direction.FORWARD);

        launchy = hwMap.dcMotor.get("launchy");
        launchy.setDirection(DcMotorSimple.Direction.FORWARD);

        lifty = hwMap.dcMotor.get("lifty");
        lifty.setDirection(DcMotorSimple.Direction.FORWARD);

        rightMotor.setPower(0);
        leftMotor.setPower(0);

        spinny.setPower(0);

        launchy.setPower(0);
        lifty.setPower(0);

        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        spinny.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        launchy.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lifty.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
