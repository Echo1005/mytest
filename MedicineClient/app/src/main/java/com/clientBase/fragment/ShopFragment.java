package com.clientBase.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.client.R;
import com.clientBase.adapter.ShopListAdapter;
import com.clientBase.adapter.TypeHotAdapter;
import com.clientBase.banner.CycleViewPager;
import com.clientBase.banner.SelectImageItem;
import com.clientBase.banner.ViewFactory;
import com.clientBase.base.BaseFragment;
import com.clientBase.config.Consts;
import com.clientBase.db.MemberUserUtils;
import com.clientBase.model.MainModel;
import com.clientBase.model.ResponseEntry;
import com.clientBase.model.ShopModel;
import com.clientBase.model.TypeModel;
import com.clientBase.observable.ShopObservable;
import com.clientBase.refresh.JellyRefreshLayout;
import com.clientBase.refresh.PullToRefreshLayout;
import com.clientBase.util.CustomToast;
import com.clientBase.view.GridviewForScrollView;
import com.clientBase.view.ListviewForScrollView;
import com.clientUI.MyShopTypeListActivity;
import com.clientUI.SearchActivity;
import com.clientUI.ShopMessageActivity;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class ShopFragment extends BaseFragment implements Observer {


    // 获取view
    private View rootView;
    // 获取控件
    private Button mviTongJi;
    private List<TypeModel> listType = new ArrayList<TypeModel>();
    private List<ShopModel> list_resultsearch = new ArrayList<ShopModel>();
    private List<ShopModel> list_resultShop = new ArrayList<ShopModel>();
    private TextView metName;
    private GridviewForScrollView mBookType;
    MainModel mainModel;
    private ListviewForScrollView mNewsListMessage;
    private ListviewForScrollView mrecommendListMessage;
    private ListView msearchListMessage;
    // 预加载标志
    private boolean isPrepared;
    private JellyRefreshLayout mJellyLayout;


    private CycleViewPager cycleViewPager;
    private List<ImageView> views = new ArrayList<ImageView>();
    private List<SelectImageItem> infos = new ArrayList<SelectImageItem>();
    private int[] imgArray = new int[]{R.drawable.banner1, R.drawable.banner2};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_message_shop, null);
//        isPrepared = true;
//        setlazyLoad();

        initWidget();
        initData();
        return rootView;
    }


    /**
     * 加载数据的方法，只要保证isPrepared和isVisible都为true的时候才往下执行开始加载数据
     */
    @Override
    protected void setlazyLoad() {
        super.setlazyLoad();

        if (!isPrepared || !isVisible) {
            return;
        }
        if (list_resultShop.size() == 0) {
            initWidget();
            initData();
        }
    }

    @Override
    public void initWidget() {

        cycleViewPager = (CycleViewPager) getActivity().getFragmentManager().findFragmentById(R.id.fragment_soft_image);


        mJellyLayout = (JellyRefreshLayout) rootView.findViewById(R.id.jelly_refresh);
        mJellyLayout.setPullToRefreshListener(new PullToRefreshLayout.PullToRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                pullToRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mJellyLayout.setRefreshing(false);
                        listShopPhoneMessage(false);

                    }
                }, 1000);
            }
        });
        View loadingView = LayoutInflater.from(getActivity()).inflate(R.layout.view_loading, null);
        mJellyLayout.setLoadingView(loadingView);


        mrecommendListMessage = (ListviewForScrollView) rootView.findViewById(R.id.mrecommendListMessage);
        msearchListMessage = (ListView) rootView.findViewById(R.id.msearchListMessage);
        mNewsListMessage = (ListviewForScrollView) rootView.findViewById(R.id.mNewsListMessage);


        mviTongJi = (Button) rootView.findViewById(R.id.mviTongJi);
        mviTongJi.setOnClickListener(this);
        metName = (TextView) rootView.findViewById(R.id.metName);
        mBookType = (GridviewForScrollView) rootView.findViewById(R.id.mBookType);

        mviTongJi.setVisibility(View.GONE);


        msearchListMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ShopMessageActivity.class);
                intent.putExtra("msg", list_resultsearch.get(position));
                startActivity(intent);

            }
        });

        mrecommendListMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ShopMessageActivity.class);
                intent.putExtra("msg", list_resulttuijian.get(position));
                startActivity(intent);

            }
        });


        mNewsListMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if(Integer.valueOf(list_resultShop.get(position).getShopNumber())>0){
                    Intent intent = new Intent(getActivity(), ShopMessageActivity.class);
                    intent.putExtra("msg", list_resultShop.get(position));
                    startActivity(intent);
                }else{
                    CustomToast.showToast(getActivity(),"库存没有了；看看其他的吧！");
                }



            }
        });

        mviTongJi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });

        mBookType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), MyShopTypeListActivity.class);
                intent.putExtra("msg", listType.get(i));
                startActivity(intent);
            }
        });


        metName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initData() {


        infos.clear();
        views.clear();
        for (int i = 0; i < imgArray.length; i++) {
            SelectImageItem info = new SelectImageItem();
            info.setUrlDrawable(imgArray[i]);
            info.setSid(i);
            infos.add(info);
        }

        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(getActivity(), infos.get(infos.size() - 1).getUrlDrawable()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ViewFactory.getImageView(getActivity(), infos.get(i).getUrlDrawable()));
        }
        // 将第一个ImageView添加进来
        views.add(ViewFactory.getImageView(getActivity(), infos.get(0).getUrlDrawable()));

        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);

        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, infos, mAdCycleViewListener);
        // 设置轮播
        cycleViewPager.setWheel(true);

        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(2000);
        // 设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();



        listShopPhoneMessage(false);


        metName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() > 0) {
                    listSearchBookMessage(false, editable.toString());
                    msearchListMessage.setVisibility(View.VISIBLE);
                } else {
                    msearchListMessage.setVisibility(View.GONE);
                }
            }
        });


    }


    private void listSearchBookMessage(boolean isShow, String searchMsg) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "listSearchMessage");
        params.put("searchMsg", searchMsg);
        httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultCode, isShow, "正在加载...");
    }



    private void listShopPhoneMessage(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "listShopPhoneMessage");
        params.put("shopCity", MemberUserUtils.getCity(getActivity()));
        httpPost(Consts.URL + Consts.APP.MessageAction, params, Consts.actionId.resultFlag, isShow, "正在加载...");
    }

    ShopListAdapter listAdapter;
    private List<ShopModel> list_resulttuijian = new ArrayList<ShopModel>();
    @Override
    protected void callBackSuccess(ResponseEntry entry, int actionId) {
        super.callBackSuccess(entry, actionId);

        switch (actionId) {

            case Consts.actionId.resultCode:


                if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {

                    String jsonMsg = entry.getData().substring(1, entry.getData().length() - 1);


                    if (null != jsonMsg && !TextUtils.isEmpty(jsonMsg)) {
                        list_resultsearch = mGson.fromJson(entry.getData(), new TypeToken<List<ShopModel>>() {
                        }.getType());
                        ShopListAdapter lookListAdapter = new ShopListAdapter(getActivity(), list_resultsearch);
                        msearchListMessage.setAdapter(lookListAdapter);

                    }

                }

                break;

            case Consts.actionId.resultFlag:

                mJellyLayout.setRefreshing(false);
                if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {

                    String jsonMsg = entry.getData().substring(1, entry.getData().length() - 1);
                    if (null != jsonMsg && !TextUtils.isEmpty(jsonMsg)) {
                        list_resultShop.clear();

                        mainModel = mGson.fromJson(entry.getData(),MainModel.class);

                        list_resultShop = mainModel.getListShop();
                        list_resulttuijian = mainModel.getListTuIjian();
                        listAdapter = new ShopListAdapter(getActivity(), list_resultShop);
                        mNewsListMessage.setAdapter(listAdapter);


                        ShopListAdapter    tuijianlistAdapter = new ShopListAdapter(getActivity(), list_resulttuijian);
                        mrecommendListMessage.setAdapter(tuijianlistAdapter);



                        TypeHotAdapter lookListAdapter = new TypeHotAdapter(getActivity(), listType);
                        mBookType.setAdapter(lookListAdapter);

                    }
                }
                break;


        }

    }

    @Override
    public void onResume() {
        super.onResume();
        ShopObservable.getInstance().addObserver(ShopFragment.this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ShopObservable.getInstance().addObserver(ShopFragment.this);
    }

    @Override
    public void update(Observable observable, Object data) {
        listShopPhoneMessage(false);
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(SelectImageItem info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                position = position - 1;
//                Intent intent = new Intent(getActivity(), MessageActivity.class);
//                intent.putExtra("msg",list_banner.get(position));
//                startActivity(intent);
            }
        }

    };

}

