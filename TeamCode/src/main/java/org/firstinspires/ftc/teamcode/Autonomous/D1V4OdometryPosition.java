package org.firstinspires.ftc.teamcode.Autonomous;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.teamcode.Functions.AutoFunctions;
import org.firstinspires.ftc.teamcode.Functions.FunctionLibrary;
import org.firstinspires.ftc.teamcode.Hardware_Maps.D1V4hardware;
@Autonomous
public class D1V4OdometryPosition extends LinearOpMode {
    private final double mmPerInch = 25.4;
    @Override
    public void runOpMode() throws InterruptedException {
        D1V4hardware robot = new D1V4hardware(this);
        AutoFunctions auto = new AutoFunctions(robot);
        FunctionLibrary.motorMovement inoutControl = new FunctionLibrary.motorMovement(robot.dcInOut);
        FunctionLibrary.motorMovement upDownControl = new FunctionLibrary.motorMovement(robot.dcUpDown1, robot.dcUpDown2);
        FunctionLibrary.motorMovement openCloseControl = new FunctionLibrary.motorMovement(robot.dcOpenClose);
        double leftRightCenterOffset = 8.5;
        double backFrontCenterOffset = 7;
        robot.initVuforia(hardwareMap);
        waitForStart();
        robot.SkystoneTrackables.activate();
        robot.setPosition(-(72-backFrontCenterOffset),-(24-leftRightCenterOffset));
        robot.setRotation(90);
        FunctionLibrary.Point SkystoneTarget = null;
        robot.setRotation(90);
        int nSwitch = 0;
        while (opModeIsActive()) {
            FunctionLibrary.Point destination = null;
            double result = 0;
            switch(nSwitch) {
                case 0:
                    result = upDownControl.move_using_encoder(4557,1,5,50);
                    if (result < 0) nSwitch++;
                    break;
                case 1:
                    result = inoutControl.move_using_encoder(405,0.5,5,20);
                    if (result < 0 ) nSwitch++;
                    break;
                case 2:
                    result = openCloseControl.move_using_encoder(-1531,1,4,10);
                    if (result < 0) nSwitch++;
                    break;
                case 3:
                    destination = new FunctionLibrary.Point(-(36+robot.CameraForwardDisplacement)-6, -(30-robot.CameraLeftDisplacement)-6);
                    result = auto.gotoPosition(destination,1,1,90);
                    if (result < 0) {
                        resetStartTime();
                        nSwitch++;
                    }
                    break;
                case 4:
                    robot.updateVuforia();
                    if(robot.VuMarkPositions.containsKey("Stone Target")) {
                        resetStartTime();
                        VectorF stonePos = robot.VuMarkPositions.get("Stone Target");
                        SkystoneTarget = new FunctionLibrary.Point((-stonePos.get(0)/mmPerInch)+robot.getX(),-(stonePos.get(1)/mmPerInch)+robot.getY());
                        Log.d("BothAutoVuforia", "x: " + SkystoneTarget.x + "y: " + SkystoneTarget.y);
                        nSwitch++;
                    } else if (getRuntime() > 2) {
                        SkystoneTarget = new FunctionLibrary.Point(-36,-48);
                        resetStartTime();
                        nSwitch++;
                    }
                    break;
                case 5:
                    destination = new FunctionLibrary.Point(-(36+robot.CameraForwardDisplacement)-6, SkystoneTarget.y+2);
                    result = auto.gotoPosition(destination,1,1,90);
                    if (result < 0) nSwitch++;
                    break;
                case 6:
                    destination = new FunctionLibrary.Point(SkystoneTarget.x-4, SkystoneTarget.y+2);
                    result = auto.gotoPosition(destination,0.5,1,90);
                    if (result < 0) nSwitch++;
                    break;
                case 7:
                    result = openCloseControl.move_using_encoder(6300, 1, 6,10);
                    if (result < 0) nSwitch++;
                    break;
                case 8:
                    destination = new FunctionLibrary.Point(-54,SkystoneTarget.y+2);
                    result = auto.gotoPosition(destination,1,1,90);
                    if (result < 0) nSwitch++;
                    break;
                case 9:
                    destination = new FunctionLibrary.Point(-54,5);
                    result = auto.gotoPosition(destination,1,1,0);
                    if (result < 0) nSwitch++;
                    break;
                case 10:
                    result = upDownControl.move_using_encoder(5080,1,5,10);
                    if (result < 0) nSwitch++;
                    break;
                case 11:
                    destination = new FunctionLibrary.Point(-20,10);
                    result = auto.gotoPosition(destination,1,1,0);
                    if (result < 0) nSwitch++;
                    break;
                case 12:
                    destination = new FunctionLibrary.Point(-25,15);
                    result = auto.gotoPosition(destination,1,10,0);
                    if (result < 0) nSwitch++;
                    break;
                case 13:
                    result = upDownControl.move_using_encoder(-9637,1,6,10);
                    if (result < 0) nSwitch++;
                    break;



            }
        }
    }
}