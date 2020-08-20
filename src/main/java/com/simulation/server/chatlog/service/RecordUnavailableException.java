package com.simulation.server.chatlog.service;

public class RecordUnavailableException extends RuntimeException {
    private String statusMsg;

    private String responseMsg;

    public RecordUnavailableException(String statusMsg, String responseMsg) {
        this.statusMsg = statusMsg;
        this.responseMsg = responseMsg;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }
}
