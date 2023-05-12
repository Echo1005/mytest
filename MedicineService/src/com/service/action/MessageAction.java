package com.service.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.service.config.Consts;
import com.service.dao.MessageDao;
import com.service.utils.PingYinUtil;

public class MessageAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletFileUpload upload;
	private final long MAXSize = 4194304 * 2L;// 4*2MB
	private String filedir = null;
	private MessageDao lostDao;

	public MessageAction() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getContextPath();
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String action_flag = request.getParameter("action_flag");
		if (action_flag.equals("addMessage")) {
			addMessage(request, response);
		} else if (action_flag.equals("listMessagePhone")) {
			listPhoneMessage(request, response);
		} else if (action_flag.equals("listMessageUserPhone")) {
			listPhoneUserMessage(request, response);
		} else if (action_flag.equals("addContact")) {
			addContact(request, response);
		} else if (action_flag.equals("ContactListMessage")) {
			ContactListMessage(request, response);
		} else if (action_flag.equals("addGreensMessage")) {
			addGreensMessage(request, response);
		} else if (action_flag.equals("greensMessage")) {
			greensMessage(request, response);
		} else if (action_flag.equals("addRecruitMessage")) {
			addRecruitMessage(request, response);
		} else if (action_flag.equals("recruitMessage")) {
			RecruitMessage(request, response);
		} else if (action_flag.equals("listLostPcMessage")) {
			listLostPcMessage(request, response);
		} else if (action_flag.equals("listRecruitPcMessage")) {
			listRecruitPcMessage(request, response);
		} else if (action_flag.equals("deleteLost")) {
			deleteLost(request, response);
		} else if (action_flag.equals("deleteRecruit")) {
			deleteRecruit(request, response);
		} else if (action_flag.equals("lostUserMessage")) {
			lostUserMessage(request, response);
		} else if (action_flag.equals("RecruitUserMessage")) {
			RecruitUserMessage(request, response);
		} else if (action_flag.equals("updateGreensMessage")) {
			updateGreensMessage(request, response);
		} else if (action_flag.equals("greensUserMessage")) {
			greensUserMessage(request, response);
		} else if (action_flag.equals("greensThreeMessage")) {
			greensThreeMessage(request, response);
		} else if (action_flag.equals("updateStateMessage")) {
			updateStateMessage(request, response);
		}

		else if (action_flag.equals("addCircleMessage")) {
			addCircleMessage(request, response);
		} else if (action_flag.equals("ListPhoneCircleMessage")) {
			ListPhoneCircleMessage(request, response);
		} else if (action_flag.equals("ListShopMessage")) {
			ListShopMessage(request, response);
		}

		else if (action_flag.equals("addOrder")) {
			addOrder(request, response);
		} else if (action_flag.equals("listOrderPhoneMessage")) {
			listOrderPhoneMessage(request, response);
		}

		else if (action_flag.equals("addActivityMessage")) {
			addActivityMessage(request, response);
		} else if (action_flag.equals("listActivityMessage")) {
			listActivityMessage(request, response);
		}

		else if (action_flag.equals("addApply")) {
			addApply(request, response);
		} else if (action_flag.equals("listPhonePlanUserMessage")) {
			listPhonePlanUserMessage(request, response);
		}

		else if (action_flag.equals("addQuanZiMessage")) {
			addQuanZiMessage(request, response);
		} else if (action_flag.equals("QuanZiMessage")) {
			QuanZiMessage(request, response);
		}

		else if (action_flag.equals("addType")) {
			addType(request, response);
		} else if (action_flag.equals("listTypeMessage")) {
			listTypeMessage(request, response);
		} else if (action_flag.equals("listTypePhoneMessage")) {
			listTypePhoneMessage(request, response);
		} else if (action_flag.equals("deleteType")) {
			deleteType(request, response);
		} else if (action_flag.equals("queryType")) {
			queryType(request, response);
		} else if (action_flag.equals("updateType")) {
			updateType(request, response);
		}else if (action_flag.equals("addDepartment")) {
			addDepartment(request, response);
		} else if (action_flag.equals("listDepartmentMessage")) {
			listDepartmentMessage(request, response);
		} else if (action_flag.equals("listChoiceDepartment")) {
			listChoiceDepartment(request, response);
		} else if (action_flag.equals("addEmploy")) {
			addEmploy(request, response);
		} else if (action_flag.equals("listEmployMessage")) {
			listEmployMessage(request, response);
		} else if (action_flag.equals("deleteEmploy")) {
			deleteEmploy(request, response);
		} else if (action_flag.equals("listPhoneEmployClass")) {
			listPhoneEmployClass(request, response);
		} else if (action_flag.equals("listPhoneEmployLoadIm")) {
			listPhoneEmployLoadIm(request, response);
		}

		else if (action_flag.equals("addBusinessReport")) {
			addBusinessReport(request, response);
		} else if (action_flag.equals("listPhoneBusinessReport")) {
			listPhoneBusinessReport(request, response);
		} else if (action_flag.equals("addFalutReport")) {
			addFalutReport(request, response);
		} else if (action_flag.equals("listPhoneFalutReport")) {
			listPhoneFalutReport(request, response);
		} else if (action_flag.equals("addLead")) {
			addLead(request, response);
		} else if (action_flag.equals("listPhoneLeadReport")) {
			listPhoneLeadReport(request, response);
		} else if (action_flag.equals("deleteBR")) {
			deleteBR(request, response);
		} else if (action_flag.equals("addShop")) {
			addShop(request, response);
		} else if (action_flag.equals("listShopPhoneMessage")) {
			listShopPhoneMessage(request, response);
		} else if (action_flag.equals("addOrder")) {
			addOrder(request, response);
		} else if (action_flag.equals("listMyOrderPhoneMessage")) {
			listMyOrderPhoneMessage(request, response);
		} else if (action_flag.equals("addPrice")) {
			addPrice(request, response);
		} else if (action_flag.equals("listPricePhoneMessage")) {
			listPricePhoneMessage(request, response);
		} else if (action_flag.equals("listShopPhoneUserMessage")) {
			listShopPhoneUserMessage(request, response);
		} else if (action_flag.equals("updateStateMessage")) {
			updateStateMessage(request, response);
		} else if (action_flag.equals("addReview")) {
			addReview(request, response);
		} else if (action_flag.equals("addExchange")) {
			addExchange(request, response);
		} else if (action_flag.equals("listPhoneExchange")) {
			listPhoneExchange(request, response);
		} else if (action_flag.equals("listPhoneIMExchange")) {
			listPhoneIMExchange(request, response);
		} else if (action_flag.equals("updateExchangeMessage")) {
			updateExchangeMessage(request, response);
		} else if (action_flag.equals("updateShopStateMessage")) {
			updateShopStateMessage(request, response);
		} else if (action_flag.equals("listReview")) {
			listReview(request, response);
		} else if (action_flag.equals("listSearchMessage")) {
			listSearchMessage(request, response);
		} else if (action_flag.equals("listTypeShopMessage")) {
			listTypeShopMessage(request, response);
		} else if (action_flag.equals("listTypeChoiceMessage")) {
			listTypeChoiceMessage(request, response);
		} else if (action_flag.equals("addShopPC")) {
			addShopPC(request, response);
		} else if (action_flag.equals("ListShopPCMessage")) {
			ListShopPCMessage(request, response);
		} else if (action_flag.equals("listUserMessage")) {
			listUserMessage(request, response);
		} else if (action_flag.equals("UpdateHot")) {
			UpdateHot(request, response);
		} else if (action_flag.equals("addCar")) {
			addCar(request, response);
		} else if (action_flag.equals("listCarPhone")) {
			listCarPhone(request, response);
		} else if (action_flag.equals("addJiJian")) {
			addJiJian(request, response);
		} else if (action_flag.equals("listJiJianPCMessage")) {
			listJiJianPCMessage(request, response);
		} else if (action_flag.equals("listRecommendMessage")) {
			listRecommendMessage(request, response);
		} else if (action_flag.equals("updateState")) {
			updateState(request, response);
		} else if (action_flag.equals("deleteShop")) {
			deleteShop(request, response);
		} else if (action_flag.equals("listShopOrderPcMessage")) {
			listShopOrderPcMessage(request, response);
		} else if (action_flag.equals("queryShop")) {
			queryShop(request, response);
		} else if (action_flag.equals("updateShop")) {
			updateShop(request, response);
		}else if (action_flag.equals("listChoiceType")) {
			listChoiceDepartment(request, response);
		}else if (action_flag.equals("reviewListMessage")) {
			reviewListMessage(request, response);
		} else if (action_flag.equals("updateOrderMessage")) {
			updateOrderMessage(request, response);
		}
	}
	
	public void init(ServletConfig config) throws ServletException {
		FileItemFactory factory = new DiskFileItemFactory();// Create a factory
		this.upload = new ServletFileUpload(factory);// Create a new file upload
		this.upload.setSizeMax(this.MAXSize);// Set overall request size
		filedir = config.getServletContext().getRealPath("upload");
		System.out.println("filedir=" + filedir);
		lostDao = new MessageDao();
	}
	
	private void updateOrderMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String orderId = request.getParameter("orderId");

		List<Object> params = new ArrayList<Object>();
		params.add("2");
		params.add(orderId);
		boolean flag = lostDao.updateOrder(params);

		if (flag) {
			listShopOrderPcMessage(request, response);

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "更新失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
		}

	}
	private void updateShop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String shopId = request.getParameter("shopId");
		
		final long MAX_SIZE = 2048 * 1024 * 1024;// 设置上传文件最大值为2G，可以改为更大
		// 表单含有文件要提交
		String path = request.getContextPath();
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		// 构建一个文件上传类
		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		// servletFileUpload.setFileSizeMax(3 * 1024 * 1024);
		servletFileUpload.setSizeMax(MAX_SIZE);// 上传文件总大小
		List<FileItem> list = null;
		List<Object> params = new ArrayList<Object>();
		try {
			// 解析request的请求
			list = servletFileUpload.parseRequest(request);
			// 取出所有表单的值:判断非文本字段和文本字段
			for (FileItem fileItem : list) {
				if (fileItem.isFormField()) {
					if (fileItem.getFieldName().equals("shopName")) {

						params.add(fileItem.getString("utf-8"));
						System.out.println(fileItem.getString("utf-8"));

					}
					
					if (fileItem.getFieldName().equals("typeMessage")) {

						params.add(fileItem.getString("utf-8").split(",")[0]);
						params.add(fileItem.getString("utf-8").split(",")[1]);
						System.out.println(fileItem.getString("utf-8"));
					}

					if (fileItem.getFieldName().equals("shopMoney")) {
						params.add(fileItem.getString("utf-8"));
						System.out.println(fileItem.getString("utf-8"));

					}
					
					if (fileItem.getFieldName().equals("shopNumber")) {
						params.add(fileItem.getString("utf-8"));
						System.out.println(fileItem.getString("utf-8"));

					}


					if (fileItem.getFieldName().equals("shopMessage")) {

						params.add(fileItem.getString("utf-8"));
						System.out.println(fileItem.getString("utf-8"));
					}

				} else {
					try {

						String image = fileItem.getName();
						String imageload = PingYinUtil.getPingYin(image);
						System.out.println("image111--->>" + imageload);
						params.add(imageload);
						String upload_path = request.getRealPath("/upload");
						System.out.println("--->>" + upload_path);
						String imgPath = Consts.imgPath;
						File real_path = new File(imgPath + "/" + imageload);
						fileItem.write(real_path);

						// 把数据插入到数据库中
					} catch (Exception e) {
						e.printStackTrace();
					}

				

				}
			}
			
			
			System.out.println("shopId:"+shopId);
			params.add(shopId);
			boolean flag = lostDao.updateShop(params);

			if (flag) {
				ListShopPCMessage(request, response);
			} else {
				System.out.println("flag:no");
			}
			
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void queryShop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String shopId = request.getParameter("shopId");
		List<Object> params = new ArrayList<Object>();
		params.add(shopId);
		Map<String, Object> map = lostDao.queryShop(params);
		List<Map<String, Object>> list = lostDao.listDepartmentMessage();
		request.setAttribute("map", map);
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../updateShop.jsp").forward(request, response);
	}
	private void updateType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String typeName = request.getParameter("typeName");
		String typeId = request.getParameter("typeId");

		List<Object> params = new ArrayList<Object>();
		params.add(typeName);
		params.add(typeId);
		boolean flag = lostDao.updateType(params);

		if (flag) {
			listTypeMessage(request, response);

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "更新失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
		}

	}
	private void queryType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String typeId = request.getParameter("typeId");
		List<Object> params = new ArrayList<Object>();
		params.add(typeId);
		Map<String, Object> list = lostDao.queryType(params);
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../updateType.jsp").forward(request, response);
	}
	
	private void listShopOrderPcMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		List<Map<String, Object>> list = lostDao.listShopOrderPcMessage();
		System.out.println(list);
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../orderMessage.jsp").forward(request, response);

	}

	private void deleteShop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String shopId = request.getParameter("shopId");
		List<Object> params = new ArrayList<Object>();
		params.add(shopId);
		boolean flag = lostDao.deleteShop(params);

		if (flag) {
			ListShopPCMessage(request, response);

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "删除失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

	}

	private void updateState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String shopState = request.getParameter("shopState");
		String shopId = request.getParameter("shopId");
		List<Object> params = new ArrayList<Object>();
		params.add(shopState);
		params.add(shopId);
		boolean flag = lostDao.updateState(params);
		if (flag) {

			ListShopPCMessage(request, response);

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "更新失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

	}

	private void listRecommendMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String searchMsg = request.getParameter("searchMsg");
		String userId = request.getParameter("userId");

		if (lostDao.CheckSearchCase(searchMsg, userId)) {

			Map<String, Object> userInfor = lostDao.queryUserInfor(userId);
			String userSearchInfor = userInfor.get("usearch").toString();
			String searchInfor = "";

			for (int i = 0; i < userSearchInfor.split(",").length; i++) {
				searchInfor = searchInfor + " shopName like '%" + userSearchInfor.split(",")[i] + "%' or";
			}

			List<Map<String, Object>> list = lostDao.listRecommendMessage(searchInfor.substring(0, searchInfor.length() - 2));
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "请求成功");
			jsonmsg.put("repCode", "666");
			jsonmsg.put("data", list);
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端

		} else {

			Map<String, Object> userInfor = lostDao.queryUserInfor(userId);
			List<Object> params = new ArrayList<Object>();

			if (userInfor.get("usearch").toString().length() == 0) {
				params.add(searchMsg);
			} else {
				params.add(userInfor.get("usearch").toString() + "," + searchMsg);
			}

			params.add(userId);

			boolean flagSearch = lostDao.updateSearch(params);
			System.out.println(flagSearch);
			if (flagSearch) {

				String userSearchInfor = userInfor.get("usearch").toString() + "," + searchMsg;
				String searchInfor = "";

				for (int i = 0; i < userSearchInfor.split(",").length; i++) {
					searchInfor = searchInfor + " shopName like '%" + userSearchInfor.split(",")[i] + "%' or";
				}
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				System.out.println(searchInfor);

				if (searchInfor.length() == 0) {
					list = lostDao.listRecommendMessage("shopName like 'no' ");
				} else {
					list = lostDao.listRecommendMessage(searchInfor.substring(0, searchInfor.length() - 2));
				}
				JSONObject jsonmsg = new JSONObject();
				jsonmsg.put("repMsg", "请求成功");
				jsonmsg.put("repCode", "666");
				jsonmsg.put("data", list);
				System.out.println(jsonmsg);
				response.getWriter().print(jsonmsg);// 将路径返回给客户端
			}
		}

	}

	private void listJiJianPCMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String jijianUserId = request.getParameter("jijianUserId");
		List<Object> params = new ArrayList<Object>();
		params.add(jijianUserId);
		List<Map<String, Object>> flagFood = lostDao.listJiJianPCMessage(params);

		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", flagFood);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addJiJian(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String jijianName = request.getParameter("jijianName");
		String jijianAddresse = request.getParameter("jijianAddresse");
		String jijianPhone = request.getParameter("jijianPhone");
		String jijianUserId = request.getParameter("jijianUserId");

		List<Object> params = new ArrayList<Object>();
		params.add(jijianName);
		params.add(jijianAddresse);
		params.add(jijianPhone);
		params.add(jijianUserId);

		boolean flag = lostDao.addJiJian(params);

		if (flag) {

			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "提交成功");
			jsonmsg.put("repCode", "666");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "签到失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

	}

	private void listCarPhone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String carUserId = request.getParameter("carUserId");
		List<Object> params = new ArrayList<Object>();
		params.add(carUserId);
		List<Map<String, Object>> flagFood = lostDao.listCarPhone(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", flagFood);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addCar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String carShopId = request.getParameter("carShopId");
		String carUserId = request.getParameter("carUserId");
		List<Object> params = new ArrayList<Object>();
		params.add(carShopId);
		params.add(carUserId);

		boolean flagCheck = lostDao.CheckBookCase(params);

		if (flagCheck) {

			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "添加购物车成功");
			jsonmsg.put("repCode", "666");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		} else {

			boolean flag = lostDao.addCar(params);

			if (flag) {
				JSONObject jsonmsg = new JSONObject();
				jsonmsg.put("repMsg", "添加购物车成功");
				jsonmsg.put("repCode", "666");
				System.out.println(jsonmsg);
				response.getWriter().print(jsonmsg);// 将路径返回给客户端

			} else {
				JSONObject jsonmsg = new JSONObject();
				jsonmsg.put("repMsg", "提交失败");
				jsonmsg.put("repCode", "111");
				System.out.println(jsonmsg);
				response.getWriter().print(jsonmsg);// 将路径返回给客户端
			}

		}

	}

	private void UpdateHot(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String isHot = request.getParameter("isHot");
		String shopId = request.getParameter("shopId");
		System.out.println(shopId);

		List<Object> params = new ArrayList<Object>();
		params.add(isHot);
		params.add(shopId);
		boolean flag = lostDao.UpdateHot(params);

		if (flag) {
			ListShopPCMessage(request, response);
		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "更新失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

	}

	private void listUserMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> listUser = lostDao.listUserMessage();
		request.setAttribute("listMessage", listUser);
		request.getRequestDispatcher("../dianMessage.jsp").forward(request, response);

	}

	private void ListShopPCMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		// 已经进行分页之后的数据集合
		List<Map<String, Object>> list = lostDao.ListShopPCMessage();
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../shopMessage.jsp").forward(request, response);

	}

	private void addShopPC(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final long MAX_SIZE = 2048 * 1024 * 1024;// 设置上传文件最大值为2G，可以改为更大
		// 表单含有文件要提交
		String path = request.getContextPath();
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		// 构建一个文件上传类
		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		// servletFileUpload.setFileSizeMax(3 * 1024 * 1024);
		servletFileUpload.setSizeMax(MAX_SIZE);// 上传文件总大小
		List<FileItem> list = null;
		List<Object> params = new ArrayList<Object>();
		try {
			// 解析request的请求
			list = servletFileUpload.parseRequest(request);
			// 取出所有表单的值:判断非文本字段和文本字段
			for (FileItem fileItem : list) {
				if (fileItem.isFormField()) {
					if (fileItem.getFieldName().equals("shopName")) {

						params.add(fileItem.getString("utf-8"));
						System.out.println(fileItem.getString("utf-8"));

					}
					
					
					if (fileItem.getFieldName().equals("typeMessage")) {

						params.add(fileItem.getString("utf-8").split(",")[0]);
						params.add(fileItem.getString("utf-8").split(",")[1]);

					}

					if (fileItem.getFieldName().equals("shopMoney")) {

						params.add(fileItem.getString("utf-8"));
						System.out.println(fileItem.getString("utf-8"));

					}

					if (fileItem.getFieldName().equals("shopNumber")) {

						params.add(fileItem.getString("utf-8"));
						System.out.println(fileItem.getString("utf-8"));

					}

					if (fileItem.getFieldName().equals("shopMessage")) {

						params.add(fileItem.getString("utf-8"));

					}

				} else {
					try {

						String image = fileItem.getName();
						String imageload = PingYinUtil.getPingYin(image);
						System.out.println("image111--->>" + imageload);
						params.add(imageload);
						String upload_path = request.getRealPath("/upload");
						System.out.println("--->>" + upload_path);
						String imgPath = Consts.imgPath;
						File real_path = new File(imgPath + "/" + imageload);
						fileItem.write(real_path);

						// 把数据插入到数据库中
					} catch (Exception e) {
						e.printStackTrace();
					}

					boolean flag = lostDao.addShopPC(params);

					if (flag) {
						ListShopPCMessage(request, response);
					} else {
						System.out.println("flag:no");
					}

				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void listTypeChoiceMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = lostDao.listTypeMessage();
		List<Map<String, Object>> listUser = lostDao.listUserMessage();
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../formShop.jsp").forward(request, response);

	}

	private void listTypeShopMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String shopTypeId = request.getParameter("shopTypeId");
		List<Object> params = new ArrayList<Object>();
		params.add(shopTypeId);

		List<Map<String, Object>> list = lostDao.listTypeShopMessage(params);
		// 生成json字符串
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void listSearchMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String searchMsg = request.getParameter("searchMsg");

		List<Map<String, Object>> list = lostDao.listSearchMessage(searchMsg);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void listReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String shopId = request.getParameter("shopId");
		List<Map<String, Object>> list = lostDao.listPhoneReview(shopId);
		// 生成json字符串
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void updateShopStateMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String shopRecycling = request.getParameter("shopRecycling");
		String shopId = request.getParameter("shopId");
		List<Object> params = new ArrayList<Object>();
		params.add(shopRecycling);
		params.add(shopId);
		boolean flag = lostDao.updateShopStateMessage(params);

		if (flag) {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "处理成功");
			jsonmsg.put("repCode", "666");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "更新失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

	}

	private void updateExchangeMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String exchangeState = request.getParameter("exchangeState");
		String exchangeId = request.getParameter("exchangeId");
		List<Object> params = new ArrayList<Object>();
		params.add(exchangeState);
		params.add(exchangeId);
		boolean flag = lostDao.updateExchangeMessage(params);

		if (flag) {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "处理成功");
			jsonmsg.put("repCode", "666");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "更新失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

	}

	private void listPhoneIMExchange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String userId = request.getParameter("userId");
		List<Object> params = new ArrayList<Object>();
		params.add(userId);

		List<Map<String, Object>> list = lostDao.listPhoneIMExchange(params);
		// 生成json字符串
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void listPhoneExchange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String userId = request.getParameter("userId");
		List<Object> params = new ArrayList<Object>();
		params.add(userId);

		List<Map<String, Object>> list = lostDao.listPhoneExchange(params);
		// 生成json字符串
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addExchange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String exchangeShopId = request.getParameter("exchangeShopId");
		String exchangeShopUserId = request.getParameter("exchangeShopUserId");
		String exchangeMyShopId = request.getParameter("exchangeMyShopId");
		String exchangeMyUserId = request.getParameter("exchangeMyUserId");

		List<Object> params = new ArrayList<Object>();
		params.add(exchangeShopId);
		params.add(exchangeShopUserId);
		params.add(exchangeMyShopId);
		params.add(exchangeMyUserId);
		params.add("1");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		params.add(df.format(new Date()));
		boolean flag = lostDao.addExchange(params);
		if (flag) {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "交换申请已经提交");
			jsonmsg.put("repCode", "666");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "添加失败");
			jsonmsg.put("repCode", "111");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		}

	}

	private void reviewListMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String reviewMessageId = request.getParameter("reviewMessageId");
		System.out.println(reviewMessageId);
		List<Object> params = new ArrayList<Object>();
		params.add(reviewMessageId);
		List<Map<String, Object>> flagFood = lostDao.reviewListMessage(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", flagFood);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String reviewMessageId = request.getParameter("reviewMessageId");
		String reviewContent = request.getParameter("reviewContent");
		String reviewUserId = request.getParameter("reviewUserId");
		String reviewUserName = request.getParameter("reviewUserName");

		System.out.println(reviewMessageId);

		List<Object> params = new ArrayList<Object>();
		params.add(reviewMessageId);
		params.add(reviewContent);
		params.add(reviewUserId);
		params.add(reviewUserName);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		params.add(df.format(new Date()));
		boolean flag = lostDao.addReview(params);

		if (flag) {

			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "评论成功");
			jsonmsg.put("repCode", "666");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "评论失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
		}

	}

	private void updateStateMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String shopIsSend = request.getParameter("shopIsSend");
		String shopId = request.getParameter("shopId");
		List<Object> params = new ArrayList<Object>();
		params.add(shopIsSend);
		params.add(shopId);
		boolean flag = lostDao.updateGreensStateMessage(params);

		if (flag) {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "处理成功");
			jsonmsg.put("repCode", "666");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "更新失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

	}

	private void listShopPhoneUserMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String shopUserId = request.getParameter("shopUserId");
		List<Object> params = new ArrayList<Object>();
		params.add(shopUserId);

		List<Map<String, Object>> list = lostDao.listShopPhoneUserMessage(params);
		// 生成json字符串
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void listPricePhoneMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String priceShopId = request.getParameter("priceShopId");
		List<Object> params = new ArrayList<Object>();
		params.add(priceShopId);
		List<Map<String, Object>> list = lostDao.listPricePhoneMessage(params);
		// 生成json字符串
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addPrice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String priceMoney = request.getParameter("priceMoney");
		String priceShopId = request.getParameter("priceShopId");
		String priceShopName = request.getParameter("priceShopName");
		String priceUserId = request.getParameter("priceUserId");
		String priceUserName = request.getParameter("priceUserName");
		List<Object> params = new ArrayList<Object>();
		params.add(priceMoney);
		params.add(priceShopId);
		params.add(priceShopName);
		params.add(priceUserId);
		params.add(priceUserName);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		params.add(df.format(new Date()));
		boolean flag = lostDao.addPrice(params);
		if (flag) {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "竞价成功");
			jsonmsg.put("repCode", "666");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "添加失败");
			jsonmsg.put("repCode", "111");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		}

	}

	private void listMyOrderPhoneMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String orderUserId = request.getParameter("orderUserId");
		List<Object> params = new ArrayList<Object>();
		params.add(orderUserId);
		List<Map<String, Object>> list = lostDao.listMyOrderPhoneMessage(params);
		// 生成json字符串
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String orderMessageId = request.getParameter("orderMessageId");
		String orderMessageMoney = request.getParameter("orderMessageMoney");
		String orderAddress = request.getParameter("orderAddress");
		String orderUserId = request.getParameter("orderUserId");
		String orderUserName = request.getParameter("orderUserName");
		String orderRemark = request.getParameter("orderRemark");
		
		List<Object> params = new ArrayList<Object>();
		params.add(orderMessageId);
		params.add(orderMessageMoney);
		params.add(orderUserId);
		params.add(orderUserName);
		params.add(orderAddress);

		System.out.println(orderMessageId);
		System.out.println(orderMessageMoney);
		System.out.println(orderUserId);
		System.out.println(orderUserName);
		System.out.println(orderAddress);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		params.add(df.format(new Date()));

		SimpleDateFormat dfInfor = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		params.add("NO" + dfInfor.format(new Date()));
		params.add("1");
		params.add(orderRemark);
		
		boolean flag = lostDao.addOrder(params);
		if (flag) {

			for (int i = 0; i < orderMessageId.split(",").length; i++) {

				List<Object> paramsDelete = new ArrayList<Object>();
				paramsDelete.add(orderMessageId.split(",")[i]);
				paramsDelete.add(orderUserId);

				lostDao.deleteCar(paramsDelete);
				
				
				Map<String, Object> shopQuery = lostDao.queryShopInfor(orderMessageId.split(",")[i]);
				int musicNumber = Integer.valueOf(shopQuery.get("shopNumber").toString());

				List<Object> paramsUpate = new ArrayList<Object>();
				paramsUpate.add((musicNumber - 1) + "");
				paramsUpate.add(orderMessageId.split(",")[i]);

				boolean flagUpate = lostDao.updateShopNumber(paramsUpate);
				
			}
			
			
		

				JSONObject jsonmsg = new JSONObject();
				jsonmsg.put("repMsg", "购买成功");
				jsonmsg.put("repCode", "666");
				response.getWriter().print(jsonmsg);// 将路径返回给客户端
				System.out.println(jsonmsg);
			
			

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "添加失败");
			jsonmsg.put("repCode", "111");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		}

	}

	private void listShopPhoneMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String shopCity = request.getParameter("shopCity");

		List<Map<String, Object>> listShop = lostDao.listShopmMainPhoneMessage();
		List<Map<String, Object>> listTuIjian = lostDao.listShopmTuiJianMessage();
		List<Map<String, Object>> listtype = lostDao.listTypeMessage();
		// 生成json字符串
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("listShop", listShop);
		map.put("listTuIjian", listTuIjian);

		jsonmsg.put("data", map);

		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addShop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String shopName = request.getParameter("shopName");
		String shopMoney = request.getParameter("shopMoney");
		String shopMessage = request.getParameter("shopMessage");
		String shopIsIm = request.getParameter("shopIsIm");

		String shopTypeId = request.getParameter("shopTypeId");
		String shopTypeName = request.getParameter("shopTypeName");

		String shopImg = request.getParameter("shopImg");
		String shopUserId = request.getParameter("shopUserId");
		String shopUserName = request.getParameter("shopUserName");

		String shopCity = request.getParameter("shopCity");
		String shopAddress = request.getParameter("shopAddress");

		String imagePath = null;
		if (shopName == null) {
			try {
				List<FileItem> items = this.upload.parseRequest(request);
				if (items != null && !items.isEmpty()) {
					for (FileItem fileItem : items) {
						String filename = fileItem.getName();

						System.out.println("文件保存路径为:" + Consts.imgPath + "/" + filename);
						File real_path = new File(Consts.imgPath + "/" + filename);
						InputStream inputSteam = fileItem.getInputStream();
						BufferedInputStream fis = new BufferedInputStream(inputSteam);
						FileOutputStream fos = new FileOutputStream(real_path);
						int f;
						while ((f = fis.read()) != -1) {
							fos.write(f);
						}
						fos.flush();
						fos.close();
						fis.close();
						inputSteam.close();
						System.out.println("文件：" + filename + "上传成功!");
						imagePath = filename;
					}
				}

			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		} else {

			List<Object> params = new ArrayList<Object>();
			params.add(shopName + "");
			params.add(shopMoney + "");
			params.add(shopMessage + "");
			params.add(shopTypeId + "");
			params.add(shopTypeName + "");

			params.add(shopImg + "");
			params.add(shopUserId + "");
			params.add(shopUserName + "");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
			params.add(df.format(new Date()));
			params.add(shopCity);
			params.add(shopAddress);
			boolean flag = lostDao.addShop(params);
			System.out.println(flag + "");
			if (flag) {
				JSONObject jsonmsg = new JSONObject();
				jsonmsg.put("repMsg", "提交成功");
				jsonmsg.put("repCode", "666");
				response.getWriter().print(jsonmsg);// 将路径返回给客户端
			} else {
				JSONObject jsonmsg = new JSONObject();
				jsonmsg.put("repMsg", "上传文件失败");
				jsonmsg.put("repCode", "111");
				response.getWriter().print(jsonmsg);// 将路径返回给客户端
			}
		}

	}

	private void deleteBR(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String businessReportId = request.getParameter("businessReportId");
		List<Object> params = new ArrayList<Object>();
		params.add(businessReportId);
		boolean flag = lostDao.deleteBR(params);

		if (flag) {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "删除成功");
			jsonmsg.put("repCode", "666");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "删除失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

	}

	private void listPhoneLeadReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = lostDao.listPhoneLeadReport();
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addLead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String employeesNo = request.getParameter("employeesNo");
		String employeesName = request.getParameter("employeesName");
		String employeesIdCard = request.getParameter("employeesIdCard");
		String employeesPhone = request.getParameter("employeesPhone");
		String employeesPosition = request.getParameter("employeesPosition");
		List<Object> params = new ArrayList<Object>();
		params.add(employeesNo);
		params.add(employeesName);
		params.add(employeesIdCard);
		params.add(employeesPhone);
		params.add(employeesPosition);
		params.add("123456");

		params.add("0");
		params.add("领导组");

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		params.add(df.format(new Date()));
		params.add("2");

		List<Object> paramsCheckIdcard = new ArrayList<Object>();
		paramsCheckIdcard.add(employeesIdCard);
		if (lostDao.employCheckIdcard(paramsCheckIdcard)) {
			PrintWriter out = response.getWriter(); // 初始化out对象
			out.print("<script language='javascript'>alert('身份证号码已经存在!');window.location.href='../servlet/MessageAction?action_flag=listChoiceDepartment';</script>");
			return;
		}

		List<Object> paramsCheckPhone = new ArrayList<Object>();
		paramsCheckPhone.add(employeesPhone);
		if (lostDao.employCheckPhone(paramsCheckPhone)) {
			PrintWriter out = response.getWriter(); // 初始化out对象
			out.print("<script language='javascript'>alert('手机号码已经存在!');window.location.href='../servlet/MessageAction?action_flag=listChoiceDepartment';</script>");
			return;
		}

		boolean flag = lostDao.addEmploy(params);

		if (flag) {

			List<Object> paramsPhone = new ArrayList<Object>();
			paramsPhone.add(employeesPhone);

			try {
				// 获取注册用户的信息
			} catch (Exception e) {
				e.printStackTrace();
			}

			listEmployMessage(request, response);
		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "提交失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			// response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

	}

	private void listPhoneFalutReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = lostDao.listPhoneFalutReport();
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addFalutReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String falutDepartment = request.getParameter("falutDepartment");
		String falutDate = request.getParameter("falutDate");
		String falutPerson = request.getParameter("falutPerson");
		String falutName = request.getParameter("falutName");
		String falutIsOrNo = request.getParameter("falutIsOrNo");
		String falutNote = request.getParameter("falutNote");
		String falutCoordinates = request.getParameter("falutCoordinates");
		String falutAddress = request.getParameter("falutAddress");
		String falutUserId = request.getParameter("falutUserId");
		String falutUserName = request.getParameter("falutUserName");

		List<Object> params = new ArrayList<Object>();
		params.add(falutDepartment);
		params.add(falutDate);
		params.add(falutPerson);
		params.add(falutName);
		params.add(falutIsOrNo);
		params.add(falutNote);
		params.add(falutCoordinates);
		params.add(falutAddress);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		params.add(df.format(new Date()));
		params.add(falutUserId);
		params.add(falutUserName);

		boolean flag = lostDao.addFalutReport(params);

		if (flag) {

			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "提交成功");
			jsonmsg.put("repCode", "666");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			// response.sendRedirect(path +
			// "/servlet/NoticeAction?action_flag=listMessage");

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "提交失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			// response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

	}

	private void listPhoneBusinessReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = lostDao.listPhoneBusinessReport();
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addBusinessReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String businessReportDepartment = request.getParameter("businessReportDepartment");
		String businessReportDate = request.getParameter("businessReportDate");
		String businessReportPerson = request.getParameter("businessReportPerson");
		String businessReportDay = request.getParameter("businessReportDay");
		String businessReportMonth = request.getParameter("businessReportMonth");
		String businessReportYear = request.getParameter("businessReportYear");
		String businessReportTemperature = request.getParameter("businessReportTemperature");
		String businessReportPressures = request.getParameter("businessReportPressures");
		String businessReportSales = request.getParameter("businessReportSales");
		String businessReportUserId = request.getParameter("businessReportUserId");
		String businessReportUserName = request.getParameter("businessReportUserName");
		List<Object> params = new ArrayList<Object>();
		params.add(businessReportDepartment);
		params.add(businessReportDate);
		params.add(businessReportPerson);
		params.add(businessReportDay);
		params.add(businessReportMonth);
		params.add(businessReportYear);
		params.add(businessReportTemperature);
		params.add(businessReportPressures);
		params.add(businessReportSales);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		params.add(df.format(new Date()));
		params.add(businessReportUserId);
		params.add(businessReportUserName);

		boolean flag = lostDao.addContact(params);

		if (flag) {

			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "提交成功");
			jsonmsg.put("repCode", "666");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			// response.sendRedirect(path +
			// "/servlet/NoticeAction?action_flag=listMessage");

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "提交失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			// response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

	}

	private void listPhoneEmployLoadIm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = lostDao.listEmployMessage();
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void listPhoneEmployClass(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = lostDao.listPhoneEmployClass();
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端
	}

	private void deleteEmploy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String employeesId = request.getParameter("employeesId");

		System.out.println(employeesId);
		List<Object> params = new ArrayList<Object>();
		params.add(employeesId);
		boolean flag = lostDao.deleteEmploy(params);
		if (flag) {
			listEmployMessage(request, response);
		} else {
			System.out.println("失败了");
		}

	}

	private void listEmployMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = lostDao.listEmployMessage();
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../static/system/employees_info.jsp").forward(request, response);

	}

	private void addEmploy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String subMsg = request.getParameter("subMsg");
		String employeesNo = request.getParameter("employeesNo");
		String employeesName = request.getParameter("employeesName");
		String employeesIdCard = request.getParameter("employeesIdCard");
		String employeesPhone = request.getParameter("employeesPhone");
		String employeesPosition = request.getParameter("employeesPosition");
		List<Object> params = new ArrayList<Object>();
		params.add(employeesNo);
		params.add(employeesName);
		params.add(employeesIdCard);
		params.add(employeesPhone);
		params.add(employeesPosition);
		params.add("123456");

		params.add(subMsg.split(",")[0]);
		params.add(subMsg.split(",")[1]);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		params.add(df.format(new Date()));
		params.add("1");
		List<Object> paramsCheckIdcard = new ArrayList<Object>();
		paramsCheckIdcard.add(employeesIdCard);
		if (lostDao.employCheckIdcard(paramsCheckIdcard)) {
			PrintWriter out = response.getWriter(); // 初始化out对象
			out.print("<script language='javascript'>alert('身份证号码已经存在!');window.location.href='../servlet/MessageAction?action_flag=listChoiceDepartment';</script>");
			return;
		}

		List<Object> paramsCheckPhone = new ArrayList<Object>();
		paramsCheckPhone.add(employeesPhone);
		if (lostDao.employCheckPhone(paramsCheckPhone)) {
			PrintWriter out = response.getWriter(); // 初始化out对象
			out.print("<script language='javascript'>alert('手机号码已经存在!');window.location.href='../servlet/MessageAction?action_flag=listChoiceDepartment';</script>");
			return;
		}

		boolean flag = lostDao.addEmploy(params);

		if (flag) {

			List<Object> paramsPhone = new ArrayList<Object>();
			paramsPhone.add(employeesPhone);

			try {
				// 获取注册用户的信息
			} catch (Exception e) {
				e.printStackTrace();
			}

			listEmployMessage(request, response);
		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "提交失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			// response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

	}

	private void listChoiceDepartment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		// 已经进行分页之后的数据集合
		List<Map<String, Object>> list = lostDao.listDepartmentMessage();
	
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../formShop.jsp").forward(request, response);

	}

	private void listDepartmentMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = lostDao.listDepartmentMessage();
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../static/system/department_info.jsp").forward(request, response);

	}

	private void addDepartment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String departmentName = request.getParameter("departmentName");
		List<Object> params = new ArrayList<Object>();
		params.add(departmentName);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		params.add(df.format(new Date()));
		boolean flag = lostDao.addDepartment(params);
		if (flag) {
			listDepartmentMessage(request, response);
		}

	}

	private void listTypeMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = lostDao.listTypeMessage();
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../typeMessage.jsp").forward(request, response);

	}

	private void deleteType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String userId = request.getParameter("typeId");

		System.out.println(userId);
		List<Object> params = new ArrayList<Object>();
		params.add(userId);
		boolean flag = lostDao.deleteType(params);
		if (flag) {
			listTypeMessage(request, response);
		} else {
			System.out.println("失败了");
		}

	}

	private void listTypePhoneMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = lostDao.listTypeMessage();
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端
	}

	private void addType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String typeName = request.getParameter("typeName");
		System.out.println(typeName);
		List<Object> params = new ArrayList<Object>();
		params.add(typeName);
		boolean flag = lostDao.addType(params);

		if (flag) {
			listTypeMessage(request, response);

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "提交失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			// response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

	}

	private void QuanZiMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String circleId = request.getParameter("circleId");
		List<Object> params = new ArrayList<Object>();
		params.add(circleId);
		List<Map<String, Object>> flagFood = lostDao.circlemsgtb(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", flagFood);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addQuanZiMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String circleId = request.getParameter("circleId");
		String circlemsgMessage = request.getParameter("circlemsgMessage");
		String circlemsgImg = request.getParameter("circlemsgImg");
		String circlemsgUserId = request.getParameter("circlemsgUserId");
		String circlemsgUserName = request.getParameter("circlemsgUserName");

		String imagePath = null;
		if (circleId == null) {
			try {
				List<FileItem> items = this.upload.parseRequest(request);
				if (items != null && !items.isEmpty()) {
					for (FileItem fileItem : items) {
						String filename = fileItem.getName();

						System.out.println("文件保存路径为:" + Consts.imgPath + "/" + filename);
						File real_path = new File(Consts.imgPath + "/" + filename);
						InputStream inputSteam = fileItem.getInputStream();
						BufferedInputStream fis = new BufferedInputStream(inputSteam);
						FileOutputStream fos = new FileOutputStream(real_path);
						int f;
						while ((f = fis.read()) != -1) {
							fos.write(f);
						}
						fos.flush();
						fos.close();
						fis.close();
						inputSteam.close();
						System.out.println("文件：" + filename + "上传成功!");
						imagePath = filename;
					}
				}

			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		} else {

			List<Object> params = new ArrayList<Object>();
			params.add(circleId + "");
			params.add(circlemsgMessage + "");
			params.add(circlemsgImg + "");
			params.add(circlemsgUserId + "");
			params.add(circlemsgUserName + "");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
			params.add(df.format(new Date()));
			boolean flag = lostDao.addQuanZiMessage(params);
			System.out.println(flag + "");
			if (flag) {
				JSONObject jsonmsg = new JSONObject();
				jsonmsg.put("repMsg", "提交成功");
				jsonmsg.put("repCode", "666");
				response.getWriter().print(jsonmsg);// 将路径返回给客户端
			} else {
				JSONObject jsonmsg = new JSONObject();
				jsonmsg.put("repMsg", "上传文件失败");
				jsonmsg.put("repCode", "111");
				response.getWriter().print(jsonmsg);// 将路径返回给客户端
			}
		}

	}

	private void listPhonePlanUserMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String applyPlanId = request.getParameter("applyPlanId");
		List<Object> params = new ArrayList<Object>();
		params.add(applyPlanId);
		List<Map<String, Object>> flagFood = lostDao.listPhonePlanUserMessage(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", flagFood);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addApply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String applyUserId = request.getParameter("applyUserId");
		String applyUserName = request.getParameter("applyUserName");
		String applyPlanId = request.getParameter("applyPlanId");
		String applyPlanName = request.getParameter("applyPlanName");

		List<Object> paramsCheck = new ArrayList<Object>();
		paramsCheck.add(applyPlanId);
		paramsCheck.add(applyUserId);

		boolean flagCheck = lostDao.checkPlan(paramsCheck);

		if (!flagCheck) {
			List<Object> params = new ArrayList<Object>();
			params.add(applyUserId);
			params.add(applyUserName);
			params.add(applyPlanId);
			params.add(applyPlanName);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
			params.add(df.format(new Date()));

			boolean flag = lostDao.addApply(params);
			JSONObject jsonmsg = new JSONObject();
			if (flag) {
				jsonmsg.put("repMsg", "参与成功");
				jsonmsg.put("repCode", "666");
				response.getWriter().print(jsonmsg);// 将路径返回给客户端
				System.out.println(jsonmsg);
			} else {
				jsonmsg.put("repMsg", "提交失败");
				jsonmsg.put("repCode", "111");
				response.getWriter().print(jsonmsg);// 将路径返回给客户端
				System.out.println(jsonmsg);
			}
		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "亲，不要重复参与哦！");
			jsonmsg.put("repCode", "1111");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		}

	}

	private void listActivityMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Map<String, Object>> list = lostDao.listActivityMessage();
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addActivityMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String activityName = request.getParameter("activityName");
		String activityNumber = request.getParameter("activityNumber");
		String activityAddress = request.getParameter("activityAddress");
		String activityMoney = request.getParameter("activityMoney");
		String activityYear = request.getParameter("activityYear");
		String activityUserId = request.getParameter("activityUserId");
		String activityUserName = request.getParameter("activityUserName");
		System.out.println("----------");
		List<Object> params = new ArrayList<Object>();

		params.add(activityName);
		params.add(activityNumber);
		params.add(activityAddress);
		params.add(activityMoney);
		params.add(activityYear);
		params.add(activityUserId);
		params.add(activityUserName);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		params.add(df.format(new Date()));

		boolean flag = lostDao.addActivityMessage(params);

		if (flag) {

			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "提交成功");
			jsonmsg.put("repCode", "666");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			// response.sendRedirect(path +
			// "/servlet/NoticeAction?action_flag=listMessage");

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "提交失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			// response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

	}

	private void listOrderPhoneMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String orderId = request.getParameter("orderId");
		List<Object> params = new ArrayList<Object>();
		params.add(orderId);
		List<Map<String, Object>> flagFood = lostDao.listorderPhoneMesage(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", flagFood);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void ListShopMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Map<String, Object>> list = lostDao.greensMessage();
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void ListPhoneCircleMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Map<String, Object>> list = lostDao.ListCircleMessage();
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addCircleMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String circleName = request.getParameter("circleName");
		String circleMessgae = request.getParameter("circleMessgae");
		String circleUserId = request.getParameter("circleUserId");
		String circleUserName = request.getParameter("circleUserName");
		String circleAddress = request.getParameter("circleAddress");
		System.out.println("----------");
		List<Object> params = new ArrayList<Object>();

		params.add(circleName);
		params.add(circleMessgae);
		params.add(circleUserId);
		params.add(circleUserName);
		params.add(circleAddress);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		params.add(df.format(new Date()));

		boolean flag = lostDao.addCircleMessage(params);

		if (flag) {

			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "提交成功");
			jsonmsg.put("repCode", "666");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			// response.sendRedirect(path +
			// "/servlet/NoticeAction?action_flag=listMessage");

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "提交失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			// response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

	}

	private void greensThreeMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Map<String, Object>> list = lostDao.greensThreeMessage();
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void greensOneMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Map<String, Object>> list = lostDao.greensMessage();
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void greensMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String greensUserId = request.getParameter("greensUserId");
		List<Object> params = new ArrayList<Object>();
		params.add(greensUserId);

		List<Map<String, Object>> list = lostDao.greensMessage();
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void greensUserMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String greensUserId = request.getParameter("greensUserId");
		System.out.println("------------" + greensUserId);
		List<Object> params = new ArrayList<Object>();
		params.add(greensUserId);
		List<Map<String, Object>> list = lostDao.greensUSERMessage(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void updateGreensMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String greensId = request.getParameter("greensId");
		String greensResultTip = request.getParameter("greensResultTip");

		String greensResultCompanyName = request.getParameter("greensResultCompanyName");
		String greensResultCompanyMessage = request.getParameter("greensResultCompanyMessage");

		String greensResultImg = request.getParameter("greensResultImg");
		String greensResultUserName = request.getParameter("greensResultUserName");

		String imagePath = null;
		if (greensResultTip == null) {
			try {
				List<FileItem> items = this.upload.parseRequest(request);
				if (items != null && !items.isEmpty()) {
					for (FileItem fileItem : items) {
						String filename = fileItem.getName();

						System.out.println("文件保存路径为:" + Consts.imgPath + "/" + filename);
						File real_path = new File(Consts.imgPath + "/" + filename);
						InputStream inputSteam = fileItem.getInputStream();
						BufferedInputStream fis = new BufferedInputStream(inputSteam);
						FileOutputStream fos = new FileOutputStream(real_path);
						int f;
						while ((f = fis.read()) != -1) {
							fos.write(f);
						}
						fos.flush();
						fos.close();
						fis.close();
						inputSteam.close();
						System.out.println("文件：" + filename + "上传成功!");
						imagePath = filename;
					}
				}

			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		} else {

			List<Object> params = new ArrayList<Object>();
			params.add("2");
			params.add(greensResultTip + "");

			params.add(greensResultCompanyName + "");
			params.add(greensResultCompanyMessage + "");

			params.add(greensResultImg + "");
			params.add(greensResultUserName + "");

			params.add(greensId + "");
			boolean flag = lostDao.updateGreensMessage(params);
			System.out.println(flag + "");
			if (flag) {
				JSONObject jsonmsg = new JSONObject();
				jsonmsg.put("repMsg", "提交成功");
				jsonmsg.put("repCode", "666");
				response.getWriter().print(jsonmsg);// 将路径返回给客户端
			} else {
				JSONObject jsonmsg = new JSONObject();
				jsonmsg.put("repMsg", "上传文件失败");
				jsonmsg.put("repCode", "111");
				response.getWriter().print(jsonmsg);// 将路径返回给客户端
			}
		}

	}

	private void RecruitUserMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String recruitUserId = request.getParameter("recruitUserId");
		List<Object> params = new ArrayList<Object>();
		params.add(recruitUserId);

		List<Map<String, Object>> list = lostDao.recruitUserMessage(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void lostUserMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String lostUserId = request.getParameter("lostUserId");
		List<Object> params = new ArrayList<Object>();
		params.add(lostUserId);

		List<Map<String, Object>> list = lostDao.lostUserMessage(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void deleteRecruit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String recruitId = request.getParameter("recruitId");
		List<Object> params = new ArrayList<Object>();
		params.add(recruitId);
		boolean flag = lostDao.deleteRecruit(params);
		if (flag) {
			listRecruitPcMessage(request, response);
		} else {
		}

	}

	private void deleteLost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	private void listRecruitPcMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		// 已经进行分页之后的数据集合
		List<Map<String, Object>> list = lostDao.recruitMessage();
		// studentDao.listMessageTeacher(list);
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../recruitMessage.jsp").forward(request, response);

	}

	private void listLostPcMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		// 已经进行分页之后的数据集合
		List<Map<String, Object>> list = lostDao.lostMessage();
		// studentDao.listMessageTeacher(list);
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../lostMessage.jsp").forward(request, response);

	}

	private void RecruitMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Map<String, Object>> list = lostDao.recruitMessage();
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addRecruitMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String lostName = request.getParameter("lostName");
		String lostPhone = request.getParameter("lostPhone");
		String lostTime = request.getParameter("lostTime");
		String lostMessage = request.getParameter("lostMessage");
		String lostLongitude = request.getParameter("lostLongitude");
		String lostLatitude = request.getParameter("lostLatitude");
		String lostAddress = request.getParameter("lostAddress");
		String lostImage = request.getParameter("lostImage");
		String lostUserId = request.getParameter("lostUserId");
		String lostUserName = request.getParameter("lostUserName");

		String imagePath = null;
		if (lostUserName == null) {
			try {
				List<FileItem> items = this.upload.parseRequest(request);
				if (items != null && !items.isEmpty()) {
					for (FileItem fileItem : items) {
						String filename = fileItem.getName();
						// String filepath = filedir + File.separator +
						// filename;
						// String imgPath = Consts.imgPath;
						System.out.println("文件保存路径为:" + Consts.imgPath + "/" + filename);
						File real_path = new File(Consts.imgPath + "/" + filename);
						// File file = new File(filepath);
						InputStream inputSteam = fileItem.getInputStream();
						BufferedInputStream fis = new BufferedInputStream(inputSteam);
						FileOutputStream fos = new FileOutputStream(real_path);
						int f;
						while ((f = fis.read()) != -1) {
							fos.write(f);
						}
						fos.flush();
						fos.close();
						fis.close();
						inputSteam.close();
						System.out.println("文件：" + filename + "上传成功!");
						imagePath = filename;
					}
				}

			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		} else {

			List<Object> params = new ArrayList<Object>();
			params.add(lostName + "");
			params.add(lostPhone + "");
			params.add(lostTime + "");
			params.add(lostMessage + "");
			params.add(lostLongitude);
			params.add(lostLatitude);
			params.add(lostAddress);
			params.add(lostImage);
			params.add(lostUserId);
			params.add(lostUserName);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
			params.add(df.format(new Date()));

			boolean flag = lostDao.addRecruitMessage(params);
			System.out.println(flag + "");
			if (flag) {
				JSONObject jsonmsg = new JSONObject();
				jsonmsg.put("repMsg", "发布成功");
				jsonmsg.put("repCode", "666");
				response.getWriter().print(jsonmsg);// 将路径返回给客户端
			} else {
				JSONObject jsonmsg = new JSONObject();
				jsonmsg.put("repMsg", "上传文件失败");
				jsonmsg.put("repCode", "111");
				response.getWriter().print(jsonmsg);// 将路径返回给客户端
			}
		}

	}

	private void addGreensMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String shopName = request.getParameter("shopName");
		String shopMoney = request.getParameter("shopMoney");
		String shopType = request.getParameter("shopType");
		String shopMessage = request.getParameter("shopMessage");
		String shopImg = request.getParameter("shopImg");

		String shopUserId = request.getParameter("shopUserId");
		String shopUserName = request.getParameter("shopUserName");

		String imagePath = null;
		if (shopName == null) {
			try {
				List<FileItem> items = this.upload.parseRequest(request);
				if (items != null && !items.isEmpty()) {
					for (FileItem fileItem : items) {
						String filename = fileItem.getName();

						System.out.println("文件保存路径为:" + Consts.imgPath + "/" + filename);
						File real_path = new File(Consts.imgPath + "/" + filename);
						InputStream inputSteam = fileItem.getInputStream();
						BufferedInputStream fis = new BufferedInputStream(inputSteam);
						FileOutputStream fos = new FileOutputStream(real_path);
						int f;
						while ((f = fis.read()) != -1) {
							fos.write(f);
						}
						fos.flush();
						fos.close();
						fis.close();
						inputSteam.close();
						System.out.println("文件：" + filename + "上传成功!");
						imagePath = filename;
					}
				}

			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		} else {

			List<Object> params = new ArrayList<Object>();
			params.add(shopName + "");
			params.add(shopMoney + "");
			params.add(shopType + "");
			params.add(shopMessage + "");
			params.add(shopImg + "");

			params.add(shopUserId + "");
			params.add(shopUserName);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
			params.add(df.format(new Date()));
			params.add("1");
			boolean flag = lostDao.addGreensMessage(params);
			System.out.println(flag + "");
			if (flag) {
				JSONObject jsonmsg = new JSONObject();
				jsonmsg.put("repMsg", "提交成功");
				jsonmsg.put("repCode", "666");
				response.getWriter().print(jsonmsg);// 将路径返回给客户端
			} else {
				JSONObject jsonmsg = new JSONObject();
				jsonmsg.put("repMsg", "上传文件失败");
				jsonmsg.put("repCode", "111");
				response.getWriter().print(jsonmsg);// 将路径返回给客户端
			}
		}

	}

	private void ContactListMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String UserId = request.getParameter("UserId");
		List<Object> params = new ArrayList<Object>();
		params.add(UserId);
		List<Map<String, Object>> list = lostDao.listContactMessage(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void listPhoneUserMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String UserId = request.getParameter("UserId");
		List<Object> params = new ArrayList<Object>();
		params.add(UserId);
		List<Map<String, Object>> list = lostDao.listLostFaBuMessage(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void listPhoneMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Map<String, Object>> list = lostDao.listLostMessage();
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String choiceType = request.getParameter("choiceType");
		String lName = request.getParameter("lName");
		String lTime = request.getParameter("lTime");
		String lMessage = request.getParameter("lMessage");
		String lUserId = request.getParameter("lUserId");
		String lUserName = request.getParameter("lUserName");
		String lLongitude = request.getParameter("lLongitude");
		String lLatitude = request.getParameter("lLatitude");
		List<Object> params = new ArrayList<Object>();

		params.add(lName);
		params.add(lTime);
		params.add(lMessage);
		params.add(lUserId);
		params.add(lUserName);
		params.add(lLongitude);
		params.add(lLatitude);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		params.add(df.format(new Date()));
		System.out.println("lLatitude:" + lLatitude);
		System.out.println("lLongitude:" + lLongitude);
		params.add(choiceType);
		boolean flag = lostDao.addMessage(params);

		if (flag) {

			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "提交成功");
			jsonmsg.put("repCode", "666");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			// response.sendRedirect(path +
			// "/servlet/NoticeAction?action_flag=listMessage");

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "提交失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			// response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

	}

	private void addContact(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String cLId = request.getParameter("cLId");
		String cLUserId = request.getParameter("cLUserId");
		String cUserId = request.getParameter("cUserId");
		String cUserPhone = request.getParameter("cUserPhone");
		String cMessage = request.getParameter("cMessage");
		List<Object> params = new ArrayList<Object>();
		params.add(cLId);
		params.add(cLUserId);
		params.add(cUserId);
		params.add(cUserPhone);
		params.add(cMessage);

		boolean flag = lostDao.addContact(params);

		if (flag) {

			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "提交成功");
			jsonmsg.put("repCode", "666");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			// response.sendRedirect(path +
			// "/servlet/NoticeAction?action_flag=listMessage");

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "提交失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			// response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

	}
}
