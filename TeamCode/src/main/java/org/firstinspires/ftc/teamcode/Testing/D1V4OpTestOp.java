package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Functions.AutoFunctions;
import org.firstinspires.ftc.teamcode.Functions.FunctionLibrary;
import org.firstinspires.ftc.teamcode.Hardware_Maps.D1V4hardware;

@TeleOp
public class D1V4OpTestOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        D1V4hardware robot = new D1V4hardware(this);
        AutoFunctions auto = new AutoFunctions(robot);
        robot.dcOpenClose.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        boolean fieldCentric = true;
        boolean backButtonPressed = false;
        waitForStart();
        while (opModeIsActive() && !gamepad1.dpad_up) {
            if (!backButtonPressed && gamepad1.back) fieldCentric = !fieldCentric;
            else if (backButtonPressed && !backButtonPressed) backButtonPressed = false;
            double x = gamepad1.left_stick_x;
            double y = gamepad1.left_stick_y;
            double angle = Math.toDegrees(Math.atan2(y,x))+90;
            telemetry.addData("angle:", angle);
            angle = angle - robot.getWorldRotation();
            telemetry.addData("adjustedAngle", angle);
            double hyp = Math.sqrt((x*x) + (y*y));
            telemetry.addData("Rotation", robot.getWorldRotation());
            telemetry.addData("hyp", hyp);

            double rotation = gamepad1.right_stick_x*.5;
            double dX = Math.cos(Math.toRadians(angle))*hyp * -1;
            double dY = Math.sin(Math.toRadians(angle))*hyp;
            robot.move(dX,dY,rotation,1);
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
            if (gamepad1.right_trigger > 0 && !robot.upperLimitSwitch.isPressed()) {
                robot.dcUpDown1.setPower(gamepad1.right_trigger);
                robot.dcUpDown2.setPower(gamepad1.right_trigger);
            } else if (gamepad1.left_trigger > 0 && !robot.lowerLimitSwitch.isPressed()) {
                robot.dcUpDown1.setPower(-gamepad1.left_trigger);
                robot.dcUpDown2.setPower(-gamepad1.left_trigger);
            } else {
                robot.dcUpDown1.setPower(0);
                robot.dcUpDown2.setPower(0);
            }
            if (gamepad1.y) {
                robot.dcInOut.setPower(1);
            } else if (gamepad1.a) {
                robot.dcInOut.setPower(-1);
            } else {
                robot.dcInOut.setPower(0);
            }

            if (gamepad1.left_bumper) {
                robot.dcOpenClose.setPower(-1);
            } else if (gamepad1.right_bumper) {
                robot.dcOpenClose.setPower(1);
            } else {
                robot.dcOpenClose.setPower(0);
            }

            telemetry.update();
        }
        while (opModeIsActive()) {
            auto.gotoPosition(new FunctionLibrary.Point(0,0), 1, 0,0);
            double x = robot.getX();
            double y = robot.getY();
            telemetry.addData("x:", x);
            telemetry.addData("y: ", y);
            telemetry.addData("distance: ", Math.sqrt((y*y) + (x*x)));
            telemetry.update();
        }
    }
}
