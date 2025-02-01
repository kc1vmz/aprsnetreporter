package com.kc1vmz.object;

import java.time.LocalDateTime;

import com.kc1vmz.enums.APRSInformationSources;

public class ApplicationContext {
    APRSInformationSources APRSprovider;
    String callsign;
    String messagePrefix;
    String apikey;
    boolean verbose;
    String netParticipantFileName;
    String netParticipantMapFileName;
    String netParticipantReportName;
    String netParticipantMapReportName;
    String operatorName;
    String taskId;
    String taskName;
    LocalDateTime startTime;
    LocalDateTime endTime;
    boolean generateDataFiles;
    boolean generateReportFiles;
    boolean retrieveData;

    public String getNetParticipantFileName() {
        return netParticipantFileName;
    }
    public void setNetParticipantFileName(String netParticipantFileName) {
        this.netParticipantFileName = netParticipantFileName;
    }
    public boolean isVerbose() {
        return verbose;
    }
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    public String getMessagePrefix() {
        return messagePrefix;
    }
    public void setMessagePrefix(String messagePrefix) {
        this.messagePrefix = messagePrefix;
    }
    public APRSInformationSources getAPRSprovider() {
        return APRSprovider;
    }
    public void setAPRSprovider(APRSInformationSources APRSprovider) {
        this.APRSprovider = APRSprovider;
    }
    public String getCallsign() {
        return callsign;
    }
    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }
    public String getApikey() {
        return apikey;
    }
    public void setApikey(String apikey) {
        this.apikey = apikey;
    }
    public String getOperatorName() {
        return operatorName;
    }
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
    public String getTaskId() {
        return taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    public String getNetParticipantMapFileName() {
        return netParticipantMapFileName;
    }
    public void setNetParticipantMapFileName(String netParticipantMapFileName) {
        this.netParticipantMapFileName = netParticipantMapFileName;
    }
    public String getNetParticipantReportName() {
        return netParticipantReportName;
    }
    public void setNetParticipantReportName(String netParticipantReportName) {
        this.netParticipantReportName = netParticipantReportName;
    }
    public String getNetParticipantMapReportName() {
        return netParticipantMapReportName;
    }
    public void setNetParticipantMapReportName(String netParticipantMapReportName) {
        this.netParticipantMapReportName = netParticipantMapReportName;
    }
    public boolean isGenerateDataFiles() {
        return generateDataFiles;
    }
    public void setGenerateDataFiles(boolean generateDataFiles) {
        this.generateDataFiles = generateDataFiles;
    }
    public boolean isGenerateReportFiles() {
        return generateReportFiles;
    }
    public void setGenerateReportFiles(boolean generateReportFiles) {
        this.generateReportFiles = generateReportFiles;
    }
    public boolean isRetrieveData() {
        return retrieveData;
    }
    public void setRetrieveData(boolean retrieveData) {
        this.retrieveData = retrieveData;
    }
}
