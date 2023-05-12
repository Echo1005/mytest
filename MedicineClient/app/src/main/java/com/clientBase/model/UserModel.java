package com.clientBase.model;

import java.io.Serializable;


public class UserModel implements Serializable{




	private int uid;
	private String utime;
	private String upswd;
	private String uphone;
	private String uname;
	private String uImg;
	private String ucard;
	private String utype;
	private String utoken;

	public String getuImg() {
		return uImg;
	}

	public void setuImg(String uImg) {
		this.uImg = uImg;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUtime() {
		return utime;
	}

	public void setUtime(String utime) {
		this.utime = utime;
	}

	public String getUpswd() {
		return upswd;
	}

	public void setUpswd(String upswd) {
		this.upswd = upswd;
	}

	public String getUphone() {
		return uphone;
	}

	public void setUphone(String uphone) {
		this.uphone = uphone;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUImg() {
		return uImg;
	}

	public void setUImg(String uImg) {
		this.uImg = uImg;
	}

	public String getUcard() {
		return ucard;
	}

	public void setUcard(String ucard) {
		this.ucard = ucard;
	}

	public String getUtype() {
		return utype;
	}

	public void setUtype(String utype) {
		this.utype = utype;
	}

	public String getUtoken() {
		return utoken;
	}

	public void setUtoken(String utoken) {
		this.utoken = utoken;
	}
}
