package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;


import java.util.HashMap;
import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "wdzj.com", 
		message = "网贷之家是中国首家P2P网贷理财行业门户网站,提供全方位、权威的网贷平台数据,是您身边的网贷资讯和P2P理财方面的专家,为您的网贷之路保驾护航.", 
		platform = "wdzj", 
		platformName = "网贷之家", 
		tags = {"金融资讯", "贷超" , "借贷" }, 
		testTelephones = {"19910538500", "18212345678" },
		exclude = true)
public class WangdaiZhijiaSpider extends PapaSpider {
	
	private Headers getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0");
		headers.put("Host", "passport.wdzj.com");
		headers.put("Referer", "https://passport.wdzj.com/user/getpwd");
		headers.put("X-Requested-With", "XMLHttpRequest");
		return Headers.of(headers);
	}

	private FormBody getParams(String mobile) {
		return new FormBody
                .Builder()
                .add("ci_csrf_token", "")
                .add("mobile", mobile)
                .build();
	}

	public boolean checkTelephone(String account) {
		String url = "https://passport.wdzj.com/userInterface/verifyMobile";
		try {
			Request request = new Request.Builder().url(url)
					.headers(getHeader())
					.post(getParams(account))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			if (result.getString("msg").contains("验证成功")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkEmail(String account) {
		return false;
	}

	@Override
	public Map<String, String> getFields() {
		return null;
	}

}
