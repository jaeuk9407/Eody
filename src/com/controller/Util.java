package com.controller;

import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public class Util {
        // �޾ƿ� �����͸� ������ �̹����� �ø���.
        public static void saveShopFile(HttpServletRequest request, MultipartFile mf, String review_name) {
        	// ���丮 ���ι޾ƾ��Ұ����� �����ؾ���.   
    		// ����������� ��Ŭ�������� �׽�Ʈ�ϱ� �����
        		//String uploadPath = request.getSession().getServletContext().getRealPath("/img/shopImg/"); 
        		String dir = "C:\\java\\Eody\\web\\img\\shopImg\\";
        		byte [] data;
        		String imgname = mf.getOriginalFilename();
        		try {
        			data = mf.getBytes();
        			FileOutputStream fo = 
        					new FileOutputStream(dir+imgname);
        			
        			fo.write(data);
        			fo.close();
        		}catch(Exception e) {
        			e.printStackTrace();
        		}
       	}
        

		public static void saveReviewFile(MultipartFile mf, String review_name) {
			//���丮 ���ι޾ƾ��Ұ����� �����ؾ���.         	
		        String dir = "C:\\java\\Eody\\web\\img\\reviewImg\\";
				byte [] data;
				String imgname = mf.getOriginalFilename();
				try {
					data = mf.getBytes();
					FileOutputStream fo = 
							new FileOutputStream(dir+imgname);
					
					fo.write(data);
					fo.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
		}
}