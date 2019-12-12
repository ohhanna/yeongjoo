package edu.springz.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class) //junit 쓸려고
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml") //junit 쓰기위해 경로 찾아준다
@Log4j//로그쓸려고 log.info();
public class DataSourceTests {
	@Setter(onMethod_ = @Autowired) // 자동으로 불러오게 하기위해서
	private DataSource dataSource;
	
	@Setter(onMethod_ = @Autowired)
	private SqlSessionFactory sqlSessionfactory;
	
	@Test
	public void testMyBatis() {
		try (SqlSession sqlSession = sqlSessionfactory.openSession();
			Connection con = dataSource.getConnection() ){
			log.info(sqlSession);	
			log.info(con);
		} catch (Exception e) {
			fail(e.getMessage());
		}	
	}
//	@Test
	public void testConnection() {
		try (Connection con = dataSource.getConnection()){
			log.info(con);
		} catch (Exception e) {
			fail(e.getMessage());
		}	
	}
}
