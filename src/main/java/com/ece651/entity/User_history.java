/**
 * 
 */
package com.ece651.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName:     User_history.java
 * @Description:   TODO(用一句话描述该文件做什么) 
 * 
 * @author         Ruins7
 * @version        V1.0  
 * @Date           2016年10月1日 下午2:03:01 
 */
@Entity
@Table(name = "USER_HISTORY", schema = "freex_db" )
public class User_history implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int uhid;
	private int uhuid;
	private Date uhtime;
	private String action;
	
	public User_history() {
		super();
	}

	public User_history(int uhid, int uhuid, Date uhtime, String action) {
		super();
		this.uhid = uhid;
		this.uhuid = uhuid;
		this.uhtime = uhtime;
		this.action = action;
	}

	@Id
	@Column(name = "UHID", unique = true, nullable = false, length = 8)
	public int getUhid() {
		return uhid;
	}

	public void setUhid(int uhid) {
		this.uhid = uhid;
	}

	@Column(name = "UHUID", nullable = false, length = 8)
	public int getUhuid() {
		return uhuid;
	}

	public void setUhuid(int uhuid) {
		this.uhuid = uhuid;
	}
	
	@Column(name = "UHTIME", nullable = false)
	public Date getUhtime() {
		return uhtime;
	}

	public void setUhtime(Date uhtime) {
		this.uhtime = uhtime;
	}

	@Column(name = "ACTION", nullable = false, length= 256)
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	
	
}
