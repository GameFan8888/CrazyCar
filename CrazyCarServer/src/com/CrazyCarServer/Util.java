package com.CrazyCarServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

				// ִ�в�ѯ
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
}
