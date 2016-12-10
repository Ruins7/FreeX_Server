/**
 * 
 */
package com.ece651.service;

import com.ece651.entity.PageResults;
import com.ece651.entity.Transaction_history;
import com.ece651.toolsUnits.SequenceQueue;

/**
 * @ClassName: TransactionHistoryService.java
 * @Description: Transaction_history serivce interface
 * @author Freddy Lee
 * @version V1.0
 * @Date 2016.10.16 11:25:10 AM
 */
public interface TransactionHistoryService {

	public SequenceQueue<Double> addNewTranHis(Transaction_history transactionHistory);
	
	public boolean addNewTranHisSimple(Transaction_history transactionHistory);
	
	public Boolean addNewTranHisForSure(Transaction_history transactionHistory, SequenceQueue<Double> queue, boolean ifproceed);

	public int deleTranHis(Transaction_history transactionHistory);

	public PageResults<Transaction_history> searchAllTranHis(PageResults pageInfo);

	public PageResults<Transaction_history> searchAllTranHisOfAUser(Transaction_history uid, PageResults pageInfo);

	public PageResults<Transaction_history> searchAllTranHisOfACurrencyOut(
			Transaction_history cidout, PageResults pageInfo);

	public PageResults<Transaction_history> searchAllTranHisOfACurrencyIn(
			Transaction_history cidin, PageResults pageInfo);

	public PageResults<Transaction_history> searchAllTranHisOfACurrencyInAndOut(
			Transaction_history cidoutandin, PageResults pageInfo);

}
