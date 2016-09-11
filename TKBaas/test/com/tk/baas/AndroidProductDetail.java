package com.tk.baas;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

import com.tk.baas.util.IOUtil;

import net.sf.json.JSONObject;

public class AndroidProductDetail {

		//测试查看所有商家信息
		public static void main(String[] args) throws Exception, SQLException {
			String path = "http://localhost:9999/TKBaas/user/app/changeInfo";
			URL url = new URL(path);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			
			con.setReadTimeout(50000);
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			
			//包装发送给服务器的json
			JSONObject page = new JSONObject();
			page.put("user_id","1" );
			page.put("picture_name", null);
			page.put("picture_str", null);
			page.put("user_sex", null);
			page.put("user_name","xiaoming");
			
			/*page.put("key", "");
			page.put("sort", "sales desc");
			page.put("left","");
			page.put("right","");
			page.put("currentPage",1);
			page.put("pageSize",30);*/
			page.put("seller_id", "2");
			page.put("currentPage", "1");
			page.put("pageSize", "20");
			//page.put("user_id", "402891815678675c015678717688014a");
			//page.put("type", "west");
			//page.put("num", 100);  
			//page.put("seller_id", "1");
			/*page.put("key", "男");
			page.put("currentPage", 1);
			page.put("pageSize", 10);
*/			
			
			//发送到服务器
			OutputStream output = con.getOutputStream();
			output.write(page.toString().getBytes());
			
			String str = IOUtil.readString(con.getInputStream());
			JSONObject json = JSONObject.fromObject(str);
			System.out.println(json);
			/*boolean result = json.getBoolean("result");
			System.out.println(result);*/
		}
	

}
