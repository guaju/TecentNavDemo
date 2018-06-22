package com.guaju.tecentnavdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showmap(View view) {
        startActivity(new Intent(this,HelloTecnetMapActivity.class));
    }

    public void show_useSuppotMapFragment_map(View view) {
        startActivity(new Intent(this,UseSupportMapFragmentActivity.class));
    }
}
