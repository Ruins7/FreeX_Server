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
 * @time              2016年11月10日 下午4:30:10
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
	
	// set注入
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

	
	/**
     * login
     * @param  List[User_history,PageResults]
     * @return succeed:List[User_history,PageResults]; failed:GetHistoryByUserFail
     */
	@Action(value = "GetHisByUser")
	public String GetHisByUser() throws IOException {
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
		// 将JSONObject转换为 object list
		jsonlist = new ArrayList();
		jsonlist = (List) JSONObject.toBean(reqObject, List.class);
		// object list中取出userhistory
		userhistory = new User_history();
		userhistory = (User_history) jsonlist.get(0);
		// object list中取出historyresult
		historyresult = new PageResults<User_history>();
		historyresult = (PageResults<User_history>) jsonlist.get(1);
		// 查询user是否存在
		user = new User();
		user.setUid(userhistory.getUhuid());
		user = userService.searchUserByID(user);
		if (user.getUid() != 0) {
			// success, user exists, then find the history
			historyresult = userhistoyservice.searchAllUserHisOfAUser(
					userhistory, historyresult);
			jsonlist.clear();
			jsonlist.add(userhistory);
			jsonlist.add(historyresult);
			JSONObject respObject = JSONObject.fromObject(jsonlist);
			this.response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write(respObject.toString());
			return null;
		} else {
			// failed,no user
			response.getWriter().write("GetHistoryByUserFail");
			return null;
		}
	}
	
	@Action(value = "AddHisByUser")
	public String AddHisByUser() throws IOException {
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
		user = new User();
		user = (User) JSONObject.toBean(reqObject, User.class);
		userhistory = new User_history();
		userhistory.setUhuid(user.getUid());
		userhistory.setUhtime(new Date());
		userhistory.setAction("Change Password");
		userhistoyservice.addNewUserHis(userhistory);
		return null;
	}
}
