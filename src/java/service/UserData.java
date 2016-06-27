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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import model.SMSCredits;
import model.SmsOutUserBean;
import model.UserBean;
import static model.UserBean.AUTH_KEY;
import util.DbUtil;
import util.HelperUtil;

/**
 *
 * @author Norrey Osako
 */
public class UserData implements Serializable {

    private UserBean userBean;
    private List<UserBean> userBeans;

    public UserData() {
        userBean = new UserBean();
        userBeans = new ArrayList<>();
    }

    public boolean createSubAccounts(Connection conn, UserBean u, UserBean adminBean) {
        String sql = "insert into tUSER(username,password,max_total,start_date,end_date,organization,contact_number,"
                + "email_address ,admin,enable_email_alert,"
                + "alertThreshold,super_account_id) VALUES(?,?,?,now(),?,?,?,?,?,?,?,?)";

        ResultSet rs = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getPassword());
            stmt.setInt(3, u.getMaxTotal());
            
            //stmt.setString(4, HelperUtil.conDateToString(HelperUtil.getTodaysDate()));
            stmt.setTimestamp(4, new Timestamp(HelperUtil.setEndDate().getTime()));
           
            stmt.setString(5, u.getOrganization());
            stmt.setString(6,HelperUtil.convertPhonenumber(u.getContactNumber()));
            stmt.setString(7, u.getEmailAddress());
            stmt.setInt(8, u.getAdmin());
            stmt.setBoolean(9, u.getEnableEmailAlert());
            stmt.setInt(10, u.getAlertThreshold());
            stmt.setInt(11, u.getSuperAccountId());
            int affected = stmt.executeUpdate();
            if (affected == 1) {

                //---------------------------------
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int smsOutId = rs.getInt(1);

                    //---------------------------------
                    SMSCredits smsCredits = new SMSCredits();
                    smsCredits.setUsername(u.getUsername());
                    //create account
                    smsCredits.setActionType('1');
                    smsCredits.setActionTime(new Date());
                    smsCredits.setPrevious_balance(0);
                    smsCredits.setNumCredits(u.getMaxTotal());
                    smsCredits.setNew_balance(u.getMaxTotal());

                    SMSCreditsUserBeanData beanData = new SMSCreditsUserBeanData();
                    beanData.persistUpdate(conn, smsCredits);
                    //-------------------------------
//                    SmsUtilisationSmsOutBeanData smsUtilizationData = new SmsUtilisationSmsOutBeanData();
//                    smsUtilizationData.insertSmsUtilization(conn, smsUtil);
                }

            }
            return affected == 1;
        } catch (SQLException e) {
            System.err.println("Error addUser():" + e.getMessage());
            return false;
        }

    }

    /**
     *
     * @param username
     * @param password
     * @return 0 for unsuceful
     */
    public int clientLogin(Connection conn, String username, String password) {
        String sql = "select * from tUSER where username = ? and password =? and admin = 4";
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                System.err.println("User not found");
                return 0;
            }

        } catch (SQLException e) {
            System.err.println("clientLogin(String username, String password): " + e.getMessage());
            return 0;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public String login() {
        System.err.println("Login clicked");
        if (userBean != null) {

            Connection conn = DbUtil.getConnection();
            int userId = this.clientLogin(conn, userBean.getUsername(), userBean.getPassword());
            if (userId > 0) {
                userBean = this.getUser(conn, userId);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(AUTH_KEY, userBean);
                DbUtil.closeConnection(conn);
//                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", uD.getUser(userId));
                return "template";
            } else {
                System.err.println("User not found()");
                return null;
            }

        } else {
            System.err.println("UserBean null");
            return null;
        }

    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .remove(AUTH_KEY);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
                .remove("conn");

        userBean.setUsername("");
        userBean.setPassword("");
        userBean.setMessage("Logout successfull");
        return "logout";
    }
//-------------------------------------------------------------------------------

    /**
     *
     * @param conn
     * @param ub
     * @return 0 if no changes are to be made , 1 (deduct form main account(Add
     * deducted value to subAccount)) 2(deduct form subAccount(Add deducted
     * value to main account))
     */
    public boolean updateSmsCredits(Connection conn, UserBean u) {
        String sql = "update tUSER set max_total = ? where id = ?";
        ResultSet rs = null;

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, u.getMaxTotal());
            stmt.setInt(2, u.getId());
            int affected = stmt.executeUpdate();
            return affected == 1;
        } catch (SQLException e) {
            System.err.println("updateSmsCredits(UserBean u):" + e.getMessage());
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public boolean updateSubAccCredits(Connection conn, UserBean mainAcc, UserBean subAcc, int smsUpdate) {

        SMSCredits smsCredits = new SMSCredits();
        smsCredits.setUsername(getUser(conn, subAcc.getId()).getUsername());
        SMSCreditsUserBeanData beanData = new SMSCreditsUserBeanData();

        int des = checkSmsCredits(conn, subAcc);
        if (des == 1) {
            UserBean adminA = getUser(conn, mainAcc.getId());
            adminA.setMaxTotal(adminA.getMaxTotal() - smsUpdate);
            if (updateSmsCredits(conn, adminA)) {
                UserBean subAccount = getUser(conn, subAcc.getId());
                subAccount.setMaxTotal(smsUpdate + subAccount.getMaxTotal());

                boolean affeted = updateSmsCredits(conn, subAccount);
                if (affeted) {

                    //---------------------------------
                    //add to sub account
                    smsCredits.setActionType('1');
                    smsCredits.setActionTime(new Date());
                    smsCredits.setPrevious_balance(subAcc.getMaxTotal());
                    smsCredits.setNumCredits(smsUpdate);
                    smsCredits.setNew_balance(subAcc.getMaxTotal() + smsUpdate);

                    beanData.persistUpdate(conn, smsCredits);
                    //-------------------------------

                }

                System.err.println("Deduct from admin:" + affeted);
                return affeted;

            } else {
                System.err.println("1:  updateSubAccCredits +++> no rows affected");
                return false;
            }

        } else if (des == 2) {
            UserBean subAccount = getUser(conn, subAcc.getId());
            subAccount.setMaxTotal(subAccount.getMaxTotal() - smsUpdate);
            if (updateSmsCredits(conn, subAccount)) {
                UserBean adminA = getUser(conn, mainAcc.getId());
                adminA.setMaxTotal(adminA.getMaxTotal() + smsUpdate);

                boolean affeted = updateSmsCredits(conn, adminA);
                if (affeted) {
                    //deduct from sub acc
                    smsCredits.setActionType('2');
                    smsCredits.setActionTime(new Date());
                    smsCredits.setPrevious_balance(subAcc.getMaxTotal() + smsUpdate);
                    smsCredits.setNumCredits(smsUpdate);
                    smsCredits.setNew_balance(subAcc.getMaxTotal());
                    beanData.persistUpdate(conn, smsCredits);
                }
                System.err.println("Add to main Admin:" + affeted);
                return affeted;
            } else {
                System.err.println("2:  updateSubAccCredits +++> no rows affected");
                return false;
            }

        } else {
            System.err.println("Decision could not be made:" + des);
            return false;
        }

    }

    public int checkSmsCredits(Connection conn, UserBean ub) {
        ResultSet rs = null;
        String sql = "select * from tUSER where id = ?";

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ub.getId());
            rs = stmt.executeQuery();
            if (rs.next()) {
                int maxCreditsDb = 0;
                maxCreditsDb = getUser(conn, rs.getInt("id")).getMaxTotal();
                if (maxCreditsDb > ub.getMaxTotal()) {

                    return 2;
                } else if (maxCreditsDb < ub.getMaxTotal()) {
                    return 1;
                } else {
                    System.err.println(maxCreditsDb + ":max credit db: vs: max total" + ub.getMaxTotal());
                    return 0;
                }
            } else {
                System.err.println("No user found");
                return 0;
            }
        } catch (SQLException e) {
            System.err.println("checkSmsCredits(Connection conn, UserBean ub)" + e.getMessage());
            return 0;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public boolean updateAccount(Connection conn, UserBean account) {
        String sql = "update tUSER set  password = ?  where id = ?";
        PreparedStatement stmt = null;
        try {
            conn = DbUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, account.getPassword());
            stmt.setInt(2, account.getId());
            int affected = stmt.executeUpdate();
            return affected == 1;
        } catch (SQLException e) {
            System.err.println("updateAccount(Connection conn, UserBean account)" + e.getMessage());
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public boolean isSmsCreditPossible(Connection conn, UserBean b, int decision) {

        if (decision == 1) {
            //(Add to subaccount) check if admin can give out
            UserBean bSub = getUser(conn, b.getId());
            int adminSmsCredits = getUser(conn, bSub.getSuperAccountId()).getMaxTotal();
            int subAccSmsCredits = b.getMaxTotal();
            if (adminSmsCredits >= subAccSmsCredits) {
                return true;
            } else {
                b.setErrorCredits("Sorry sms credits exceeds available amount");
                return false;
            }

        } else if (decision == 2) {
            //(Deduct) check if sub account has that amount
            int subAccSmsCredits = getUser(conn, b.getId()).getMaxTotal();
            int passedSmsCredits = b.getMaxTotal();

            if (subAccSmsCredits >= passedSmsCredits) {
                return true;
            } else {
                b.setErrorCredits("Sorry sms credits exceeds available amount");
                return false;
            }

        } else {
            return false;
        }

    }

    //---------------------------------------------------------------------------
    public static UserBean getUser(Connection conn, int userId) {
        String sql = "select * from tUSER where id = ?";
        ResultSet rs = null;

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                UserBean uB = new UserBean();
                uB.setId(rs.getInt("id"));
                uB.setUsername(rs.getString("username"));
                uB.setPassword(rs.getString("password"));
                uB.setOrganization(rs.getString("organization"));
                uB.setMaxTotal(rs.getInt("max_total"));
                uB.setContactNumber(rs.getString("contact_number"));
                uB.setEmailAddress(rs.getString("email_address"));
                uB.setSuperAccountId(rs.getInt("super_account_id"));
                uB.setSelected(false);
                return uB;
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Error getUser(): " + e.getMessage());
            return null;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static boolean isUserNameExists(Connection conn, String use) {
        String sql = "select * from tUSER where username = ?";
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, use);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.err.println("isUserNameExists: " + e.getMessage());
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public List<UserBean> getAdminUsers(Connection conn, int userId) {
        String sql = "select * from tUSER  where super_account_id = ?";
        ResultSet rs = null;
        ArrayList<UserBean> al = new ArrayList<>();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                al.add(getUser(conn, rs.getInt("id")));
            }
            return al;
        } catch (SQLException e) {
            System.err.println("getAdminUsers(int userId):" + e.getMessage());
            return null;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static boolean isAlterSmsCredits(SmsOutUserBean bean, int status) {

        return false;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public List<UserBean> getUserBeans() {
        return userBeans;
    }

    public void setUserBeans(List<UserBean> userBeans) {
        this.userBeans = userBeans;
    }

}
