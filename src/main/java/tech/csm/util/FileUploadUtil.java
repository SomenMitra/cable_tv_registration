package tech.csm.util;

import java.io.FileOutputStream;
import java.io.InputStream;


import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	public static String uploadFile(MultipartFile part) {
		try {
			InputStream is = part.getInputStream();
			byte[] allData = is.readAllBytes();
			FileOutputStream fos = new FileOutputStream("D:\\file_server\\" + part.getOriginalFilename());
			fos.write(allData);
			is.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return part.getOriginalFilename();		
	}
}
