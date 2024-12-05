package capstone.checkIT.controller;

import capstone.checkIT.service.memberService.MemberCommandService;
import capstone.checkIT.DTO.MemberRequestDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberViewController {

}
/*
* private final MemberCommandService memberCommandService;

    public MemberViewController(MemberCommandService memberCommandService) {
        this.memberCommandService = memberCommandService;
    }

    // 회원가입 페이지 렌더링 (GET 요청)
    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("memberJoinDto", new MemberRequestDTO.JoinDto());
        return "signup";
    }

    // 회원가입 데이터 처리 (POST 요청)
    @PostMapping("/signup")
    public String joinMember(@ModelAttribute("memberJoinDto") MemberRequestDTO.JoinDto request,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            return "signup"; // 데이터 바인딩 실패 시 다시 폼 페이지로 이동
        }

        try {
            memberCommandService.joinMember(request);
            return "redirect:/login"; // 성공적으로 회원가입 후 로그인 페이지로 이동
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage()); // 에러 메시지 전달
            return "signup"; // 실패 시 다시 회원가입 폼으로 이동
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
* */
