package com.example.joohonga.kakaomap;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class MerchantsAdapter extends RecyclerView.Adapter<MerchantsAdapter.ViewHolder> {

    public List<Merchant> mList;
    Context mContext;


    public MerchantsAdapter(Context mContext, List<Merchant> mList){
        this.mContext = mContext;
        this.mList = mList;
    }


    public static class ViewHolder  extends RecyclerView.ViewHolder {
        public ImageView ivMerchant;
        public TextView tvName;
        public TextView tvDes;
        public TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            ivMerchant = itemView.findViewById(R.id.img_view);
            tvName = itemView.findViewById(R.id.merchant_name);
            tvDes = itemView.findViewById(R.id.merchant_des);
            tvPrice = itemView.findViewById(R.id.merchant_price);

        }

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.merchant_info, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvName.setText(mList.get(position).getItemName());
        holder.tvDes.setText(mList.get(position).getDescription());
        holder.tvPrice.setText(mList.get(position).getPrice());
        Log.d("MerchantListCheck",mList.get(position).getDongName());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void checking(){
        Log.d("mList check",mList.get(0).getDongName());
    }


}
