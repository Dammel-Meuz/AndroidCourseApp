package com.example.coursesapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.HardwareRenderer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursesapp.data.DataBaseHelpers.DataBaseHelpers;
import com.example.coursesapp.data.model.Liste;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FragmentList extends Fragment {
    RecyclerView recyclerView;
    DataBaseHelpers db;


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

            Intent intent = new Intent(getActivity(),AddListCourseActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        db = new DataBaseHelpers(getContext());
        ListAdapter listAdapter =new ListAdapter(db.listeCourse());
        recyclerView.setAdapter(listAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Liste liste = new Liste();
        TextView titreList, dateList,montant,idlist;

        public ListHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.onerow, parent, false));

            titreList = itemView.findViewById(R.id.titleList);
            dateList = itemView.findViewById(R.id.dateList);
            montant = itemView.findViewById(R.id.montantListe);
            idlist = itemView.findViewById(R.id.idList);
            itemView.setOnClickListener(this);

        }


        public void bind(Liste liste) {
            this.liste = liste;
            titreList.setText(liste.getListName());
            dateList.setText(liste.getDateCreation());
            montant.setText(liste.getMontantPrevue()+" fr");
            idlist.setText(Integer.toString(liste.getIdList()));

        }

        @Override
        public void onClick(View v) {
            Log.i("clic", "Cliquer");
            Intent intent = new Intent(getActivity(),Activity_list_product.class);
            intent.putExtra("id",liste.getIdList());
            startActivity(intent);
        }
    }
        class ListAdapter extends RecyclerView.Adapter<ListHolder>{

            List<Liste> listList;

            public ListAdapter(List<Liste> listList) {
                this.listList = listList;
            }

            @NonNull
            @NotNull
            @Override
            public ListHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());

                return new ListHolder(inflater,parent);
            }

            @Override
            public void onBindViewHolder(@NonNull @NotNull ListHolder holder, int position) {
                holder.bind(listList.get(position));

            }

            @Override
            public int getItemCount() {
                return listList.size();
            }
            //to delete item

            public void deleteItemList (int position){
                Liste item = listList.get(position);
                db.getWritableDatabase();
                db.deleteList(item.getIdList());
                listList.remove(position);
                notifyItemRemoved(position);
            }
        }

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                //int position = viewHolder.getPosition();
                switch (direction){
                    case ItemTouchHelper.LEFT:
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Delete Task");
                        builder.setMessage("Are you sure you want ti delete this task");
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(getContext()," moved LEFT",Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("annuler", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              dialog.cancel();
                            }
                        });
                        Toast.makeText(getContext()," moved LEFT",Toast.LENGTH_SHORT).show();
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        break;
                    case ItemTouchHelper.RIGHT:
                        Toast.makeText(getContext()," moved RIGHT",Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builderR = new AlertDialog.Builder(getContext());
                        builderR.setTitle("Udate Task");
                        builderR.setMessage("Are you sure you want ti Udate this task");
                        Toast.makeText(getContext()," moved LEFT",Toast.LENGTH_SHORT).show();
                        AlertDialog dialogR = builderR.create();
                        dialogR.show();
                        break;
                }

            }
        };
    }


