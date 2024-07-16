package com.demoweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_member")  // Table이 만들어짐 (name 속성 이름으로)
public class MemberEntity {
    @Id   // Primary Key 라는 뜻
    private String memberId;
    @Column  // 일반 Column 이라는 뜻
    private String passwd;
    @Column
    private String email;

    @Builder.Default   // Builder를 사용해서 객체 만들 때 기본 값 사용하는 설정
    @Column
    private String userType = "user";
    @Builder.Default
    @Column
    private Date regDate = new Date();
    @Builder.Default
    @Column
    private boolean active = true;

    @ManyToMany
    // 무게 중심이 있는 쪽으로 테이블을 생성
    @JoinTable(
            name = "tbl_member_role",
            joinColumns = @JoinColumn(name = "memberId"),
            inverseJoinColumns = @JoinColumn(name = "roleNo")
    )
    private Set<RoleEntity> roles;

}
