package com.example.asd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import static com.example.asd.MainActivity.sub;

public class RecommendMain extends Activity implements View.OnClickListener {
    double SUGAR = 0;
    double ALCOHOL = 0;
    double BODY = 0;
    double UNIQUE_ = 0;
    int N=0;
    int resID=0;

    Button connect_btn;                 // ip 받아오는 버튼

    private Handler mHandler;

    private Socket socket;

    private DataOutputStream dos;
    private DataInputStream dis;

    private String ip = "172.30.1.46";            // IP 번호
    private int port = 8096;                          // port 번호

    private final String dbName = "InnerDatabase(SQLite).db";
    private final String tableName = "user_table";

    ArrayList<Cocktail> arrayList;
    SQLiteDatabase db;
    DatabaseHelper mDbOpenHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_main);
        connect_btn = (Button)findViewById(R.id.buttonSubmit);
        connect_btn.setOnClickListener(this);
        CalcAvg();

    }
    public  void CalcAvg(){
        mDbOpenHelper = new DatabaseHelper(this);
        db=mDbOpenHelper.getWritableDatabase();
        int[] a=loadDataInListView();
        mDbOpenHelper.onCreate(db);
        SUGAR = a[0];
        ALCOHOL = a[1];
        BODY = a[2];
        UNIQUE_ = a[3];
        N=a[4];
        SUGAR=SUGAR/N;
        ALCOHOL=ALCOHOL/N;
        BODY=BODY/N;
        UNIQUE_=UNIQUE_/N;
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
            case R.id.checkBox1:
                if (checked) {
                    SUGAR = SUGAR * 1.2;
                    break;
                }
                else{
                    SUGAR = SUGAR / 1.2;
                    break;
                }
            case R.id.checkBox2:
                if (checked) {
                    ALCOHOL = ALCOHOL * 1.2;
                    break;
                }
                else{
                    ALCOHOL = ALCOHOL / 1.2;
                    break;
                }
            case R.id.checkBox3:
                if (checked) {
                    BODY = BODY * 1.2;
                    break;
                }
                else {
                    BODY = BODY / 1.2;
                    break;
                }
            case R.id.checkBox4:
                if (checked) {
                    UNIQUE_ = UNIQUE_ * 1.2;
                    break;
                }
                else {
                    UNIQUE_ = UNIQUE_ / 1.2;
                    break;
                }
        }
    }

    Button buttonSubmit;
    public static final int sub = 1001;
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonSubmit:     // ip 받아오는 버튼
                String sugar = Double.toString(SUGAR);
                String alcohol = Double.toString(ALCOHOL);
                String body= Double.toString(BODY);
                String unique_ = Double.toString(UNIQUE_);
                String pass= sugar+" "+alcohol+" "+body+" "+unique_;
                connect(pass);
                while (resID==0) {
                }
                Intent intent = new Intent(getApplicationContext(), RecommendResult.class);
                intent.putExtra("res",resID);
                startActivityForResult(intent,sub);

        }
    }


    // 로그인 정보 db에 넣어주고 연결시켜야 함.
    void connect(String pass) {
        mHandler = new Handler();
        Log.w("connect", "연결 하는중");
        // 받아오는거
        Thread checkUpdate = new Thread() {
            public void run() {

                // 서버 접속
                try {
                    socket = new Socket(ip, port);
                    Log.w("서버 ", "접속됨");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }


                try {
                    dos = new DataOutputStream(socket.getOutputStream());   // output에 보낼꺼 넣음
                    dis = new DataInputStream(socket.getInputStream());//input에 받을꺼 넣어짐
                    dos.writeUTF(pass);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                byte[] a;
                // 서버에서 계속 받아옴 - 한번은 문자, 한번은 숫자를 읽음. 순서 맞춰줘야 함.
                try {
                    Log.w("값", "받아옴");
                    resID= ((int)dis.read());


                } catch (Exception e) {

                }
            }
        };
        // 소켓 접속 시도, 버퍼생성
        checkUpdate.start();
    }
    private int[] loadDataInListView() {
        int[] a = mDbOpenHelper.getRequireData();
        return a;
    }
}
