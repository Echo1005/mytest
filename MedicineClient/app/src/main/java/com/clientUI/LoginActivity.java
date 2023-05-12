package com.clientUI;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.client.R;
import com.clientBase.app.PonyApplication;
import com.clientBase.base.BaseActivity;
import com.clientBase.config.Consts;
import com.clientBase.db.MemberUserUtils;
import com.clientBase.model.ResponseEntry;
import com.clientBase.model.UserModel;
import com.clientBase.util.Constant;
import com.clientBase.util.CustomToast;
import com.clientBase.util.LoadingDialog;
import com.clientBase.util.ToastUtil;

import net.tsz.afinal.http.AjaxParams;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends BaseActivity  {
    // title
    // 登录用户名称
    private EditText mLoginNumber;
    // 登录密码
    private EditText mLoginPswd;
    // 登录按钮
    private Button mLogin;
    private Button mEnterpriseQuery;
    private LinearLayout mllTop;
    private UserModel userModel;

    private TextView login_for_pswd;

    private TextView mTvTitle;
    String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    List<String> mPermissionList = new ArrayList<String>();
    private static final int ACCESS_FINE_LOCATION = 1;
    private static final int WRITE_EXTERNAL_STORAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initWidget();
        checkPermission();
    }

    /**
     * 控件初始化
     */
    @Override
    public void initWidget() {

//        mTvTitle = (TextView) findViewById(R.id.mTvTitle);
//        mTvTitle.setText("登录");

        login_for_pswd= (TextView) findViewById(R.id.login_for_pswd);
        mdialog = new LoadingDialog(this, "正在登录");
        mLoginNumber = (EditText) findViewById(R.id.mLoginNumber);
        mLoginPswd = (EditText) findViewById(R.id.mLoginPswd);
        mLogin = (Button) findViewById(R.id.mLogin);
        mEnterpriseQuery = (Button) findViewById(R.id.mEnterpriseQuery);
        // mLoginNumber.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        // 事件的监听
        mLogin.setOnClickListener(this);
        mEnterpriseQuery.setOnClickListener(this);
        // 给输入框设置默认的测试数据
        mLoginNumber.setSelection(mLoginNumber.getText().length());

        mLoginNumber.setText("12345678901");
        mLoginPswd.setText("123456");
        login_for_pswd.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.mLogin:
                if (TextUtils.isEmpty(mLoginNumber.getText().toString())) {
                    ToastUtil.ShowCentre(LoginActivity.this, "请输入手机号码");
                    return;
                }
                if (TextUtils.isEmpty(mLoginPswd.getText().toString())) {
                    ToastUtil.ShowCentre(LoginActivity.this, "请输入登录密码");
                    return;
                }


                LoginUserPost(true);
                break;

            case R.id.mEnterpriseQuery:
                Intent mEnterpriseQuery = new Intent(LoginActivity.this, RegisterCreatActivity.class);
                startActivity(mEnterpriseQuery);

                break;
            case R.id.login_for_pswd:
                break;

        }
    }

    @Override
    public void initData() {
    }

    /**
     * 用户的登录
     *
     * @param isShow
     */
    private void LoginUserPost(boolean isShow) {
        AjaxParams params = new AjaxParams();
        params.put("action_flag", "login");
        params.put("userPhone", mLoginNumber.getText().toString());
        params.put("userPswd", mLoginPswd.getText().toString());
        httpPost(Consts.URL + Consts.APP.RegisterAction, params, Consts.actionId.resultFlag, isShow, "正在登录...");
    }

    @Override
    protected void callBackSuccess(ResponseEntry entry, int actionId) {
        super.callBackSuccess(entry, actionId);

        switch (actionId) {
            case Consts.actionId.resultFlag:

                if (null != entry.getData() && !TextUtils.isEmpty(entry.getData())) {
                    userModel = mGson.fromJson(entry.getData(), UserModel.class);
                    MemberUserUtils.setUid(LoginActivity.this, userModel.getUid()+"");
                    MemberUserUtils.setName(LoginActivity.this, userModel.getUname());
                    MemberUserUtils.setLoginFlag(LoginActivity.this, "food");
                    MemberUserUtils.putBean(LoginActivity.this, "user_messgae", userModel);

                    Intent intent = new Intent(LoginActivity.this, FrameworkActivity.class);
                    startActivity(intent);
                    finish();

                }
                break;
        }

    }

    @Override
    protected void callBackAllFailure(String strMsg, int actionId) {
        super.callBackAllFailure(strMsg, actionId);
        ToastUtil.show(LoginActivity.this, strMsg);

    }



    private void checkPermission(){
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }

        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
            initWidget();
            initData();
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(this, permissions, ACCESS_FINE_LOCATION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initWidget();
                initData();
            } else {
                CustomToast.showToast(this,"权限已拒绝");
            }
        }else if (requestCode == WRITE_EXTERNAL_STORAGE){
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //判断是否勾选禁止后不再询问
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]);
                    if (showRequestPermission) {
                        CustomToast.showToast(this,"权限未申请");
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    /**
     * 检查是否拥有权限
     *
     * @param thisActivity
     * @param permission
     * @param requestCode
     * @param errorText
     */
    protected void checkPermission(Activity thisActivity, String permission, int requestCode, String errorText) {
        //判断当前Activity是否已经获得了该权限
        if (ContextCompat.checkSelfPermission(thisActivity, permission) != PackageManager.PERMISSION_GRANTED) {
            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                    permission)) {

                CustomToast.showToast(this,errorText);
                //进行权限请求
                ActivityCompat.requestPermissions(thisActivity,
                        new String[]{permission},
                        requestCode);
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(thisActivity,
                        new String[]{permission},
                        requestCode);
            }
        } else {

        }
    }


}