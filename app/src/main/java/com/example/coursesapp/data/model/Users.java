package com.example.coursesapp.data.model;

public class Users {
    private int userid;
    private String username;
    private String passeword;

    public Users(){

    }
    public Users(int userid, String username, String passeword) {
        this.userid = userid;
        this.username = username;
        this.passeword = passeword;
    }

    public int getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getPasseword() {
        return passeword;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasseword(String passeword) {
        this.passeword = passeword;
    }
}
