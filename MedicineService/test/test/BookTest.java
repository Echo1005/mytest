package test;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.service.dao.MessageDao;

public class BookTest {

	static long originFileSize = 1024 * 1024 * 100;// 100M
	static int blockFileSize = 1024 * 1024 * 1;// 15M
	private MessageDao lostDao;
	@Test
	public void Reg() {
		lostDao = new MessageDao();
		javaInfor();
	}
	
	
	
	private void javaInfor() {
		Document document;
		try {
			document = Jsoup.connect("https://voice.baidu.com/act/newpneumonia/newpneumonia/?from=osari_pc_3").timeout(5000).get();
			Elements elements = document.getElementsByClass("VirusSummarySix_1-1-285_ZRHUKw");
	
			System.out.println(elements);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
