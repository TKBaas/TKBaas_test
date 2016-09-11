package com.tk.baas.model;

import java.io.Serializable;

public class ChangePasswd implements Serializable{
	
	private static final long serialVersionUID = 3829439062679045106L;

	private String oldPasswd;
	
	private String newPasswd;

	public String getOldPasswd() {
		return oldPasswd;
	}

	public void setOldPasswd(String oldPasswd) {
		this.oldPasswd = oldPasswd;
	}

	public String getNewPasswd() {
		return newPasswd;
	}

	public void setNewPasswd(String newPasswd) {
		this.newPasswd = newPasswd;
	}
	
	
}
