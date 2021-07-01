package com.example.coursesapp.data.model;

public class Produits {
    private int idProduit;
    private int idList;
    private String nomProduit;
    private String imageProduit;
    private int prixProduit;
    private int quantiteProduit;
    private boolean isAccomplitAchatProduit;

    public Produits() {
    }

    public Produits(int idProduit, int idList, String nomProduit, String imageProduit, int prixProduit, int quantiteProduit, boolean isAccomplitAchatProduit) {
        this.idProduit = idProduit;
        this.idList = idList;
        this.nomProduit = nomProduit;
        this.imageProduit = imageProduit;
        this.prixProduit = prixProduit;
        this.quantiteProduit = quantiteProduit;
        this.isAccomplitAchatProduit = isAccomplitAchatProduit;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getIdList() {
        return idList;
    }

    public void setIdList(int idList) {
        this.idList = idList;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public String getImageProduit() {
        return imageProduit;
    }

    public void setImageProduit(String imageProduit) {
        this.imageProduit = imageProduit;
    }

    public int getPrixProduit() {
        return prixProduit;
    }

    public void setPrixProduit(int prixProduit) {
        this.prixProduit = prixProduit;
    }

    public int getQuantiteProduit() {
        return quantiteProduit;
    }

    public void setQuantiteProduit(int quantiteProduit) {
        this.quantiteProduit = quantiteProduit;
    }

    public boolean getisAccomplitAchatProduit() {
        return isAccomplitAchatProduit;
    }

    public void setAccomplitAchatProduit(boolean accomplitAchatProduit) {
        isAccomplitAchatProduit = accomplitAchatProduit;
    }
}
