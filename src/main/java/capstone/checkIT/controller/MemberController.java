package capstone.checkIT.controller;

import capstone.checkIT.DTO.MemberDTO.MemberResponseDTO;
import capstone.checkIT.apipayLoad.ApiResponse;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.service.memberService.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final JwtManager jwtManager;

    @GetMapping("/my")
    @Operation(summary="내 정보 조회 API",
                description="내 정보 조회 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<MemberResponseDTO.MypageDTO> getMyPage(HttpServletRequest token) throws Exception{
        String accessToken = jwtManager.getToken(token);

        MemberResponseDTO.MypageDTO myPageDTO = memberService.getMyPage(accessToken);
        return ApiResponse.onSuccess(myPageDTO);
    }

}
