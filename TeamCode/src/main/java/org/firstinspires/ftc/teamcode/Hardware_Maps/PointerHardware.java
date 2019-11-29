package org.firstinspires.ftc.teamcode.Hardware_Maps;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class PointerHardware {
    //define the motors
    public final DcMotor mFront;
    public final DcMotor mBack;
    public final DcMotor mRight;
    public final DcMotor mLeft;
    public final DcMotor lift;
    public final DcMotor inout;
    public final DcMotor gripper;

    private double wheelDiameter = 4;
    private double dKp = 0.001;
    private double dMinMoveSpeed = 0.1;
    //this will take the inputs on initialization of the hMap.
    public PointerHardware(HardwareMap hMap) {
        mFront = hMap.dcMotor.get("mFront");
        mBack = hMap.dcMotor.get("mBack");
        mRight = hMap.dcMotor.get("mRight");
        mLeft = hMap.dcMotor.get("mLeft");
        lift = hMap.dcMotor.get("lift");
        inout = hMap.dcMotor.get("inout");
        gripper = hMap.dcMotor.get("gripper");

        mFront.setDirection(DcMotor.Direction.FORWARD);
        mBack.setDirection(DcMotor.Direction.REVERSE);
        mLeft.setDirection(DcMotor.Direction.FORWARD);
        mRight.setDirection(DcMotor.Direction.REVERSE);
        mFront.setPower(0);
        mBack.setPower(0);
        mRight.setPower(0);
        mLeft.setPower(0);
        lift.setPower(0);

    }
}
