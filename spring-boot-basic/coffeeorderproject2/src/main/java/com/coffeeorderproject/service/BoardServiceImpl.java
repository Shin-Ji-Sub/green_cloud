package com.coffeeorderproject.service;

import java.util.ArrayList;
import java.util.List;

import com.coffeeorderproject.dto.OrdersDto;
import com.coffeeorderproject.dto.ProductDto;
import com.coffeeorderproject.entity.BoardEntity;
import com.coffeeorderproject.mapper.BoardMapper;

import com.coffeeorderproject.repository.BoardRepository;
import lombok.Setter;

import com.coffeeorderproject.dto.BoardAttachDto;
import com.coffeeorderproject.dto.BoardCommentDto;
import com.coffeeorderproject.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class BoardServiceImpl implements BoardService {

	@Setter
	BoardMapper boardMapper;

	@Setter
	BoardRepository boardRepository;

	@Override
	public void writeBoard(BoardDto board) {

		System.out.println(board.getProdNo());
		
		boardMapper.insertBoard(board); 
		boardMapper.insertReview(board);
		
		for (BoardAttachDto attach : board.getAttachments()) {
			attach.setBoardNo(board.getBoardNo()); // 위 게시글 insert 후 생성된 글번호 저장
			boardMapper.insertBoardAttach(attach); // boardattach 테이블에 데이터 저장
		}

	}
//
//	@Override
//	public void writeBoardContent(BoardDto board) {
//
////		boardDao.insertBoardContnet(board); // board 테이블에 데이터 저장 -> boardNo 결정 (DB에서)
////		// board.getBoardNo() : 새로 만든 글 번호
////
////		for (BoardAttachDto attach : board.getAttachments()) {
////			attach.setBoardNo(board.getBoardNo()); // 위 게시글 insert 후 생성된 글번호 저장
////			boardDao.insertBoardAttach(attach); // boardattach 테이블에 데이터 저장
////		}
//
//	}
//
//	@Override
//	public List<BoardDto> findReviewBoard() {
//
////		List<BoardDto> board = boardMapper.selectReviewBoard();
////		return board;
//
//	}

	@Override
	public List<BoardDto> findReviewBoardByRange(int pageNo, int count) {

//		List<BoardDto> board = boardMapper.selectReviewBoardByRange(start, count);

		Pageable pageable = PageRequest.of(pageNo, count, Sort.by(Sort.Direction.DESC, "boardNo"));
		Page<BoardEntity> page = boardRepository.findAll(pageable);
		List<BoardDto> boards = new ArrayList<>();
		for (BoardEntity boardEntity : page.getContent()) {
			boards.add(BoardDto.of(boardEntity));
		}

		return boards;

	}

	@Override
	public int getBoardCount() {

		int count = boardMapper.selectReviewCount();
		
		return count;
	}

	@Override
	public List<ProductDto> findProdNameList() {
		
		List<ProductDto> Products = boardMapper.selectProdByProdNoByProdName();
		
		return Products;
	}
	
	@Override
	public BoardDto findBoardByBoardNo(int boardNo) {

		// 게시글 조회
		BoardDto board = boardMapper.selectBoardByBoardNo(boardNo);

		// 첨부파일 조회

		List<BoardAttachDto> attaches = boardMapper.selectBoardAttachByBoardNo(boardNo);
		board.setAttachments(attaches);

//		ArrayList<BoardCommentDto> comments = boardDao.selectBoardCommentByBoardNo(boardNo);
//		board.setComments(comments);

		return board;

	}
	
	@Override
	public BoardAttachDto findBoardAttachByAttachNo(int attachNo) {
		BoardAttachDto attach = boardMapper.selectBoardAttachByAttachNo(attachNo);
		return attach;
	}

	@Override
	public void writeComment(BoardCommentDto comment) {

		boardMapper.insertBoardComment(comment);

	}

	@Override
	public List<BoardCommentDto> findBoardCommentsByBoardNo(int boardNo) {
		
		List<BoardCommentDto> comments = boardMapper.selectBoardCommentsByBoardNo(boardNo);
		
		return comments;
	}

//	
//	@Override
//	public BoardDto findBoardByBoardNo2(int boardNo) {
//
//		// 게시글 조회
//		BoardDto board = boardDao.selectBoardByBoardNo2(boardNo);
//
//		// 첨부파일 조회
//
//		ArrayList<BoardAttachDto> attaches = boardDao.selectBoardAttachByBoardNo(boardNo);
//		board.setAttachments(attaches);
//
//		ArrayList<BoardCommentDto> comments = boardDao.selectBoardCommentByBoardNo(boardNo);
//		board.setComments(comments);
//
//		return board;
//
//	}
//
//
//	@Override
//	public void modifyBoard(BoardDto board) {
//
//		boardDao.updateBoard(board);
//
//		for (BoardAttachDto attach : board.getAttachments()) {
//			boardDao.insertBoardAttach(attach);
//		}
//
//	}
//	
//	
//
//	@Override
//	public void deleteBoard(int boardNo) {
//		boardDao.updateBoardDeleted(boardNo);
//
//	}
//
	
//
//	@Override
//	public List<BoardDto> findAnnouncementBoard() {
//		List<BoardDto> board = boardDao.selectAnnouncementBoard();
//		return board;
//
//	}
//
//	@Override
//	public List<BoardDto> findInquiry1on1Board() {
//		List<BoardDto> board = boardDao.selectInquiry1on1Board();
//		return board;
//
//	}
//

//
//	@Override
//	public void deleteComment(int commentNo) {
//
//		boardDao.deleteComment(commentNo);
//
//	}
//
//	@Override
//	public void editComment(BoardCommentDto comment) {
//
//		boardDao.updateComment(comment);
//
//	}
//
	@Override
	public void writeReComment(BoardCommentDto comment) {
		// 부모 댓글을 조회해서 자식 댓글(대댓글)의 step, depth를 설정
		BoardCommentDto parent = boardMapper.selectBoardCommentByCommentNo(comment.getCommentNo());
		comment.setGroupno(parent.getGroupno());
		comment.setReplycount(parent.getReplycount() + 1);
		comment.setReplylocation(parent.getReplylocation() + 1);

		// 새로 삽입될 대댓글보다 순서번호(step)가 뒤에 있는 대댓글의 step 수정 (+1)
		boardMapper.updateStep(parent);

		boardMapper.insertReComment(comment);
	}
//
//	@Override
//	public List<BoardDto> findSearchReviewBoard(BoardDto board) {
//
//		List<BoardDto> searchreviewlist = boardDao.selectSearchBoardList(board);
//
//		return searchreviewlist;
//	}
//
//	@Override
//	public int getBoardCount() {
//		return boardDao.selectBoardCount();
//	}
//	@Override
//	public int getBoardAnnoCount() {
//		return boardDao.selectBoardAnnoCount();
//	}
//	@Override
//	public int getBoard1on1Count() {
//		return boardDao.selectBoard1on1Count();
//	}
//	@Override
//	public int getBoardCountProdNoList(int prodno) {
//		return boardDao.selectBoardCountByProdNo(prodno);
//	}
//	
//	@Override
//	public int getBoardCountKeywordList(String keyword) {
//		
//		return boardDao.selectBoardCountByKeyword(keyword);
//	}
//
//
//	@Override
//	public List<BoardDto> findBoradByProdNo(int prodNo, int start, int count) {
//		return boardDao.selectBoardByProdNo(prodNo, start, count);
//	}
//
//	@Override
//	public List<ProductDto> findProdNameList() {
//
//		List<ProductDto> Product = boardDao.selectProdByProdNoByProdName();
//
//		return Product;
//	}
//
//	@Override
//	public BoardDto findBoardContentByBoardNo(int boardNo) {
//		// 첨부파일 조회
//		BoardDto board = boardDao.selectBoardContentByBoardNo(boardNo);
//
//		ArrayList<BoardAttachDto> attaches = boardDao.selectBoardAttachByBoardNo(boardNo);
//		board.setAttachments(attaches);
//
//		ArrayList<BoardCommentDto> comments = boardDao.selectBoardCommentByBoardNo(boardNo);
//		board.setComments(comments);
//
//		return board;
//	}
//
//	@Override
//	public BoardDto findBoardByProdName(int prodNo) {
//
//
//		return boardDao.selectBoardByProdName(prodNo);
//	}
//
//	@Override
//	public OrdersDto findUserOder(int orderId) {
//		
//		OrdersDto userorder = boardDao.selectUesridByOrders(orderId);
//		
//		return userorder;
//		
//	}
//
//	@Override
//	public List<BoardDto> findSearchChearBoard(String keyword, int start, int count) {
//		return boardDao.selectBoardByKeyword(keyword, start, count);
//	}
//
//	@Override
//	public BoardDto findUserIdByUseradmin(String userId) {
//		BoardDto user =	boardDao.selectUserIdByUseradmin(userId);
//		return user;
//	}
//
//	@Override
//	public List<BoardDto> findReviewBoardByAdminByRange(int start, int count) {
//		
//		List<BoardDto> board = boardDao.selectReviewBoardAdminByRange(start, count );
//		return board;
//
//	}




}








