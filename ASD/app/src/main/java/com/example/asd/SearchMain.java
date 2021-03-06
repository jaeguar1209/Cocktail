package com.example.asd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

public class SearchMain extends Activity {
    Button button2;
    TextInputEditText textInputEditText1, textInputEditText2;
    Button SearchBtn1, SearchBtn2;
    ImageView imageView10,imageView11,imageView12;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);
        textInputEditText1 = findViewById(R.id.ingredient);

        textInputEditText2 = findViewById(R.id.cocktailName);
        imageView10 = findViewById(R.id.imageView10);
        imageView11= findViewById(R.id.imageView11);
        imageView12 = findViewById(R.id.imageView12);

        SearchBtn1 = (Button)findViewById(R.id.SearchBtn1);
        SearchBtn2 = (Button)findViewById(R.id.SearchBtn2);

        imageView10.setImageResource(R.drawable.illu1);
        imageView11.setImageResource(R.drawable.illu2);
        imageView12.setImageResource(R.drawable.illu3);


        SearchBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredient = textInputEditText1.getText().toString();
                Intent intent = new Intent(SearchMain.this, SearchActivity.class);
                intent.putExtra("ingredient", ingredient);
                startActivity(intent);
            }
        });

        SearchBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = textInputEditText2.getText().toString();
                Intent intent = new Intent(SearchMain.this, NameSearchActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
    }


}