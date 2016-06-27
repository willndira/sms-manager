/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.AllowedAlphanumerics;
import model.UserBean;
import service.AllowedAlphanumericsdata;
import service.UserData;
import util.DbUtil;
import util.HelperUtil;

/**
 *
 * @author Norrey Osako
 */
public class SubAccountController {

    private UserBean mainAdmin;
    private UserBean subAccount;
    private UserBean selectedItem;

    static private UserBean editAccount;
    private int maxCredit;
    private List<UserBean> subAccounts;
    private AllowedAlphanumerics allowedAlphanumerics;
    private AllowedAlphanumericsdata allowedAlphanumericsdata;
    private UserData userData;
    private boolean createAccount;

    public SubAccountController() {

        maxCredit = 0;
        if (subAccount != null) {
            subAccount = null;
        }

        if (subAccounts != null) {
            subAccounts = null;
        }

        selectedItem = new UserBean();
        subAccount = new UserBean();
        subAccounts = new ArrayList<>();
        editAccount = new UserBean();

        allowedAlphanumerics = new AllowedAlphanumerics();
        allowedAlphanumericsdata = new AllowedAlphanumericsdata();
        createAccount = false;
        userData = new UserData();
        ExternalContext tmpEC;
        Map sMap;
        tmpEC = FacesContext.getCurrentInstance().getExternalContext();
        sMap = tmpEC.getSessionMap();

        Connection conn = DbUtil.getConnection();
        mainAdmin = (UserBean) sMap.get(UserBean.AUTH_KEY);
        mainAdmin = userData.getUser(conn, mainAdmin.getId());
        int max_total = 0;
        int adminId = 0;
        max_total = mainAdmin.getMaxTotal();
        if (mainAdmin != null) {
            adminId = mainAdmin.getId();
            subAccounts = userData.getAdminUsers(conn, adminId);
            allowedAlphanumerics = allowedAlphanumericsdata.getOneAllowedAlphanumerics(conn, mainAdmin.getUsername());

            subAccount.setMaxTotal(0);
            subAccount.setEnableEmailAlert(false);
            subAccount.setOrganization(mainAdmin.getUsername());
            subAccount.setSuperAccountId(mainAdmin.getId());
        }

        DbUtil.closeConnection(conn);

    }

    public String addSubAccount() {

        Connection conn = DbUtil.getConnection();
        if (subAccount != null) {
            subAccount.setAdmin(2);

            subAccount.setEndDate(null);

            boolean affected = userData.createSubAccounts(conn, subAccount, mainAdmin);

            if (affected) {
                int newSmsCredits = mainAdmin.getMaxTotal() - subAccount.getMaxTotal();
                if (newSmsCredits < 0) {
                    newSmsCredits = 0;
                }
                mainAdmin.setMaxTotal(newSmsCredits);
                userData.updateSmsCredits(conn, mainAdmin);

                AllowedAlphanumerics adminAlphanumerics = allowedAlphanumericsdata.getOneAllowedAlphanumerics(conn, mainAdmin.getUsername());

                AllowedAlphanumerics aa = new AllowedAlphanumerics();
                aa.setUsername(subAccount.getUsername());
                aa.setAlphanumerics(adminAlphanumerics.getAlphanumerics());
                allowedAlphanumericsdata.addAllowedAlphanumerics(conn, aa);

                DbUtil.closeConnection(conn);
                return "accounts";
            } else {
                DbUtil.closeConnection(conn);
                return null;
            }

        } else {
            DbUtil.closeConnection(conn);
            return null;
        }

    }

    public String moveToEditAcc(UserBean sub) {

        if (sub != null) {
            Connection conn = DbUtil.getConnection();
            editAccount = userData.getUser(conn, sub.getId());
            DbUtil.closeConnection(conn);
            maxCredit = mainAdmin.getMaxTotal() + editAccount.getMaxTotal();
            return "editAccount";
        } else {
            return null;
        }

    }

    public String updateSMSCredits() {
        UserBean sub = selectedItem;
        if (sub.getId() != 0) {
            String desc = sub.getSelectedAction();
            if (HelperUtil.stringCharcters(desc)) {
                int d = 0;
                if (desc.equalsIgnoreCase("add")) {
                    System.out.println("Add is selected");
                    d = 1;
                } else if (desc.equalsIgnoreCase("update")) {
                    System.out.println("Update selected");
                    d = 2;
                } else {
                    System.out.println("No action selected");
                    d = 3;
                }

                //-----------------------------------------------------------
                Connection conn = DbUtil.getConnection();
                if (userData.isSmsCreditPossible(conn, sub, d)) {
                    int diffValue = 0;
                    int dbMaxValue = 0;
                    String selAction = sub.getSelectedAction();
                    if (!selAction.equals("")) {

                        dbMaxValue = userData.getUser(conn, sub.getId()).getMaxTotal();
                        try {
                            if (selAction.equalsIgnoreCase("add")) {
                                System.err.println("Add is selected======>");
                                diffValue = sub.getMaxTotal();
                                sub.setMaxTotal(diffValue + dbMaxValue);
                                boolean check = userData.updateSubAccCredits(conn, mainAdmin, sub, diffValue);
                                if (check) {
                                    UserBean updatedUser = userData.getUser(conn, sub.getId());
                                    if (updatedUser != null) {
                                        return linkTosubAcc(updatedUser);
                                    } else {
                                        System.err.println("updateSMSCredits(UserBean sub): object null 1");
                                        return "accounts";
                                    }
                                } else {
                                    System.err.println("updateSMSCredits(UserBean sub): no update was done:" + diffValue);
                                    return "accounts";
                                }

                            } else if (selAction.equalsIgnoreCase("update")) {
                                System.err.println("Update is selected======>");
                                diffValue = sub.getMaxTotal();
                                //dbMaxValue value of subAccount balance
                                //
                                sub.setMaxTotal(dbMaxValue - diffValue);
                                boolean check = userData.updateSubAccCredits(conn, mainAdmin, sub, diffValue);
                                if (check) {
                                    UserBean updatedUser = userData.getUser(conn, sub.getId());
                                    if (updatedUser != null) {
                                        return linkTosubAcc(updatedUser);
                                    } else {
                                        System.err.println("updateSMSCredits(UserBean sub): object null 3");
                                        return "accounts";
                                    }
                                } else {
                                    System.err.println("updateSMSCredits(UserBean sub): object null 4");
                                    return "accounts";
                                }
                            } else {
                                System.err.println("No action was selected");
                                return "accounts";
                            }
                        } finally {
                            if (conn != null) {
                                try {
                                    conn.close();
                                } catch (SQLException ex) {
                                    Logger.getLogger(SubAccountController.class.getName()).log(Level.SEVERE, null, ex);
                                    return "accounts";
                                }
                            }
                        }

                    } else {
                        return "accounts";
                    }
                } else {
                    editAccount = sub;
                    System.err.println("Update was not done since it is not possible");
                    return linkToMyeditSMSCredit(sub);

                }

                //------------------------------------------------------------
            } else {
                System.err.println("Select value was empty");
                return "accounts";
            }

        } else {
            System.err.println("Object passed is null");
            return "accounts";
        }

    }

    public String linkToMysubAcc() {

        UserBean u = selectedItem;
        if (u == null) {
            System.err.println("Object passed is null");
            return "accounts";
        }
        subAccount = null;
        Connection conn = DbUtil.getConnection();
        subAccount = userData.getUser(conn, u.getId());
        DbUtil.closeConnection(conn);
        if (subAccount != null) {
            return "account";
        } else {
            System.err.println("Object is not found");
            return "accounts";
        }

    }

    public String linkTosubAcc(UserBean u) {

        if (u == null) {
            System.err.println("Object passed is null");
            return "accounts";
        }
        subAccount = null;
        Connection conn = DbUtil.getConnection();
        subAccount = userData.getUser(conn, u.getId());
        DbUtil.closeConnection(conn);
        if (subAccount != null) {
            return "account";
        } else {
            System.err.println("Object is not found");
            return "accounts";
        }

    }

    public String linkToeditSMSCredit() {
        UserBean sub = selectedItem;
        if (sub != null) {
            Connection conn = DbUtil.getConnection();
            subAccount = userData.getUser(conn, sub.getId());
            DbUtil.closeConnection(conn);
            editAccount.setId(subAccount.getId());
            return "editSmsCredits";
        } else {
            return null;
        }

    }

    public String linkToMyeditSMSCredit(UserBean sub) {

        if (sub != null) {
            Connection conn = DbUtil.getConnection();
            subAccount = userData.getUser(conn, sub.getId());
            DbUtil.closeConnection(conn);
            editAccount.setId(subAccount.getId());
            return "editSmsCredits";
        } else {
            return null;
        }

    }

    public String linkToeditSubAcc() {
        UserBean sub = selectedItem;
        if (sub != null) {
            Connection conn = DbUtil.getConnection();
            editAccount = userData.getUser(conn, sub.getId());
            DbUtil.closeConnection(conn);
            return "editAccount";
        } else {
            return null;
        }
    }

    public String updateSubAcc() {
        UserBean sub = selectedItem;
        if (sub != null) {
            Connection conn = DbUtil.getConnection();
            try {
                int userId = 0;
                userId = sub.getId();
                if (userId > 0) {
                    boolean aff = userData.updateAccount(conn, sub);
                    if (aff) {
                        UserBean u = userData.getUser(conn, userId);
                        return this.linkTosubAcc(u);
                    } else {
                        System.err.println("Update not done");
                        return "accounts";
                    }

                } else {
                    System.err.println("USer id not initialised");
                    return "accounts";
                }

            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SubAccountController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        } else {
            return null;
        }
    }

    public String link() {
        return "accounts";
    }

    public boolean checkUserName(String userN) {
        boolean re = false;
        Connection conn = DbUtil.getConnection();
        re = userData.isUserNameExists(conn, userN);
        DbUtil.closeConnection(conn);
        return re;
    }

    public int getMaxCredit() {
        return maxCredit;
    }

    public void setMaxCredit(int maxCredit) {
        this.maxCredit = maxCredit;
    }

    public UserBean getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(UserBean selectedItem) {
        this.selectedItem = selectedItem;
    }

    public UserBean getMainAdmin() {

        return mainAdmin;
    }

    public void setMainAdmin(UserBean mainAdmin) {

        this.mainAdmin = mainAdmin;
    }

    public UserBean getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(UserBean subAccount) {
        this.subAccount = subAccount;
    }

    public AllowedAlphanumerics getAllowedAlphanumerics() {
        return allowedAlphanumerics;
    }

    public void setAllowedAlphanumerics(AllowedAlphanumerics allowedAlphanumerics) {
        this.allowedAlphanumerics = allowedAlphanumerics;
    }

    public boolean isCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(boolean createAccount) {
        this.createAccount = createAccount;
    }

    public List<UserBean> getSubAccounts() {
        Connection conn = DbUtil.getConnection();
        if (FacesContext.getCurrentInstance().getRenderResponse()) {
            subAccounts = userData.getAdminUsers(conn, mainAdmin.getId());
        }
        DbUtil.closeConnection(conn);
        return subAccounts;
    }

    public void setSubAccounts(List<UserBean> subAccounts) {
        this.subAccounts = subAccounts;
    }

    public UserBean getEditAccount() {
        return editAccount;
    }

    public void setEditAccount(UserBean editAccount) {
        this.editAccount = editAccount;
    }

}
