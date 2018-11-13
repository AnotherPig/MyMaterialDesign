package com.zzx.spanzy.mymaterialdesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zzx.spanzy.mymaterialdesign.AnimalActivity;
import com.zzx.spanzy.mymaterialdesign.R;
import com.zzx.spanzy.mymaterialdesign.entity.Animal;
import com.zzx.spanzy.mymaterialdesign.entity.Animal;

import java.util.List;

/**
 * Created by Spanzy on 2018/11/6.
 */
public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.ViewHolder> {

    private Context mContext;
    private List<Animal> mAnimalList;

    public AnimalAdapter(List<Animal> mAnimalList) {
        this.mAnimalList = mAnimalList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.animal_item , parent ,
                false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Animal animal = mAnimalList.get(position);
                Intent intent = new Intent(mContext, AnimalActivity.class);
                intent.putExtra(AnimalActivity.ANIMAL_NNAME,animal.getName());
                intent.putExtra(AnimalActivity.ANIMAL_IMAGE_ID,animal.getImageId());
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Animal animal = mAnimalList.get(position);
        holder.animalName.setText(animal.getName());
        Glide.with(mContext).load(animal.getImageId()).into(holder.animalImage);
    }

    @Override
    public int getItemCount() {
        return mAnimalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView animalImage;
        TextView animalName;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            animalImage = itemView.findViewById(R.id.animal_image);
            animalName = itemView.findViewById(R.id.animal_name);
        }
    }
}
