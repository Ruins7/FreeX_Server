/**
 * 
 */
package com.ece651.toolsUnits;

import java.util.ArrayList;

import com.ece651.entity.Balance;
import com.ece651.entity.Transaction_history;
import com.ece651.entity.User;

/**
 * @ClassName:     PackValues.java
 * @Description:   用于拼接SQL查询条件的工具类
 * 
 * @author         Ruins7
 * @version        V1.0  
 * @Date           2016年10月15日 下午2:37:27 
 */
public class PackValuesTools {

	/**
	 * 为User表拼接查询条件Values
	 * @return Object[]
	 * @param User
	 */
	public static Object[] packValues(User user){
		if(user == null){
			return null;
		}
		ArrayList list = new ArrayList();
		if (user.getUid() != 0) {
			list.add(user.getUid());
		}
		if (user.getUsername() != null) {
			list.add(user.getUsername());
		}
		if (user.getPassword() != null) {
			list.add(user.getPassword());
		}
		if (user.getEmail() != null) {
			list.add(user.getEmail());
		}
		
		Object[] values = new Object[list.size()];
		for (int i = 0; i < list.size(); i++) {
			values[i] = list.get(i);
		}
		return values;
	}
	
	/**
	 * 为Balance表拼接查询条件Values
	 * @return Object[]
	 * @param Balance
	 */
	public static Object[] packValues(Balance balance){
		
		ArrayList list = new ArrayList();
		if (balance.getBid() != 0) {
			list.add(balance.getBid());
		}
		if (balance.getBuid() != 0) {
			list.add(balance.getBuid());
		}
		if (balance.getBcid() != 0) {
			list.add(balance.getBcid());
		}
		if (balance.getBamount() != null) {
			list.add(balance.getBamount());
		}
		
		Object[] values = new Object[list.size()];
		for (int i = 0; i < list.size(); i++) {
			values[i] = list.get(i);
		}
		return values;
	}
	
	/**
	 * 为Transaction_history表拼接查询条件Values
	 * @return Object[]
	 * @param Transaction_history
	 */
	public static Object[] packValues(Transaction_history transactionHistory){
		
		ArrayList list = new ArrayList();
		if (transactionHistory.getThid() != 0) {
			list.add(transactionHistory.getThid());
		}
		if (transactionHistory.getThuid() != 0) {
			list.add(transactionHistory.getThuid());
		}
		if (transactionHistory.getCidout() != 0) {
			list.add(transactionHistory.getCidout());
		}
		if (transactionHistory.getCidin() != 0) {
			list.add(transactionHistory.getCidin());
		}
		if (transactionHistory.getThamount() != null) {
			list.add(transactionHistory.getThamount());
		}
		if (transactionHistory.getRate() != null) {
			list.add(transactionHistory.getRate());
		}
		if (transactionHistory.getThtime() != null) {
			list.add(transactionHistory.getThtime());
		}
		
		Object[] values = new Object[list.size()];
		for (int i = 0; i < list.size(); i++) {
			values[i] = list.get(i);
		}
		return values;
	}
}
