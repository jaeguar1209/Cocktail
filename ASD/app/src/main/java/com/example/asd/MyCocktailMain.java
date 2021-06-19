package com.example.asd;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyCocktailMain extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "UserDb";
    //제목
    TextView text_Main;
    // 정렬을 위한 버튼
    Button button_Name;
    Button button_Alcohol;
    Button button_Sugar;
    Button button_Body;
    Button button_Unique;
    Button button_delete;
    //커스텀 리스트뷰 설정
    ArrayAdapter<String> arrayAdapter;
    ArrayList<Cocktail> arrayList;
    ListView listView;
    MyAdapterUser myAdapterUser;
    SQLiteDatabase db;
    DatabaseHelper mDbOpenHelper;
    int[] countryFlags={R.drawable.jackcoke,R.drawable.godfather,R.drawable.mintjulpe,R.drawable.hottoddy,R.drawable.highball,
            R.drawable.irishcoffee,R.drawable.screwdriver,R.drawable.saltydog,R.drawable.godmother,R.drawable.balalaika,
            R.drawable.kamikaje,R.drawable.sexonthebeach,R.drawable.bluelagoon,R.drawable.vodkatonic,R.drawable.brainhemorrhage,
            R.drawable.orgazm,R.drawable.americano,R.drawable.bluesapphire,R.drawable.angelskiss,R.drawable.midorisour,
            R.drawable.bandb,R.drawable.kahluamilk,R.drawable.appetizer,R.drawable.bluemoon,R.drawable.ginrickey,
            R.drawable.gibson,R.drawable.gimlet,R.drawable.gintonic,R.drawable.ginbuck,R.drawable.faust,
            R.drawable.katharsis,R.drawable.xyz,R.drawable.mojito,R.drawable.rumandcoke};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //액티비티 연결
        setContentView(R.layout.mycocktail_main);
        listView = (ListView)findViewById(R.id.listView);
        mDbOpenHelper = new DatabaseHelper(this);
        db=mDbOpenHelper.getWritableDatabase();

        //정렬 버튼
        button_Name=(Button)findViewById(R.id.button_name);
        button_Name.setOnClickListener(this);
        button_Sugar=(Button)findViewById(R.id.button_sugar);
        button_Sugar.setOnClickListener(this);
        button_Alcohol=(Button)findViewById(R.id.button_alcohol);
        button_Alcohol.setOnClickListener(this);
        button_Body=(Button)findViewById(R.id.button_body);
        button_Body.setOnClickListener(this);
        button_Unique=(Button)findViewById(R.id.button_unique);
        button_Unique.setOnClickListener(this);


        listView.setAdapter(arrayAdapter);

        //커스텀 리스트뷰
        arrayList = new ArrayList<>();
        loadDataInListView();
        mDbOpenHelper.onCreate(db);
    }

    // 리스트뷰에 데이타 로드
    private void loadDataInListView() {
        arrayList = mDbOpenHelper.getAllData1();
        myAdapterUser = new MyAdapterUser(this,arrayList,countryFlags);
        listView.setAdapter(myAdapterUser);
        myAdapterUser.notifyDataSetChanged();
    }

    //각 버튼 클릭시 기능 구현
    public void nameButton(View v){
        Comparator<Cocktail> Asc = new Comparator<Cocktail>() {
            @Override
            public int compare(Cocktail c1, Cocktail c2) {
                return c1.getName().compareTo(c2.getName());
            }
        };
        Collections.sort(arrayList,Asc);
        myAdapterUser.notifyDataSetChanged();
    }
    public void sugarButton(View v){
        Comparator<Cocktail> Desc = new Comparator<Cocktail>() {
            @Override
            public int compare(Cocktail cocktail, Cocktail t1) {
                return (t1.getSugar()-cocktail.getSugar());
            }
        };
        Collections.sort(arrayList,Desc);
        myAdapterUser.notifyDataSetChanged();
    }
    public void alcoholButton(View v){
        Comparator<Cocktail> Desc = new Comparator<Cocktail>() {
            @Override
            public int compare(Cocktail cocktail, Cocktail t1) {
                return (t1.getAlcohol()-cocktail.getAlcohol());
            }
        };
        Collections.sort(arrayList,Desc);
        myAdapterUser.notifyDataSetChanged();
    }
    public void bodyButton(View v){
        Comparator<Cocktail> Desc = new Comparator<Cocktail>() {
            @Override
            public int compare(Cocktail cocktail, Cocktail t1) {
                return (t1.getBody()-cocktail.getBody());
            }
        };
        Collections.sort(arrayList,Desc);
        myAdapterUser.notifyDataSetChanged();
    }
    public void uniqueButton(View v){
        Comparator<Cocktail> Desc = new Comparator<Cocktail>() {
            @Override
            public int compare(Cocktail cocktail, Cocktail t1) {
                return (t1.getUnique_()-cocktail.getUnique_());
            }
        };
        Collections.sort(arrayList,Desc);
        myAdapterUser.notifyDataSetChanged();
    }
    // 정렬 버튼 클릭시
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_name:
                nameButton(v);
                break;
            case R.id.button_sugar:
                sugarButton(v);
                break;
            case R.id.button_alcohol:
                alcoholButton(v);
                break;
            case R.id.button_body:
                bodyButton(v);
                break;
            case R.id.button_unique:
                uniqueButton(v);
                break;
        }
    }
}