package com.clientUI;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.client.R;
import com.clientBase.adapter.ReplyAdapter;
import com.clientBase.base.BaseActivity;
import com.clientBase.config.Consts;
import com.clientBase.model.ResponseEntry;
import com.clientBase.model.ReviewBean;
import com.clientBase.model.ShopModel;
import com.clientBase.model.UserModel;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.http.AjaxParams;

import java.util.List;


public class ShopReviewActivity extends BaseActivity {

    // title
    private TextView mTvTitle;
    // 返回
    private ImageView mIvBack;
    // 查询按钮
    private ListView mListMessage;

    ShopModel projectModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {

        mListMessage = (ListView) findViewById(R.id.mListMessage);
        mIvBack = (ImageView) findViewById(R.id.mIvBack);
        mTvTitle = (TextView) findViewById(R.id.mTvTitle);
        mTvTitle.setText("药品评论");
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.mIvBack:
                ShopReviewActivity.this.finish();
                break;

        }
    }


    @Override
    public void initData() {

        projectModel = (ShopModel) this.getIntent().getSerializableExtra("msg");


        ListNoticesMessage(false);
    }


    private void ListNoticesMessage(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "reviewListMessage");
        params.put("reviewMessageId",  projectModel.getShopId() + "");
        httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultFlag, isShow, "正在提交...");
    }

    @Override
    protected void callBackSuccess(ResponseEntry entry, int actionId) {
        super.callBackSuccess(entry, actionId);

        switch (actionId) {


            case Consts.actionId.resultFlag:
                List<ReviewBean> list_result = mGson.fromJson(entry.getData(), new TypeToken<List<ReviewBean>>() {
                }.getType());
                ReplyAdapter replyAdapter = new ReplyAdapter(this, list_result);
                mListMessage.setAdapter(replyAdapter);
                break;

        }

    }

}
