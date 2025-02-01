package com.kc1vmz.object;

import java.time.LocalDateTime;

public class APRSLocation {
    String name;
    String srcCallsign;
    String destCallsign;
    LocalDateTime time;
    LocalDateTime lastTime;
    Float longitude;
    Float latitude;
    String symbol;
    String comment;
    String path;
    String type;
    Integer phg;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSrcCallsign() {
        return srcCallsign;
    }
    public void setSrcCallsign(String srcCallsign) {
        this.srcCallsign = srcCallsign;
    }
    public String getDestCallsign() {
        return destCallsign;
    }
    public void setDestCallsign(String destCallsign) {
        this.destCallsign = destCallsign;
    }
    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    public LocalDateTime getLastTime() {
        return lastTime;
    }
    public void setLastTime(LocalDateTime lastTime) {
        this.lastTime = lastTime;
    }
    public Float getLongitude() {
        return longitude;
    }
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
    public Float getLatitude() {
        return latitude;
    }
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Integer getPhg() {
        return phg;
    }
    public void setPhg(Integer phg) {
        this.phg = phg;
    }

}
