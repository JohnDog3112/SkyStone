package org.firstinspires.ftc.teamcode.Examples;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Functions.AutoConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@TeleOp
public class ReadFile extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("hashcode", hardwareMap.hashCode());
        telemetry.addData("data1",22);
        telemetry.addData("data2", 23);

        ArrayList<HashMap<String,String>> data = new ArrayList<>();
        HashMap<String,String> dataLine = new HashMap<>();
        dataLine.put("hashcode", "" + hardwareMap.hashCode());
        dataLine.put("data1", "" + 22);
        dataLine.put("data2", "" + 23);

        data.add(dataLine);
        telemetry.addData("wroteFile:", AutoConfig.writeToFile("testSettingsFile", data));

        AutoConfig.readFileReturnData fileData = AutoConfig.readFile("testSettingsFile");
        for (String headers : fileData.headers) telemetry.addData("Header: ",headers);
        int iteration = 0;
        for (HashMap<String,String> map : fileData.data) {
            telemetry.addData("data set", iteration);
            iteration++;
            for (Map.Entry<String,String> entry : map.entrySet()) {
                telemetry.addData(entry.getKey(), entry.getValue());
            }
        }
        telemetry.update();
        waitForStart();
        while (opModeIsActive());
    }
}
