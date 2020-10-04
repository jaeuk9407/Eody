package com.controller;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.frame.Biz;
import com.vo.DistanceVO;
import com.vo.HotPlaceVO;

public class RegionSearch {

	public ArrayList<HotPlaceVO> DistanceFirstSearch(String[] keys){

			System.out.println("App Start .....");
			AbstractApplicationContext factory =
					new GenericXmlApplicationContext("myspring.xml");
			System.out.println("Spring Started .......");

			Biz<String,Integer,HotPlaceVO> biz =
					(Biz)factory.getBean("hbiz");


			//rawdata set up
			ArrayList<HotPlaceVO> rawdata = null;
			try {
				rawdata = biz.get();
	//			System.out.println("get test!!!!!!!!!!!!!!!!!!!!");
	//			for(HotPlaceVO co: rawdata) {
	//				System.out.println(co);
	//			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			//�Է¹��� ������� �ִ�, �ּ� �浵 ��ǥ
			double maxLat=0.0, minLat=9999.9;
			//�Է¹��� ������� �ִ�, �ּ� ���� ��ǥ
			double maxLng=0.0, minLng=9999.9;
			for(int i=0; i<keys.length; i++) {
				HotPlaceVO hotKey = new HotPlaceVO();
				try {
					hotKey = biz.get1(keys[i]);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(maxLat < Double.parseDouble(hotKey.getH_lat())) {
					maxLat = Double.parseDouble(hotKey.getH_lat());
				}
				if(minLat > Double.parseDouble(hotKey.getH_lat())) {
					minLat = Double.parseDouble(hotKey.getH_lat());
				}
				if(maxLng < Double.parseDouble(hotKey.getH_lng())) {
					maxLat = Double.parseDouble(hotKey.getH_lng());
				}
				if(minLng > Double.parseDouble(hotKey.getH_lng())) {
					minLat = Double.parseDouble(hotKey.getH_lng());
				}
			}
			
			//������� �Ÿ� ��꿡 ���ʿ��� rawdata�� ������ ArrayList
			ArrayList<HotPlaceVO> filteredData = new ArrayList<>();
			for(HotPlaceVO hp : rawdata) {
				hp.getH_lat();
				hp.getH_lng();
			}
			//rawdata, ������� �Ÿ��� ��Ƶα� ���� 2���� �迭 
	        double[][] dists = new double[keys.length][rawdata.size()];

	        for(int j=0; j<rawdata.size();j++) {
	        	/* lat_raw = rawdata�� �浵 
	        	 * lng_raw = rawdata�� ���� 
	        	 * lat_key = ������� �浵 
	        	 * lng_key = ������� ���� 
	        	 */
	        	double lat_raw=0.0;
	        	double lng_raw=0.0;
	        	double lat_key=0.0;
	        	double lng_key=0.0;

	        	//rawdata�� �浵, ���� �Է� 
	        	lat_raw = Double.parseDouble(rawdata.get(j).getH_lat());
	        	lng_raw = Double.parseDouble(rawdata.get(j).getH_lng());

	        	//�Է¹��� ��ġ�� ������ŭ ����� lat, lng �Է�  
	        	for(int i=0;i<keys.length;i++) {
	        		//�Է� ���� ��ġ�� lat �Է� 
	        		try {
						lat_key = Double.parseDouble(biz.get1(keys[i]).getH_lat());
						//System.out.println("lat_key: "+lat_key+"�Է� �Ϸ�.");
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
	        		//�Է� ���� ��ġ�� lng �Է� 
	        		try {
						lng_key = Double.parseDouble(biz.get1(keys[i]).getH_lng());
	//					System.out.println("lng_key: "+lng_key+"�Է� �Ϸ�.");
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
						}
	        		
		        	//�Է¹��� ������� ���� �ִ� rawdata �Ÿ� ��� 
		        	double theta = lng_key - lng_raw;
		            double dist = Math.sin(deg2rad(lat_key)) * Math.sin(deg2rad(lat_raw)) + Math.cos(deg2rad(lat_key)) * Math.cos(deg2rad(lat_raw)) * Math.cos(deg2rad(theta));
		            try {
	//					System.out.print(biz.get(keys[i]).getH_name());
					} catch (Exception e) {
						e.printStackTrace();
					}
	//	            System.out.println("dist 1�� ���: "+dist+"�Է� �Ϸ�.");
		            dist = Math.acos(dist);
	//	            System.out.println("dist 2�� ���: "+dist+"�Է� �Ϸ�.");
		            dist = rad2deg(dist);
	//	            System.out.println("dist 3�� ���: "+dist+"�Է� �Ϸ�.");
		            dist = dist * 60 * 1.1515;
		            
		            //killometer ���� ��ȯ 
		            dist = dist * 1.609344;
		            dists[i][j] = dist;
	        	}
	        }
	        //�Ÿ� ��� ���� NaN�� ������ ����(�ڱ� ��ġ���� �ڱ� ��ġ �Ÿ� ��� ���̽�) 0���� ó��
	        for(int j=0; j<rawdata.size();j++) {
	        	for(int i=0;i<keys.length;i++) {
	        		if(Double.isNaN(dists[i][j])) {
	        			dists[i][j]=0.0;
	        		}
	        	}
	        }
	        
	        //dist �迭(������� rawdata �Ÿ�) �Է� �� Ȯ�� 
	        for(int i=0;i<keys.length;i++) {
	        	for(int j=0;j<rawdata.size(); j++) {
	        		System.out.print(dists[i][j]+ " ");
	        	}
	        	System.out.println();
	        }
	        
	    	//rawdata�� �� ������� �Ÿ� ���
	        //DistanceVO[] distances = new DistanceVO[rawdata.size()];
	        ArrayList<DistanceVO> distances = new ArrayList<>();
	        
	        double[] sumarr = new double[rawdata.size()];
	        
	        System.out.println("rawdata�� avg distance");
	    	for(int i =0; i<rawdata.size();i++) {
	    		double sum=0.0;
	    		for(int j=0; j<keys.length;j++) {
	    			sum += dists[j][i];
	    		}
	    		DistanceVO distance = new DistanceVO();
	    		distance.setHp(rawdata.get(i));
	    		distance.setSum_distance(sum);
	    		distance.setAvg_distance(sum/keys.length);
	    		distances.add(distance);
	    		System.out.println(distances.get(i).getAvg_distance()+" ");

	    	}
	    
	    	
	    	//����
	    	System.out.println("@@@@@@@@@@@@@@@@@@@@Before sort@@@@@@@@@@@@@@@@@@@@@@@");
	    	print(distances);
	    	System.out.println("");
	    	System.out.println("@@@@@@@@@@@@@@@@@@@@@@After sort@@@@@@@@@@@@@@@@@@@@@@@@");
	    	Collections.sort(distances);
	    	print(distances);
	    	System.out.println("");
	    	
	    	//��ȯ�� 3�� ��ü ���� �迭 ���� 
	    	DistanceVO[] result = new DistanceVO[3];
	    	System.out.println("@@@@@@@@@@@@@@@��õ 3����@@@@@@@@@@@@@@@");
	    	
	    	//i: ��� ��ȯ �迭 �ε���, j: ����� �ε���, temp: �Ÿ���� ArrayList �ε���
			int temp = 0;
	    	for(int i =0; i<3; i++) {
	    		result[i] = new DistanceVO();
	    			//�Է¹��� ������� ArrayList���� ã�� �����մϴ�.
	    			for(int j =0; j<keys.length;j++) {
	    				//�Է¹��� ������� �̸��� ��õ���� �������� �̸��� ������ ArrayList���� �����մϴ�.
	    				if(keys[j].equals(distances.get(temp).getHp().getH_name())) {
	    					distances.remove(distances.get(temp));
	    				}else {
	    					temp=0;
	    				}
	    			}
	    			result[i] = distances.get(temp);
	    			System.out.println(result[i]);
	    			distances.remove(distances.get(temp));
	    			temp=0;
	    		if(result[2]!=null) {
	    			break;
	    		}
	    	}
	    	
	    	
	    	//print(result);
	    	ArrayList<HotPlaceVO> result_list = new ArrayList<HotPlaceVO>();
	    	for(int i=0;i<3;i++) {
	    		result_list.add(result[i].getHp());
	    	}
	    	
			return result_list;
		}
		
	    // This function converts decimal degrees to radians
	    private static double deg2rad(double deg) {
	        return (deg * Math.PI / 180.0);
	    }
	     
	    // This function converts radians to decimal degrees
	    private static double rad2deg(double rad) {
	        return (rad * 180 / Math.PI);
	    }
	    //test�� ��ü ArrayList�� print���ִ� �Լ��Դϴ�.
	    public static void print(ArrayList<DistanceVO> distances) {
			for (int i = 0; i < distances.size(); i++) {
				System.out.println(distances.get(i));
			}
		}

}

