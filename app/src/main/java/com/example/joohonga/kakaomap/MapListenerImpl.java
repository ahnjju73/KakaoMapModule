package com.example.joohonga.kakaomap;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

/**
 * Implement MapView EventListeners
 */
public class MapListenerImpl implements MapView.MapViewEventListener{



    @Override
    public void onMapViewInitialized(MapView mapView) {
        /*//사용 가능한 상태가 되었을 시
        Log.d("daumMap", "시작");
        mapView.setZoomLevel(1, true);
        //다음맵 트래킹설정
        if (mapView.getCurrentLocationTrackingMode() == MapView.CurrentLocationTrackingMode.TrackingModeOff) {
            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading); //트래킹 설정
            Log.d("daumMAp", "트래킹설정완료");
        }*/
    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {//지도 중심좌표 이동

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {//확대 축소 레벨변경
    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {//지도위를 터치
        mapView.refreshMapTiles();
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {//지도위 한점을 더블 터치

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {//롱 프레스

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {//지도 드레그

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {//드레그 종료

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {//지도 이동 종료

    }



}
