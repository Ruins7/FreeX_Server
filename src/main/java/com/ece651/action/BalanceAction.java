package com.ece651.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
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
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @ClassName BalanceAction.java
 * @Description deposit,withdrawal
 * @author Zhao
 * @time 2016.11.8 1:20:53 pm
 * @version v1.0
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

	// set annotation
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
	 * 
	 * @param Balance
	 *            (no need bid)
	 * @return succeed:Balance; failed:DepositFail
	 */
	@Action(value = "deposit", results = { @Result(name = "success", type = "chain", location = "balanceAddHistory") })
	public String deposit() throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/json;charset=utf-8");
		// get request object (json)
		BufferedReader br = new BufferedReader(new InputStreamReader(
				request.getInputStream(), "utf-8"));
		StringBuffer sb = new StringBuffer("");
		String temp = "";
		while ((temp = br.readLine()) != null) {
			sb.append(temp);
		}
		br.close();
		JSONObject reqObject = JSONObject.fromObject(sb.toString());
		tranhistory = new Transaction_history();
		tranhistory = (Transaction_history) JSONObject.toBean(reqObject,
				Transaction_history.class);
		balance = new Balance();
		session = request.getSession();
		balance.setBuid((int) session.getAttribute("userid"));
		balance.setBcid(tranhistory.getCidin());
		user = new User();
		user.setUid((int) session.getAttribute("userid"));
		user = userService.searchUserByID(user);
		// use methods of balanceService to get the current currency
		balance = balanceService.searchOneCurrOfUser(balance);
		if (balance == null) {
			// if the currency does not exist
			balance = new Balance();
			balance.setBuid(tranhistory.getThuid());
			balance.setBcid(tranhistory.getCidin());
			balance.setBamount(tranhistory.getThamount());

			Serializable bid = balanceService.addNewCurrencyBalance(balance);
			if (bid != null) {
				// add new currency succeed
				balance.setBid(Integer.valueOf(bid.toString()));
				JSONObject respObject = JSONObject.fromObject(balance);
				this.response.setCharacterEncoding("UTF-8");
				this.response.getWriter().write(respObject.toString());
				// transfer tranhistory to next action
				ActionContext.getContext().put("T", tranhistory);
				return SUCCESS;
			} else {
				// adding new currency failed, return "DepositFail"
				this.response.getWriter().write("DepositFail");
				return null;
			}
		} else {
			// this currency exists and update currency
			BigDecimal bd_b = new BigDecimal(balance.getBamount());
			BigDecimal bd_t = new BigDecimal(tranhistory.getThamount());
			balance.setBamount(bd_b.add(bd_t).toString());
			int ret = balanceService.deposit(balance);
			if (ret == 1) {
				// succeed, return balance
				JSONObject respObject = JSONObject.fromObject(balance);
				this.response.setCharacterEncoding("UTF-8");
				this.response.getWriter().write(respObject.toString());
				return SUCCESS;
			} else {
				// failed, return "DepositFail"
				this.response.getWriter().write("DepositFail");
				return null;
			}
		}
	}

	/**
	 * withdrawal
	 * 
	 * @param Balance
	 *            (no need bid)
	 * @return succeed:Balance; failed:WithdrawalFail
	 */
	@Action(value = "withdrawal", results = { @Result(name = "success", type = "chain", location = "balancReduceHistory") })
	public String withdrawal() throws IOException {
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
		System.out.println("blance..." + sb.toString());
		JSONObject reqObject = JSONObject.fromObject(sb.toString());
		tranhistory = new Transaction_history();
		tranhistory = (Transaction_history) JSONObject.toBean(reqObject,
				Transaction_history.class);
		balance = new Balance();
		session = request.getSession();
		balance.setBuid((int) session.getAttribute("userid"));
		balance.setBcid(tranhistory.getCidout());
		user = new User();
		user.setUid((int) session.getAttribute("userid"));
		user = userService.searchUserByID(user);
		// use methods of balanceService to get the current currency
		balance = balanceService.searchOneCurrOfUser(balance);
		System.out.println("blance" + balance);
		if (balance != null) {
			if (balance.getBid() == 0) {
				// this currency does not exist
				this.response.getWriter().write("WithdrawalFail");
				return null;
			} else {
				// this currency exists and update currency
				BigDecimal bd_b = new BigDecimal(balance.getBamount());
				BigDecimal bd_t = new BigDecimal(tranhistory.getThamount());
				int bret = bd_b.compareTo(bd_t);
				if (bret >= 0) {
					balance.setBamount(bd_b.subtract(bd_t).toString());
					int ret = balanceService.withdrawal(balance);
					if (ret == 1) {
						// succeed, return balance
						JSONObject respObject = JSONObject.fromObject(balance);
						this.response.setCharacterEncoding("UTF-8");
						this.response.getWriter().write(respObject.toString());
						// transfer tranhistory to next action
						ActionContext.getContext().put("T", tranhistory);
						return SUCCESS;
					} else {
						// failed, return "WithdrawalFail"
						this.response.getWriter().write("WithdrawalFail");
						return null;
					}
				} else {
					// failed, return "WithdrawalFail"
					this.response.getWriter().write("WithdrawalFail");
					return null;
				}

			}
		} else {
			// failed, return "WithdrawalFail"
			this.response.getWriter().write("WithdrawalFail");
			return null;
		}

	}

	/**
	 * fetchBalance
	 * 
	 * @param User
	 *            (no need uid)
	 * @return succeed:JSONArray; failed:FetchFail
	 */
	@Action(value = "fetchBalance")
	public String fetchBalance() throws IOException {
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
		session = request.getSession();
		user.setUid((int) session.getAttribute("userid"));
		if (user.getUid() != 0) {
			// set balance
			balance = new Balance();
			balance.setBuid(user.getUid());
			System.out.println(" bbbb   "+balance.getBuid());

			List<Balance> balanceList = balanceService.searchAllBalOfUser(balance);
			for (int i = 0; i < balanceList.size(); i++) {
				System.out.println(" bbbbbbb   "+balanceList.get(i).getBuid()+"  "+balanceList.get(i).getBcid()+"  "+balanceList.get(i).getBamount());
			}
			
			if (balanceList.size() != 0) {
				// succeed, return balances (JSONArray)
				JSONArray jarray = new JSONArray();
				for (Balance b : balanceList) {
					JSONObject jsonb = JSONObject.fromObject(b);
					jarray.add(jsonb);
				}
				this.response.setCharacterEncoding("UTF-8");
				this.response.getWriter().write(jarray.toString());
				return null;
			} else {
				//failed, return "FetchFail"
				this.response.getWriter().write("FetchFail");
				return null;
			}
		} else {
			//failed, return "FetchFail"
			this.response.getWriter().write("FetchFail");
			return null;
		}
	}
}
