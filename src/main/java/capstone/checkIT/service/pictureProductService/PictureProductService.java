package capstone.checkIT.service.pictureProductService;

import capstone.checkIT.DTO.openAiDTO.response.ChatGPTResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface PictureProductService {

    //ChatGPTResponse requestTextAnalysis(String requestText);

    String requestImageAnalysis(MultipartFile image, String requestText)throws IOException;

}