package com.winneredge.stockly.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.winneredge.stockly.R;
import com.winneredge.stockly.apptour.AppTourActivity;
import com.winneredge.stockly.mystocks.HomeActivity;
import com.winneredge.stockly.wcommons.utils.AppUtils;

public class SplashActivity extends AppCompatActivity {

    public static final int STARTUP_DELAY = 300;
    public static final int ANIM_ITEM_DURATION = 1000;
    public static final int ITEM_DELAY = 300;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView getStarted = (TextView)findViewById(R.id.btn_choice2);
        final ImageView logoImageView = (ImageView) findViewById(R.id.img_logo);


        if(!AppUtils.isFirstTimeLaunch(SplashActivity.this)){

            ViewGroup container = (ViewGroup) findViewById(R.id.container);

            ViewCompat.animate(logoImageView)
                    .translationY(-250)
                    .setStartDelay(STARTUP_DELAY)
                    .setDuration(ANIM_ITEM_DURATION).setInterpolator(
                    new DecelerateInterpolator(1.2f)).start();
            for (int i = 0; i < container.getChildCount(); i++) {
                View v = container.getChildAt(i);
                ViewPropertyAnimatorCompat viewAnimator;

                if (!(v instanceof Button)) {
                    viewAnimator = ViewCompat.animate(v)
                            .translationY(50).alpha(1)
                            .setStartDelay((ITEM_DELAY * i) + 500)
                            .setDuration(1000);
                } else {
                    viewAnimator = ViewCompat.animate(v)
                            .scaleY(1).scaleX(1)
                            .setStartDelay((ITEM_DELAY * i) + 500)
                            .setDuration(500);
                }

                viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
            }

            findViewById(R.id.btn_choice1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent tourIntent = new Intent(SplashActivity.this, AppTourActivity.class);
                    tourIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(tourIntent);
                }
            });

            findViewById(R.id.btn_choice2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.updateLaunchInfo(SplashActivity.this);
                    launchHomeScreen();
                }
            });
        }
        else{

            ViewCompat.animate(logoImageView)
                    .scaleX(1.8f)
                    .scaleY(1.8f)
                    .setDuration(ANIM_ITEM_DURATION).setInterpolator(
                    new AccelerateInterpolator()).setListener(new ViewPropertyAnimatorListener() {
                @Override
                public void onAnimationStart(View view) {

                }

                @Override
                public void onAnimationEnd(View view) {
                    launchHomeScreen();
                }

                @Override
                public void onAnimationCancel(View view) {

                }
            }).start();
        }
    }

    private void launchHomeScreen() {
        Intent tourIntent = new Intent(SplashActivity.this, HomeActivity.class);
        tourIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(tourIntent);
    }


}
