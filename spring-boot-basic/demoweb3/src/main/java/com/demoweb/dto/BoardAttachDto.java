package com.demoweb.dto;

import com.demoweb.entity.BoardAttachEntity;
import com.demoweb.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardAttachDto {
	private int attachNo;
	private int boardNo;
	private String userFileName;
	private String savedFileName;
	private int downloadCount;
	
	// boardattach 테이블과 board 테이블 사이의 1 : 1 관계를 구현하는 필드
	private BoardDto board;

	public BoardAttachEntity toEntity() {
		return BoardAttachEntity.builder()
//								.attachNo(attachNo)
//								.boardNo(boardNo)
								.userFileName(userFileName)
								.savedFileName(savedFileName)
								.downloadCount(downloadCount)
								.build();
	}

	public static BoardAttachDto of(BoardAttachEntity entity) {
		return BoardAttachDto.builder()
				.attachNo(entity.getAttachNo())
				.userFileName(entity.getUserFileName())
				.savedFileName(entity.getSavedFileName())
				.downloadCount(entity.getDownloadCount())
				.build();
	}
	
}
