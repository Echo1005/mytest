package com.clientUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import net.tsz.afinal.http.AjaxParams;
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
import com.clientBase.adapter.SendAddressAdapter;
import com.clientBase.base.BaseActivity;
import com.clientBase.config.Consts;
import com.clientBase.db.MemberUserUtils;
import com.clientBase.model.JiJianModel;
import com.clientBase.model.ResponseEntry;
import com.clientBase.observable.AddressObservable;
import com.google.gson.reflect.TypeToken;


public class AddressListActivity extends BaseActivity implements Observer{


	private TextView mTvTitle;

	private ImageView mIvBack;
	private TextView mIvStu;
	private ListView mListMessage;
	private List<JiJianModel> list_result = new ArrayList<JiJianModel>();
	private String state;
	private LinearLayout mllNomessage;
	private ImageView mivCreateMessage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_im_create);
		initWidget();
		initData();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.mIvBack:
				finish();
				break;
			case R.id.mivCreateMessage:
				Intent intent = new Intent(this, CreatJiJianActivity.class);
				startActivity(intent);
				break;
		}
	}

	@Override
	public void initWidget() {

		mivCreateMessage = (ImageView) findViewById(R.id.mivCreateMessage);
		mivCreateMessage.setOnClickListener(this);
		mIvStu = (TextView) findViewById(R.id.mIvStu);
		mIvStu.setVisibility(View.GONE);
		mllNomessage = (LinearLayout) findViewById(R.id.mllNomessage);
		mListMessage = (ListView) findViewById(R.id.mListMessage);

		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);
		mTvTitle.setText("收货地址管理");
		mIvBack.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(this);
		mIvStu.setOnClickListener(this);
	}

	@Override
	public void initData() {
		MessageAction(true);
		mListMessage.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			}
		});
	}

	private void MessageAction(boolean isShow) {
		AjaxParams params = new AjaxParams();
		params.put("action_flag", "listJiJianPCMessage");
		params.put("jijianUserId", MemberUserUtils.getUid(this));
		httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultFlag, isShow, "姝ｅ湪鍔犺浇...");
	}

	SendAddressAdapter listAdapter;
	@Override
	protected void callBackSuccess(ResponseEntry entry, int actionId) {
		super.callBackSuccess(entry, actionId);

		switch (actionId) {
			case Consts.actionId.resultFlag:
				if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {

					String jsonMsg = entry.getData().substring(1, entry.getData().length() - 1);
					if (null != jsonMsg && !TextUtils.isEmpty(jsonMsg)) {
						list_result.clear();
						list_result = mGson.fromJson(entry.getData(), new TypeToken<List<JiJianModel>>() {
						}.getType());
						listAdapter= new SendAddressAdapter(AddressListActivity.this, list_result);
						mListMessage.setAdapter(listAdapter);
						mllNomessage.setVisibility(View.GONE);
					} else {
						mllNomessage.setVisibility(View.VISIBLE);
					}
				}
				break;


		}

	}

	@Override
	public void onResume() {
		super.onResume();
		AddressObservable.getInstance().addObserver(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		AddressObservable.getInstance().deleteObserver(this);

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		MessageAction(false);
	}

}
