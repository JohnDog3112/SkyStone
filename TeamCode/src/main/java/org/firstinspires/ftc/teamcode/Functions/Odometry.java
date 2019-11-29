package org.firstinspires.ftc.teamcode.Functions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//class for running the odometry in a separate thread
public class Odometry implements Runnable {
    //takes a robotconstructor in order to call the updateOdometry function
    private final RobotConstructor robot;
    //takes a LinearOpMode to automaticly shut down when the opMode does
    private final LinearOpMode opMode;
    //constructor class
    public Odometry(RobotConstructor robot, LinearOpMode opMode) {
        this.robot = robot;
        this.opMode = opMode;
    }
    @Override
    public void run() {
        //on start, keep running unless stop is requested
        while (!opMode.isStopRequested()) {
            //call the robot updateOdometry class
            robot.updateOdometry();
            try {
                //pause for 50 ms to prevent overloading of CPU runtime
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
