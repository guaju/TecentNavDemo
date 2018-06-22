package com.guaju.tecentnavdemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;

public class UseSupportMapFragmentActivity extends FragmentActivity {

    private TencentMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_support_map_fragment);
        findid();
    }

    private void findid() {
        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment supportMapFragment= (SupportMapFragment) fm.findFragmentById(R.id.mapfragment);
         map = supportMapFragment.getMap();
    }
}
