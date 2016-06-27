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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AllowedAlphanumerics;

/**
 *
 * @author Norrey Osako
 */
public class AllowedAlphanumericsdata implements Serializable {

    private AllowedAlphanumerics alphanumerics;
    private List<AllowedAlphanumerics> allowedAlphanumericses;

    public AllowedAlphanumericsdata() {
    }

    public AllowedAlphanumerics getOneAllowedAlphanumerics(Connection conn, int id) {
        String sql = "select * from tAllowedAlphanumerics where id = ?";
        ResultSet rs = null;

        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                AllowedAlphanumerics a = new AllowedAlphanumerics();
                a.setId(rs.getInt("id"));
                a.setUsername(rs.getString("username"));
                a.setAlphanumerics(rs.getString("alphanumeric"));
                return a;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("etOneAllowedAlphanumerics(int id) :" + e.getMessage());
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AllowedAlphanumericsdata.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AllowedAlphanumericsdata.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public AllowedAlphanumerics getOneAllowedAlphanumerics(Connection conn, String username) {
        String sql = "select * from tAllowedAlphanumerics where username = ?";
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                AllowedAlphanumerics a = new AllowedAlphanumerics();
                a.setId(rs.getInt("id"));
                a.setUsername(rs.getString("username"));
                a.setAlphanumerics(rs.getString("alphanumeric"));
                return a;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("etOneAllowedAlphanumerics(String username) :" + e.getMessage());
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AllowedAlphanumericsdata.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AllowedAlphanumericsdata.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public boolean addAllowedAlphanumerics(Connection conn, AllowedAlphanumerics a) {
        String sql = "insert into tAllowedAlphanumerics(username,alphanumeric) values(?,?)";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, a.getUsername());
            stmt.setString(2, a.getAlphanumerics());
            int affected = stmt.executeUpdate();
            return affected == 1;
        } catch (SQLException e) {
            System.err.println("addAllowedAlphanumerics(AllowedAlphanumerics a):" + e.getMessage());
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AllowedAlphanumericsdata.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public AllowedAlphanumerics getAlphanumerics() {
        return alphanumerics;
    }

    public void setAlphanumerics(AllowedAlphanumerics alphanumerics) {
        this.alphanumerics = alphanumerics;
    }

    public List<AllowedAlphanumerics> getAllowedAlphanumericses() {
        return allowedAlphanumericses;
    }

    public void setAllowedAlphanumericses(List<AllowedAlphanumerics> allowedAlphanumericses) {
        this.allowedAlphanumericses = allowedAlphanumericses;
    }

}
