package com.tk.baas.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.plexus.util.Base64;

public class IOUtil {
	
	public static String readString(InputStream input) throws IOException{
		
		BufferedReader ois =new BufferedReader(new InputStreamReader(input));
		StringBuffer sb = new StringBuffer();
		String str = null;
		
		while((str = ois.readLine()) != null){
			sb.append(str);
		}
		return sb.toString();
	}
    
	public static void outPut(HttpServletResponse response, 
			                  String json) throws Exception{
		OutputStream output = response.getOutputStream();
		output.write(json.getBytes());
	}
	
	public static String writeFile(String writePath, String fileStr, String fileName) throws IOException {
		fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
		
		byte[] b = Base64.decodeBase64(fileStr.getBytes());
		ByteArrayInputStream byteZip = new ByteArrayInputStream(b);
		GZIPInputStream zip = new GZIPInputStream(byteZip);
		
		FileOutputStream fos = new FileOutputStream(writePath + fileName);
		byte[] bb = new byte[1024];
		int len = 0;
		while((len = zip.read(bb)) != -1) {
			fos.write(bb, 0, len);
		}
		
		return fileName;
	}

}
