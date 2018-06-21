package com.snugjar.truthordare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.widget.ImageView;
import android.widget.TextView;

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
public class MainPlayCard {

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

    public MainPlayCard(Context context, Choices profile, SwipePlaceHolderView swipeView) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
    }

    @Resolve
    private void onResolved() {
        //load images for the cards
        Glide
                .with(mContext)
                .load(mProfile.getImageUrl())
                .error(R.drawable.lady_pic)
                .crossFade()
                .into(profileImageView);

        //load text for the cards
        mainTxt.setText(mProfile.getName());
        Typeface face = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");
        mainTxt.setTypeface(face);
    }

    @Click(R.id.mainImageView)
    private void onClick() {
        //when card gets clicked by user
    }

    @SwipeOut
    private void onSwipedOut() {
        //Log.d("EVENT", "onSwipedOut");
        //negative swipe
    }

    @SwipeCancelState
    private void onSwipeCancelState() {
        //Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn() {
        //Log.d("EVENT", "onSwipedIn");
        //Positive Swipe
    }

    @SwipeInState
    private void onSwipeInState() {
        //Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState() {
        //Log.d("EVENT", "onSwipeOutState");
    }

}
