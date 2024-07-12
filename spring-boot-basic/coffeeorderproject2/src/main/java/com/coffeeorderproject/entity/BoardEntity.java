package com.coffeeorderproject.entity;

import com.coffeeorderproject.dto.BoardAttachDto;
import com.coffeeorderproject.dto.BoardCategoryDto;
import com.coffeeorderproject.dto.BoardCommentDto;
import com.coffeeorderproject.dto.ProductDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "board")
public class BoardEntity {

    //board 게시판 정보
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardNo;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private int boardcategoryId;
    @Column(nullable = false)
    private String boardTitle;

    @Builder.Default
    @Column
    private Date boardDate = new Date();
    @Builder.Default
    @Column
    private Timestamp boardModifyDate = new Timestamp(System.currentTimeMillis());

    @Column(nullable = false)
    private String boardContent;

    @Builder.Default
    @Column
    private boolean boardDelete = false;
//    private String usernickname;

    // 유저가 선택한 상품 정보
//    @Column(nullable = false)
//    private int prodNo;
//    @Column(nullable = false)
//    private String prodName;
//    @Column(nullable = false)
//    private String prodExplain;
//
//    @Column
//    private int userAdmin;

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "boardcategoryId")
//    private ArrayList<BoardCategoryDto> categorys;
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "boardNo")
//    private List<BoardAttachDto> attachments;
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "boardNo")
//    private ArrayList<BoardCommentDto> comments;
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "prodNo")
//    private List<ProductDto> products;

}
