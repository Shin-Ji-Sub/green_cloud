package com.demoweb.dto;

public class BoardAttachDto {
	private int attachNo;
	private int boardNo;
	private String userFileName;
	private String savedFileName;
	private int downloadCount;
	
	// boardattach 테이블과 board 테이블 사이의 1 : 1 관계를 구현하는 필드
	private BoardDto board;
	
	public int getAttachNo() {
		return attachNo;
	}
	public void setAttachNo(int attachNo) {
		this.attachNo = attachNo;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getUserFileName() {
		return userFileName;
	}
	public void setUserFileName(String userFileName) {
		this.userFileName = userFileName;
	}
	public String getSavedFileName() {
		return savedFileName;
	}
	public void setSavedFileName(String savedFileName) {
		this.savedFileName = savedFileName;
	}
	public int getDownloadCount() {
		return downloadCount;
	}
	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}
	public BoardDto getBoard() {
		return board;
	}
	public void setBoard(BoardDto board) {
		this.board = board;
	}
	
	
}
