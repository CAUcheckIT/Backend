package capstone.checkIT.controller;

import capstone.checkIT.DTO.MonthDTO.MonthRequestDTO;
import capstone.checkIT.DTO.MonthDTO.MonthResponseDTO;
import capstone.checkIT.apipayLoad.ApiResponse;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.service.monthService.MonthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/months")
public class MonthController {
    private final MonthService monthService;
    private final JwtManager jwtManager;

    @PostMapping
    @Operation(summary="한달 목표 생성 API",
            description="한달 목표 생성 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<MonthResponseDTO.CreateMonthDto> createMonth(HttpServletRequest token, @RequestBody @Valid MonthRequestDTO.CreateMonthDto request){
        String accessToken = jwtManager.getToken(token);
        MonthResponseDTO.CreateMonthDto response = monthService.createMonth(accessToken, request);
        return ApiResponse.onSuccess(response);

    }

    @PatchMapping("/{monthId}")
    @Operation(summary="한달 목표 수정 API",
            description="한달 목표 수정 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<MonthResponseDTO.CreateMonthDto> updateMonth(HttpServletRequest token, @PathVariable Long monthId, @RequestBody @Valid MonthRequestDTO.CreateMonthDto request){
        String accessToken = jwtManager.getToken(token);
        MonthResponseDTO.CreateMonthDto response= monthService.updateMonth(accessToken, monthId, request);
        return ApiResponse.onSuccess(response);
    }

    @PatchMapping("/achieves/{monthId}")
    @Operation(summary="한달 목표 하루 달성 API",
            description="한달 목표 하루 달성 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<MonthResponseDTO.getMonthDto> achieveDay(HttpServletRequest token, @PathVariable Long monthId, @RequestParam boolean newIsStart){
        String accessToken = jwtManager.getToken(token);
        MonthResponseDTO.getMonthDto response = monthService.achieveDay(accessToken, monthId, newIsStart);
        return ApiResponse.onSuccess(response);
    }

    @DeleteMapping("/{monthId}")
    @Operation(summary="한달 목표 삭제 API",
            description="한달 목표 삭제 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<String> deleteMonth(HttpServletRequest token, @PathVariable Long monthId){
        String accessToken = jwtManager.getToken(token);
        monthService.deleteMonth(accessToken, monthId);
        return ApiResponse.onSuccess("삭제 성공");
    }

    @GetMapping("/{monthId}")
    @Operation(summary="한달 목표 조회 API",
            description="한달 목표 조회 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<MonthResponseDTO.getMonthDto> getMonth(HttpServletRequest token, @PathVariable Long monthId){
        String accessToken = jwtManager.getToken(token);
        MonthResponseDTO.getMonthDto response = monthService.getMonth(accessToken, monthId);
        return ApiResponse.onSuccess(response);
    }
}
