package capstone.checkIT.controller;


import java.io.IOException;

import capstone.checkIT.apipayLoad.ApiResponse;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.service.pictureProductService.PictureProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PictureProductController {
    private final PictureProductService pictureProductService;
    private final JwtManager jwtManager;

//    @PostMapping("/tomorrowImage")
//    public String imageAnalysis(@RequestParam MultipartFile image)
//            throws IOException {
//        String fixedRequestText = "이 사진에 있는 물건들이 뭔지 설명없이 키워드만 말해줘";
//        ChatGPTResponse response = pictureProductService.requestImageAnalysis(image, fixedRequestText);
//        return response.getChoices().get(0).getMessage().getContent();
//    }
//
//    @PostMapping("/takeImage")
//    public String imageTake(@RequestParam MultipartFile image)
//            throws IOException {
//        String fixedRequestText = "이 사진에 있는 물건들이 뭔지 설명없이 키워드만 말해줘";
//        ChatGPTResponse response = pictureProductService.requestImageAnalysis(image, fixedRequestText);
//        return response.getChoices().get(0).getMessage().getContent();
//    }

    @PostMapping(value="/tomorrowImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary="내일 사진분석 API",
            description="내일 사진분석 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<String> tomorrowImageAnalysis(HttpServletRequest token, @RequestParam("image") MultipartFile image)
            throws IOException {
        String accessToken = jwtManager.getToken(token);
        String fixedRequestText = "이 사진에 있는 물건들이 뭔지 설명없이 키워드만 ,로 구분해서 말해줘";
        pictureProductService.requestImageAnalysis(accessToken, image, fixedRequestText);
        return ApiResponse.onSuccess("오늘 사진분석 완료");
    }

//    @PostMapping(value="/todayImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @Operation(summary="내일 사진분석 API",
//            description="내일 사진분석 API",security = {@SecurityRequirement(name="session-token")} )
//    public ApiResponse<String> todayImageAnalysis(HttpServletRequest token, @RequestParam("image") MultipartFile image)
//            throws IOException {
//        String accessToken = jwtManager.getToken(token);
//        String fixedRequestText = "이 사진에 있는 물건들이 뭔지 설명없이 키워드만 말해줘";
//        pictureProductService.requestImageAnalysis(accessToken, image, fixedRequestText);
//        return ApiResponse.onSuccess("오늘 사진분석 완료");
//    }



//text로만 요청
//    @PostMapping("/text")
//    public String textAnalysis(@RequestParam String requestText) {
//        ChatGPTResponse response = pictureProductService.requestTextAnalysis(requestText);
//        return response.getChoices().get(0).getMessage().getContent();
//    }
}