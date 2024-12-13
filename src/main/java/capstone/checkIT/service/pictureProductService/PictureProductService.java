package capstone.checkIT.service.pictureProductService;

import capstone.checkIT.DTO.openAiDTO.response.ChatGPTResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface PictureProductService {

    //ChatGPTResponse requestTextAnalysis(String requestText);

    Long requestImageAnalysis(String accessToken, String imageUrl, String requestText)throws IOException;

    void requestTakeImageAnalysis(String accessToken, String imageUrl, String requestText, Long todoId)  throws IOException;
}