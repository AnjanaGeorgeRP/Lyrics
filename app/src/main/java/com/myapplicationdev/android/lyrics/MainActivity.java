package com.myapplicationdev.android.lyrics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText etArtist, etTrack;
    Button search;
    TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etArtist = findViewById(R.id.etArtist);
        etTrack = findViewById(R.id.etLyrics);
        search = findViewById(R.id.btnSearch);
        tvResult = findViewById(R.id.tvResult);

    }

    @Override
    protected void onResume() {
        super.onResume();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String artist = etArtist.getText().toString().trim();
                String track = etTrack.getText().toString().trim();
                String url = "https://orion.apiseeds.com/api/music/lyric/"+artist+"/"+track+"?apikey=cI5eeMjFiXKzgYXCG6Y1NhnM2NDofva0m4BfD5UuxFAxsLC5TDAx9nfX8bDWo03E";
                HttpRequest request = new HttpRequest(url);
                request.setOnHttpResponseListener(mHttpResponseListener);
                request.setMethod("GET");
                request.execute();
            }
        });
    }

    // Code for step 2 start
    private HttpRequest.OnHttpResponseListener mHttpResponseListener =
            new HttpRequest.OnHttpResponseListener() {
                @Override
                public void onResponse(String response){

                    // process response here
                    try {
                        JSONObject root = new JSONObject(response);

                        JSONObject resultObj = root.getJSONObject("result");
                        JSONObject artistObj = resultObj.getJSONObject("artist");
                        String name = artistObj.getString("name");


                        JSONObject trackObj = resultObj.getJSONObject("track");
                        String trackname = trackObj.getString("name");
                        String text = trackObj.getString("text");


                        String displayResults = "Artist name: " + name+ "\n\nTrack Name: "
                                    + trackname + "\n\nText: " + text + "\n";
                        tvResult.setText(displayResults);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }

                }
            };
}
