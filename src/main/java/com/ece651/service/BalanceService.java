/**
 * 
 */
package com.ece651.service;

import java.io.Serializable;
import java.util.List;

import com.ece651.entity.Balance;

/**
 * @ClassName: BalanceService.java
 * @Description: Balance serivce interface
 * 
 * @author Freddy Lee
 * @version V1.0
 * @Date 2016.10.15  6:12:32 PM
 */
public interface BalanceService {

	public Serializable addNewCurrencyBalance(Balance balance);

	public int deposit(Balance balance);

	public int withdrawal(Balance balance);

	public int deleteCurrencyBalance(Balance balance);

	public List<Balance> searchAllBalOfUser(Balance balance);

	public Balance searchOneCurrOfUser(Balance balance);
	
	public Boolean searchOneCurrExist(Balance balance);

}
