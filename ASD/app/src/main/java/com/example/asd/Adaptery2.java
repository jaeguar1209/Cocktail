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

public class Adaptery2 extends RecyclerView.Adapter<Adaptery2.MyViewHolder> {

    private Context mContext;
    private List<ModelClass> mData;

    public Adaptery2(Context mContext, List<ModelClass> mData){
        this.mContext = mContext;
        this.mData = mData;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v=inflater.inflate(R.layout.search_nameresult, parent, false);

        return new MyViewHolder(v);
    }




    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.strDrink.setText(mData.get(position).getStrDrink());
        holder.strInstruction.setText(mData.get(position).getStrInstructions());
        holder.strRecipe.setText(mData.get(position).getIngredList());
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
        TextView strInstruction;
        TextView strRecipe;


        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            strDrink = itemView.findViewById(R.id.cocktailName);
            strDrinkThumb = itemView.findViewById(R.id.imageView2);
            strInstruction = itemView.findViewById(R.id.Instruction);
            strRecipe = itemView.findViewById(R.id.recipe);
        }
    }
}
