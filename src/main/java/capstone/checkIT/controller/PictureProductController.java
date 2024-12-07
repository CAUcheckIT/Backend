package capstone.checkIT.controller;

/*
import java.io.IOException;

import capstone.checkIT.dto.openAiDTO.response.ChatGPTResponse;
import capstone.checkIT.service.pictureProductService.PictureProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor*/
public class PictureProductController {
    /*private final PictureProductService pictureProductService;

    @PostMapping("/image")
    public String imageAnalysis(@RequestParam MultipartFile image)
            throws IOException {
        String fixedRequestText = "이 사진에 있는 물건들이 뭔지 설명없이 키워드만 말해줘";
        ChatGPTResponse response = pictureProductService.requestImageAnalysis(image, fixedRequestText);
        return response.getChoices().get(0).getMessage().getContent();
    }
//text로만 요청
//    @PostMapping("/text")
//    public String textAnalysis(@RequestParam String requestText) {
//        ChatGPTResponse response = pictureProductService.requestTextAnalysis(requestText);
//        return response.getChoices().get(0).getMessage().getContent();
//    }*/
}
