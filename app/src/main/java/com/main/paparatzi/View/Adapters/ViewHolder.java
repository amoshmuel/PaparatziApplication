package com.main.paparatzi.View.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.paparatzi.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    private ImageView IMG_holder;
    private TextView LBL_title,LBL_body,date;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        initViews(itemView);
    }

    private void initViews(View itemView) {
         IMG_holder=(ImageView)itemView.findViewById(R.id.photoRow_IMG_holder);
         LBL_title=(TextView)itemView.findViewById(R.id.photoRow_LBL_title);
         LBL_body=(TextView)itemView.findViewById(R.id.photoRow_LBL_body);
         date=(TextView)itemView.findViewById(R.id.photoRow_LBL_date);

         itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

             }
         });
    }

    public ImageView getIMG_holder() {
        return IMG_holder;
    }

    public void setIMG_holder(ImageView IMG_holder) {
        this.IMG_holder = IMG_holder;
    }

    public TextView getLBL_title() {
        return LBL_title;
    }

    public void setLBL_title(TextView LBL_title) {
        this.LBL_title = LBL_title;
    }

    public TextView getLBL_body() {
        return LBL_body;
    }

    public void setLBL_body(TextView LBL_body) {
        this.LBL_body = LBL_body;
    }

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
    }
}
