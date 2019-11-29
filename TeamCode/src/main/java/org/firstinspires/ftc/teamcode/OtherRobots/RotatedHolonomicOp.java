package org.firstinspires.ftc.teamcode.OtherRobots;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware_Maps.PointerHardware;

@TeleOp(name = "RotatedHolonomicOp", group = "B")
public class RotatedHolonomicOp extends LinearOpMode {

    public void runOpMode() {
        double dFront = 0;
        double dBack = 0;
        double dRight = 0;
        double dLeft = 0;
        double dMax = 0;
        PointerHardware robot = new PointerHardware(hardwareMap);
        waitForStart();
        double power = 1;
        while(opModeIsActive()) {
            dFront = -gamepad1.left_stick_x + gamepad1.right_stick_x;
            dBack = -gamepad1.left_stick_x - gamepad1.right_stick_x;
            dRight = gamepad1.left_stick_y - gamepad1.right_stick_x;
            dLeft = gamepad1.left_stick_y + gamepad1.right_stick_x;
            dMax = Math.abs(dFront);
            if (Math.abs(dBack) > dMax) dMax = Math.abs(dBack);
            if (Math.abs(dRight) > dMax) dMax = Math.abs(dRight);
            if (Math.abs(dLeft) > dMax) dMax = Math.abs(dLeft);
            if (dMax > power) {
                dMax = power/dMax;
                dFront = dFront*dMax;
                dBack = dBack*dMax;
                dRight = dRight*dMax;
                dLeft = dLeft*dMax;
            }
            robot.mFront.setPower(dFront);
            robot.mBack.setPower(dBack);
            robot.mRight.setPower(dRight);
            robot.mLeft.setPower(dLeft);
            robot.lift.setPower(gamepad1.right_trigger - gamepad1.left_trigger);

            if (gamepad1.left_bumper) robot.gripper.setPower(-1);
            if (gamepad1.right_bumper) robot.gripper.setPower(1);
            if (!gamepad1.right_bumper && !gamepad1.left_bumper) robot.gripper.setPower(0);

            if (gamepad1.dpad_up) robot.inout.setPower(0.35);
            if (gamepad1.dpad_down) robot.inout.setPower(-0.35);
            if (!gamepad1.dpad_up && !gamepad1.dpad_down) robot.inout.setPower(0);


        }
    }
}
