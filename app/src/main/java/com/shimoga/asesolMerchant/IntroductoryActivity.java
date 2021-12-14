package com.shimoga.asesolMerchant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class IntroductoryActivity extends AppCompatActivity {

    ImageView logo,splashImg;
    TextView appName;
    LottieAnimationView lottieAnimationView;

    private static final int NUM_PAGES=3;
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;

    Animation anim;

    private static  int SPLASH_TIME_OUT=5000;
    SharedPreferences mSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_introductory);

        logo=findViewById(R.id.logo);
        splashImg=findViewById(R.id.img);
        appName=findViewById(R.id.app_name);
        lottieAnimationView=findViewById(R.id.lottie);

        viewPager=findViewById(R.id.pager);
        pagerAdapter=new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        //anim= AnimationUtils.loadAnimation(this,R.anim.o_b_anim);
        //viewPager.startAnimation(anim);

        splashImg.animate().translationY(-3200).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(3200).setDuration(1000).setStartDelay(4000);
        appName.animate().translationY(3200).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(3200).setDuration(1000).setStartDelay(4000);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSharedPref=getSharedPreferences("SharedPref",MODE_PRIVATE);
                boolean isFirstTime=mSharedPref.getBoolean("firstTime",true);

                if (isFirstTime){
                    SharedPreferences.Editor editor=mSharedPref.edit();
                    editor.putBoolean("firstTime",false);
                    editor.commit();
                }
                else {
                    Intent intent=new Intent(IntroductoryActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },SPLASH_TIME_OUT);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    OnBoardingFragment1 tab1=new OnBoardingFragment1();
                    return tab1;
                case 1:
                    OnBoardingFragment2 tab2=new OnBoardingFragment2();
                    return tab2;
                case 2:
                    OnBoardingFragment3 tab3=new OnBoardingFragment3();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}