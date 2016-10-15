/**
 * 
 */
package com.ece651.toolsUnits;

import java.util.ArrayList;

import com.ece651.entity.User;

/**
 * @ClassName: Tools.java
 * @Description: 系统使用的工具类(加密，解密，封装，SQL拼接)
 * 
 * @author Ruins7
 * @version V1.0
 * @Date 2016年10月14日 下午9:37:13
 */
public class Tools {

	/**
	 * 为User表拼接SQL 条件查询
	 */
	public static String packSQL(User user) {

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
	 * 封装 Object[] 类型的User
	 * @param Object[]
	 * @return User(一个)
	 */
	public static User packAUser(Object[] obj) {
		User user = new User();
		user.setUid((int) obj[0]);
		user.setUsername((String) obj[1]);
		user.setUsername((String) obj[2]);
		user.setEmail((String) obj[3]);
		return user;
	}

}
