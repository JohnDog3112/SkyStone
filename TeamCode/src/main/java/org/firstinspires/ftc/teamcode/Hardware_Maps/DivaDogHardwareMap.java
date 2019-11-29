package org.firstinspires.ftc.teamcode.Hardware_Maps;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DivaDogHardwareMap {
    public DcMotor leftMotor = null;
    public DcMotor rightMotor = null;
    public DcMotor catapult = null;
    public DcMotor tounge = null;
    public void init (HardwareMap hMap) {
        leftMotor = hMap.dcMotor.get("leftMotor");
        rightMotor = hMap.dcMotor.get("rightMotor");
        catapult = hMap.dcMotor.get("catapult");
        tounge = hMap.dcMotor.get("tounge");

        leftMotor.setDirection(DcMotor.Direction.REVERSE);
    }
}
