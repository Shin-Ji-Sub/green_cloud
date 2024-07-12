package com.demoweb.entity;

import com.demoweb.dto.BoardAttachDto;
import com.demoweb.dto.BoardCommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_board")
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.TABLE)
//    @TableGenerator(name = "board_seq", allocationSize = 1)  // default 는 50
    private int boardNo;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, length = 1000)
    private String content;
    @Column(nullable = false)
    private String writer;

    @Builder.Default
    @Column
    private Date writeDate = new Date();
    @Builder.Default
    @Column
    private Date modifyDate = new Date();
    @Builder.Default
    @Column
    private int readCount = 0;
    @Builder.Default
    @Column
    private boolean deleted = false;

    // board 테이블과 boardattach 테이블 사이의 1 : Many 관계를 구현하는 필드
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)  // 지워지면 자식 테이블은 어떻게 할건지 설정
    @JoinColumn(name = "boardNo")
    private List<BoardAttachEntity> attachments;

    // board 테이블과 boardcomment 테이블 사이의 1 : Many 관계를 구현하는 필드
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardNo")
    private List<BoardCommentEntity> comments;

}
