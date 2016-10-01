package com.ece651.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ece651.service.UserService;

@ParentPackage("struts-default")
@Action(value = "strust2Test")
// 使用convention-plugin插件提供的@Action注解将一个普通java类标注为一个可以处理用户请求的Action，Action的名字为struts2Test
@Namespace("/")
// 使用convention-plugin插件提供的@Namespace注解为这个Action指定一个命名空间
public class TestAction {

	/**
	 * 注入userService
	 */
	@Autowired
	private UserService userService;

	/**
	 * http://localhost:8080/FreeX_Server/strust2Test!test.action MethodName:
	 * test Description:
	 */
	public void test() {

		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"spring.xml", "spring-hibernate.xml");
		userService = (UserService) ac.getBean("userService");
		System.out.println("进入TestAction");
		userService.test();
	}
}
