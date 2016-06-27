/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Norrey Osako
 */
public class SMSCreditsUserBean implements Serializable{

    private SMSCredits credits;
    private UserBean userBean;

    public SMSCreditsUserBean() {
    }

    public SMSCredits getCredits() {
        return credits;
    }

    public void setCredits(SMSCredits credits) {
        this.credits = credits;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

}
