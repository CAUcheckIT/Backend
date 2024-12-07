package capstone.checkIT.controller;

import capstone.checkIT.DTO.MemberDTO.MemberRequestDTO;
import capstone.checkIT.DTO.MemberDTO.MemberResponseDTO;
import capstone.checkIT.apipayLoad.ApiResponse;
import capstone.checkIT.service.memberService.MemberCommandService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberRestController {

    private final MemberCommandService memberCommandService;

    @PostMapping("/signup")
    @Operation(summary="회원가입 API",
                description="회원가입 API")
    public ApiResponse<MemberResponseDTO.JoinResultDTO> join(@RequestBody @Valid MemberRequestDTO.JoinDto request){
        MemberResponseDTO.JoinResultDTO response = memberCommandService.joinMember(request);
        return ApiResponse.onSuccess(response);
    }

    @PostMapping("/login")
    @Operation(summary="로그인 API",
                description = "로그인 API - access token 응답")
    public ApiResponse<MemberResponseDTO.LoginResultDTO> login(@RequestBody @Valid MemberRequestDTO.LoginDto request){
        MemberResponseDTO.LoginResultDTO response= memberCommandService.loginMember(request);
        return ApiResponse.onSuccess(response);
    }




}