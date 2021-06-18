package com.example.asd;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder> {

    private Context mContext;
    private List<CocktailModelClass> mData;

    public Adaptery(Context mContext, List<CocktailModelClass> mData){
        this.mContext = mContext;
        this.mData = mData;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v=inflater.inflate(R.layout.search_ingresult, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.strDrink.setText(mData.get(position).getStrDrink());

        Glide.with(mContext).load(mData.get(position).getStrDrinkThumb())
                .into(holder.strDrinkThumb);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView strDrink;
        ImageView strDrinkThumb;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            strDrink = itemView.findViewById(R.id.textView);
            strDrinkThumb = itemView.findViewById(R.id.imageView);
        }
    }
}

