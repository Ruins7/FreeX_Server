/**
 * 
 */
package com.ece651.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName:     Balance.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * 
 * @author         Ruins7
 * @version        V1.0  
 * @Date           2016年10月1日 下午1:58:30 
 */
@Entity
@Table(name = "BALANCE", schema = "freex_db" )
public class Balance implements Serializable{

	private int bid;
	private int buid;
	private int bcid;
	private BigDecimal bamount;
	
	public Balance() {
		super();
	}

	public Balance(int bid, int buid, int bcid, BigDecimal bamount) {
		super();
		this.bid = bid;
		this.buid = buid;
		this.bcid = bcid;
		this.bamount = bamount;
	}

	@Id
	@Column(name = "BID", unique = true, nullable = false, length = 8)
	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	@Column(name = "BUID", nullable = false, length = 8)
	public int getBuid() {
		return buid;
	}

	public void setBuid(int buid) {
		this.buid = buid;
	}

	@Column(name = "BCID", nullable = false, length = 8)
	public int getBcid() {
		return bcid;
	}

	public void setBcid(int bcid) {
		this.bcid = bcid;
	}

	@Column(name = "BAMOUNT", nullable = false)
	public BigDecimal getBamount() {
		return bamount;
	}

	public void setBamount(BigDecimal bamount) {
		this.bamount = bamount;
	}
	
	
	
	
}
