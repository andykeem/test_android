package com.apppartner.androidtest.animation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
    protected static final String TAG = AnimationActivity.class.getSimpleName();
    protected static final long FADE_ANIM_DURATION_MILLIS = 3000;
    protected Button mBtnFade;
    protected ImageView mIvLogo;
    protected int mIvLogoWidth;
    protected int mIvLogoHeight;
    protected int mDeviceWidth;
    protected int mDeviceHeight;

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

        Point outSize = new Point();
        this.getWindowManager().getDefaultDisplay().getSize(outSize);
        mDeviceWidth = outSize.x;
        mDeviceHeight = outSize.y;

        Log.d(TAG, "w: " + mDeviceWidth + ", h: " + mDeviceHeight);

        mIvLogo = (ImageView) this.findViewById(R.id.iv_app_partner_logo);
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mIvLogo.getLayoutParams();
        mIvLogoWidth = mIvLogo.getWidth();
        mIvLogoHeight = mIvLogo.getHeight();
        mIvLogo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                Log.d(TAG,"view w: " + view.getWidth() + ", view h: " + view.getHeight());
                int x = (int) motionEvent.getRawX();
                int y = (int) motionEvent.getRawY();
                Log.d(TAG, "x: " + x + ", y: " + y);
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int maxX = x + mIvLogoWidth;
                        int maxY = y + mIvLogoHeight;
                        if (maxX > mDeviceWidth) {
                            x = mDeviceWidth - mIvLogoWidth;
                        } else if (x < 0) {
                            x = 0;
                        }
                        if (maxY > mDeviceHeight) {
                            y = mDeviceHeight - mIvLogoHeight;
                        } else if (y < 0) {
                            y = 0;
                        }
                        Log.d(TAG, "x2: " + x + ", y2: " + y);
                        Log.d(TAG,"view w: " + view.getWidth() + ", view h: " + view.getHeight());
                        layoutParams.leftMargin = x;
                        layoutParams.topMargin = y;
                        break;
                }
                view.setLayoutParams(layoutParams);
                view.invalidate();
                return true;
            }
        });

        mBtnFade = (Button) this.findViewById(R.id.btn_face);
        /*mBtnFade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation fadeOut = new AlphaAnimation(1, 0);
                fadeOut.setDuration(FADE_ANIM_DURATION_MILLIS);
                fadeOut.setInterpolator(new AccelerateInterpolator());
                mIvLogo.startAnimation(fadeOut);
                fadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Animation fadeIn = new AlphaAnimation(0, 1);
                        fadeIn.setDuration(FADE_ANIM_DURATION_MILLIS);
                        fadeIn.setInterpolator(new AccelerateInterpolator());
                        mIvLogo.startAnimation(fadeIn);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });*/
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}