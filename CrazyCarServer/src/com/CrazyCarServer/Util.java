package com.CrazyCarServer;

import java.security.Key;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Util {
	public static class JDBC{
		// MySQL 8.0 ���ϰ汾 - JDBC �����������ݿ� URL
		static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
		static final String DB_URL = "jdbc:mysql://localhost:3306/crazy_car?"
				+ "useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

		// ���ݿ���û��������룬��Ҫ�����Լ�������
		static final String USER = "root";
		static final String PASS = "164728";

		public static String ExecuteSelectString(String sql, String key) {
			System.out.println("ExecuteSelect sql = " + sql);
			Connection conn = null;
			Statement stmt = null;
			String resultStr = null;
			try {
				// ע�� JDBC ����
				Class.forName(JDBC_DRIVER);

				// ������
				//System.out.println("�������ݿ�...");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);

				// ִ�в�ѯ
				//System.out.println(" ʵ����Statement����...");
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);

				// չ����������ݿ�
				while (rs.next()) {
					// ͨ���ֶμ���
					resultStr = rs.getString(key);
					System.out.println("ExecuteSelect : " + key + " = " + resultStr);
				}
				// ��ɺ�ر�
				System.out.println("ExecuteSelect  Finish");
				rs.close();
				stmt.close();
				conn.close();
				return resultStr;
			} catch (SQLException se) {
				// ���� JDBC ����
				se.printStackTrace();
			} catch (Exception e) {
				// ���� Class.forName ����
				e.printStackTrace();
				return resultStr;
			} finally {
				// �ر���Դ
				try {
					if (stmt != null)
						stmt.close();
				} catch (SQLException se2) {
				} // ʲô������
				try {
					if (conn != null)
						conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

			System.out.println("Goodbye!");
			return resultStr;
		}
		
		public static int ExecuteSelectInt(String sql, String key) {
			System.out.println("ExecuteSelect sql = " + sql);
			Connection conn = null;
			Statement stmt = null;
			int resultInt = -1;
			try {
				// ע�� JDBC ����
				Class.forName(JDBC_DRIVER);

				// ������
				//System.out.println("�������ݿ�...");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				//System.out.println(" ʵ����Statement����...");
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);

				// չ����������ݿ�
				while (rs.next()) {
					// ͨ���ֶμ���
					resultInt = rs.getInt(key);
					System.out.println("ExecuteSelect : " + key + " = " + resultInt);
				}
				// ��ɺ�ر�
				System.out.println("ExecuteSelect  Finish");
				rs.close();
				stmt.close();
				conn.close();
				return resultInt;
			} catch (SQLException se) {
				// ���� JDBC ����
				se.printStackTrace();
			} catch (Exception e) {
				// ���� Class.forName ����
				e.printStackTrace();
				return resultInt;
			} finally {
				// �ر���Դ
				try {
					if (stmt != null)
						stmt.close();
				} catch (SQLException se2) {
				} // ʲô������
				try {
					if (conn != null)
						conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

			System.out.println("Goodbye!");
			return resultInt;
		}
		
		public static List<Integer> ExecuteSelectAllInt(String sql, String key) {
			System.out.println("ExecuteSelect sql = " + sql);
			Connection conn = null;
			Statement stmt = null;
			List<Integer> resultList = new ArrayList<Integer>();
			try {
				// ע�� JDBC ����
				Class.forName(JDBC_DRIVER);

				// ������
				//System.out.println("�������ݿ�...");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);

				// ִ�в�ѯ
				//System.out.println(" ʵ����Statement����...");
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);

				// չ����������ݿ�
				while (rs.next()) {
					// ͨ���ֶμ���
					resultList.add(rs.getInt(key));
					System.out.println("ExecuteSelect : " + key + " = " + rs.getInt(key));
				}
				// ��ɺ�ر�
				System.out.println("ExecuteSelect  Finish");
				rs.close();
				stmt.close();
				conn.close();
				return resultList;
			} catch (SQLException se) {
				// ���� JDBC ����
				se.printStackTrace();
			} catch (Exception e) {
				// ���� Class.forName ����
				e.printStackTrace();
				return resultList;
			} finally {
				// �ر���Դ
				try {
					if (stmt != null)
						stmt.close();
				} catch (SQLException se2) {
				} // ʲô������
				try {
					if (conn != null)
						conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

			System.out.println("Goodbye!");
			return resultList;
		}
		
		public static void ExecuteInsert(String sql) {
			Connection conn = null;
			System.out.println("ExecuteInsert sql = " + sql);	
			try {
				// ע�� JDBC ����
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DB_URL, USER, PASS);

				PreparedStatement pst = conn.prepareStatement(sql);
	            pst.executeUpdate();
				System.out.println(" ExecuteInsert Finish " + pst);
				conn.close();
				return;
			} catch (SQLException se) {
				// ���� JDBC ����
				se.printStackTrace();
			} catch (Exception e) {
				// ���� Class.forName ����
				e.printStackTrace();
				return;
			} finally {
				// �ر���Դ
				try {
					if (conn != null)
						conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

			System.out.println("Goodbye!");
			return;
		}
	}
	
	public static class JWT {

	    // The secret key. This should be in a property file NOT under source
	    // control and not hard coded in real life. We're putting it here for
	    // simplicity.
	    private static String SECRET_KEY = "oeRaQQ7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";

	    //Sample method to construct a JWT
	    private static String createJWT(String id, String issuer, String subject, long ttlMillis) {

	        //The JWT signature algorithm we will be using to sign the token
	        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	        long nowMillis = System.currentTimeMillis();
	        Date now = new Date(nowMillis);

	        //We will sign our JWT with our ApiKey secret
	        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
	        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

	        //Let's set the JWT Claims
	        JwtBuilder builder = Jwts.builder().setId(id)
	                .setIssuedAt(now)
	                .setSubject(subject)
	                .setIssuer(issuer)
	                .signWith(signatureAlgorithm, signingKey);

	        //if it has been specified, let's add the expiration
	        if (ttlMillis >= 0) {
	            long expMillis = nowMillis + ttlMillis;
	            Date exp = new Date(expMillis);
	            builder.setExpiration(exp);
	        }

	        //Builds the JWT and serializes it to a compact, URL-safe string
	        return builder.compact();
	    }

	    private static Claims decodeJWT(String jwt) {

	        //This line will throw an exception if it is not a signed JWS (as expected)
	    	Claims claims = null;
	    	
	    	try {
	    		claims = Jwts.parser()
	                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
	                    .parseClaimsJws(jwt).getBody();
	    	} catch (Exception e) {
				e.printStackTrace();
			}
	        
	        return claims;
	    } 
	    
	    public static String createJWTById(String id) {

	        String jwtId = id;
	        String jwtIssuer = "TastSong";
	        String jwtSubject = "CrazyCar";
	        int jwtTimeToLive = 6000000;

	        String jwt = Util.JWT.createJWT(
	                jwtId, // claim = jti Ψһ��ݱ�ʶ����Ҫ������Ϊһ����token,�Ӷ��ر��طŹ�����
	                jwtIssuer, // claim = iss  ǩ����
	                jwtSubject, // claim = sub ��������û�
	                jwtTimeToLive // used to calculate expiration (claim = exp) ����ʱ�䣬�������ʱ�����Ҫ����ǩ��ʱ��
	        );
	        
	        System.out.println("createAndDecodeJWT jwt = \"" + jwt.toString() + "\"");
	        return jwt;
	    }
	    
	    public static boolean isLegalJWT(String jwt) {	        	        
	        System.out.println("isLegalJWT jwt = \"" + jwt.toString() + "\"");
	        Claims claims = Util.JWT.decodeJWT(jwt);
	        if (claims == null){
	            System.out.println("Token ����");
	            return false;
	        } else{
	            System.out.println("claims = " + claims.toString());
	            return true;
	        }
	    }
	}
	
	public static int GetIdByName(String userName, String id){
		String sql = "select " + id + " from all_user where user_name = "
				+ "\"" + userName + "\";";
		return Util.JDBC.ExecuteSelectInt(sql, id);
	}
}
