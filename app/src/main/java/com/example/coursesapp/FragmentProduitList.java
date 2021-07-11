package com.example.coursesapp;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursesapp.data.DataBaseHelpers.DataBaseHelpers;
import com.example.coursesapp.data.model.Liste;
import com.example.coursesapp.data.model.Produits;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class FragmentProduitList extends Fragment {
    RecyclerView recyclerView;
    DataBaseHelpers db;
    int idlistrecup;
   //ActionBar actionBar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //pour placer le menu dans  l activity cas d un fragment
        setHasOptionsMenu(true);
    }
    @Override
    //pour placer le menu xml
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu,menu);
    }
    //L action apres clic menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAjout){

            Intent intent = new Intent(getActivity(),AdjouProductActivity.class);
            intent.putExtra("id",idlistrecup = (int)getActivity().getIntent().getSerializableExtra("id"));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_produit,container,false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        db = new DataBaseHelpers(getContext());
        idlistrecup = (int)getActivity().getIntent().getSerializableExtra("id");
        FragmentProduitList.ProductListAdapter productListAdapter=new ProductListAdapter(db.listProduct(idlistrecup));
        recyclerView.setAdapter(productListAdapter);

        //swip
        RecycleItemTouchHelper recycleItemTouchHelper = new RecycleItemTouchHelper(getContext(), recyclerView, 200) {
            @Override
            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<RecycleItemTouchHelper.MyButton> buffer) {

                buffer.add(new MyButton(getContext(),
                        "Delete",
                        30,
                        0,
                        Color.parseColor("#FF3c30"),
                        new MyButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                final int position = viewHolder.getAdapterPosition();
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Delete Task");
                                builder.setMessage("Are you sure you want ti delete this task");
                                builder.setPositiveButton("confirmer",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                productListAdapter.deleteItemList(position);

                                            }
                                        });
                                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        productListAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                Toast.makeText(getContext(),"Delete Click",Toast.LENGTH_SHORT).show();
                            }
                        }));
                buffer.add(new MyButton(getContext(),
                        "Update",
                        30,
                        0,
                        // R.drawable.ic_edit_whith_launcher50_foreground,
                        Color.parseColor("#01D758"),
                        new MyButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Intent intent = new Intent(getContext(),Edit_List.class);
                                //Liste liste =new Liste();
                                intent.putExtra("id",3);
                                startActivity(intent);
                                Toast.makeText(getContext(),"Update Click",Toast.LENGTH_SHORT).show();
                            }
                        }));
            }
        };


        return view;
    }
    class ListProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
       // Produits produits = new Produits();
        TextView nameProduct, PrixProduct, quantityProduct;
        ImageView imgProduct;
        CheckBox isvalidProduct;
        boolean test = true;


        public ListProductHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.onerow_produit, parent, false));

            nameProduct = itemView.findViewById(R.id.nameProductList);
            PrixProduct = itemView.findViewById(R.id.prixProductList);
            quantityProduct = itemView.findViewById(R.id.quantityProductList);
            imgProduct = itemView.findViewById(R.id.imgProdiutlist);
            isvalidProduct = itemView.findViewById(R.id.isValidCheck);
            itemView.setOnClickListener(this);

        }
        public void bind(Produits produits) {

            test =produits.getisAccomplitAchatProduit();
            nameProduct.setText(produits.getNomProduit());
            PrixProduct.setText(produits.getPrixProduit()+" fr");
            quantityProduct.setText(String.valueOf(produits.getQuantiteProduit()));
            imgProduct.setImageURI(Uri.parse(produits.getImageProduit()));
            isvalidProduct.isChecked();



        }

        @Override
        public void onClick(View v) {
            //Toast.makeText( FragmentProduitList.this," Tout les champs doivent etre remplis ",Toast.LENGTH_LONG).show();
            Toast.makeText(getContext(),"message "+test ,Toast.LENGTH_SHORT).show();
        }
    }
    class ProductListAdapter extends RecyclerView.Adapter<ListProductHolder> {

        List<Produits> productList;

        public ProductListAdapter(List<Produits> productList) {
            this.productList = productList;
        }

        @NonNull
        @NotNull
        @Override
        public ListProductHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            return new ListProductHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull ListProductHolder holder, int position) {
        holder.bind(productList.get(position));
        }
        @Override
        public int getItemCount() {
            return productList.size();
        }

        //to delete item

        public void deleteItemList (int position){
            Produits item = productList.get(position);
            db.getWritableDatabase();
            db.deleteList(item.getIdList());
            productList.remove(position);
            notifyItemRemoved(position);
        }
    }
}
