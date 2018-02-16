package com.apppartner.androidtest.animation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
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
    protected int mInitTop;
    protected int mInitLeft;

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
        this.getWindowManager().getDefaultDisplay().getRealSize(outSize);
        mDeviceWidth = outSize.x;
        mDeviceHeight = outSize.y;

        mIvLogo = (ImageView) this.findViewById(R.id.iv_app_partner_logo);
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mIvLogo.getLayoutParams();
        mIvLogoWidth = mIvLogo.getWidth();
        mIvLogoHeight = mIvLogo.getHeight();

//        mInitTop = layoutParams.topMargin;
//        mInitLeft = layoutParams.leftMargin;

        mIvLogo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                RelativeLayout.LayoutParams lParams = null;
                int x = (int) motionEvent.getRawX();
                int y = (int) motionEvent.getRawY();
//                Log.d(TAG, "x: " + x + ", y: " + y);
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
                        lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        mInitTop = lParams.topMargin;
                        mInitLeft = lParams.leftMargin;
//                        view.setLayoutParams(lParams);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        lParams.removeRule(RelativeLayout.CENTER_IN_PARENT);

                        int viewWidth = view.getWidth();
                        int viewHeight = view.getHeight();

                        if (mInitTop > 0) {
//                            mInitTop = lParams.topMargin;
                        }
                        if (mInitLeft > 0) {
//                            mInitLeft = lParams.leftMargin;
                        }

                        lParams.topMargin = y; // (y - mInitTop);
                        lParams.leftMargin = x; // (x - mInitLeft);

                        // check device boundaries




                        lParams.bottomMargin = (viewHeight * -1);
                        lParams.rightMargin = (viewWidth * -1);
                        view.setLayoutParams(lParams);
                        break;
                }
                view.invalidate();
                return true;
            }
        });

        mBtnFade = (Button) this.findViewById(R.id.btn_face);
        mBtnFade.setOnClickListener(new View.OnClickListener() {
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
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}