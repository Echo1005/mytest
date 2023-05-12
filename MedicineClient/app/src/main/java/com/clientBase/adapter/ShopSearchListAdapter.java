package com.clientBase.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.client.R;
import com.clientBase.app.PonyApplication;
import com.clientBase.config.Consts;
import com.clientBase.model.ShopModel;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class ShopSearchListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<ShopModel> list_result;
    private int posIndex;
    private Context mContext;

    public ShopSearchListAdapter(Context context, List<ShopModel> list_result) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        this.list_result = list_result;
    }

    @Override
    public int getCount() {
        return list_result.size();
    }

    @Override
    public Object getItem(int position) {
        return list_result.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.search_item, null);
            holder = new ViewHolder();
            holder.mTvTitle = (TextView) convertView.findViewById(R.id.mTvTitle);
            holder.mTvMoney = (TextView) convertView.findViewById(R.id.mTvMoney);
            holder.mtvTime = (TextView) convertView.findViewById(R.id.mtvTime);
            holder.mivShop = (ImageView) convertView.findViewById(R.id.mivShop);
            holder.mtvJuli = (TextView) convertView.findViewById(R.id.mtvJuli);
            holder.mtvaddress = (TextView) convertView.findViewById(R.id.mtvaddress);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//
//			String[] addressInfor = list_result.get(position).getShopAddress().split(",");
//			LatLng latLngStart = new LatLng(PonyApplication.getInstance().mAppAmapLocation.getLatitude(),PonyApplication.getInstance().mAppAmapLocation.getLongitude());
//			LatLng latLngEnd = new LatLng(Double.valueOf(addressInfor[1]),Double.valueOf(addressInfor[0]));
//
//
//
//			float distance = AMapUtils.calculateLineDistance(latLngStart,latLngEnd);
//			double  distanceInfor= distance*0.001;
//			DecimalFormat data=new DecimalFormat("#.0");
        holder.mtvaddress.setText(list_result.get(position).getShopCity());
        holder.mtvJuli.setText(list_result.get(position).getShopAddress() + "m");
        holder.mTvTitle.setText(list_result.get(position).getShopName() + "(" + list_result.get(position).getAvgScore() + "分)");
        holder.mTvMoney.setText("价格：" + list_result.get(position).getShopMoney() + "元");

        holder.mtvTime.setText("销量：" + list_result.get(position).getOrderNumber() + "件");

        if (!TextUtils.isEmpty(list_result.get(position).getShopImg())) {
            Picasso.with(mContext).load(Consts.URL_IMAGE + list_result.get(position).getShopImg())
                    .placeholder(R.drawable.default_drawable_show_pictrue).into(holder.mivShop);
        }


        return convertView;

    }

    private class ViewHolder {
        private TextView mTvTitle, mtvJuli, mtvaddress;
        private TextView mTvMoney;
        private TextView mtvTime;
        private ImageView mivShop;
    }

    public void setPos(int pos) {
        posIndex = pos;
    }

}
