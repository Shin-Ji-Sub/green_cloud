package com.demoweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.demoweb.service.AccountService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = { "/account/dup-check"})
public class DupCheckServlet extends HttpServlet {

	private AccountService accountService = new AccountService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String memberId = req.getParameter("id");
		
		boolean dup = accountService.checkDuplication(memberId);
		
		resp.setContentType("plain/text;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(dup);
		
	}
	
}
