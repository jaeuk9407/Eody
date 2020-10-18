package com.frame;

import java.util.ArrayList;

public interface Dao<K1, K2, V> { //K1: STRING, K2: INTEGER
        public void insert(V v) throws Exception;
        public int delete1(K1 k) throws Exception;
        public int delete2(K2 k) throws Exception;
        public int update(V v) throws Exception;
        public V select1(K1 k) throws Exception;
        public V select2(K2 k) throws Exception;
        public ArrayList<V> selectall() throws Exception;
        public ArrayList<V> shop_select(K1 k) throws Exception;
        public ArrayList<V> bookingselect_shop(K1 k) throws Exception;          // ���� �̸� �߽����� ���ฮ��Ʈ ��� 
        public ArrayList<V> bookingget_searcher(K1 k) throws Exception;			//searcher id�� ���� ����Ʈ ��� 
        public ArrayList<V> bookingupdate_reviewstat(K1 k) throws Exception;
        public ArrayList<V> review_select(K1 k) throws Exception;
        public ArrayList<V> shop_hotplace_select(K1 k) throws Exception;        //���� �������� ���� ���
        public void shop_hitcnt(K1 k) throws Exception;                         //���� ��ȸ��
        public int shop_score_avg(K1 k) throws Exception;
}
