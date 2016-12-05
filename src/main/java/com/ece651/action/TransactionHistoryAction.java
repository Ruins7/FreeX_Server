package com.ece651.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.ece651.service.BalanceService;
import com.ece651.service.TransactionHistoryService;
import com.ece651.service.UserService;
import com.ece651.toolsUnits.SequenceQueue;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @ClassName TransactionHistoryAction.java
 * @Description tansaction history action
 * @author Zhao
 * @time 2016.11.8 9:52:13 am
 * @version v1.0
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

	// set annotation
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

	/*
	 * // change transaction history // balance add history
	 * 
	 * @Action(value = "balanceAddHistory") public String BalanceAddHistory()
	 * throws IOException, ParseException { tranhistory = new
	 * Transaction_history(); tranhistory = (Transaction_history)
	 * ActionContext.getContext().get("T"); // 更新Transaction_history
	 * tranhistory.setCidout(0); tranhistory.setRate("1"); // Add time
	 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Date
	 * d = sdf.parse(sdf.format(new Date())); tranhistory.setThtime(d);
	 * transactionhistoryservice.addNewTranHis(tranhistory); return null; }
	 * 
	 * // balance withdrawal history
	 * 
	 * @Action(value = "balancReduceHistory") public String
	 * BalancReduceHistory() throws IOException, ParseException { tranhistory =
	 * new Transaction_history(); tranhistory = (Transaction_history)
	 * ActionContext.getContext().get("T"); // 更新Transaction_history
	 * tranhistory.setCidin(0); tranhistory.setRate("1"); SimpleDateFormat sdf =
	 * new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); Date d =
	 * sdf.parse(sdf.format(new Date())); tranhistory.setThtime(d);
	 * 
	 * List<Double> thid = transactionhistoryservice
	 * .addNewTranHis(tranhistory); if (thid.get(0) != 0) {
	 * response.getWriter().write("TransactionSuccess"); } else {
	 * response.getWriter().write("TrandactionFail"); } return null; }
	 */

	/**
	 * add new transaction history
	 * 
	 * @param Transaction_history
	 *            (no need thid)
	 * @return Succeed:"TransactionSuccess", Fail: "TrandactionFail"
	 * @throws ParseException
	 */
	@Action(value = "AddNewTransaction")
	public String AddNewTransaction() throws IOException, ParseException {
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
		tranhistory = new Transaction_history();
		tranhistory = (Transaction_history) JSONObject.toBean(reqObject,
				Transaction_history.class);
		session = request.getSession();
		tranhistory.setThuid((int) session.getAttribute("userid"));
		// set time
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = sdf.parse(sdf.format(new Date()));
		tranhistory.setThtime(d);
		// set uid of user
		balance = new Balance();
		balance.setBuid((int) session.getAttribute("userid"));
		balance.setBcid(tranhistory.getCidout());
		balance = balanceService.searchOneCurrOfUser(balance);
		// check if money is enough of not
		BigDecimal bd_b = new BigDecimal(balance.getBamount());
		BigDecimal bd_t = new BigDecimal(tranhistory.getThamount());
		int bret = bd_b.compareTo(bd_t);
		if (bret >= 0) {
			// enough money
			SequenceQueue<Double> sqList = transactionhistoryservice
					.addNewTranHis(tranhistory);
			List<Double> list = new ArrayList<Double>();
			list.add(1.0);
			list.add(1.0);
			list.add(1.0);
			list.set(2, sqList.element());// list[0]
			sqList.remove();
			list.set(1, sqList.element());// list[1]
			sqList.remove();
			list.set(0, sqList.element());// list[2] 
			sqList.remove();
			
			sqList.add(list.get(2));
			sqList.add(list.get(1));
			sqList.add(list.get(0));
			System.out.println("sqList:    "+sqList);
			// store sqList into session
			session = request.getSession();
			session.setAttribute("Transaction_history", tranhistory);
			session.setAttribute("SequenceQueue", sqList);
			session.setAttribute("list", list);
			if (list.get(0) != 0) {
				// succeed, return tranhistory (List)
				JSONObject jsonb = new JSONObject();
				int i = 1;
				for (Double t : list) {
					System.out.println("t:     "+t.toString());
					jsonb.put(i, t.toString());
					i++;
				}
				this.response.setCharacterEncoding("UTF-8");
				this.response.getWriter().write(jsonb.toString());
			} else {
				//Failed, return "TransactionFail"
				response.getWriter().write("TransactionFail");
			}
		} else {
			// money is not enough, return "MoneyNotEnough" 
			response.getWriter().write("MoneyNotEnough");
		}
		return null;
	}

	/**
	 * cancel or execute Transaction  
	 * 
	 * @param "flag":1; "flag":0
	 * 
	 * @return Succeed:"TransactionSuccess", Fail: "TrandactionFail"
	 * @throws ParseException
	 */
	@Action(value = "ExecuteNewTransaction")
	public String ExecuteNewTransaction() throws IOException, ParseException {
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
		if (reqObject.getInt("flag") == 1) {
			session = request.getSession();
			// check: SequenceQueue,Transaction_history exist or not
			if ((session.getAttribute("SequenceQueue") != null)
					&& session.getAttribute("Transaction_history") != null) {
				// if SequenceQueue,Transaction_history exist
				SequenceQueue<Double> sq = new SequenceQueue<Double>();
				sq = (SequenceQueue<Double>) session
						.getAttribute("SequenceQueue");
				tranhistory = (Transaction_history) session
						.getAttribute("Transaction_history");
				// execute
				Boolean bret = transactionhistoryservice.addNewTranHisForSure(
						tranhistory, sq, true);
				if (bret) {
					// success
					List<Double> list = new ArrayList<Double>();
					list = (List<Double>) session.getAttribute("list");
					System.out.println("List:    " + list.toString());
					balance = new Balance();
					balance.setBuid((int) session.getAttribute("userid"));
					balance.setBcid(tranhistory.getCidout());
					balance = balanceService.searchOneCurrOfUser(balance);
					// check if there is enough money or not
					BigDecimal bd_b = new BigDecimal(balance.getBamount());
					BigDecimal bd_t = new BigDecimal(tranhistory.getThamount());
					// change the user balance 
					Balance balance1 = new Balance();
					balance1.setBuid(balance.getBuid());
					balance.setBcid(tranhistory.getCidout());
					BigDecimal bout_1 = new BigDecimal(list.get(1));
					balance.setBamount(bd_b.subtract(bd_t).add(bout_1)
							.toString());
					balanceService.withdrawal(balance);

					System.out.println("tranhistory.getCidin()    "
							+ tranhistory.getCidin());
					balance1.setBcid(tranhistory.getCidin());

					balance1 = balanceService.searchOneCurrOfUser(balance1);
					System.out.println("amount .......  " + balance1);

					BigDecimal bd_1 = new BigDecimal(list.get(0));
					BigDecimal bd_2 = new BigDecimal(balance1.getBamount());

					System.out.println("bd1    " + bd_1);
					System.out.println("bd2    " + bd_2);

					balance1.setBamount(bd_2.add(bd_1).toString());
					balanceService.deposit(balance1);
		
					this.response.getWriter().write("Success");
					session.removeAttribute("SequenceQueue");
					session.removeAttribute("Transaction_history");
				} else {
					// fail, return "Fail"
					this.response.getWriter().write("Fail");
				}
			} else {
				// if SequenceQueue,Transaction_history not exist
				this.response.getWriter().write("Fail");
			}
		} else {
			// cancel this transaction
			if ((session.getAttribute("SequenceQueue") != null)
					& (session.getAttribute("Transaction_history")) != null) {
				// if SequenceQueue,Transaction_history exist
				SequenceQueue<Double> sq = new SequenceQueue<Double>();
				sq = (SequenceQueue<Double>) session
						.getAttribute("SequenceQueue");
				tranhistory = (Transaction_history) session
						.getAttribute("Transaction_history");
				// execute
				Boolean bret = transactionhistoryservice.addNewTranHisForSure(
						tranhistory, sq, false);
				session.removeAttribute("SequenceQueue");
				session.removeAttribute("Transaction_history");
				this.response.getWriter().write("Success");
			} else {
				// if SequenceQueue,Transaction_history not exist
				this.response.getWriter().write("Fail");
				if (session.getAttribute("SequenceQueue") != null) {
					session.removeAttribute("SequenceQueue");
				}
				if (session.getAttribute("Transaction_history") != null) {
					session.removeAttribute("Transaction_history");
				}
			}
		}
		return null;
	}

	/**
	 * search transaction history
	 * 
	 * @param User
	 *            (no need uid)
	 * 
	 * @return Succeed:"SearchTransactionSuccess", fial:searchTFail
	 */
	@Action(value = "searchTransactionHistory")
	public String searchTransaction() throws IOException {
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
		user.setUid((int) session.getAttribute("userid"));
		// create a new Transaction_history object
		tranhistory = new Transaction_history();
		tranhistory.setThuid(user.getUid());
		// create a PageResults object to set the result data
		PageResults<Transaction_history> pageInfo = new PageResults<Transaction_history>();
		pageInfo.setPageNo(1);
		pageInfo.setPageSize(100);
		// check Transaction_history
		pageInfo = transactionhistoryservice.searchAllTranHisOfAUser(
				tranhistory, pageInfo);
		if (pageInfo != null) {
			// if succeed, return Transaction_history
			JSONArray jarray = JSONArray.fromObject(pageInfo.getResults());
			JSONObject respObject = JSONObject.fromObject(jarray);
			this.response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write(respObject.toString());
			return null;
		} else {
			// if failed, return "searchFail"
			this.response.getWriter().write("searchTFail");
			return null;
		}
	}
}
