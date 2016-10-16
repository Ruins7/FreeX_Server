package com.ece651.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.ece651.entity.User;
import com.ece651.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/user")
public class UserAction extends ActionSupport {
	
	private UserService userService;
	 
	//set注入
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Action("login")
//  results = {
//	@Result(name = "success", type="chain", location = "exe"),
//	@Result(name = "error", location = "/index.jsp")
//}
	public String login(){
		 System.out.println("进入TestAction");
		 User user = new User();
		 user.setUsername("freddyd");
		 user.setPassword("123123");
		 user = userService.login(user);
		 System.out.println("uid:   "+user.getUid());
		return null;	
	}

}
