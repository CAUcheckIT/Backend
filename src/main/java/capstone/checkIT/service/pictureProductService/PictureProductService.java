package capstone.checkIT.service.pictureProductService;

import capstone.checkIT.DTO.LocationPictureDTO;
import capstone.checkIT.DTO.TodoDTO.TodoResponseDTO;

import java.io.IOException;


public interface PictureProductService {

    //ChatGPTResponse requestTextAnalysis(String requestText);

    TodoResponseDTO.TomorrowResponse requestImageAnalysis(String accessToken, String imgUrl, String requestText)  throws IOException;

    TodoResponseDTO.TodayResponse requestTakeImageAnalysis(String accessToken, String imgUrl, String requestText, Long todoId)  throws IOException;

    LocationPictureDTO.LocationPictureResponseDTO locationImageAnalysis(String accessToken, String url, String fixedRequestText, Long todoId) throws IOException;
}