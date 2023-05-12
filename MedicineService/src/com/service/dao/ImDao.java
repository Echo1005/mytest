package com.service.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.service.jdbc.JdbcUtils;
import com.service.service.BaseService;

public class ImDao implements BaseService {
	private JdbcUtils jdbcUtils;

	public ImDao() {
		jdbcUtils = new JdbcUtils();
	}
	
	
	public List<Map<String, Object>> ListIsTypeNoGroup(List<Object> params) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from groupmsg where groupType != ?";
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
	
	public List<Map<String, Object>> ListIsTypeOkGroup(List<Object> params) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from groupmsg where groupType = ?";
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
	
	
	public boolean addJsonMessage(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into groupJoinMsg (groupJoinGroupId,groupJoinGroupName,groupJoinUserId,groupJoinUserName) values  (?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	
	public List<Map<String, Object>> listSearchGroupMessage() {

		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from groupmsg";
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
//	
//	public List<Map<String, Object>> listSearchGroupMessage(String proname) {
//
//		// TODO Auto-generated method stub
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		String sql = "select * from groupmsg where (1=1) ";
//		// limit ?,?
//		StringBuffer buffer = new StringBuffer(sql);
//		List<Object> params = new ArrayList<Object>();
//		if (proname != null) {
//			buffer.append(" and groupUserId like ? ");
//			params.add("%" + proname + "%");
//		}
//		try {
//			jdbcUtils.getConnection();
//
//			System.out.println(buffer.toString());
//			list = jdbcUtils.findMoreResult(buffer.toString(), params);
//		} catch (Exception e) {
//			// TODO: handle exception
//		} finally {
//			jdbcUtils.releaseConn();
//		}
//		return list;
//	}
//	


	
	public List<Map<String, Object>> ListGroup() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from groupmsg";
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

	
	public Map<String, Object> queryGroup(List<Object> params) {
		Map<String, Object> map = null;
		String sql = "select * from groupmsg where groupName=? and groupUserId=?";
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
	
	public boolean addGroupMessage(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into groupmsg (groupName,groupMessage,groupUserId,groupUserName) values  (?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	
	public boolean checkDay(List<Object> params) {
		boolean flag = false;
		String sql = "select * from daymsg where dayTime=?";
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
	
	
	public boolean deleteFileMessage(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "delete from filemsg where fileId=?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	
	
	public boolean addFileMessage(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into filemsg (fileSub,fileName,fileZip) values  (?,?,?)";
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
	
	
	
	public List<Map<String, Object>> listhobbymsgMessageFile() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from hobbymsg";
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

	
	
	public boolean addhobbymsgMessage(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into hobbymsg (hobbyName,hobbyNumber,hobbyAddress,hobbyTime,hobbyMessage,hobbyImage) values  (?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	
	
	
	public List<Map<String, Object>> listMessageFile() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from daymsg";
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

	public boolean addFile(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into daymsg (dayName,dayTime,dayUserId,dayUserName) values  (?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	
	public List<Map<String, Object>> listInterestMessage() {
		
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;
		
		
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from interestmsg order by interestId desc";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);

			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);
				List<Object> paramsFocus = new ArrayList<Object>();
				paramsFocus.clear();
				paramsFocus.add(list.get(i).get("interestUserId"));
				mapResult.put("userMessage", queryUser(paramsFocus));
				listResult.add(mapResult);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}
	
	
	
	public Map<String, Object> queryUser(List<Object> params) {
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
	
	
	
	public boolean deletehobbymsg(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "delete from hobbymsg where hobbyId=?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	
	public List<Map<String, Object>> listHuoDongMessage() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from hobbymsg";
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

	
	
	public boolean deleteInter(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "delete from interestmsg where interestId=?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	public List<Map<String, Object>> listInterestUserMessage(List<Object> params) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from interestmsg where interestUserId = ? order by interestId desc";
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

	
	
	public boolean addInterest(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into interestmsg (interestMessage,interestUserId,interestUserName,interestTime) values  (?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);

		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	
	public int getItemCount(List<Object> params) {
		int result = 0;
		Map<String, Object> map = null;
		String sqlString = "select count(*) mycount FROM appointmentmsg where appointmentDoctorId = ? and appointmentTime=?;";
//		String sql = " select count(*) mycount from ordermsg ";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sqlString, params);
			result = Integer.parseInt(map.get("mycount").toString());
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		// TODO Auto-generated method stub
		return result;
	}
	
	
	public Map<String, Object> queryMRMessage(List<Object> params) {
		Map<String, Object> map = null;
		String sql = "select * from medicalRecordPatientmsg where medicalRecordPatientId=? and appointmentId=?";
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
	
	
	public boolean addMRMessage(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into medicalRecordPatientmsg (medicalRecordPatientId,medicalRecordPatientName,medicalRecordPatientPhone,medicalRecordPatientTime,medicalRecordPatientMessage,medicalRecordDoctorId,medicalRecordDoctorName,appointmentId) values  (?,?,?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	
	
	
	
	public List<Map<String, Object>> listDoctorAppointmentmsgMessage(List<Object> params) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from appointmentmsg where appointmentDoctorId = ?";
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
	
	
	
	public List<Map<String, Object>> listAppointmentmsgMessage(List<Object> params) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from appointmentmsg where appointmentUserId = ?";
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
	public boolean addAppointment(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into appointmentmsg (appointmentDoctorId,appointmentDoctorName,appointmentUserId,appointmentUserName,appointmentTime,appointmentNumber) values  (?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	
	
	
	
	public List<Map<String, Object>> listSearchMessage(String proname) {

		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from navigation where (1=1) ";
		// limit ?,?
		StringBuffer buffer = new StringBuffer(sql);
		List<Object> params = new ArrayList<Object>();
		if (proname != null) {
			buffer.append(" and nName like ? ");
			params.add("%" + proname + "%");
		}
		try {
			jdbcUtils.getConnection();

			System.out.println(buffer.toString());
			list = jdbcUtils.findMoreResult(buffer.toString(), params);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean updateMessage(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "update  review set rReplyContent =? where rId = ?";
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
			String sql = "insert into hobbymsg (hobbyName,hobbyPhone,hobbyTime,hobbyNumber,hobbyAddress,hobbyMessage,hobbyUserId,hobbyUserName) values  (?,?,?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);

		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	
	
	public List<Map<String, Object>> listTopFood() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from bookmsg where fTopState = 2";
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
	

	public List<Map<String, Object>> listPhoneMessage() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from hobbymsg";
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

	public List<Map<String, Object>> listStudentMessage(List<Object> params) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from student where  sId = ?";
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
		String sql = "select * from interestmsg";
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
		boolean flag = false;
		try {
			String sql = "delete from bookmsg where flowerId=?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
}
