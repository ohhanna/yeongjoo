package edu.springz.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.springz.service.MemoServiceTests;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
					   "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
@WebAppConfiguration
public class MemoControllerTests {
	
	@Setter(onMethod_ = @Autowired)
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc; //가짜 MVC 
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test
	public void testModify() throws Exception {
		log.info(
            mockMvc.perform(MockMvcRequestBuilders.post("/memo/modify")
            .param("no", "1")
            .param("title", "test new title")
            .param("content", "text new content")
            .param("id", "user00"))
            .andReturn().getModelAndView().getViewName());
	}
	
//	@Test
	public void testReomve() throws Exception {
		
		 log.info (mockMvc.perform(MockMvcRequestBuilders.post("/memo/remove")
		        		.param("no", "8"))
		        		.andReturn().getModelAndView().getViewName());
		
	}
	
//	@Test
	public void testGet() throws Exception {
		log.info(
            mockMvc.perform(MockMvcRequestBuilders.get("/memo/read")
            		.param("no", "1"))
            		.andReturn().getModelAndView().getModelMap());
	}
	
//	@Test
	public void testRegister() throws Exception {
		log.info(
            mockMvc.perform(MockMvcRequestBuilders.post("/memo/register")
            .param("title", "test new title")
            .param("content", "text new content")
            .param("id", "user00"))
            .andReturn().getModelAndView().getModelMap());
	}
	
//	@Test
	public void testList() throws Exception {
		log.info(
            mockMvc.perform(MockMvcRequestBuilders.get("/memo/list"))
            .andReturn().getModelAndView().getModelMap());
	}
	
	
	
	
	
	
	
	

}
