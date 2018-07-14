package com.example.joohonga.kakaomap;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.joohonga.kakaomap.R;
import com.example.joohonga.kakaomap.fragment.MapFragment;
import com.example.joohonga.kakaomap.fragment.MerchantListFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;



public class MainActivity extends AppCompatActivity implements MapFragment.Communication{

    MerchantListFragment merchantListFragment;
    MapFragment mapFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        mapFragment = new MapFragment();
        merchantListFragment = new MerchantListFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.map_container, mapFragment);
        fragmentTransaction.add(R.id.merchant_list, merchantListFragment);
        fragmentTransaction.commit();


    }



    @Override
    public void listUpdate(List<Merchant> merchantList) {
        Log.d("merchantListPutin2",merchantList.get(0).getDongName());
        merchantListFragment.recieveList(merchantList);

    }
}
