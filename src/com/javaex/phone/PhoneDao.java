package com.javaex.phone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PhoneDao {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "phonedb";
	private String pw = "phonedb";
	//DB연결
	public void getConnection() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
		    System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
	}
	//자원정리
	public void close() {
		try {
			if(rs!=null) {
				rs.close();
			}
			if(pstmt!=null) {
				pstmt.close();
			}
			if(conn!=null) {
				conn.close();
			}
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
	}
	
	/***1.리스트***/
	public List<PersonVo> personSelect() {
		List<PersonVo> personList = new ArrayList<PersonVo>();
		getConnection();
		try {
			//SQL문
			String query = "";
			query += " select person_id, ";
			query += " 		  name, ";
			query += " 		  hp, ";
			query += " 		  company ";
			query += " from person ";
			//바인딩
			pstmt = conn.prepareStatement(query);
			//실행
			rs = pstmt.executeQuery();
			//결과처리
			while(rs.next()) {
				int personId = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");
				
				PersonVo personVo = new PersonVo(personId, name, hp, company);
				personList.add(personVo);
			}
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		close();
		return personList;
	}
	
	/***2.등록***/
	public int personInsert(PersonVo personVo) {
		int count = -1;
		getConnection();
		try {
			//SQL문
			String query = "";
			query +=" insert into person ";
			query +=" values(seq_person_id.nextval, ?, ?, ?) ";
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3,  personVo.getCompany());
			//실행
			count = pstmt.executeUpdate();
			//결과처리
			System.out.println("["+count+"건 등록되었습니다.]");
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		close();
		return count;
	}
	
	/***3.수정***/
	public int personUpdate(PersonVo personVo) {
		int count = -1;
		getConnection();
		try {
			//SQL문
			String query = "";
			query += " update person ";
			query += " set  name = ?, ";
			query += " 	    hp = ?, ";
			query += " 		company = ? ";
			query += " where person_id = ? ";
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3,  personVo.getCompany());
			pstmt.setInt(4, personVo.getPersonId());
			//실행
			count = pstmt.executeUpdate();
			//결과처리
			System.out.println("["+count+"건 수정되었습니다.]");
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		close();
		return count;
	}
	
	/***4.삭제***/
	public int personDelete(PersonVo personVo) {
		int count = -1;
		getConnection();
		try {
			//SQL문
			String query = "";
			query += " delete from person ";
			query += " where person_id = ? ";
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, personVo.getPersonId());
			//실행
			count = pstmt.executeUpdate();
			//결과처리
			System.out.println("["+count+"건 삭제되었습니다.]");
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		close();
		return count;
	}
	
	/***5.검색***/
	public List<PersonVo> personSearch() {
		Scanner sc = new Scanner(System.in);
		String search = sc.next();
		List<PersonVo> personSearch = new ArrayList<PersonVo>();
		getConnection();
		try {
			//SQL문
			String query = "";
			query += " select person_id, ";
			query += " 		  name, ";
			query += " 		  hp, ";
			query += " 		  company ";
			query += " from	  person ";
			query += " where  name like ? ";
			query += " or	  hp like ? ";
			query += " or	  company ? ";
			//바인딩
			String searchP = '%'+search+'%';
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, searchP);
			pstmt.setString(2, searchP);
			pstmt.setString(3, searchP);
			//실행
			rs = pstmt.executeQuery();
			//결과처리
			while(rs.next()) {
				int personId = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");
				
				PersonVo personVo = new PersonVo(personId, name, hp, company);
				personSearch.add(personVo);
			}
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		}
		sc.close();
		close();
		return personSearch;
	}
}
