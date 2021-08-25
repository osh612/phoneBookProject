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
			// 주석1
			Properties properties = new Properties();
			String filePath = DatabaseTest.class.getResource("db.properties").getPath();
			filePath = URLDecoder.decode(filePath, "utf-8");
			properties.load(new FileReader(filePath));

			// 바인딩하기
			String driver = properties.getProperty("DRIVER");
			String url = properties.getProperty("URL");
			String userID = properties.getProperty("USER_ID");
			String userPassword = properties.getProperty("USER_PASSWORD");

			// 드리이버 로드하기
			Class.forName(driver);

			// 데이타베이스 연결하기
			con = DriverManager.getConnection(url, userID, userPassword);

		} catch (Exception e) {
			System.out.println("Mysql Databse connection fail");
			e.printStackTrace();
		}

		return con;
	}

}
