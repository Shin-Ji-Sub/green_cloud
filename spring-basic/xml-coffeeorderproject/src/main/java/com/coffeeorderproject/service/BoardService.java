package com.coffeeorderproject.service;

import java.util.List;

import com.coffeeorderproject.dto.BoardAttachDto;
import com.coffeeorderproject.dto.BoardCommentDto;
import com.coffeeorderproject.dto.BoardDto;
import com.coffeeorderproject.dto.OrdersDto;
import com.coffeeorderproject.dto.ProductDto;

public interface BoardService {

	void writeBoard(BoardDto board);

	List<BoardDto> findReviewBoardByRange(int start, int count);

	int getBoardCount();

	List<ProductDto> findProdNameList();

	BoardDto findBoardByBoardNo(int boardNo);
	
	BoardAttachDto findBoardAttachByAttachNo(int attachNo);
	
	void writeComment(BoardCommentDto comment);

	List<BoardCommentDto> findBoardCommentsByBoardNo(int boardNo);
	
//	void writeBoardContent(BoardDto board);
//
//	List<BoardDto> findReviewBoard();
//
//
//	List<BoardDto> findReviewBoardByRange2(int start, int count, String userId);
//
//	List<BoardDto> findReviewBoardByRange3(int start, int count);
//
//
//	BoardDto findBoardByBoardNo2(int boardNo);
//
//	void modifyBoard(BoardDto board);
//
//	void deleteBoard(int boardNo);
//
//
//	List<BoardDto> findAnnouncementBoard();
//
//	List<BoardDto> findInquiry1on1Board();
//

	
//
//	void deleteComment(int commentNo);
//
//	void editComment(BoardCommentDto comment);
//
	void writeReComment(BoardCommentDto comment);
//
//	List<BoardDto> findSearchReviewBoard(BoardDto board);
//
//	int getBoardCount();
//
//	int getBoardAnnoCount();
//
//	int getBoard1on1Count();
//
//	int getBoardCountProdNoList(int prodno);
//
//	int getBoardCountKeywordList(String keyword);
//
//	List<BoardDto> findBoradByProdNo(int prodNo, int start, int count);
//
//	List<ProductDto> findProdNameList();
//
//	BoardDto findBoardContentByBoardNo(int boardNo);
//
//	BoardDto findBoardByProdName(int prodNo);
//
//	OrdersDto findUserOder(int orderId);
//
//	List<BoardDto> findSearchChearBoard(String keyword, int start, int count);
//
//	BoardDto findUserIdByUseradmin(String userId);
//
//	List<BoardDto> findReviewBoardByAdminByRange(int start, int count);


}