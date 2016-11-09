package com.ece651.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

import com.opensymphony.xwork2.ActionSupport;


/**
 * @ClassName         TestAction.java
 * @Description       Android测试用action
 * @author            Freddy
 * @time              2016年11月7日 下午8:20:53
 * @version			  v1.0
 */
public class TestAction extends ActionSupport{
	
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	
	/**
     * text method
     */
	@Action(value = "text")
	public String test() throws IOException {
		System.out.println("已进入server test方法...");
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
		System.out.println("此次请求的发送的json数据为:  "+sb.toString());
		return null;
	}

}
