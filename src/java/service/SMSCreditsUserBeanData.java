/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.SMSCredits;
import model.UserBean;

/**
 *
 * @author Norrey Osako
 */
public class SMSCreditsUserBeanData implements Serializable {

    public SMSCreditsUserBeanData() {
    }

    public boolean persistUpdate(Connection conn, SMSCredits smsCredits) {

        String sql = "INSERT INTO tManageCredits(username, actionType, actionTime, numCredits, previous_balance, new_balance ) VALUES (?, ?, now(), ?, ?, ?)";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, smsCredits.getUsername());
            stmt.setString(2, String.valueOf(smsCredits.getActionType()));
            stmt.setInt(3, smsCredits.getNumCredits());
            stmt.setInt(4, smsCredits.getPrevious_balance());
            stmt.setInt(5, smsCredits.getNew_balance());
            int affected = stmt.executeUpdate();
            return affected == 1;
        } catch (SQLException e) {
            System.err.println("persistUpdate(SMSCredits smsCredits, Connection conn):::" + e.getMessage());
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SMSCreditsUserBeanData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public List<SMSCredits> getAllSmsCredits(Connection conn, UserBean admin) {
        String sql = "select * from tManageCredits  join tUSER  on tUSER.username  = tManageCredits.username where tUSER.super_account_id = ? order by tManageCredits.id desc limit 15  ";
        List<SMSCredits> smscs = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, admin.getId());
            rs = stmt.executeQuery();
            while (rs.next()) {
                SMSCredits credits = new SMSCredits();
                credits.setId(rs.getInt("id"));
                credits.setUsername(rs.getString("username"));
                String charValueStr = rs.getString("actionType");
                if (charValueStr != null) {
                    if (charValueStr.length() > 0) {
                        credits.setActionType(charValueStr.charAt(0));
                    } else {
                        credits.setActionType('0');
                    }
                } else {
                    credits.setActionType('0');
                }
                if(credits.getActionType() == '1'){
                credits.setActType("Add");
                }else{
                credits.setActType("Deduct");
                }
                credits.setActionTime(rs.getTimestamp("actionTime"));
                credits.setNumCredits(rs.getInt("numCredits"));
                credits.setPrevious_balance(rs.getInt("previous_balance"));
                credits.setNew_balance(rs.getInt("new_balance"));
                smscs.add(credits);
            }
            return smscs;
        } catch (SQLException e) {
            System.err.println("getAllSmsCredits(Connection conn, UserBean admin)" + e.getMessage());
            return null;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SMSCreditsUserBeanData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SMSCreditsUserBeanData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
