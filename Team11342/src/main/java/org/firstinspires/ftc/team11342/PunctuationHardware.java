package org.firstinspires.ftc.team11342;

/* Hardware class for Punctuation's team */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class PunctuationHardware {
    public DcMotor rightMotor;
    public DcMotor leftMotor;

    public DcMotor spinner;

    private HardwareMap hwMap;

    public void init(HardwareMap hMap) {
        hwMap = hMap;

        rightMotor = hwMap.dcMotor.get("right");
        rightMotor.setDirection(DcMotor.Direction.FORWARD);

        leftMotor = hwMap.dcMotor.get("left");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);

        spinner = hwMap.dcMotor.get("spinner");
        spinner.setDirection(DcMotorSimple.Direction.REVERSE);

        rightMotor.setPower(0);
        leftMotor.setPower(0);

        spinner.setPower(0);

        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        spinner.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
