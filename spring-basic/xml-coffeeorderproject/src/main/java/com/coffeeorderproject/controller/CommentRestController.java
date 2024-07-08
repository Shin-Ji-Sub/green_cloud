package com.coffeeorderproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.coffeeorderproject.dto.BoardCommentDto;
import com.coffeeorderproject.service.BoardService;

import lombok.Setter;

@RestController
@RequestMapping(path = { "/board" })
public class CommentRestController {

	@Setter(onMethod_ = { @Autowired })
	private BoardService boardService;
	
	@PostMapping(path = { "/write-comment" }, produces = "text/plain;charset=utf-8")
	// @ResponseBody
	public String writeComment(BoardCommentDto comment) {
		System.out.println(comment.getCommentContent());
		String result = "success";
		
		try {
			boardService.writeComment(comment);
		} catch (Exception ex) {
			result = "fail";
		}
		
		return result;
	}
	
	@PostMapping(path = { "/write-recomment" })
	public String writeRecomment(BoardCommentDto comment) {
		
		boardService.writeReComment(comment);
		
		return "success";
	}
	
}
