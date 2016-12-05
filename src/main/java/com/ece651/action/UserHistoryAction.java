package com.ece651.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

import com.ece651.entity.Balance;
import com.ece651.entity.PageResults;
import com.ece651.entity.Transaction_history;
import com.ece651.entity.User;
import com.ece651.entity.User_history;
import com.ece651.service.BalanceService;
import com.ece651.service.TransactionHistoryService;
import com.ece651.service.UserHistoryService;
import com.ece651.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @ClassName         UserHistoryAction.java
 * @Description       UserHistory: GetHisByUser,AddHisByUser 
 * @author            Zhao
 * @time              2016.11.10 4:30:10 pm
 * @version			  v1.0
 * 
 */

public class UserHistoryAction extends ActionSupport {

	
	private List<Object> jsonlist; // the list of object
	private PageResults<User_history> historyresult;
	private User_history userhistory;
	private Transaction_history tranhistory;
	private Balance balance;
	private User user;
	private HttpSession session;
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	private UserService userService;
	private BalanceService balanceService;
	private TransactionHistoryService transactionhistoryservice;
	private UserHistoryService userhistoyservice;
	
	// set annotation
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setTransactionHistoryService(
			TransactionHistoryService transactionhistoryservice) {
		this.transactionhistoryservice = transactionhistoryservice;
	}
	public void setUserHistoryService(
			UserHistoryService userhistoryservice) {
		this.userhistoyservice = userhistoryservice;
	}

	public void setBalanceService(BalanceService balanceService) {
		this.balanceService = balanceService;
	}

	
	// get history information by user name
	@Action(value = "GetHisByUser")
	public String GetHisByUser() throws IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/json;charset=utf-8");
		BufferedReader br = new BufferedReader(new InputStreamReader(
				request.getInputStream(), "utf-8"));
		StringBuffer sb = new StringBuffer("");
		String temp = "";
		while ((temp = br.readLine()) != null) {
			sb.append(temp);
		}
		br.close();
		JSONObject reqObject = JSONObject.fromObject(sb.toString());
		// check user exists or not
		user = new User();
		user=(User) JSONObject.toBean(reqObject, User.class);
		session = request.getSession();
		user.setUid((int) session.getAttribute("userid"));
		user = userService.searchUserByID(user);
		if (user.getUid() != 0) {
			// success, user exists, then find the history
			userhistory = new User_history();
			userhistory.setUhuid(user.getUid());
			// get historyresult from object list 
			historyresult = new PageResults<User_history>();
			historyresult = userhistoyservice.searchAllUserHisOfAUser(
					userhistory, historyresult);
			//write into JSONArray()
			JSONArray jarray = new JSONArray();
			jarray.add(userhistory);
			jarray.add(historyresult);
			JSONObject respObject = JSONObject.fromObject(jarray);
			this.response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write(respObject.toString());
			return null;
		} else {
			// failed,no user, return "GetHistoryByUserFail"
			response.getWriter().write("GetHistoryByUserFail");
			return null;
		}
	}
	// add new user history information
	@Action(value = "AddHisByUser")
	public String AddHisByUser() throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/json;charset=utf-8");
		BufferedReader br = new BufferedReader(new InputStreamReader(
				request.getInputStream(), "utf-8"));
		StringBuffer sb = new StringBuffer("");
		String temp = "";
		while ((temp = br.readLine()) != null) {
			sb.append(temp);
		}
		br.close();
		JSONObject reqObject = JSONObject.fromObject(sb.toString());
		user = new User();
		user = (User) JSONObject.toBean(reqObject, User.class);
		userhistory = new User_history();
		userhistory.setUhuid((int) session.getAttribute("userid"));
		userhistory.setUhtime(new Date());
		userhistory.setAction("Change Password");
		userhistoyservice.addNewUserHis(userhistory);
		return null;
	}
}
