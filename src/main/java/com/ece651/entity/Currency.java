/**
 * 
 */
package com.ece651.entity;

import java.io.Serializable;

/**
 * @ClassName: Currency.java
 * @Description: TODO(用一句话描述该文件做什么)
 * 
 * @author Ruins7
 * @version V1.0
 * @Date 2016年10月1日 下午1:55:05
 */

public class Currency implements Serializable {

	private static final long serialVersionUID = 1L;
	private int cid;
	private String cname;

	public Currency() {
		super();
	}

	public Currency(int cid, String cname) {
		super();
		this.cid = cid;
		this.cname = cname;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	
}
