package kr.or.mrhi.project;

import java.io.FileReader;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtility {
	public static Connection getConnection() {
		Connection con = null;

		try {
			// �ּ�1
			Properties properties = new Properties();
			String filePath = DatabaseTest.class.getResource("db.properties").getPath();
			filePath = URLDecoder.decode(filePath, "utf-8");
			properties.load(new FileReader(filePath));

			// ���ε��ϱ�
			String driver = properties.getProperty("DRIVER");
			String url = properties.getProperty("URL");
			String userID = properties.getProperty("USER_ID");
			String userPassword = properties.getProperty("USER_PASSWORD");

			// �帮�̹� �ε��ϱ�
			Class.forName(driver);

			// ����Ÿ���̽� �����ϱ�
			con = DriverManager.getConnection(url, userID, userPassword);

		} catch (Exception e) {
			System.out.println("Mysql Databse connection fail");
			e.printStackTrace();
		}

		return con;
	}

}
