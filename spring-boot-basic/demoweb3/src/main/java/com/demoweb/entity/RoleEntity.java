package com.demoweb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleNo;

    @Column
    private String roleName;

    @Column
    private String roleDesc;

    @ManyToMany(mappedBy = "roles")
    private Set<MemberEntity> members;  // 권한 중복이 있으면 안되기 때문에 Set 으로
}
