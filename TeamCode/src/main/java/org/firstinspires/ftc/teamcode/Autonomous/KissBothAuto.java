package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.teamcode.Functions.AutoFunctions;
import org.firstinspires.ftc.teamcode.Functions.FunctionLibrary;
import org.firstinspires.ftc.teamcode.Hardware_Maps.KissHardware;

public class KissBothAuto extends LinearOpMode {
    private final double dMTFOffset = 7.25;
    private int nSwitch = 0;
    FunctionLibrary.Point destination = new FunctionLibrary.Point(0,0);
    FunctionLibrary.Point SkystoneTarget = new FunctionLibrary.Point(0,0);
    private double result = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        KissHardware robot = new KissHardware(this);
        AutoFunctions auto = new AutoFunctions(robot);
        waitForStart();
        robot.setPosition(-72+dMTFOffset,-12);
        robot.setRotation(90);
        while (opModeIsActive()) {
            switch(nSwitch) {
                case 0:
                    destination = new FunctionLibrary.Point(-36-robot.CameraForwardDisplacement,-24-8-robot.CameraLeftDisplacement);
                    result = auto.gotoPosition(destination, 1, 0.5,90);
                    if (result < 0) {
                        resetStartTime();
                        nSwitch++;
                    }
                    break;
                case 1:
                    robot.updateVuforia();
                    if(robot.VuMarkPositions.containsKey("Stone Target")) {
                        resetStartTime();
                        VectorF stonePos = robot.VuMarkPositions.get("Stone Target");
                        SkystoneTarget = new FunctionLibrary.Point(stonePos.get(0),stonePos.get(1));
                        nSwitch++;
                    } else if (getRuntime() > 2) {
                        SkystoneTarget = new FunctionLibrary.Point(-24,-48+4);
                        resetStartTime();
                        nSwitch++;
                    }
                    break;
                case 2:
                    destination = new FunctionLibrary.Point(-36-robot.CameraForwardDisplacement,-18);
                    result = auto.gotoPosition(destination,1,1,90);
                    if (result < 0) nSwitch++;
                    break;
                case 3:
                    destination = new FunctionLibrary.Point(0,-18);
                    result = auto.gotoPosition(destination,1,1,90);
                    if (result < 0) nSwitch++;
                    break;
                case 4:
                    destination = new FunctionLibrary.Point(0,-24);
                    result = auto.gotoPosition(destination,1,1,90);
                    if (result < 0) nSwitch++;
                    break;
                case 5:
                    destination = new FunctionLibrary.Point(0,SkystoneTarget.y);
                    result = auto.gotoPosition(destination,1,1,-90);
                    if (result < 0) nSwitch++;
                    break;
                case 6:
                    destination = new FunctionLibrary.Point(SkystoneTarget.x-12, SkystoneTarget.y);
                    result = auto.gotoPosition(destination,1,1,-90);
                    if (result < 0) nSwitch++;
                    break;
                case 7:
                    destination = new FunctionLibrary.Point(-48, -12);
                    result = auto.gotoPosition(destination,1,1,0);
                    if (result < 0) nSwitch++;
                    break;
                case 8:
                    destination = new FunctionLibrary.Point(-48,24);
                    result = auto.gotoPosition(destination,1,1,0);
                    if (result < 0) nSwitch++;
                    break;
                case 9:
                    destination = new FunctionLibrary.Point(-48,18);
                    result = auto.gotoPosition(destination,1,1,0);
                    if (result < 0) nSwitch++;
                    break;
                case 10:
                    destination = new FunctionLibrary.Point(-24,24);
                    result = auto.gotoPosition(destination,1,1,-90);
                    if (result < 0) nSwitch++;
                    break;
                case 11:
                    destination = new FunctionLibrary.Point(0,24);
                    result = auto.gotoPosition(destination,1,1,-45);
                    if (result < 0) nSwitch++;
                    break;
                case 12:
                    destination = new FunctionLibrary.Point(-24,48);
                    result = auto.gotoPosition(destination,1,1,-45);
                    if (result < 0) nSwitch++;
                    break;
                case 13:
                    destination = new FunctionLibrary.Point(-18,48);
                    result = auto.gotoPosition(destination,1,1,-90);
                    if (result < 0) nSwitch++;
                    break;
                case 14:
                    destination = new FunctionLibrary.Point(-36,48);
                    result = auto.gotoPosition(destination,1,1,-90);
                    break;
                case 15:
                    destination = new FunctionLibrary.Point(-36,0);
                    result = auto.gotoPosition(destination,1,1,180);
                    if (result < 0) nSwitch++;
                    break;



            }
        }
    }
}
