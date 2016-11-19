package com.ece651.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;

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
 * @ClassName         BalanceAction.java
 * @Description       deposit,withdrawal
 * @author            Zhao
 * @time              2016年11月8日 下午1:20:53
 * @version			  v1.0
 */
public class BalanceAction extends ActionSupport {

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

	public void setBalanceService(BalanceService balanceService) {
		this.balanceService = balanceService;
	}
	
	public void setTransactionHistoryService(
			TransactionHistoryService transactionhistoryservice) {
		this.transactionhistoryservice = transactionhistoryservice;
	}

	/**
     * deposit
     * @param  Balance(no need bid)
     * @return succeed:Balance; failed:DepositFail
     */
	@Action(value = "deposit", results = { @Result(name = "success", type = "chain", location = "balanceAddHistory") })
	public String deposit() throws IOException {
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
		tranhistory = new Transaction_history();
		tranhistory = (Transaction_history) JSONObject.toBean(reqObject, Transaction_history.class);
		//新建balance
		balance = new Balance();
		balance.setBuid(tranhistory.getThuid());
		balance.setBcid(tranhistory.getCidin());//入账
		//balance.setBamount(tranhistory.getThamount());
		//balance = (Balance) JSONObject.toBean(reqObject, Balance.class);
		// 调用userService，设置当前用户为balance中所指用户
		user = new User();
		user.setUid(tranhistory.getThuid());
		user = userService.searchUserByID(user);
		// 调用balanceService层，获取当前用户的币种，如果没有则添加，如果有则直接更改余额
		balance = balanceService.searchOneCurrOfUser(balance);
		if (balance == null) {
			// this currency does not exist
			balance = new Balance();
			balance.setBuid(tranhistory.getThuid());
			balance.setBcid(tranhistory.getCidin());//入账
			balance.setBamount(tranhistory.getThamount());
			
			Serializable bid = balanceService.addNewCurrencyBalance(balance);
			if (bid != null) {
				// add new currency succeed
				balance.setBid(Integer.valueOf(bid.toString()));
				JSONObject respObject = JSONObject.fromObject(balance);
				this.response.setCharacterEncoding("UTF-8");
				this.response.getWriter().write(respObject.toString());
				return SUCCESS;
			} else {
				// adding new currency failed
				this.response.getWriter().write("DepositFail");
				return null;
			}
		} else {
			// this currency exists and update currency
		    BigDecimal bd_b=new BigDecimal(balance.getBamount());
		    BigDecimal bd_t=new BigDecimal(tranhistory.getThamount());
			balance.setBamount(bd_b.add(bd_t).toString());
			int ret = balanceService.deposit(balance);
			if (ret == 1) {
				// succeed
				JSONObject respObject = JSONObject.fromObject(balance);
				this.response.setCharacterEncoding("UTF-8");
				this.response.getWriter().write(respObject.toString());
				return SUCCESS;
			} else {
				// failed
				this.response.getWriter().write("DepositFail");
				return null;
			}
		}
	}
	
	/**
     * withdrawal
     * @param  Balance(no need bid)
     * @return succeed:Balance; failed:WithdrawalFail
     */
	@Action(value = "withdrawal", results = { @Result(name = "success", type = "chain", location = "BalancReduceHistory") })
	public String withdrawal() throws IOException {
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
		// 将JSONObject转换为对象
		tranhistory = new Transaction_history();
		tranhistory = (Transaction_history) JSONObject.toBean(reqObject,
				Transaction_history.class);
		// 新建balance
		balance = new Balance();
		balance.setBuid(tranhistory.getThuid());
		balance.setBcid(tranhistory.getCidin());// 入账
		balance.setBamount(tranhistory.getThamount());
		// balance = (Balance) JSONObject.toBean(reqObject, Balance.class);
		// 调用userService，设置当前用户为balance中所指用户
		user = new User();
		user.setUid(tranhistory.getThuid());
		user = userService.searchUserByID(user);
		// 调用service层
		// 调用balanceService层，获取当前用户的币种，如果没有则返回WithdrawalFailed，如果有则直接更改余额
		balance.setBid(balanceService.searchOneCurrOfUser(balance).getBid());
		if (balance.getBid() == 0) {
			// this currency does not exist
			this.response.getWriter().write("WithdrawalFail");
			return null;
		} else {
			// this currency exists and update currency
			int ret = balanceService.withdrawal(balance);
			if (ret == 1) {
				// succeed
				JSONObject respObject = JSONObject.fromObject(balance);
				this.response.setCharacterEncoding("UTF-8");
				this.response.getWriter().write(respObject.toString());
				return SUCCESS;
			} else {
				// failed
				this.response.getWriter().write("WithdrawalFail");
				return null;
				
			}
		}
	}
}
