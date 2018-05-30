package cn.gxw;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class JDBC {
	public static void main(String[] arys) throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/rssi";
		String username="root";
		String password="password";
		Connection con = (Connection) DriverManager.getConnection(url, username, password);
		
		Statement stat=(Statement) con.createStatement();//statment对象，将sql语句发送给数据库
		/*int row =stat.executeUpdate("INSERT INTO information(X,Y,Z,AP1,AP2,AP3,AP4,AP5,AP6,remark) "
				+ "VALUES('1',1,'0','-10','-20','-10','-20','-20','-20','坐标1')");
		System.out.println(row);*/
		
		//stat.executeUpdate           增删改  insert update delete
		//stat.executeQuery(sql)       查询   select  返回resultset
		
		String sql="select * from information";
  		ResultSet rs=stat.executeQuery(sql);    //返回结果集
  		
  		while(rs.next())         //rs.next()有则为真
  		{
  			System.out.println(rs.getString("remark"));
  		}
		stat.close();
		con.close();
	}
}
