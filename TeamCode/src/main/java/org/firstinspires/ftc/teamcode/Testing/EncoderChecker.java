package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware_Maps.RotatedHolonomicHardware;

@TeleOp
public class EncoderChecker extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        RotatedHolonomicHardware robot = new RotatedHolonomicHardware(this);
        waitForStart();
        while (opModeIsActive()) {
            robot.mFront.setPower(gamepad1.left_stick_y);
            robot.mBack.setPower(gamepad1.right_stick_y);
            robot.mLeft.setPower(gamepad2.left_stick_y);
            robot.mRight.setPower(gamepad2.right_stick_y);



            telemetry.addData("mFront", robot.mFront.getCurrentPosition());
            telemetry.addData("mBack", robot.mBack.getCurrentPosition());
            telemetry.addData("mLeft", robot.mLeft.getCurrentPosition());
            telemetry.addData("mRight", robot.mRight.getCurrentPosition());

            telemetry.update();
        }
    }
}
