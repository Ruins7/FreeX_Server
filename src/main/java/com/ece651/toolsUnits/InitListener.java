package com.ece651.toolsUnits;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ece651.toolsUnits.h2.H2currenyPool;

public class InitListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("listener......init.....");
		try {
			H2currenyPool.initialize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("listener......dest.....");

	}

}
