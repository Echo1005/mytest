package com.clientBase.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.client.R;
import com.clientBase.config.Consts;
import com.clientBase.listener.CarListner;
import com.clientBase.model.CarModel;
import com.clientBase.model.ShopModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarListAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<CarModel> list_result;
	private int posIndex;
	private Context mContext;
	private CarListner mcarListner;
	public CarListAdapter(Context context, List<CarModel> list_result, CarListner carListner) {
		mContext = context;
		inflater = LayoutInflater.from(context);
		this.list_result = list_result;
		this.mcarListner =carListner;
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
			convertView = inflater.inflate(R.layout.car_item, null);
			holder = new ViewHolder();
			holder.mTvTitle = (TextView) convertView.findViewById(R.id.mTvTitle);
			holder.mTvMoney = (TextView) convertView.findViewById(R.id.mTvMoney);
			holder.mtvTime = (TextView) convertView.findViewById(R.id.mtvTime);
			holder.shopNumber = (TextView) convertView.findViewById(R.id.shopNumber);
			holder.mivShop = (ImageView) convertView.findViewById(R.id.mivShop);

			holder.ivjian = (ImageView) convertView.findViewById(R.id.ivjian);
			holder.ivadd = (ImageView) convertView.findViewById(R.id.ivadd);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}


		if(list_result.get(position).isChoice()){
			holder.mTvTitle.setText(list_result.get(position).getShopName()+"(已选择)");
		}else{
			holder.mTvTitle.setText(list_result.get(position).getShopName());
		}

			holder.mTvMoney.setText("价格：" + list_result.get(position).getShopMoney() + "元");

			holder.mtvTime.setText("发布时间：" + list_result.get(position).getShopCreatime());

			if (!TextUtils.isEmpty(list_result.get(position).getShopImg())) {
				Picasso.with(mContext).load(Consts.URL_IMAGE + list_result.get(position).getShopImg())
						.placeholder(R.drawable.default_drawable_show_pictrue).into(holder.mivShop);
			}
			holder.shopNumber.setText(list_result.get(position).getCarNumber()+"");

			holder.ivjian.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {

					if(list_result.get(position).getCarNumber()>1){
						mcarListner.setJian(position,list_result.get(position));
					}

				}
			});

			holder.ivadd.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					mcarListner.setJia(position,list_result.get(position));
				}
			});


		return convertView;

	}

	private class ViewHolder {
		private TextView mTvTitle,shopNumber;
		private TextView mTvMoney;
		private TextView mtvTime;
		private ImageView mivShop,ivjian,ivadd;
	}

	public void setPos(int pos) {
		posIndex = pos;
	}

}
