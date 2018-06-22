package com.guaju.tecentnavdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.tencentmap.mapsdk.maps.LocationSource;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.UiSettings;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

public class HelloTecnetMapActivity extends AppCompatActivity implements TencentLocationListener,LocationSource {


    private MapView mMapView;
    private TencentMap tencentMap;
    private UiSettings mapUiSettings;
    private TencentLocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_tecnet_map);
        findid();

        startLocate();

    }

    private void startLocate() {
        TencentLocationRequest request = TencentLocationRequest.create();


        locationManager = TencentLocationManager.getInstance(this);
        int error = locationManager.requestLocationUpdates(request, this);
    }

    private void findid() {
        mMapView = (MapView) findViewById(R.id.map);
        tencentMap = mMapView.getMap();
        mapUiSettings = tencentMap.getUiSettings();
        //设置放大缩小控件的显示
        mapUiSettings.setZoomControlsEnabled(true);
        //设置双指放大 双击放大等等效果
        mapUiSettings.setZoomGesturesEnabled(true);
        //设置指南针显示
        mapUiSettings.setCompassEnabled(true);

        mapUiSettings.setMyLocationButtonEnabled(true);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        mMapView.onStop();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        mMapView.onRestart();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int error, String s) {
        //是当定位位置发生变化时触发
        if (TencentLocation.ERROR_OK == error) {
            Log.e("GUAJU", "onLocationChanged: "+tencentLocation );
            // 定位成功
            Toast.makeText(this, "定位成功", Toast.LENGTH_SHORT).show();
            tencentMap.setLocationSource(this);
            showLocatePosition(tencentLocation);

            
        } else {
            // 定位失败
            Toast.makeText(this, "定位失败", Toast.LENGTH_SHORT).show();
        }
        locationManager.removeUpdates(this);
    }

    private void showLocatePosition(TencentLocation tencentLocation) {
        //标注坐标
        LatLng latLng = new LatLng(tencentLocation.getLatitude(),tencentLocation.getLongitude());
        final Marker marker = tencentMap.addMarker(new MarkerOptions().
                position(latLng).
                title("北京").
                snippet("DefaultMarker"));

        marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
        
//        tencentMap.setMapCenterAndScale()
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {
       //当设备定位信息发生变化时触发（如用户自己断开/打开gps 断开/打开 wifi 断开/打开 4g ）
    }



    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }
}
