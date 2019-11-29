package org.firstinspires.ftc.teamcode.OtherRobots;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware_Maps.DivaDogHardwareMap;

@TeleOp(name="DivaDog", group= "B")
public class DivaDogTeleop extends LinearOpMode {


    @Override
    public void runOpMode() {
        DivaDogHardwareMap robot = new DivaDogHardwareMap();
        robot.init(hardwareMap);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.catapult.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.catapult.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.tounge.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        double dCooldown = time;
        while(opModeIsActive()) {
            robot.rightMotor.setPower(gamepad1.right_stick_y);
            robot.leftMotor.setPower(gamepad1.left_stick_y);
            if (gamepad1.left_trigger > 0 && !robot.catapult.isBusy()) {
                robot.catapult.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.catapult.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.catapult.setPower(1);
                robot.catapult.setTargetPosition(1660);
            }
            if (!robot.catapult.isBusy()) robot.catapult.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            telemetry.addData("position:",robot.catapult.getCurrentPosition());
            telemetry.addData("TargetPosition", robot.catapult.getTargetPosition());
            telemetry.update();
            if (gamepad1.left_bumper && dCooldown < time) {
                robot.tounge.setPower((robot.tounge.getPower()+0.5) % 1);
                dCooldown = time+1;
            }
            if (gamepad1.right_bumper && dCooldown < time) {
                robot.tounge.setPower(((Math.abs(robot.tounge.getPower())+0.5) % 1) * -1);
                dCooldown = time+1;
            }

        }
    }
}
