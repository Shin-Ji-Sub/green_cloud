package com.demoweb.service;

import com.demoweb.entity.MemberEntity;
import com.demoweb.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.demoweb.common.Util;
import com.demoweb.dto.MemberDto;
import com.demoweb.mapper.MemberMapper;

import lombok.Setter;

import java.util.Optional;

public class AccountServiceImpl implements AccountService {
	
	// 필드 선언
	// 빈 설정 파일에서 의존성 주입하는 통로
//	@Setter
//	private MemberMapper memberMapper;

	@Setter
	private MemberRepository memberRepository;
	
	// 회원 가입 처리
	@Override
	public void registerMember(MemberDto member) {
		
		// 업무 규칙(요구사항) 처리
		String hashedPasswd = Util.getHashedString(member.getPasswd(), "SHA-256");
		member.setPasswd(hashedPasswd);
		
		// 데이터베이스에 데이터 저장 ( Dao 호출 )
		// memberMapper.insertMember(member);

		MemberEntity memberEntity = member.toEntity();
		memberRepository.save(memberEntity);  // Entity 저장 -> insert or update
		
	}
	
	//public MemberDto findMemberByMemeberIdAndPasswd(String memberId, String passwd) {
	@Override
	public MemberDto findMemberByMemeberIdAndPasswd(MemberDto member) {
		
		// 업무 규칙(요구사항) 처리
		String hashedPasswd = Util.getHashedString(member.getPasswd(), "SHA-256");
//		member.setPasswd(hashedPasswd);
		
		// 데이터베이스에서 데이터 조회
		// MemberDto foundMember = memberDao.selectMemberByMemberIdAndPasswd(memberId, passwd);
//		MemberDto foundMember = memberMapper.selectMemberByMemberIdAndPasswd(member);

		MemberEntity foundMemberEntity = memberRepository.findMemberByMemberIdAndPasswd(member.getMemberId(), hashedPasswd);
		MemberDto foundMember = MemberDto.of(foundMemberEntity);

		// 호출한 곳으로 조회 결과 반환
		return foundMember;
	}
	
	@Override
	public void resetPasswd(MemberDto member) {
		
		// 업무 규칙(요구사항) 처리
		String hashedPasswd = Util.getHashedString(member.getPasswd(), "SHA-256");
//		member.setPasswd(hashedPasswd);

		Optional<MemberEntity> tempEntity = memberRepository.findById(member.getMemberId());
		if (!tempEntity.isEmpty()) {
			MemberEntity memberEntity = tempEntity.get();
			memberEntity.setPasswd(hashedPasswd);
			memberRepository.save(memberEntity);
		}

//		memberMapper.updatePasswd(member);
		
	}

	@Override
	public boolean checkDuplication(String memberId) {
		return memberRepository.findById(memberId).isEmpty();
//		int count = memberMapper.selectMemberCountByMemberId(memberId);
//		return count == 0;
	}

}










