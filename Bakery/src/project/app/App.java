package project.app;

import java.awt.Menu;
import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.SimpleFormatter;

import project.book.BookDAO;
import project.book.BookVO;
import project.customer.CustomerDAO;
import project.customer.CustomerVO;
import project.menu.MenuDAO;
import project.menu.MenuVO;


public class App {
	private MenuDAO mDao = new MenuDAO();
	private BookDAO bDao = new BookDAO();
	private CustomerDAO cDao = new CustomerDAO();
	private String id;
	private String password;
	private Scanner scan = new Scanner(System.in);
	private MySQLAccess msql = new MySQLAccess();

	public static void main(String[] arg) {
		App app = new App();
		
		app.startPage();
		
	}
	
	public void startPage() {
		Scanner scan = new Scanner(System.in);
	    System.out.println("==========================");
	    System.out.println("[BAKERY에 오신 걸 환영합니다!]");
	    System.out.println("로그인을 하셔야 이용하실 수 있습니다.  ");
	    System.out.println("==========================");
	    System.out.println("1.로그인" + "\t" + "2.회원가입 ");

		int num = scan.nextInt();
		
		if (num == 1) {
			logIn();
			
		} else if (num == 2) {
			// 회원가입
			createUser();
		} else {
			System.out.println("다시 눌러주세요");
			startPage();
		}
		
	}
	
	public void logIn() {
		Scanner scan = new Scanner(System.in);
		System.out.print("ID : ");
		String str = scan.nextLine();
		id = str;
		
		System.out.print("PW : ");
		String str1 = scan.nextLine();
		password = str1;
		
		MySQLAccess msql = new MySQLAccess();
        boolean isExistUser = msql.checkExistUser(str, str1);
        if (isExistUser) {
            System.out.println("로그인에 성공하였습니다.");
            successLogIn();
        } else {
            System.out.println("로그인에 실패하였습니다.");
            System.out.println("시작 화면으로 돌아갑니다");
            goHome();
        }
	}
	
	public static void createUser() {
    	App app = new App();
    	Scanner scan = new Scanner(System.in);
    	CustomerDAO dao = new CustomerDAO();
	      // 회원가입
	      System.out.println("--회원가입--");
	      System.out.print("사용하실 ID : ");
	      String email = scan.nextLine();
	      while (dao.checkEmail(email)) {
	         System.out.println("이미 가입된 아이디입니다.");
	         System.out.print("다른 아이디를 입력하세요>>");
	         email = scan.nextLine();
	      }
	      
	      System.out.print("Password : ");
	      String pw = scan.nextLine();
	      
	      System.out.print("Phone : ");
	      String phone = scan.nextLine();
	      
	      System.out.print("Name : ");
	      String name = scan.nextLine();
    	      
	      CustomerVO vo = new CustomerVO(email, pw, name, phone);
	      dao.insert(vo);
	      System.out.println("회원가입이 완료됐습니다! 다시 로그인 하세요");
	      app.logIn();
	      
    }
	
	
	public void bookMenu2() {
		String totalDate = null;
		Scanner scan = new Scanner(System.in);
		System.out.println("예약 날짜를 입력합니다, H를 누르면 홈으로 돌아갑니다");
		System.out.println("계속 하시려면 아무 버튼이나 눌러주세요");
		System.out.print(">>  ");
		String home = scan.next();
		if (home == "H" || home == "h") {
			goHome();
		}

		System.out.print("예약할 달을 알려주세요 : ");
		int month = scan.nextInt();
		while (month < 0 && month > 12) {
			System.out.print("달이 잘못 입력되었습니다, 다시 입력해주세요 : ");
			month = scan.nextInt();
		}
//		if (month <10) {
//			month = "0" + month;
//		}
		System.out.print("예약할 날짜를 알려주세요 : ");
		int date = scan.nextInt();
		while (date < 0 && date > 31) {
			System.out.print("잘못 입력되었습니다, 다시 입력해주세요 : ");
			date = scan.nextInt();
		}
		
		System.out.print("예약할 시간을 알려주세요 : ");
		int hour = scan.nextInt();
		while (hour < 0 && hour > 24) {
			System.out.print("잘못 입력되었습니다, 다시 입력해주세요 : ");
		}
		System.out.print("예약할 분을 알려주세요 : ");
		int min = scan.nextInt();
		if (min < 0 && min > 60) {
			System.out.println("잘못 입력되었습니다, 다시 입력해주세요 : ");
		}
		
		totalDate = "2022" + month + date + hour + min;
		System.out.println(totalDate);
		//checkDate(totalDate); //예약 날짜가 오늘 이전인지 체크하는 메소드
		
		int custId = cDao.getCustId(id, password);
		System.out.println("회원번호 : " + custId + "번" + "\n");
		System.out.println();
		while (true) {
			System.out.println(">>결제창으로 넘어가시려면 99번을 입력 해주세요<<");
			System.out.print("음식번호를  입력 해주세요 : ");
			int menuNum = scan.nextInt();
			if (menuNum == 99) {
				System.out.println("[결제창으로 넘어가겠습니다]");
				payment(); //결제 메소드
				break;
			}
			System.out.println("주문하신 음식번호가 " + menuNum + "번이 맞습니까?");
			System.out.println("[맞으면 1, 아니면 2]");
			System.out.print(" >> ");
			int abc = scan.nextInt();
			if (abc == 2) {
				System.out.println("[예약하기 초기화면으로 돌아갑니다]");
				System.out.println();
				bookMenu2();
			} else if (abc == 1) {
				MenuVO newM = mDao.selectOneMenu(menuNum);
				System.out.println(custId);
				System.out.println(newM.getFood_id());
				BookVO newB = new BookVO(totalDate, custId, newM.getFood_id());
				bDao.insert(newB);
			} else {
				System.out.println("[잘못 입력했습니다. 다시 입력하세요.]");
				bookMenu2();
			}
		}
	}
	
	public void checkDate(String totalDate) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
			Date today = new Date();
			Date bookD = df.parse(totalDate);
			if (today.after(bookD)) {
				System.out.println("예약 날짜가 오늘 날짜 전입니다, 다시 입력해주세요.");
				bookMenu2();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	//결제 메소드 : 주문 내역, 결제 처리, 처음 페이지로 돌아가기
	public void payment() {
		int totalPrice = msql.getTotprice(cDao.getCustId(id, password));
		System.out.println("결제 금액은 총" + totalPrice + " 입니다");
		System.out.print("결제 도와드리겠습니다, 금액을 제시해주세요 : ");
		int card = scan.nextInt();
		
		if (card > totalPrice) {
			int change = card - totalPrice;
			System.out.println("총 금액 : " + totalPrice);
			System.out.println("결제 금액 : " + totalPrice);
			System.out.println("남은 금액 : " + change);
			System.out.println("이용해주셔서 감사합니다");
			System.out.println("1.시작페이지로 돌아가기    2.나가기");
			msql.delBook(cDao.getCustId(id, password));
			int num = scan.nextInt();
			switch(num) {
				case 1: startPage(); break;
				case 2: System.out.println("bakry를 이용해 주셔서 감사합니다"); break;
				default: break;
			}
		}
	}
	
	public void changMenu() {
		Scanner scan = new Scanner(System.in);
		System.out.print("상품 이름을 바꿉니다, 바꾸실 상품 번호를 입력해주세요 :");
		int foodId = scan.nextInt();
		
		System.out.print("새로운 이름을 입력해주세요 :");
		String newName = scan.nextLine();
		
		int check = mDao.updateName(foodId, newName);
		if (check == 1) {
			System.out.print("상품 이름 변경 완료했습니다");
		}
		System.out.print(mDao.selectOneMenu(foodId));
	}
	
	public void successLogIn() {
		MenuDAO menuDAO = new MenuDAO();
		CustomerDAO custDAO = new CustomerDAO();
		Scanner scan = new Scanner(System.in);
		System.out.println("1. 메뉴 조회" + "\t" + "2. 예약하기" + "\t" + "3. 회원정보 관리" + "\t" 
					+ "4. 로그아웃" + "\t"+ "Customer Number : "  + cDao.getCustId(id, password)+ "번");
		System.out.print(">> 원하시는 번호를 고르세요 : ");
		int selMenu = scan.nextInt();
		System.out.println();
		
		switch (selMenu){
			case 1 : 
				menuDAO.selectAll();
				goHome();
				break;
			case 2 :
				bookMenu2();
				break;
			case 3 : 
				custInfo();
				break;
			case 4 : 
				System.out.println("[로그아웃 되었습니다! 좋은하루 보내세요.]" + "\n");
				startPage();
				break;
		}
		//scan.close();
	}
	
	
	public void custInfo() {
		Scanner scan = new Scanner(System.in);
		CustomerDAO dao = new CustomerDAO();
		System.out.println("========== Information ==========");
		System.out.println( dao.selectOne(id));
		System.out.println("=============================" + "\n");
		System.out.println("1. 비밀번호	2. 전화번호	9. 회원탈퇴    0. 홈으로");
		System.out.print(">> 원하시는 번호를 고르세요 : ");
		int sel = scan.nextInt();
		switch (sel) {
		case 1 :  
			System.out.print("변경 할 비밀번호 : ");
			String aftPW = scan.next();
			dao.updatePW(id, aftPW);
			System.out.println("비밀번호 변경 완료!! 다시 로그인 하세요");
			startPage();
			break;
		case 2 : 
			System.out.print("변경 할 전화번호 : ");
			String aftPN = scan.next();
			dao.updatePhone(id, aftPN);
			System.out.println("전화번호 변경 완료!! 다시 로그인 하세요");
			startPage();
			break;
		case 9 :
			System.out.println("삭제하려는 ID, Password 입력해주세요");
			System.out.print("ID : ");
			String delID = scan.next();
			System.out.print("PW : ");
			String delPW = scan.next();
			msql.delBook(cDao.getCustId(delID, delPW));
			int result = dao.delete(delID, delPW);
			if (result == 0) {
				while (true) {
					System.out.println("ID, PW 오류!! 다시 입력하세요");
					System.out.print("ID : ");
					delID = scan.next();
					System.out.print("PW : ");
					delPW = scan.next();
					result = dao.delete(delID, delPW);
					if (result == 1) {
						System.out.println("계정이 삭제되었습니다.");
						break;
					}
				}
			} else {
				msql.delBook(cDao.getCustId(delID, delPW));
				System.out.println("계정이 삭제되었습니다.");
				startPage();
			}
		case 0 : 
			System.out.println();
			successLogIn();
		}
		
	}//custInfo
	
	public void goHome() {
		Scanner scan = new Scanner(System.in);
		System.out.println("								0 : 홈으로");
		System.out.print("									>>");
		int home = scan.nextInt();
		System.out.println();
		while (home == 0) {
			successLogIn();
			if (home != 0) {
				System.out.println("0을 입력해주세요");
			}
		}
	}
	

}
