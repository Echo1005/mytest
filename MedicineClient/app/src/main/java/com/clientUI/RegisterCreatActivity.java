package com.clientUI;

import net.tsz.afinal.http.AjaxParams;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.client.R;
import com.clientBase.base.BaseActivity;
import com.clientBase.config.Consts;
import com.clientBase.model.ResponseEntry;
import com.clientBase.util.ToastUtil;


/**
 * 用户注册
 */
public class RegisterCreatActivity extends BaseActivity {
    // 标题
    private TextView mTvTitle;
    // 返回
    private ImageView mIvBack;
    private Button mSubmit;

    private EditText metName;
    private EditText metPhone;
    private EditText metPswd;
    private EditText mettwoPswd;
    private EditText metEmail;

    private RadioGroup mrgChoice;
    private RadioButton mrb1 = null;
    private RadioButton mrb2 = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_register);
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

                if (TextUtils.isEmpty(metName.getText().toString())) {
                    ToastUtil.ShowCentre(this, "请输入用户名称");
                    return;
                }


                if (TextUtils.isEmpty(metPhone.getText().toString())) {
                    ToastUtil.ShowCentre(this, "请输入手机号码");
                    return;
                }




                if (TextUtils.isEmpty(metPswd.getText().toString())) {
                    ToastUtil.ShowCentre(this, "请输入登录密码");
                    return;
                }



                if (TextUtils.isEmpty(mettwoPswd.getText().toString())) {
                    ToastUtil.ShowCentre(this, "请输入确认密码");
                    return;
                }


                if (!metPswd.getText().toString().equals(mettwoPswd.getText().toString())) {
                    ToastUtil.ShowCentre(this, "两次输入密码不一致");
                    return;
                }


                createTopicPost(true);

                break;

        }
    }

    @Override
    public void initWidget() {

        mrgChoice = (RadioGroup) findViewById(R.id.mrgChoice);
        mrb1 = (RadioButton) findViewById(R.id.mrb1);
        mrb2 = (RadioButton) findViewById(R.id.mrb2);


        metEmail = (EditText) findViewById(R.id.metEmail);
        metName = (EditText) findViewById(R.id.metName);
        metPhone = (EditText) findViewById(R.id.metPhone);
        metPswd = (EditText) findViewById(R.id.metPswd);
        mettwoPswd = (EditText) findViewById(R.id.mettwoPswd);
        mSubmit = (Button) findViewById(R.id.mSubmit);
        mIvBack = (ImageView) findViewById(R.id.mIvBack);
        mTvTitle = (TextView) findViewById(R.id.mTvTitle);
        mTvTitle.setText("注册");
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(this);
        mSubmit.setOnClickListener(this);

    }
    @Override
    public void initData() {
        mrgChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == mrb1.getId()) {
                } else if (checkedId == mrb2.getId()) {
                }
            }
        });

    }


    /**
     * 添加注册的参数信息
     *
     * @param isShow
     */
    private void createTopicPost(boolean isShow) {

        AjaxParams params = new AjaxParams();
        params.put("action_flag", "add");
        params.put("uname", metName.getText().toString());
        params.put("upswd", metPswd.getText().toString());
        params.put("uphone", metPhone.getText().toString());

        httpPost(Consts.URL + Consts.APP.RegisterAction, params, Consts.actionId.resultCode, isShow, "正在注册...");

    }

    /**
     * 注册成功的数据回调
     *
     * @param entry
     * @param actionId
     */
    @Override
    protected void callBackSuccess(ResponseEntry entry, int actionId) {
        super.callBackSuccess(entry, actionId);
        ToastUtil.show(RegisterCreatActivity.this, entry.getRepMsg());
        new Handler().postDelayed(new Runnable() {
            //
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }

    /**
     * 注册失败的数据回调
     *
     * @param actionId
     */
    @Override
    protected void callBackAllFailure(String strMsg, int actionId) {
        super.callBackAllFailure(strMsg, actionId);
        ToastUtil.show(RegisterCreatActivity.this, strMsg);

    }
}
