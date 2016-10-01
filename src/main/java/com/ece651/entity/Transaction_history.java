/**
 * 
 */
package com.ece651.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName:     Transaction_history.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * 
 * @author         Ruins7
 * @version        V1.0  
 * @Date           2016年10月1日 下午2:06:32 
 */
@Entity
@Table(name = "TRANSACTION_HISTORY", schema = "freex_db" )
public class Transaction_history implements Serializable{
	
	private int thid;
	private int thuid;
	private int cidout;
	private int cidin;
	private BigDecimal thamount;
	private Double rate;
	private Date thtime;
	
	public Transaction_history() {
		super();
	}

	public Transaction_history(int thid, int thuid, int cidout, int cidin,
			BigDecimal thamount, Double rate, Date thtime) {
		super();
		this.thid = thid;
		this.thuid = thuid;
		this.cidout = cidout;
		this.cidin = cidin;
		this.thamount = thamount;
		this.rate = rate;
		this.thtime = thtime;
	}

	@Id
	@Column(name = "THID", unique = true, nullable = false, length = 8)
	public int getThid() {
		return thid;
	}

	public void setThid(int thid) {
		this.thid = thid;
	}

	@Column(name = "THUID", nullable = false, length = 8)
	public int getThuid() {
		return thuid;
	}

	public void setThuid(int thuid) {
		this.thuid = thuid;
	}

	@Column(name = "CIDOUT", length = 8)
	public int getCidout() {
		return cidout;
	}

	public void setCidout(int cidout) {
		this.cidout = cidout;
	}

	@Column(name = "CIDIN", length = 8)
	public int getCidin() {
		return cidin;
	}

	public void setCidin(int cidin) {
		this.cidin = cidin;
	}

	@Column(name = "THAMOUNT", nullable = false)
	public BigDecimal getThamount() {
		return thamount;
	}

	public void setThamount(BigDecimal thamount) {
		this.thamount = thamount;
	}

	@Column(name = "RATE", nullable = false)
	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@Column(name = "THTIME", nullable = false)
	public Date getThtime() {
		return thtime;
	}

	public void setThtime(Date thtime) {
		this.thtime = thtime;
	}
	
	
	

}
