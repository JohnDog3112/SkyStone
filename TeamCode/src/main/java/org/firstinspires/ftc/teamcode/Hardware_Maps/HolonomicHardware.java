package org.firstinspires.ftc.teamcode.Hardware_Maps;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HolonomicHardware {
    public DcMotor mFrontRight = null;
    public DcMotor mFrontLeft = null;
    public DcMotor mBackRight = null;
    public DcMotor mBackLeft = null;

    HardwareMap hMap = null;

    public void init(HardwareMap hMap) {
        mFrontRight = hMap.dcMotor.get("mFrontRight");
        mFrontLeft = hMap.dcMotor.get("mFrontLeft");
        mBackRight = hMap.dcMotor.get("mBackRight");
        mBackLeft = hMap.dcMotor.get("mBackLeft");

        mFrontRight.setDirection(DcMotor.Direction.REVERSE);
        mFrontLeft.setDirection(DcMotor.Direction.FORWARD);
        mBackRight.setDirection(DcMotor.Direction.REVERSE);
        mBackLeft.setDirection(DcMotor.Direction.FORWARD);
        mFrontRight.setPower(0);
        mFrontLeft.setPower(0);
        mBackRight.setPower(0);
        mBackLeft.setPower(0);
    }
}
