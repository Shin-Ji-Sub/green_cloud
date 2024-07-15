package com.demoweb.repository;

import com.demoweb.entity.BoardAttachEntity;
import com.demoweb.entity.BoardCommentEntity;
import com.demoweb.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

//    @Query(value = "SELECT attachNo, userFilename, savedFilename, downloadCount " +
//                    "FROM tbl_boardattach " +
//                    "WHERE attachno = :attachNo ",
//                    nativeQuery = true)  // 실제 데이터베이스의 구문 규칙에 따라 작성한 SQL
    @Query(value = "SELECT ba FROM BoardAttachEntity ba WHERE ba.attachNo = :attachNo")
    BoardAttachEntity findBoardAttachByAttachNo(@Param("attachNo") int attachNo);

    @Query(value = "SELECT bc FROM BoardCommentEntity bc WHERE bc.commentNo = :commentNo")
    BoardCommentEntity findBoardACommentByAttachNo(@Param("commentNo") int commentNo);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM BoardAttachEntity ba WHERE ba.attachNo = :attachNo")
    void deleteBoardAttachByAttachNo(@Param("attachNo") int attachNo);

//    @Modifying
//    @Transactional
//    @Query(value = "INSERT INTO BoardAttachEntity (boardNo, userFileName, savedFileName) " +
//            "VALUES (?, ?, ?, 0", nativeQuery = true)
//    void insertBoardAttach(int boardNo, String userFileName, String savedFileName);

}
