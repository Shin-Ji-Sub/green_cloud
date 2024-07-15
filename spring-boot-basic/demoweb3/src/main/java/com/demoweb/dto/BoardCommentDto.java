package com.demoweb.dto;

import java.util.Date;

import com.demoweb.entity.BoardCommentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardCommentDto {
	
	private int commentNo;
	private int boardNo;
	private String content;
	private String writer;
	private Date writeDate;
	private Date modifyDate;
	private boolean deleted;
	
	private int groupNo;
	private int step;
	private int depth;

	public BoardCommentEntity toEntity() {
		return BoardCommentEntity.builder()
				.writer(writer)
				.content(content)
				.build();
	}

	public static BoardCommentDto of(BoardCommentEntity entity) {
		return BoardCommentDto.builder()
				.commentNo(entity.getCommentNo())
				.content(entity.getContent())
				.writer(entity.getWriter())
				.writeDate(entity.getWriteDate())
				.modifyDate(entity.getModifyDate())
				.build();
	}

}