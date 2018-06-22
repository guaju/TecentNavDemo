package com.guaju.tecentnavdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.LocationSource;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.UiSettings;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
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

         //给marker添加点击事件

        tencentMap.setOnMarkerClickListener(new TencentMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.isInfoWindowShown()){
                    marker.hideInfoWindow();
                }else{
                    marker.showInfoWindow();
                }

                return true;
            }
        });

        //设置点击地图 隐藏infowindow方法
        tencentMap.setOnTapMapViewInfoWindowHidden(true);

        tencentMap.setOnInfoWindowClickListener(new TencentMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(HelloTecnetMapActivity.this, marker.getTitle()+marker.getSnippet(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInfoWindowClickLocation(int i, int i1, int i2, int i3) {

            }
        });

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
        //是当定位位置发生变化时触发  ：
        if (TencentLocation.ERROR_OK == error) {
            //定位成功时 
            Log.e("GUAJU", "onLocationChanged: "+tencentLocation );
            // 定位成功
            Toast.makeText(this, "定位成功", Toast.LENGTH_SHORT).show();
            tencentMap.setLocationSource(this);
            //展示定位位置 并且显示到屏幕中心位置
            showLocatePosition(tencentLocation);

            
        } else {
            // 定位失败
            Toast.makeText(this, "定位失败", Toast.LENGTH_SHORT).show();
        }
        locationManager.removeUpdates(this);
    }

    private void addInfoWindow(final Marker marker) {
        TencentMap.InfoWindowAdapter infoWindowAdapter = new TencentMap.InfoWindowAdapter() {

            private TextView tv_des;
            TextView tvTitle;

            //返回View为信息窗自定义样式，返回null时为默认信息窗样式
            @Override
            public View getInfoWindow(final Marker arg0) {
                // TODO Auto-generated method stub
                if (arg0.equals(marker)) {
                    LinearLayout custInfowindow = (LinearLayout) View.inflate(
                            HelloTecnetMapActivity.this, R.layout.infowindow_tecent, null);
                    tvTitle = (TextView)custInfowindow.findViewById(R.id.tv_title);
                    tv_des = (TextView)custInfowindow.findViewById(R.id.tv_des);
                    tvTitle.setText(arg0.getTitle());
                    tv_des.setText(arg0.getSnippet());

                    return custInfowindow;
                }
                return null;
            }

            //返回View为信息窗内容自定义样式，返回null时为默认信息窗样式
            @Override
            public View getInfoContents(Marker arg0) {
                // TODO Auto-generated method stub
                return null;
            }
        };

        //设置信息窗适配器
        tencentMap.setInfoWindowAdapter(infoWindowAdapter);


    }

    private void showLocatePosition(TencentLocation tencentLocation) {
        //标注坐标
        LatLng latLng = new LatLng(tencentLocation.getLatitude(),tencentLocation.getLongitude());
        final Marker marker = tencentMap.addMarker(new MarkerOptions().
                position(latLng).
                title(tencentLocation.getName()).
                snippet(tencentLocation.getAddress()));

        marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
        
        //显示到中心位置  参数2 是地图的缩放级别 ： 最小是3  最大是19
        /*
        参数1  中心点的经纬度
        参数2  地图的缩放级别    3-19
        参数3  旋转角的度数
        参数4  倾斜角的度数
         */
        CameraPosition cameraPosition = new CameraPosition(new LatLng(tencentLocation.getLatitude(), tencentLocation.getLongitude()), 16.0f, 0.0f, 0.0f);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        tencentMap.moveCamera(cameraUpdate);
        //定位成功之后，添加infowindow
        addInfoWindow(marker);

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
