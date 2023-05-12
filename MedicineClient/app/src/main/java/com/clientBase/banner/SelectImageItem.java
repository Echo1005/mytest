package com.clientBase.banner;

import java.io.Serializable;

/**
 *
 *图片处理类
 */
public class SelectImageItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	private int urlDrawable;
	private int sid;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getUrlDrawable() {
		return urlDrawable;
	}

	public void setUrlDrawable(int urlDrawable) {
		this.urlDrawable = urlDrawable;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}
}
