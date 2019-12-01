package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware_Maps.D1V4hardware;

@TeleOp
public class D1V4Op extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        D1V4hardware robot = new D1V4hardware(this);
        robot.dcOpenClose.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();
        while (opModeIsActive()) {
            robot.move(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, 1);
            telemetry.addData("x: ", robot.getX());
            telemetry.addData("y: ", robot.getY());
            telemetry.update();
            if (gamepad1.dpad_left) {
                robot.csRight.setPower(-1);
                robot.csLeft.setPower(-1);
            } else if(gamepad1.dpad_right) {
                robot.csRight.setPower(1);
                robot.csLeft.setPower(1);
            } else {
                robot.csRight.setPower(0);
                robot.csLeft.setPower(0);
            }
            if (gamepad1.dpad_up && !robot.upperLimitSwitch.isPressed()) {
                robot.dcUpDown1.setPower(1);
                robot.dcUpDown2.setPower(1);
            } else if (gamepad1.dpad_down && !robot.lowerLimitSwitch.isPressed()) {
                robot.dcUpDown1.setPower(-1);
                robot.dcUpDown2.setPower(-1);
            } else {
                robot.dcUpDown1.setPower(0);
                robot.dcUpDown2.setPower(0);
            }
            robot.dcInOut.setPower(gamepad1.right_trigger-gamepad1.left_trigger);
            if (gamepad1.right_bumper) {
                robot.sHook.setPosition(0.7);
            } else if (gamepad1.left_bumper) {
                robot.sHook.setPosition(0.15);
            }

            if (gamepad1.a) {
                robot.dcOpenClose.setPower(-1);
            } else if (gamepad1.b) {
                robot.dcOpenClose.setPower(1);
            } else {
                robot.dcOpenClose.setPower(0);
            }

            telemetry.update();
        }
    }
}
