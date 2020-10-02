package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.frame.Biz;
import com.vo.ManagerVO;
import com.vo.ShopVO;

@Controller
public class ManagerController {

        @Resource(name = "mbiz")
        Biz<String, Integer, ManagerVO> biz;
        @Resource(name = "shopbiz")                                                        
        Biz<String, Integer, ShopVO> biz_shop;  // ���� �Դϴ�.
        // ������������ �̵� 
        @RequestMapping("/amain.mc")
        public ModelAndView amain() {
                ModelAndView mv = new ModelAndView();
                mv.setViewName("manager/amain");
                return mv;
        }

        // main�������� login ��ư ���� �� �α��� �������� �̵� 
        @RequestMapping("/alogin.mc")
        public ModelAndView alogin() {
                ModelAndView mv = new ModelAndView();
                mv.setViewName("manager/alogin");
                return mv;
        }
        // �α��� �Ŀ� ���� ����� logout ��ư ���� �� ���� �������� �̵�
        @RequestMapping("/alogout.mc")
        public ModelAndView alogout() {
        	ModelAndView mv = new ModelAndView();
        	mv.setViewName("manager/amain");
        	return mv;
        }
        // center ������ �̵�: Chart
        @RequestMapping("/chart.mc")
        public ModelAndView chart() {
        	ModelAndView mv = new ModelAndView();
        	mv.addObject("centerpage", "chart");
        	mv.setViewName("manager/shopdetail");
        	return mv;
        }
        // Center Page: Booklist//
        @RequestMapping("/booklist.mc")
        public ModelAndView booklist() {
        	ModelAndView mv = new ModelAndView();
        	mv.addObject("centerpage","booklist");
        	mv.setViewName("manager/shopdetail");
        	return mv;
        }

        // �α��� ��ư ���� ����
        @RequestMapping("/aloginimpl.mc")
        public ModelAndView aloginimpl(HttpServletRequest request) {
                ModelAndView mv = new ModelAndView();
                String id = request.getParameter("id");
                String pwd = request.getParameter("pwd");
                ManagerVO dbmanager = null;
                try {
                        dbmanager = biz.get1(id);
                        if (dbmanager.getManager_pwd().equals(pwd)) {
                                HttpSession session = request.getSession();
                                session.setAttribute("aloginuser", dbmanager);
                              
                                //�α��� ����� ���� ���� ����Ʈ �̾Ƴ���
                                ArrayList<ShopVO> shoplist = null;                        
                                try{
                                        shoplist = biz_shop.shop_get(id);                                        //selectall �Լ��Դϴ�. �ش� ���̵� �����ִ� shop ���̺��� ���� �����ɴϴ�.
                                }catch (Exception e) {
                                        e.printStackTrace();
                                }
                                mv.addObject("centerpage", "center1");                                // centerpage�� center1.jsp ������ ����ϴ�. centerpage�� myroom.jsp�� �ֽ��ϴ�.
                                session.setAttribute("shoplist", shoplist);                        //  �� shop ���̺��� session�� ��ҽ��ϴ�.
                                System.out.println(shoplist);
                                mv.setViewName("manager/amyroom");                                        // myroom �������� �̵��մϴ�.
                        } else {
                                mv.setViewName("redirect:alogin.mc");
                        }
                } catch (Exception e) {
                        mv.setViewName("redirect:alogin.mc");
                        e.printStackTrace();
                }

                return mv;
        }

        //Sign Up ��ư ���� �� ȸ������ �������� �̵�
        @RequestMapping("/manageradd.mc")
        public ModelAndView manageradd(ModelAndView mv) {
                mv.setViewName("manager/aregister");
                return mv;
        }

        // ȸ������ ��ư ���� ����
        @RequestMapping("/manageraddimpl.mc")
        public ModelAndView manageraddimpl(ModelAndView mv, ManagerVO manager, String user_birth_year,
                        String user_birth_month, String user_birth_day) {
                try {
                        // search_birthday  �� ����
                        manager.setManager_birthday(user_birth_year + "-" + user_birth_month + "-" + user_birth_day);
                        biz.register(manager);
                        mv.setViewName("manager/aregisterok");
                } catch (Exception e) {
                        mv.setViewName("redirect:aregister.mc");
                        e.printStackTrace();
                }
                return mv;
        }

       //���̵� �ߺ� üũ Ȯ��
                @RequestMapping("/aidcheckimpl.mc")
                public void id_check_impl(HttpServletResponse res, String id) {
                        int result = 0;

                        ManagerVO dbmanager = null;
                        try {
                                dbmanager = biz.get1(id);
                                if(dbmanager != null) {
                                        result = 1;
                                }
                        } catch (Exception e) {
                                e.printStackTrace();
                        }

                        PrintWriter out = null;
                        try {
                                out = res.getWriter();
                                out.print(result);
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                        out.close();
                }

}