package com.ctv.quizup.user.business.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.ctv.quizup.user.redis.UserBaseInfoRedis;
import com.ctv.quizup.util.HttpUtil;
import com.ctv.quizup.util.LoggerUtil;

public class UserNotifProcess {
	public static final String GOOGLE_API_KEY = "AIzaSyDaeU4wnbierU2Wv72bwYQk6r1tum-rxg0";
	public static final String GOOGLE_PUSH_SERVICE = "https://android.googleapis.com/gcm/send";

	public static Logger logger = LoggerUtil.getDailyLogger("UserNotificationProcess" + "_error");
	HttpUtil httpUtils;

	UserBaseInfoRedis userRedis;

	public UserNotifProcess() {
		this.userRedis = new UserBaseInfoRedis();
	}

	public void createUserRegister(String userId, String regId) {
		this.userRedis.writeUserRegId(userId, regId);
	}

	public String getUserRegId(String userId) {
		return this.userRedis.getUserRegId(userId);
	}
	
	public void broadcastNotif(String message) {
		List<String> regList = this.userRedis.getAllRegId();
		
		for(String regId : regList) {
			this.sendNotif(regId, message);
		}
	}

	public String sendUserNotif(String userId, String message) {
		String regId = this.getUserRegId(userId);
		String result = this.sendNotif(regId, message);
		return "userId: " + userId + "\n" + "message: " + message + "\n" + "regId:" + regId + "\n" + "result: " +  result;

	}

	public String sendNotif(String regId, String content) {
		String result = "";
		HashMap<String, Object> message = new HashMap<String, Object>();

		// Id-List to send
		ArrayList<String> ids = new ArrayList<String>();
		ids.add(regId);

		message.put("registration_ids", ids);

		HashMap<String, String> data = new HashMap<String, String>();
		data.put("content", content);

		message.put("data", data);

		HttpURLConnection httpConnection = null;
		try {
			URL url = new URL(GOOGLE_PUSH_SERVICE);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type","application/json");
			httpConnection.addRequestProperty("Authorization", "key=" + GOOGLE_API_KEY);
			httpConnection.setDoOutput(true);
		} catch (Exception e) {
			logger.error(e);
		}

		if (httpConnection == null) {
			return "error httpconnection";
		}

		

		try {
			OutputStream output = httpConnection.getOutputStream();

			String dataJSON = "";
			ObjectMapper mapper = new ObjectMapper();
			try {
				dataJSON = mapper.writeValueAsString(message);
				//System.out.println("data: " + dataJSON);
			} catch (IOException e) {
				logger.error(e);
				return "error mapping json data";
			}

			output.write(dataJSON.getBytes());
			output.close();
			System.out.println("finish push");

			int responseCode = httpConnection.getResponseCode();
			System.out.println(responseCode);
			BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			//System.out.println(response.toString());
			result = response.toString();
			return result;
		} catch (Exception e) {
			logger.error(e);
			return "error reading response " + e.toString();
		}
		
	}

	public static void main(String[] args) {
		UserNotifProcess pro = new UserNotifProcess();
		String regId = "APA91bGOrvaqsT3JVnJSobO_EAuRkkoEGwzURsZtimDmMLz6gf77j65ZZY5l4M6OhuJOludY6Ysg0q4xShGVZc_Q3wYw5TuiGnzWwY2y20G6feFcKalf-N7PWzJEFa2J_CmZwwlQoeSM5OLlMylcrZqOrl5Id_f8E_b5g7AnD6laHgLllru3l1c";
		String content = "hello notification";
		String result = pro.sendNotif(regId, content);
		System.out.println("return result: " + result);
	}

}
