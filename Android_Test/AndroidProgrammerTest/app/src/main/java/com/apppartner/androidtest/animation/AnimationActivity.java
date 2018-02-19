package com.apppartner.androidtest.animation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.apppartner.androidtest.BaseActivity;
import com.apppartner.androidtest.MainActivity;
import com.apppartner.androidtest.R;
import com.plattysoft.leonids.ParticleSystem;

import java.util.Random;

/**
 * Screen that displays the AppPartner icon.
 * The icon can be moved around on the screen as well as animated.
 * <p>
 * Created on Aug 28, 2016
 *
 * @author Thomas Colligan
 */
public class AnimationActivity extends BaseActivity
{
    //==============================================================================================
    // Class Properties
    //==============================================================================================
    protected static final String TAG = AnimationActivity.class.getSimpleName();
    protected static final long FADE_ANIM_DURATION_MILLIS = 5000;

    protected View mAnimContainer;
    protected Button mBtnFade;
    protected ImageView mIvLogo;
    protected int mDeviceWidth;
    protected int mDeviceHeight;
    protected int mDeltaX;
    protected int mDeltaY;
    protected int mBtnFadeTop;
    protected MediaPlayer mPlayer;
    protected int mPlayerCurrentPos;
    protected Random mRandom;
    protected ParticleSystem mParticleSystem;

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
        actionBar.setTitle(R.string.activity_animation_title);
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked

        // TODO: When the fade button is clicked, you must animate the AppPartner Icon.
        // TODO: It should fade from 100% alpha to 0% alpha, and then from 0% alpha to 100% alpha

        // TODO: The user should be able to touch and drag the AppPartner Icon around the screen.

        // TODO: Add a bonus to make yourself stick out. Music, color, fireworks, explosions!!!

        mAnimContainer = this.findViewById(R.id.anim_container);
        Point outSize = new Point();
        this.getWindowManager().getDefaultDisplay().getSize(outSize);
        mDeviceWidth = outSize.x;
        mDeviceHeight = outSize.y;
        mIvLogo = (ImageView) this.findViewById(R.id.iv_app_partner_logo);
        mIvLogo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int x = (int) motionEvent.getRawX();
                int y = (int) motionEvent.getRawY();
                int action = motionEvent.getAction();
                switch (action & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        lp.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
                        if (lp.leftMargin == 0) {
                            lp.leftMargin = view.getLeft();
                        }
                        if (lp.topMargin == 0) {
                            lp.topMargin = view.getTop();
                        }
                        // save delta (x, y) so we can use them when we drag the logo..
                        mDeltaX = (x - lp.leftMargin);
                        mDeltaY = (y - lp.topMargin);
                        view.setLayoutParams(lp);
                        break;
                    case MotionEvent.ACTION_MOVE: // dragging..
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        int viewWidth = view.getWidth();
                        int viewHeight = view.getHeight();

                        int leftMargin = (x - mDeltaX);
                        int topMargin = (y - mDeltaY);

                        // handle horizontal device boundaries..
                        if (leftMargin < 0) {
                            leftMargin = 0;
                        } else if ((leftMargin + viewWidth) > mDeviceWidth) {
                            leftMargin = (mDeviceWidth - viewWidth);
                        }

                        // handle vertical device boundaries..
                        mBtnFadeTop = mBtnFade.getTop();
                        if (topMargin < 0) {
                            topMargin = 0;
                        } else if ((topMargin + viewHeight) > mBtnFadeTop) {
                            topMargin = (mBtnFadeTop - viewHeight);
                        }

                        lParams.leftMargin = leftMargin;
                        lParams.topMargin = topMargin;

                        // handle bottom right shrinking issue..
                        lParams.bottomMargin = (viewHeight * -1);
                        lParams.rightMargin = (viewWidth * -1);

                        loadParticles(leftMargin, topMargin);
                        view.setLayoutParams(lParams);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mParticleSystem != null) {
                            mParticleSystem.stopEmitting();
                            mParticleSystem.cancel();
                        }
                        break;
                }
                mAnimContainer.invalidate();
                return true;
            }
        });

        // handle [FADE] button click's fade in / out animations..
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

        // play music..
        mPlayer = MediaPlayer.create(AnimationActivity.this, R.raw.summer);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setLooping(true);
        mPlayer.start();

        // load particles..
        mRandom = new Random();
//        loadFirstParticles();
//        loadSecondParticles();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayerCurrentPos = mPlayer.getCurrentPosition();
        mPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayer.seekTo(mPlayerCurrentPos);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPlayerCurrentPos = mPlayer.getCurrentPosition();
        mPlayer.release();
        mPlayer = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("curr_pos", mPlayerCurrentPos);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPlayerCurrentPos = savedInstanceState.getInt("curr_pos");
    }

    protected int[] getParticlesDrawableResIds() {
        return new int[]{R.drawable.firework_red0, R.drawable.firework_red1,
                R.drawable.firework_red2, R.drawable.firework_red3, R.drawable.firework_red4,
                R.drawable.firework_red5, R.drawable.firework_red6, R.drawable.firework_red7};
    }

    protected void loadParticles(int emiterX, int emiterY) {
        int numParticles = 100;
        long timeToLive = 5000;
        int[] resIds = this.getParticlesDrawableResIds();
        int i = mRandom.nextInt(resIds.length);
        int drawableResId = resIds[0];
        new ParticleSystem(AnimationActivity.this, numParticles, drawableResId, timeToLive)
                .setSpeedRange(0.2f, 0.5f)
                .setFadeOut(timeToLive)
                .emit(emiterX, emiterY, numParticles);
    }

    protected void loadFirstParticles() {
        int numParticles = 1000;
        long timeToLive = 1000;
        int[] resIds = this.getParticlesDrawableResIds();
        int i = mRandom.nextInt(resIds.length);
        int drawableResId = resIds[i];
        int emiterX = 0; // (mDeviceWidth / 4);
        int emiterY = 0; // (mDeviceHeight / 4);
        mParticleSystem = new ParticleSystem(AnimationActivity.this, numParticles, drawableResId, timeToLive);
        mParticleSystem.setSpeedRange(0.2f, 0.5f)
                .emit(emiterX, emiterY, numParticles);
    }

    protected void loadSecondParticles() {
        int numParticles = 1000;
        long timeToLive = 2000;
        int[] resIds = this.getParticlesDrawableResIds();
        int i = mRandom.nextInt(resIds.length);
        int drawableResId = resIds[i];
        int emiterX = mDeviceWidth; // ((mDeviceWidth / 4) * 3);
        int emiterY = 0; // (mDeviceHeight / 4);
        new ParticleSystem(AnimationActivity.this, numParticles, drawableResId, timeToLive)
                .setSpeedRange(0.2f, 0.5f)
//                .emit(mAnimContainer, numParticles);
                .emit(emiterX, emiterY, numParticles);
    }
}