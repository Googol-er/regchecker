package com.jisucloud.clawler.regagent.service.impl.social;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Random;


@Slf4j
@PapaSpiderConfig(
		home = "baihe.com", 
		message = "百合网是中国第一家通过网上实名进行交友和婚恋的服务商，以“帮助亿万中国人拥有幸福的婚姻和家庭”为己任。2005年5月，百合网正式发布，并在中国首次推出“心灵匹配，成就幸福婚姻”的独特婚恋服务模式。", 
		platform = "baihe", 
		platformName = "百合网", 
		tags = { "单身交友" , "婚恋" }, 
		testTelephones = { "18700001101", "18212345678" })
public class BaiheWangSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://uapi.jiayuan.com/Api/Reglogin/login";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("platform", "baihe")
	                .add("osv", "4.4.2")
	                .add("mac", "08%3A6D%3A41%3AD5%3A7A%3A6A")
	                .add("ver", "1.0")
	                .add("lang", "zh_CN")
	                .add("traceid", "3bf22b9088ab" + new Random().nextInt(10000))
	                .add("deviceid", "352284040670808")
	                .add("version", "1")
	                .add("channelid", "001")
	                .add("link_path", "100000_110003")
	                .add("clientid", "87")
	                .add("dd", "8692-A00")
	                .add("pwd", "69c8597d5e079efe633dcd30d5f6091110e005ca")
	                .add("token", "")
	                .add("isJailbreak", "1")
	                .add("bd", "a9xproltechn")
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-cn; 8692-A00 Build/KOT49H) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30")
					.addHeader("Host", "uapi.jiayuan.com")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			res = StringUtil.unicodeToString(res);
			if (res.contains("密码错误")) {
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
