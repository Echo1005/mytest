package com.clientUI;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.http.AjaxParams;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.client.R;
import com.clientBase.base.BaseActivity;
import com.clientBase.config.Consts;
import com.clientBase.db.MemberUserUtils;
import com.clientBase.model.ResponseEntry;
import com.clientBase.model.UserModel;
import com.clientBase.observable.AddressObservable;
import com.clientBase.util.ToastUtil;

public class CreatJiJianActivity extends BaseActivity {
	// 标题
	private TextView mTvTitle;
	// 返回
	private ImageView mIvBack;

	private Button mSubmit;

	private EditText sendJiJianName;
	private EditText sendJiJianAddress;
	private EditText sendJiJianPhone;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_jijian);
		initWidget();
		initData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mIvBack:
			finish();
			break;
		case R.id.mSubmit:
			addJiJian(true);
			break;

		}
	}

	@Override
	public void initWidget() {


		sendJiJianName = (EditText) findViewById(R.id.sendJiJianName);
		sendJiJianAddress = (EditText) findViewById(R.id.sendJiJianAddress);
		sendJiJianPhone = (EditText) findViewById(R.id.sendJiJianPhone);


		mSubmit = (Button) findViewById(R.id.mSubmit);
		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);
		mTvTitle.setText("添加地址信息");
		mIvBack.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(this);
		mSubmit.setOnClickListener(this);


	}

	UserModel userModel;
	List<String> listData = new ArrayList<String>();

	@Override
	public void initData() {


	}

	private void addJiJian(boolean isShow) {

		AjaxParams params = new AjaxParams();
		params.put("action_flag", "addJiJian");
		params.put("jijianName", sendJiJianName.getText().toString());
		params.put("jijianAddresse", sendJiJianAddress.getText().toString());
		params.put("jijianPhone", sendJiJianPhone.getText().toString());
		params.put("jijianUserId", MemberUserUtils.getUid(this));
		httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultCode, isShow, "正在上传...");

	}

	@Override
	protected void callBackSuccess(ResponseEntry entry, int actionId) {
		super.callBackSuccess(entry, actionId);
		AddressObservable.getInstance().notifyStepChange("ok");
		ToastUtil.show(CreatJiJianActivity.this, entry.getRepMsg());
		new Handler().postDelayed(new Runnable() {
			//
			@Override
			public void run() {
				finish();
			}
		}, 2000);
	}

	@Override
	protected void callBackAllFailure(String strMsg, int actionId) {
		super.callBackAllFailure(strMsg, actionId);
		ToastUtil.show(CreatJiJianActivity.this, strMsg);

	}

}
