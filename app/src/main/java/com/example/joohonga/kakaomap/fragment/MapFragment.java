package com.example.joohonga.kakaomap.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joohonga.kakaomap.Cluster;
import com.example.joohonga.kakaomap.Merchant;
import com.example.joohonga.kakaomap.MerchantsAdapter;
import com.example.joohonga.kakaomap.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapFragment extends Fragment implements MapView.POIItemEventListener,
        MapView.MapViewEventListener {

    public static final int MAX_ZOOMLEVEL = 11;
    public static final int MIN_ZOOMLEVEL = 2;

    MapView mapView;
    ViewGroup mapViewContainer;
    MerchantListFragment merchantListFragment;
    MapPoint centerMapPoint;
    double width;
    double height;

    private List<Merchant> merchantsList = new ArrayList<>();
    private List<Merchant> tempList;
    private Map<String, ArrayList<Merchant>> gatherMarkers;  //
    private Cluster tempCluster;

    Communication communication;


    String clutsteringUnit;

    Bundle bundle;

    Bitmap bm1, bm2, bm3, bm4, bm5, bm6, bm7;



    public interface Communication {
        public void listUpdate(List<Merchant> merchantList);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View marker = inflater.inflate(R.layout.custommarker, null);
        TextView marker1 = marker.findViewById(R.id.tv_marker2);
        TextView marker2 = marker.findViewById(R.id.tv_marker3);
        TextView marker3 = marker.findViewById(R.id.tv_marker4);
        TextView marker4 = marker.findViewById(R.id.tv_marker5);
        TextView marker5 = marker.findViewById(R.id.tv_marker10);
        TextView marker6 = marker.findViewById(R.id.tv_marker50);
        TextView marker7 = marker.findViewById(R.id.tv_marker100);

        bm1 = loadBitmapFromView(marker1);
        bm2 = loadBitmapFromView(marker2);
        bm3 = loadBitmapFromView(marker3);
        bm4 = loadBitmapFromView(marker4);
        bm5 = loadBitmapFromView(marker5);
        bm6 = loadBitmapFromView(marker6);
        bm7 = loadBitmapFromView(marker7);


        View v = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = new MapView(getActivity());
        mapViewContainer = (ViewGroup) v.findViewById(R.id.kakao_map);
        mapViewContainer.addView(mapView);
        mapView.setPOIItemEventListener(this);
        mapView.setMapViewEventListener(this);


        //reloadCallback.reloadCallback(mapView.getMapCenterPoint(),100);


        //테스트
        for (int i = 0; i < 20; i++) {

            Merchant merchant = new Merchant(37.5 + 0.001 * i, 127 + 0.003 * i, "hello",
                    "서울시", "용산구", "서빙고동");
            merchant.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            merchant.setCustomImageBitmap(bm1);
            merchant.setCustomImageAutoscale(false);
            merchant.setCustomImageAnchor(0.5f, 1.0f);
            merchantsList.add(merchant);

        }
        for (int i = 0; i < 20; i++) {

            Merchant merchant = new Merchant(37.6 + 0.001 * i, 127 + 0.003 * i, "hello1",
                    "서울시", "용구", "동빙고동");
            merchant.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            merchant.setCustomImageBitmap(bm1);
            merchant.setCustomImageAutoscale(false);
            merchant.setCustomImageAnchor(0.5f, 1.0f);
            merchantsList.add(merchant);

        }

        Merchant merchantArray[] = new Merchant[merchantsList.size()];
        for (int i = 0; i < merchantsList.size(); i++)
            merchantArray[i] = merchantsList.get(i);

        mapView.addPOIItems(merchantArray);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.5, 127), false);
        mapView.setZoomLevel(6, false);
        mapView.removeAllPOIItems();

        mapView.setPOIItemEventListener(this);

        Log.d("merchantListPutin1", merchantsList.get(0).getDongName());

        communication.listUpdate(merchantsList);
        //send all merchants in mapview to list


        return v;
    }


    private void clustering(MapView mapview, List<Merchant> merchants) {

        mapview.removeAllPOIItems();

        gatherMarkers = new HashMap<>();

        //Zoom Level당 Cluster
        if (mapview.getZoomLevel() >= 9) {
            clutsteringUnit = "city";
            for (Merchant i : merchants) {
                if (!gatherMarkers.containsKey(i.getCityName())) {
                    gatherMarkers.put(i.getCityName(), new ArrayList<Merchant>());
                    gatherMarkers.get(i.getCityName()).add(i);
                } else
                    gatherMarkers.get(i.getCityName()).add(i);
            }
        } else if (mapview.getZoomLevel() >= 5 && mapview.getZoomLevel() < 9) {
            clutsteringUnit = "gu";
            for (Merchant i : merchants) {
                if (!gatherMarkers.containsKey(i.getGuName())) {
                    gatherMarkers.put(i.getGuName(), new ArrayList<Merchant>());
                    gatherMarkers.get(i.getGuName()).add(i);
                } else
                    gatherMarkers.get(i.getGuName()).add(i);
            }
        } else if (mapview.getZoomLevel() < 5) {
            clutsteringUnit = "dong";
            for (Merchant i : merchants) {
                if (!gatherMarkers.containsKey(i.getDongName())) {
                    gatherMarkers.put(i.getDongName(), new ArrayList<Merchant>());
                    gatherMarkers.get(i.getDongName()).add(i);
                } else
                    gatherMarkers.get(i.getDongName()).add(i);
            }
        }

        //set ItemName to Key
        // save each key's merchants and save clustered merchants in mapview
        //only one element contained
        for (List<Merchant> i : gatherMarkers.values()) {
            Merchant temp;
            String key = "";

            for (String k : gatherMarkers.keySet()) {
                if (i.equals(gatherMarkers.get(k))) {
                    key = k;
                }
            }

            if (i.size() != 1) {
                tempCluster = new Cluster(i);
                tempCluster.addAll(i);
                temp = new Merchant(tempCluster.getAverageCenter());
                temp.setItemName(key);
                temp.setClustered(true);
                temp.setMarkerType(MapPOIItem.MarkerType.CustomImage);
                //사이즈별로 마커 이미지 변경
                if (i.size() == 2)
                    temp.setCustomImageBitmap(bm1);
                else if (i.size() == 3)
                    temp.setCustomImageBitmap(bm2);
                else if (i.size() == 4)
                    temp.setCustomImageBitmap(bm3);
                else if (i.size() >= 5 && i.size() < 10)
                    temp.setCustomImageBitmap(bm4);
                else if (i.size() >= 10 && i.size() < 50)
                    temp.setCustomImageBitmap(bm5);
                else if (i.size() >= 50 && i.size() < 100)
                    temp.setCustomImageBitmap(bm6);
                else if (i.size() >= 100)
                    temp.setCustomImageBitmap(bm7);


            } else {
                temp = i.get(0);
                temp.setMarkerType(MapPOIItem.MarkerType.BluePin);           // 기본으로 제공하는 BluePin 마커 모양.
                temp.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);    // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

            }
            mapview.addPOIItem(temp);
        }

    }


    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, 100, 100);
        v.draw(c);
        return b;
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

        merchantListFragment = new MerchantListFragment();
        if (((Merchant) mapPOIItem).isClustered()) {
            switch (clutsteringUnit) {
                case "city":
                    tempList = new ArrayList<>();
                    for (Merchant temp : merchantsList) {
                        if (temp.getCityName().equals(mapPOIItem.getItemName())) {
                            tempList.add(temp);
                        }
                    }
                    communication.listUpdate(tempList);
                    Log.d("tempList Check", tempList.get(0).getDongName());

                    break;
                case "gu":
                    tempList = new ArrayList<>();
                    for (Merchant temp : merchantsList) {
                        if (temp.getGuName().equals(mapPOIItem.getItemName())) {
                            tempList.add(temp);
                        }
                    }
                    communication.listUpdate(tempList);

                    //communication.listUpdate(tempList);
                    break;
                case "dong":
                    tempList = new ArrayList<>();
                    for (Merchant temp : merchantsList) {
                        if (temp.getDongName().equals(mapPOIItem.getItemName())) {
                            tempList.add(temp);
                        }
                    }
                    communication.listUpdate(tempList);

                    //communication.listUpdate(tempList);
                    break;
            }
            Log.d("itemselectedCheck", clutsteringUnit);
            //send selected merchants to list
        } else {
            tempList.add((Merchant) mapPOIItem);
        }


    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {
        Log.d("ZOOMLEVEL", mapView.getZoomLevel() + ",,");

        if (mapView.getZoomLevel() < MIN_ZOOMLEVEL)
            mapView.setZoomLevel(MIN_ZOOMLEVEL, false);
        if (mapView.getZoomLevel() > MAX_ZOOMLEVEL)
            mapView.setZoomLevel(MAX_ZOOMLEVEL, false);

        if (mapView.getZoomLevel() >3)
            clustering(mapView, merchantsList);
        Log.d("clustering check", clutsteringUnit);
        Log.d("clustering check", merchantsList.get(0).getItemName());

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        Log.d("pixel checkk",mapPoint.getMapPointGeoCoord().latitude+"");

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {
        if (mapView.getZoomLevel() <= MIN_ZOOMLEVEL)
            mapView.setZoomLevel(MIN_ZOOMLEVEL, false);
        else mapView.zoomIn(false);
    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
        double distance = 0;
        if (centerMapPoint == null)
            centerMapPoint = mapView.getMapCenterPoint();
        else {
            distance = distance(centerMapPoint, mapView.getMapCenterPoint());
            centerMapPoint = mapView.getMapCenterPoint();
            Log.d("location", distance + ";;");

            //0,0 is top-left
            //therefore, width and height are
            width =2 *( centerMapPoint.getMapPointGeoCoord().longitude-MapPoint.mapPointWithScreenLocation(0,0).getMapPointScreenLocation().x);
            height = 2 * (MapPoint.mapPointWithScreenLocation(0,0).getMapPointScreenLocation().y-centerMapPoint.getMapPointGeoCoord().latitude);


            

        }


    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    public double distance(MapPoint mapPoint1, MapPoint mapPoint2) {
        MapPoint.GeoCoordinate geoCoordinate1 = mapPoint1.getMapPointGeoCoord();
        MapPoint.GeoCoordinate geoCoordinate2 = mapPoint2.getMapPointGeoCoord();

        double lat1 = geoCoordinate1.latitude;
        double lon1 = geoCoordinate1.longitude;

        double lat2 = geoCoordinate2.latitude;
        double lon2 = geoCoordinate2.longitude;

        if (lat1 == lat2 || lon1 == lon2) {
            return 0;
        } else
            return distance(lat1, lon1, lat2, lon2);


    }

    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double latDistance = lat2 - lat1;
        double lonDistance = lon2 - lon1;

        double distance = Math.sqrt(Math.pow(latDistance, 2) + Math.pow(lonDistance, 2));

        return distance;

    }

    public double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        return dist;
    }

    public double distFromMapPoint(MapPoint mapPoint1, MapPoint mapPoint2) {
        double earthRadius = 6371000; //meters
        double lat1 = mapPoint1.getMapPointGeoCoord().latitude;
        double lat2 = mapPoint2.getMapPointGeoCoord().latitude;
        double lng1 = mapPoint1.getMapPointGeoCoord().longitude;
        double lng2 = mapPoint2.getMapPointGeoCoord().longitude;
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        return dist;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            communication = (Communication) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }

    }

}
