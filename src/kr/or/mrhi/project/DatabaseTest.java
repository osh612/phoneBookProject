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

		// �޴�����
		while (!flag) {
			// �޴���� �� ��ȣ����
			selectNumber = displayMenu();

			switch (selectNumber) {
			case INSERT:
				phoneBookInsert();
				break; // ��ȭ��ȣ�� �Է��ϱ�
			case SEARCH:
				phoneBookSearch();
				break; // ��ȭ��ȣ�� �˻��ϱ�
			case DELETE:
				phoneBookDelete();
				break; // ��ȭ��ȣ�� �����ϱ�
			case UPDATE:
				phoneBookUpdate();
				break; // ��ȭ��ȣ�� �����ϱ�
			case SHOWTB:
				phoneBookSelect();
				break; // ��ȭ��ȣ�� ����ϱ�
			case FINISH:
				flag = true;
				break;
			default:
				System.out.println("���ڹ����ʰ� �ٽ��Է¿��");
				break;
			}// end of switch
		} // end of while

		System.out.println("���α׷� ����!");
	}// end of main

	// 1 ��ȭ��ȣ�� �Է��ϱ�
	private static void phoneBookInsert() {
		String phoneNumber = null;
		String name = null;
		String gender = null;
		String job = null;
		String birthday = null;
		int age = 0;

		while (true) {
			System.out.print("   ��ȭ��ȣ(000-0000-0000) �Է� >>");
			phoneNumber = scan.nextLine();
			if (phoneNumber.length() != 13) {
				System.out.println("��ȭ��ȣ 13�ڸ��� �Է����ּ���");
				continue;
			}
			boolean checkDuplicatePhoneNumber = duplicatePhoneNumberCheck(phoneNumber);
			if (checkDuplicatePhoneNumber == true) {
				System.out.println("�ߺ��� ��ȣ�Դϴ�. �ٽ� �Է��ϼ���.");
				continue;
			}
			break;
		} // end of while

		while (true) {
			System.out.print("�̸�(ȫ�浿) �Է� >>");
			name = scan.nextLine();
			if (name.length() < 2 || name.length() > 7) {
				System.out.println("�̸� �ٽ� �Է����ּ���");
				continue;
			} else {
				break;
			}
		} // end of while

		while (true) {
			System.out.print("����(����,����) �Է� >>");
			gender = scan.nextLine();
			if (!(gender.equals("����") || gender.equals("����"))) {
				System.out.println("���� �ٽ� �Է����ּ���");
				continue;
			} else {
				break;
			}
		} // end of while

		while (true) {
			System.out.print("����(20���ڹ̸�) �Է� >>");
			job = scan.nextLine();
			if (job.length() < 1 || job.length() > 20) {
				System.out.println("���� �ٽ� �Է����ּ���");
				continue;
			} else {
				break;
			}
		} // end of while

		while (true) {
			System.out.print("�������(19950830) �Է� >>");
			birthday = scan.nextLine();
			if (birthday.length() != 8) {
				System.out.println("������� �ٽ� �Է����ּ���");
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
			System.out.println(phoneBook.getName() + "�� ���Լ���");
		} else {
			System.out.println(phoneBook.getName() + "�� ���Խ���");
		}

	}

	// 2 ��ȭ��ȣ�� �˻��ϱ�
	private static void phoneBookSearch() {
		final int PHONE = 1, NAME = 2, GENDER = 3, EXIT = 4;
		List<PhoneBook> list = new ArrayList<PhoneBook>();
		boolean flag = false;
		String searchData = null;
		int searchNumber = 0;

		// �˻��� ���� �Է¹ޱ�(�˻��� ������ �����п� �����ؼ� �ټ� �ִ�.
		while (!flag) {
			int selectNumber = displaySearchMenu();

			switch (selectNumber) {
			case PHONE:
				System.out.print("1.��ȭ��ȣ���Է� >>");
				searchData = scan.nextLine();
				searchNumber = PHONE;
				break;
			case NAME:
				System.out.print("2.�̸��Է� >>");
				searchData = scan.nextLine();
				searchNumber = NAME;
				break;
			case GENDER:
				System.out.print("3.�����Է� >>");
				searchData = scan.nextLine();
				searchNumber = GENDER;
				break;
			case EXIT:
				return;
			default:
				System.out.println("�˻���ȣ ������ ������ϴ�. (�ٽ��Է¿��)");
				continue;
			}
			flag = true;
		}
		list = DBController.phoneBookSearchTBL(searchData, searchNumber);

		if (list.size() <= 0) {
			System.out.println("�����Ͱ� ���ų� �߸��� �˻� �����Դϴ�.");
			return;
		}

		for (PhoneBook pb : list) {
			System.out.println(pb);
		}

	}

	// 3 ��ȭ��ȣ�� �����ϱ�
	private static void phoneBookDelete() {
		System.out.print("������ �̸� �Է�>>");
		String name = scan.nextLine();

		int count = DBController.phoneBookDeleteTBL(name);

		if (count != 0) {
			System.out.println(name + "�� �����Ϸ��߽��ϴ�.");
		} else {
			System.out.println(name + "�� ���������߽��ϴ�.");
		}
	}

	// 4 ��ȭ��ȣ�� �����ϱ�
	private static void phoneBookUpdate() {

		System.out.println("������ ��ȭ��ȣ �Է�>>");
		String phoneNumber = scan.nextLine();

		System.out.print("������ �̸� �ӷ�>>");
		String name = scan.nextLine();

		int count = DBController.phoneBookUpdateTBL(phoneNumber, name);

		// ����Ÿ���̽��� ����
		if (count != 0) {
			System.out.println(phoneNumber + "�� �̸��� �����Ǿ����ϴ�");
		} else {
			System.out.println(phoneNumber + "�� �̸��� ������ �� �����ϴ�.");
		}
	}

	// ��ȭ��ȣ�� ����ϱ�
	private static void phoneBookSelect() {
		List<PhoneBook> list = new ArrayList<PhoneBook>();

		list = DBController.phoneBookSelectTBL();

		if (list.size() <= 0) {
			System.out.println("����� �����Ͱ� �����ϴ�.");
			return;
		}
		for (PhoneBook pb : list) {
			System.out.println(pb.toString());
		}
	}

	// �޴� ��� �� ��ȣ�����ϱ�
	private static int displayMenu() {

		int selectNumber = 0;
		boolean flag = false;
		while (!flag) {
			System.out.println("��������������������������������������������������������������������������������������������������������������������������������������������������");
			System.out.println("��  1. �Է�   ��  2. ��ȸ   ��  3. ����   ��  4. ����   ��  5. ���   ��  6. ����   ��");
			System.out.println("��������������������������������������������������������������������������������������������������������������������������������������������������");
			System.out.print("   ��ȣ����>>");
			// ��ȣ����
			try {
				selectNumber = Integer.parseInt(scan.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("���� �Է� ���");
				continue;
			} catch (Exception e) {
				System.out.println("���� �Է� �����߻� ���Է¿��");
				continue;
			}
			break;
		}
		return selectNumber;
	}

	// �˻� �޴� ��� �� ��ȣ�����ϱ�
	private static int displaySearchMenu() {
		int selectNumber = 0;
		boolean flag = false;
		while (!flag) {
			System.out.println("������������������������������������������������������������������������������������������������������������������������������������������������");
			System.out.println("��  �˻�����  ��  1. ��ȭ��ȣ �˻�  ��  2.�̸� �˻�  ��  3.���� �˻�  ��  4. ���ư���  ��");
			System.out.println("������������������������������������������������������������������������������������������������������������������������������������������������");
			System.out.print("��ȣ����>>");
			// ��ȣ����
			try {
				selectNumber = Integer.parseInt(scan.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("���� �Է� ���");
				continue;
			} catch (Exception e) {
				System.out.println("���� �Է� �����߻� ���Է¿��");
				continue;
			}
			break;
		}
		return selectNumber;

	}

	// 2 ��ȭ��ȣ�� �˻��ϱ�
	private static boolean duplicatePhoneNumberCheck(String phoneNumber) {
		final int PHONE = 1;
		List<PhoneBook> list = new ArrayList<PhoneBook>();
		int searchNumber = PHONE;

		// �˻��� ���� �Է¹ޱ�(�˻��� ������ �����п� �����ؼ� �ټ� �ִ�.

		list = DBController.phoneBookSearchTBL(phoneNumber, searchNumber);

		if (list.size() >= 1) {

			return true;
		}
		return false;
	}

}
