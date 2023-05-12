package com.service.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.service.jdbc.JdbcUtils;
import com.service.service.MessageService;

public class MessageDao implements MessageService {
	private JdbcUtils jdbcUtils;

	public MessageDao() {
		jdbcUtils = new JdbcUtils();
	}

	/*----------------------------start-------------------------------*/
	
	public boolean updateOrder(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "update  ordertb set orderState=? where orderId = ?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	
	
	
	public boolean updateShop(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "update  shoptb set shopName=?,shopTypeId=?,shopTypeName=?,shopMoney=?,shopNumber=?,shopMessage=?,shopImg=? where shopId = ?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	
	
	public boolean updateType(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "update  typetb set typeName=? where typeId = ?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	
	public Map<String, Object> queryType(List<Object> params) {
		Map<String, Object> map = null;
		String sql = "select * from typetb where typeId=?";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}
	
	
	public List<Map<String, Object>> listShopOrderPcMessage() {

		
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from ordertb ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
			
			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);

				String[] ids = list.get(i).get("orderMessageId").toString().split(",");

				List<Map<String, Object>> listResultShop = new ArrayList<Map<String, Object>>();
				String  shopInfor = "";
				for (int a = 0; a < ids.length; a++) {
					List<Object> paramsCheck = new ArrayList<Object>();
					paramsCheck.clear();
					paramsCheck.add(ids[a] + "");
					Map<String, Object> queryShop = queryShop(paramsCheck);
					listResultShop.add(queryShop);
					shopInfor = shopInfor+queryShop.get("shopName").toString()+",";
				}
				mapResult.put("shopLists", shopInfor.substring(0, shopInfor.length()-1));

				listResult.add(mapResult);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}
	
	
	
	public boolean updateShopNUmberState(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "update  shoptb set shopNumber =? where shopId = ?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	
	
	public boolean deleteShop(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "delete from shoptb where shopId=?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	
	
	public boolean updateState(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "update  shoptb set shopState =? where shopId = ?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	
	public boolean deleteCar(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "delete from cartb where carShopId=? and carUserId = ? ";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	
	
	public List<Map<String, Object>> listRecommendMessage(String searchInfor) {

		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from shoptb where  shopState = 2 and " + searchInfor;

		System.out.println(sql);
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);

			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);

				Map<String, Object> queryOrderLook = queryOrderLook(list.get(i).get("shopId").toString());

				if (queryOrderLook.get("mycount") == null) {
					mapResult.put("orderNumber", "0");
				} else {
					mapResult.put("orderNumber", queryOrderLook.get("mycount"));
				}
				
				
				Map<String, Object> queryShopAvg = queryShopAvg(list.get(i).get("shopId").toString());

				if (queryShopAvg.get("avgScore").equals("")) {
					mapResult.put("avgScore", "0");
				} else {
					mapResult.put("avgScore", queryShopAvg.get("avgScore"));
				}
				

			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	
	
	public Map<String, Object> queryShopAvg(String orderMessageId) {
		Map<String, Object> map = null;
		String sql = "select  AVG(reviewScore) as avgScore from reviewtb   where reviewShopId =  " + orderMessageId ;
		
		System.out.println(sql);
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}
	
	public boolean CheckSearchCase(String searchInfor, String uid) {
		boolean flag = false;
		String sql = "select * from user where  usearch like '%" + searchInfor + "%' and uid=" + uid;
		try {
			jdbcUtils.getConnection();
			Map<String, Object> map = jdbcUtils.findSimpleResult(sql, null);
			flag = !map.isEmpty() ? true : false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public boolean updateSearch(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "update  user set usearch =? where uid = ?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public Map<String, Object> queryUserInfor(String userId) {
		Map<String, Object> map = null;
		String sql = "select * from User where uid=" + userId;
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}

	public List<Map<String, Object>> listJiJianPCMessage(List<Object> params) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from jijiantb where jijianUserId = ?  ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean addJiJian(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into jijiantb (jijianName,jijianAddresse,jijianPhone,jijianUserId) values  (?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> listCarPhone(List<Object> params) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from cartb LEFT JOIN shoptb on cartb.carShopId = shoptb.shopId where carUserId = ?";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean CheckBookCase(List<Object> params) {
		boolean flag = false;
		String sql = "select * from cartb where carShopId=? and carUserId=?";
		try {
			jdbcUtils.getConnection();
			Map<String, Object> map = jdbcUtils.findSimpleResult(sql, params);
			flag = !map.isEmpty() ? true : false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public boolean addCar(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into cartb (carShopId,carUserId) values  (?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> listShopBaoMessage() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from shoptb where isBAO  = 1 ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean UpdateHot(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "update  shoptb set isBao = ? where shopId = ?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> listUserDianMessage() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from user where utype  = 2  and isHot = 1 ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listUserMessage() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from user where utype  = 2 ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> ListShopPCMessage() {
		// TODO Auto-generated method stub

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from shoptb ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listTypeShopMessage(List<Object> params) {
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from shoptb where shopTypeId = ? ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);
			
			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);

				Map<String, Object> queryOrderLook = queryOrderLook(list.get(i).get("shopId").toString());

				if (queryOrderLook.get("mycount") == null) {
					mapResult.put("orderNumber", "0");
				} else {
					mapResult.put("orderNumber", queryOrderLook.get("mycount"));
				}

				
				Map<String, Object> queryShopAvg = queryShopAvg(list.get(i).get("shopId").toString());

				if (queryShopAvg.get("avgScore").equals("")) {
					mapResult.put("avgScore", "0");
				} else {
					mapResult.put("avgScore", queryShopAvg.get("avgScore"));
				}
				
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listSearchMessage(String searchInfor) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from shoptb   where  shopName like '%" + searchInfor + "%'";

		System.out.println(sql);
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);


		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listPhoneReview(String reviewShopId) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from reviewtb where reviewShopId = "+reviewShopId+" order by reviewId desc ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean updateShopStateMessage(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "update shoptb set shopRecycling =? where shopId = ?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public boolean updateExchangeMessage(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "update  exchangetb set exchangeState =? where exchangeId = ?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> listPhoneIMExchange(List<Object> params) {

		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from exchangetb where exchangeShopUserId= ? ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);

			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);

				List<Object> myparams = new ArrayList<Object>();
				myparams.clear();
				myparams.add(list.get(i).get("exchangeMyShopId") + "");
				mapResult.put("myShop", queryShop(myparams));

				List<Object> paramsFocus = new ArrayList<Object>();
				paramsFocus.clear();
				paramsFocus.add(list.get(i).get("exchangeShopId") + "");
				mapResult.put("imShop", queryShop(paramsFocus));

				listResult.add(mapResult);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listPhoneExchange(List<Object> params) {

		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from exchangetb where exchangeMyUserId= ? ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);

			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);

				List<Object> myparams = new ArrayList<Object>();
				myparams.clear();
				myparams.add(list.get(i).get("exchangeMyShopId") + "");
				mapResult.put("myShop", queryShop(myparams));

				List<Object> paramsFocus = new ArrayList<Object>();
				paramsFocus.clear();
				paramsFocus.add(list.get(i).get("exchangeShopId") + "");
				mapResult.put("imShop", queryShop(paramsFocus));

				listResult.add(mapResult);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean addExchange(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into exchangetb (exchangeShopId,exchangeShopUserId,exchangeMyShopId,exchangeMyUserId,exchangeState,exchangeTime) values  (?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	
	public boolean addReview(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into reviewtb (reviewMessageId,reviewContent,reviewUserId,reviewUserName,reviewTime) values  (?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	
	
	public List<Map<String, Object>> reviewListMessage(List<Object> params) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from reviewtb where reviewMessageId = ?  order by  reviewId desc ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}
	public List<Map<String, Object>> listShopPhoneUserMessage(List<Object> params) {
		// TODO Auto-generated method stub

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from shoptb  where shopUserId = ? order by shopId desc";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listPricePhoneMessage(List<Object> params) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from pricetb where priceShopId= ? ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listMyOrderPhoneMessage(List<Object> params) {

		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from ordertb where orderUserId= ? ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);

			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);
				List<Object> paramsFocus = new ArrayList<Object>();
				paramsFocus.clear();
				paramsFocus.add(list.get(i).get("orderMessageId") + "");
				mapResult.put("shopMessage", queryShop(paramsFocus));
				listResult.add(mapResult);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listShopmTuiJianMessage() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from shoptb  ORDER BY RAND() LIMIT 3 ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);


		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	
	
	public List<Map<String, Object>> listShopmMainPhoneMessage() {
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from shoptb order by shopId desc";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);


		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public Map<String, Object> queryOrderLook(String orderMessageId) {
		Map<String, Object> map = null;
		String sql = "select count(*) mycount from ordertb where orderMessageId like '" + orderMessageId + "' ";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}

	public List<Map<String, Object>> listShopPhoneMessage() {
		// TODO Auto-generated method stub

		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from shoptb RIGHT JOIN  ordertb on shoptb.shopId != ordertb.orderMessageId  order by shopId desc";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);

			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);
				List<Object> paramsFocus = new ArrayList<Object>();
				paramsFocus.clear();
				paramsFocus.add(list.get(i).get("shopUserId") + "");
				Map<String, Object> queryUserMsg = queryUserMsg(paramsFocus);
				mapResult.put("userPhone", queryUserMsg.get("uphone"));

				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
				String format = "yyyy-MM-dd";
				Date nowTime = new SimpleDateFormat(format).parse(df.format(new Date()).toString());
				Date startTime = new SimpleDateFormat(format).parse(list.get(i).get("shopTimeStart") + "");
				Date endTime = new SimpleDateFormat(format).parse(list.get(i).get("shopTimeEnd") + "");
				mapResult.put("isEndPrice", isEffectiveDate(nowTime, startTime, endTime));

				List<Object> paramsTime = new ArrayList<Object>();
				paramsTime.clear();
				paramsTime.add(list.get(i).get("shopId") + "");
				mapResult.put("priceUser", queryPriceMessage(paramsTime));

			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public Map<String, Object> queryPriceMessage(List<Object> params) {
		Map<String, Object> map = null;
		String sql = "SELECT * from pricetb where priceShopId=? ORDER BY priceMoney desc LIMIT 1";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}

	public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
		if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
			return true;
		}

		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(startTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}

	public Map<String, Object> queryUserMsg(List<Object> params) {
		Map<String, Object> map = null;
		String sql = "select * from user where uid=?";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}
	
	public boolean addShopPC(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into shoptb (shopName,shopTypeId,shopTypeName,shopMoney,shopNumber,shopMessage,shopImg) values  (?,?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	// params.add(shopCity);
	// params.add(shopAddress);
	public boolean addShop(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into shoptb (shopName,shopMoney,shopMessage,shopTypeId,shopTypeName,shopImg,shopUserId,shopUserName,shopCreatime"
					+ ",shopCity,shopAddress) values  (?,?,?,?,?,?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public boolean deleteFalut(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "delete from faluttb where falutId=?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public boolean deleteBR(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "delete from businessReporttb where businessReportId=?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> listPhoneLeadReport() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from employeestb where employeesType = 2 ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listPhoneFalutReport() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from faluttb";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean addFalutReport(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into faluttb (falutDepartment,falutDate" + ",falutPerson,falutName,falutIsOrNo,"
					+ "falutNote,falutCoordinates,falutAddress" + ",falutTime,falutUserId,falutUserName) values  (?,?,?,?,?,?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> listPhoneBusinessReport() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from businessReporttb";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean resgisterToken(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into Token (uid,utoken) values  (?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public Map<String, Object> queryEmploy(List<Object> params) {
		Map<String, Object> map = null;
		String sql = "select * from employeestb where employeesPhone=?";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}

	public List<Map<String, Object>> listPhoneEmployClass() {

		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from departmenttb";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);

			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);
				List<Object> paramsUser = new ArrayList<Object>();
				paramsUser.clear();
				paramsUser.add(list.get(i).get("departmentId") + "");
				mapResult.put("employMessage", lisUserMessage(paramsUser));
				listResult.add(mapResult);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> lisUserMessage(List<Object> params) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from employeestb where employeesdepartmentId=?";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean employCheckIdcard(List<Object> params) {
		boolean flag = false;
		String sql = "select * from employeestb where  employeesIdCard = ?";
		try {
			jdbcUtils.getConnection();
			Map<String, Object> map = jdbcUtils.findSimpleResult(sql, params);
			flag = !map.isEmpty() ? true : false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public boolean employCheckPhone(List<Object> params) {
		boolean flag = false;
		String sql = "select * from employeestb where employeesPhone=?";
		try {
			jdbcUtils.getConnection();
			Map<String, Object> map = jdbcUtils.findSimpleResult(sql, params);
			flag = !map.isEmpty() ? true : false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public boolean deleteEmploy(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "delete from employeestb where employeesId=?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> listEmployMessage() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from employeestb";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean addEmploy(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into employeestb (employeesNo,employeesName,employeesIdCard,employeesPhone,employeesPosition,employeesPassword,employeesdepartmentId,employeesdepartmentName,employeesTime,employeesType) values  (?,?,?,?,?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> listDepartmentMessage() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from typetb";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean addDepartment(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into departmenttb (departmentName,departmentTime) values  (?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	/*----------------------------end-------------------------------*/

	public boolean deleteType(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "delete from typetb where typeId=?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> listTypeMessage() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from typetb";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean addType(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into typetb (typeName) values  (?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> circlemsgtb(List<Object> params) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from circlemsgtb where circleId = ? order by circlemsgId desc";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean addQuanZiMessage(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into circlemsgtb (circleId,circlemsgMessage,circlemsgImg,circlemsgUserId," + "circlemsgUserName,"
					+ "circlemsgTime) values  (?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public boolean addApply(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into applymsg (applyUserId,applyUserName,applyPlanId,applyPlanName,applyTime) values  (?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);

		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public boolean checkPlan(List<Object> params) {
		boolean flag = false;
		String sql = "select * from applymsg where applyPlanId=? and applyUserId=?";
		try {
			jdbcUtils.getConnection();
			Map<String, Object> map = jdbcUtils.findSimpleResult(sql, params);
			flag = !map.isEmpty() ? true : false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> listPhonePlanUserMessage(List<Object> params) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from applymsg where applyPlanId = ?";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listActivityMessage() {

		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from activitytb";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean addActivityMessage(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into activitytb (activityName,activityNumber,activityAddress,activityMoney,activityYear,activityUserId,activityUserName,activityTime) values  (?,?,?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public boolean addPrice(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into pricetb (priceMoney,priceShopId,priceShopName,priceUserId,priceUserName,priceTime) values  (?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public boolean addOrder(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into ordertb (orderMessageId,orderMessageMoney,orderUserId,orderUserName,orderAddress,orderCreatime,orderNo,orderState,orderRemark) values  (?,?,?,?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> listorderPhoneMesage(List<Object> params) {

		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from ordertb where orderUserId = ?";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);

			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);

				String[] ids = list.get(i).get("orderMessageId").toString().split(",");

				List<Map<String, Object>> listResultShop = new ArrayList<Map<String, Object>>();
				for (int a = 0; a < ids.length; a++) {
					List<Object> paramsCheck = new ArrayList<Object>();
					paramsCheck.clear();
					paramsCheck.add(ids[a] + "");
					Map<String, Object> queryShop = queryShop(paramsCheck);
					listResultShop.add(queryShop);
				}
				mapResult.put("shopLists", listResultShop);

				listResult.add(mapResult);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}
	
	
	public boolean updateShopNumber(List<Object> params) {
		System.out.println(params.toString());
		boolean flag = false;
		try {
			String sql = "update shoptb set shopNumber =? where shopId = ?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	
	
	public Map<String, Object> queryShopInfor(String shopId) {
		Map<String, Object> map = null;
		String sql = "select * from shoptb where shopId="+shopId;
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}

	public Map<String, Object> queryShop(List<Object> params) {
		Map<String, Object> map = null;
		String sql = "select * from shoptb where shopId=?";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}

	public List<Map<String, Object>> ListCircleMessage() {

		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from circletb";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean addCircleMessage(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into circletb (circleName,circleMessgae,circleUserId,circleUserName,circleAddress,circleTime) values  (?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public boolean updateGreensStateMessage(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "update  shoptb set shopIsSend =? where shopId = ?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> greensThreeMessage() {

		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from greenstb where greensState= 3";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> greensUSERMessage(List<Object> params) {

		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from greenstb where greensUserId = ?";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean updateGreensMessage(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "update  greenstb set greensState =?,greensResultTip =?,greensResultCompanyName =?,greensResultCompanyMessage =?,greensResultImg =?,greensResultUserName = ? where greensId = ?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> greensMessage() {

		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from shoptb ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> recruitUserMessage(List<Object> params) {

		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from recruittb where recruitUserId = ? ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);

			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);

				List<Object> paramsLost = new ArrayList<Object>();
				paramsLost.clear();
				paramsLost.add(list.get(i).get("recruitUserId") + "");

				Map<String, Object> userMessage = queryUser(paramsLost);
				mapResult.put("userMessage", userMessage);
				listResult.add(mapResult);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return listResult;
	}

	public List<Map<String, Object>> lostUserMessage(List<Object> params) {

		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from losttb where lostUserId = ? ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);

			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);

				List<Object> paramsLost = new ArrayList<Object>();
				paramsLost.clear();
				paramsLost.add(list.get(i).get("lostUserId") + "");

				Map<String, Object> userMessage = queryUser(paramsLost);
				mapResult.put("userMessage", userMessage);
				listResult.add(mapResult);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return listResult;
	}

	public boolean deleteRecruit(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "delete from recruittb where recruitId=?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}



	public List<Map<String, Object>> recruitMessage() {

		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from recruittb ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);

			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);

				List<Object> paramsLost = new ArrayList<Object>();
				paramsLost.clear();
				paramsLost.add(list.get(i).get("recruitUserId") + "");

				Map<String, Object> userMessage = queryUser(paramsLost);
				mapResult.put("userMessage", userMessage);
				listResult.add(mapResult);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return listResult;
	}

	public boolean addRecruitMessage(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into recruittb (recruitName,recruitPhone,recruitTime,recruitMessage,recruitLongitude,recruitLatitude"
					+ ",recruitAddress,recruitImage,recruitUserId,recruitUserName,recruitCreateTime) values  (?,?,?,?,?,?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> lostMessage() {

		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from losttb ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);

			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);

				List<Object> paramsLost = new ArrayList<Object>();
				paramsLost.clear();
				paramsLost.add(list.get(i).get("lostUserId") + "");

				Map<String, Object> userMessage = queryUser(paramsLost);
				mapResult.put("userMessage", userMessage);
				listResult.add(mapResult);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return listResult;
	}

	public boolean addGreensMessage(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into shoptb (shopName,shopMoney,shopType,shopMessage,shopImg," + "shopUserId,shopUserName,shopTime"
					+ ",shopState) values  (?,?,?,?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> listContactMessage(List<Object> params) {

		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from contact where cLUserId = ?";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);

			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);

				List<Object> paramsLost = new ArrayList<Object>();
				paramsLost.clear();
				paramsLost.add(list.get(i).get("cLId") + "");
				Map<String, Object> lostMessage = queryLost(paramsLost);
				mapResult.put("lostMessage", lostMessage);

				List<Object> paramsFocus = new ArrayList<Object>();
				paramsFocus.clear();
				paramsFocus.add(list.get(i).get("cUserId") + "");

				Map<String, Object> userMessage = queryUser(paramsFocus);
				mapResult.put("userMessage", userMessage);
				listResult.add(mapResult);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return listResult;
	}

	public Map<String, Object> queryLost(List<Object> params) {
		Map<String, Object> map = null;
		String sql = "select * from lost where lId=? ";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}

	public Map<String, Object> queryUser(List<Object> params) {
		Map<String, Object> map = null;
		String sql = "select * from user where userId=? ";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}

	public boolean updateMessage(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "update  review set rReplyContent =? where rid = ?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public boolean addContact(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into businessReporttb (businessReportDepartment,businessReportDate"
					+ ",businessReportPerson,businessReportDay,businessReportMonth,"
					+ "businessReportYear,businessReportTemperature,businessReportPressures"
					+ ",businessReportSales,businessReportTime,businessReportUserId,businessReportUserName) values  (?,?,?,?,?,?,?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	@Override
	public boolean addMessage(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into lost (lName,lTime,lMessage,lUserId,lUserName,lLongitude,lLatitude,lCreateTime,choiceType) values  (?,?,?,?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> listLostFaBuMessage(List<Object> params) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from lost where lUserId = ?";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listLostMessage() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from lost ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listNoticesMessage(List<Object> params) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from review  where  rNoticeId = ? ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listReviewMessage() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from review";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> listMessage() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from review";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	@Override
	public boolean deleteMessage(List<Object> params) {
		// TODO Auto-generated method stub
		return false;
	}
}
