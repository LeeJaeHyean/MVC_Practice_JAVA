package com.example.demo.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.demo.dto.BoardDto;
import com.example.demo.dto.LogInDto;

@Component
public class BoardDao {
   
   
   public Connection getConnection() {
      Connection conn = null;
      String driver = "com.mysql.cj.jdbc.Driver";
      String url = "jdbc:mysql://localhost:3306/project?serverTimezone=UTC";
      String user = "root";
      String pw = "1234";
      
      try {
         Class.forName(driver);
         conn = DriverManager.getConnection(url,user,pw);
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      }
      catch (Exception e) {
         e.printStackTrace();
      }
      return conn;
   }
   
   public void close(Connection conn, PreparedStatement pstmt) {
      try {
         conn.close();
         pstmt.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }
   
   public void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
      try {
         conn.close();
         pstmt.close();
         rs.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }
   
  
   public LogInDto selectLogin(String id, String pw) {
      Connection conn = getConnection();
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String sql = " SELECT * from member4 WHERE id=? and pw=? ";
      LogInDto dto = null;
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, id);
         pstmt.setString(2, pw);
         rs = pstmt.executeQuery();
         
         while(rs.next()) {
            dto = new LogInDto();
            dto.setId(rs.getString("id"));
            dto.setPw(rs.getString("pw"));
            dto.setName(rs.getString("name"));
            dto.setSex(rs.getString("sex"));
            dto.setBrith1(rs.getString("brith1"));
         }
      } catch (SQLException e)
      {
         e.printStackTrace();
      }
      return dto;
   }
   
   public void insertMember(LogInDto loginDto) { 
      Connection conn = getConnection();
      PreparedStatement pstmt = null;
      String sql = " insert into member4 values(?, ?, ?, ?, ?) ";
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, loginDto.getId());
         pstmt.setString(2, loginDto.getPw());
         pstmt.setString(3, loginDto.getName());
         pstmt.setString(4, loginDto.getSex());
         pstmt.setString(5, loginDto.getBrith1());
         
         pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         close(conn,pstmt);
      }
   }
   
   public void delete(int no, String id) {
      Connection conn = getConnection();
      PreparedStatement pstmt = null;
      String sql = " DELETE FROM boardTable WHERE id=? and no=? ";
      
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, id);
         pstmt.setInt(2, no);
         pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         close(conn, pstmt);
      }
   }

   public ArrayList<BoardDto> selectBoard() {
      Connection conn = getConnection();
      PreparedStatement pstmt = null;
      String sql = " SELECT no, id, title, contents FROM boardTable ";
      ResultSet rs = null;
      ArrayList<BoardDto> dto_list = new ArrayList<>();
      try {
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();
         
         while(rs.next()) {
            BoardDto dto = new BoardDto();
            dto.setNo(rs.getInt("no"));
            dto.setId(rs.getString("id"));
            dto.setTitle(rs.getString("title"));
            dto.setContents(rs.getString("contents"));
            dto_list.add(dto);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return dto_list;
   }
   
   public String modify(int no) {
      Connection conn = getConnection();
      PreparedStatement pstmt = null;
      String sql = " SELECT contents FROM boardTable WHERE no=? " ;
      ResultSet rs = null;
      String cont = "";
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, no);
         rs = pstmt.executeQuery();
         while(rs.next()) {
            cont = rs.getString("contents");
         }
      } catch (SQLException e) {
         System.out.println(cont);
         e.printStackTrace();
      } finally {
         close(conn, pstmt, rs);
      }
      return cont;
   }
  
   
		
   public void modifyOK(BoardDto dto) {
      Connection conn = getConnection();
      PreparedStatement pstmt = null;
      String sql = " UPDATE boardTable SET contents=? WHERE id=? and no=? " ;
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, dto.getContents());
         pstmt.setString(2, dto.getId());
         pstmt.setInt(3, dto.getNo());
         System.out.println(dto.getContents());
         pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         close(conn, pstmt);
      }
   }
   
   public void writeOk(BoardDto board_dto) {
      
      Connection conn = getConnection();
      PreparedStatement pstmt = null;
      String sql = " insert into boardTable(no, id, title, contents) values(?, ?, ?, ?) ";
      
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, board_dto.getNo());
         pstmt.setString(2, board_dto.getId());
         pstmt.setString(3, board_dto.getTitle());
         pstmt.setString(4, board_dto.getContents());
         pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         close(conn, pstmt);
      }
   }
   
   public Map<String,Object> viewOK(int no) {
	   Map<String,Object> retunMap = new HashMap<String,Object>() ;  
	   
	   Connection conn = getConnection();
	      PreparedStatement pstmt = null;
	      String sql = " SELECT*FROM boardTable WHERE no=?" ;
	      ResultSet rs = null;
	      String cont = "";
	      try {
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setInt(1, no);
	         rs = pstmt.executeQuery();
	         while(rs.next()) {
	        	 retunMap.put("title", rs.getString("title"));
	        	 retunMap.put("contents", rs.getString("contents"));
	        	 retunMap.put("id", rs.getString("id"));
	         }
	      } catch (SQLException e) {
	         System.out.println(cont);
	         e.printStackTrace();
	      } finally {
	         close(conn, pstmt, rs);
	      }
	      return retunMap;
	   }
}

	
