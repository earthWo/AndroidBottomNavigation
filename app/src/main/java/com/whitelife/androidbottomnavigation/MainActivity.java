package com.whitelife.androidbottomnavigation;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.whitelife.library.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView= (BottomNavigationView) findViewById(R.id.bottom_navigation);

        int color[]={Color.RED,Color.BLUE,Color.GREEN};
        try {
            bottomNavigationView.setColors(color);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
