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
public class SmsOutUserBean implements Serializable{

    private UserBean userBean;
    private SmsOutModel smsOutModel;

    /**
     * Creates a new instance of SmsOutUserBean
     */
    public SmsOutUserBean() {
        userBean = new UserBean();
        smsOutModel = new SmsOutModel();

    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public SmsOutModel getSmsOutModel() {
        return smsOutModel;
    }

    public void setSmsOutModel(SmsOutModel smsOutModel) {
        this.smsOutModel = smsOutModel;
    }

}
