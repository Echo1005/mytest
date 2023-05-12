package com.clientUI;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.client.R;
import com.clientBase.adapter.OrderAdapter;
import com.clientBase.base.BaseActivity;
import com.clientBase.config.Consts;
import com.clientBase.db.MemberUserUtils;
import com.clientBase.model.OrderBean;
import com.clientBase.model.ResponseEntry;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.http.AjaxParams;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MyOrderActivity extends BaseActivity {

	// 标题
	private TextView mTvTitle;
	// 返回
	private ImageView mIvBack;
	private TextView mIvStu;
	private ListView mListMessage;
	private List<OrderBean> list_result = new ArrayList<OrderBean>();
	private String state;
	private LinearLayout mllNomessage;
	OrderAdapter orderListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
		initWidget();
		initData();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.mIvBack:
				finish();
				break;
		}
	}

	@Override
	public void initWidget() {
		mIvStu = (TextView) findViewById(R.id.mIvStu);
		mIvStu.setText("添加");
		mIvStu.setVisibility(View.GONE);
		mllNomessage = (LinearLayout) findViewById(R.id.mllNomessage);
		mListMessage = (ListView) findViewById(R.id.mListMessage);

		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);
		state = this.getIntent().getStringExtra("state");
		mTvTitle.setText("我的订单");
		mIvBack.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(this);
		mIvStu.setOnClickListener(this);
	}

	@Override
	public void initData() {
		MessageAction(true);
		mListMessage.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

				Intent intent = new Intent(MyOrderActivity.this, OrderInforActivity.class);
				intent.putExtra("msg", (Serializable) list_result.get(pos).getShopLists());
				startActivity(intent);
			}
		});
	}

	private void MessageAction(boolean isShow) {
		AjaxParams params = new AjaxParams();
		params.put("action_flag", "listOrderPhoneMessage");
		params.put("orderId", MemberUserUtils.getUid(this));
		httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultFlag, isShow, "正在加载...");
	}

	@Override
	protected void callBackSuccess(ResponseEntry entry, int actionId) {
		super.callBackSuccess(entry, actionId);

		switch (actionId) {
			case Consts.actionId.resultFlag:
				if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {

					String jsonMsg = entry.getData().substring(1, entry.getData().length() - 1);
					if (null != jsonMsg && !TextUtils.isEmpty(jsonMsg)) {
						list_result = mGson.fromJson(entry.getData(), new TypeToken<List<OrderBean>>() {
						}.getType());
						orderListAdapter = new OrderAdapter(MyOrderActivity.this, list_result);
						mListMessage.setAdapter(orderListAdapter);
					} else {
						mllNomessage.setVisibility(View.VISIBLE);
					}
				}
				break;
			default:
				break;
		}

	}

}
