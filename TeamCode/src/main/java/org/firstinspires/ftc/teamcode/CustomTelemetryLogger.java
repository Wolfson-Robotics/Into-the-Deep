package org.firstinspires.ftc.teamcode;

import java.io.FileWriter;
import java.io.IOException;

public class CustomTelemetryLogger {
    private FileWriter fileWriter;
    private int logNum = 1;
    public CustomTelemetryLogger(String filePath) throws IOException {
        if (fileWriter == null)
            fileWriter = new FileWriter(filePath, true); // true for append mode
    }

    public synchronized void logData(String data) {
        try {
            fileWriter.write(logNum + ": " + data + "\n");
            logNum++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void close() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}