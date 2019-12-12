package edu.springz.security;

import java.sql.*;
import java.util.*;

import javax.sql.DataSource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
                              "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
public class MemberTests {
   
   @Setter(onMethod_ = @Autowired)
   private PasswordEncoder pwencoder;
   // PasswordEncoder를 구현한 CustomNoOpPasswordEncoder의 메소드를 이용할 수 있음
   
   @Setter(onMethod_ = @Autowired)
   private DataSource ds;
   
//   @Test
   public void testInsertMember() {
      
      String query = "insert into tbl_memo_member(userid, userpw, username) values(?, ?, ?)";
      
      try(Connection con = ds.getConnection(); 
            PreparedStatement pstmt = con.prepareStatement(query)) {
         for(int i=0; i<100; i++) {
            pstmt.setString(2, pwencoder.encode("1111"));
            if(i<80) {
               pstmt.setString(1, "user"+i);
               pstmt.setString(3, "일반사용자"+i);
            } else if(i<90) {
               pstmt.setString(1, "manager"+i);
               pstmt.setString(3, "운영자"+i);
            } else {
               pstmt.setString(1, "admin"+i);
               pstmt.setString(3, "관리자"+i);
            }
            pstmt.executeUpdate();
         }
      } catch(Exception e) {
         e.printStackTrace();
      }
   }
   
   @Test
   public void testInsertAuth() {
      
      String query = "insert into tbl_memo_member_auth(userid, auth) values(?, ?)";
      
      try(Connection con = ds.getConnection(); 
            PreparedStatement pstmt = con.prepareStatement(query)) {
         for(int i=0; i<100; i++) {
            if(i<80) {
               pstmt.setString(1, "user"+i);
               pstmt.setString(2, "ROLE_USER");
            } else if(i<90) {
               pstmt.setString(1, "manager"+i);
               pstmt.setString(2, "ROLE_MEMBER");
            } else {
               pstmt.setString(1, "admin"+i);
               pstmt.setString(2, "ROLE_ADMIN");
            }
            pstmt.executeUpdate();
         }
      } catch(Exception e) {
         e.printStackTrace();
      }
   }
}
   