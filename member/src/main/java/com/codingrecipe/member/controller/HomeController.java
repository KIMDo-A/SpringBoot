package com.codingrecipe.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    //기본 페이지
    @GetMapping("/")
    public String index(){
        //기본주소로 요청이 오면 이 메서드 실행
        return "index"; // -> templates 폴더의 index.html을 찾아감 -> viewResolver
    }
}
