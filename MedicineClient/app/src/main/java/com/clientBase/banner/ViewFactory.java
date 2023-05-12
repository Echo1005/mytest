package com.clientBase.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.client.R;
import com.clientBase.view.RoundRectImageView;


public class ViewFactory {

	public static ImageView getImageView(Context context, int url) {
		RoundRectImageView imageView = (RoundRectImageView)LayoutInflater.from(context).inflate(
				R.layout.view_banner, null);
		imageView.setImageResource(url);
//		ImageLoader.getInstance().displayImage(url, imageView);
		return imageView;
	}
}
