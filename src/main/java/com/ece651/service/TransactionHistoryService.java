/**
 * 
 */
package com.ece651.service;

import java.io.Serializable;
import java.util.List;

import com.ece651.entity.PageResults;
import com.ece651.entity.Transaction_history;

/**
 * @ClassName: TransactionHistoryService.java
 * @Description: Transaction_history serivce interface
 * @author Freddy Lee
 * @version V1.0
 * @Date 2016.10.16 11:25:10 AM
 */
public interface TransactionHistoryService {

	public List<Double> addNewTranHis(Transaction_history transactionHistory);

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
