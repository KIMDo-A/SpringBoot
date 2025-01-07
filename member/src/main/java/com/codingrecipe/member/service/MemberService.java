package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//spring이 관리해주는 객체= > springbin
@Service
@RequiredArgsConstructor 
public class MemberService {
    
    //생성자 주입
    private final MemberRepository memberRepository;

    //회원가입 기능
    public void save(MemberDTO memberDTO) {

        // 1. DTO -> entity변환 : entity에 메소드를 만듬
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);

        //2. -> repository(JPA)의 save메서드 호출 (조건) entity객체를 넘겨줘야 함)
        memberRepository.save(memberEntity); 
        //save() : JPA가 제공해주는 메서드 -> Insert쿼리
    }

    //로그인 기능
    public MemberDTO login(MemberDTO memberDTO) {
        /*
           1. 회원이 입력한 이메일로 DB에서 조회를 함
           2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
        */

        //1. 이메일 DB조회
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        System.out.println("byMemberEmail : " + byMemberEmail); //byMemberEmail : Optional[com.codingrecipe.member.entity.MemberEntity@34e87421]

        if(byMemberEmail.isPresent()){
            //조회 결과가 있다(=해당 이메일을 가진 회원 정보가 있다)
            MemberEntity memberEntity = byMemberEmail.get();
            //.get() : Optional에서 실제 값 추출
            System.out.println("memberEntity : " + memberEntity); //memberEntity : com.codingrecipe.member.entity.MemberEntity@34e87421

            //2. 비밀번호 일치 확인 - .equals()
            if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
                //entity -> DTO 변환 후 리턴 : DTO에 메소드를 만듬
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            }else {
                //비밀번호 불일치(로그인 실패)
                return null;
            }
        }else {
            //조회 결과가 없다(=해당 이메일을 가진 회원이 없다)
            return null;
        }
    }

    //전체 회원 목록 출력
    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();

        //DTOList만들기 : EntityList에서 하나하나 꺼내서 -> DTOList(리스트로 보냄)
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity: memberEntityList){
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
        }
        return memberDTOList;
    }

    //상세 회원 출력
    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
//            MemberEntity memberEntity = optionalMemberEntity.get();
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            return memberDTO;
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    //회원 수정 전 회원 정보 출력
    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if (optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        }else{
            return null;
        }
    }

    //회원 수정
    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toupdateMemberEntity(memberDTO));
        //save(id가 있을 경우 update쿼리 수행)
    }

    //회원 삭제
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    //이메일 중복 체크
    public String emailCheck(String memberEmail) {
        Optional<MemberEntity> byMemberEmail  = memberRepository.findByMemberEmail(memberEmail);
        if(byMemberEmail.isPresent()){
            //조회결과가 있다 -> 사용할 수 없다!
            return null;
        }else{
            //조회결과가 없다 -> 사용할 수 있다!
            return "ok";
        }
    }
}
