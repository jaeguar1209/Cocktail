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

public class SearchActivity extends AppCompatActivity {
    String JSON_URL ="https://www.thecocktaildb.com/api/json/v1/1/filter.php?i=";


    List<CocktailModelClass> cocktailList;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_recycle);

        cocktailList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String ingredient = bundle.getString("ingredient");
        StringBuilder BaseURL = new StringBuilder();
        BaseURL.append(JSON_URL);
        BaseURL.append(ingredient);
        JSON_URL = BaseURL.toString();

        SearchActivity.GetData getData = new SearchActivity.GetData();
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
                ArrayList<Integer> RandomA;
                RandomA = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    int a = (int) (Math.random() * jsonArray.length());
                    for (int j = 0; j < RandomA.size(); j++) {
                        if (RandomA.get(j) == a)
                            a += 1;
                    }
                    RandomA.add(a);
                }

                for (int i = 0; i < 5; i++) {
                    int j = RandomA.get(i);
                    JSONObject jsonObject1 = jsonArray.getJSONObject(j);

                    CocktailModelClass model = new CocktailModelClass();
                    model.setStrDrink(jsonObject1.getString("strDrink"));
                    model.setStrDrinkThumb(jsonObject1.getString("strDrinkThumb"));

                    cocktailList.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            PutDataIntoRecyclerView(cocktailList);
        }
    }


    private void PutDataIntoRecyclerView(List<CocktailModelClass> cocktailList){

        Adaptery adaptery = new Adaptery(this, cocktailList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adaptery);
    }




}
