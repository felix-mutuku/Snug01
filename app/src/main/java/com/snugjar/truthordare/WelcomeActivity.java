package com.snugjar.truthordare;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class WelcomeActivity extends AppCompatActivity {
    ImageView logo, backgrnd;
    TextView disclaimer;
    Button buttonAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        logo = findViewById(R.id.logo);
        disclaimer = findViewById(R.id.disclaimer);
        buttonAgree = findViewById(R.id.buttonAgree);
        backgrnd = findViewById(R.id.backgrnd);

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        disclaimer.setTypeface(face);

        Glide
                .with(WelcomeActivity.this)
                .load(R.drawable.logo)
                .skipMemoryCache(true)
                .crossFade()
                .into(logo);

        Glide
                .with(WelcomeActivity.this)
                .load("https://lh3.googleusercontent.com/WEiNc5H_kBJ0OOXu-ann0uWnbNMiycqbxyzMfOEl_KUpR-v_XZCZ3YBCYBU_XcpMZCjLGonTR1FhDwv6dhnAP2KznDuKL-Bqhja43uB0pdbfzE28GSTizvhsRHmXJg_pWYPVRN57FnKRhz4Hl3fnf316BXmz6-ycjEbHF0Vnw6KLhCBE31W2bv0ePB-wM1AT-GxNL3L3toC1QZrAWtYcKyim7_Qjh5VoJr594H149v7jWQ1eagPnY3ohqlt_uRN1Ly6VGhtFb21OvkywEK7blXzDCCgvY-beOTpqwUShwRmsZzfUfKtn5wcDkH9hairiW-if80wBcR5sA8iimTxFAsFCvPb77beh3dzg2Ym6Txp6cR-FR1Ps5KjtMeGnodbLRSsyjkonTplLDumIj_otm5okUB9bV9m7jdinbXxTg7NsA_GrjpPZdQm0NCaz3GftogMby5WGU-C85f2G8oZVFRuWaekQzKBZcdhQ8j8C0mz_dt2ax2UX9sOicp6rk2qxlLt1QpqZ0E-GF2pGeO28uGLifPRgyXUd_GrITWA5MFWdGMOVEEV4B7timTD8ZE_a-viFxYwwjEEO-sZxPZoWDitPK1AGrNsJFYonLpuG=w390-h694-no")
                .error(R.drawable.lady_pic)
                .crossFade()
                .into(backgrnd);

        buttonAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                //WelcomeActivity.this.finish();
                presentActivity(v);
            }
        });
    }

    public void presentActivity(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(MainActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }
}
