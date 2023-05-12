package com.clientBase.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.client.R;
import com.clientBase.adapter.CarListAdapter;
import com.clientBase.base.BaseFragment;
import com.clientBase.config.Consts;
import com.clientBase.db.MemberUserUtils;
import com.clientBase.listener.CarListner;
import com.clientBase.model.CarModel;
import com.clientBase.model.ResponseEntry;
import com.clientBase.observable.CarObservable;
import com.clientUI.PayCarMessageActivity;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.http.AjaxParams;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class CarFragment extends BaseFragment implements Observer, CarListner {
    // 获取view
    private View rootView;
    // 获取控件
    private ListView mListMessage;
    private List<CarModel> list_result = new ArrayList<CarModel>();
    CarListAdapter orderListAdapter;
    private LinearLayout mllNomessage;
    // 预加载标志
    private boolean isPrepared;
    private TextView mtvNumber;
    private LinearLayout mllGoBuy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_refresh_jin, null);
        isPrepared = true;
        setUserVisibleHint(true);
        return rootView;
    }

    /**
     * 加载数据的方法，只要保证isPrepared和isVisible都为true的时候才往下执行开始加载数据
     */
    @Override
    protected void setlazyLoad() {
        super.setlazyLoad();

        Log.i("pony_log", "isPrepared:" + isPrepared + ",isVisible:" + isVisible);
        if (!isPrepared || !isVisible) {
            return;
        }
        if (list_result.size() == 0) {
            initWidget();
            initData();
        }
    }

    @Override
    public void initWidget() {

        mtvNumber = (TextView) rootView.findViewById(R.id.mtvNumber);
        mllGoBuy = (LinearLayout) rootView.findViewById(R.id.mllGoBuy);
        mllGoBuy.setOnClickListener(this);
        mllNomessage = (LinearLayout) rootView.findViewById(R.id.mllNomessage);
        mListMessage = (ListView) rootView.findViewById(R.id.mListMessage);
        mListMessage.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {


                CarModel carModel = list_result.get(position);
                if (carModel.isChoice()) {
                    carModel.setChoice(false);
                } else {
                    carModel.setChoice(true);
                }
                list_result.set(position, carModel);
                orderListAdapter.notifyDataSetChanged();
                TotalMoney();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.mllGoBuy:
                Intent intent = new Intent(getActivity(), PayCarMessageActivity.class);
                intent.putExtra("msg", (Serializable) list_result_choice);
                intent.putExtra("payMoney", shopMoneyPrice + "");
                startActivity(intent);

                break;

        }
    }

    @Override
    public void initData() {
        listCarPhone(true);
    }

    private void listCarPhone(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "listCarPhone");
        params.put("carUserId", MemberUserUtils.getUid(getActivity()));
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
                        list_result.clear();
                        list_result = mGson.fromJson(entry.getData(), new TypeToken<List<CarModel>>() {
                        }.getType());
                        orderListAdapter = new CarListAdapter(getActivity(), list_result, this);
                        mListMessage.setAdapter(orderListAdapter);
                        mllNomessage.setVisibility(View.GONE);
                        TotalMoney();
                    } else {
                        mllNomessage.setVisibility(View.VISIBLE);
                    }
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        CarObservable.getInstance().addObserver(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CarObservable.getInstance().addObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {
        listCarPhone(false);
    }


    @Override
    public void setJian(int pos, CarModel carModel) {
        Log.i("pony_log:", pos + "");
        carModel.setCarNumber(carModel.getCarNumber() - 1);
        list_result.set(pos, carModel);
        orderListAdapter.notifyDataSetChanged();
        TotalMoney();
    }

    @Override
    public void setJia(int pos, CarModel carModel) {
        Log.i("pony_log:", pos + "");
        carModel.setCarNumber(carModel.getCarNumber() + 1);
        list_result.set(pos, carModel);
        orderListAdapter.notifyDataSetChanged();
        TotalMoney();
    }

    double shopMoneyPrice;


    private List<CarModel> list_result_choice = new ArrayList<CarModel>();


    /**
     * 计算总价格
     */
    private void TotalMoney() {

        shopMoneyPrice = 0;
        list_result_choice.clear();
        for (int i = 0; i < list_result.size(); i++) {

            if (list_result.get(i).isChoice()) {
                shopMoneyPrice = shopMoneyPrice + (Double.valueOf(list_result.get(i).getShopMoney()) * list_result.get(i).getCarNumber());
                list_result_choice.add(list_result.get(i));
            }
        }
        mtvNumber.setText(shopMoneyPrice + "元");
    }
}
