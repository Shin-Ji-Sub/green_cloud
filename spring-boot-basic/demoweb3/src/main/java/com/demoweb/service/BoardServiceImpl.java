package com.demoweb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.demoweb.entity.BoardAttachEntity;
import com.demoweb.entity.BoardEntity;
import com.demoweb.repository.BoardAttachRepository;
import com.demoweb.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.demoweb.dto.BoardAttachDto;
import com.demoweb.dto.BoardCommentDto;
import com.demoweb.dto.BoardDto;
import com.demoweb.mapper.BoardMapper;

import lombok.Setter;

public class BoardServiceImpl implements BoardService {

	@Setter
	private BoardMapper boardMapper;

	@Setter
	private BoardRepository boardRepository;

	@Setter
	private BoardAttachRepository boardAttachRepository;
	
	@Setter
	private TransactionTemplate transactionTemplate;
	

	@Override
//	@Transactional(rollbackFor = Exception.class,
//				   propagation = Propagation.REQUIRED, // propagation = Propagation.REQUIRED : default 값
//				   isolation = Isolation.READ_COMMITTED) // isolation = Isolation.READ_COMMITTED : READ_COMMITTED 가 default 값
	public void writeBoard(BoardDto board) {
		
//		boardMapper.insertBoard2(board);

		BoardEntity boardEntity = board.toEntity();
//		boardRepository.save(boardEntity);

		// int x = 10 / 0;  // 트랜젝션 테스트를 위해 강제 예외 발생

		List<BoardAttachEntity> attachments = new ArrayList<>();
		for (BoardAttachDto attach : board.getAttachments()) {
//			attach.setBoardNo(boardEntity.getBoardNo());
			attachments.add(attach.toEntity());
//			boardAttachRepository.save(attach.toEntity());
		}
		boardEntity.setAttachments(attachments);
		boardRepository.save(boardEntity);

	}
	
	@Override
	public List<BoardDto> findAllBaord() {
		
		List<BoardDto> boards = boardMapper.selectAllBoard();
		return boards;
		
	}
	
	@Override
	public int getBoardCount() {
		return (int)boardRepository.count();
	}
	@Override
	public ArrayList<BoardDto> findBaordByRange(int start, int count) {

		ArrayList<BoardDto> boards = boardMapper.selectBoardByRange(start, start + count);
		return boards;
		
	}
	@Override
	public List<BoardDto> findBaordByRange2(int pageNo, int count) {

		Pageable pageable = PageRequest.of(pageNo, count, Sort.by(Sort.Direction.DESC, "boardNo"));
		Page<BoardEntity> page = boardRepository.findAll(pageable);
		List<BoardDto> boards = new ArrayList<>();
		for (BoardEntity boardEntity : page.getContent()) {
			boards.add(BoardDto.of(boardEntity));
		}
		return boards;

	}
	
	@Override
	public BoardDto findBoardByBoardNo(int boardNo) {
		
		// 게시글 조회
		BoardDto board = boardMapper.selectBoardByBoardNo(boardNo);

		// 첨부파일 조회
		List<BoardAttachDto> attaches = boardMapper.selectBoardAttachByBoardNo(boardNo);
		board.setAttachments(attaches);
		
//		// 댓글 조회
//		List<BoardCommentDto> comments = boardMapper.selectBoardCommentByBoardNo(boardNo);
//		board.setComments(comments);
		
		return board;
		
	}
	@Override
	public BoardDto findBoardByBoardNo2(int boardNo) {
		
		// 게시글 조회
//		BoardDto board = boardMapper.selectBoardByBoardNo2(boardNo);
//		BoardDto board = boardMapper.selectBoardByBoardNo3(boardNo);

		Optional<BoardEntity> entity = boardRepository.findById(boardNo);
		if (entity.isPresent()) {
			BoardEntity boardEntity = entity.get();
			BoardDto board = BoardDto.of(boardEntity);
//			List<BoardAttachDto> attachments = new ArrayList<>();
//			for (BoardAttachEntity entity2 : boardEntity.getAttachments()) {
//				attachments.add(BoardAttachDto.of(entity2));
//			}

//			List<BoardAttachDto> attachments = boardEntity.getAttachments().stream().map(attach -> BoardAttachDto.of(attach)).toList();
			List<BoardAttachDto> attachments = boardEntity.getAttachments().stream().map(BoardAttachDto::of).toList();
			board.setAttachments(attachments);
			return board;
		} else {
			return null;
		}

//		return entity.isPresent() ? BoardDto.of(entity.get()) : null;
	}
	

	@Override
	public BoardAttachDto findBoardAttachByAttachNo(int attachNo) {
//		Optional<BoardAttachEntity> entity = boardAttachRepository.findById(attachNo);
//		return entity.isPresent() ? BoardAttachDto.of(entity) : null;
		BoardAttachEntity entity = boardRepository.findBoardAttachByAttachNo(attachNo);
		return BoardAttachDto.of(entity);
	}

	@Override
	public void deleteBoard(int boardNo) {
//		boardMapper.updateBoardDeleted(boardNo);

		BoardEntity entity = boardRepository.findById(boardNo).get();
		// DB에서 데이터 삭제
//		boardRepository.delete(entity);

		// DB에서 데이터 업데이트
		entity.setDeleted(true);
		boardRepository.save(entity);

	}

	@Override
	public void deleteBoardAttach(int attachNo) {
		// 1
//		boardAttachRepository.deleteById(attachNo);

		// 2
//		BoardAttachEntity entity = boardRepository.findBoardAttachByAttachNo(attachNo);
//		boardAttachRepository.delete(entity);

		// 3
		boardRepository.deleteBoardAttachByAttachNo(attachNo);

	}

	@Override
	public void modifyBoard(BoardDto board) {

		BoardEntity entity = boardRepository.findById(board.getBoardNo()).get();
		entity.setTitle(board.getTitle());
		entity.setContent(board.getContent());
//		boardRepository.save(entity);

		if (board.getAttachments() != null) {
			for (BoardAttachDto attach : board.getAttachments()) {
//				List<BoardAttachEntity> attaches = board.getAttachments().stream().map(a -> a.toEntity()).toList();
				boardRepository.insertBoardAttach(attach.getBoardNo(), attach.getUserFileName(), attach.getSavedFileName());
			}
//			entity.setAttachments(attaches);
		}
//		boardRepository.save(entity);
	}

	@Override
	public void writeComment(BoardCommentDto comment) {
		
		boardMapper.insertComment(comment);
		
	}

	@Override
	public List<BoardCommentDto> findBoardCommentsByBoardNo(int boardNo) {
		
		List<BoardCommentDto> comments = boardMapper.selectBoardCommentsByBoardNo(boardNo);
		
		return comments;
	}

	@Override
	public void deleteComment(int commentNo) {
		
		boardMapper.updateCommentDeleted(commentNo); 
		
	}

	@Override
	public void editComment(BoardCommentDto comment) {
		
		boardMapper.updateComment(comment);
		
	}

	@Override
	public void writeReComment(BoardCommentDto comment) {
		
		// 부모 댓글을 조회해서 자식 댓글(대댓글)의 step, depth를 설정
		BoardCommentDto parent = boardMapper.selectBoardCommentByCommentNo(comment.getCommentNo());
		comment.setGroupNo(parent.getGroupNo());
		comment.setStep(parent.getStep() + 1);
		comment.setDepth(parent.getDepth() + 1);
		
		// 새로 삽입될 대댓글보다 순서번호(step)가 뒤에 있는 대댓글의 step 수정 (+1)
		boardMapper.updateStep(parent);
		
		boardMapper.insertReComment(comment);
		
	}

	
	
}















