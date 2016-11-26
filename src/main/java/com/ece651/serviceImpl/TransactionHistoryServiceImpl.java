/**
 * 
 */
package com.ece651.serviceImpl;

import java.io.Serializable;
import java.util.Date;

import com.ece651.dao.TransactionHistoryDao;
import com.ece651.entity.PageResults;
import com.ece651.entity.Transaction_history;
import com.ece651.service.TransactionHistoryService;
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
	public Serializable addNewTranHis(Transaction_history transactionHistory) {
		//TODO 添加逻辑
		Double amount = Double.valueOf(transactionHistory.getThamount());
		Double rate = Double.valueOf(transactionHistory.getRate());
		Traderinfo tradeinfo = new Traderinfo(transactionHistory.getThuid(),transactionHistory.getCidout()*10+transactionHistory.getCidin(),amount,rate, transactionHistory.getThtime().getDay());
		Seller_Stack sellstack = new Seller_Stack();
		sellstack = Trade.match(tradeinfo, sellstack);
		int i = sellstack.stackpoptradere();
			double amount_avail = sellstack.stackpops();
			double amount_left = sellstack.stackpops();
			double rate_result = amount_avail/(amount - amount_left);
			//database
			transactionHistory.setThamount((String.valueOf(amount_avail/rate_result)));
			transactionHistory.setRate(String.valueOf(rate_result));
		return transactionHistoryDao.insertNewTranHistory(transactionHistory);
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
