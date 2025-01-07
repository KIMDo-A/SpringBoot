package com.codingrecipe.member.dto;

import com.codingrecipe.member.entity.MemberEntity;
import lombok.*;

//lombok 라이브러리 : 필드 각각의 getter/setter 메서드를 자동으로 만들어줌
@Getter
@Setter
@NoArgsConstructor //기본생성자
@AllArgsConstructor //모든필드를 매개변수로 하는 생성자
@ToString //DTO필드객체 필드값 출력

//DTO = "회원정보"의 필요한 내용을 필드로 정리
//필드를 private으로 감추고 -> getter/setter로 간접적으로 사용
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    //entity -> dto
    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberName(memberEntity.getMemberName());
        return memberDTO;
    }
}
