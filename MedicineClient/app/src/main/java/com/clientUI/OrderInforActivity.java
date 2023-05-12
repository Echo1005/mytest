package com.clientUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.client.R;
import com.clientBase.adapter.ShopOrderReviewAdapter;
import com.clientBase.base.BaseActivity;
import com.clientBase.model.ShopModel;

import java.util.ArrayList;
import java.util.List;


public class OrderInforActivity extends BaseActivity {

    // 标题
    private TextView mTvTitle;
    // 返回
    private ImageView mIvBack;
    private TextView mIvStu;
    private ListView mListMessage;
    private String state;
    private LinearLayout mllNomessage;
    private List<ShopModel> listChoice = new ArrayList<ShopModel>();

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
            case R.id.mIvStu:

                break;

        }
    }

    @Override
    public void initWidget() {
        mIvStu = (TextView) findViewById(R.id.mIvStu);
        mIvStu.setText("评价");
        mIvStu.setVisibility(View.GONE);
        mllNomessage = (LinearLayout) findViewById(R.id.mllNomessage);
        mListMessage = (ListView) findViewById(R.id.mListMessage);

        mIvBack = (ImageView) findViewById(R.id.mIvBack);
        mTvTitle = (TextView) findViewById(R.id.mTvTitle);
        mTvTitle.setText("订单商品");
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(this);
        mIvStu.setOnClickListener(this);
    }

    @Override
    public void initData() {

        listChoice = (List<ShopModel>) this.getIntent().getSerializableExtra("msg");

        ShopOrderReviewAdapter campusAdapter = new ShopOrderReviewAdapter(OrderInforActivity.this, listChoice);
        mListMessage.setAdapter(campusAdapter);

        mListMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OrderInforActivity.this, ReviewShareMessageActivity.class);
                intent.putExtra("msg", listChoice.get(position));
                startActivity(intent);

            }
        });
    }


}
