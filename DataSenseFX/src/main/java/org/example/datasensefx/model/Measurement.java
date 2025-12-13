package org.example.datasensefx.model;
import java.time.LocalDateTime;

public class Measurement {
    private LocalDateTime date;
    private double usageKWh;
    private double laggingReactive;
    private double leadingReactive;
    private double co2;
    private double laggingPowerFactor;
    private double leadingPowerFactor;
    private int nsm;
    private String weekStatus;
    private String dayOfWeek;
    private String loadType;

    public Measurement(LocalDateTime date, double usageKWh, double laggingReactive,
                       double leadingReactive, double co2, double laggingPowerFactor,
                       double leadingPowerFactor, int nsm, String weekStatus,
                       String dayOfWeek, String loadType) {
        this.date = date;
        this.usageKWh = usageKWh;
        this.laggingReactive = laggingReactive;
        this.leadingReactive = leadingReactive;
        this.co2 = co2;
        this.laggingPowerFactor = laggingPowerFactor;
        this.leadingPowerFactor = leadingPowerFactor;
        this.nsm = nsm;
        this.weekStatus = weekStatus;
        this.dayOfWeek = dayOfWeek;
        this.loadType = loadType;
    }

    public double getLaggingPowerFactor() {
        return laggingPowerFactor;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public double getUsageKWh() {
        return usageKWh;
    }

    public double getLaggingReactive() {
        return laggingReactive;
    }

    public double getLeadingReactive() {
        return leadingReactive;
    }

    public double getCo2() {
        return co2;
    }

    public double getLeadingPowerFactor() {
        return leadingPowerFactor;
    }

    public int getNsm() {
        return nsm;
    }

    public String getWeekStatus() {
        return weekStatus;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getLoadType() {
        return loadType;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "date=" + date +
                ", usageKWh=" + usageKWh +
                ", laggingReactive=" + laggingReactive +
                ", leadingReactive=" + leadingReactive +
                ", co2=" + co2 +
                ", laggingPowerFactor=" + laggingPowerFactor +
                ", leadingPowerFactor=" + leadingPowerFactor +
                ", nsm=" + nsm +
                ", weekStatus='" + weekStatus + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", loadType='" + loadType + '\'' +
                '}';
    }
}
