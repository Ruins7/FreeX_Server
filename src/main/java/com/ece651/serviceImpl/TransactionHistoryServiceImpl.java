/**
 * 
 */
package com.ece651.serviceImpl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import com.ece651.dao.TransactionHistoryDao;
import com.ece651.entity.PageResults;
import com.ece651.entity.Transaction_history;
import com.ece651.service.TransactionHistoryService;
import com.ece651.toolsUnits.SequenceQueue;
import com.ece651.toolsUnits.h2.H2currenyPool;
import com.ece651.toolsUnits.h2.Seller_Stack;
import com.ece651.toolsUnits.h2.Trade;
import com.ece651.toolsUnits.h2.Traderinfo;

/**
 * @ClassName: TransactionHistoryServiceImpl.java
 * @Description: Transaction_history object service layer
 * @author Freddy Lee
 * @version V1.0
 * @Date 2016.10.16 11:31:24 AM
 */
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

	private TransactionHistoryDao transactionHistoryDao;
	private List<Double> list;

	/**
	 * injection
	 */
	public void setTransactionHistoryDao(
			TransactionHistoryDao transactionHistoryDao) {
		this.transactionHistoryDao = transactionHistoryDao;
	}

	/**
	 * add new transaction history
	 * @param Transaction_history
	 *            (no need thid)
	 * @return thid(Serializable)
	 */
	@Override
	public SequenceQueue<Double>addNewTranHis(
			Transaction_history transactionHistory) {
		// try {
		// H2currenyPool.readtable();
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		Double amount = Double.valueOf(transactionHistory.getThamount());
		Double rate = Double.valueOf(0);
		System.out.println("tradeifo amount test");
		int cidtrade = transactionHistory.getCidout() * 10
				+ transactionHistory.getCidin();

		Traderinfo tradeinfo = new Traderinfo(transactionHistory.getThuid(),
				cidtrade, rate, amount, 0);
		// transactionHistory.getThtime().getDay()
		System.out.println("tradeifo amount");
		// System.out.println("tradeifo amount" + tradeinfo.getamount()+" cid"
		// +tradeinfo.getcid());

		Seller_Stack sellstack = new Seller_Stack();
		sellstack = Trade.match(tradeinfo, sellstack);

		int i = sellstack.stackpoptradere();
		SequenceQueue<Double> queue = new SequenceQueue<Double>();
		
		
		double amount_avail = sellstack.stackpops();// in
		double amount_left = sellstack.stackpops();// out
		double rate_result = amount_avail / (amount - amount_left);

		queue.add(rate_result);
		queue.add(amount_left);
		queue.add(amount_avail);

		while (!sellstack.ac.empty()) {
			queue.add((double) sellstack.stackpoptradere());
		}

		//stack clear to varify the next round		
		// sellstack.ac.clear();
		return queue;
	}
	
	@Override
	public boolean addNewTranHisSimple(Transaction_history transactionHistory) {
		// TODO Auto-generated method stub
		Serializable ss = null;
		Boolean b = false;
		ss = transactionHistoryDao.insertNewTranHistory(transactionHistory);
		if(ss != null){
			b = true;
		}
		return b;
	}

	@Override
	public Boolean addNewTranHisForSure(Transaction_history transactionHistory,
			SequenceQueue<Double> queue, boolean ifproceed) {
		// TODO Auto-generated method stub
		boolean result = false;
		
		while (queue.length() >= 5) {
			Traderinfo sellinfo = new Traderinfo(0, 0, 0, 0, 0);
			sellinfo.settime((int)Math.round(queue.element()));
			queue.remove();
			double amount = ((double) queue.element()) / 10000;
			queue.remove();
			System.out.println("amount pop" + amount);
			sellinfo.setamount(amount);
			double rate = ((double) queue.element()) / 10000;
			queue.remove();
			sellinfo.setrate(rate);
			sellinfo.setcid((int)Math.round(queue.element()));
			queue.remove();
			sellinfo.setID((int)Math.round(queue.element()));
			queue.remove();
			
			try {
				H2currenyPool.update_notrade(sellinfo);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// add exception
		}

		double rate_result = queue.element();
		queue.remove();
		double amount_left = queue.element();
		queue.remove();
		double amount_avail  = queue.element();
		queue.remove();
		

		if (ifproceed) {

			// database
			transactionHistory.setThamount((String.valueOf(amount_avail
					/ rate_result)));
			transactionHistory.setRate(String.valueOf(rate_result));
			Serializable ss = transactionHistoryDao
					.insertNewTranHistory(transactionHistory);
			if (ss == null) {
				result = false;
			}
			result = true;
		}

		return result;
	}

	/**
	 * delete transaction history
	 * 
	 * @param Transaction_history
	 * @return int,1=true,0=false
	 */
	@Override
	public int deleTranHis(Transaction_history transactionHistory) {
		// TODO 添加逻辑

		return transactionHistoryDao.deleteTranHistory(transactionHistory);
	}

	/**
	 * search all transaction history by page
	 * @param PageResults
	 * @return PageResults<Transaction_history>
	 */
	@Override
	public PageResults<Transaction_history> searchAllTranHis(
			PageResults pageInfo) {
		// TODO 添加逻辑

		return transactionHistoryDao.findMoreByCondition(null, pageInfo);
	}

	/**
	 * search a user transaction history by page
	 * @param Transaction_history
	 *            , PageResults
	 * @return PageResults<Transaction_history>
	 */
	@Override
	public PageResults<Transaction_history> searchAllTranHisOfAUser(
			Transaction_history uid, PageResults pageInfo) {
		// TODO 添加逻辑

		return transactionHistoryDao.findMoreByCondition(uid, pageInfo);
	}

	/**
	 * search all sell out transaction history for a certain currency by page
	 * @param Transaction_history
	 *            , PageResults
	 * @return PageResults<Transaction_history>
	 */
	@Override
	public PageResults<Transaction_history> searchAllTranHisOfACurrencyOut(
			Transaction_history cidout, PageResults pageInfo) {
		// TODO 添加逻辑

		return transactionHistoryDao.findMoreByCondition(cidout, pageInfo);
	}

	/**
	 * search all buy in transaction history for a certain currency by page
	 * @param Transaction_history
	 *            , PageResults
	 * @return PageResults<Transaction_history>
	 */
	@Override
	public PageResults<Transaction_history> searchAllTranHisOfACurrencyIn(
			Transaction_history cidin, PageResults pageInfo) {
		// TODO 添加逻辑

		return transactionHistoryDao.findMoreByCondition(cidin, pageInfo);
	}

	/**
	 * search all buy in and sell out transaction history for a certain currency by page
	 * @param Transaction_history
	 *            (cidout and cidin), PageResults
	 * @return PageResults<Transaction_history>
	 */
	@Override
	public PageResults<Transaction_history> searchAllTranHisOfACurrencyInAndOut(
			Transaction_history cidoutandin, PageResults pageInfo) {
		// TODO 添加逻辑

		return transactionHistoryDao.findMoreByCondition(cidoutandin, pageInfo);
	}

	

}
