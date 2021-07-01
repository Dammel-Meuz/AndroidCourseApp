package com.example.coursesapp.data.model;

import java.time.LocalDateTime;
import java.util.Date;

public class Liste {
    private int idList;
    private String userNameListId;
    private String listName;
    private String dateCreation;
    private String  dateModification;
    private String montantPrevue;

    public Liste() {
    }

    public Liste(int idList, String usernameListId, String listName, String dateCreation, String dateModification, String montantPrevue) {
        this.idList = idList;
        this.userNameListId = userNameListId;
        this.listName = listName;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.montantPrevue = montantPrevue;
    }

    public int getIdList() {
        return idList;
    }

    public void setIdList(int idList) {
        this.idList = idList;
    }

    public String getUserNameListId() {
        return userNameListId;
    }

    public void setUserListId(String userNameListId) {
        this.userNameListId = userNameListId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDateModification() {
        return dateModification;
    }

    public void setDateModification(String dateModification) {
        this.dateModification = dateModification;
    }

    public String getMontantPrevue() {
        return montantPrevue;
    }

    public void setMontantPrevue(String montantPrevue) {
        this.montantPrevue = montantPrevue;
    }
}
