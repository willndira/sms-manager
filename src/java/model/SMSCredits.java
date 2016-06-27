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
public class SMSCredits implements Serializable {

    private int id;
    private String username;
    private char actionType;
    private String actType;
    private Date actionTime;
    private int numCredits;
    private int previous_balance;
    private int new_balance;

    public SMSCredits() {
        this.id = 0;
        this.username = "";
        actType = "";
        this.actionType = '0';
        this.actionTime = null;
        this.numCredits = 0;
        this.previous_balance = 0;
        this.new_balance = 0;
    }

    public SMSCredits(int id, String username, char actionType, String actType, Date actionTime, int numCredits, int previous_balance, int new_balance) {
        this.id = id;
        this.username = username;
        this.actionType = actionType;
        this.actType = actType;
        this.actionTime = actionTime;
        this.numCredits = numCredits;
        this.previous_balance = previous_balance;
        this.new_balance = new_balance;
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

    public char getActionType() {
        return actionType;
    }

    public void setActionType(char actionType) {
        this.actionType = actionType;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public int getNumCredits() {
        return numCredits;
    }

    public void setNumCredits(int numCredits) {
        this.numCredits = numCredits;
    }

    public int getPrevious_balance() {
        return previous_balance;
    }

    public void setPrevious_balance(int previous_balance) {
        this.previous_balance = previous_balance;
    }

    public int getNew_balance() {
        return new_balance;
    }

    public void setNew_balance(int new_balance) {
        this.new_balance = new_balance;
    }

    public String getActType() {
        return actType;
    }

    public void setActType(String actType) {
        this.actType = actType;
    }
    
    

}
