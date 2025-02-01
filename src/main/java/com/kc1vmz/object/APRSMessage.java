package com.kc1vmz.object;

import java.time.LocalDateTime;

public class APRSMessage {
    String messageId;
    String srcCallsign;
    String destCallsign;
    String message;
    LocalDateTime time;

    public String getMessageId() {
        return messageId;
    }
    public void setMessageId(String messageId) {
        this.messageId = messageId;
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
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
