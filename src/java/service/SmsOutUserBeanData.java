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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.SmsOutModel;
import model.SmsOutUserBean;
import model.UserBean;
import util.HelperUtil;
import static util.HelperUtil.stringCharcters;

/**
 *
 * @author Norrey Osako
 */
public class SmsOutUserBeanData implements Serializable {

    private SmsOutUserBean smsOutUserBean;
    private List<SmsOutUserBean> smsOutUserBeans;
    private UserData userData;

    public SmsOutUserBeanData() {
        userData = new UserData();
        smsOutUserBeans = new ArrayList<>();
    }

    public List<SmsOutUserBean> getAdminSubAccSMS(Connection conn, int adminId) {
        String sql = "select tSMSOUT.id from tUSER join tSMSOUT on tSMSOUT.user = tUSER.username where tUSER.super_account_id = ? ";
        ResultSet rs = null;
        List<SmsOutUserBean> beans = new ArrayList<>();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, adminId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                SmsOutUserBean beanB = getOneSubAccSmsOut(conn, rs.getInt("tSMSOUT.id"));
                beans.add(beanB);
            }
            return beans;
        } catch (SQLException e) {
            System.err.println("getAdminSubAccSMS: " + e.getMessage());
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SmsOutUserBeanData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SmsOutUserBeanData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public SmsOutUserBean getOneSubAccSmsOut(Connection conn, int smsId) {

        String sql = "select * from tSMSOUT join tUSER on tUSER.username = tSMSOUT.user where tSMSOUT.id = ?";
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, smsId);
            //
            rs = stmt.executeQuery();
            if (rs.next()) {
                SmsOutUserBean soub = new SmsOutUserBean();
                soub.setUserBean(userData.getUser(conn, rs.getInt("tUSER.id")));//user
                soub.setSmsOutModel(getOneSmsOut(conn, rs.getInt("tSMSOUT.id")));
                return soub;
            } else {
                System.err.println("getOneSubAccSmsOut: null");
                return null;
            }

        } catch (SQLException e) {
            System.err.println("getOneSubAccSmsOut(Connection conn, int adminId)" + e.getMessage());
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SmsOutUserBeanData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SmsOutUserBeanData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    public SmsOutModel getOneSmsOut(Connection conn, int id) {
        String sql = "select * from tSMSOUT left join  tSMSSTATUS on tSMSSTATUS.id = tSMSOUT.status  where tSMSOUT.id  = ?";
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                SmsOutModel outModel = new SmsOutModel();
                outModel.setId(rs.getInt("tSMSOUT.id"));
                outModel.setRid(rs.getString("seqNo"));
                outModel.setSeqNo(rs.getString("seqNo"));
                outModel.setServiceType(rs.getString("service_type"));
                outModel.setSourceAddress(rs.getString("source_addr"));
                outModel.setDestinationAddress(rs.getString("destination_addr"));
                outModel.setMessagePayload(rs.getString("message_payload"));
                outModel.setUserMessageReference(rs.getString("user_message_reference"));
                outModel.setTimeSubmitted(HelperUtil.convetStringToDate(rs.getString("time_submitted")));
                outModel.setTimeProcessed(HelperUtil.convetStringToDate(rs.getString("time_processed")));
                outModel.setStatus(rs.getBoolean("status"));
                //outModel.setRealStatus(this.getSmsStatus(rs.getString("status")));
                outModel.setRealStatus(rs.getString("desctiption"));
                outModel.setErrorinfo(rs.getString("errorinfo"));
                outModel.setMessageId(rs.getString("message_id"));
                outModel.setSentBy(rs.getString("sentby"));
                outModel.setEsmClass(rs.getInt("esm_class"));
                outModel.setRule(rs.getString("rule"));
                outModel.setUser(rs.getString("user"));
                outModel.setSubmittedBy(rs.getString("submittedby"));
                outModel.setSmsCount(outModel.countSMS(outModel.getMessagePayload()));
                return outModel;

            } else {
                System.err.println("getOneSmsOut: null");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("getSmsOut(Connection conn, int smsId): " + e.getMessage());
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SmsOutUserBeanData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public String conDateToString(Date time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String formattedDate = simpleDateFormat.format(time);
        return formattedDate;
    }

    public static Date convetStringToDate(String d) {
        if (stringCharcters(d)) {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            try {
                d = d.trim();
                return (df.parse(d));
            } catch (ParseException ex) {
                Logger.getLogger(HelperUtil.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } else {
            return new java.util.Date();
        }

    }

    public List<SmsOutUserBean> search(Connection conn, SmsOutUserBean bean) {

        String sql = "";
        ResultSet rs = null;
        PreparedStatement stmt = null;
        ArrayList<SmsOutUserBean> arrayList = new ArrayList<>();
        try {

            if (bean.getSmsOutModel().getStartTime() != null
                    && bean.getSmsOutModel().getEndTime() != null
                    && HelperUtil.stringCharcters(bean.getUserBean().getUsername())
                    && HelperUtil.stringCharcters(bean.getSmsOutModel().getDestinationAddress())) {

                System.err.println(":::>1<:::");
                sql = "select * from tSMSOUT left join  tSMSSTATUS on tSMSSTATUS.id = tSMSOUT.status join tUSER on tUSER.username = tSMSOUT.user where tUSER.super_account_id = ? and tUSER.username = ?  and tSMSOUT.destination_addr = ? and  cast(tSMSOUT.time_submitted as date)  between ? and  ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, bean.getUserBean().getSuperAccountId());
                stmt.setString(2, bean.getUserBean().getUsername());
                stmt.setString(3, bean.getSmsOutModel().getDestinationAddress());
                stmt.setString(4, conDateToString(bean.getSmsOutModel().getStartTime()));
                stmt.setString(5, conDateToString(bean.getSmsOutModel().getEndTime())
                );

            } else if (bean.getSmsOutModel().getStartTime() != null
                    && bean.getSmsOutModel().getEndTime() != null
                    && (bean.getUserBean().getUsername() == null || bean.getUserBean().getUsername() == "")
                    && (bean.getSmsOutModel().getDestinationAddress() == null || bean.getSmsOutModel().getDestinationAddress() == "")) {
                System.err.println(":::>2<::: " + conDateToString(bean.getSmsOutModel().getStartTime())+ "tEnd: " + conDateToString(bean.getSmsOutModel().getEndTime()));
                sql = "select * from tSMSOUT left join  tSMSSTATUS on tSMSSTATUS.id = tSMSOUT.status  join tUSER on tUSER.username = tSMSOUT.user where tUSER.super_account_id = ? and cast(tSMSOUT.time_submitted as date)  between ? and  ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, bean.getUserBean().getSuperAccountId());
                stmt.setString(2, conDateToString(bean.getSmsOutModel().getStartTime()));
                stmt.setString(3, conDateToString(bean.getSmsOutModel().getEndTime()));

            } else if (bean.getSmsOutModel().getStartTime() != null
                    && bean.getSmsOutModel().getEndTime() != null
                    && HelperUtil.stringCharcters(bean.getUserBean().getUsername())
                    && (bean.getSmsOutModel().getDestinationAddress() == null || bean.getSmsOutModel().getDestinationAddress() == "")) {
                System.err.println(":::>3<::::");
                sql = "select * from tSMSOUT left join  tSMSSTATUS on tSMSSTATUS.id = tSMSOUT.status join tUSER on tUSER.username = tSMSOUT.user where tUSER.super_account_id = ? and tUSER.username = ?  and cast(tSMSOUT.time_submitted as date)  between ? and  ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, bean.getUserBean().getSuperAccountId());
                stmt.setString(2, bean.getUserBean().getUsername());
                stmt.setString(3, conDateToString(bean.getSmsOutModel().getStartTime()));
                stmt.setString(4, conDateToString(bean.getSmsOutModel().getEndTime()));

            } else if (bean.getSmsOutModel().getStartTime() != null
                    && bean.getSmsOutModel().getEndTime() != null
                    && (bean.getUserBean().getUsername() == null || bean.getUserBean().getUsername() == "")
                    && HelperUtil.stringCharcters(bean.getSmsOutModel().getDestinationAddress())) {
                System.err.println(":::>4<:::");
                sql = "select * from tSMSOUT left join  tSMSSTATUS on tSMSSTATUS.id = tSMSOUT.status join tUSER on tUSER.username = tSMSOUT.user where tUSER.super_account_id = ? and  tSMSOUT.destination_addr = ? and cast(tSMSOUT.time_submitted as date)  between ? and  ?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, bean.getUserBean().getSuperAccountId());
                stmt.setString(2, bean.getSmsOutModel().getDestinationAddress());
                stmt.setString(3, conDateToString(bean.getSmsOutModel().getStartTime()));
                stmt.setString(4, conDateToString(bean.getSmsOutModel().getEndTime()));

            } else if (bean.getSmsOutModel().getStartTime() == null
                    && bean.getSmsOutModel().getEndTime() == null
                    && HelperUtil.stringCharcters(bean.getUserBean().getUsername())
                    && HelperUtil.stringCharcters(bean.getSmsOutModel().getDestinationAddress())) {

                sql = "select * from tSMSOUT left join  tSMSSTATUS on tSMSSTATUS.id = tSMSOUT.status join tUSER on tUSER.username = tSMSOUT.user where tUSER.super_account_id = ? and tUSER.username = ?  and tSMSOUT.destination_addr = ? ";
                stmt = conn.prepareStatement(sql);
                System.err.println(":::>5<:::" + sql);
                stmt.setInt(1, bean.getUserBean().getSuperAccountId());
                stmt.setString(2, bean.getUserBean().getUsername());
                stmt.setString(3, bean.getSmsOutModel().getDestinationAddress());

            } else if (bean.getSmsOutModel().getStartTime() == null
                    && bean.getSmsOutModel().getEndTime() == null
                    && (bean.getUserBean().getUsername() == null || bean.getUserBean().getUsername() == "")
                    && HelperUtil.stringCharcters(bean.getSmsOutModel().getDestinationAddress())) {
                System.err.println(":::>6<:::");

                sql = "select * from tSMSOUT left join  tSMSSTATUS on tSMSSTATUS.id = tSMSOUT.status join tUSER on tUSER.username = tSMSOUT.user where tUSER.super_account_id = ? and  tSMSOUT.destination_addr = ? ";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, bean.getUserBean().getSuperAccountId());
                stmt.setString(2, bean.getSmsOutModel().getDestinationAddress());

            } else if (bean.getSmsOutModel().getStartTime() == null
                    && bean.getSmsOutModel().getEndTime() == null
                    && HelperUtil.stringCharcters(bean.getUserBean().getUsername())
                    && (bean.getSmsOutModel().getDestinationAddress() == null || bean.getSmsOutModel().getDestinationAddress() == "")) {
                System.err.println(":::>7<:::");

                sql = "select * from tSMSOUT left join  tSMSSTATUS on tSMSSTATUS.id = tSMSOUT.status join tUSER on tUSER.username = tSMSOUT.user where tUSER.super_account_id = ? and tUSER.username = ? ";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, bean.getUserBean().getSuperAccountId());
                stmt.setString(2, bean.getUserBean().getUsername());

            } else {
                System.err.println("Not defined" + sql);
                return null;
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                ///----------------------------------------------------------------------------

                ///----------------------------------------------------------------------------
                //SmsOutUserBean bean1 = getOneSubAccSmsOut(conn, rs.getInt("tSMSOUT.id"));
                SmsOutUserBean soub = new SmsOutUserBean();
                ///----------------------------------------------------------------------------

                //soub.setUserBean(userData.getUser(rs.getInt("tUSER.id")));//user
                UserBean uB = new UserBean();
                uB.setId(rs.getInt("tUSER.id"));
                uB.setUsername(rs.getString("tUSER.username"));
                uB.setPassword(rs.getString("tUSER.password"));
                uB.setOrganization(rs.getString("tUSER.organization"));
                uB.setMaxTotal(rs.getInt("tUSER.max_total"));
                uB.setContactNumber(rs.getString("tUSER.contact_number"));
                uB.setEmailAddress(rs.getString("tUSER.email_address"));
                uB.setSuperAccountId(rs.getInt("tUSER.super_account_id"));
                uB.setSelected(false);
                soub.setUserBean(uB);
                ///----------------------------------------------------------------------------

                SmsOutModel outModel = new SmsOutModel();
                outModel.setId(rs.getInt("tSMSOUT.id"));
                outModel.setRid(rs.getString("tSMSOUT.seqNo"));
                outModel.setSeqNo(rs.getString("tSMSOUT.seqNo"));
                outModel.setServiceType(rs.getString("tSMSOUT.service_type"));
                outModel.setSourceAddress(rs.getString("tSMSOUT.source_addr"));
                outModel.setDestinationAddress(rs.getString("tSMSOUT.destination_addr"));
                outModel.setMessagePayload(rs.getString("tSMSOUT.message_payload"));
                outModel.setUserMessageReference(rs.getString("tSMSOUT.user_message_reference"));
                
                outModel.setTimeSubmitted(convetStringToDate(rs.getString("tSMSOUT.time_submitted")));
                outModel.setTimeProcessed(convetStringToDate(rs.getString("tSMSOUT.time_processed")));
                
                outModel.setStatus(rs.getBoolean("tSMSOUT.status"));
                //outModel.setRealStatus(this.getSmsStatus(rs.getString("status")));
                outModel.setRealStatus(rs.getString("tSMSSTATUS.desctiption"));
                outModel.setErrorinfo(rs.getString("tSMSOUT.errorinfo"));
                outModel.setMessageId(rs.getString("tSMSOUT.message_id"));
                outModel.setSentBy(rs.getString("tSMSOUT.sentby"));
                outModel.setEsmClass(rs.getInt("tSMSOUT.esm_class"));
                outModel.setRule(rs.getString("tSMSOUT.rule"));
                outModel.setUser(rs.getString("tSMSOUT.user"));
                outModel.setSubmittedBy(rs.getString("tSMSOUT.submittedby"));
                outModel.setSmsCount(outModel.countSMS(outModel.getMessagePayload()));
                soub.setSmsOutModel(outModel);
                //soub.setSmsOutModel(getOneSmsOut(conn, rs.getInt("tSMSOUT.id")));
                ///----------------------------------------------------------------------------

                arrayList.add(soub);
            }
            if (arrayList.size() > 0) {

                return arrayList;

            } else {
                System.err.println("Empty ,no results for query passed");
                return null;
            }

        } catch (SQLException e) {
            System.err.println("search(Connection conn, SmsOutUserBean bean)::>>" + e.getMessage());
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SmsOutUserBeanData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SmsOutUserBeanData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

    public List<String> getAdminSubAccUsernames(Connection conn, int id) {
        String sql = "select username from tUSER where super_account_id = ?";
        List<String> names = new ArrayList<>();
        ResultSet rs = null;
        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                names.add(rs.getString("username"));
            }
            return names;
        } catch (SQLException e) {
            Logger.getLogger(SmsOutUserBeanData.class.getName()).log(Level.SEVERE, null, e);
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SmsOutUserBeanData.class.getName()).log(Level.SEVERE, null, ex);
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

}
