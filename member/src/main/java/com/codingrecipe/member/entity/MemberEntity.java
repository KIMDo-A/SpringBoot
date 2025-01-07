package com.codingrecipe.member.entity;

import com.codingrecipe.member.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//Entity : springDagaJPA를 사용할 때, 테이블 역할
//데이터베이스의 테이블을 자바 객체처럼 사용
@Entity
@Setter
@Getter
@Table(name = "member_table") //DB테이블생성이름
public class MemberEntity {
    //컬럼 생성
    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //= auto_increment
    private Long id;

    @Column(unique = true) //unique 제약조건 추가
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberName;

    //DTO -> Entity객체로 변환
    public static MemberEntity toMemberEntity(MemberDTO memberDTO){
        //service -> repository -> entity DTO객체 받음

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        //Entity객체.setter메소드(DTO.get메소드)
        //getter : 읽음, setter : 변경
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        return memberEntity;
    }

    public static MemberEntity toupdateMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        //ID 추가하기(update사용 위해)
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        return memberEntity;
    }
}
