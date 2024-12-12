package capstone.checkIT.service.pictureProductService;

import capstone.checkIT.DTO.TodoDTO.TodoResponseDTO;
import capstone.checkIT.DTO.openAiDTO.response.ChatGPTResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface PictureProductService {

    //ChatGPTResponse requestTextAnalysis(String requestText);

    TodoResponseDTO.TomorrowResponse requestImageAnalysis(String accessToken, String imgUrl, String requestText)  throws IOException;

    TodoResponseDTO.TodayResponse requestTakeImageAnalysis(String accessToken, String imgUrl, String requestText, Long todoId)  throws IOException;
}