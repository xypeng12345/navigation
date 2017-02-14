package com.xyp.meyki_bear.navigationtest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ViewPager vp;
    private ViewGroupNavgation vn;
//    private String[] titles = new String[]{"全部", "未开始", "进行中"};
//    private String[] types = new String[]{"0", "1", "2"};
//    private Fragment[] fragments = new Fragment[3];
    private String[] titles = new String[]{"全部", "未开始asd4564654654654654asdasd", "进行中","进行中1","进行中2","进行中3","进行中4"};
    private String[] types = new String[]{"0", "1", "2","3","4","5","6"};
    private Fragment[] fragments = new Fragment[7];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vn= (ViewGroupNavgation) findViewById(R.id.vn);
        vp= (ViewPager) findViewById(R.id.vp_1);
        fragments[0]=new MyFramgent();
        fragments[1]=new MyFramgent();
        fragments[2]=new MyFramgent();
        fragments[3]=new MyFramgent();
        fragments[4]=new MyFramgent();
        fragments[5]=new MyFramgent();
        fragments[6]=new MyFramgent();
        FragmentPagerAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles, types);
        vp.setAdapter(adapter);
        vn.bindViewPager(vp);
        TextView tv1= (TextView) findViewById(R.id.tv1);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp.setCurrentItem(fragments.length);
            }
        });
        TextView tv2= (TextView) findViewById(R.id.tv2);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp.setCurrentItem(0);
            }
        });

    }
}
