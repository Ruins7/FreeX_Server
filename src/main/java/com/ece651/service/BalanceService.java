/**
 * 
 */
package com.ece651.service;

import java.io.Serializable;
import java.util.List;

import com.ece651.entity.Balance;
import com.ece651.entity.Currency;
import com.ece651.entity.User;

/**
 * @ClassName: BalanceService.java
 * @Description: Balance对象的业务逻辑类接口
 * 
 * @author Ruins7
 * @version V1.0
 * @Date 2016年10月15日 下午6:12:32
 */
public interface BalanceService {

	public Serializable addNewCurrencyBalance(Balance balance);

	public int deposit(Balance balance);

	public int withdrawal(Balance balance);

	public int deleteCurrencyBalance(Balance balance);

	public List<Balance> searchAllBalOfUser(Balance balance);

	public Balance searchOneCurrOfUser(User user, Currency currency);

}
