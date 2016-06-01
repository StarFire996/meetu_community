package com.meetu.community.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.meetu.community.service.ComBiuService;
import com.meetu.community.service.NotifyService;
import com.meetu.community.service.UserService;

@Controller
@RequestMapping("app/overall")
public class OverAllController {
	
	public static Logger LOGGER = LoggerFactory.getLogger(OverAllController.class);
	
	@Autowired
	private ComBiuService biuService;
	
	@Autowired
	private NotifyService notifyService;
	
	@Autowired
	private UserService userService;
	
	// 抢一条biu
	@RequestMapping(value = "getStatus", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getStatus(HttpServletRequest request,
			@RequestParam("data") String jsonData) {
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();
		Map<String, Object> debugMap = new HashMap<String, Object>();
		try {
			// 请求参数获取
			JSONObject data = JSONObject.parseObject(jsonData);
			String newToken = (String) request.getAttribute("token");
			String userId = (String) request.getAttribute("userid");
			
			
			json2.put("token", newToken);
			debugMap.put("userId", userId);

			Integer userFrom = this.userService.selectCodeById(userId);

			//userFrom = 12880;
			
			
			Integer comBiuNum = this.biuService.selectBiuUnRead(userFrom);
			Integer notifyNum = this.notifyService.selectNotifyUnRead(userFrom);
			
			json2.put("comBiuNum", comBiuNum==null?0:comBiuNum);
			json2.put("notifyNum", notifyNum==null?0:notifyNum);

			json.put("data", json2);
			json.put("state", "200");

			debugMap.put("json", json);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("getStatus:{}", debugMap);
			}

			return ResponseEntity.ok(json);
		} catch (Exception e) {
			json.put("state", "300");
			json.put("error", e.getMessage());
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("getStatus_err:{}", e.getMessage());
			}
		}
		return ResponseEntity.ok(json);
	}
}
