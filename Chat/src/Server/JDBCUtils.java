package Server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;



public class JDBCUtils {
	private static String url;
	private static String user;
	private static String password;
	private static String driver;
	
	
	static {
		try {
			//创建properties集合类
			Properties pro = new Properties();
			
			//获取src路径下文件的方式---->ClassLoader类加载器
			ClassLoader classloader = JDBCUtils.class.getClassLoader();
			/*URL res = classloader.getResource("/jdbc.properties");
			String path = res.getPath();
			pro.load(new FileReader(path));*/
			InputStream is = classloader.getResourceAsStream("jdbc.properties");
			//加载文件
			pro.load(is);
			//pro.load(new FileReader("D:\\Program Files\\java test\\Chat\\src\\jdbc.properties"));
			
			url = pro.getProperty("url");
			user = pro.getProperty("user");
			password = pro.getProperty("password");
			driver = pro.getProperty("driver");
			
			Class.forName(driver);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*
	 * 获取连接
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}
	/*
	 * 释放资源
	 */
	public static void close(Connection conn,Statement stmt) {
		close(conn,stmt,null);
	}
	/*
	 * 重载
	 */
	public static void close(Connection conn,Statement stmt,ResultSet rs) {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
  
}
