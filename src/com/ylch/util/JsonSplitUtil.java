package com.ylch.util;

import org.json.JSONObject;

/*
 * 更据产品的不同，返回不同的json字符串
 */
public class JsonSplitUtil {
	
	 public static String splitString(String acplx,JSONObject jsonObj){
		 Prop cdd = PropKit.use("scard.properties");
		 JSONObject jsonObject=new JSONObject();
		 String cddString="";
		 if(acplx.equals("1")){
		  //车抵贷 
			 cddString = cdd.get("cddScoreCard");
		 }else if(acplx.equals("2")){
		  //融资租赁
			 cddString = cdd.get("rzzlScoreCard");
		 }else if(acplx.equals("3")){
		  //商用车
			 cddString = cdd.get("cardScoreCard");
		 }
		String[] splitArr = cddString.split(",");
		for (int i = 0; i < splitArr.length; i++) {		
			String string = jsonObj.getString(splitArr[i]);
			jsonObject.put(splitArr[i], string);
		}
		 return jsonObject.toString();
	 }
}
