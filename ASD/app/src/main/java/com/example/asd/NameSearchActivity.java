package com.example.asd;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NameSearchActivity extends AppCompatActivity {
    String JSON_URL ="https://www.thecocktaildb.com/api/json/v1/1/search.php?s=";


    List<ModelClass> cocktailList;
    RecyclerView recyclerView;
    String name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_recycle);

        cocktailList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String name = bundle.getString("name");

        StringBuilder BaseURL = new StringBuilder();
        BaseURL.append(JSON_URL);
        BaseURL.append(name);
        JSON_URL = BaseURL.toString();

        NameSearchActivity.GetData getData = new NameSearchActivity.GetData();
        getData.execute();
    }

    public class GetData extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... strings) {
            String current = "";

            try {
                URL url;
                HttpURLConnection urlConnection = null;

                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(in);

                    int data = isr.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }
        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("drinks");

                for (int i = 0; i < 3; i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    ModelClass model = new ModelClass();
                    model.setStrDrink(jsonObject1.getString("strDrink"));
                    model.setStrDrinkThumb(jsonObject1.getString("strDrinkThumb"));
                    model.setStrInstructions(jsonObject1.getString("strInstructions"));
                    String ingredientIndex;
                    for (int j = 1; j < 10; j++){
                        StringBuilder ingredient = new StringBuilder();
                        ingredient.append("strIngredient");
                        ingredient.append(j);
                        ingredientIndex = ingredient.toString();
                        if (jsonObject1.getString(ingredientIndex) == "null")
                            break;
                        model.AddingredList(jsonObject1.getString(ingredientIndex));
                    }
                    cocktailList.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            PutDataIntoRecyclerView(cocktailList);
        }
    }


    private void PutDataIntoRecyclerView(List<ModelClass> cocktailList){

        Adaptery2 adaptery = new Adaptery2(this, cocktailList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adaptery);
    }

}
