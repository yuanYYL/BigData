package com.ylch.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.ylch.util.JsonSplitUtil;



/**
 * Servlet implementation class BigDataServlet
 */
@WebServlet("/searchScoreCard")
public class BigDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BigDataServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		try {
			// Ajax跨域请求处理 start
			//跨域请求来源
			response.setHeader("Access-Control-Allow-Origin", "*");
			//跨域请求方式
			response.setHeader("Access-Control-Allow-Methods", "*");
			//跨域请求设置的Headers字段
			response.setHeader("Access-Control-Allow-Headers", "*");
			//返回结果可以用于缓存的最大时间，单位为妙，(-1表示禁用缓存)
			//当两个同样的url请求连接时，只对其中一个url有效
			response.setHeader("Access-Control-Max-Age", "3600");
			//response.setHeader("Access-Control-Allow-Credentials", "true");
			//Ajax跨域请求处理 end
			response.setContentType("text/json; charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Cache-Control", "must-revalidate");
			response.setDateHeader("Expires", 0);		
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			Map<String, String> map=new HashMap<String, String>();
			// 将资料解码
			String reqBody = sb.toString();
			String inJson = URLDecoder.decode(reqBody, "UTF-8");
			inJson = inJson.toUpperCase();
			try {
				// 解析Json数据
				JSONObject jsonObj = new JSONObject(inJson);
				//产品类型：1：车抵贷  2:融资租赁  3：商用车
				String acplx = jsonObj.getString("ACPLX");
				//根据产品类型筛选json字符串
				String jsonString = JsonSplitUtil.splitString(acplx, jsonObj);
				try {
					//返回json字符串
					out.write(jsonString);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					out.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("msg", "参数错误");
				jsonObject.put("code", "406");
				out.write(jsonObject.toString());
			}finally{
				out.close();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("msg", "接口异常");
			jsonObject.put("code", "404");
			out.write(jsonObject.toString());
		}finally{
			out.close();
		}
	}

}
