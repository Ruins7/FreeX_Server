package com.ece651.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.ece651.entity.Balance;
import com.ece651.entity.Transaction_history;
import com.ece651.entity.User;
import com.ece651.service.BalanceService;
import com.ece651.service.TransactionHistoryService;
import com.ece651.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @ClassName         TransactionHistoryAction.java
 * @Description       tansaction history action
 * @author            Zhao
 * @time              2016年11月8日 上午9:52:13
 * @version			  v1.0
 */

public class TransactionHistoryAction extends ActionSupport {

	private Transaction_history tranhistory;
	private Balance balance;
	private User user;
	private HttpSession session;
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpServletResponse response = ServletActionContext.getResponse();
	private UserService userService;
	private BalanceService balanceService;
	private TransactionHistoryService transactionhistoryservice;

	// set注入
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setTransactionHistoryService(
			TransactionHistoryService transactionhistoryservice) {
		this.transactionhistoryservice = transactionhistoryservice;
	}

	public void setBalanceService(BalanceService balanceService) {
		this.balanceService = balanceService;
	}

	// change transaction history
	// balance add history
	@Action(value = "BalanceAddHistory")
	public String BalanceAddHistory() throws IOException {
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
		balance = new Balance();
		balance = (Balance) JSONObject.toBean(reqObject, Balance.class);
		// 调用userService，设置当前用户为balance中所指用户
		user = new User();
		user.setUid(balance.getBuid());
		user = userService.searchUserByID(user);
		// 更新Transaction_history
		tranhistory = new Transaction_history();
		tranhistory.setThuid(user.getUid());
		tranhistory.setCidout(0);
		tranhistory.setCidin(balance.getBcid());
		tranhistory.setThamount(balance.getBamount());
		tranhistory.setRate("1");
		tranhistory.setThtime(new Date());
		transactionhistoryservice.addNewTranHis(tranhistory);
		return null;
	}

	// balance withdrawal history
	@Action(value = "BalancReduceHistory")
	public String BalancReduceHistory() throws IOException {
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
		balance = new Balance();
		balance = (Balance) JSONObject.toBean(reqObject, Balance.class);
		// 调用userService，设置当前用户为balance中所指用户
		user = new User();
		user.setUid(balance.getBuid());
		user = userService.searchUserByID(user);
		// 更新Transaction_history
		tranhistory = new Transaction_history();
		tranhistory.setThuid(user.getUid());
		tranhistory.setCidout(balance.getBcid());
		tranhistory.setCidin(0);
		tranhistory.setThamount(balance.getBamount());
		tranhistory.setRate("1");
		tranhistory.setThtime(new Date());
		transactionhistoryservice.addNewTranHis(tranhistory);
		return null;
	}
}
