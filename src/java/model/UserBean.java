/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import javax.faces.context.FacesContext;
import service.UserData;
import util.DbUtil;

/**
 *
 * @author Norrey Osako
 */
public class UserBean implements Serializable{

    public static final String AUTH_KEY = "non.smpp.manager";
    private int id;
    private String username;
    private String password;
    private String agent;
    private int maxDaily;
    private int maxWeekly;
    private int maxTotal;
    private Date startDate;
    private Date endDate;
    private boolean loggedIn;
    private Date loggedInTime;
    private String message;
    private String destinationAddress;
    private int admin;
    private String shortCodes;
    private Date lastReceived;
    private Date lastSend;
    private int lastReceivedId;
    private int lastSendId;
    private int smsCountToday;
    private int smsCountWeek;
    private int smsCountMonth;
    private int smsCountTotal;
    private int receivedTotal;
    private Date receivedMonth;
    private Date receivedToday;
    private String name;
    private String contactNumber;
    private String contactNum;
    private String taskAdmin;
    private String surname;
    private String firstname;
    private String emailAddress;
    private Date dateUpdated;
    private String organization;
    private Boolean enableEmailAlert;
    private int alertThreshold;
    private int superAccountId;
    private boolean selected;
    private String selectedAction;

    //---------------------------------
    private String loginMessage;
    private String error;
    private String status;
    private String errorCredits = "";

    //----------------------------
    public UserBean() {
    }

    public UserBean(int id, String username, String password, String agent, int maxDaily, int maxWeekly, int maxTotal, Date startDate, Date endDate, boolean loggedIn, Date loggedInTime, String message, String destinationAddress, int admin, String shortCodes, Date lastReceived, Date lastSend, int lastReceivedId, int lastSendId, int smsCountToday, int smsCountWeek, int smsCountMonth, int smsCountTotal, int receivedTotal, Date receivedMonth, Date receivedToday, String name, String contactNumber, String contactNum, String taskAdmin, String surname, String firstname, String emailAddress, Date dateUpdated, String organization, boolean enableEmailAlert, int alertThreshold, int superAccountId, boolean selected) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.agent = agent;
        this.maxDaily = maxDaily;
        this.maxWeekly = maxWeekly;
        this.maxTotal = maxTotal;
        this.startDate = startDate;
        this.endDate = endDate;
        this.loggedIn = loggedIn;
        this.loggedInTime = loggedInTime;
        this.message = message;
        this.destinationAddress = destinationAddress;
        this.admin = admin;
        this.shortCodes = shortCodes;
        this.lastReceived = lastReceived;
        this.lastSend = lastSend;
        this.lastReceivedId = lastReceivedId;
        this.lastSendId = lastSendId;
        this.smsCountToday = smsCountToday;
        this.smsCountWeek = smsCountWeek;
        this.smsCountMonth = smsCountMonth;
        this.smsCountTotal = smsCountTotal;
        this.receivedTotal = receivedTotal;
        this.receivedMonth = receivedMonth;
        this.receivedToday = receivedToday;
        this.name = name;
        this.contactNumber = contactNumber;
        this.contactNum = contactNum;
        this.taskAdmin = taskAdmin;
        this.surname = surname;
        this.firstname = firstname;
        this.emailAddress = emailAddress;
        this.dateUpdated = dateUpdated;
        this.organization = organization;
        this.enableEmailAlert = enableEmailAlert;
        this.alertThreshold = alertThreshold;
        this.superAccountId = superAccountId;
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public int getMaxDaily() {
        return maxDaily;
    }

    public void setMaxDaily(int maxDaily) {
        this.maxDaily = maxDaily;
    }

    public int getMaxWeekly() {
        return maxWeekly;
    }

    public void setMaxWeekly(int maxWeekly) {
        this.maxWeekly = maxWeekly;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Date getLoggedInTime() {
        return loggedInTime;
    }

    public void setLoggedInTime(Date loggedInTime) {
        this.loggedInTime = loggedInTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public String getShortCodes() {
        return shortCodes;
    }

    public void setShortCodes(String shortCodes) {
        this.shortCodes = shortCodes;
    }

    public Date getLastReceived() {
        return lastReceived;
    }

    public void setLastReceived(Date lastReceived) {
        this.lastReceived = lastReceived;
    }

    public Date getLastSend() {
        return lastSend;
    }

    public void setLastSend(Date lastSend) {
        this.lastSend = lastSend;
    }

    public int getLastReceivedId() {
        return lastReceivedId;
    }

    public void setLastReceivedId(int lastReceivedId) {
        this.lastReceivedId = lastReceivedId;
    }

    public int getLastSendId() {
        return lastSendId;
    }

    public void setLastSendId(int lastSendId) {
        this.lastSendId = lastSendId;
    }

    public int getSmsCountToday() {
        return smsCountToday;
    }

    public void setSmsCountToday(int smsCountToday) {
        this.smsCountToday = smsCountToday;
    }

    public int getSmsCountWeek() {
        return smsCountWeek;
    }

    public void setSmsCountWeek(int smsCountWeek) {
        this.smsCountWeek = smsCountWeek;
    }

    public int getSmsCountMonth() {
        return smsCountMonth;
    }

    public void setSmsCountMonth(int smsCountMonth) {
        this.smsCountMonth = smsCountMonth;
    }

    public int getSmsCountTotal() {
        return smsCountTotal;
    }

    public void setSmsCountTotal(int smsCountTotal) {
        this.smsCountTotal = smsCountTotal;
    }

    public int getReceivedTotal() {
        return receivedTotal;
    }

    public void setReceivedTotal(int receivedTotal) {
        this.receivedTotal = receivedTotal;
    }

    public Date getReceivedMonth() {
        return receivedMonth;
    }

    public void setReceivedMonth(Date receivedMonth) {
        this.receivedMonth = receivedMonth;
    }

    public Date getReceivedToday() {
        return receivedToday;
    }

    public void setReceivedToday(Date receivedToday) {
        this.receivedToday = receivedToday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getTaskAdmin() {
        return taskAdmin;
    }

    public void setTaskAdmin(String taskAdmin) {
        this.taskAdmin = taskAdmin;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public boolean getEnableEmailAlert() {
        return enableEmailAlert;
    }

    public void setEnableEmailAlert(boolean enableEmailAlert) {
        this.enableEmailAlert = enableEmailAlert;
    }

    public int getAlertThreshold() {
        return alertThreshold;
    }

    public void setAlertThreshold(int alertThreshold) {
        this.alertThreshold = alertThreshold;
    }

    public int getSuperAccountId() {
        return superAccountId;
    }

    public void setSuperAccountId(int superAccountId) {
        this.superAccountId = superAccountId;
    }

    public boolean isLoggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get(AUTH_KEY) != null;
    }

    //--------------------------------------------------------------------------
    public String getLoginMessage() {
        return loginMessage;
    }

    public void setLoginMessage(String loginMessage) {
        this.loginMessage = loginMessage;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEnableEmailAlert(Boolean enableEmailAlert) {
        this.enableEmailAlert = enableEmailAlert;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getSelectedAction() {
        return selectedAction;
    }

    public void setSelectedAction(String selectedAction) {
        this.selectedAction = selectedAction;
    }

    public String getErrorCredits() {
        return errorCredits;
    }

    public void setErrorCredits(String errorCredits) {
        this.errorCredits = errorCredits;
    }

    public String login(UserBean u) {
        if (u != null) {
            UserData uD = new UserData();
            Connection conn = DbUtil.getConnection();
            int userId = uD.clientLogin(conn, u.getUsername(), u.getPassword());
            if (userId > 0) {
                u = uD.getUser(conn, userId);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(AUTH_KEY, u);
                DbUtil.closeConnection(conn);
//                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", uD.getUser(userId));
                return "template";
            } else {
                return null;
            }

        } else {
            return null;
        }

    }

  
}
