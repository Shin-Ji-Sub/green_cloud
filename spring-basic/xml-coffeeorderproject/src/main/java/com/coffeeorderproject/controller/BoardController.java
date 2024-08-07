package com.coffeeorderproject.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

import com.coffeeorderproject.common.Util;
import com.coffeeorderproject.dto.BoardAttachDto;
import com.coffeeorderproject.dto.BoardCommentDto;
import com.coffeeorderproject.dto.BoardDto;
import com.coffeeorderproject.dto.ProductDto;
import com.coffeeorderproject.dto.UserDto;
import com.coffeeorderproject.service.BoardService;
import com.coffeeorderproject.ui.ThePager;
import com.coffeeorderproject.view.DownloadView2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;

@Controller
@RequestMapping(path = { "/board" })
public class BoardController {
	
	@Setter(onMethod_ = { @Autowired })
	private BoardService boardService;

	@GetMapping(path = { "/review" })
	public String review(@RequestParam(name="pageNo", defaultValue = "1") int pageNo, HttpServletRequest req, Model model) {
		
		int pageSize = 5;
		int pagerSize = 3;
		int dataCount = boardService.getBoardCount();
		
		String uri = req.getRequestURI();
		String linkUrl = uri.substring(uri.lastIndexOf("/") + 1);
		String queryString = req.getQueryString();
		
		 int start = pageSize * (pageNo - 1) + 1;
		
		ThePager pager = new ThePager(dataCount, pageNo, pageSize, pagerSize, linkUrl, queryString);
		
		List<BoardDto> boardList = boardService.findReviewBoardByRange(start, pageSize);
		List<ProductDto> prodList = boardService.findProdNameList();
		
		model.addAttribute("prodList", prodList);
		model.addAttribute("boardList", boardList);
		model.addAttribute("pager", pager);
		model.addAttribute("pageNo", pageNo);

		
		return "board/review";
	}
	
	@GetMapping(path = { "/detail" })
	public String reviewDetail(@RequestParam(name="boardno") int boardNo, @RequestParam(name="pageNo") int pageNo, Model model) {
		
		BoardDto board = boardService.findBoardByBoardNo(boardNo);
		
		// 2. JSP에서 읽을 수 있도록 데이터 전달 ( request 객체에 저장 )
		model.addAttribute("board", board);
	
		
		return "board/review-detail";
	}
	
	@GetMapping(path = { "/reviewboardwrite" })
	public String reviewWriteForm() {
		
		return "board/reviewboardwrite";
	}
	
	@PostMapping(path = { "/reviewboardwrite" })
	public String reviewWrite(@ModelAttribute("board") BoardDto board, MultipartFile attach, HttpServletRequest req, HttpSession session) {
		
		BoardAttachDto attachment = new BoardAttachDto();
		ArrayList<BoardAttachDto> attachments = new ArrayList<>();
		
		try {
			
			String dir = req.getServletContext().getRealPath("/board-attachments");
			String userFileName = attach.getOriginalFilename();
			String savedFileName = Util.makeUniqueFileName(userFileName);
			attach.transferTo(new File(dir, savedFileName));
			
			attachment.setUserfilename(userFileName);
			attachment.setSavedfilename(savedFileName);
			attachments.add(attachment);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
//		UserDto user = (UserDto)session.getAttribute("loginUser");
//		board.setUserId(user.getUserId());
		board.setAttachments(attachments);
		boardService.writeBoard(board);
		
		return "redirect:/board/review";
	}
	
	@GetMapping(path = { "/download" })
	public View download(@RequestParam("attachno") int attachNo, Model model) {
		
		BoardAttachDto attach = boardService.findBoardAttachByAttachNo(attachNo);
		
		model.addAttribute("attach", attach);
		
		return new DownloadView2();
		
	}
	
	
	@GetMapping(path = { "/list-comment" })
	public String listComment(int boardNo, Model model) {
		
		List<BoardCommentDto> comments = boardService.findBoardCommentsByBoardNo(boardNo);
		model.addAttribute("comments", comments);
		
		return "board/comment-list";
	}
	
}
