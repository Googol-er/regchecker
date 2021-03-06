package com.jisucloud.clawler.regagent.service.impl.money;

import com.deep007.goniub.selenium.mitm.ChromeAjaxHookDriver;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "epicc.com", 
		message = "人保官网直销省更多，安全有保障，无中间环节，车险多省15％，其他保险产品更优惠。优质理赔服务，享受与线下同等理赔服务，更可拥有网上投保专属特色服务；全国10万个网点，30万个专业理赔服务人员。方便快捷，条款、金额公开透明，自助选择，轻松对比，我的保险我做主。", 
		platform = "epicc", 
		platformName = "人保官网", 
		tags = { "理财", "保险" }, 
		testTelephones = { "15985268900", "18212345678" })
public class ZhongGuoRenMinBaoXianSpider extends PapaSpider {

	private ChromeAjaxHookDriver chromeDriver;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newNoHookInstance(true, true, IOS_USER_AGENT);
			chromeDriver.get("http://www.epicc.com.cn/newecenter/findPwd/find.do");
			smartSleep(5000);
			chromeDriver.findElementById("userName").sendKeys(account);
			chromeDriver.findElementByClassName("js-input-next").click();
			smartSleep(3000);
			Document doc = Jsoup.parse(chromeDriver.getPageSource());
			if (doc.select("#userMobile").attr("value").contains("****")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
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
