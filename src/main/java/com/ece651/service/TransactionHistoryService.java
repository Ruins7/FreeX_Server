/**
 * 
 */
package com.ece651.service;

import java.io.Serializable;

import com.ece651.entity.PageResults;
import com.ece651.entity.Transaction_history;

/**
 * @ClassName: TransactionHistoryService.java
 * @Description: Transaction_history对象的业务逻辑类接口
 * 
 * @author Ruins7
 * @version V1.0
 * @Date 2016年10月16日 上午11:25:10
 */
public interface TransactionHistoryService {

	public Serializable addNewTranHis(Transaction_history transactionHistory);

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
