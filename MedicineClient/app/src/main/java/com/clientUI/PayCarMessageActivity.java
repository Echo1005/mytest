package com.clientUI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.client.R;
import com.clientBase.adapter.CarListAdapter;
import com.clientBase.adapter.CarOrderAdapter;
import com.clientBase.base.BaseActivity;
import com.clientBase.config.Consts;
import com.clientBase.db.MemberUserUtils;
import com.clientBase.model.CarModel;
import com.clientBase.model.JiJianModel;
import com.clientBase.model.ResponseEntry;
import com.clientBase.model.ShopBean;
import com.clientBase.model.ShopModel;
import com.clientBase.model.UserModel;
import com.clientBase.observable.CarObservable;
import com.clientBase.util.CustomToast;
import com.clientBase.util.ToastUtil;
import com.clientBase.view.DialogMsg;
import com.clientBase.view.ListviewForScrollView;

import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;


public class PayCarMessageActivity extends BaseActivity {

	// 标题
	private TextView mTvTitle;
	// 返回
	private ImageView mIvBack;
	private Button mPay;

	private List<CarModel> listChoice = new ArrayList<CarModel>();
	private ListviewForScrollView mListMessage;

	private TextView orderMoney,mtvAddress;

	EditText mtvremark;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paycar_message);
		initWidget();
		initData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.mIvBack:
				finish();
				break;
			case R.id.mPay:
				dialogMsg.Show();
				break;
			case R.id.mtvAddress:
				Intent intent = new Intent(this, AddressChoiceActivity.class);
				this.startActivityForResult(intent, 0);
				break;
		}
	}
	DialogMsg dialogMsg;
	@Override
	public void initWidget() {


		dialogMsg = new DialogMsg(this);
		dialogMsg.Set_Msg("确定支付吗？");
		dialogMsg.submit_ok().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialogMsg.Close();
				addOrder(true);
			}
		});

		dialogMsg.submit_no().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialogMsg.Close();
			}
		});
		mtvremark = (EditText) findViewById(R.id.mtvremark);
		orderMoney = (TextView) findViewById(R.id.orderMoney);
		mtvAddress = (TextView) findViewById(R.id.mtvAddress);
		mtvAddress.setOnClickListener(this);
		mListMessage = (ListviewForScrollView) findViewById(R.id.mListMessage);
		mPay = (Button) findViewById(R.id.mPay);
		mIvBack = (ImageView) findViewById(R.id.mIvBack);
		mTvTitle = (TextView) findViewById(R.id.mTvTitle);
		mTvTitle.setText("支付确认");
		mIvBack.setVisibility(View.VISIBLE);
		mIvBack.setOnClickListener(this);
		mPay.setOnClickListener(this);
	}
	String payMoney;
	@Override
	public void initData() {
		listChoice = (List<CarModel>) this.getIntent().getSerializableExtra("msg");
		payMoney = this.getIntent().getStringExtra("payMoney");
		CarOrderAdapter carListAdapter = new CarOrderAdapter(this, listChoice);
		mListMessage.setAdapter(carListAdapter);

		orderMoney.setText(payMoney+"元");

	}


	private void addOrder(boolean isShow) {

		String ids="";
		for(int i=0;i<listChoice.size();i++){
			ids = ids+listChoice.get(i).getShopId()+",";
		}
		AjaxParams params = new AjaxParams();
		params.put("action_flag", "addOrder");
		params.put("orderMessageId", ids.substring(0,ids.length()-1));
		params.put("orderMessageMoney",payMoney);
		params.put("orderAddress",mtvAddress.getText().toString());
		params.put("orderRemark",mtvremark.getText().toString());

		params.put("orderUserId", MemberUserUtils.getUid(this));
		params.put("orderUserName",MemberUserUtils.getName(this));
		httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultCode, isShow, "正在加载...");
	}

	@Override
	protected void callBackSuccess(ResponseEntry entry, int actionId) {
		super.callBackSuccess(entry, actionId);
		CarObservable.getInstance().notifyStepChange("ok");
		CustomToast.showToast(this, entry.getRepMsg());
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				finish();
			}
		}, 2000);

	}

	@Override
	protected void callBackAllFailure(String strMsg, int actionId) {
		super.callBackAllFailure(strMsg, actionId);
		CustomToast.showToast(this, strMsg);

	}
	private JiJianModel jianModel;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 0) {
			jianModel = (JiJianModel) data.getSerializableExtra("msg");
			mtvAddress.setText(jianModel.getJijianName()+","+jianModel.getJijianPhone()+","+jianModel.getJijianAddresse());
		}
	}

}
