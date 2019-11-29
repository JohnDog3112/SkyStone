package org.firstinspires.ftc.teamcode.OtherRobots;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(group = "B")
public class Tank extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {


        DcMotor mLeft = hardwareMap.dcMotor.get("left_drive");
        DcMotor mRight = hardwareMap.dcMotor.get("right_drive");
        mLeft.setDirection(DcMotor.Direction.REVERSE);
        waitForStart();
        while (opModeIsActive()) {
            mLeft.setPower(gamepad1.left_stick_y);
            mRight.setPower(gamepad1.right_stick_y);
        }

    }
}
