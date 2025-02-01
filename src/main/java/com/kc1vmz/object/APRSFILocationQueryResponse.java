package com.kc1vmz.object;

import java.util.List;

public class APRSFILocationQueryResponse {

    private String command;
    private String result;
    private Long found;
    private String what;
    private List<APRSFILocation> entries;
    private String code;
    private String description;

    public String getCommand() {
        return command;
    }
    public void setCommand(String command) {
        this.command = command;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public Long getFound() {
        return found;
    }
    public void setFound(Long found) {
        this.found = found;
    }
    public String getWhat() {
        return what;
    }
    public void setWhat(String what) {
        this.what = what;
    }
    public List<APRSFILocation> getEntries() {
        return entries;
    }
    public void setEntries(List<APRSFILocation> entries) {
        this.entries = entries;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}