/**
 * 
 */
package com.ece651.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName:     Transaction_history.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * 
 * @author         Ruins7
 * @version        V1.0  
 * @Date           2016年10月1日 下午2:06:32 
 */
public class Transaction_history implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int thid;
	private int thuid;
	private int cidout;
	private int cidin;
	private String thamount;
	private String rate;
	private Date thtime;
	
	public Transaction_history() {
		super();
	}

	public Transaction_history(int thid, int thuid, int cidout, int cidin,
			String thamount, String rate, Date thtime) {
		super();
		this.thid = thid;
		this.thuid = thuid;
		this.cidout = cidout;
		this.cidin = cidin;
		this.thamount = thamount;
		this.rate = rate;
		this.thtime = thtime;
	}

	public int getThid() {
		return thid;
	}

	public void setThid(int thid) {
		this.thid = thid;
	}

	public int getThuid() {
		return thuid;
	}

	public void setThuid(int thuid) {
		this.thuid = thuid;
	}

	public int getCidout() {
		return cidout;
	}

	public void setCidout(int cidout) {
		this.cidout = cidout;
	}

	public int getCidin() {
		return cidin;
	}

	public void setCidin(int cidin) {
		this.cidin = cidin;
	}

	public String getThamount() {
		return thamount;
	}

	public void setThamount(String thamount) {
		this.thamount = thamount;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public Date getThtime() {
		return thtime;
	}

	public void setThtime(Date thtime) {
		this.thtime = thtime;
	}

	

}
