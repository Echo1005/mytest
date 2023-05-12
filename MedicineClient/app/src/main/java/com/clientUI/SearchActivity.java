package com.clientUI;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.client.R;
import com.clientBase.adapter.ShopListAdapter;
import com.clientBase.adapter.ShopSearchListAdapter;
import com.clientBase.app.PonyApplication;
import com.clientBase.base.BaseActivity;
import com.clientBase.config.Consts;
import com.clientBase.db.MemberUserUtils;
import com.clientBase.model.ResponseEntry;
import com.clientBase.model.ShopModel;
import com.clientBase.util.ToastUtil;
import com.google.gson.reflect.TypeToken;


import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchActivity extends BaseActivity {

    // title
    private TextView mTvTitle;
    // 返回
    private ImageView mIvBack;
    // 查询按钮
    private EditText metName;
    private ListView mListMessage;
    private LinearLayout mllNomessage;
    private TextView mtvSearch;
    List<ShopModel> list_result = new ArrayList<ShopModel>();
    List<ShopModel> list_resultjuli = new ArrayList<ShopModel>();

    private CheckBox cbxiaoliang,cbpingjia,cbjuli,cbchandi,cbjiage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initWidget();
        initData();
    }

    @Override
    public void initWidget() {

        cbxiaoliang = (CheckBox) findViewById(R.id.cbxiaoliang);
        cbpingjia = (CheckBox) findViewById(R.id.cbpingjia);
        cbjuli = (CheckBox) findViewById(R.id.cbjuli);
        cbchandi = (CheckBox) findViewById(R.id.cbchandi);
        cbjiage = (CheckBox) findViewById(R.id.cbjiage);


        mllNomessage = (LinearLayout) findViewById(R.id.mllNomessage);
        mListMessage = (ListView) findViewById(R.id.mListMessage);
        metName = (EditText) findViewById(R.id.metName);
        mtvSearch = (TextView) findViewById(R.id.mtvSearch);
        mIvBack = (ImageView) findViewById(R.id.mIvBack);
        mTvTitle = (TextView) findViewById(R.id.mTvTitle);
        mTvTitle.setText("商品搜索");
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(this);
        mtvSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.mIvBack:
                SearchActivity.this.finish();
                break;
            case R.id.mtvSearch:

                if (TextUtils.isEmpty(metName.getText().toString())) {
                    ToastUtil.ShowCentre(SearchActivity.this, "请输入单词");
                    return;
                }
                break;
        }
    }

    @Override
    public void initData() {
//        listRecommendMessage(false,"");
        metName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(editable.toString().length()>0){
                    listSearchBookMessage(false,editable.toString());
                }
            }
        });

        mListMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, ShopMessageActivity.class);
                intent.putExtra("msg", list_result.get(position));
                startActivity(intent);

            }
        });
        cbxiaoliang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {



                if(b){
                    Comparator<ShopModel> netTypeComparator = new Comparator<ShopModel>() {
                        @Override
                        public int compare(ShopModel o1, ShopModel o2) {
                            return Integer.valueOf(o1.getOrderNumber()) - Integer.valueOf(o2.getOrderNumber());
                        }
                    };
                    Collections.sort(list_result,netTypeComparator);
                    campusAdapter.notifyDataSetChanged();
                }else{
                    Comparator<ShopModel> netTypeComparator = new Comparator<ShopModel>() {
                        @Override
                        public int compare(ShopModel o1, ShopModel o2) {
                            return Integer.valueOf(o2.getOrderNumber()) - Integer.valueOf(o1.getOrderNumber());
                        }
                    };
                    Collections.sort(list_result,netTypeComparator);
                    campusAdapter.notifyDataSetChanged();
                }



            }
        });
        cbpingjia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Comparator<ShopModel> netTypeComparator = new Comparator<ShopModel>() {
                        @Override
                        public int compare(ShopModel o1, ShopModel o2) {
                            return o1.getAvgScore().compareTo(o2.getAvgScore());
                        }
                    };
                    Collections.sort(list_result,netTypeComparator);
                    campusAdapter.notifyDataSetChanged();
                }else{
                    Comparator<ShopModel> netTypeComparator = new Comparator<ShopModel>() {
                        @Override
                        public int compare(ShopModel o1, ShopModel o2) {
                            return o2.getAvgScore().compareTo(o1.getAvgScore());
                        }
                    };
                    Collections.sort(list_result,netTypeComparator);
                    campusAdapter.notifyDataSetChanged();
                }
            }
        });
        cbjuli.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Comparator<ShopModel> netTypeComparator = new Comparator<ShopModel>() {
                        @Override
                        public int compare(ShopModel o1, ShopModel o2) {
                            return o1.getShopAddress().compareTo(o2.getShopAddress());
                        }
                    };
                    Collections.sort(list_result,netTypeComparator);
                    campusAdapter.notifyDataSetChanged();
                }else{
                    Comparator<ShopModel> netTypeComparator = new Comparator<ShopModel>() {
                        @Override
                        public int compare(ShopModel o1, ShopModel o2) {
                            return o2.getShopAddress().compareTo(o1.getShopAddress());
                        }
                    };
                    Collections.sort(list_result,netTypeComparator);
                    campusAdapter.notifyDataSetChanged();
                }
            }
        });
        cbchandi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            }
        });
        cbjiage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(b){
                    Comparator<ShopModel> netTypeComparator = new Comparator<ShopModel>() {
                        @Override
                        public int compare(ShopModel o1, ShopModel o2) {
                            return Integer.valueOf(o1.getShopMoney()) - Integer.valueOf(o2.getShopMoney());
                        }
                    };
                    Collections.sort(list_result,netTypeComparator);
                    campusAdapter.notifyDataSetChanged();
                }else{
                    Comparator<ShopModel> netTypeComparator = new Comparator<ShopModel>() {
                        @Override
                        public int compare(ShopModel o1, ShopModel o2) {
                            return Integer.valueOf(o2.getShopMoney()) - Integer.valueOf(o1.getShopMoney());
                        }
                    };
                    Collections.sort(list_result,netTypeComparator);
                    campusAdapter.notifyDataSetChanged();
                }


            }
        });

    }

    private void listSearchBookMessage(boolean isShow, String searchMsg) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "listSearchMessage");
        params.put("searchMsg", searchMsg);
        params.put("userId", MemberUserUtils.getUid(this));
        httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultCode, isShow, "正在加载...");
    }
    private void listRecommendMessage(boolean isShow, String searchMsg) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "listRecommendMessage");
        params.put("searchMsg", searchMsg);
        params.put("userId", MemberUserUtils.getUid(this));
        httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultCode, isShow, "正在加载...");
    }
    ShopSearchListAdapter campusAdapter;
    @Override
    protected void callBackSuccess(ResponseEntry entry, int actionId) {
        super.callBackSuccess(entry, actionId);

        switch (actionId) {
            case Consts.actionId.resultCode:
                if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {

                    String jsonMsg = entry.getData().substring(1, entry.getData().length() - 1);
                    if (null != jsonMsg && !TextUtils.isEmpty(jsonMsg)) {

                        list_result.clear();
                        list_result = mGson.fromJson(entry.getData(), new TypeToken<List<ShopModel>>() {
                        }.getType());

                        ShopListAdapter lookListAdapter = new ShopListAdapter(SearchActivity.this, list_result);
                        mListMessage.setAdapter(lookListAdapter);


                    } else {
                    }
                }
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            cbchandi.setText(data.getStringExtra("msg"));
            listSearchBookMessage(false,data.getStringExtra("msg"));
        }
    }



}
