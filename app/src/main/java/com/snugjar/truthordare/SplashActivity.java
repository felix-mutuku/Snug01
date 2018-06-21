package com.snugjar.truthordare;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.MobileAds;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.INTERNET;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DURATION = 2000; //2 second
    ConnectivityManager cManager;
    NetworkInfo nInfo;
    private boolean backbtnPress;
    public static final String DEFAULT = "N/A";
    public static final int RequestPermissionCode = 7;
    ImageView logo;
    TextView logoText,versionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.logo);
        logoText = findViewById(R.id.logoText);
        versionText = findViewById(R.id.versionText);

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/JennaSue.ttf");
        logoText.setTypeface(face);

        Typeface face2 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        versionText.setTypeface(face2);

        String versionName = BuildConfig.VERSION_NAME;
        String title = "App v" + versionName;
        versionText.setText(title);

        MobileAds.initialize(this, getString(R.string.APP_UNIT_ID));

        Glide
                .with(SplashActivity.this)
                .load(R.drawable.logo)
                .crossFade()
                .into(logo);

        cManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        assert cManager != null;
        nInfo = cManager.getActiveNetworkInfo();

        Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!backbtnPress) {
                    if (nInfo != null && nInfo.isConnected()) {
                        if (CheckingPermissionIsEnabledOrNot()) {
                            goToNext();
                        } else {
                            RequestMultiplePermission();
                        }
                    } else {
                        Intent intent = new Intent(SplashActivity.this, InternetActivity.class);
                        SplashActivity.this.startActivity(intent);
                        finish();
                    }
                }
            }
        }, SPLASH_DURATION);

    }

    private void goToNext() {
        Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
        SplashActivity.this.startActivity(intent);
        finish();
    }

    //Permission function starts from here
    private void RequestMultiplePermission() {
        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(SplashActivity.this, new String[]
                {
                        INTERNET,
                        ACCESS_NETWORK_STATE,
                        ACCESS_FINE_LOCATION,
                        ACCESS_COARSE_LOCATION,
                }, RequestPermissionCode);

    }

    //Calling override method.
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean InternetPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean NetworkStatePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean FineLocationPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean CoarseLocationPermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;

                    if (InternetPermission &&
                            NetworkStatePermission &&
                            FineLocationPermission &&
                            CoarseLocationPermission) {

                        Toast toast = Toast.makeText(SplashActivity.this, "All permissions granted :)", Toast.LENGTH_LONG);
                        View toastView = toast.getView(); //This'll return the default View of the Toast.
                        TextView toastMessage = toastView.findViewById(android.R.id.message);
                        toastMessage.setTextSize(12);
                        toastMessage.setTextColor(getResources().getColor(R.color.white));
                        toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_launcher, 0, 0, 0);
                        toastMessage.setGravity(Gravity.CENTER);
                        toastMessage.setCompoundDrawablePadding(10);
                        toastView.setBackground(getResources().getDrawable(R.drawable.bg_button));
                        toast.show();

                        goToNext();
                    } else {
                        Toast toast = Toast.makeText(SplashActivity.this, "Permissions Denied!!\nPermissions need to be granted to use the app.", Toast.LENGTH_LONG);
                        View toastView = toast.getView(); //This'll return the default View of the Toast.
                        TextView toastMessage = toastView.findViewById(android.R.id.message);
                        toastMessage.setTextSize(12);
                        toastMessage.setTextColor(getResources().getColor(R.color.white));
                        toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_launcher, 0, 0, 0);
                        toastMessage.setGravity(Gravity.CENTER);
                        toastMessage.setCompoundDrawablePadding(10);
                        toastView.setBackground(getResources().getDrawable(R.drawable.bg_button));
                        toast.show();

                        finish();
                    }
                }

                break;
        }
    }

    //Checking permission is enabled or not using function starts from here.
    public boolean CheckingPermissionIsEnabledOrNot() {
        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), INTERNET);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_NETWORK_STATE);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int FourthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED &&
                FourthPermissionResult == PackageManager.PERMISSION_GRANTED;
    }
}
