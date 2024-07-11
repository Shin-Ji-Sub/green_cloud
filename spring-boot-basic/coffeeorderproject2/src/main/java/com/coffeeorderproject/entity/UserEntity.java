package com.coffeeorderproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    private String userId;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String userNickname;
    @Column(nullable = false)
    private String userPhone;
    @Column(nullable = false)
    private String userEmail;
    @Column(nullable = false)
    private String userPw;

    @Builder.Default
    @Column
    private Boolean userAdmin = false;
    @Builder.Default
    @Column
    private Date userRegidate = new Date();
    @Builder.Default
    @Column
    private Boolean userActive = false;


}
