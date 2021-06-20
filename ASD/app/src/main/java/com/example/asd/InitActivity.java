package com.example.asd;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class InitActivity extends AppCompatActivity{
    //커스텀 리스트뷰 설정
    //ArrayAdapter<String> arrayAdapter;
    ArrayList<Cocktail> items = new ArrayList<Cocktail>();
    ArrayAdapter<String> adapter;
    ListView listView;
    MyAdapterInit myAdapterInit;
    SQLiteDatabase db;
    DatabaseHelper mDbOpenHelper;
    Button button_Init;
    public static final int sub = 1001; /*다른 액티비티를 띄우기 위한 요청코드(상수)*/
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
        //커스텀 액티비티 연결
        setContentView(R.layout.init_cocktail);
        listView = (ListView)findViewById(R.id.listView2);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        mDbOpenHelper = new DatabaseHelper(this);
        db=mDbOpenHelper.getWritableDatabase();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_multiple_choice,items);
        listView.setAdapter(adapter);
        loadDataInListView();
        mDbOpenHelper.onCreate(db);
        button_Init = (Button)findViewById(R.id.init_button);
        button_Init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityStart.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getApplicationContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                Log.d("init button","click");
            }
        });
    }
    //커스텀 뷰에 데이타 올리기
    private void loadDataInListView() {
        items = mDbOpenHelper.getRandom0();
        myAdapterInit = new MyAdapterInit(this,items,countryFlags);
        listView.setAdapter(myAdapterInit);
        myAdapterInit.notifyDataSetChanged();
    }

}
