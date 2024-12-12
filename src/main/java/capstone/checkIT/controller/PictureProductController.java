package capstone.checkIT.controller;


import java.io.IOException;
import java.util.List;

import capstone.checkIT.DTO.LocationPictureDTO;
import capstone.checkIT.DTO.TodoDTO.TodoResponseDTO;
import capstone.checkIT.apipayLoad.ApiResponse;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.service.pictureProductService.PictureProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PictureProductController {
    private final PictureProductService pictureProductService;
    private final JwtManager jwtManager;


    @PostMapping(value="/tomorrowImage")
    @Operation(summary = "내일 사진분석 API",
            description = "내일 사진분석 API", security = {@SecurityRequirement(name = "session-token")})
    public ApiResponse<TodoResponseDTO.TomorrowResponse> tomorrowImageAnalysis(
            HttpServletRequest token,
            @RequestParam("url") String url) throws IOException {
        String accessToken = jwtManager.getToken(token);
        String fixedRequestText = "이 사진에 있는 물건들이 뭔지 설명없이 키워드만 ,로 구분해서 띄어쓰기 없이 말해줘";
        TodoResponseDTO.TomorrowResponse tomorrowResponse = pictureProductService.requestImageAnalysis(accessToken, url, fixedRequestText);

        return ApiResponse.onSuccess(tomorrowResponse);
    }

    @PostMapping(value="/takeImage/{todoId}")
    @Operation(summary="챙기기 사진분석 API",
            description="챙기기 사진분석 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<TodoResponseDTO.TodayResponse> takeImageAnalysis(
            HttpServletRequest token,
            @RequestParam("url") String url,
            @PathVariable("todoId") Long todoId)
            throws IOException {
        String accessToken = jwtManager.getToken(token);
        String fixedRequestText = "이 사진에 있는 물건들이 뭔지 설명없이 키워드만 ,로 구분해서 띄어쓰기 없이 말해줘";
        TodoResponseDTO.TodayResponse todayResponse = pictureProductService.requestTakeImageAnalysis(accessToken, url, fixedRequestText, todoId);
        return ApiResponse.onSuccess(todayResponse);
    }


    @PostMapping(value="/locationImage/{todoId}")
    @Operation(summary="장소 사진분석 API",
            description="장소 사진분석 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<LocationPictureDTO.LocationProductResponseDTO> locationImageAnalysis(
            HttpServletRequest token,
            @RequestParam("url") String url,
            @PathVariable("todoId") Long todoId)
            throws IOException {
        String accessToken = jwtManager.getToken(token);
        String fixedRequestText = "이 사진에 있는 물건들이 뭔지 설명없이 키워드만 ,로 구분해서 띄어쓰기 없이 말해줘";
        LocationPictureDTO.LocationProductResponseDTO locationResponse = pictureProductService.locationImageAnalysis(accessToken, url, fixedRequestText, todoId);
        return ApiResponse.onSuccess(locationResponse);
    }

//text로만 요청
//    @PostMapping("/text")
//    public String textAnalysis(@RequestParam String requestText) {
//        ChatGPTResponse response = pictureProductService.requestTextAnalysis(requestText);
//        return response.getChoices().get(0).getMessage().getContent();
//    }
}