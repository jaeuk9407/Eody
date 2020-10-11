package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.frame.Biz;
import com.vo.ReviewVO;
import com.vo.SearcherVO;
import com.vo.ShopVO;

@Controller
public class ReviewController {

        @Resource(name = "rbiz")
        Biz<String, Integer, ReviewVO> rbiz;
        @Resource(name = "shopbiz")
        Biz<String,Integer, ShopVO> shopbiz;
        
        // ���� ��� ����
        @RequestMapping("/reviewadd.mc")
        public ModelAndView reviewadd(ModelAndView mv, ReviewVO review, SearcherVO searcher,
                        @RequestParam("files") MultipartFile[] files) {
        	
        		// ���� �̸��� hidden ������ �Ѱܹ޾���
                // ���信 �ű� ������ hidden ������ �Ѱܹ޾���
                // ���並 �ۼ��� searcher�� nickname���� hidden���� �Ѱܹ޾���

                // ���信 ���ε��� �����̸� ����
                System.out.println("size : " + files.length);
                int len = files.length;
                System.out.println("���� ���� : " + len);
                if(files[0].getOriginalFilename() == "") {
                	review.setReview_image1("default.jpg");
                    review.setReview_image2("default.jpg");
                    review.setReview_image3("default.jpg");
                }
                else if (len == 1) {
                        review.setReview_image1(review.getReview_name() + files[0].getOriginalFilename());
                        review.setReview_image2("default.jpg");
                        review.setReview_image3("default.jpg");
                } else if (len == 2) {
                        review.setReview_image1(review.getReview_name() + files[0].getOriginalFilename());
                        review.setReview_image2(review.getReview_name() + files[1].getOriginalFilename());
                        review.setReview_image3("default.jpg");
                } else {
                        review.setReview_image1(review.getReview_name() + files[0].getOriginalFilename());
                        review.setReview_image2(review.getReview_name() + files[1].getOriginalFilename());
                        review.setReview_image3(review.getReview_name() + files[2].getOriginalFilename());
                }

                try {
                        rbiz.register(review);
                        // �������� ������ ����
                        for (MultipartFile f : files) {
                                if (f.getOriginalFilename() == "") {
                                        continue;
                                }
                                Util.saveReviewFile(f, review.getReview_name());
                        }
                        // ���� ��� ���� ����
                        shopbiz.shop_score_avg(review.getShop_name());
                } catch (Exception e) {
                        e.printStackTrace();
                }

                // redirect���� �ؾ� submit �ߺ��� ����
                mv.setViewName("redirect:myroom.mc");
                return mv;
        }

        // ���丮��Ʈ ȭ�� ó��
        @ResponseBody
        @RequestMapping("/getReview.mc")
        public void getReview(HttpServletResponse res, String ashop) throws IOException {
                System.out.println("shop �̸� : " + ashop);
                JSONArray ja = new JSONArray();
                ArrayList<ReviewVO> list = new ArrayList<>();
                try {
                        list = rbiz.review_get(ashop);
                } catch (Exception e) {
                        System.out.println("error");
                        e.printStackTrace();
                }
                //System.out.println("list: " + list.toString());
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                System.out.println("������ : " + list.size());
                for (int i = 0; i < list.size(); i++) {
                        JSONObject data = new JSONObject();
                        data.put("review_date", format.format(list.get(i).getReview_date()));
                        data.put("review_contents", list.get(i).getReview_contents());
                        data.put("review_image1", list.get(i).getReview_image1());
                        data.put("review_image2", list.get(i).getReview_image2());
                        data.put("review_image3", list.get(i).getReview_image3());
                        data.put("shop_name", list.get(i).getShop_name());
                        data.put("review_name", list.get(i).getReview_name());
                        data.put("shop_score", list.get(i).getShop_score()+ "");
                        ja.add(data);
                        //System.out.println(ja.toString());
                        
                }
                //System.out.println("ja : " + ja);
                res.setCharacterEncoding("utf-8");
                res.setContentType("application/json");
                PrintWriter out = res.getWriter();

                
                out.print(ja.toJSONString());
                out.close();
        }


}