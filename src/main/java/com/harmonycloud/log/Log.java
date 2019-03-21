package com.harmonycloud.log;

public class Log {

    private String clientIp;

    private String loginName;

    private String projectCode;

    private String correlationId;

    private String applicationName;

    public Log() {
    }

    public Log(String clientIp, String loginName, String projectCode, String correlationId, String applicationName) {
        this.clientIp = clientIp;
        this.loginName = loginName;
        this.projectCode = projectCode;
        this.correlationId = correlationId;
        this.applicationName = applicationName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public String toString() {
        return "clientIp='" + clientIp + '\'' +
                ", loginName='" + loginName + '\'' +
                ", projectCode='" + projectCode + '\'' +
                ", correlationId='" + correlationId + '\'' +
                ", applicationName='" + applicationName + '\'';

    }
}
