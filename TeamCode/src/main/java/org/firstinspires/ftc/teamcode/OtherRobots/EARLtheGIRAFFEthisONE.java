package org.firstinspires.ftc.teamcode.OtherRobots;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware_Maps.HardwareEARL;

/**
 * Created by Diva on 11/04/2018.
 * This is the teleop program for Flower which has an arm to pick up balls and score them.
 */
@TeleOp(name = "EARLtheGIRAFFE(This One)", group = "B")
public class EARLtheGIRAFFEthisONE extends LinearOpMode {

    public void runOpMode() {
        double dRightLeft;
        double dForwardBack;
        double dCwCcw;
        double dK = .50;
        double dFrontLeft;
        double dFrontRight;
        double dBackLeft;
        double dBackRight;
        double dMax;
        double dNone = 0;
        double dTime = 0;
        // encoder counts target when we put the updown up
        int nBallscrewUp = -4650;
        // Down position is probably zero
        int nBallScrewDown =0;
        int nDir =0;
        boolean bLeftBumperLast =false;
        boolean bRightBumperLast = false;

        HardwareEARL robot = new HardwareEARL();

        robot.init(hardwareMap);

        // Set back to run without encoder mode for teleop
        robot.rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.updown.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.inandout.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            // Set up the game controller in Halo format, where left stick is ForwardBack and LeftRight
            // The right stick is rotation.
            // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)

            //dForwardBack = scaleInput(gamepad1.left_stick_y);
            //dRightLeft = scaleInput(+gamepad1.left_stick_x);
            dForwardBack = scaleInput(-gamepad1.left_stick_x);
            dRightLeft = scaleInput(-gamepad1.left_stick_y);
            dCwCcw = scaleInput(-gamepad1.right_stick_x);

            dCwCcw = dK * dCwCcw; //This is scaling the rotation.

            dFrontLeft = dForwardBack + dCwCcw + dRightLeft;
            dFrontRight = dForwardBack - dCwCcw - dRightLeft;
            dBackLeft = dForwardBack + dCwCcw - dRightLeft;
            dBackRight = dForwardBack - dCwCcw + dRightLeft;

            dMax = Math.abs(dFrontLeft);

            if (Math.abs(dFrontRight) > dMax) {
                dMax = Math.abs(dFrontRight); }
            if (Math.abs(dBackLeft) > dMax) {
                dMax = Math.abs(dBackLeft); }
            if (Math.abs(dBackRight) > dMax) {
                dMax = Math.abs(dBackRight); }

            if (dMax > 1) {
                dFrontLeft = dFrontLeft / dMax;
                dFrontRight = dFrontRight / dMax;
                dBackLeft = dBackLeft / dMax;
                dBackRight = dBackRight / dMax; }

            if (gamepad1.left_trigger>.05) {
                // Move tongue out
                robot.side2side.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.side2side.setPower(gamepad1.left_trigger); }
            else if (gamepad1.right_trigger>.05) {
                // Move tongue in
                robot.side2side.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.side2side.setPower(-gamepad1.right_trigger); }
            else{
                // Stop moving tongue
                robot.side2side.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                robot.side2side.setPower(0); }
            // Y moves updown up
            if (gamepad1.y==true){
                robot.updown.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.updown.setPower(-.75); }
            // else A moves updown down
            else if (gamepad1.a==true){
                robot.updown.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.updown.setPower(.75); }
            // else turn the motor off
            else {
                robot.updown.setPower(0); }

            if (gamepad1.dpad_up==true){
                robot.inandout.setPower(-1);}
            else if (gamepad1.dpad_down==true){
                //robot.side2side.setDirection(DcMotor.Direction.REVERSE);
                robot.inandout.setPower(1);}
            else{
                robot.inandout.setPower(0);}

            if (gamepad1.left_bumper==true)
                robot.bucket.setPosition(0);
            else
                robot.bucket.setPosition(1);

            //the left bumper was just pushed so the bucket servo will open
            /*if (gamepad1.left_bumper==true){
                robot.bucket.setPosition(1); }
            //the right bumper was just pushed so the bucket servo will close
            else if (gamepad1.right_bumper == true) {
                robot.bucket.setPosition(0);}
            else{
                robot.bucket.setPower(0);}
            */
            robot.leftMotorFront.setPower(dFrontLeft);
            robot.leftMotorBack.setPower(dBackLeft);
            robot.rightMotorFront.setPower(dFrontRight);
            robot.rightMotorBack.setPower(dBackRight);

            telemetry.addData("Screw:",robot.updown.getCurrentPosition());
            telemetry.addData("In/Out",robot.inandout.getCurrentPosition());
            telemetry.addData("RightFront",robot.rightMotorFront.getCurrentPosition());
            telemetry.addData("RightBack", robot.rightMotorBack.getCurrentPosition());
            telemetry.addData("LeftFront",robot.leftMotorFront.getCurrentPosition());
            telemetry.addData("LeftBack",robot.leftMotorBack.getCurrentPosition());
            telemetry.addData("Arm",robot.side2side.getCurrentPosition());
            telemetry.update();

            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop

        }
    }

    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal) {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);
        if (index < 0) {
            index = -index;
        } else if (index > 16) {
            index = 16;
        }

        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        return dScale;
    }
}
