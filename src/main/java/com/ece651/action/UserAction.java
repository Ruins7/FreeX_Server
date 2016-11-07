package com.ece651.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

import com.ece651.entity.User;
import com.ece651.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {

	private User loginuser;
	private HttpSession session;
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	private UserService userService;

	// set注入
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Action(value = "login")
	public String login() throws IOException {
		// 设置JSON格式
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/json;charset=utf-8");
		// 通过bufferreader获取json数据
		BufferedReader br = new BufferedReader(new InputStreamReader(
				request.getInputStream(), "utf-8"));
		StringBuffer sb = new StringBuffer("");
		String temp = "";
		while ((temp = br.readLine()) != null) {
			sb.append(temp);
		}
		br.close();
		// 将获取到的数据转换为JSONObjec
		JSONObject reqObject = JSONObject.fromObject(sb.toString());
		// 将JSONObject转换为对象
		loginuser = new User();
		loginuser = (User) JSONObject.toBean(reqObject, User.class);
		// 调用service层
		loginuser = userService.login(loginuser);

		// JSONObject respObject=new JSONObject();
		// 结果判断
		if (loginuser.getUid() != 0) {
			// 首次登陆获取session来保存该用户的信息
			session = request.getSession();
			session.setAttribute("username", loginuser.getUsername());
			session.setAttribute("userid", loginuser.getUid());
			// user封装成JSON,返回给客户端
			JSONObject respObject = JSONObject.fromObject(loginuser);
			this.response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write(respObject.toString());
			return null;
		} else {
			// 返回到登录界面,返回值为empty
			response.getWriter().write("empty");
			return null;
		}
	}

	@Action(value = "userinfo")
	public String returnhistroy() throws IOException {
		// 返回用户的历史记录
		JSONObject respObject = new JSONObject();
		return null;
	}

	@Action(value = "register")
	public String register() throws IOException {
		// 设置JSON格式
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/json;charset=utf-8");
		// 通过bufferreader获取json数据
		BufferedReader br = new BufferedReader(new InputStreamReader(
				request.getInputStream(), "utf-8"));
		StringBuffer sb = new StringBuffer("");
		String temp = "";
		while ((temp = br.readLine()) != null) {
			sb.append(temp);
		}
		br.close();
		// 将获取到的数据转换为JSONObjec
		JSONObject reqObject = JSONObject.fromObject(sb.toString());
		// 将JSONObject转换为对象
		loginuser = new User();
		loginuser = (User) JSONObject.toBean(reqObject, User.class);
		// 调用service层

		Serializable uid = userService.signup(loginuser);
		// 结果判断
		if (uid != null) {
			// 首次登陆获取session来保存该用户的信息
			session = request.getSession();
			session.setAttribute("username", loginuser.getUsername());
			session.setAttribute("userid", loginuser.getUid());
			// user封装成JSON,返回给客户端
			JSONObject respObject = JSONObject.fromObject(loginuser);
			this.response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write(respObject.toString());
			return null;
		} else {
			// 注册失败时返回;
			response.getWriter().write("signupfail");
			return null;
		}
	}
}
