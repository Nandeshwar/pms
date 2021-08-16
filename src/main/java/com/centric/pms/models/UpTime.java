package com.centric.pms.models;

public class UpTime {
    private long upTime;
    private String unit;

    public UpTime(Long upTime, String unit) {
        this.upTime = upTime;
        this.unit = unit;
    }

    public long getUpTime() {
        return upTime;
    }

    public void setUpTime(long upTime) {
        this.upTime = upTime;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
