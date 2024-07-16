package com.demoweb.dto;

import java.util.Date;

import com.demoweb.entity.MemberEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {

	@NotBlank(message = "아이디를 입력하세요")
	@Pattern(regexp = "^[A-Za-z0-9]{8,20}$", message = "아이디는 8 ~ 20개의 영문자 숫자 조합으로 작성해주세요")
	private String memberId;
	@NotBlank(message = "패스워드를 입력하세요")
	@Pattern(regexp = "^[A-Za-z0-9]{8,20}$", message = "패스워드는 8 ~ 20개의 영문자 숫자 조합으로 작성해주세요")
	private String passwd;
	@NotBlank(message = "이메일을 입력하세요")
	@Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "이메일은 형식이 잘못되었습니다")
	private String email;

	@Builder.Default
	private String userType = "ROLE_USER";
	private Date regDate;
	private boolean active;

	public MemberEntity toEntity() {
		MemberEntity memberEntity = MemberEntity.builder()
									.memberId(memberId)
									.passwd(passwd)
									.email(email)
									.userType(userType)
									.build();
		return memberEntity;
	}

	public static MemberDto of(MemberEntity memberEntity) {
		return MemberDto.builder()
							.memberId(memberEntity.getMemberId())
							.passwd(memberEntity.getPasswd())
							.email(memberEntity.getEmail())
							.userType(memberEntity.getUserType())
							.regDate(memberEntity.getRegDate())
							.build();
	}

}