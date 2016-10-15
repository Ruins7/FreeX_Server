/**
 * 
 */
package com.ece651.toolsUnits;

import java.util.ArrayList;

import com.ece651.entity.Balance;
import com.ece651.entity.Transaction_history;
import com.ece651.entity.User;

/**
 * @ClassName:     PackSQLTools.java
 * @Description:   用于拼接SQL查询语句的工具类 
 * 
 * @author         Ruins7
 * @version        V1.0  
 * @Date           2016年10月15日 下午2:37:49 
 */
public class PackSQLTools {

	/**
	 * 为User表拼接SQL 条件查询
	 * @return String 
	 * @param User
	 */
	public static String packSQL(User user) {
		if(user == null){
			return "select * from user";
		}
		StringBuffer sqlselect = new StringBuffer("select * from user where");
		StringBuffer sqlid = new StringBuffer(" uid = ?");
		StringBuffer sqlname = new StringBuffer(" username = ?");
		StringBuffer sqlpwd = new StringBuffer(" password = ?");
		StringBuffer sqlemail = new StringBuffer(" email = ?");
		ArrayList<StringBuffer> list = new ArrayList<StringBuffer>();

		if (user.getUid() != 0) {
			list.add(sqlid);
		}
		if (user.getUsername() != null) {
			list.add(sqlname);
		}
		if (user.getPassword() != null) {
			list.add(sqlpwd);
		}
		if (user.getEmail() != null) {
			list.add(sqlemail);
		}

		for (int i = 0; i < list.size(); i++) {
			sqlselect.append(list.get(i));
		}

		// 添加and
		for (int i = 0; i < sqlselect.length(); i++) {
			if (sqlselect.charAt(i) == '?' && i != sqlselect.length() - 1) {
				sqlselect.insert(i + 1, " and");
			}
		}
		return sqlselect.toString();
	}
	
	/**
	 * 为Balance表拼接SQL 条件查询
	 * @return String 
	 * @param Balance
	 */
	public static String packSQL(Balance balance) {
		if(balance == null){
			return "select * from balance";
		}
		StringBuffer sqlselect = new StringBuffer("select * from balance where");
		StringBuffer sqlid = new StringBuffer(" bid = ?");
		StringBuffer sqluid = new StringBuffer(" buid = ?");
		StringBuffer sqlcid = new StringBuffer(" bcid = ?");
		StringBuffer sqlamount = new StringBuffer(" bamount = ?");
		ArrayList<StringBuffer> list = new ArrayList<StringBuffer>();

		if (balance.getBid() != 0) {
			list.add(sqlid);
		}
		if (balance.getBuid() != 0) {
			list.add(sqluid);
		}
		if (balance.getBcid() != 0) {
			list.add(sqlcid);
		}
		if (balance.getBamount() != null) {
			list.add(sqlamount);
		}

		for (int i = 0; i < list.size(); i++) {
			sqlselect.append(list.get(i));
		}

		// 添加and
		for (int i = 0; i < sqlselect.length(); i++) {
			if (sqlselect.charAt(i) == '?' && i != sqlselect.length() - 1) {
				sqlselect.insert(i + 1, " and");
			}
		}
		return sqlselect.toString();
	}
	
	/**
	 * 为Transaction_history表拼接SQL 条件查询
	 * @return String 
	 * @param Transaction_history
	 */
	public static String packSQL(Transaction_history transactionHistory) {
		if(transactionHistory == null){
			return "select * from transaction_history";
		}
		StringBuffer sqlselect = new StringBuffer("select * from transaction_history where");
		StringBuffer sqlid = new StringBuffer(" thid = ?");
		StringBuffer sqlthuid = new StringBuffer(" thuid = ?");
		StringBuffer sqlcidout = new StringBuffer(" cidout = ?");
		StringBuffer sqlcidin = new StringBuffer(" cidin = ?");
		StringBuffer sqlmount = new StringBuffer(" thamount = ?");
		StringBuffer sqlrate = new StringBuffer(" rate = ?");
		StringBuffer sqltime = new StringBuffer(" thtime = ?");
		ArrayList<StringBuffer> list = new ArrayList<StringBuffer>();

		if (transactionHistory.getThid() != 0) {
			list.add(sqlid);
		}
		if (transactionHistory.getThuid() != 0) {
			list.add(sqlthuid);
		}
		if (transactionHistory.getCidout() != 0) {
			list.add(sqlcidout);
		}
		if (transactionHistory.getCidin() != 0) {
			list.add(sqlcidin);
		}
		if (transactionHistory.getThamount() != null) {
			list.add(sqlmount);
		}
		if (transactionHistory.getRate() != null) {
			list.add(sqlrate);
		}
		if (transactionHistory.getThtime() != null) {
			list.add(sqltime);
		}

		for (int i = 0; i < list.size(); i++) {
			sqlselect.append(list.get(i));
		}

		// 添加and
		for (int i = 0; i < sqlselect.length(); i++) {
			if (sqlselect.charAt(i) == '?' && i != sqlselect.length() - 1) {
				sqlselect.insert(i + 1, " and");
			}
		}
		return sqlselect.toString();
	}
}
