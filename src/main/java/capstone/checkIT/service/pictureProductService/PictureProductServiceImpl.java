package capstone.checkIT.service.pictureProductService;

import java.io.IOException;

import capstone.checkIT.DTO.openAiDTO.request.ChatGPTRequest;
import capstone.checkIT.DTO.openAiDTO.response.ChatGPTResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PictureProductServiceImpl implements PictureProductService {
    @Value("${openai.model}")
    private String apiModel;

    @Value("${openai.api.url}")
    private String apiUrl;

    private final RestTemplate template;

//    public ChatGPTResponse requestTextAnalysis(String requestText) {
//        ChatGPTRequest request = ChatGPTRequest.createTextRequest(apiModel, 500, "user", requestText);
//        return template.postForObject(apiUrl, request, ChatGPTResponse.class);
//    }

    // ImageUrl을 만듦
    public String requestImageAnalysis(MultipartFile image, String requestText)  throws IOException{
        String base64Image = Base64.encodeBase64String(image.getBytes());
        String imageUrl = "data:image/jpeg;base64," + base64Image;
        ChatGPTRequest request = ChatGPTRequest.createImageRequest(apiModel, 500, "user", requestText, imageUrl);
        ChatGPTResponse response = template.postForObject(apiUrl, request, ChatGPTResponse.class);
        return response.getChoices().get(0).getMessage().getContent();
    }
//    // ImageUrl을 만듦
//    //원래 throws IOException 포함
//    public ChatGPTResponse requestImageAnalysis(MultipartFile image, String requestText)  throws IOException{
//        String base64Image = Base64.encodeBase64String(image.getBytes());
//        String imageUrl = "data:image/jpeg;base64," + base64Image;
//        ChatGPTRequest request = ChatGPTRequest.createImageRequest(apiModel, 500, "user", requestText, imageUrl);
//        return template.postForObject(apiUrl, request, ChatGPTResponse.class);
//    }
}