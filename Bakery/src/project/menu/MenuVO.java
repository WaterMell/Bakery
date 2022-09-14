package project.menu;

import java.text.DecimalFormat;

public class MenuVO {
	private int food_id;
	private String food_name;
	private int price;
	private String food_info;
	
	
	public MenuVO(int food_id, String food_name, int price, String food_info) {
		super();
		this.food_id = food_id;
		this.food_name = food_name;
		this.price = price;
		this.food_info = food_info;
	}
	
	public MenuVO(int food_id, String food_name, int price) {
		super();
		this.food_id = food_id;
		this.food_name = food_name;
		this.price = price;
	}
	
	public MenuVO(String food_name, int price) {
		super();
		this.food_name = food_name;
		this.price = price;
	}



	public int getFood_id() {
		return food_id;
	}
	public void setFood_id(int food_id) {
		this.food_id = food_id;
	}
	public String getFood_name() {
		return food_name;
	}
	public void setFood_name(String food_name) {
		this.food_name = food_name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat();
		String money = df.format(price);
		return "제품번호 : " + food_id + ", 제품명 : " + food_name + ", 가격 : ￦" + money + "\n"
						+ ">> 상세 : " + food_info;
	}
	
	
}
