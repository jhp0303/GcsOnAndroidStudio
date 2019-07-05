package com.jangin.gcsonandroidstudio;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.PolylineOverlay;

import java.util.ArrayList;
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

        //MapType Spinner (same android:entries="@array/SelectMapType" in activity_main.xml)
        //Spinner SelectMapType = (Spinner)findViewById(R.id.selectMapType);
        //ArrayAdapter TypeAdapter = ArrayAdapter.createFromResource(this, R.array.SelectMapType, android.R.layout.simple_spinner_item);
        //TypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //SelectMapType.setAdapter(TypeAdapter);





    }



    @UiThread
    @Override
    public void onMapReady(@NonNull final NaverMap naverMap) {

        Spinner selectMapType = (Spinner)findViewById(R.id.selectMapType);

        selectMapType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    naverMap.setMapType(NaverMap.MapType.Basic);
                }
                else if(i == 1){
                    naverMap.setMapType(NaverMap.MapType.Satellite);
                }
                else if(i == 2){
                    naverMap.setMapType(NaverMap.MapType.Hybrid);
                }
                else{
                    naverMap.setMapType(NaverMap.MapType.Terrain);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayList<LatLng> markersLatLng = new ArrayList<>();
        ArrayList<Marker> markersArray = new ArrayList<>();
        ArrayList<InfoWindow> infoWindowsArray = new ArrayList<>();

        markersLatLng.add(new LatLng(35.945282, 126.68215299999997));    //KSUmarker
        markersLatLng.add(new LatLng(35.9675829, 126.7368305));          //KSCHmarker
        markersLatLng.add(new LatLng(35.9703446, 126.95475629999999));   //WKUmarker
        markersLatLng.add(new LatLng(35.8441821,127.12927769999999));    //JBNUmarker

        CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(markersLatLng.get(0), 15)
                .animate(CameraAnimation.Fly,5000);
        naverMap.moveCamera(cameraUpdate);

        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);
        final LatLngBounds bounds = new LatLngBounds.Builder()
                .include(markersLatLng.get(0))
                .include(markersLatLng.get(1))
                .include(markersLatLng.get(2))
                .include(markersLatLng.get(3))
                .build();
        CameraUpdate cameraCenter = CameraUpdate.fitBounds(bounds, 100);
        naverMap.moveCamera(cameraCenter);

        for (int i=0; i < markersLatLng.size(); i++) {
            Marker marker = new Marker();
            marker.setPosition(markersLatLng.get(i));
            marker.setMap(naverMap);
            marker.setWidth(Marker.SIZE_AUTO);
            marker.setHeight(Marker.SIZE_AUTO);
            markersArray.add(marker);

            InfoWindow infoWindow = new InfoWindow();
            infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
                @NonNull
                @Override
                public CharSequence getText(@NonNull InfoWindow infoWindow) {
                    return "정보창";
                }
            });
            infoWindow.open(marker);
            infoWindowsArray.add(infoWindow);
        }

        PolylineOverlay polyline = new PolylineOverlay();
        polyline.setCoords(Arrays.asList(
                markersLatLng.get(0),
                markersLatLng.get(1),
                markersLatLng.get(2),
                markersLatLng.get(3)
        ));
        polyline.setMap(naverMap);
        polyline.setColor(Color.CYAN);

        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (markersArray.get(0).getMap() == null) {
                    // 현재 마커가 지도에 없을 경우 마커를 찍음
                    for (int i = 0; i < markersArray.size(); i++) {
                        markersArray.get(i).setMap(naverMap);
                        infoWindowsArray.get(i).open(markersArray.get(i));
                    }
                    polyline.setMap(naverMap);

                } else {
                    // 이미 마커가 지도에 있을 경우 마커를 닫음

                    //for (int i = 0; i < markersArray.size(); i++) {
                    //    markersArray.get(i).setMap(null);
                    //}
                    for (Marker i : markersArray) { //위의 for문과 동일
                        i.setMap(null);
                    }
                    polyline.setMap(null);
                }
            }
        };

        Button markerBtn = (Button) findViewById(R.id.button);
        markerBtn.setOnClickListener(listener);

        naverMap.setOnMapLongClickListener((point, coord) ->
                Toast.makeText(this, coord.latitude + ", " + coord.longitude,
                        Toast.LENGTH_SHORT).show());
    }
}