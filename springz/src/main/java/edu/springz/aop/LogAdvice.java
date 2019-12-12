package edu.springz.aop;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect
@Log4j
@Component
public class LogAdvice {
	
	@Around("execution(* org.zerock.service.SampleService*.*(..))")
	public Object logTime(ProceedingJoinPoint pjp) {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat sf = new SimpleDateFormat("ss");
		
		long start = System.currentTimeMillis();
		log.info("START : " + df.format(start));
		log.info("TAGRGET : " + pjp.getTarget());
		log.info("PARAM : " + Arrays.toString(pjp.getArgs()));
		
		//proceed 실행전
		Object result = null;
		
		try {
			Thread.sleep(1000);
			result = pjp.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		//proceed 실행후
		long end = System.currentTimeMillis();
		log.info("END : " + df.format(end));
		log.info("소요시간 : " + sf.format(end-start));
		return result;
		
	}

	@AfterThrowing(pointcut = "execution(* org.zerock.service.SampleService*.*(..))",
				   throwing = "exception")
	public void logException(Exception exception) {
		log.info("====logException()===");
		log.info("exception : " + exception);
	}
	
	//@Before 다음 찍히는게 @After
	@After( "execution(* org.zerock.service.SampleService*.*(..))")
	public void logBefore() {
		log.info("====logAfter()===");
	}
	
	@Before( "execution(* org.zerock.service.SampleService*.doAdd(String, String)) && args(str1, str2)")
	public void logBeforeWithParam(String str1, String str2) {
		log.info("logBeforeWithParam");
		log.info("str1 : " + str1);
		log.info("str2 : " + str2);
	}
	

}
