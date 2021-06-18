package com.example.asd;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DbMain extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CocktailDb";
    //제목
    TextView text_Main;
    // 정렬을 위한 버튼
    Button button_Name;
    Button button_Alcohol;
    Button button_Sugar;
    Button button_Body;
    Button button_Unique;;

    //커스텀 리스트뷰 설정
    ArrayAdapter<String> arrayAdapter;
    ArrayList<Cocktail> arrayList;
    ListView listView;
    MyAdapterCocktail myAdapterCocktail;
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //커스텀 액티비티 연결
        setContentView(R.layout.activity_cocktail_custom);
        listView = (ListView)findViewById(R.id.listView);
        mDbOpenHelper = new DatabaseHelper(this);
        db=mDbOpenHelper.getWritableDatabase();

        //제목 텍스트박스
        text_Main=(TextView)findViewById(R.id.text_main);

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
        listView.setOnItemClickListener(onClickListener);
        listView.setOnItemLongClickListener(longClickListener);

        //커스텀 리스트뷰
        arrayList = new ArrayList<>();
        loadDataInListView();
        mDbOpenHelper.onCreate(db);
    }
    //커스텀 뷰에 데이타 올리기
    private void loadDataInListView() {
        arrayList = mDbOpenHelper.getAllData0();
        myAdapterCocktail = new MyAdapterCocktail(this,arrayList,countryFlags);
        listView.setAdapter(myAdapterCocktail);
        myAdapterCocktail.notifyDataSetChanged();
    }
    //아이템 클릭 했을 때
    private final AdapterView.OnItemClickListener onClickListener = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            Log.e("On Click","position = "+position);
            String[] nowData = new String[8];
            nowData[0] = ((Cocktail) myAdapterCocktail.getItem(position)).getName();
            nowData[1] = Integer.toString(((Cocktail) myAdapterCocktail.getItem(position)).getSugar());
            nowData[2]=Integer.toString(((Cocktail) myAdapterCocktail.getItem(position)).getAlcohol());
            nowData[3]=Integer.toString(((Cocktail) myAdapterCocktail.getItem(position)).getBody());
            nowData[4]=Integer.toString(((Cocktail) myAdapterCocktail.getItem(position)).getUnique_());
            nowData[5]=((Cocktail) myAdapterCocktail.getItem(position)).getBase();
            nowData[6]=Integer.toString(((Cocktail) myAdapterCocktail.getItem(position)).getId());

            Log.d("nowdata 0: ",nowData[0]);
            Log.d("nowdata 1: ",nowData[1]);
            Log.d("nowdata 2: ",nowData[2]);
            Log.d("nowdata 3: ",nowData[3]);
            Log.d("nowdata 4: ",nowData[4]);
            Log.d("nowdata 5: ",nowData[5]);
            Log.d("nowdata 6: ",nowData[6]);
        }
    };
    // 데이터 길게 클릭했을때
    private final AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener(){
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("Long Click","position = "+position);
            String[] nowData = new String[7];
            nowData[0] = ((Cocktail) myAdapterCocktail.getItem(position)).getName();
            nowData[1] = Integer.toString(((Cocktail) myAdapterCocktail.getItem(position)).getSugar());
            nowData[2]=Integer.toString(((Cocktail) myAdapterCocktail.getItem(position)).getAlcohol());
            nowData[3]=Integer.toString(((Cocktail) myAdapterCocktail.getItem(position)).getBody());
            nowData[4]=Integer.toString(((Cocktail) myAdapterCocktail.getItem(position)).getUnique_());
            nowData[5]=((Cocktail) myAdapterCocktail.getItem(position)).getBase();
            nowData[6]=Integer.toString(((Cocktail) myAdapterCocktail.getItem(position)).getId());

            String viewData = nowData[0]+", "+nowData[1]+", "+nowData[2]+", "+nowData[3]+", "+nowData[4]+", "+nowData[5];
            AlertDialog.Builder dialog = new AlertDialog.Builder(com.example.asd.DbMain.this);
            dialog.setTitle("내 칵테일에 저장")
                    .setMessage("해당 칵테일을 저장 하시겠습니까?"+"\n"+viewData)
                    .setPositiveButton("네",(dialog12, which)-> {
                        Toast.makeText(com.example.asd.DbMain.this, "칵테일을 저장했습니다.", Toast.LENGTH_SHORT).show();
                        mDbOpenHelper.insertData(nowData);
                    })
                    .setNegativeButton("아니오",(dialog1,which)->{
                        Toast.makeText(com.example.asd.DbMain.this,"저장을 취소했습니다.",Toast.LENGTH_SHORT).show();
                    })
                    .create()
                    .show();
            return false;
        }
    };
    //각 버튼 클릭시 기능 구현
    public void nameButton(View v){
        Comparator<Cocktail> Asc = new Comparator<Cocktail>() {
            @Override
            public int compare(Cocktail c1, Cocktail c2) {
                return c1.getName().compareTo(c2.getName());
            }
        };
        Collections.sort(arrayList,Asc);
        myAdapterCocktail.notifyDataSetChanged();
    }
    public void sugarButton(View v){
        Comparator<Cocktail> Desc = new Comparator<Cocktail>() {
            @Override
            public int compare(Cocktail cocktail, Cocktail t1) {
                return (t1.getSugar()-cocktail.getSugar());
            }
        };
        Collections.sort(arrayList,Desc);
        myAdapterCocktail.notifyDataSetChanged();
    }
    public void alcoholButton(View v){
        Comparator<Cocktail> Desc = new Comparator<Cocktail>() {
            @Override
            public int compare(Cocktail cocktail, Cocktail t1) {
                return (t1.getAlcohol()-cocktail.getAlcohol());
            }
        };
        Collections.sort(arrayList,Desc);
        myAdapterCocktail.notifyDataSetChanged();
    }
    public void bodyButton(View v){
        Comparator<Cocktail> Desc = new Comparator<Cocktail>() {
            @Override
            public int compare(Cocktail cocktail, Cocktail t1) {
                return (t1.getBody()-cocktail.getBody());
            }
        };
        Collections.sort(arrayList,Desc);
        myAdapterCocktail.notifyDataSetChanged();
    }
    public void uniqueButton(View v){
        Comparator<Cocktail> Desc = new Comparator<Cocktail>() {
            @Override
            public int compare(Cocktail cocktail, Cocktail t1) {
                return (t1.getUnique_()-cocktail.getUnique_());
            }
        };
        Collections.sort(arrayList,Desc);
        myAdapterCocktail.notifyDataSetChanged();
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