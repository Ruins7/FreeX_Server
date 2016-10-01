package com.ece651.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ece651.entity.User;
import com.ece651.service.UserService;


public class TestHibernate {
	 private UserService userService;
	    
	    /**
	     * 这个before方法在所有的测试方法之前执行，并且只执行一次
	     * 所有做Junit单元测试时一些初始化工作可以在这个方法里面进行
	     * 比如在before方法里面初始化ApplicationContext和userService
	     */
	/*    @Before
	    public void before(){
	        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml","spring-hibernate.xml");
	        userService = (UserService) ac.getBean("userService");
	    }
	    
	    @Test
	    public void testSaveMethod(){
//	        ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{"spring.xml","spring-hibernate.xml"});
//	        UserService userService = (UserService) ac.getBean("userService");
	        User user = new User();
	        user.setUsername("freddy");
	        user.setPassword("123123");
	        user.setEmail("12312312@hotmail.com");
	        userService.save(user);
	    }*/
}
