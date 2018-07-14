package com.example.joohonga.kakaomap;

import net.daum.mf.map.api.MapPoint;

import java.util.List;

import static net.daum.mf.map.api.MapPoint.mapPointWithGeoCoord;

public class Cluster {


    private List<Merchant> cluster;
    private int cnt;

    public Cluster(List<Merchant> testMerchants) {
        cluster = testMerchants;
    }


    //Mid Point of  marks in a cluster
    public MapPoint getAverageCenter() {
        double latitude = 0;
        double longitude = 0;
        MapPoint avgPoint;

        for (Merchant i : cluster) {
            latitude += i.getMapPoint().getMapPointGeoCoord().latitude;
            longitude += i.getMapPoint().getMapPointGeoCoord().longitude;
        }
        latitude = latitude / cluster.size();
        longitude = longitude / cluster.size();
        avgPoint = mapPointWithGeoCoord(latitude, longitude);
        return avgPoint;
    }


    public void add(Merchant Item) {
        cluster.add(Item);
    }

    public void addAll(List<Merchant> poiItems) {
        cluster = poiItems;
    }

    public int elemCount() {
        return cluster.size();
    }


}
