package capstone.checkIT.service.todoService;

import capstone.checkIT.DTO.TodoDTO.TodoResponseDTO;

public interface TodoService {
    TodoResponseDTO.TomorrowResponse getTomorrowList(String accessToken, Long todoId);
    TodoResponseDTO.TomorrowResponse createTomorrow(String accessToken, Long todoId);
    void deleteTomorrow(String accessToken, Long productId);
    TodoResponseDTO.TomorrowResponse addTomorrow(String accessToken, Long todoId, String productName);
    TodoResponseDTO.TomorrowResponse updateTomorrow(String accessToken, Long productId, String productName);

    //TodoResponseDTO.TodayResponse getTodayList(String accessToken, Long todoId);
    TodoResponseDTO.TodayResponse createToday(String accessToken, Long todoId);
    //void deleteToday(String accessToken, Long productId);
    //TodoResponseDTO.TodayResponse addToday(String accessToken, Long todoId, String productName);
    //TodoResponseDTO.TodayResponse updateToday(String accessToken, Long productId, String productName);

}
