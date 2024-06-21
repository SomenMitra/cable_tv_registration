package tech.csm.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FileDownloadUtil {
	
	public static void downloadFile(HttpServletRequest req, HttpServletResponse resp) throws FileNotFoundException, IOException {
		String fileName = "D:/file_server/" + req.getParameter("fileName");
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-disposition", "attachment;filename=" + req.getParameter("fileName"));
		
		try (FileInputStream in = new FileInputStream(fileName);
				OutputStream out = resp.getOutputStream()) {
			
	            byte[] buffer = new byte[1024];	            
	            int numBytesRead;
	            while ((numBytesRead = in.read(buffer)) > 0) {
	                out.write(buffer, 0, numBytesRead);
	            }
		}

		
	}

}
