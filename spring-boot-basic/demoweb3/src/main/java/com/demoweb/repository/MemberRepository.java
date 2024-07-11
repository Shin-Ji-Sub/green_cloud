package com.demoweb.repository;

import com.demoweb.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, String> { // String 자리 parameter는 PK의 자료형을 주면 됨.

    MemberEntity findMemberByMemberIdAndPasswd(String memberId, String passwd);

}
