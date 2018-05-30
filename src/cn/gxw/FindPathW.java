package cn.gxw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class FindPathW
 */
@WebServlet("/FindPathW")
public class FindPathW extends HttpServlet {              //此类用来实现路径数据的传送
	private static final long serialVersionUID = 1L;
    
	int Spoint;     //起点
	int Epoint;     //终点
	static ArrayList<Integer> list = new ArrayList<Integer>();  //使用链表存储路径
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindPathW() {
        super();
        // TODO Auto-generated constructor stub
        
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
		
		//得到要到达的坐标点
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));//使用字符流读取客户端发过来的数据
		String line = null;
		StringBuffer buffer = new StringBuffer();
		while ((line = br.readLine()) != null) {
			buffer.append(line);
		}
		br.close();
		
		try {
			JSONObject jsonObject=new JSONObject(buffer.toString());
			System.out.println(jsonObject);
			Spoint=Integer.parseInt(jsonObject.getString("Spoint"));
			Epoint=Integer.parseInt(jsonObject.getString("Epoint"));
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		list=FindPath.getList(Spoint,Epoint);        //得到路径
		
		System.out.println(list);
		System.out.println(Spoint);
		System.out.println(Epoint);
		
		
		int[] d = new int[list.size()];
		for(int i = 0;i<list.size();i++){
		    d[i] = list.get(i);
		}
		
		JSONObject jsonObject=new JSONObject();
        try {
        	for(int i=0;i<d.length;i++)
        	{
    			jsonObject.put("第"+i+"步",d[i]);
        	}
        	jsonObject.put("总步数",d.length);
        	list.clear();      //清楚list中的数据
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
        
        //发送给Android端的数据
		ServletOutputStream out = response.getOutputStream();        //封装坐标传送到Android客户端
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out,"utf-8"));
//		new String(s.getBytes("gbk"),"utf-8")   jsonObject.toString()
		bw.write(jsonObject.toString());
		System.out.println(jsonObject.toString());   
		bw.flush();//刷新缓冲区，把数据发送出去
        out.close();
        bw.close();//使用完关闭
	}

}
