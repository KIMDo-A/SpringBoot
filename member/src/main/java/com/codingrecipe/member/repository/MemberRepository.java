package com.codingrecipe.member.repository;

import com.codingrecipe.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>{
    //<JpaRepository 규칙>
    //첫번째 인자 : entity클래스 이름, 두번째 인자 : pk의 데이터타입 -> @Id컬럼의 타입
    //데이터베이스와 작업을 해주는 곳 -> 반드시 ★Entity객체★로 넘겨줘야 함!

    //특정 쿼리 추가 정의하기(인터페이스 -> 추상메소드)
    //이메일로 회원 정보 조회(select * from member_table where member_email = ?)
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
    //Optinal : null제외

}
