package com.example.joohonga.kakaomap;



import android.os.Parcel;
import android.os.Parcelable;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;

public class Merchant extends MapPOIItem implements Parcelable {

    private boolean clustered =false;
    private String cityName;
    private String guName;
    private String dongName;
    private String price;
    private String description;
    private MapPoint mapPoint;



    public Merchant(MapPoint mapPoint)
    {
        this.setMapPoint(mapPoint);
    }
    public Merchant(Parcel in){
        readFromParcel(in);
    }

    public Merchant(double lat, double lon, String cityName, String guName, String dongName, String price, String description)
    {
        this.mapPoint=MapPoint.mapPointWithGeoCoord(lat,lon);
        this.cityName = cityName;
        this.guName = guName;
        this.dongName = dongName;
        this.price = price;
        this.description = description;
    }
    public Merchant(double lat, double lon, String Name, String cityName, String guName, String dongName){

        this.setMapPoint(MapPoint.mapPointWithGeoCoord(lat,lon));
        this.setItemName(Name);
        this.cityName = cityName;
        this.guName = guName;
        this.dongName = dongName;
    }


    public static final Creator<Merchant> CREATOR = new Creator<Merchant>() {
        @Override
        public Merchant createFromParcel(Parcel in) {
            return new Merchant(in);
        }

        @Override
        public Merchant[] newArray(int size) {
            return new Merchant[size];
        }
    };

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setGuName(String guName) {
        this.guName = guName;
    }

    public String getGuName() {
        return guName;
    }

    public void setDongName(String dongName) {
        this.dongName = dongName;
    }

    public String getDongName() {
        return dongName;
    }


    public void setClustered(boolean clustered) {
        this.clustered = clustered;
    }

    public boolean isClustered() {
        return this.clustered;
    }

    public String getPrice() {return price;}

    public void setPrice(String price) {this.price = price;}

    public void setDescription(String description) {this.description = description;}

    public String getDescription() {return description;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(isClustered()? 1:0);
        dest.writeValue(mapPoint);
        dest.writeString(cityName);
        dest.writeString(guName);
        dest.writeString(dongName);
        dest.writeString(price);
        dest.writeString(description);
    }
    public void readFromParcel(Parcel in){

        if (in.readInt()==1){
            clustered = true;
        }
        else
            clustered =false;
        cityName = in.readString();
        guName= in.readString();
        dongName= in.readString();
        price= in.readString();
        description= in.readString();
        mapPoint= (MapPoint) in.readValue(ClassLoader.getSystemClassLoader());
    }
}
