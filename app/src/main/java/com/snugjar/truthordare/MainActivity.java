package com.snugjar.truthordare;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ImageView about, logo, share, rate;
    TextView resetText;
    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";

    View rootLayout;

    private int revealX;
    private int revealY;
    MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = getIntent();

        logo = findViewById(R.id.logo);
        about = findViewById(R.id.about);
        mSwipeView = findViewById(R.id.swipeView);
        rootLayout = findViewById(R.id.root_layout);
        resetText = findViewById(R.id.resetText);
        share = findViewById(R.id.share);
        rate = findViewById(R.id.rate);
        mContext = getApplicationContext();

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        resetText.setTypeface(face);

        Glide
                .with(MainActivity.this)
                .load(R.drawable.logo)
                .skipMemoryCache(true)
                .crossFade()
                .into(logo);

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
                        .setSwipeMaxChangeAngle(2f)
                        .setSwipeInMsgLayoutId(R.layout.swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.swipe_out_msg_view));


        for (Choices profile : Objects.requireNonNull(MainUtils.loadChoices(this.getApplicationContext()))) {
            mSwipeView.addView(new MainCard(mContext, profile, mSwipeView));
        }

        /*findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });*/

        /*findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });*/

        findViewById(R.id.undoBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateButtonUndo();
            }
        });

        findViewById(R.id.resetBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateButtonReset();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentActivity(v);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateApp();
            }
        });

        /*new AppRate(this)
                .setMinLaunchesUntilPrompt(1)
                .setShowIfAppHasCrashed(false)
                .setOnClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do something.
                        Toast.makeText(MainActivity.this, "Clicked Something", Toast.LENGTH_LONG).show();
                    }
                })
                .init();*/

        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {

            rootLayout.setVisibility(View.INVISIBLE);
            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);

            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(revealX, revealY);
                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        } else {
            rootLayout.setVisibility(View.VISIBLE);
        }

    }

    private void shareApp() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Truth or Drink ()");
            String sAux = "\nThe Truth or Drink App you have been waiting for\n\nFor Couples, Exes, Siblings, Friends and Parents\n\n";
            sAux = sAux + "http://play.google.com/store/apps/details?id=com.snugjar.truthordare";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            Snackbar snackbar = Snackbar.make(rootLayout, "Please Try again Later :(", Snackbar.LENGTH_LONG)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            shareApp();
                        }
                    });

            //Changing message text color
            snackbar.setActionTextColor(Color.WHITE);
            //Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
            sbView.setBackgroundColor(getResources().getColor(R.color.white));
            //displaying the
            snackbar.show();

        }
    }

    private void rateApp() {
        Uri uri = Uri.parse("market://details?id=" + mContext.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + mContext
                            .getPackageName())));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        unRevealActivity();
    }

    protected void revealActivity(int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);

            // create the animator for this view (the start radius is zero)
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0, finalRadius);
            circularReveal.setDuration(400);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            // make the view visible and start the animation
            rootLayout.setVisibility(View.VISIBLE);
            circularReveal.start();

        } else {
            finish();
        }
    }

    protected void unRevealActivity() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            finish();
        } else {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, revealX, revealY, finalRadius, 0);

            circularReveal.setDuration(600);
            circularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    rootLayout.setVisibility(View.INVISIBLE);
                    finish();
                }
            });

            circularReveal.start();
        }
    }

    void animateButtonUndo() {
        //Load the animation
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        double animationDuration = 1000; //time in seconds
        myAnim.setDuration((long) animationDuration);

        //Use custom animation interpolator to achieve the bounce effect
        MyBounceInterpolator interpolator = new MyBounceInterpolator(1, 10);

        myAnim.setInterpolator(interpolator);

        findViewById(R.id.undoBtn).startAnimation(myAnim);
        playSound();

        //Run button animation again after it finished
        myAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
                //when animation starts
                mSwipeView.undoLastSwipe();
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                //when animation repeats
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                //animateButton();
                findViewById(R.id.undoBtn).setVisibility(View.GONE);
            }
        });
    }

    void animateButtonReset() {
        //Load the animation
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        double animationDuration = 1000; //time in seconds
        myAnim.setDuration((long) animationDuration);

        //Use custom animation interpolator to achieve the bounce effect
        MyBounceInterpolator interpolator = new MyBounceInterpolator(5, 20);

        myAnim.setInterpolator(interpolator);

        findViewById(R.id.resetBtn).startAnimation(myAnim);
        playSound();

        //Run button animation again after it finished
        myAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
                //when animation starts
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                //when animation repeats
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                //animateButton();
                Intent i = new Intent(MainActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    void playSound() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.reset();
        }

        mPlayer = MediaPlayer.create(MainActivity.this, R.raw.toggle);
        mPlayer.start();
    }

    public void presentActivity(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(this, AboutActivity.class);
        intent.putExtra(AboutActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(AboutActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

}
