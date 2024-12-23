package bank.member;

import java.sql.Date;

public class MemberVO {
	private int	   no;
	private String id;
	private String pwd;
	private String name;
	private Date register_time;
	
	public MemberVO() {
		
	}
	
	public MemberVO(int no,String id, String pwd, String name, Date register_time) {
		super();
		this.no = no;
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.register_time = register_time;
	}
	
	
	public MemberVO(String id) {
		super();
		this.id = id;
	}

	public int getNo() {
		return no;
	}
	
	public void setId(int no) {
		this.no = no;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getRegister_time() {
		return register_time;
	}

	public void setRegister_time(Date register_time) {
		this.register_time = register_time;
	}

	@Override
	public String toString() {
		return "MemberVO [no = " + no + ", id=" + id + ", pwd=" + pwd + ", name=" + name + ", register_time=" + register_time + "]";
	}
	
	
	
}
