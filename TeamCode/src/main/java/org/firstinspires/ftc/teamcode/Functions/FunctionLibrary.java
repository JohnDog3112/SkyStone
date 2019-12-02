package org.firstinspires.ftc.teamcode.Functions;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

//a class dedicated to various class or functions needed for programming
public class FunctionLibrary {
    //a class that can be used to control any given motor through encoder ticks
    public static class motorMovement {
        //define the motor
        private final DcMotor motor;
        private int nState = 0;
        ElapsedTime timer = new ElapsedTime();
        //define the inputs needed to create a new instance of the class
        public motorMovement(DcMotor motor) {
            //take motor input and assign it to the stored motor variable
            this.motor = motor;
        }
        //allows the motor to be moved using encoder ticks
        // 0: Starting, reset encoders
        // 1: Check if encoders have been reset. If they aren't, reset them again. If they are, move on to the next state
        // 2: encoders have been reset, start the movement
        // -1: movement is done
        // -2: program timeout
        public int move_using_encoder(int ticks, double power, double timeout) {
            int nReturn = 0;
            switch (nState) {
                case 0:
                    //reset the encoders
                    motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    //reset the timer for the timeout
                    timer.reset();
                    //set the return to go to the next state
                    nReturn = 1;
                    nState = 1;
                    break;
                case 1:
                    //check if encoders have been reset properly, if not reset them again
                    if (motor.getCurrentPosition() < 100) {
                        nReturn = 2;
                        nState = 2;
                    } else {
                        nReturn = 1;
                        nState = 1;
                        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    }
                    break;
                case 2:
                    //check if the program has ran past the timeout, if so, stop the program and return timedout
                    if (timeout*1000<timer.milliseconds()) {
                        nState = 0;
                        return -2;
                    }
                    //set the motors power and position
                    motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    motor.setPower(power);
                    motor.setTargetPosition(ticks);
                    //check if the motor has reached it's target
                    if (Math.abs(motor.getCurrentPosition()-ticks)<100) {
                        //stop the motor
                        motor.setPower(0);
                        //tell the autonomous that the movement is done
                        nReturn = -1;
                        nState = 0;
                    }

            }
            return nReturn;
        }
    }
    //function for retrieving the heading from the gyroscope given an offest and gyro
    public static double GetYaw(double dOffsetgyro, BNO055IMU imu){
        double dFixCurHeading;
        //grab the imu orientation and apply the given offset
        dFixCurHeading = -(imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle-dOffsetgyro);

        //wrap the angle to fit within -180 and 180 degrees
        while (dFixCurHeading>180) {
            dFixCurHeading = dFixCurHeading-360;
        }

        while (dFixCurHeading<-180) {
            dFixCurHeading = dFixCurHeading+360;
        }
        //return the values it found
        return dFixCurHeading;
    }

    //function for wrapping a given angle to be between -180 and 180 degrees
    public static double WrapAngleDegrees(double degrees) {
        // if degrees is positive then divide it by 360 and take the remainder
        //this wraps the rotation to between 0 and 360
        degrees = degrees > 0 ? degrees = degrees%360 : degrees;
        //if degrees is negative then divide it by -360 and take the remainder
        //this wraps the rotation to between -360 and 0
        degrees = degrees < 0 ? degrees = degrees%-360 : degrees;

        //if degrees is greater than 180, subtract 360 to make it be between -180 and 180
        degrees = degrees > 180 ? degrees = degrees-360 : degrees;
        //if degrees is less than 180, add 360 to make it between -180 and 180
        degrees = degrees < 180 ? degrees = degrees+360 : degrees;

        //return the value
        return degrees;
    }
    //constructor class for a point containing an x and y position
    public static class Point {
        public double x;
        public double y;
        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    //lineCircleintersection function for PurePursuit which I haven't implemented yet
    public static ArrayList<Point> lineCircleIntersection(Point center, double Radius, Point linePoint1, Point linePoint2) {

        //sets the line points to be relative to the center point
        Point localPoint1 = new Point(linePoint1.x-center.x, linePoint1.y-center.y);
        Point localPoint2 = new Point(linePoint2.x-center.x, linePoint2.y-center.y);

        //make sure that the distance slope between points isn't too small so we can calculate slope
        localPoint2.x = abs(localPoint1.x-localPoint2.x) < 0.005 ? localPoint2.x+0.003 : localPoint2.x;
        localPoint2.y = abs(localPoint1.y-localPoint2.y) < 0.005 ? localPoint2.y+0.003 : localPoint2.y;

        //define m in y=mx+b  for a linear line
        double m = (localPoint2.y-localPoint1.y)/(localPoint2.x-localPoint2.x);

        //define b in y=mx+B for a linear line
        double b = localPoint2.y-m*localPoint2.x;

        //set Radius equal to r
        double r = Radius;

        //create an arraylist of points as there can be 0, 1, or 2 points
        ArrayList<Point> Points = new ArrayList<>();
        try {
            //define parts A, B, and C of an equation similar to the quadratic formula
            double partA = m*b;
            double partB = sqrt(pow(Radius,2) + pow(m,2) * pow(Radius,2) - pow(b,2));
            double partC = pow(m,2) + 1;
            //get the x of one point
            double x1 = (-partA + partB)/partC;
            //get the x of the other point
            double x2 = (partA + partB)/partC * -1;

            //transition local x coordinates back to global and calculate y using y = mx+b
            Point globalPoint1 = new Point(x1+center.x, (x1*m + b) + center.y);
            Point globalPoint2 = new Point(x2+center.x,(x2*m+b) + center.y);

            //find the line point with the minimum x
            double minX = min(linePoint1.x,linePoint2.x);
            //find the line point with the maximum x
            double maxX = max(linePoint1.x, linePoint2.x);

            //check if the point we found is on the line
            //if so, add it to the list of points found
            if (globalPoint1.x > minX && globalPoint1.x < maxX) {
                Points.add(globalPoint1);
            }
            //check if the other point we found is on the line
            //if so, add it to the list of points found
            if (globalPoint2.x > minX && globalPoint2.x < maxX) {
                Points.add(globalPoint2);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //return the points that were found
        return Points;
    }

}
