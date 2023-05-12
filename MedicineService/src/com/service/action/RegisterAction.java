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
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import net.sf.json.JSONObject;

import com.service.config.Consts;
import com.service.dao.RegisterDao;

public class RegisterAction extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ServletFileUpload upload;
	private final long MAXSize = 4194304 * 2L;// 4*2MB
	private String filedir = null;
	private RegisterDao registerDao;

	public RegisterAction() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String action_flag = request.getParameter("action_flag");
		if (action_flag.equals("add")) {
			registerMessage(request, response);
		} else if (action_flag.equals("listUser")) {
			listUser(request, response);
		}else if (action_flag.equals("login")) {
			loginMessage(request, response);
		}else if (action_flag.equals("deleteUser")) {
			deleteUserPc(request, response);
		}else if (action_flag.equals("updateName")) {
			updateName(request, response);
		}else if (action_flag.equals("updatePhone")) {
			updatePhone(request, response);
		}else if (action_flag.equals("loginPc")) {
			login(request, response);
		}else if (action_flag.equals("updatePro")) {
			updatePro(request, response);
		}else if (action_flag.equals("updateEmail")) {
			updateEmail(request, response);
		}else if (action_flag.equals("updateSchool")) {
			updateSchool(request, response);
		}else if (action_flag.equals("updatePswd")) {
			updatePswd(request, response);
		} else if (action_flag.equals("updateImage")) {
			updateImage(request, response);
		}else if (action_flag.equals("uploadImage")) {
			uploadImage(request, response);
		}


		


	}
	public void init(ServletConfig config) throws ServletException {
		FileItemFactory factory = new DiskFileItemFactory();// Create a factory
		this.upload = new ServletFileUpload(factory);// Create a new file upload
		this.upload.setSizeMax(this.MAXSize);// Set overall request size
		filedir = config.getServletContext().getRealPath("upload");
		System.out.println("filedir=" + filedir);
		registerDao = new RegisterDao();
	}
	private void updateImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String uImg = request.getParameter("uImg");
		String uid = request.getParameter("userId");
		List<Object> params = new ArrayList<Object>();
		params.add(uImg);
		params.add(uid);
		boolean flag = registerDao.updateImage(params);
		if (flag) {
			
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "修改成功");
			jsonmsg.put("repCode", "666");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);
			
		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "修改失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);
		}

	}
	
	private void uploadImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String imagePath = null;
		try {
			List<FileItem> items = this.upload.parseRequest(request);
			if (items != null && !items.isEmpty()) {
				for (FileItem fileItem : items) {
					String filename = fileItem.getName();

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
	}
	private void updatePswd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String upswd = request.getParameter("upswd");
		String uid = request.getParameter("uid");
		List<Object> params = new ArrayList<Object>();
		params.add(upswd);
		params.add(uid);
		boolean flag = registerDao.updatePswd(params);
		if (flag) {

			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "更新成功");
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
	private void updateEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String uphone = request.getParameter("uEmail");
		String uid = request.getParameter("uid");
		List<Object> params = new ArrayList<Object>();
		params.add(uphone);
		params.add(uid);
		boolean flag = registerDao.updateEmail(params);
	if (flag) {
			
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "更新成功");
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
	private void updateSchool(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String uphone = request.getParameter("uSchool");
		String uid = request.getParameter("uid");
		List<Object> params = new ArrayList<Object>();
		params.add(uphone);
		params.add(uid);
		boolean flag = registerDao.updateSchool(params);
	if (flag) {
			
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "更新成功");
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
	
	private void updatePro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String uphone = request.getParameter("upro");
		String uid = request.getParameter("uid");
		List<Object> params = new ArrayList<Object>();
		params.add(uphone);
		params.add(uid);
		boolean flag = registerDao.updatePro(params);
	if (flag) {
			
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "更新成功");
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
	
	
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String loginName = request.getParameter("loginName");
		String loginPassword = request.getParameter("loginPassword");
		System.out.println("-------loginName----" + loginName);
		System.out.println("-------loginPassword----" + loginPassword);

		if (loginName.equals("admin") && loginPassword.equals("123456")) {
			HttpSession session = request.getSession();
			session.setAttribute("loginState", "loginOk");
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		} else {
			PrintWriter out = response.getWriter(); // 初始化out对象
			out.print("<script language='javascript'>alert('用户名或密码不正确!');window.location.href='../login.jsp';</script>");

		}
	}
	
	
	private void updateName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String uname = request.getParameter("uname");
		String uid = request.getParameter("uid");
		List<Object> params = new ArrayList<Object>();
		params.add(uname);
		params.add(uid);
		boolean flag = registerDao.updateName(params);
		if (flag) {
			
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "更新成功");
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
	
	
	
	
	private void updatePhone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String uphone = request.getParameter("uphone");
		String uid = request.getParameter("uid");
		List<Object> params = new ArrayList<Object>();
		params.add(uphone);
		params.add(uid);
		boolean flag = registerDao.updatePhone(params);
	if (flag) {
			
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "更新成功");
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
	
	
	private void deleteUserPc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String uid = request.getParameter("uid");
		List<Object> params = new ArrayList<Object>();
		params.add(uid);
		boolean flag = registerDao.deleteUser(params);
		if (flag) {
			System.out.println("成功了");
			response.sendRedirect(path + "/servlet/RegisterAction?action_flag=listUser&uid=" + uid);
		} else {
			System.out.println("失败了");
		}

	}

	/**
	 * 注册
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	
	private void registerMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String uname = request.getParameter("uname");
		String upswd = request.getParameter("upswd");
		String uphone = request.getParameter("uphone");
		

		List<Object> params_check_login = new ArrayList<Object>();
		params_check_login.add(uname);
		params_check_login.add(upswd);

		boolean flag = registerDao.resgisterCheck(params_check_login);
		if (flag == true) {
			Map<String, Object> user_model = registerDao.queryOne(params_check_login);
			
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "此用户已经注册");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		} else {
			List<Object> params = new ArrayList<Object>();
			params.add(uname);
			params.add(upswd);
			params.add(uphone);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
			params.add(df.format(new Date()));
			
			JSONObject jsonmsg = new JSONObject();
			//数据的注册
			boolean flag_zhuce = registerDao.resgisterPhone(params);
			
			//注册成功处理
			if(flag_zhuce){
				jsonmsg.put("repMsg", "注册成功");
				jsonmsg.put("repCode", "666");
				response.getWriter().print(jsonmsg);// 将路径返回给客户端
				System.out.println(jsonmsg);
			} else {
				jsonmsg.put("repMsg", "注册失败");
				jsonmsg.put("repCode", "111");
				response.getWriter().print(jsonmsg);// 将路径返回给客户端
				System.out.println(jsonmsg);
			}
			
			System.out.println(flag_zhuce);
			
		}

	}
	
	/**
	 * 获取用户信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void listUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 已经进行分页之后的数据集合
		List<Map<String, Object>> list = registerDao.listUser();
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../userMessage.jsp").forward(request, response);
	}
	
	
	
	private void loginMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String user_phone = request.getParameter("userPhone");
		String user_pswd = request.getParameter("userPswd");

		List<Object> params_check_login = new ArrayList<Object>();
		params_check_login.add(user_phone);
		params_check_login.add(user_pswd);
		boolean flag = registerDao.Login(params_check_login);
		if (flag) {
			Map<String, Object> map = registerDao.queryOne(params_check_login);
			
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "登录成功");
			jsonmsg.put("repCode", "666");
			jsonmsg.put("data", map);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
			

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "用户名或密码不正确");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}
	}
	
	

}
