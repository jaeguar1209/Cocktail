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

import static com.example.asd.MainActivity.sub;

public class RecommendMain extends Activity implements View.OnClickListener {
    int SUGAR = 0;
    int ALCOHOL = 0;
    int BODY = 0;
    int UNIQUE_ = 0;
    int resID=0;
    double sweetweight=1;
    double alcoholweight=1;
    double bodyweight=1;
    double specialweight=1;
    Button connect_btn;                 // ip 받아오는 버튼

    private Handler mHandler;

    private Socket socket;

    private DataOutputStream dos;
    private DataInputStream dis;

    private String ip = "172.30.1.46";            // IP 번호
    private int port = 3000;                          // port 번호

    private final String dbName = "CocktailDB.db";
    private final String tableName = "cocktail_table";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_main);
        connect_btn = (Button)findViewById(R.id.buttonSubmit);
        connect_btn.setOnClickListener(this);
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.checkBox1:
                if (checked)
                    sweetweight=1.2;
                    break;
            case R.id.checkBox2:
                if (checked)
                    alcoholweight=1.2;
                    break;
            case R.id.checkBox3:
                if (checked)
                    bodyweight=1.2;
                break;
            case R.id.checkBox4:
                if (checked)
                    specialweight=1.2;
                break;
            default:
                sweetweight=1.0;
                alcoholweight=1.0;
                bodyweight=1.0;
                specialweight=1.0;
        }
    }

    Button buttonSubmit;
    public static final int sub = 1001;
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonSubmit:     // ip 받아오는 버튼
                connect();
                System.out.println(resID);
                Intent intent = new Intent(getApplicationContext(), RecommendResult.class);
                startActivityForResult(intent,sub);
        }
    }

    // 로그인 정보 db에 넣어주고 연결시켜야 함.
    void connect() {
        mHandler = new Handler();
        Log.w("connect", "연결 하는중");
        // 받아오는거
        Thread checkUpdate = new Thread() {
            public void run() {

                // 서버 접속
                try {
                    socket = new Socket(ip, port);
                    Log.w("서버 접속됨", "서버 접속됨");
                } catch (IOException e1) {
                    Log.w("서버접속못함", "서버접속못함");
                    e1.printStackTrace();
                }

                Log.w("edit 넘어가야 할 값 : ", "안드로이드에서 서버로 연결요청");

                try {
                    dos = new DataOutputStream(socket.getOutputStream());   // output에 보낼꺼 넣음
                    dis = new DataInputStream(socket.getInputStream());//input에 받을꺼 넣어짐
                    dos.writeUTF("19 80 60 80");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("버퍼", "버퍼생성 잘못됨");
                }
                Log.w("버퍼", "버퍼생성 잘됨");
                byte[] a;
                // 서버에서 계속 받아옴 - 한번은 문자, 한번은 숫자를 읽음. 순서 맞춰줘야 함.
                try {
                    resID= ((int)dis.read());


                } catch (Exception e) {

                }
            }
        };
        // 소켓 접속 시도, 버퍼생성
        checkUpdate.start();
    }
}
