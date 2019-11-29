package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.teamcode.Functions.AutoFunctions;
import org.firstinspires.ftc.teamcode.Functions.FunctionLibrary;
import org.firstinspires.ftc.teamcode.Hardware_Maps.KissHardware;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

@TeleOp
public class MecannumOp extends LinearOpMode {
    private static final float mmPerInch = 25.4f;
    private FunctionLibrary.Point targetPosition = new FunctionLibrary.Point(0,0);
    @Override
    public void runOpMode() throws InterruptedException {
        KissHardware robot = new KissHardware(this);
        AutoFunctions auto = new AutoFunctions(robot);
        waitForStart();
        robot.setRotation(0);
        robot.setPosition(0,0);
        while (opModeIsActive() && !gamepad1.back) {
            robot.move(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, 1);
            FunctionLibrary.Point position = robot.getPosition();
            telemetry.addData("x:",position.x);
            telemetry.addData("y",position.y);
            double gyroAngle = robot.getWorldRotation();
            if (gamepad1.a) {
                robot.setRotation(0);
            }
            if (gamepad1.y)
                targetPosition = position;

            telemetry.addData("Rotation",gyroAngle);
            telemetry.update();
        }
        while(opModeIsActive()) {
            auto.gotoPosition(targetPosition, 1,0);
        }
    }
}
