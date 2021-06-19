package com.example.asd;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapterCocktail extends BaseAdapter{
    //어댑터에 데이터를 받기위해 생성자 만든다.
    //컨텍스트와 리스트는 받아오지만 인플레이터는 안받아온다.
    Context context;
    ArrayList<Cocktail> arrayList = new ArrayList<>();
    SQLiteDatabase db;
    DatabaseHelper mDbOpenHelper;
    int[] flags;
    public MyAdapterCocktail(Context context, ArrayList<Cocktail> arrayList,int[] countryFlags){
        this.context=context;
        this.arrayList=arrayList;
        this.flags=countryFlags;
        mDbOpenHelper = new DatabaseHelper(context);
        db=mDbOpenHelper.getWritableDatabase();
    }
    //리스트에서 항목을 몇개나 가져와서 몇개의 화면을 만들 것인지 정하는 메서드
    @Override
    public int getCount(){
        return arrayList.size();
    }
    //리스트에서 해당하는 인덱스의 데이터를 모두 가져오는 메서드
    @Override
    public Object getItem(int position){
        return arrayList.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //리스트뷰에 아이템이 인플레이트 되어있는지 확인한후
        //아이템이 없다면 아래처럼 아이템 레이아웃을 인플레이트 하고 view객체에 담는다.
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_cocktail_view,null);
        //이제 아이템에 존재하는 텍스트뷰 객체들을 view객체에서 찾아 가져온다
        TextView cName = (TextView)convertView.findViewById(R.id.cocktailName0);
        RatingBar cSugar = (RatingBar) convertView.findViewById(R.id.rb_sugar0);
        RatingBar cAlcohol = (RatingBar)convertView.findViewById(R.id.rb_alcohol0);
        RatingBar cBody = (RatingBar)convertView.findViewById(R.id.rb_body0);
        RatingBar cUnique = (RatingBar)convertView.findViewById(R.id.rb_unique0);
        TextView cBase=(TextView)convertView.findViewById(R.id.cocktailBase0);
        ImageView cImage=(ImageView)convertView.findViewById(R.id.imageView0);
        //현재 포지션에 해당하는 아이템에 글자를 적용하기 위해 list배열에서 객체를 가져온다.
        Cocktail cocktail = arrayList.get(position);

        cName.setText(cocktail.getName());
        cSugar.setRating((float)(cocktail.getSugar()*0.05));
        cAlcohol.setRating((float) (cocktail.getAlcohol()*0.05));
        cBody.setRating((float) (cocktail.getBody()*0.05));
        cUnique.setRating((float) (cocktail.getUnique_()*0.05));
        cBase.setText(cocktail.getBase());
        cImage.setImageResource(flags[cocktail.getId()-1]);
        cImage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String[] nowData = new String[7];
                nowData[0] = cocktail.getName();
                nowData[1] = String.valueOf(cocktail.getSugar());
                nowData[2] = String.valueOf(cocktail.getAlcohol());
                nowData[3] = String.valueOf(cocktail.getBody());
                nowData[4] = String.valueOf(cocktail.getUnique_());
                nowData[5] = cocktail.getBase();
                nowData[6] = String.valueOf(cocktail.getId());
                //Intent intent = new Intent(context, CocktailDetailActivity.class);
                //intent.putExtra("cocktaildata",nowData);
                //context.startActivity(intent);
            }
        });

        Log.d("adapter name",cocktail.getName());
        Log.d("adapter sugar", String.valueOf(cocktail.getSugar()));
        Log.d("adapter alcohol", String.valueOf(cocktail.getAlcohol()));
        Log.d("adapter body", String.valueOf(cocktail.getBody()));
        Log.d("adapter unique", String.valueOf(cocktail.getUnique_()));
        Log.d("adapter base",cocktail.getBase());
        return convertView;
    }
}
