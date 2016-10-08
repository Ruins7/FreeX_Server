package com.ece651.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.ece651.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

//@ParentPackage("struts-default")
//@Namespace("/user")
//@Action("usertest")
@Controller("testAction")
public class TestAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 注入userService
	 */
	@Autowired
	private UserService userService;

	@Action(value = "/login"
//            results = {
//			@Result(name = "success", type="chain", location = "exe"),
//			@Result(name = "error", location = "/index.jsp")
//		}				
	)
	@Override
	public String execute() throws Exception {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"spring.xml", "spring-hibernate.xml");
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
