package com.snugjar.truthordare;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.Objects;

public class PlayCardsActivity extends AppCompatActivity {
    TextView logo;
    String info;
    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    View rootLayout;
    TextView resetText;
    AdView mAdView;
    ConnectivityManager cManager;
    NetworkInfo nInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("PositiveSwipe", Context.MODE_PRIVATE);
        info = sharedPreferences.getString("choice", "N/A");

        //set theme appropriately of cards to play
        switch (info) {
            case "Couples":
                setTheme(R.style.Couples);
                break;
            case "Siblings":
                setTheme(R.style.Siblings);
                break;
            case "Friends":
                setTheme(R.style.Friends);
                break;
            case "Parents":
                setTheme(R.style.Parents);
                break;
            case "Exes":
                setTheme(R.style.Exes);
                break;
            case "Dares":
                setTheme(R.style.Dares);
                break;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_cards);

        logo = findViewById(R.id.logo);
        mSwipeView = findViewById(R.id.swipeView);
        rootLayout = findViewById(R.id.root_layout);
        resetText = findViewById(R.id.resetText);
        mAdView = findViewById(R.id.adView);
        mContext = getApplicationContext();

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/JennaSue.ttf");
        Typeface face2 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        logo.setTypeface(face);
        resetText.setTypeface(face2);

        //SharedPreferences sharedPreferences = getSharedPreferences("PositiveSwipe", Context.MODE_PRIVATE);
        //info = sharedPreferences.getString("choice", "N/A");

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

        int bottomMargin = MainUtils.dpToPx(160);
        Point windowSize = MainUtils.getDisplaySize(getWindowManager());
        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setIsUndoEnabled(true)
                .setHeightSwipeDistFactor(10)
                .setWidthSwipeDistFactor(5)
                .setSwipeDecor(new SwipeDecor()
                        .setViewWidth(windowSize.x)
                        .setViewHeight(windowSize.y - bottomMargin)
                        .setViewGravity(Gravity.CENTER)
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeMaxChangeAngle(2f));

        switch (info) {
            case "Couples":
                rootLayout.setBackgroundColor(getResources().getColor(R.color.rose_red));
                for (Choices profile : Objects.requireNonNull(MainUtils.loadCouples(this.getApplicationContext()))) {
                    mSwipeView.addView(new MainPlayCard(mContext, profile, mSwipeView));
                }

                break;
            case "Siblings":
                rootLayout.setBackgroundColor(getResources().getColor(R.color.sky_blue));
                for (Choices profile : Objects.requireNonNull(MainUtils.loadSiblings(this.getApplicationContext()))) {
                    mSwipeView.addView(new MainPlayCard(mContext, profile, mSwipeView));
                }

                break;
            case "Friends":
                rootLayout.setBackgroundColor(getResources().getColor(R.color.denim));
                for (Choices profile : Objects.requireNonNull(MainUtils.loadFriends(this.getApplicationContext()))) {
                    mSwipeView.addView(new MainPlayCard(mContext, profile, mSwipeView));
                }

                break;
            case "Parents":
                rootLayout.setBackgroundColor(getResources().getColor(R.color.light_purple));
                for (Choices profile : Objects.requireNonNull(MainUtils.loadParents(this.getApplicationContext()))) {
                    mSwipeView.addView(new MainPlayCard(mContext, profile, mSwipeView));
                }

                break;
            case "Exes":
                rootLayout.setBackgroundColor(getResources().getColor(R.color.black));
                for (Choices profile : Objects.requireNonNull(MainUtils.loadExes(this.getApplicationContext()))) {
                    mSwipeView.addView(new MainPlayCard(mContext, profile, mSwipeView));
                }

                break;
            case "Dares":
                rootLayout.setBackgroundColor(getResources().getColor(R.color.anchor));
                for (Choices profile : Objects.requireNonNull(MainUtils.loadDares(this.getApplicationContext()))) {
                    mSwipeView.addView(new MainPlayCard(mContext, profile, mSwipeView));
                }

                break;
            case "ThirtySix":
                for (Choices profile : Objects.requireNonNull(MainUtils.loadThirtySix(this.getApplicationContext()))) {
                    mSwipeView.addView(new MainPlayCard(mContext, profile, mSwipeView));
                }

                break;
        }

    }
}
