package com.ece651.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ece651.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/user")
public class UserAction extends ActionSupport {
	
//  results = {
//	@Result(name = "success", type="chain", location = "exe"),
//	@Result(name = "error", location = "/index.jsp")
//}
	
	private UserService userService;
	
	@Action("login")
	public String login(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring-hibernate.xml");
		userService = (UserService) ac.getBean("userService");
		System.out.println("进入TestAction");
		// UserService.test();
		// User user = new User();
		// user.setUsername("freddy");
		// user.setPassword("123123");
		// user.setEmail("12312312@hotmail.com");
		// userService.save(user);
		return null;
		
	}

}
