package com.example.asd;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MyAdapterInit extends BaseAdapter {
    ArrayList<Cocktail> arrayList = new ArrayList<Cocktail>();
    Context context;
    SQLiteDatabase db;
    DatabaseHelper mDbOpenHelper;
    int[] flags;
    public MyAdapterInit(Context context, ArrayList<Cocktail> arrayList, int[] countryFlags){
        this.context=context;
        this.arrayList=arrayList;
        this.flags=countryFlags;
        mDbOpenHelper = new DatabaseHelper(context);
        db=mDbOpenHelper.getWritableDatabase();
    }
    @Override
    public int getCount(){
        return 4;
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
        convertView = inflater.inflate(R.layout.custom_init_view,null);
        //이제 아이템에 존재하는 텍스트뷰 객체들을 view객체에서 찾아 가져온다
        TextView cName = (TextView)convertView.findViewById(R.id.cocktailName2);
        RatingBar cSugar = (RatingBar) convertView.findViewById(R.id.rb_sugar2);
        RatingBar cAlcohol = (RatingBar)convertView.findViewById(R.id.rb_alcohol2);
        RatingBar cBody = (RatingBar)convertView.findViewById(R.id.rb_body2);
        RatingBar cUnique = (RatingBar)convertView.findViewById(R.id.rb_unique2);
        TextView cBase=(TextView)convertView.findViewById(R.id.cocktailBase2);
        ImageView cImage=(ImageView)convertView.findViewById(R.id.imageView2);
        CheckBox cBox=(CheckBox)convertView.findViewById(R.id.checkBox1);
        //현재 포지션에 해당하는 아이템에 글자를 적용하기 위해 list배열에서 객체를 가져온다.
        Cocktail cocktail = arrayList.get(position);

        cName.setText(cocktail.getName());
        cSugar.setRating((float)(cocktail.getSugar()*0.05));
        cAlcohol.setRating((float) (cocktail.getAlcohol()*0.05));
        cBody.setRating((float) (cocktail.getBody()*0.05));
        cUnique.setRating((float) (cocktail.getUnique_()*0.05));
        cBase.setText(cocktail.getBase());
        cImage.setImageResource(flags[cocktail.getId()-1]);
        cBox.setChecked(cBox.isChecked());
        cBox.setChecked(((ListView)parent).isItemChecked(position));
        cBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] nowData = new String[7];
                nowData[0] = cocktail.getName();
                nowData[1] = String.valueOf(cocktail.getSugar());
                nowData[2] = String.valueOf(cocktail.getAlcohol());
                nowData[3] = String.valueOf(cocktail.getBody());
                nowData[4] = String.valueOf(cocktail.getUnique_());
                nowData[5] = cocktail.getBase();
                nowData[6] = String.valueOf(cocktail.getId());
                boolean haveItem = mDbOpenHelper.isFavorite(Long.parseLong(nowData[6]));
                // 내 칵테일에 있는 데이터의 경우 클릭시 데이터 삭제
                if(haveItem){
                    mDbOpenHelper.deleteColumn1(Long.valueOf(nowData[6]));
                }
                // 클릭시 내 칵테일에 데이터 추가.
                else{
                    mDbOpenHelper.insertData(nowData);
                }
                setFavoriteButton(cBox,nowData[6]);
            }
        });
        return convertView;
    }
    private void setFavoriteButton(CheckBox cBox,String idx){
        boolean haveItem = mDbOpenHelper.isFavorite(Long.parseLong(idx));
        if(haveItem){
            cBox.setChecked(true);
        }
        else{
            cBox.setChecked(false);
        }
    }
}
