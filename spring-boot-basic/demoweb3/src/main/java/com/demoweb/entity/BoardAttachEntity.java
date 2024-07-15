package com.demoweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_boardattach")
public class BoardAttachEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attachNo;
//    @Column(nullable = false)
//    private int boardNo;
    @Column(nullable = false)
    private String userFileName;
    @Column(nullable = false)
    private String savedFileName;

    @Builder.Default
    @Column
    private int downloadCount = 0;

    @ManyToOne
    @JoinColumn(name = "boardNo")
    private BoardEntity board;

}
