package kr.or.mrhi.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//�����ͺ��̽��� ���õ� ������ ���⼭ �ְ��Ѵ�.(����)
public class DBController {

	// ��ȭ��ȣ�� �Է��ϱ�
	public static int phoneBookInsertTBL(PhoneBook phoneBook) {
		// ����Ÿ���̽��� ����
		Connection con = DBUtility.getConnection();
		String insertQuery = "insert into phonebookDB.phonebookTBL values(?,?,?,?,?,?)";
		PreparedStatement preparedStatement = null;
		int count = 0;

		try {
			preparedStatement = con.prepareStatement(insertQuery);

			preparedStatement.setString(1, phoneBook.getPhoneNumber());
			preparedStatement.setString(2, phoneBook.getName());
			preparedStatement.setString(3, phoneBook.getGender());
			preparedStatement.setString(4, phoneBook.getJob());
			preparedStatement.setString(5, phoneBook.getBirthday());
			preparedStatement.setInt(6, phoneBook.getAge());

			count = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}

			} catch (SQLException e) {
			}
		}
		return count;
	}

	// ��ȭ��ȣ�� �˻��ϱ�
	public static List<PhoneBook> phoneBookSearchTBL(String searchData, int searchNumber) {

		List<PhoneBook> list = new ArrayList<PhoneBook>();

		// ����Ÿ���̽��� ����
		Connection con = DBUtility.getConnection();
		String searchQuery = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		// �˻��� ���� �Է¹ޱ�(�˻��� ������ �����п� �����ؼ� �ټ� �ִ�.

		try {
			switch (searchNumber) {
			case 1:
				searchQuery = "select * from phonebooktbl where phoneNumber like ?";
				break;
			case 2:
				searchQuery = "select * from phonebooktbl where name like ?";
				break;
			case 3:
				searchQuery = "select * from phonebooktbl where gender like ?";
				break;
			default:
				System.out.println("�˻���ȣ ������ ������ϴ�. (�ٽ��Է¿��)");
				return list;
			}

			preparedStatement = con.prepareStatement(searchQuery);
			searchData = "%" + searchData + "%";
			preparedStatement.setString(1, searchData);

			resultSet = preparedStatement.executeQuery();

			if (!resultSet.isBeforeFirst()) {
				return list;
			}

			while (resultSet.next()) {
				String phoneNumber = resultSet.getString(1);
				String name = resultSet.getString(2);
				String gender = resultSet.getString(3);
				String job = resultSet.getString(4);
				String birthday = resultSet.getString(5);
				int age = resultSet.getInt(6);

				PhoneBook phoneBook = new PhoneBook(phoneNumber, name, gender, job, birthday, age);
				list.add(phoneBook);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}

			} catch (SQLException e) {
			}
		}
		return list;
	}

	// ��ȭ��ȣ�� �����ϱ�
	public static int phoneBookDeleteTBL(String name) {
		// ����Ÿ���̽��� ����
		Connection con = DBUtility.getConnection();
		String deleteQuery = "DELETE from phonebooktbl where name like ?";
		PreparedStatement preparedStatement = null;
		int count = 0;

		try {
			preparedStatement = con.prepareStatement(deleteQuery);
			String strName = "%" + name + "%";
			preparedStatement.setString(1, strName);
			count = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}

			} catch (SQLException e) {
			}
		}
		return count;
	}

	// ��ȭ��ȣ�� �����ϱ�
	public static int phoneBookUpdateTBL(String phoneNumber, String name) {
		Connection con = DBUtility.getConnection();
		String updateQuery = "update phonebooktbl set name = ? where phoneNumber = ?";
		PreparedStatement preparedStatement = null;
		int count = 0;

		try {
			preparedStatement = con.prepareStatement(updateQuery);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, phoneNumber);
			count = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}

			} catch (SQLException e) {
			}
		}
		return count;
	}

	// ��ȭ��ȣ�� ����ϱ�
	public static List<PhoneBook> phoneBookSelectTBL() {
		List<PhoneBook> list = new ArrayList<PhoneBook>();
		// ����Ÿ���̽��� ����
		Connection con = DBUtility.getConnection();
		String selectQuery = "select * from phonebooktbl";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = con.prepareStatement(selectQuery);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String phoneNumber = resultSet.getString(1);
				String name = resultSet.getString(2);
				String gender = resultSet.getString(3);
				String job = resultSet.getString(4);
				String birthday = resultSet.getString(5);
				int age = resultSet.getInt(6);

				PhoneBook phoneBook = new PhoneBook(phoneNumber, name, gender, job, birthday, age);
				list.add(phoneBook);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
				if (con != null && !con.isClosed()) {
					con.close();
				}

			} catch (SQLException e) {
			}
		}
		return list;
	}

}
