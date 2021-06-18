package com.example.asd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

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

public class RecommendResult extends Activity{
    private RadarChart chart;
    private String[] nowData;
    String CocktailBase;
    TextView tv_name,tv_sugar,tv_alcohol,tv_body,tv_unique,tv_base;
    ToggleButton btn_favorite;
    ScaleAnimation scaleAnimation;
    DatabaseHelper mDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.recommend_result);
        Intent intent = getIntent();
        int res=intent.getIntExtra("res",-1);
        mDbOpenHelper = new DatabaseHelper(this);
        String[] nowData=mDbOpenHelper.selectData(res);
        chart = (RadarChart) findViewById(R.id.chart_radar);
        makeChart(nowData);
        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_name.setText(nowData[0]);
        tv_sugar = (TextView)findViewById(R.id.tv_sugar);
        tv_sugar.setText(nowData[1]);
        tv_alcohol=(TextView)findViewById(R.id.tv_alcohol);
        tv_alcohol.setText(nowData[2]);
        tv_body=(TextView)findViewById(R.id.tv_body);
        tv_body.setText(nowData[3]);
        tv_unique=(TextView)findViewById(R.id.tv_unique);
        tv_unique.setText(nowData[4]);
        tv_base=(TextView)findViewById(R.id.tv_base);
        tv_base.setText(nowData[5]);
        btn_favorite=(ToggleButton)findViewById(R.id.btn_favorite);
        btn_favorite.setOnClickListener(onClickListener);
        scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF,0.7f,Animation.RELATIVE_TO_SELF,0.7f);
        scaleAnimation.setDuration(500);
        BounceInterpolator bounceInterPolator = new BounceInterpolator();
        scaleAnimation.setInterpolator(bounceInterPolator);
        setFavoriteButton(nowData);
    }

    private void setFavoriteButton(String[] nowData){
        boolean haveItem = mDbOpenHelper.isFavorite(Long.parseLong(nowData[6]));
        if(haveItem){
            btn_favorite.setBackgroundResource(R.drawable.ic_favorite);
            btn_favorite.startAnimation(scaleAnimation);
        }
        else{
            btn_favorite.setBackgroundResource(R.drawable.ic_favorite_border);
            btn_favorite.startAnimation(scaleAnimation);
        }
    }
    // 좋아요 버튼 기능 구현
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean haveItem = mDbOpenHelper.isFavorite(Long.parseLong(nowData[6]));
            // 내 칵테일에 있는 데이터의 경우 클릭시 데이터 삭제
            if(haveItem){
                mDbOpenHelper.deleteColumn1(Long.valueOf(nowData[6]));
            }
            // 클릭시 내 칵테일에 데이터 추가.
            else{
                mDbOpenHelper.insertData(nowData);
            }
            setFavoriteButton(nowData);
        }
    };
    private String selectBase(String data){
        if(data.equals("Whisky"))
            return "위스키";
        else if(data.equals("Vodka"))
            return "보드카";
        else if(data.equals("Rum"))
            return "럼";
        else if(data.equals("Gin"))
            return "진";
        else
            return "리큐르";
    }
    private ArrayList<RadarEntry> dataValue(String[] nowData){
        ArrayList<RadarEntry> dataVals = new ArrayList<>();

        /* nowData
        0 : string : name
        1 : long : 당도
        2 : long : 도수
        3 : long : 바디감
        4 : long : 독특함
        5 : string : 베이스
         */
        dataVals.add(new RadarEntry(Long.parseLong(nowData[1])));
        dataVals.add(new RadarEntry(Long.parseLong(nowData[2])));
        dataVals.add(new RadarEntry(Long.parseLong(nowData[3])));
        dataVals.add(new RadarEntry(Long.parseLong(nowData[4])));
        CocktailBase = selectBase(nowData[5]);
        return dataVals;
    }
    private void makeChart(String[] nowData){
        //데이타셋

        RadarDataSet dataSet = new RadarDataSet(dataValue(nowData),nowData[0]+"("+CocktailBase+")");
        //데이타셋 색상
        dataSet.setColor(Color.GREEN);
        //데이타 색 채우기
        dataSet.setFillColor(Color.rgb(51,102,51));
        //데이타 색 채우기 허용 여부
        dataSet.setDrawFilled(true);
        //눌럿을 때 가로십자선 표시 여부
        dataSet.setDrawHighlightIndicators(false);
        //데이터 값 원으로 표시 여부
        dataSet.setDrawHighlightCircleEnabled(true);
        // 20.0 30.0 40.0 60.0 수치값
        dataSet.setValueTextSize(10f);
        RadarData data =new RadarData();
        data.addDataSet(dataSet);
        //x축
        String[] labels={"당도","도수","바디감","독특함"};
        XAxis xAxis = chart.getXAxis();
        //x축 라벨 설정
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        //x축 라벨 글자 색상
        xAxis.setTextColor(Color.BLACK);
        //x축 라벨 글자 크기
        xAxis.setTextSize(14f);

        //Y축
        YAxis yAxis = chart.getYAxis();
        //y축 단계값 표시 여부
        yAxis.setDrawLabels(false);
        //y축 단계값 글자 색상
        yAxis.setTextColor(Color.RED);
        //y축 안쪽 거미줄 단위 개수
        yAxis.setLabelCount(6,true);
        //y축 단계값 글자 크기
        yAxis.setTextSize(4f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(100f);


        //radar chart의 legend (제목 표시X)
        Legend l = chart.getLegend();
        l.setEnabled(false);

        //큰 거미줄 색
        chart.setWebColor(Color.BLACK);
        //작은 거미줄 색
        chart.setWebColorInner(Color.rgb(51,51,51));
        //배경 색
        chart.setBackgroundColor(Color.WHITE);
        //Description
        chart.getDescription().setEnabled(false);
        //거미줄 선 폭
        chart.setWebLineWidth(1.5f);
        //설정값 적용
        chart.setData(data);
    }
}