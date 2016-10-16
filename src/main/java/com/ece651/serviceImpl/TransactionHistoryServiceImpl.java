/**
 * 
 */
package com.ece651.serviceImpl;

import java.io.Serializable;

import com.ece651.dao.TransactionHistoryDao;
import com.ece651.entity.PageResults;
import com.ece651.entity.Transaction_history;
import com.ece651.service.TransactionHistoryService;

/**
 * @ClassName:     TransactionHistoryServiceImpl.java
 * @Description:   Transaction_history对象的业务逻辑类 
 * 
 * @author         Ruins7
 * @version        V1.0  
 * @Date           2016年10月16日 上午11:31:24 
 */
public class TransactionHistoryServiceImpl implements TransactionHistoryService{

	private TransactionHistoryDao transactionHistoryDao;
	
	/**
     * 注入
     */
	public void setTransactionHistoryDao(TransactionHistoryDao transactionHistoryDao) {
		this.transactionHistoryDao = transactionHistoryDao;
	}

	/**
     * 新增交易记录
     * @param Transaction_history(no need thid)
     * @return thid(Serializable)
     */
	@Override
	public Serializable addNewTranHis(Transaction_history transactionHistory) {
		//TODO 添加逻辑
		
		return transactionHistoryDao.insertNewTranHistory(transactionHistory);
	}

	/**
     * 删除交易记录
     * @param Transaction_history
     * @return int,1=true,0=false
     */
	@Override
	public int deleTranHis(Transaction_history transactionHistory) {
		//TODO 添加逻辑		
		
		return transactionHistoryDao.deleteTranHistory(transactionHistory);
	}

	/**
     * 查询所有交易记录(分页)
     * @param PageResults
     * @return PageResults
     */
	@Override
	public PageResults<Transaction_history> searchAllTranHis(PageResults pageInfo) {
		//TODO 添加逻辑
		
		return transactionHistoryDao.findMoreByCondition(null, pageInfo);	
	}

	/**
     * 查询某个user所有交易记录(分页)
     * @param Transaction_history, PageResults
     * @return PageResults<Transaction_history>
     */
	@Override
	public PageResults<Transaction_history> searchAllTranHisOfAUser(Transaction_history uid, PageResults pageInfo) {
		//TODO 添加逻辑
		
		return transactionHistoryDao.findMoreByCondition(uid, pageInfo);
	}

	/**
     * 查询某个币种所有卖出的交易记录(分页)
     * @param Transaction_history, PageResults
     * @return PageResults<Transaction_history>
     */
	@Override
	public PageResults<Transaction_history> searchAllTranHisOfACurrencyOut(
			Transaction_history cidout, PageResults pageInfo) {
		//TODO 添加逻辑
	
		return transactionHistoryDao.findMoreByCondition(cidout, pageInfo);
	}

	/**
     * 查询某个币种所有买入的交易记录(分页)
     * @param Transaction_history, PageResults
     * @return PageResults<Transaction_history>
     */
	@Override
	public PageResults<Transaction_history> searchAllTranHisOfACurrencyIn(
			Transaction_history cidin, PageResults pageInfo) {
		//TODO 添加逻辑
		
		return transactionHistoryDao.findMoreByCondition(cidin, pageInfo);
	}

	/**
     * 查询某个币种所有买入卖出的交易记录(分页)
     * @param Transaction_history(cidout and cidin), PageResults
     * @return PageResults<Transaction_history>
     */
	@Override
	public PageResults<Transaction_history> searchAllTranHisOfACurrencyInAndOut(
			Transaction_history cidoutandin, PageResults pageInfo) {
		//TODO 添加逻辑
		
		return transactionHistoryDao.findMoreByCondition(cidoutandin, pageInfo);
	}

	

}
