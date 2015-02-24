package com.ctv.quizup.user.business.impl;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.ctv.account.auth.AccessToken;
import com.ctv.account.auth.AccessTokenDencoder;
import com.ctv.account.profile.Adapter;
import com.ctv.account.profile.Profile;
import com.ctv.account.security.CodeGenerator;
import com.ctv.quizup.user.business.UserBaseInterface;
import com.ctv.quizup.user.model.UserBaseInfo;
import com.ctv.quizup.user.redis.UserBaseInfoRedis;
import com.ctv.quizup.util.HttpUtil;
import com.ctv.quizup.util.LoggerUtil;

public class UserServiceProcess implements UserBaseInterface {
	public static final String SERVICE_URL =
	// "http://5play.me:8890/account/v1.0";
	"http://5play.mobi:8893/account/v1.0";
	// "http://5play.mobi:8893/account-service-0.1.1";
	// "http://123.30.210.5:8890/account/v0.0";
	// "http://5play.mobi:8893/account-service-0.1.1";//http://123.30.210.5:8890/account/v0.0/

	public static Logger logger = LoggerUtil
			.getDailyLogger("UserServiceProcess" + "_error");

	UserBaseInfoRedis userRedis;
	HttpUtil httpUtils;

	public UserServiceProcess() {
		this.userRedis = new UserBaseInfoRedis();
		this.httpUtils = new HttpUtil();
	}

	public void createUser(UserBaseInfo user) {
		this.userRedis.writeUserToRedis(user.toString(), user.getUserId());
	}

	public String checkConvert(String userId) {
		if (userId.length() != 6) {
			return userId;
		}

		CodeGenerator gen = new CodeGenerator();
		try {
			String result = "" + gen.decode(userId);
			return result;
		} catch (Exception e) {}
		return null;
	}

	private UserBaseInfo getUserByAdapter(String userId) {

		Profile profile = Adapter.singletons().getUserProfile(
				Long.parseLong(userId));
		if (profile == null) {
			return null;
		}

		UserBaseInfo user = new UserBaseInfo(userId, profile.getName(),
				profile.getAvatarUrl(), profile.getCoverUrl());
		return user;

	}

	public UserBaseInfo getUserByService(String userId) {
		UserBaseInfo user = null;

		String url = SERVICE_URL + "/api/user/" + userId + "/profile";
		String userData = this.httpUtils.getHttp(url);
		ObjectMapper mapper = new ObjectMapper();

		try {
			UserResponse response = mapper.readValue(userData,
					UserResponse.class);
			user = new UserBaseInfo(userId, response.user.name,
					response.user.avatarUrl, response.user.coverUrl);
		} catch (Exception e) {
			logger.error(e);
			System.out.println(e.toString());
		}

		return user;
	}

	public UserBaseInfo getUser(String userId) {
		try {
			return this.getUserByAdapter(userId);
		} catch (Exception e) {}
		return null;
	}

	public static void main(String[] args) {
		UserServiceProcess pro = new UserServiceProcess();
		String userId = "727126420669934";
		System.out.println(pro.getUser(userId));
	}

	private static class UserInfo {
		public String avatarUrl;
		public String coverUrl;
		public String firstName;
		public String middleName;
		public String lastName;
		public String email;
		public String name;
		public String id;

		public UserInfo() {

		}

		@Override
		public String toString() {
			String result = "";

			ObjectMapper mapper = new ObjectMapper();
			try {
				result = mapper.writeValueAsString(this);
				return result;
			} catch (IOException e) {
				e.printStackTrace();
			}

			return result;
		}
	};

	private static class UserResponse {
		public int code;

		public UserInfo user;

		public int status;

		public UserResponse() {

		}

		@Override
		public String toString() {
			String result = "";

			ObjectMapper mapper = new ObjectMapper();
			try {
				result = mapper.writeValueAsString(this);
				return result;
			} catch (IOException e) {
				e.printStackTrace();
			}

			return result;
		}

	};

}
