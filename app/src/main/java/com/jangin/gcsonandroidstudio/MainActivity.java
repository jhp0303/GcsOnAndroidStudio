package com.jangin.gcsonandroidstudio;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.PolylineOverlay;

import java.util.Arrays;

public class MainActivity extends FragmentActivity
        implements OnMapReadyCallback {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

        LatLng coord = new LatLng(35.945282, 126.68215299999997);

        Toast.makeText(this,
                "위도: " + coord.latitude + ", 경도: " + coord.longitude,
                Toast.LENGTH_SHORT).show();


    }

    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        naverMap.setMapType(NaverMap.MapType.Terrain);

        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(35.945282, 126.68215299999997))
                .animate(CameraAnimation.Fly,5000);
        CameraUpdate camerazoom = CameraUpdate.zoomTo(15);
        naverMap.moveCamera(camerazoom);
        naverMap.moveCamera(cameraUpdate);

        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);

        Marker KSUmarker = new Marker();
        KSUmarker.setPosition(new LatLng(35.945282, 126.68215299999997));
        KSUmarker.setMap(naverMap);
        KSUmarker.setWidth(Marker.SIZE_AUTO);
        KSUmarker.setHeight(Marker.SIZE_AUTO);
        InfoWindow KSUinfoWindow = new InfoWindow();
        KSUinfoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return "군산대학교";
            }
        });
        KSUinfoWindow.open(KSUmarker);

        Marker KSCHmarker = new Marker();
        KSCHmarker.setPosition(new LatLng(35.9675829, 126.7368305));
        KSCHmarker.setMap(naverMap);
        KSCHmarker.setWidth(Marker.SIZE_AUTO);
        KSCHmarker.setHeight(Marker.SIZE_AUTO);
        InfoWindow KSCHinfoWindow = new InfoWindow();
        KSCHinfoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return "군산시청";
            }
        });
        KSCHinfoWindow.open(KSCHmarker);

        Marker WKUmarker = new Marker();
        WKUmarker.setPosition(new LatLng(35.9703446,126.95475629999999));
        WKUmarker.setMap(naverMap);
        WKUmarker.setWidth(Marker.SIZE_AUTO);
        WKUmarker.setHeight(Marker.SIZE_AUTO);
        InfoWindow WKUinfoWindow = new InfoWindow();
        WKUinfoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return "원광대학교";
            }
        });
        WKUinfoWindow.open(WKUmarker);

        Marker JBNUmarker = new Marker();
        JBNUmarker.setPosition(new LatLng(35.8441821,127.12927769999999));
        JBNUmarker.setMap(naverMap);
        JBNUmarker.setWidth(Marker.SIZE_AUTO);
        JBNUmarker.setHeight(Marker.SIZE_AUTO);
        InfoWindow JBNUinfoWindow = new InfoWindow();
        JBNUinfoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return "전북대학교";
            }
        });
        JBNUinfoWindow.open(JBNUmarker);


        PolylineOverlay polyline = new PolylineOverlay();
        polyline.setCoords(Arrays.asList(
                new LatLng(35.945282, 126.68215299999997),
                new LatLng(35.9675829, 126.7368305),
                new LatLng(35.9703446,126.95475629999999),
                new LatLng(35.8441821,127.12927769999999)
        ));
        polyline.setMap(naverMap);
        polyline.setColor(Color.CYAN);
    }
}