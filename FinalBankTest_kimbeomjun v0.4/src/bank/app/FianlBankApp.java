package bank.app;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import bank.account.AccountDAO;
import bank.account.AccountHistoryVO;
import bank.account.AccountService;
import bank.account.AccountVO;
import bank.account.OracleAccountDAO;
import bank.account.SYAccountService;
import bank.member.MemberVO;
import bank.exception.NoAccountException;

public class FianlBankApp {
		
		static Scanner sc = new Scanner(System.in);
		static MemberVO loggedMember = new MemberVO(120501, "final01", "1111", "기말실기", new Date(0));
		
		public static void main(String[] args) {
			System.out.println("+----------------------------------------------------+");
			System.out.println(" " + loggedMember.getName() + "님, Final Bank에 오신 것을 환영합니다.");
			
			int menu = 0;
			
			do {
				System.out.println();
				System.out.println("======================================================");
				System.out.println(" 1.계좌목록 2.입금 3.출금 4.입출금내역 5.계좌개설 6.계좌해지 0.종료");
				System.out.println("======================================================");
				System.out.print(">> 선택 : ");
				menu = Integer.parseInt(sc.nextLine());
				switch (menu) {
					case 1 : accountList();	break;
					case 2 : deposit();	break;
					case 3 : withdraw(); break;
					case 4 : viewAccountHistory(); break;
					case 5 : createAccount(); break;
					case 6 : removeAccount(); break;
					case 0 : exit(); break;
					default : System.out.println("[선택 오류] 메뉴에 있는 번호를 선택하세요.");
				}
			} while (menu != 0);
			
		}
		
		private static void accountList() {
		    System.out.println();
		    System.out.println("[계좌목록]");
		    
		    // 코드 추가
		    AccountService accountService = new SYAccountService(new OracleAccountDAO());
		    List<AccountVO> accountList = accountService.listAllAccount(loggedMember.getId());
		    
		    if (accountList == null || accountList.isEmpty()) {
		        System.out.println("계좌가 존재하지 않습니다.");
		    } else {
		        for (AccountVO account : accountList) {
		            System.out.println(account);
		        }
		    }
		}


		private static void deposit() {
			System.out.println();
			System.out.println("[입금]");
			System.out.print(">> 계좌번호 : ");
			int accountNo = Integer.parseInt(sc.nextLine());
			System.out.print(">> 입금액 : ");
			int money = Integer.parseInt(sc.nextLine());

			// 코드 추가
		    AccountDAO accountDao = new OracleAccountDAO(); 
		    AccountService accountService = new SYAccountService(accountDao);
		    try {
		        boolean result = accountService.deposit(accountNo, money);
		        if (result) {
		            System.out.println("입금이 완료되었습니다.");
		        } else {
		            System.out.println("입금 실패하였습니다.");
		        }
		    } catch (NoAccountException e) {
		        System.out.println(e.getMessage());
		    }
		}
		

		private static void withdraw() {
			System.out.println();
			System.out.println("[출금]");
			System.out.print(">> 계좌번호 : ");
			int accountNo = Integer.parseInt(sc.nextLine());
			System.out.print(">> 출금액 : ");
			int money = Integer.parseInt(sc.nextLine());

			// 코드 추가
			
		    AccountService accountService = new SYAccountService(new OracleAccountDAO());

		    try {
		        boolean result = accountService.withdraw(accountNo, money);
		        if (result) {
		            System.out.println("출금이 완료되었습니다.");
		        } else {
		            System.out.println("출금 실패하였습니다.");
		        }
		    } catch (NoAccountException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		private static void viewAccountHistory() {
			System.out.println();
			System.out.println("[입출금 내역]");
			System.out.print(">> 계좌번호 : ");
			int accountNo = Integer.parseInt(sc.nextLine());

			// 코드 추가
			
		    AccountService accountService = new SYAccountService(new OracleAccountDAO());
		    
		    try {
		        AccountVO account = accountService.searchAccountByNo(accountNo);
		        List<AccountHistoryVO> historyList = accountService.listAllHistoryByNos(List.of(account));

		        if (historyList == null || historyList.isEmpty()) {
		            System.out.println("입출금 내역이 존재하지 않습니다.");
		        } else {
		            for (AccountHistoryVO history : historyList) {
		                System.out.println(history);
		            }
		        }
		    } catch (NoAccountException e) {
		        System.out.println(e.getMessage());
		    }
		}

		private static void createAccount() {
			System.out.println();
			System.out.println("[계좌개설]");
			System.out.print(">> 입금액 : ");
			int money = Integer.parseInt(sc.nextLine());

			// 코드 추가
			
		    AccountService accountService = new SYAccountService(new OracleAccountDAO());
		    
		    boolean result = accountService.createAccount(loggedMember.getId(), money);
		    if (result) {
		        System.out.println("계좌가 개설되었습니다.");
		    } else {
		        System.out.println("계좌 개설 실패하였습니다.");
		    }
			
		}

		private static void removeAccount() {
			System.out.println();
			System.out.println("[계좌해지]");
			System.out.print(">> 계좌번호 : ");
			int accountNo = Integer.parseInt(sc.nextLine());
			
			// 코드 추가
			
		    AccountService accountService = new SYAccountService(new OracleAccountDAO());
		    
		    boolean result = accountService.createAccount(loggedMember.getId(), accountNo);
		    if (result) {
		        System.out.println("계좌가 개설되었습니다.");
		    } else {
		        System.out.println("계좌 개설 실패하였습니다.");
		    }
		}
		
		private static void exit() {
			System.out.println("Final Bank을 이용해주셔서 감사합니다.");	
		}

	}
