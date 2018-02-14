package com.apppartner.androidtest.animation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import com.apppartner.androidtest.MainActivity;
import com.apppartner.androidtest.R;

/**
 * Screen that displays the AppPartner icon.
 * The icon can be moved around on the screen as well as animated.
 * <p>
 * Created on Aug 28, 2016
 *
 * @author Thomas Colligan
 */
public class AnimationActivity extends AppCompatActivity
{
    //==============================================================================================
    // Class Properties
    //==============================================================================================
    protected Button mBtnFade;
    protected ImageView mIvLogo;

    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context)
    {
        Intent starter = new Intent(context, AnimationActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked

        // TODO: When the fade button is clicked, you must animate the AppPartner Icon.
        // TODO: It should fade from 100% alpha to 0% alpha, and then from 0% alpha to 100% alpha

        // TODO: The user should be able to touch and drag the AppPartner Icon around the screen.

        // TODO: Add a bonus to make yourself stick out. Music, color, fireworks, explosions!!!

        mIvLogo = (ImageView) this.findViewById(R.id.iv_app_partner_logo);
        mBtnFade = (Button) this.findViewById(R.id.btn_face);
        mBtnFade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation fadeOut = new AlphaAnimation(1, 0);
                fadeOut.setDuration(3000);
                fadeOut.setInterpolator(new AccelerateInterpolator());
                mIvLogo.startAnimation(fadeOut);
                fadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Animation fadeIn = new AlphaAnimation(0, 1);
                        fadeIn.setDuration(3000);
                        fadeIn.setInterpolator(new AccelerateInterpolator());
                        mIvLogo.startAnimation(fadeIn);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}