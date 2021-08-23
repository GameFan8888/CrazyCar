package com.CrazyCarServer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;

/**
 * Servlet implementation class TestJWT
 */
@WebServlet("/TestJWT")
public class TestJWT extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestJWT() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		createAndDecodeJWT();
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
    public void createAndDecodeJWT() {

        String jwtId = "SOMEID1234";
        String jwtIssuer = "JWT Demo";
        String jwtSubject = "Andrew";
        int jwtTimeToLive = 800000;

        String jwt = JWTDemo.createJWT(
                jwtId, // claim = jti Ψһ��ݱ�ʶ����Ҫ������Ϊһ����token,�Ӷ��ر��طŹ�����
                jwtIssuer, // claim = iss  ǩ����
                jwtSubject, // claim = sub ��������û�
                jwtTimeToLive // used to calculate expiration (claim = exp) ����ʱ�䣬�������ʱ�����Ҫ����ǩ��ʱ��
        );
        
        System.out.println("jwt = \"" + jwt.toString() + "\"");
        Claims claims = JWTDemo.decodeJWT(jwt);
        if (claims == null){
            System.out.println("Token ����");
        } else{
            System.out.println("claims = " + claims.toString());
        }


    }

 
    public void decodeShouldFail() {

        String notAJwt = "This is not a JWT";
        // This will fail with expected exception listed above
        Claims claims = JWTDemo.decodeJWT(notAJwt);
        System.out.println("claims = \"" + claims.toString() + "\"");
    }


    public void createAndDecodeTamperedJWT() {

        String jwtId = "SOMEID1234";
        String jwtIssuer = "JWT Demo";
        String jwtSubject = "Andrew";
        int jwtTimeToLive = 800000;

        String jwt = JWTDemo.createJWT(
                jwtId, // claim = jti
                jwtIssuer, // claim = iss
                jwtSubject, // claim = sub
                jwtTimeToLive // used to calculate expiration (claim = exp)
        );
      
        StringBuilder tamperedJwt = new StringBuilder(jwt);
        tamperedJwt.setCharAt(22, 'I');

        System.out.println("tamperedJwt = \"" + tamperedJwt.toString() + "\"");
        // this will fail with a SignatureException
        JWTDemo.decodeJWT(tamperedJwt.toString());
    }

}
