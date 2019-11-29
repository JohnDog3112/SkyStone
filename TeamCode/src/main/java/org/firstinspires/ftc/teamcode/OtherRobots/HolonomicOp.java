package org.firstinspires.ftc.teamcode.OtherRobots;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware_Maps.HolonomicHardware;

@TeleOp(name = "HolonomicOp", group = "B")
public class HolonomicOp extends LinearOpMode {

    public void runOpMode() {
        double dFrontRight = 0;
        double dFrontLeft = 0;
        double dBackRight = 0;
        double dBackLeft = 0;
        double dMax = 0;
        HolonomicHardware robot = new HolonomicHardware();
        robot.init(hardwareMap);
        waitForStart();
        while(opModeIsActive()) {
            dFrontRight = gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x;
            dFrontLeft = gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x;
            dBackRight = gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x;
            dBackLeft = gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x;
            dMax = Math.abs(dFrontRight);
            if (Math.abs(dFrontLeft) > dMax) dMax = Math.abs(dFrontLeft);
            if (Math.abs(dBackRight) > dMax) dMax = Math.abs(dBackRight);
            if (Math.abs(dBackLeft) > dMax) dMax = Math.abs(dBackLeft);
            if (dMax > 1) {
                dFrontRight = dFrontRight/dMax;
                dFrontLeft = dFrontLeft/dMax;
                dBackRight = dBackRight/dMax;
                dBackLeft = dBackLeft/dMax;
            }
            robot.mFrontRight.setPower(dFrontRight);
            robot.mFrontLeft.setPower(dFrontLeft);
            robot.mBackRight.setPower(dBackRight);
            robot.mBackLeft.setPower(dBackLeft);
        }
    }
}
