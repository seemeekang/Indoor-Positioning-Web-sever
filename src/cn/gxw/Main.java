package cn.gxw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.BufferOverflowException;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

@WebServlet("/Main")
public class Main extends HttpServlet {        //数据采集阶段
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
	Float Xcoord;
	Float Ycoord;
	Float AP1;
	Float AP2;
	Float AP3;
	Float AP4;
	Float AP5;
	String url = "jdbc:mysql://localhost:3306/rssi";
	String username="root";
    String password="password";
    Connection con;
    Statement stat;
    
    public Main() throws ClassNotFoundException, SQLException {
        // TODO Auto-generated constructor stub
    	 Class.forName("com.mysql.jdbc.Driver");
		 con = (Connection) DriverManager.getConnection(url, username, password);
		 stat=(Statement) con.createStatement();//statment对象，将sql语句发送给数据库
		 
		 System.out.println("hello world");
    	 FindPath.init();
         FindPath.floydMethod();
         FindPath.printResult(1,8);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));//使用字符流读取客户端发过来的数据
		String line = null;
		StringBuffer buffer = new StringBuffer();
		while ((line = br.readLine()) != null) {
			buffer.append(line);
		}
		br.close();
		
		
		//数据库操作
		try {
			JSONObject jsonObject=new JSONObject(buffer.toString());
			System.out.println(jsonObject);
			Xcoord=Float.valueOf(jsonObject.getString("Xcoord"));
			Ycoord=Float.valueOf(jsonObject.getString("Ycoord"));
			AP1=Float.valueOf(jsonObject.getString("AP1"));
			AP2=Float.valueOf(jsonObject.getString("AP2"));
			AP3=Float.valueOf(jsonObject.getString("AP3"));
			AP4=Float.valueOf(jsonObject.getString("AP4"));
			AP5=Float.valueOf(jsonObject.getString("AP5"));
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		System.out.println(buffer.toString());
		System.out.println(Xcoord);
		System.out.println(Ycoord);
		System.out.println(AP1);
		System.out.println(AP2);
		System.out.println(AP3);
		System.out.println(AP4);
		System.out.println(AP5);


		ServletOutputStream out = response.getOutputStream();
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement("INSERT INTO information(X,Y,AP1,AP2,AP3,AP4,AP5) "
					+ "VALUES(?,?,?,?,?,?,?)");
			ps.setFloat(1, Xcoord);
			ps.setFloat(2, Ycoord);
			ps.setFloat(3, AP1);
			ps.setFloat(4, AP2);
			ps.setFloat(5, AP3);
			ps.setFloat(6, AP4);
			ps.setFloat(7, AP5);
			ps.executeUpdate();
			String send ="采集成功";
			System.out.println(send);
	        out.write(send.getBytes("UTF-8"));
	        out.flush();
		    out.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			String send ="重复采集，采集失败";
			System.out.println(send);
	        
	        out.write(send.getBytes("UTF-8"));
	        out.flush();
		    out.close();
		}			
	}

}
