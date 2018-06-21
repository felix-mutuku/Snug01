package com.snugjar.truthordare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.mindorks.placeholderview.annotations.swipe.SwipeView;

@Layout(R.layout.main_card_view)
public class MainCard {

    private String SChoice;
    private MediaPlayer mPlayer;
    private Intent intent, i;

    @View(R.id.mainImageView)
    private ImageView profileImageView;

    @View(R.id.mainTxt)
    private TextView mainTxt;

    @SwipeView
    private android.view.View cardView;
    private Choices mProfile;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public MainCard(Context context, Choices profile, SwipePlaceHolderView swipeView) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
    }

    @Resolve
    private void onResolved() {
        /*MultiTransformation multi = new MultiTransformation(
                new BlurTransformation(mContext, 30),
                new RoundedCornersTransformation(
                        mContext, MainUtils.dpToPx(7), 0,
                        RoundedCornersTransformation.CornerType.TOP));*/

        /*Glide.with(mContext)
                .load(mProfile.getImageUrl())
                .bitmapTransform(multi)
                .into(profileImageView);*/

        Glide
                .with(mContext)
                .load(mProfile.getImageUrl())
                .error(R.drawable.lady_pic)
                .crossFade()
                .into(profileImageView);

        mainTxt.setText(mProfile.getName());
        Typeface face = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");
        mainTxt.setTypeface(face);
    }

    @Click(R.id.mainImageView)
    private void onClick() {
        //Log.d("EVENT", "profileImageView click");
        mSwipeView.addView(this);

        SChoice = mProfile.getLocation();

        switch (SChoice) {
            case "Welcome":
                cardClick();

                break;
            case "Couples":
                cardClick();

                break;
            case "Siblings":
                cardClick();

                break;
            case "Friends":
                cardClick();

                break;
            case "Parents":
                cardClick();

                break;
            case "Exes":
                cardClick();

                break;
            case "ThirtySix":
                cardClick();

                break;
            default:
                //do nothing when card clicked

                break;
        }

    }

    @SwipeOut
    private void onSwipedOut() {
        //Log.d("EVENT", "onSwipedOut");
        //mSwipeView.addView(this);
        //Negative Swipe
        //Play negative sound
        playSoundError();
    }

    @SwipeCancelState
    private void onSwipeCancelState() {
        //Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn() {
        //Log.d("EVENT", "onSwipedIn");
        //Positive Swipe
        SChoice = mProfile.getLocation();

        //play positive sound
        playSoundPositive();

        if (!SChoice.equals("Welcome")) {
            positiveSwipe();
        }
    }

    @SwipeInState
    private void onSwipeInState() {
        //Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState() {
        //Log.d("EVENT", "onSwipeOutState");
    }

    private void playSoundError() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.reset();
        }

        mPlayer = MediaPlayer.create(mContext, R.raw.glass);
        mPlayer.start();
    }

    private void playSoundPositive() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.reset();
        }

        mPlayer = MediaPlayer.create(mContext, R.raw.burp);
        mPlayer.start();
    }

    private void cardClick() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("CardClick", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("info", SChoice);
        editor.apply();

        Intent intent = new Intent(mContext, InfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    private void positiveSwipe() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("PositiveSwipe", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("choice", SChoice);
        editor.apply();

        Intent intent = new Intent(mContext, PlayCardsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
