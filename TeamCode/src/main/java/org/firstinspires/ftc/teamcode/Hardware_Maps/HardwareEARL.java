package org.firstinspires.ftc.teamcode.Hardware_Maps;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/*
 * Created by Diva's on 01/07/2019.
 */

public class HardwareEARL {

    /* Public OpMode members. */
    public DcMotor leftMotorFront = null;  //AM-2964a-40 motor w/encoder, 280x4 or 1120 ppr
    public DcMotor leftMotorBack   = null;  //AM-2964a-40 motor w/encoder, 280x4 or 1120 ppr
    public DcMotor rightMotorFront = null;  //AM-2964a-40 motor w/encoder, 280x4 or 1120 ppr
    public DcMotor rightMotorBack  = null;  //AM-2964a-40 motor w/encoder, 280x4 or 1120 ppr
    public DcMotor side2side       = null;
    public DcMotor inandout        = null;
    public DcMotor updown          = null;
    public Servo bucket          = null;

    /* Local OpMode members. */
    HardwareMap hwMap = null;

    /* Initialize standard Hardware interfaces */
    public void init (HardwareMap hwMap){

        // Define and Initialize Motors
        rightMotorFront = hwMap.dcMotor.get("rmf");             //port
        rightMotorFront.setDirection(DcMotor.Direction.REVERSE);
        leftMotorFront = hwMap.dcMotor.get("lmf");              //port
        //leftMotorFront.setDirection(DcMotor.Direction.REVERSE);
        rightMotorBack = hwMap.dcMotor.get("rmb");              //port
        //rightMotorBack.setDirection(DcMotor.Direction.REVERSE);
        leftMotorBack = hwMap.dcMotor.get("lmb");               //port
        side2side= hwMap.dcMotor.get ("side");                  //port
        inandout = hwMap.dcMotor.get("io");                     //port
        updown= hwMap.dcMotor.get("hook");                      //port
        bucket = hwMap.get(Servo.class, "buck");      //port

        // Set all motors to zero power
        leftMotorFront.setPower(0);
        rightMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorBack.setPower(0);
        side2side.setPower(0);
        inandout.setPower(0);
        updown.setPower(0);

        // Set all motors to use encoders.
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        side2side.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        inandout.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        updown.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}