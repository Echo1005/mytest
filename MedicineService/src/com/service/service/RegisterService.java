package com.service.service;

import java.util.List;
import java.util.Map;

public interface RegisterService {
	//手机注册
	public boolean resgisterPhone(List<Object> params);
	//pc注册
	public boolean resgister(List<Object> params);
	//token注册
	public boolean resgisterToken(List<Object> params);
	//登陆
	public boolean Login(List<Object> params);
	//注册检查
	public boolean resgisterCheck(List<Object> params);
	//查询
	public Map<String, Object> queryOne(List<Object> params);
	//查询Token
	public Map<String, Object> queryToken(List<Object> params);
	//查询id
	public Map<String, Object> queryId(List<Object> params);
	
}
