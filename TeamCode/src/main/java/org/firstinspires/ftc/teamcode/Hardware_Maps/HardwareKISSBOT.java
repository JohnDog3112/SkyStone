package org.firstinspires.ftc.teamcode.Hardware_Maps;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/*
 * Created by Diva's on 01/07/2019.
 */

public class HardwareKISSBOT {

    /* Public OpMode members. */
    public DcMotor leftMotorFront = null;  //AM-2964a-40 motor w/encoder, 280x4 or 1120 ppr
    public DcMotor leftMotorBack   = null;  //AM-2964a-40 motor w/encoder, 280x4 or 1120 ppr
    public DcMotor rightMotorFront = null;  //AM-2964a-40 motor w/encoder, 280x4 or 1120 ppr
    public DcMotor rightMotorBack  = null;  //AM-2964a-40 motor w/encoder, 280x4 or 1120 ppr
    public DcMotor updown = null;


    /* Local OpMode members. */
    HardwareMap hwMap = null;

    /* Initialize standard Hardware interfaces */
    public void init (HardwareMap hwMap){

        // Define and Initialize Motors
        rightMotorFront = hwMap.dcMotor.get("rmf");             //port
        leftMotorFront = hwMap.dcMotor.get("lmf");              //port
        leftMotorFront.setDirection(DcMotor.Direction.REVERSE);
        rightMotorBack = hwMap.dcMotor.get("rmb");              //port
        leftMotorBack = hwMap.dcMotor.get("lmb");               //port
        leftMotorBack.setDirection(DcMotor.Direction.REVERSE);         //port
        updown= hwMap.dcMotor.get("hook");                     //port

        // Set all motors to zero power
        leftMotorFront.setPower(0);
        rightMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorBack.setPower(0);
        updown.setPower(0);


        // Set all motors to use encoders.
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        updown.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }
}