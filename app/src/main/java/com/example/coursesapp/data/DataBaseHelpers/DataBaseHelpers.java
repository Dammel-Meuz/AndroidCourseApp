package com.example.coursesapp.data.DataBaseHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.coursesapp.data.model.Liste;
import com.example.coursesapp.data.model.Produits;
import com.example.coursesapp.data.model.Users;

import java.util.ArrayList;

public class DataBaseHelpers extends SQLiteOpenHelper {

    static final String DB_name ="CoursApp";
    static final int DB_version =1;

    private static final String USERS_TABLE = "users";
    private static final String ID = "id";
    private static final String USER_NAME = "username";
    private static final String PASSWORDS = "passeword";
   // private static final String CREATE_USERS_TABLE ="create table users(id integerger primary key,username text ,passeword text)";
   private static final String CREATE_USERS_TABLE ="CREATE TABLE " + USERS_TABLE + "" +
           "("+
              ID +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
               USER_NAME +" "+ " text," +
               PASSWORDS +" "+ " text)";

   private static final String LIST_TABLE="lists";
   private static final  String ID_LIST ="idList";
   private static final String NAME_LIST ="listName";
   private static final String DATE_CREATION_LIST="dateCreation";
   private static final String DATE_MODIFICATION_LIST="dateModification";
   private static final String MONTANT_LIST="montantPrevue";
   private static final String USER_ID="user_id";
   private static final String CREATE_LIST_TABLE ="CREATE TABLE " + LIST_TABLE + "" +
            "("+
            ID_LIST +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            USER_ID +" INTEGER,"+
            NAME_LIST +" "+ " text," +
            MONTANT_LIST+" "+"text,"+
            DATE_CREATION_LIST +" "+ " text,"+
            DATE_MODIFICATION_LIST +" "+" text,"+
            "FOREIGN KEY ("+USER_ID+") REFERENCES "+USERS_TABLE+"("+ID+")" +
            ")";

   private static final String PRODUCT_TABLE ="product";
   private static final String ID_PRODUCT ="idproduct";
   private static final String LIST_ID = "idlist";
   private static final String NAME_PRODUCT = "name";
   private static final String QUANTITY_PRODUCT = "quantity";
   private static final String PRIX_PRODUCT = "prix";
   private static final String IMAGE_PRODUCT ="image";
   private static final String ISACCOMPLITACHAT = "isAccomplitAchatProduit";

    private static final String CREATE_PRODUCT_TABLE ="CREATE TABLE " + PRODUCT_TABLE + "" +
            "("+
            ID_PRODUCT +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            LIST_ID +" INTEGER,"+
            NAME_PRODUCT +" "+ " text," +
            QUANTITY_PRODUCT+" "+"INTEGER,"+
            PRIX_PRODUCT +" "+ " INTEGER,"+
            IMAGE_PRODUCT+" "+" text,"+
            ISACCOMPLITACHAT+" "+" BOOLEAN,"+
            "FOREIGN KEY ("+LIST_ID+") REFERENCES "+LIST_TABLE+"("+ID_LIST+")" +
            ")";



    private SQLiteDatabase db;
   // private SQLiteDatabase dbList;

    public DataBaseHelpers(@Nullable Context context) {

        super(context, DB_name, null, DB_version);
        Log.d("table",CREATE_LIST_TABLE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_LIST_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LIST_TABLE);

    }

    public void onpenDataBase(){
        db =this.getWritableDatabase();
;
    }

    public void addUser(Users user){
        ContentValues cv = new ContentValues();

        cv.put(USER_NAME,user.getUsername());
        cv.put(PASSWORDS,user.getPasseword());
        db.insert(USERS_TABLE,null,cv);

    }
    //login
    public Boolean checkUserName(String userName){
        db =this.getWritableDatabase();
        Cursor cursor =db.rawQuery("select *from "+USERS_TABLE+" where "+USER_NAME+" =?",new String[]{userName});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public Boolean checkPasswordUserName(String password,String userName){
        db =this.getWritableDatabase();
        Cursor cursor =db.rawQuery("select *from "+USERS_TABLE+" where "+PASSWORDS+" =? and "+USER_NAME+" =?",new String[] {password,userName});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public void addList(Liste liste){
        ContentValues cv = new ContentValues();
        cv.put(NAME_LIST,liste.getListName());
        cv.put(MONTANT_LIST,liste.getMontantPrevue());
        cv.put(DATE_CREATION_LIST,liste.getDateCreation());
        cv.put(DATE_MODIFICATION_LIST, liste.getDateModification());
        db.insert(LIST_TABLE,null,cv);
    }

    public void deleteList(int id){
        db.delete(LIST_TABLE,ID_LIST + "=?",new String[]{String.valueOf(id)});
    }


    // Getting single list
    public Liste oneliste(int id) {
        Liste liste = null;
        String sql = "SELECT * FROM " +LIST_TABLE+"  WHERE id = " + id;
       db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            int idlist = Integer.parseInt(cursor.getString(cursor.getColumnIndex(LIST_ID)));
            String nameList = cursor.getString(cursor.getColumnIndex(NAME_LIST));
            String montantList = cursor.getString(cursor.getColumnIndex(MONTANT_LIST));
            liste = new Liste();
            liste.setIdList(idlist);
            liste.setListName(nameList);
            liste.setMontantPrevue(montantList);

        }
        cursor.close();
        db.close();
        return liste;
    }
    public void updatList(int id,Liste liste){
        ContentValues cv = new ContentValues();
        cv.put(NAME_LIST,liste.getListName());
        cv.put(MONTANT_LIST,liste.getMontantPrevue());
        cv.put(DATE_MODIFICATION_LIST,liste.getDateModification());
        cv.put(DATE_CREATION_LIST,liste.getDateModification());
        db.update(LIST_TABLE, cv,ID + "=?",new String[] {String.valueOf(id)});
    }


    public ArrayList<Liste> listeCourse(){
        String sql = "select * from "+LIST_TABLE;
        db=this.getReadableDatabase();
        ArrayList<Liste> storeCourse = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0)) ;
                String name = cursor.getString(2);
                String montant = cursor.getString(3);
                String date = cursor.getString(4);
                Liste liste = new Liste();
                liste.setIdList(id);
                liste.setListName(name);
                liste.setMontantPrevue(montant);
                liste .setDateCreation(date);
                storeCourse.add(liste);
            }while (cursor.moveToNext());
        }
                cursor.close();
                return storeCourse;
    }
    public void addproduct(Produits produits){
        ContentValues cv = new ContentValues();
        cv.put(NAME_PRODUCT,produits.getNomProduit());
        cv.put(QUANTITY_PRODUCT,produits.getQuantiteProduit());
        cv.put(PRIX_PRODUCT,produits.getPrixProduit());
        cv.put(IMAGE_PRODUCT,produits.getImageProduit());
        cv.put(ISACCOMPLITACHAT,produits.getisAccomplitAchatProduit());
        cv.put(LIST_ID,produits.getIdList());
        db.insert(PRODUCT_TABLE,null,cv);

    }
    public ArrayList<Produits> listProduct(int id){
        String sql ="select *from "+PRODUCT_TABLE+" where "+ ID_LIST+" = "+id+ " order by "+ID_PRODUCT+ " desc";
        db=this.getReadableDatabase();
        ArrayList<Produits> storProduct = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            do {
                String productName = cursor.getString(2);
                int quantity = Integer.parseInt(cursor.getString(3));
                int prix = Integer.parseInt(cursor.getString(4));
                boolean isachat =Boolean.parseBoolean(cursor.getString(6));
                String image = cursor.getString(5);
               // byte[] image = cursor.getBlob(6);
                Produits p = new Produits();
                p.setNomProduit(productName);
                p.setQuantiteProduit(quantity);
                p.setPrixProduit(prix);
                p.setAccomplitAchatProduit(isachat);
                p.setImageProduit(image);
                storProduct.add(p);
            }while (cursor.moveToNext());

            }
        cursor.close();
        return storProduct;
    }
    public void deleteProduit(int id){
        db.delete(PRODUCT_TABLE,ID_PRODUCT + "=?",new String[]{String.valueOf(id)});
    }


}
