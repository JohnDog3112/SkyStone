package org.firstinspires.ftc.teamcode.Calibrations;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.ArrayList;
import java.util.Map;

@TeleOp
public class CheckAllHardwareDevices extends LinearOpMode {
    String[] deviceTypes = {
            "Motors",
            "CRServos",
            "Servos"
    };
    int selectedDeviceType = 0;
    int selectedMotor = 0;
    int selectedServo = 0;
    int selectedCRServo = 0;
    int selectedMotor2 = 0;
    int selectedServo2 = 0;
    int selectedCRServo2 = 0;
    int numberOfMotors;
    int numberOfServos;
    int numberOfCRServos;
    ArrayList<Map.Entry<String,DcMotor>> motors = new ArrayList<>();
    ArrayList<Map.Entry<String,Servo>> servos = new ArrayList<>();
    ArrayList<Map.Entry<String,CRServo>> crServos = new ArrayList<>();
    @Override
    public void runOpMode() throws InterruptedException {
        numberOfMotors = hardwareMap.dcMotor.entrySet().size();
        for (Map.Entry<String, DcMotor> entry : hardwareMap.dcMotor.entrySet()) {
            motors.add(entry);
        }
        numberOfServos = hardwareMap.servo.entrySet().size();
        for (Map.Entry<String,Servo> entry : hardwareMap.servo.entrySet()) {
            servos.add(entry);
        }
        numberOfCRServos = hardwareMap.crservo.entrySet().size();
        for (Map.Entry<String, CRServo> entry : hardwareMap.crservo.entrySet()) {
            crServos.add(entry);
        }
        while (!isStopRequested()) {

            telemetry.addData("Selected Device Type", deviceTypes[selectedDeviceType]);
            String deviceType = deviceTypes[selectedDeviceType];
            int leftSideModifier = 0;
            int rightSideModifier = 0;
            if (gamepad1.dpad_down) leftSideModifier = 1;
            if (gamepad1.dpad_down) leftSideModifier = -1;
            if (gamepad1.y) rightSideModifier = 1;
            if (gamepad1.a) rightSideModifier = -1;
            if (getRuntime() > 0.5) {
                if (gamepad1.right_bumper) {
                    selectedDeviceType++;
                    selectedDeviceType = selectedDeviceType%(deviceTypes.length-1);
                    resetStartTime();
                } else if (gamepad1.left_bumper) {
                    selectedDeviceType--;
                    selectedDeviceType = selectedDeviceType%(deviceTypes.length-1);
                    resetStartTime();
                }
                if (gamepad1.dpad_up || gamepad1.dpad_down) {
                    if (deviceType == "Motors" && motors.size() > 1) {
                        selectedMotor += leftSideModifier;
                        selectedMotor = selectedMotor%(motors.size()-1);
                        resetStartTime();
                    } else if (deviceType == "Servos" && servos.size() > 1) {
                        selectedServo += leftSideModifier;
                        selectedServo = selectedServo%(servos.size()-1);
                        resetStartTime();
                    } else if (deviceType == "CRServos" && servos.size() > 1) {
                        selectedCRServo += leftSideModifier;
                        selectedCRServo = selectedCRServo%(crServos.size()-1);
                        resetStartTime();
                    }
                }
                if (gamepad1.y || gamepad1.a) {
                    if (deviceType == "Motors" && motors.size() > 1) {
                        selectedMotor2 += rightSideModifier;
                        selectedMotor2 = selectedMotor2%(motors.size()-1);
                        resetStartTime();
                    } else if (deviceType == "Servos" && servos.size() > 1) {
                        selectedServo2 += rightSideModifier;
                        selectedServo2 = selectedServo2%(servos.size()-1);
                        resetStartTime();
                    } else if (deviceType == "CRServos" && servos.size() > 1) {
                        selectedCRServo2 += rightSideModifier;
                        selectedCRServo2 = selectedCRServo2%(crServos.size()-1);
                        resetStartTime();
                    }
                }
            }

            if (deviceType == "Motors" && motors.size() > 0) {
                telemetry.addData("Motor 1", motors.get(selectedMotor).getKey());
                motors.get(selectedMotor).getValue().setPower(gamepad1.left_stick_y);
                telemetry.addData("Motor 2", motors.get(selectedMotor2).getKey());
                motors.get(selectedMotor2).getValue().setPower(gamepad1.right_stick_y);
            }
            if (deviceType == "Servos" && servos.size() > 0) {
                telemetry.addData("Servo 1", servos.get(selectedServo).getKey());
                Servo servo = servos.get(selectedServo).getValue();
                servo.setPosition(servo.getPosition()+(gamepad1.left_stick_y/2));
                telemetry.addData("Servo 2", servos.get(selectedServo2).getKey());
                Servo servo2 = servos.get(selectedServo2).getValue();
                servo2.setPosition(servo2.getPosition()+(gamepad1.right_stick_y/2));
            }
            if (deviceType == "CRServos" && crServos.size() > 0) {
                telemetry.addData("CRServo 1", crServos.get(selectedCRServo).getKey());
                crServos.get(selectedCRServo).getValue().setPower(gamepad1.left_stick_y);
                telemetry.addData("CRServo 2", crServos.get(selectedCRServo).getKey());
                crServos.get(selectedCRServo).getValue().setPower(gamepad1.right_stick_y);
            }

            for (Map.Entry<String, DcMotor> entry : motors) {
                telemetry.addData(entry.getKey(), entry.getValue().getCurrentPosition());
                entry.getValue().setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                if ((entry != motors.get(selectedMotor) && entry != motors.get(selectedMotor2)) || deviceType != "Motors") entry.getValue().setPower(0);
            }
            for (Map.Entry<String,Servo> entry : servos) {
                telemetry.addData(entry.getKey(), entry.getValue().getPosition());
            }
            for (Map.Entry<String, CRServo> entry : crServos) {
                telemetry.addData(entry.getKey(), entry.getValue().getPower());
                if ((entry != crServos.get(selectedCRServo) && entry != crServos.get(selectedCRServo2)) || deviceType != "CRServos") entry.getValue().setPower(0);
            }

            telemetry.update();
        }
    }
}
