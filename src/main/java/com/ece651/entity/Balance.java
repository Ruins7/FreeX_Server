/**
 * 
 */
package com.ece651.entity;

import java.io.Serializable;

/**
 * @ClassName:     Balance.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * 
 * @author         Ruins7
 * @version        V1.0  
 * @Date           2016年10月1日 下午1:58:30 
 */
public class Balance implements Serializable{

	private static final long serialVersionUID = 1L;
	private int bid;
	private int buid;
	private int bcid;
	private String bamount;
	
	public Balance() {
		super();
	}

	public Balance(int bid, int buid, int bcid, String bamount) {
		super();
		this.bid = bid;
		this.buid = buid;
		this.bcid = bcid;
		this.bamount = bamount;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public int getBuid() {
		return buid;
	}

	public void setBuid(int buid) {
		this.buid = buid;
	}

	public int getBcid() {
		return bcid;
	}

	public void setBcid(int bcid) {
		this.bcid = bcid;
	}

	public String getBamount() {
		return bamount;
	}

	public void setBamount(String bamount) {
		this.bamount = bamount;
	}

	
}

