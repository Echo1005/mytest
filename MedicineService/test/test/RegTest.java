package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.service.dao.NewsDao;
import com.service.dao.RegisterDao;


public class RegTest {

	private NewsDao newsDao;

	@Test
	public void Reg() {
		newsDao = new NewsDao();
		 List<Object> params = new ArrayList<Object>();
		 params.add("106");
			List<Map<String, Object>> list_msg = newsDao.listPhoneJsonMessage("106");
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list_msg);
		System.out.println(jsonmsg);
	}
}
