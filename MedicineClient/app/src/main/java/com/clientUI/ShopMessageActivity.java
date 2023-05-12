package com.clientUI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.client.R;
import com.clientBase.adapter.ChoiceShopAdapter;
import com.clientBase.base.BaseActivity;
import com.clientBase.config.Consts;
import com.clientBase.db.MemberUserUtils;
import com.clientBase.model.ResponseEntry;
import com.clientBase.model.ShopModel;
import com.clientBase.observable.CarObservable;
import com.clientBase.observable.ShopObservable;
import com.clientBase.photo.ui.ShowPictureActivity;
import com.clientBase.util.CustomToast;
import com.squareup.picasso.Picasso;

import net.tsz.afinal.http.AjaxParams;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ShopMessageActivity extends BaseActivity {
    // title
    private TextView mTvTitle;
    // 返回
    private ImageView mIvBack;
    // 查询按钮
    private TextView mtvtitle;
    private TextView mtvcontent;
    ShopModel noticeModel;

    private TextView Name;
    private TextView phone;

    private Button mbtnPay, mbtnChat;
    private TextView mtvShopPrice;

    private int choiceTime = 1;
    private TextView mIvStu;

    private ImageView guide_image;

    private List<ShopModel> mlistData = new ArrayList<ShopModel>();
    private ChoiceShopAdapter listaAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopmsg);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {
        listaAdapter = new ChoiceShopAdapter(this);

        guide_image = (ImageView) findViewById(R.id.guide_image);

        mIvStu = (TextView) findViewById(R.id.mIvStu);
        mIvStu.setOnClickListener(this);
        mIvStu.setText("评价");
        mIvStu.setVisibility(View.VISIBLE);


        mbtnChat = (Button) findViewById(R.id.mbtnChat);
        mbtnChat.setOnClickListener(this);

        mbtnPay = (Button) findViewById(R.id.mbtnPay);
        mbtnPay.setOnClickListener(this);
        mtvShopPrice = (TextView) findViewById(R.id.mtvShopPrice);

        Name = (TextView) findViewById(R.id.Name);
        phone = (TextView) findViewById(R.id.phone);

        mtvtitle = (TextView) findViewById(R.id.mtvtitle);
        mtvcontent = (TextView) findViewById(R.id.mtvcontent);

        mIvBack = (ImageView) findViewById(R.id.mIvBack);
        mTvTitle = (TextView) findViewById(R.id.mTvTitle);
        mTvTitle.setText("药品详情");
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.mIvBack:
                ShopMessageActivity.this.finish();
                break;
            case R.id.mbtnChat:
                addCar(false);
                break;

            case R.id.mbtnPay:
                list_result_choice.add(noticeModel);


                Intent mbtnPay = new Intent(ShopMessageActivity.this, PayShopMessageActivity.class);
                mbtnPay.putExtra("msg", (Serializable) list_result_choice);
                mbtnPay.putExtra("payMoney", noticeModel.getShopMoney() + "");
                startActivity(mbtnPay);
                break;


            case R.id.mIvStu:
                Intent mIvStu = new Intent(this, ShopReviewActivity.class);
                mIvStu.putExtra("msg", noticeModel);
                startActivity(mIvStu);

                break;
            case R.id.guide_image:

                Intent intent = new Intent(this, ShowPictureActivity.class);
                intent.putExtra("piction_path", Consts.URL_IMAGE + noticeModel.getShopImg());
                startActivity(intent);

                break;

        }
    }
    private List<ShopModel> list_result_choice = new ArrayList<ShopModel>();
    @Override
    public void initData() {

        noticeModel = (ShopModel) this.getIntent().getSerializableExtra("msg");


        mtvtitle.setText(noticeModel.getShopName());
        Name.setText("发布商家：" + noticeModel.getShopUserName());
        phone.setText("类型：" + noticeModel.getShopTypeName());
        mtvcontent.setText("        " + noticeModel.getShopMessage());

        mtvShopPrice.setText(noticeModel.getShopMoney() + "元");

//
        if (!TextUtils.isEmpty(noticeModel.getShopImg())) {
            Picasso.with(this).load(Consts.URL_IMAGE + noticeModel.getShopImg()).placeholder(R.drawable.default_drawable_show_pictrue)
                    .into(guide_image);
        }


        listShopPhoneUserMessage(false);

    }

    private int posIndex = 0;


    private void listShopPhoneUserMessage(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "listShopPhoneUserMessage");
        params.put("shopUserId", MemberUserUtils.getUid(this));
        httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultFlag, isShow, "正在加载...");
    }

    private void updateShopStateMessage(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "updateShopStateMessage");
        params.put("shopRecycling","2");
        params.put("shopId",noticeModel.getShopId());
        httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultCode, isShow, "正在加载...");
    }


    private void addCar(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "addCar");
        params.put("carShopId", noticeModel.getShopId());
        params.put("carUserId", MemberUserUtils.getUid(this)+"");
        httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultState, isShow, "正在加载...");
    }


    @Override
    protected void callBackSuccess(ResponseEntry entry, int actionId) {
        super.callBackSuccess(entry, actionId);

        switch (actionId) {

            case Consts.actionId.resultState:
                CarObservable.getInstance().notifyStepChange("ok");
                CustomToast.showToast(this, entry.getRepMsg());
                break;
            case Consts.actionId.resultCode:

                CustomToast.showToast(this, entry.getRepMsg());
                ShopObservable.getInstance().notifyStepChange("ok");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);
                break;

        }

    }



}
