package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.Arrays;

public class PersistentTelemetry {

    private Telemetry telemetry;
    private ArrayList<String> telemetryLines = new ArrayList<>();

    public PersistentTelemetry(Telemetry telemetry, ArrayList<String> telemetryLines) {
        this.telemetry = telemetry;
        this.telemetryLines = telemetryLines;
    }
    public PersistentTelemetry(Telemetry telemetry, String[] telemetryLines) {
        this(telemetry, new ArrayList<>(Arrays.asList(telemetryLines)));
    }
    public PersistentTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }


    public void addLine(String content) {
        this.telemetryLines.add(content);
    }

    public void setLine(int lineIndex, String content) {
        this.telemetryLines.set(lineIndex, content);
    }
    public void setLine(String searchContent, String content) {
        // iterate backwards because we want to modify most recent line not older ones
        searchContent = searchContent.trim().toLowerCase();
        for (int lineIndex = (this.telemetryLines.size() - 1); lineIndex >= 0; lineIndex--) {
            if (this.telemetryLines.get(lineIndex).trim().toLowerCase().contains(searchContent)) {
                this.setLine(lineIndex, content);
                return;
            }
        }
        this.addLine(content);

    }

    public String getLine(int lineIndex) {
        return this.telemetryLines.get(lineIndex);
    }


    public void update() {
        this.telemetryLines.forEach(telemetryLine -> {
            this.telemetry.addLine(telemetryLine);
        });
        this.telemetry.update();
    }
    public void clear() {
        this.telemetry.clear();
    }
}
