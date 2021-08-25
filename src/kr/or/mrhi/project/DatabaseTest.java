package kr.or.mrhi.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class DatabaseTest {
	public static final Scanner scan = new Scanner(System.in);
	public static final int INSERT = 1, SEARCH = 2, DELETE = 3, UPDATE = 4, SHOWTB = 5, FINISH = 6;

	public static void main(String[] args) throws IOException {
		boolean flag = false;
		int selectNumber = 0;

		// 메뉴선택
		while (!flag) {
			// 메뉴출력 및 번호선택
			selectNumber = displayMenu();

			switch (selectNumber) {
			case INSERT:
				phoneBookInsert();
				break; // 전화번호부 입력하기
			case SEARCH:
				phoneBookSearch();
				break; // 전화번호부 검색하기
			case DELETE:
				phoneBookDelete();
				break; // 전화번호부 삭제하기
			case UPDATE:
				phoneBookUpdate();
				break; // 전화번호부 수정하기
			case SHOWTB:
				phoneBookSelect();
				break; // 전화번호부 출력하기
			case FINISH:
				flag = true;
				break;
			default:
				System.out.println("숫자범위초과 다시입력요망");
				break;
			}// end of switch
		} // end of while

		System.out.println("프로그램 종료!");
	}// end of main

	// 1 전화번호부 입력하기
	private static void phoneBookInsert() {
		String phoneNumber = null;
		String name = null;
		String gender = null;
		String job = null;
		String birthday = null;
		int age = 0;

		while (true) {
			System.out.print("   전화번호(000-0000-0000) 입력 >>");
			phoneNumber = scan.nextLine();
			if (phoneNumber.length() != 13) {
				System.out.println("전화번호 13자리를 입력해주세요");
				continue;
			}
			boolean checkDuplicatePhoneNumber = duplicatePhoneNumberCheck(phoneNumber);
			if (checkDuplicatePhoneNumber == true) {
				System.out.println("중복된 번호입니다. 다시 입력하세요.");
				continue;
			}
			break;
		} // end of while

		while (true) {
			System.out.print("이름(홍길동) 입력 >>");
			name = scan.nextLine();
			if (name.length() < 2 || name.length() > 7) {
				System.out.println("이름 다시 입력해주세요");
				continue;
			} else {
				break;
			}
		} // end of while

		while (true) {
			System.out.print("성별(남자,여자) 입력 >>");
			gender = scan.nextLine();
			if (!(gender.equals("남자") || gender.equals("여자"))) {
				System.out.println("성별 다시 입력해주세요");
				continue;
			} else {
				break;
			}
		} // end of while

		while (true) {
			System.out.print("직업(20글자미만) 입력 >>");
			job = scan.nextLine();
			if (job.length() < 1 || job.length() > 20) {
				System.out.println("직업 다시 입력해주세요");
				continue;
			} else {
				break;
			}
		} // end of while

		while (true) {
			System.out.print("생년월일(19950830) 입력 >>");
			birthday = scan.nextLine();
			if (birthday.length() != 8) {
				System.out.println("생년월일 다시 입력해주세요");
				continue;
			} else {
				int year = Integer.parseInt(birthday.substring(0, 4));
				int currentYear = Calendar.getInstance().get(Calendar.YEAR);
				age = currentYear - year + 1;
				break;
			}
		} // end of while

		PhoneBook phoneBook = new PhoneBook(phoneNumber, name, gender, job, birthday, age);

		int count = DBController.phoneBookInsertTBL(phoneBook);

		if (count != 0) {
			System.out.println(phoneBook.getName() + "님 삽입성공");
		} else {
			System.out.println(phoneBook.getName() + "님 삽입실패");
		}

	}

	// 2 전화번호부 검색하기
	private static void phoneBookSearch() {
		final int PHONE = 1, NAME = 2, GENDER = 3, EXIT = 4;
		List<PhoneBook> list = new ArrayList<PhoneBook>();
		boolean flag = false;
		String searchData = null;
		int searchNumber = 0;

		// 검색할 내용 입력받기(검색할 조건을 여러분에 선택해서 줄수 있다.
		while (!flag) {
			int selectNumber = displaySearchMenu();

			switch (selectNumber) {
			case PHONE:
				System.out.print("1.전화번호부입력 >>");
				searchData = scan.nextLine();
				searchNumber = PHONE;
				break;
			case NAME:
				System.out.print("2.이름입력 >>");
				searchData = scan.nextLine();
				searchNumber = NAME;
				break;
			case GENDER:
				System.out.print("3.성별입력 >>");
				searchData = scan.nextLine();
				searchNumber = GENDER;
				break;
			case EXIT:
				return;
			default:
				System.out.println("검색번호 범위에 벗어났습니다. (다시입력요망)");
				continue;
			}
			flag = true;
		}
		list = DBController.phoneBookSearchTBL(searchData, searchNumber);

		if (list.size() <= 0) {
			System.out.println("데이터가 없거나 잘못된 검색 범위입니다.");
			return;
		}

		for (PhoneBook pb : list) {
			System.out.println(pb);
		}

	}

	// 3 전화번호부 삭제하기
	private static void phoneBookDelete() {
		System.out.print("삭제할 이름 입력>>");
		String name = scan.nextLine();

		int count = DBController.phoneBookDeleteTBL(name);

		if (count != 0) {
			System.out.println(name + "님 삭제완료했습니다.");
		} else {
			System.out.println(name + "님 삭제실패했습니다.");
		}
	}

	// 4 전화번호부 수정하기
	private static void phoneBookUpdate() {

		System.out.println("수정할 전화번호 입력>>");
		String phoneNumber = scan.nextLine();

		System.out.print("수정할 이름 임력>>");
		String name = scan.nextLine();

		int count = DBController.phoneBookUpdateTBL(phoneNumber, name);

		// 데이타베이스에 연결
		if (count != 0) {
			System.out.println(phoneNumber + "님 이름이 수정되었습니다");
		} else {
			System.out.println(phoneNumber + "님 이름을 수정할 수 없습니다.");
		}
	}

	// 전화번호부 출력하기
	private static void phoneBookSelect() {
		List<PhoneBook> list = new ArrayList<PhoneBook>();

		list = DBController.phoneBookSelectTBL();

		if (list.size() <= 0) {
			System.out.println("출력할 데이터가 없습니다.");
			return;
		}
		for (PhoneBook pb : list) {
			System.out.println(pb.toString());
		}
	}

	// 메뉴 출력 및 번호선택하기
	private static int displayMenu() {

		int selectNumber = 0;
		boolean flag = false;
		while (!flag) {
			System.out.println("┏━━━━━━━━━━━┳━━━━━━━━━━━┳━━━━━━━━━━━┳━━━━━━━━━━━┳━━━━━━━━━━━┳━━━━━━━━━━━┓");
			System.out.println("┃  1. 입력   ┃  2. 조회   ┃  3. 삭제   ┃  4. 수정   ┃  5. 출력   ┃  6. 종료   ┃");
			System.out.println("┗━━━━━━━━━━━┻━━━━━━━━━━━┻━━━━━━━━━━━┻━━━━━━━━━━━┻━━━━━━━━━━━┻━━━━━━━━━━━┛");
			System.out.print("   번호선택>>");
			// 번호선택
			try {
				selectNumber = Integer.parseInt(scan.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("숫자 입력 요망");
				continue;
			} catch (Exception e) {
				System.out.println("숫자 입력 문제발생 재입력요망");
				continue;
			}
			break;
		}
		return selectNumber;
	}

	// 검색 메뉴 출력 및 번호선택하기
	private static int displaySearchMenu() {
		int selectNumber = 0;
		boolean flag = false;
		while (!flag) {
			System.out.println("┏━━━━━━━━━━┳━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━┳━━━━━━━━━━━━━┳━━━━━━━━━━━━━┓");
			System.out.println("┃  검색선택  ┃  1. 전화번호 검색  ┃  2.이름 검색  ┃  3.성별 검색  ┃  4. 돌아가기  ┃");
			System.out.println("┗━━━━━━━━━━┻━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━┻━━━━━━━━━━━━━┻━━━━━━━━━━━━━┛");
			System.out.print("번호선택>>");
			// 번호선택
			try {
				selectNumber = Integer.parseInt(scan.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("숫자 입력 요망");
				continue;
			} catch (Exception e) {
				System.out.println("숫자 입력 문제발생 재입력요망");
				continue;
			}
			break;
		}
		return selectNumber;

	}

	// 2 전화번호부 검색하기
	private static boolean duplicatePhoneNumberCheck(String phoneNumber) {
		final int PHONE = 1;
		List<PhoneBook> list = new ArrayList<PhoneBook>();
		int searchNumber = PHONE;

		// 검색할 내용 입력받기(검색할 조건을 여러분에 선택해서 줄수 있다.

		list = DBController.phoneBookSearchTBL(phoneNumber, searchNumber);

		if (list.size() >= 1) {

			return true;
		}
		return false;
	}

}
