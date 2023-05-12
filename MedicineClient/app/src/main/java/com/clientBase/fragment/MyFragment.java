package com.clientBase.fragment;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.client.R;
import com.clientBase.base.BaseFragment;
import com.clientBase.config.Consts;
import com.clientBase.db.MemberUserUtils;
import com.clientBase.model.ResponseEntry;
import com.clientBase.model.UserModel;
import com.clientBase.util.CustomToast;
import com.clientBase.view.CircleImageView;
import com.clientUI.AddressListActivity;
import com.clientUI.LoginActivity;
import com.clientUI.MyOrderActivity;
import com.clientUI.UserActivity;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.io.File;
import java.io.FileNotFoundException;


import static android.app.Activity.RESULT_OK;


/**
 * @author WangXuan
 */
public class MyFragment extends BaseFragment {

    // 获取view
    private View rootView;
    private RelativeLayout mtvUser;
    private RelativeLayout mrlMyCollect;
    private RelativeLayout mtvSoft;


    private RelativeLayout mrlMyShop;

    private RelativeLayout mrlcollect;
    private RelativeLayout mrlpraise;
    private RelativeLayout mrlMytopic;

    private RelativeLayout mrlReview;

    private TextView userName;
    private TextView userPhone;
    private CircleImageView musicImage;
    private static final int REQUEST_CODE_SCAN_GALLERY = 100;

    private RelativeLayout mrltuichu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my, null);

        return rootView;
    }

    /**
     * 获取控件
     */
    @Override
    public void initWidget() {




        userName = (TextView) rootView.findViewById(R.id.userName);
        userPhone = (TextView) rootView.findViewById(R.id.userPhone);
        musicImage = (CircleImageView) rootView.findViewById(R.id.musicImage);
        musicImage.setOnClickListener(this);
        userName.setOnClickListener(this);

        mrlMyShop = (RelativeLayout) rootView.findViewById(R.id.mrlMyShop);
        mtvSoft = (RelativeLayout) rootView.findViewById(R.id.mtvSoft);
        mrlMyCollect = (RelativeLayout) rootView.findViewById(R.id.mrlMyCollect);
        mtvUser = (RelativeLayout) rootView.findViewById(R.id.mtvUser);
        mrlMyCollect.setOnClickListener(this);
        mtvUser.setOnClickListener(this);
        mtvSoft.setOnClickListener(this);
        mrlMyShop.setOnClickListener(this);


        mrltuichu = (RelativeLayout) rootView.findViewById(R.id.mrltuichu);
        mrltuichu.setOnClickListener(this);



        mrlMytopic = (RelativeLayout) rootView.findViewById(R.id.mrlMytopic);
        mrlMytopic.setOnClickListener(this);


        mrlReview = (RelativeLayout) rootView.findViewById(R.id.mrlReview);
        mrlReview.setOnClickListener(this);
    }

    /**
     * 处理点击事件
     */
    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.mrltuichu:

                MemberUserUtils.setUid(getActivity(),"");

                Intent mrltuichu = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(mrltuichu);
                getActivity().finish();
                break;

            case R.id.musicImage:
//                Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT); //"android.intent.action.GET_CONTENT"
//                innerIntent.setType("image/*");
//                startActivityForResult(innerIntent, REQUEST_CODE_SCAN_GALLERY);
                break;

            case R.id.mrlMyCollect:
                Intent mrlMyCollect = new Intent(getActivity(), AddressListActivity.class);
                getActivity().startActivity(mrlMyCollect);
                break;

            case R.id.mrlReview:
                break;

            case R.id.userName:
                Intent userName = new Intent(getActivity(), UserActivity.class);
                getActivity().startActivity(userName);
                break;



            case R.id.mrlMytopic:
                Intent LinearLayout01 = new Intent(getActivity(), MyOrderActivity.class);
                getActivity().startActivity(LinearLayout01);
                break;

            case R.id.mtvUser:
                Intent mtvUser = new Intent(getActivity(), UserActivity.class);
                getActivity().startActivity(mtvUser);
                break;


            default:
                break;
        }

    }

    /**
     * 处理数据
     */
    @Override
    public void initData() {


        UserModel userModel = (UserModel) MemberUserUtils.getBean(getActivity(), "user_messgae");
        userName.setText(userModel.getUname());
        userPhone.setText(userModel.getUphone());

        Log.i("pony_log","图片上传成功"+ Consts.URL_IMAGE + userModel.getuImg());


//        if (!TextUtils.isEmpty(userModel.getUImg())) {
//            Picasso.with(getActivity()).load(Consts.URL_IMAGE + userModel.getUImg()).placeholder(R.drawable.stuimg).into(musicImage);
//        }

    }

    @Override
    public void onResume() {
        super.onResume();
        initWidget();
        initData();
    }
    private String userImage;

    private void updateImage(boolean isShow) {

        AjaxParams params = new AjaxParams();
        params.put("action_flag", "updateImage");
        params.put("uImg", userImage + "");
        params.put("userId", MemberUserUtils.getUid(getActivity()) + "");
        httpPost(Consts.URL + Consts.APP.RegisterAction, params, Consts.actionId.resultCode, isShow, "正在注册...");
    }


    @Override
    protected void callBackSuccess(ResponseEntry entry, int actionId) {
        super.callBackSuccess(entry, actionId);
        switch (actionId) {
            case Consts.actionId.resultCode:
                musicImage.setImageURI(uriInfor);
                CustomToast.showToast(getActivity(), "修改成功");
                break;

        }
    }

    Uri uriInfor;

    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SCAN_GALLERY:
                    uriInfor = data.getData();
                    String imagePath = getRealPathFromUri(getActivity(), data.getData());

                    String[] arrPath = imagePath.split("\\/");
                    userImage = arrPath[arrPath.length - 1];

                    FinalHttp finalHttp = new FinalHttp();
                    // 文件上传到服务器的位置

                    AjaxParams params = new AjaxParams();
                    // 获取要上传的本地资源
                    try {
                        params.put("userImage", new File(imagePath));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    finalHttp.post(Consts.URL + Consts.APP.RegisterAction + "?action_flag=uploadImage", params, new AjaxCallBack<Object>() {
                        @Override
                        public void onStart() {
                            // mbtnAdd.setText("开始上传");
                            super.onStart();
                        }

                        @Override
                        public void onSuccess(Object o) {
                            // mbtnAdd.setText("上传成功");
//                            ToastUtil.show(getActivity(), "上传成功");
                            Log.i("pony_log","图片上传成功");
                            // updateUser(false);
                            super.onSuccess(o);
                            updateImage(true);
                        }

                        @Override
                        public void onFailure(Throwable t, int errorNo, String strMsg) {
                            // mbtnAdd.setText("上传失败");
                            super.onFailure(t, errorNo, strMsg);
                        }
                    });


                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 根据图片的Uri获取图片的绝对路径(已经适配多种API)
     *
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    public static String getRealPathFromUri(Context context, Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion < 11) {
            // SDK < Api11
            return getRealPathFromUri_BelowApi11(context, uri);
        }
        if (sdkVersion < 19) {
            // SDK > 11 && SDK < 19
            return getRealPathFromUri_Api11To18(context, uri);
        }
        // SDK > 19
        return getRealPathFromUri_AboveApi19(context, uri);
    }

    /**
     * 适配api19以上,根据uri获取图片的绝对路径
     */
    @RequiresApi( api = Build.VERSION_CODES.KITKAT )
    private static String getRealPathFromUri_AboveApi19(Context context, Uri uri) {
        String filePath = null;
        String wholeID = DocumentsContract.getDocumentId(uri);

        // 使用':'分割
        String id = wholeID.split(":")[1];

        String[] projection = {MediaStore.Images.Media.DATA};
        String selection = MediaStore.Images.Media._ID + "=?";
        String[] selectionArgs = {id};

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                selection, selectionArgs, null);
        int columnIndex = cursor.getColumnIndex(projection[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    /**
     * 适配api11-api18,根据uri获取图片的绝对路径
     */
    private static String getRealPathFromUri_Api11To18(Context context, Uri uri) {
        String filePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};

        CursorLoader loader = new CursorLoader(context, uri, projection, null,
                null, null);
        Cursor cursor = loader.loadInBackground();

        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }

    /**
     * 适配api11以下(不包括api11),根据uri获取图片的绝对路径
     */
    private static String getRealPathFromUri_BelowApi11(Context context, Uri uri) {
        String filePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }


}
