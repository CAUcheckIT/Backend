package capstone.checkIT.service.pictureProductService;

import capstone.checkIT.dto.openAiDTO.response.ChatGPTResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface PictureProductService {

    ChatGPTResponse requestTextAnalysis(String requestText);

    ChatGPTResponse requestImageAnalysis(MultipartFile image, String requestText)throws IOException;

}
