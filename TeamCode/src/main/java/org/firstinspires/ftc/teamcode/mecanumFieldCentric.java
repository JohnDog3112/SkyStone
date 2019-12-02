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
