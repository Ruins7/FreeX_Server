/**
 * 
 */
package com.ece651.serviceImpl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ece651.dao.TransactionHistoryDao;
import com.ece651.entity.PageResults;
import com.ece651.entity.Transaction_history;
import com.ece651.service.TransactionHistoryService;
import com.ece651.toolsUnits.h2.H2currenyPool;
import com.ece651.toolsUnits.h2.Seller_Stack;
import com.ece651.toolsUnits.h2.Trade;
import com.ece651.toolsUnits.h2.Traderinfo;

/**
 * @ClassName: TransactionHistoryServiceImpl.java
 * @Description: Transaction_history对象的业务逻辑类
 * @author Freddy Lee
 * @version V1.0
 * @Date 2016.10.16 11:31:24 AM
 */
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

	private TransactionHistoryDao transactionHistoryDao;
	private List<Double> list;

	/**
	 * 注入
	 */
	public void setTransactionHistoryDao(
			TransactionHistoryDao transactionHistoryDao) {
		this.transactionHistoryDao = transactionHistoryDao;
	}

	/**
	 * 新增交易记录
	 * 
	 * @param Transaction_history
	 *            (no need thid)
	 * @return thid(Serializable)
	 */
	@Override
	public List<Double> addNewTranHis(Transaction_history transactionHistory) {
		// TODO 添加逻辑
		System.out.println("ssdfsdfsdf----------------:" + transactionHistory.getThamount()+ "  "+transactionHistory.getThuid()+"  "+transactionHistory.getCidin()+"  "+transactionHistory.getCidout() );
		try {
			H2currenyPool.readtable();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		list = new ArrayList<Double>();
		System.out.println("tradeifo amount test111");
		Double amount = Double.valueOf(transactionHistory.getThamount());
		Double rate = Double.valueOf(0);
		System.out.println("tradeifo amount test");
		int cidtrade=transactionHistory.getCidout() * 10+ transactionHistory.getCidin();
		
		Traderinfo tradeinfo = new Traderinfo(transactionHistory.getThuid(),
				cidtrade, rate,amount,
				0);
		//transactionHistory.getThtime().getDay()
		System.out.println("tradeifo amount");
		//System.out.println("tradeifo amount" + tradeinfo.getamount()+" cid" +tradeinfo.getcid());

		Seller_Stack sellstack = new Seller_Stack();
		sellstack = Trade.match(tradeinfo, sellstack);
		int i = sellstack.stackpoptradere();
		double amount_avail = sellstack.stackpops();// in
		double amount_left = sellstack.stackpops();// out
		double rate_result = amount_avail / (amount - amount_left);
System.out.println("avali----------------:" + amount_avail +"   amount_left:" +amount_left);
		list.add(amount_avail);
		list.add(amount_left);
		list.add(rate_result);
		// database
		transactionHistory.setThamount((String.valueOf(amount_avail
				/ rate_result)));
		transactionHistory.setRate(String.valueOf(rate_result));
		transactionHistoryDao.insertNewTranHistory(transactionHistory);
		return list;
	}

	/**
	 * 删除交易记录
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
	 * 查询所有交易记录(分页)
	 * 
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
	 * 查询某个user所有交易记录(分页)
	 * 
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
	 * 查询某个币种所有卖出的交易记录(分页)
	 * 
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
	 * 查询某个币种所有买入的交易记录(分页)
	 * 
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
	 * 查询某个币种所有买入卖出的交易记录(分页)
	 * 
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
