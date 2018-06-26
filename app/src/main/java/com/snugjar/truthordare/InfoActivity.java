package com.snugjar.truthordare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InfoActivity extends AppCompatActivity {
    ImageView logo, back;
    TextView infoTxt;
    String info, SFileName;
    Button buttonPlay;
    AdView mAdView;
    ConnectivityManager cManager;
    NetworkInfo nInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        back = findViewById(R.id.back);
        logo = findViewById(R.id.logo);
        infoTxt = findViewById(R.id.infoTxt);
        buttonPlay = findViewById(R.id.buttonPlay);
        mAdView = findViewById(R.id.adView);

        Glide
                .with(InfoActivity.this)
                .load(R.drawable.logo)
                .crossFade()
                .into(logo);

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        infoTxt.setTypeface(face);

        cManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        assert cManager != null;
        nInfo = cManager.getActiveNetworkInfo();

        if (nInfo != null && nInfo.isConnected()) {
            //when connected to the internet, show ads
            mAdView.setVisibility(View.VISIBLE);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        } else {
            //user not connected to the internet, don't show ads
            mAdView.setVisibility(View.GONE);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("CardClick", Context.MODE_PRIVATE);
        info = sharedPreferences.getString("info", "N/A");
        //infoFrom = sharedPreferences.getString("infoFrom", "N/A");

        if (info.equals("Welcome")) {
            //setting visibility of play button when activity called from playing cards
            buttonPlay.setVisibility(View.GONE);
        }

        switch (info) {
            case "Welcome":
                //show more info about the Welcome card
                SFileName = "Welcome.txt";
                readTxt();
                buttonPlay.setVisibility(View.GONE);
                break;
            case "Couples":
                //show more info about the Couples card
                SFileName = "Couples.txt";
                readTxt();
                break;
            case "Siblings":
                //show more info about the Siblings card
                SFileName = "Siblings.txt";
                readTxt();
                break;
            case "Friends":
                //show more info about the Friends card
                SFileName = "Friends.txt";
                readTxt();
                break;
            case "Parents":
                //show more info about the Parents card
                SFileName = "Parents.txt";
                readTxt();
                break;
            case "Exes":
                //show more info about the Exes card
                SFileName = "Exes.txt";
                readTxt();
                break;
            case "Dares":
                //show more info about the Exes card
                SFileName = "Dares.txt";
                readTxt();
                break;
            case "ThirtySix":
                //show more info about the Thirty Six card
                SFileName = "ThirtySix.txt";
                readTxt();
                break;
            default:
                //show more info about the card
                infoTxt.setText(info);
                break;
        }

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("PositiveSwipe", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("choice", info);
                editor.apply();

                Intent intent = new Intent(InfoActivity.this, PlayCardsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void readTxt() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open(SFileName)));
            StringBuilder total = new StringBuilder();
            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                total.append(mLine).append("\n");
            }
            String finalString = total.toString();

            infoTxt.setText(finalString);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
