package com.example.joohonga.kakaomap.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.joohonga.kakaomap.Merchant;
import com.example.joohonga.kakaomap.MerchantsAdapter;
import com.example.joohonga.kakaomap.R;

import net.daum.mf.map.api.MapPOIItem;

import java.util.ArrayList;
import java.util.List;



/**
 * Bottom of Map Implement activity
 * Slidable Merchant list menu
 */
public class MerchantListFragment extends Fragment {


    View refView;
    List<Merchant> merchantsList;
    RecyclerView recyclerView;
    MerchantsAdapter mAdapter;
    Button mButton;

    public MerchantListFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public MerchantListFragment(List<Merchant> merchantsList){
        this.merchantsList = merchantsList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        refView = inflater.inflate(R.layout.fragment_merchant_list, container, false);
        recyclerView = refView.findViewById(R.id.merchants_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        Log.d("merchantListPutin55",mAdapter.mList.get(0).getDongName());

        return refView;
    }



    public void recieveList (List<Merchant> merchantsList){
        mAdapter = new MerchantsAdapter(getActivity(), merchantsList);
        Log.d("merchantListPutin3",merchantsList.get(0).getDongName());
        mAdapter.checking();

    }


}



