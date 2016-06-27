/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Norrey Osako
 */
public class SmsOutModel implements Serializable{

    private int id;
    private String rid;
    private String seqNo;
    private String serviceType;
    private String sourceAddress;
    private String destinationAddress;
    private String messagePayload;
    private String userMessageReference;
    private Date timeSubmitted;
    private Date timeProcessed;
    private boolean status;
    private String errorinfo;
    private String messageId;
    private String sentBy;
    private int esmClass;
    private String rule;
    private String user;
    private String submittedBy;
    private int smsCount;
    private String realStatus;

    //--search values---------------------------
    private Date startTime;
    private Date endTime;

    ////
    public SmsOutModel() {
//        this.id = 0;
//        this.rid = "";
//        this.seqNo = "";
//        this.serviceType = "";
//        this.sourceAddress = "";
//        this.destinationAddress = "";
//        this.messagePayload = "";
//        this.userMessageReference = "";
//        this.timeSubmitted = null;
//        this.timeProcessed = null;
//        this.status = false;
//        this.errorinfo = "";
//        this.messageId = "";
//        this.sentBy = "";
//        this.esmClass = 0;
//        this.rule = "";
//        this.user = "";
//        this.submittedBy = "";
//        this.startTime = null;
//        this.endTime = null;
    }

    public SmsOutModel(int id, String rid, String seqNo, String serviceType, String sourceAddress, String destinationAddress, String messagePayload, String userMessageReference, Date timeSubmitted, Date timeProcessed, boolean status, String errorinfo, String messageId, String sentBy, int esmClass, String rule, String user, String submittedBy, int smsCount, String realStatus, Date startTime, Date endTime) {
        this.id = id;
        this.rid = rid;
        this.seqNo = seqNo;
        this.serviceType = serviceType;
        this.sourceAddress = sourceAddress;
        this.destinationAddress = destinationAddress;
        this.messagePayload = messagePayload;
        this.userMessageReference = userMessageReference;
        this.timeSubmitted = timeSubmitted;
        this.timeProcessed = timeProcessed;
        this.status = status;
        this.errorinfo = errorinfo;
        this.messageId = messageId;
        this.sentBy = sentBy;
        this.esmClass = esmClass;
        this.rule = rule;
        this.user = user;
        this.submittedBy = submittedBy;
        this.smsCount = smsCount;
        this.realStatus = realStatus;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getMessagePayload() {
        return messagePayload;
    }

    public void setMessagePayload(String messagePayload) {
        this.messagePayload = messagePayload;
    }

    public String getUserMessageReference() {
        return userMessageReference;
    }

    public void setUserMessageReference(String userMessageReference) {
        this.userMessageReference = userMessageReference;
    }

    public Date getTimeSubmitted() {
        return timeSubmitted;
    }

    public void setTimeSubmitted(Date timeSubmitted) {
        this.timeSubmitted = timeSubmitted;
    }

    public Date getTimeProcessed() {
        return timeProcessed;
    }

    public void setTimeProcessed(Date timeProcessed) {
        this.timeProcessed = timeProcessed;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getErrorinfo() {
        return errorinfo;
    }

    public void setErrorinfo(String errorinfo) {
        this.errorinfo = errorinfo;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public int getEsmClass() {
        return esmClass;
    }

    public void setEsmClass(int esmClass) {
        this.esmClass = esmClass;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getSmsCount() {
        return smsCount;
    }

    public void setSmsCount(int smsCount) {
        this.smsCount = smsCount;
    }

    public String getRealStatus() {
        return realStatus;
    }

    public void setRealStatus(String realStatus) {
        this.realStatus = realStatus;
    }
    
    

    public int countSMS(String msg) {
        int ret = 0;
        if (msg == null || msg.length() <= 160) {
            return 1;
        }
        ret = msg.length() / 134;
        if (msg.length() % 134 > 0) {
            ret++;
        }
        return ret;
    }

}
