package com.jisucloud.clawler.regagent.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.JJsoupUtil;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import lombok.extern.slf4j.Slf4j;
import me.kagura.JJsoup;
import me.kagura.Session;
import okhttp3.Request;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class WangdaiZhijiaSpider implements PapaSpider {

	@Override
	public String message() {
		return "网贷之家是中国首家P2P网贷理财行业门户网站,提供全方位、权威的网贷平台数据,是您身边的网贷资讯和P2P理财方面的专家,为您的网贷之路保驾护航.";
	}

	@Override
	public String platform() {
		return "wdzj";
	}

	@Override
	public String home() {
		return "wdzj.com";
	}

	@Override
	public String platformName() {
		return "网贷之家";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("金融理财", new String[] { "借贷", "贷超" });
			}
		};
	}

	public static void main(String[] args) {
		System.out.println(new WangdaiZhijiaSpider().checkTelephone("18210538513"));
		System.out.println(new WangdaiZhijiaSpider().checkTelephone("18210538577"));
	}

	private Map<String, String> getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0");
		headers.put("Host", "passport.wdzj.com");
		headers.put("Referer", "https://passport.wdzj.com/user/getpwd");
		headers.put("X-Requested-With", "XMLHttpRequest");
		return headers;
	}
	
	private Map<String, String> getParams(String mobile) {
		Map<String, String> params = new HashMap<>();
		params.put("ci_csrf_token", "");
		params.put("mobile", mobile);
		return params;
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			Session session = JJsoupUtil.newProxySession();
			String url = "https://passport.wdzj.com/userInterface/verifyMobile";
			System.out.println(url);
			Connection.Response response = session.connect(url)
					.method(Method.POST)
					.data(getParams(account))
					.headers(getHeader()).ignoreContentType(true).execute();
			if (response != null) {
				JSONObject result = JSON.parseObject(response.body());
				if (result.getString("msg").contains("验证成功")) {
					return true;
				}
				return false;
			}
		} catch (Exception e) {
			if (e.getMessage().contains("Read timed out")) {
				return false;
			}
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkEmail(String account) {
		return checkTelephone(account);
	}

	@Override
	public Map<String, String> getFields() {
		return null;
	}

}
