package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequiredArgsConstructor //lombok, 생성자 생성
public class MemberController {

    //생성자 주입
    private final MemberService memberService;

    //회원가입 페이지
    @GetMapping("/member/save")
    // @GetMapping : 링크 클릭
    public String saveForm(){
        return "save";
    }

    //회원가입 기능
    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        //@ModelAttribute : 폼데이터나 요청데이터를 객체로 바인딩할 때 사용
        System.out.println("memberDTO = " + memberDTO);
        //@RequestParam : input태그의 name="memberEmail"을 가져와서 -> memberEmail
//            @RequestParam("memberEmail") String memberEmail,
//            @RequestParam("memberPassword") String memberPassword,
//            @RequestParam("memberName") String memberName
        //System.out.println("memberEmail = " + memberEmail + ", memberPassword = " + memberPassword + ", memberName = " + memberName);
        //System.out.println("MemberController.save");
        memberService.save(memberDTO);
        return "login";
    }

    //로그인 페이지
    @GetMapping("/member/login")
    public String loginForm(){
        return "login";
    }

    //로그인 기능
    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO loginResult = memberService.login(memberDTO);
        System.out.println("loginResult : " + loginResult); //MemberDTO(id=5, memberEmail=teddy@love.com, memberPassword=null, memberName=정테디)

        if (loginResult != null) {
            // login 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        }else {
            // login 실패
            return "login";
        }
    }

    //전체 회원목록 출력
    @GetMapping("/member/")
    public String findAll(Model model){
        //여러 회원 -> ArrayList(DTO가 여러개)
        List<MemberDTO> memberDTOList= memberService.findAll();
        // Model : html로 가져갈 데이터 있을때 사용
        model.addAttribute("memberList", memberDTOList);
        //session에 저장
        return "list";
    }

    //회원 상세 출력
    @GetMapping("/member/{id}")
    public String findById(@PathVariable Long id, Model model){
        //@PathVariable : 경로에 있는 값
        //한 회원 -> MemberDTO
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "detail";
    }

    //수정 페이지
    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model){
        String myEmail = (String) session.getAttribute("loginEmail");
        //session에서 꺼내오기
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember",memberDTO);
        return "update";
    }

    //수정 기능
    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        //System.out.println(memberDTO);
        memberService.update(memberDTO);
        return "redirect:/member/" + memberDTO.getId();
        //redirect : 해당 메소드 처리가 끝난 후 다른 컨트롤러 메소드 요청
    }

    //삭제 기능
    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable Long id){
        memberService.deleteById(id);
        return "redirect:/member/" ;
    }

    //로그아웃 기능
    @GetMapping("/member/logout")
    public String logout(HttpSession session){
        session.invalidate();
        //세션무효화
        return "index";
    }

    //이메일 중복체크
    @PostMapping("/member/email-check")
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail){
        //@ResponseBody : ajax사용할때 리턴 타입
        System.out.println("memberEmail = " + memberEmail);
        String checkResult = memberService.emailCheck(memberEmail);
        return checkResult;
//        if (checkResult != null){
//            return "ok";
//        } else {
//            return "no";
//        }
    }
}

