package com.coffeeorderproject.aop;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.coffeeorderproject.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
public class Logger {

	@Before("execution(* com.coffeeorderproject.controller.*.*(..))")
	public void doLog(JoinPoint joinPoint) {
//		System.out.println(joinPoint.getSignature().getName());
		
		// 현재 웹 요청 정보를 가져오기
		ServletRequestAttributes attrs = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		if (attrs != null) {
			HttpServletRequest req = attrs.getRequest();
			UserDto loginUser = (UserDto)req.getSession().getAttribute("loginUser");
			String uri = req.getRequestURI();
			
			String user = loginUser != null ? loginUser.getUserId() : "guest";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			
			System.out.printf("[%s][%s][%s]\n", sdf.format(new Date()), user, uri);
		}
	}
	
}
