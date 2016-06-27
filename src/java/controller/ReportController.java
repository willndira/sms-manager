/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.Reports;
import model.SMSCredits;
import model.SmsOutUserBean;
import model.UserBean;
import service.SMSCreditsUserBeanData;
import service.SmsOutUserBeanData;
import service.UserData;
import util.DbUtil;

/**
 *
 * @author Norrey Osako
 */
public class ReportController implements Serializable {

    private SmsOutUserBean smsOutUserBean;
    private SmsOutUserBean searchSmsUserBean;
    private SmsOutUserBean selectedSearchSmsUserBean;
    private List<SmsOutUserBean> smsOutUserBeans;
    private UserBean mainAdmin;

    private int totalSmsCount;
    private List<String> userNameSuggetions;

    private SmsOutUserBeanData beanData;
    private UserData userData;
    private Reports reportData;

    // private List<SmsUtilisationSmsOutBean> smsUtilisationSmsOutBeans;
    private List<SMSCredits> smsCreditses;
    // private SmsUtilisationSmsOutBeanData beanData1;
    private SMSCreditsUserBeanData sMSCreditsUserBeanData;

    public ReportController() {

        beanData = new SmsOutUserBeanData();
        reportData = new Reports();
        sMSCreditsUserBeanData = new SMSCreditsUserBeanData();
        smsOutUserBean = new SmsOutUserBean();
        searchSmsUserBean = new SmsOutUserBean();
        selectedSearchSmsUserBean = new SmsOutUserBean();
        smsOutUserBeans = new ArrayList<>();
        userNameSuggetions = new ArrayList<>();
        smsCreditses = new ArrayList<>();
        mainAdmin = new UserBean();
        userData = new UserData();
        totalSmsCount = 0;
        mainAdmin = this.getUser();
        Connection conns = DbUtil.getConnection();
        try {
            if (mainAdmin != null) {
                mainAdmin = userData.getUser(conns, mainAdmin.getId());
                smsCreditses = sMSCreditsUserBeanData.getAllSmsCredits(conns, mainAdmin);
                userNameSuggetions = beanData.getAdminSubAccUsernames(conns, mainAdmin.getId());

            }
        } finally {
            if (conns != null) {
                try {
                    conns.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public UserBean getUser() {
        ExternalContext tmpEC;
        Map sMap;
        tmpEC = FacesContext.getCurrentInstance().getExternalContext();
        sMap = tmpEC.getSessionMap();
        return (UserBean) sMap.get(UserBean.AUTH_KEY);
    }

    public String search() {
        SmsOutUserBean bean = selectedSearchSmsUserBean;
        totalSmsCount = 0;
        if (mainAdmin != null) {
            if (smsOutUserBeans != null) {
                smsOutUserBeans.clear();
            }

            bean.getUserBean().setSuperAccountId(mainAdmin.getId());
            Connection conn = DbUtil.getConnection();
            try {

                smsOutUserBeans = beanData.search(conn, bean);
                if (smsOutUserBeans != null) {
                    if (smsOutUserBeans.size() > 0) {
                        int count = 0;
                        for (SmsOutUserBean bean1 : smsOutUserBeans) {
                            count += bean1.getSmsOutModel().getSmsCount();
                        }
                        totalSmsCount = count;
                        return "smsOutReports";
                    }
                }

            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }

        return "smsOutReports";
    }

    public void generateReport() {
        SmsOutUserBean bean = selectedSearchSmsUserBean;
        totalSmsCount = 0;
        if (mainAdmin != null) {
            if (smsOutUserBeans != null) {
                smsOutUserBeans.clear();
            }

            bean.getUserBean().setSuperAccountId(mainAdmin.getId());
            Connection conn = DbUtil.getConnection();
            try {

                smsOutUserBeans = beanData.search(conn, bean);
                if (smsOutUserBeans != null) {
                    if (smsOutUserBeans.size() > 0) {
                        int count = 0;
                        for (SmsOutUserBean bean1 : smsOutUserBeans) {
                            count += bean1.getSmsOutModel().getSmsCount();
                        }
                        totalSmsCount = count;
                        reportData.generateXSL(smsOutUserBeans, count);
                    }
                }

            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }

    }

    public SmsOutUserBean getSmsOutUserBean() {
        return smsOutUserBean;
    }

    public void setSmsOutUserBean(SmsOutUserBean smsOutUserBean) {
        this.smsOutUserBean = smsOutUserBean;
    }

    public List<SmsOutUserBean> getSmsOutUserBeans() {
        return smsOutUserBeans;
    }

    public void setSmsOutUserBeans(List<SmsOutUserBean> smsOutUserBeans) {
        this.smsOutUserBeans = smsOutUserBeans;
    }

    public SmsOutUserBean getSearchSmsUserBean() {
        return searchSmsUserBean;
    }

    public void setSearchSmsUserBean(SmsOutUserBean searchSmsUserBean) {
        this.searchSmsUserBean = searchSmsUserBean;
    }

    public UserBean getMainAdmin() {
        return mainAdmin;
    }

    public void setMainAdmin(UserBean mainAdmin) {
        this.mainAdmin = mainAdmin;
    }

    public int getTotalSmsCount() {
        return totalSmsCount;
    }

    public void setTotalSmsCount(int totalSmsCount) {
        this.totalSmsCount = totalSmsCount;
    }

    public List<String> getUserNameSuggetions() {
        return userNameSuggetions;
    }

    public void setUserNameSuggetions(List<String> userNameSuggetions) {
        this.userNameSuggetions = userNameSuggetions;
    }

    public List<SMSCredits> getSmsCreditses() {
        return smsCreditses;
    }

    public void setSmsCreditses(List<SMSCredits> smsCreditses) {
        this.smsCreditses = smsCreditses;
    }

    public SmsOutUserBean getSelectedSearchSmsUserBean() {
        return selectedSearchSmsUserBean;
    }

    public void setSelectedSearchSmsUserBean(SmsOutUserBean selectedSearchSmsUserBean) {
        this.selectedSearchSmsUserBean = selectedSearchSmsUserBean;
    }

}
