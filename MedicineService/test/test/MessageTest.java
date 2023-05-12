package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.service.dao.MessageDao;

public class MessageTest {

	private MessageDao lostDao;
	
	@Test
	public void Reg() {
		lostDao = new MessageDao();
		
		
		 List<Object> params = new ArrayList<Object>();
		 params.add("27");
		 
		List<Map<String, Object>> listBao = lostDao.listShopOrderPcMessage();
		
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", listBao);
		System.out.println(jsonmsg);
	}
}
