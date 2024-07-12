package com.coffeeorderproject.dto;

import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.coffeeorderproject.dto.ProductDto;
import com.coffeeorderproject.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {

	//board 게시판 정보
	private int boardNo;
	private String userId;
	private int boardcategoryId;
	private String boardTitle;
	private Date boardDate;
	private Timestamp boardModifyDate;
	private String boardContent;
	private boolean boardDelete;
	private String usernickname;

	// 유저가 선택한 상품 정보
	private int prodNo;
	private String prodName;
	private String prodExplain;
	
	private int userAdmin;

	private ArrayList<BoardCategoryDto> categorys;
	private List<BoardAttachDto> attachments;
	private ArrayList<BoardCommentDto> comments;
	private List<ProductDto> products;

	public static BoardDto of(BoardEntity entity) {
		return BoardDto.builder()
				.boardTitle(entity.getBoardTitle())
				.userId(entity.getUserId())
				.boardContent(entity.getBoardContent())
				.boardDate(entity.getBoardDate())
				.boardModifyDate(entity.getBoardModifyDate())
				.boardDelete(entity.isBoardDelete())
				.build();
	}

}
