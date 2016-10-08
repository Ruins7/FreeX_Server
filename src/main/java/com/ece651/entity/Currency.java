/**
 * 
 */
package com.ece651.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: Currency.java
 * @Description: TODO(用一句话描述该文件做什么)
 * 
 * @author Ruins7
 * @version V1.0
 * @Date 2016年10月1日 下午1:55:05
 */

@Entity
@Table(name = "CURRENCY", schema = "freex_db")
public class Currency implements Serializable {

	/**
	 * 
	 */
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

	@Id
	@Column(name = "CID", unique = true, nullable = false, length = 8)
	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	@Column(name = "CNAME", nullable = false, length = 256)
	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}
}
