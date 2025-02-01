package com.kc1vmz.object;

public class APRSFIMessage {
    private String messageid;
    private String time;
    private String srccall;
    private String dst;
    private String message;

    public String getMessageid() {
        return messageid;
    }
    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getSrccall() {
        return srccall;
    }
    public void setSrccall(String srccall) {
        this.srccall = srccall;
    }
    public String getDst() {
        return dst;
    }
    public void setDst(String dst) {
        this.dst = dst;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
