package com.kc1vmz.object;

import com.google.gson.annotations.SerializedName;

public class APRSFILocation {
    /*
     */
    @SerializedName("class")
    private String className;
    private String name;
    private String showname;
    private String type;
    private String time;
    private String lasttime;
    private String lat;
    private String lng;
    private String symbol;
    private String srccall;
    private String dstcall;
    private String comment;
    private String path;
    private String course;
    private String speed;
    private String altitude;
    private String phg;

    public String getShowname() {
        return showname;
    }
    public void setShowname(String showname) {
        this.showname = showname;
    }
    public String getCourse() {
        return course;
    }
    public void setCourse(String course) {
        this.course = course;
    }
    public String getSpeed() {
        return speed;
    }
    public void setSpeed(String speed) {
        this.speed = speed;
    }
    public String getAltitude() {
        return altitude;
    }
    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }
    public String getPhg() {
        return phg;
    }
    public void setPhg(String phg) {
        this.phg = phg;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus_lasttime() {
        return status_lasttime;
    }
    public void setStatus_lasttime(String status_lasttime) {
        this.status_lasttime = status_lasttime;
    }
    public String getMmsi() {
        return mmsi;
    }
    public void setMmsi(String mmsi) {
        this.mmsi = mmsi;
    }
    public String getImo() {
        return imo;
    }
    public void setImo(String imo) {
        this.imo = imo;
    }
    public String getVesselclass() {
        return vesselclass;
    }
    public void setVesselclass(String vesselclass) {
        this.vesselclass = vesselclass;
    }
    public String getNavstat() {
        return navstat;
    }
    public void setNavstat(String navstat) {
        this.navstat = navstat;
    }
    public String getHeading() {
        return heading;
    }
    public void setHeading(String heading) {
        this.heading = heading;
    }
    public String getLength() {
        return length;
    }
    public void setLength(String length) {
        this.length = length;
    }
    public String getWidth() {
        return width;
    }
    public void setWidth(String width) {
        this.width = width;
    }
    public String getDraught() {
        return draught;
    }
    public void setDraught(String draught) {
        this.draught = draught;
    }
    public String getRef_front() {
        return ref_front;
    }
    public void setRef_front(String ref_front) {
        this.ref_front = ref_front;
    }
    public String getRef_left() {
        return ref_left;
    }
    public void setRef_left(String ref_left) {
        this.ref_left = ref_left;
    }
    private String status;
    private String status_lasttime;
    private String mmsi;
    private String imo;
    private String vesselclass;
    private String navstat;
    private String heading;
    private String length;
    private String width;
    private String draught;
    private String ref_front;
    private String ref_left;

    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getLasttime() {
        return lasttime;
    }
    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }
    public String getLat() {
        return lat;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }
    public String getLng() {
        return lng;
    }
    public void setLng(String lng) {
        this.lng = lng;
    }
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public String getSrccall() {
        return srccall;
    }
    public void setSrccall(String srccall) {
        this.srccall = srccall;
    }
    public String getDstcall() {
        return dstcall;
    }
    public void setDstcall(String dstcall) {
        this.dstcall = dstcall;
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
}
