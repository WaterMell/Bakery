package project.book;

import java.sql.Date;
import java.sql.Timestamp;

public class BookVO {
	private int book_Id;
	private String bookDate;
	private int cust_Id;
	private int food_Id;

	public BookVO(int book_Id, String bookDate, int cust_Id, int food_Id) {
		super();
		this.book_Id = book_Id;
		this.bookDate = bookDate;
		this.cust_Id = cust_Id;
		this.food_Id = food_Id;
	}
	

	public BookVO(String bookDate, int cust_Id, int food_Id) {
		super();
		this.bookDate = bookDate;
		this.cust_Id = cust_Id;
		this.food_Id = food_Id;
	}

	public int getBook_Id() {
		return book_Id;
	}

	public void setBook_Id(int book_Id) {
		this.book_Id = book_Id;
	}

	public String getBookDate() {
		return bookDate;
	}

	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}


	public int getCust_Id() {
		return cust_Id;
	}

	public void setCust_Id(int cust_Id) {
		this.cust_Id = cust_Id;
	}

	public int getFood_Id() {
		return food_Id;
	}

	public void setFood_Id(int food_Id) {
		this.food_Id = food_Id;
	}

	@Override
	public String toString() {
		return "BookVO [book_Id=" + book_Id + ", bookDate=" + bookDate  + ", cust_Id="
				+ cust_Id + ", food_Id=" + food_Id + "]";
	}
	
	
}
