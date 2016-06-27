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
public class AllowedAlphanumerics implements Serializable{

    private int id;
    private String username;
    private String alphanumerics;

    public AllowedAlphanumerics() {
        this.id = 0;
        this.username = "";
        this.alphanumerics = "";
    }

    public AllowedAlphanumerics(int id, String username, String alphanumerics) {
        this.id = id;
        this.username = username;
        this.alphanumerics = alphanumerics;
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

    public String getAlphanumerics() {
        return alphanumerics;
    }

    public void setAlphanumerics(String alphanumerics) {
        this.alphanumerics = alphanumerics;
    }

}
