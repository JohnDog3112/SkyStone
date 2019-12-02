package org.firstinspires.ftc.teamcode.Functions;

import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AutoConfig {
    public static boolean writeLineToFile(String fileName, ArrayList<String> values) {
        File file = AppUtil.getInstance().getSettingsFile(fileName);
        if (file.exists()) {
            readFileReturnData oldData = readFile(fileName);
            ArrayList<HashMap<String,String>> data = new ArrayList<>();
            HashMap<String,String> lineToAdd = new HashMap<>();
            for (int i = 0; i < oldData.headers.length; i++) {
                if (i < values.size()) {
                    lineToAdd.put(oldData.headers[i], values.get(i));
                } else {
                    lineToAdd.put(oldData.headers[i], "");
                }
            }
            data.add(lineToAdd);
            String writeString = "";
            for (int i =0; i < oldData.headers.length; i++) {
                writeString += oldData.headers[i];
                if (i != oldData.headers.length-1) {
                    writeString += ",";
                }
            }
            writeString += "\n";
            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < oldData.headers.length; j++) {
                    writeString += data.get(i).get(oldData.headers[j]);
                    if (j != oldData.headers.length-1) {
                        writeString += ",";
                    }
                }
                if (i != data.size()-1) {
                    writeString += "\n";
                }
            }
            ReadWriteFile.writeFile(file,writeString);
            return true;
        }
        return false;
    }
    public static boolean writeToFile(String fileName, ArrayList<HashMap<String,String>> values) {
        File file = AppUtil.getInstance().getSettingsFile(fileName);
        if (file.exists()) {
            readFileReturnData oldData = readFile(fileName);
            ArrayList<HashMap<String,String>> data = new ArrayList<>();
            ArrayList<HashMap<String,String>> linesToAdd = new ArrayList<>();
            for (int i = 0; i < values.size(); i ++) {
                HashMap<String,String> lineToAdd = new HashMap<>();
                for (int j = 0; j < oldData.headers.length; j++) {
                    if (i < values.size()) {
                        lineToAdd.put(oldData.headers[i], values.get(i).get(oldData.headers[i]));
                    } else {
                        lineToAdd.put(oldData.headers[i], "");
                    }
                }
            }
            data.addAll(linesToAdd);
            String writeString = "";
            for (int i =0; i < oldData.headers.length; i++) {
                writeString += oldData.headers[i];
                if (i < oldData.headers.length-1) {
                    writeString += ",";
                }
            }
            writeString += "\n";
            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < oldData.headers.length; j++) {
                    writeString += data.get(i).get(oldData.headers[j]);
                    if (j != oldData.headers.length-1) {
                        writeString += ",";
                    }
                }
                if (i != data.size()-1) {
                    writeString += "\n";
                }
            }
            ReadWriteFile.writeFile(file,writeString);
            return true;
        }
        return false;
    }
    public static readFileReturnData readFile(String fileName) {
        File file = AppUtil.getInstance().getSettingsFile(fileName);
        if (file.exists()) {
            String read = ReadWriteFile.readFile(file);
            ArrayList<HashMap<String,String>> parsedData = new ArrayList<>();
            String[] lines = read.split("\n");
            String[] headers = read.split(",");
            for (int i = 1; i < lines.length; i++) {
                String[] dataOnLine = lines[i].split(",");
                HashMap<String,String> parsedDataOnLine = new HashMap<>();
                for (int j = 0; j < headers.length; j++) {
                    if (j < dataOnLine.length) {
                        parsedDataOnLine.put(headers[j], dataOnLine[j]);
                    } else {
                        parsedDataOnLine.put(headers[j],"");
                    }

                }
                parsedData.set(i-1,parsedDataOnLine);
            }
            return new readFileReturnData(headers,parsedData);
        }
        return new readFileReturnData(new String[0], new ArrayList<HashMap<String, String>>());
    }
    public static void createFile(String fileName, String[] headers) throws IOException {
        File file = AppUtil.getInstance().getSettingsFile(fileName);
        if (file.exists()) {
            file.delete();
            file = AppUtil.getInstance().getSettingsFile(fileName);
        }
        file.createNewFile();
        String writeString = "";
        for (int i = 0; i < headers.length; i++) {
            writeString += headers[i];
            if (i < headers.length-1) writeString += ",";
        }
        ReadWriteFile.writeFile(file,writeString);
    }
    public static class readFileReturnData {
        public final String[] headers;
        public final ArrayList<HashMap<String,String>> data;
        public readFileReturnData(String[] headers, ArrayList<HashMap<String,String>> data) {
            this.headers = headers;
            this.data = data;
        }
    }
    public static void writeToDataFile(String fileName, ArrayList<HashMap<String,String>> data) {
        File file = AppUtil.getInstance().getSettingsFile(fileName);
        if (file.exists()) {
            readFileReturnData fileData = readDataFile(fileName);
            ArrayList<HashMap<String,String>> parsedData = fileData.data;
            for (HashMap<String,String> line : data) {
                if (parsedData.get(Integer.parseInt(line.get(fileData.headers[0]))) != null) parsedData.remove(Integer.parseInt(line.get(fileData.headers[0])));
                HashMap<String,String> dataLine = new HashMap<>();
                for (int i = 1; i < fileData.headers.length; i++) {
                    if (line.containsKey(fileData.headers[i])) {
                        dataLine.put(fileData.headers[i], line.get(fileData.headers[i]));
                    } else {
                        dataLine.put(fileData.headers[i], "");
                    }
                }
                parsedData.add(Integer.parseInt(line.get(fileData.headers[0])), dataLine);
            }

            String writeString = "";
            for (int i = 0; i < fileData.headers.length; i++) {
                writeString += fileData.headers[i];
                if (i < fileData.headers.length-1) writeString += ",";
            }
            for (HashMap<String,String> line : parsedData) {
                writeString += "\n";
                for (int i = 0; i < fileData.headers.length; i++) {
                    writeString += line.get(fileData.headers[i]);
                    if (i < fileData.headers.length-1) writeString += ",";
                }
            }
            ReadWriteFile.writeFile(file,writeString);
        }
    }
    public static readFileReturnData readDataFile(String fileName) {
        File file = AppUtil.getInstance().getSettingsFile(fileName);
        if (file.exists()) {
            readFileReturnData fileData = readFile(fileName);
            ArrayList<HashMap<String,String>> parsedData = new ArrayList<>();
            HashMap<String,String> tempHashMap = new HashMap<>();
            for (HashMap<String,String> line : fileData.data) {
                for (Map.Entry<String,String> value : line.entrySet()) {
                    tempHashMap.put(value.getKey(),value.getValue());
                }
                parsedData.add(Integer.parseInt(line.get(fileData.headers[0])), tempHashMap);
            }
            return new readFileReturnData(fileData.headers, parsedData);
        }
        return null;
    }
}
