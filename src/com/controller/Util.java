package com.controller;

import java.io.FileOutputStream;
import org.springframework.web.multipart.MultipartFile;

public class Util {
        // �޾ƿ� �����͸� ������ �̹����� �ø���.
        public static void saveFile(MultipartFile mf, String review_name) {
        	//���丮 ���ι޾ƾ��Ұ����� �����ؾ���.
        	//Shopadd, Reviewadd���� ���ǰ� ����. ���� �б�ó��
                String dir = "C:\\new_eody\\Eody\\web\\img";
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