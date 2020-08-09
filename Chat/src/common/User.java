package common;

import java.io.Serializable;

public class User implements Serializable {

	private String uid;
	private String pwd;

	public User(String uid, String pwd) {
		this.uid = uid;
		this.pwd = pwd;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "User{" + "uid='" + uid + '\'' + ", pwd='" + pwd + '\'' + '}';
	}

}
