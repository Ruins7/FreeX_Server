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
 * @ClassName: User.java
 * @Description: TODO(用一句话描述该文件做什么)
 * 
 * @author Ruins7
 * @version V1.0
 * @Date 2016年9月30日 下午9:13:54
 */

@Entity
@Table(name = "USER", schema = "freex_db" )
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int uid;
	private String username;
	private String password;
	private String email;

	public User() {
		super();
	}

	public User(int uid, String username, String password, String email) {
		super();
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	@Id
	@Column(name = "UID", unique = true, nullable = false, length = 8)
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	@Column(name = "USERNAME", nullable = false, length = 256)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD", nullable = false, length = 256)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "EMAIL", nullable = false, length = 256)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
