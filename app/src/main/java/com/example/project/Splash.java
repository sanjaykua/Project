package com.example.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.project.databinding.ActivitySplashBinding;

public class Splash extends AppCompatActivity {

    ActivitySplashBinding binding;
    String s1,s2;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(Splash.this,R.layout.activity_splash);

        imageView= findViewById(R.id.splash);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.transition);
        imageView.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
                s1=sharedPreferences.getString("Mobile",null);
                s2=sharedPreferences.getString("Email",null);

                if (s1==null && s2==null){
                    Intent intent=new Intent(Splash.this,Login.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent= new Intent(Splash.this,Login.class);
                    startActivity(intent);
                    finish();
                }
            }
        },5000);
    }
}
