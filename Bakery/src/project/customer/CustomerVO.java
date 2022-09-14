package project.customer;

public class CustomerVO {
	private int cust_id;
	private String id;
	private String password;
	private String name;
	private String phone;
	
	public CustomerVO(int cust_id, String id, String password, String name, String phone) {
		super();
		this.cust_id = cust_id;
		this.id = id;
		this.password = password;
		this.name = name;
		this.phone = phone;
	}
	
	public CustomerVO(String id, String password, String name, String phone) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.phone = phone;
	}
	public int getCust_id() {
		return cust_id;
	}
	public void setCust_id(int cust_id) {
		this.cust_id = cust_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpassword() {
		return password;
	}
	public void setpassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	@Override
	public String toString() {
		return "고객번호 : " + cust_id + "\n"
				+ "아  이  디 : " + id + "\n"
				+ "비밀번호 : " + password + "\n"
				+ "성       명 : " + name + "\n"
				+ "전화번호 : " + phone + "\n";
	}
	
	
	
	
	
	
}
