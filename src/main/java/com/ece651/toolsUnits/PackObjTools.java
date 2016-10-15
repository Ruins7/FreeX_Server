/**
 * 
 */
package com.ece651.toolsUnits;

import com.ece651.entity.Balance;
import com.ece651.entity.Transaction_history;
import com.ece651.entity.User;

import java.util.Date;

/**
 * @ClassName:     PackObjTools.java
 * @Description:   用于封装单个对象的工具类 （暂时没有用）
 * 
 * @author         Ruins7
 * @version        V1.0  
 * @Date           2016年10月15日 下午2:38:12 
 */
public class PackObjTools {

	/**
	 * 封装 Object[] 类型的User
	 * @param Object[]
	 * @return User(single obj)
	 */
	public static User packAUser(Object[] obj) {
		User user = new User();
		user.setUid((int) obj[0]);
		user.setUsername((String) obj[1]);
		user.setUsername((String) obj[2]);
		user.setEmail((String) obj[3]);
		return user;
	}
	
	/**
	 * 封装 Object[] 类型的Balance
	 * @param Object[]
	 * @return Balance(single obj)
	 */
	public static Balance packABalance(Object[] obj) {
		Balance balance = new Balance();
		balance.setBid((int) obj[0]);
		balance.setBuid((int) obj[1]);
		balance.setBcid((int) obj[2]);
		balance.setBamount((String) obj[3]);
		return balance;
	}
	
	/**
	 * 封装 Object[] 类型的Transaction_history
	 * @param Object[]
	 * @return Transaction_history(single obj)
	 */
	public static Transaction_history packATransaction_history(Object[] obj) {
		Transaction_history transactionHistory = new Transaction_history();
		transactionHistory.setThid((int) obj[0]);
		transactionHistory.setThuid((int) obj[1]);
		transactionHistory.setCidout((int) obj[2]);
		transactionHistory.setCidin((int) obj[3]);
		transactionHistory.setThamount((String) obj[4]);
		transactionHistory.setRate((String) obj[5]);
		transactionHistory.setThtime((Date) obj[6]);
		
		return transactionHistory;
	}
}
