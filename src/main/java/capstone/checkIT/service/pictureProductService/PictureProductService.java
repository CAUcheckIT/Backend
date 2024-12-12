package capstone.checkIT.service.pictureProductService;

import capstone.checkIT.DTO.openAiDTO.response.ChatGPTResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface PictureProductService {

    //ChatGPTResponse requestTextAnalysis(String requestText);

    void requestImageAnalysis(String accessToken, MultipartFile image, String requestText)throws IOException;

    void requestTakeImageAnalysis(String accessToken, MultipartFile image, String requestText, Long todoId)  throws IOException;
}