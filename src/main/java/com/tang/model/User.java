package com.tang.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class User implements Serializable{

	private static final long serialVersionUID = -8086691690484854980L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String userid;
	private String username;

	public User() {

	}

	public User(String userid, String username) {
		super();
		this.userid = userid;
		this.username = username;
	}

}
