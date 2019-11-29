package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware_Maps.KissHardware;


@TeleOp
public class mecanumFieldCentric extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        KissHardware robot = new KissHardware(this);


        waitForStart();
        double dOffset = 0;
        while (opModeIsActive()) {
            double angle = 0;
            double x = gamepad1.left_stick_x;
            double y = gamepad1.left_stick_y;
            angle = Math.toDegrees(Math.atan2(y,x))+90;
            if (gamepad1.a) robot.setRotation(0);
            telemetry.addData("angle:", angle);
            angle = angle - robot.getWorldRotation();
            telemetry.addData("adjustedAngle", angle);
            double hyp = Math.sqrt((x*x) + (y*y));
            telemetry.addData("Rotation", robot.getWorldRotation());
            telemetry.addData("hyp", hyp);

            double rotation = gamepad1.right_stick_x*.5;
            double dX = Math.sin(Math.toRadians(angle))*hyp;
            double dY = Math.cos(Math.toRadians(angle))*hyp *-1;
            robot.move(dX,dY,rotation,1);
            /*
            double pFront = Math.sin(Math.toRadians(angle)) * hyp - rotation;
            double pBack = Math.sin(Math.toRadians(angle)) * hyp + rotation;
            double pLeft = Math.cos(Math.toRadians(angle)) * hyp * -1 - rotation;
            double pRight = Math.cos(Math.toRadians(angle)) * hyp * -1 + rotation;

            double scaler = Math.max(Math.max(Math.abs(pFront),Math.abs(pBack)),Math.max(Math.abs(pLeft),Math.abs(pRight)));
            telemetry.addData("max",scaler);
            if (scaler > 1) {
                scaler = 1/scaler;
                telemetry.addData("scaler", scaler);
                pFront *= scaler;
                pBack *= scaler;
                pLeft *= scaler;
                pRight *= scaler;
            }
            telemetry.addData("pFront",pFront);
            telemetry.addData("pBack",pBack);
            telemetry.addData("pLeft", pLeft);
            telemetry.addData("pRight",pRight);
            robot.mFront.setPower(pFront);
            robot.mBack.setPower(pBack);
            robot.mLeft.setPower(pLeft);
            robot.mRight.setPower(pRight);

            telemetry.addData("LeftRightPower",Math.cos(Math.toRadians(angle)) * hyp);
            telemetry.addData("FrontBackPower", Math.sin(Math.toRadians(angle))*hyp);

            */
            telemetry.update();

        }

    }


}
