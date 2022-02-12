package com.main.paparatzi.View.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.paparatzi.Control.FireBase;
import com.main.paparatzi.Model.Photo;
import com.main.paparatzi.View.Activities.PhotoActivity;
import com.main.paparatzi.R;
import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
    private ArrayList<Photo> localDataSet;
    private Context context;

    public CustomAdapter(ArrayList<Photo> list, Context context) {
        localDataSet=list;
        this.context=context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.photo_recycler_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if((localDataSet.get(position).getImageId()!=null)){
            FireBase.getInstance().downloadStorageData(localDataSet.get(position).getImageId(),context,holder.getIMG_holder());
        }
        holder.getLBL_title().setText(localDataSet.get(position).getTitle());
        holder.getLBL_body().setText(localDataSet.get(position).getBody());
        holder.getDate().setText(localDataSet.get(position).getDate().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("pttt", "onClick: "+localDataSet.get(position).getTitle()+localDataSet.get(position).getImageId());
                openPhotoActivity(localDataSet.get(position));
            }
        });


    }

    private void openPhotoActivity(Photo photo) {
        Log.d("pttt", "openPhotoActivity: ");
        Intent myIntent = new Intent(context, PhotoActivity.class);
        myIntent.putExtra("photo", photo);
         context.startActivity(myIntent);
    }

    @Override
    public int getItemCount() {
        Log.d("pttt", "getItemCount: ");
        return localDataSet.size();
    }

}
