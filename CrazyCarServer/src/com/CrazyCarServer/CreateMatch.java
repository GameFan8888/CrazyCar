package com.CrazyCarServer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Util.Util;
import com.alibaba.fastjson.JSONObject;

/**
 * Servlet implementation class CreateMatch
 */
@WebServlet("/CreateMatch")
public class CreateMatch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateMatch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("BuyEquip ...");
		String token = request.getHeader("Authorization");
		if (!Util.JWT.isLegalJWT(token)){
			System.out.println("illegal JWT");
			return;
		} 
		
		PrintWriter out = response.getWriter();
		JSONObject outJB = new JSONObject();
		outJB.put("code", 200);
		createMatch();
		out.println(outJB.toString());
		out.flush();
		out.close();	
	}
	
	private void createMatch() {
		long enrollTime =  System.currentTimeMillis()/1000;
		long startTime = enrollTime + 60;
		String sql = "insert into match_class (star, map_id, limit_time, class_name, times, start_time, enroll_time) values "
				+ "(2, 0, 60, " + "\"TastSong\"" + ", 1, " +  startTime + ", " + enrollTime + ");";
		Util.JDBC.executeInsert(sql);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
