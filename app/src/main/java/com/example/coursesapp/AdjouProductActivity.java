package com.example.coursesapp;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.coursesapp.data.DataBaseHelpers.DataBaseHelpers;
import com.example.coursesapp.data.model.Produits;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;

public class AdjouProductActivity extends AppCompatActivity {

        EditText name,quantity,prix,idlist;
        ImageView image;
        Button ajouter;
        int idlistrecup;

        DataBaseHelpers db;

        private  static  final int CAMERA_REQUEST_CODE = 100;
        private  static  final int STORAGE_REQUEST_CODE = 101;

        private  static  final int IMAGE_PICK_CAMERA_CODE = 102;
        private  static  final int IMAGE_PICK_GALLERY_CODE = 103;

        private String[] cameraPermissions;
        private String[] storagePermissions;
        private Uri imageUri;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_produit);
            name =(EditText) findViewById(R.id.productname);
            quantity = (EditText) findViewById(R.id.quantity);
            prix = (EditText) findViewById(R.id.prix);
            //idlist = (EditText) findViewById(R.id.idListProduct);
            image =(ImageView) findViewById(R.id.imageView);
            ajouter =(Button) findViewById(R.id.ajoutproduct);
            db = new DataBaseHelpers(this);



            cameraPermissions =new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
            storagePermissions =new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
            idlistrecup = (int)getIntent().getSerializableExtra("id");
            //idlist.setText(idlistrecup);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    imagePickDialog();
                }
            });

            ajouter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Produits produits =new Produits();
                    if (prix.getText().toString().equals("")||quantity.getText().toString().equals("")){
                        Toast.makeText(com.example.coursesapp.AdjouProductActivity.this,"Veuillez remplire tout les champs" ,Toast.LENGTH_LONG).show();


                    }else {
                        try {
                            produits.setIdList(idlistrecup);
                            produits.setPrixProduit(Integer.parseInt(prix.getText().toString()));
                            produits.setQuantiteProduit(Integer.parseInt(quantity.getText().toString()));
                            produits.setAccomplitAchatProduit(true);
                            produits.setNomProduit(name.getText().toString());


                            produits.setImageProduit(imageUri.toString());
                            db.onpenDataBase();
                            db.addproduct(produits);
                            Intent intent = new Intent(AdjouProductActivity.this, Activity_list_product.class);
                            intent.putExtra("id", produits.getIdList());
                            startActivity(intent);

                            Toast.makeText(com.example.coursesapp.AdjouProductActivity.this, " l id  " + imageUri, Toast.LENGTH_LONG).show();


                        } catch (NullPointerException e) {
                            Toast.makeText(com.example.coursesapp.AdjouProductActivity.this, "ajouter un image", Toast.LENGTH_LONG).show();


                        }
                    }

                    }
            });

        }

        private void imagePickDialog() {
            String[] options ={"camera","gallery"};
            AlertDialog.Builder  builder =new AlertDialog.Builder(this);
            builder.setTitle("Select for ilage");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0){
                        if (!checkCameraPermission()){
                            requestCameraPermission();
                        }else {
                            pickFromCamera();
                        }
                    }else if (which ==1){
                        if (!checkStoragePermission()){
                            requestStoragePermission();
                        }else {
                            picFromStorage();
                        }
                    }

                }
            });
            builder.create().show();
        }

        private void picFromStorage() {
            //get image from gallery
            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent,IMAGE_PICK_GALLERY_CODE);
        }

        private void pickFromCamera() {
            //get image from camera
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE,"Image title");
            values.put(MediaStore.Images.Media.DESCRIPTION,"Image description");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
            Intent cameraIntent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE);

        }

        private boolean checkStoragePermission() {
            boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    ==(PackageManager.PERMISSION_GRANTED);
            return result;
        }

        private  void requestStoragePermission(){
            ActivityCompat.requestPermissions(this,storagePermissions,STORAGE_REQUEST_CODE);
        }

        private boolean checkCameraPermission(){
            boolean result1 = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    ==(PackageManager.PERMISSION_GRANTED);
            boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                    ==(PackageManager.PERMISSION_GRANTED);

            return result && result1;
        }
        private  void requestCameraPermission(){
            ActivityCompat.requestPermissions(this,cameraPermissions,CAMERA_REQUEST_CODE);
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode){
                case CAMERA_REQUEST_CODE:{
                    if (grantResults.length>0){
                        boolean cameraAccepted =grantResults[0]==PackageManager.PERMISSION_GRANTED;
                        boolean storageAccepted =grantResults[1]==PackageManager.PERMISSION_GRANTED;
                        if (cameraAccepted && storageAccepted){
                            pickFromCamera();
                        }else {
                            Toast.makeText(this,"camera permission Required",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;

                case STORAGE_REQUEST_CODE:{
                    if (grantResults.length>0){
                        boolean storageAccepted =grantResults[0]==PackageManager.PERMISSION_GRANTED;
                        if (storageAccepted){
                            picFromStorage();
                        }
                        else {
                            Toast.makeText(this,"Storage Permission Required",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {

            if (resultCode == RESULT_OK){
                if (requestCode ==IMAGE_PICK_GALLERY_CODE){
                    CropImage.activity(data.getData())
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1,1)
                            .start(this);
                }else if (requestCode == IMAGE_PICK_CAMERA_CODE){
                    CropImage.activity(imageUri)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1,1)
                            .start(this);
                }else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (resultCode == RESULT_OK ){
                        Uri resultUri = result.getUri();
                        imageUri = resultUri;
                        image.setImageURI(resultUri);
                    }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                        Exception error =result.getError();
                        Toast.makeText(this,""+error,Toast.LENGTH_SHORT).show();
                    }

                }
            }

            super.onActivityResult(requestCode, resultCode, data);
        }

}
