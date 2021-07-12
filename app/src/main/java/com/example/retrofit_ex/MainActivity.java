package com.example.retrofit_ex;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final int Fragment_1 = 1;
    private final int Fragment_2 = 2;
    private final int Fragment_3 = 3;
    private boolean PERMISSIONS_REQUEST_RESULT = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager vp=findViewById(R.id.viewpager);
        VPAdapter adapter=new VPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        // 연동
        TabLayout tab=findViewById(R.id.tab);
        tab.setupWithViewPager(vp);

        ArrayList<Integer> images=new ArrayList<>();
        images.add(R.drawable.profile);
        images.add(R.drawable.gallery);
        images.add(R.drawable.music);

        for(int i=0;i<3;i++) tab.getTabAt(i).setIcon(images.get(i));
    }

}