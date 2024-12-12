package capstone.checkIT.controller;

import capstone.checkIT.DTO.TodoDTO.TodoResponseDTO;
import capstone.checkIT.apipayLoad.ApiResponse;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.service.todoService.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;
    private final JwtManager jwtManager;

    @GetMapping("/tomorrow/list/{todoId}")
    @Operation(summary="내일 소지품 목록 조회 API",
            description="내일 소지품 목록 조회 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<TodoResponseDTO.TomorrowResponse> getTomorrowList (HttpServletRequest token, @PathVariable("todoId") Long todoId) {
        String accessToken = jwtManager.getToken(token);
        TodoResponseDTO.TomorrowResponse tomorrowResponse = todoService.getTomorrowList(accessToken, todoId);
        return ApiResponse.onSuccess(tomorrowResponse);
    }

//    @PostMapping("/tomorrow/{todoId}")
//    @Operation(summary="내일 소지품 등록 API",
//            description="내일 소지품 등록 API",security = {@SecurityRequirement(name="session-token")} )
//    public ApiResponse<TodoResponseDTO.TomorrowResponse> createTomorrowTodo (HttpServletRequest token, @PathVariable("todoId") Long todoId) {
//        String accessToken = jwtManager.getToken(token);
//        TodoResponseDTO.TomorrowResponse tomorrowResponse =todoService.createTomorrow(accessToken, todoId);
//        return ApiResponse.onSuccess(tomorrowResponse);
//    }

    @DeleteMapping("/tomorrow/{productId}")
    @Operation(summary="내일 소지품 삭제 API",
            description="내일 소지품 삭제 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<String> deleteTomorrowTodo (HttpServletRequest token, @PathVariable("productId") Long productId) {
        String accessToken = jwtManager.getToken(token);
        todoService.deleteTomorrow(accessToken, productId);
        return ApiResponse.onSuccess("삭제 성공");
    }

    @PostMapping("/tomorrow/add/{todoId}")
    @Operation(summary="내일 소지품 추가 API",
            description="내일 소지품 추가 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<TodoResponseDTO.TomorrowResponse> addTomorrowTodo (HttpServletRequest token, @PathVariable("todoId") Long todoId, String productName) {
        String accessToken = jwtManager.getToken(token);
        TodoResponseDTO.TomorrowResponse tomorrowResponse = todoService.addTomorrow(accessToken, todoId, productName);
        return ApiResponse.onSuccess(tomorrowResponse);


    }
    @PatchMapping("/tomorrow/{productId}")
    @Operation(summary="내일 소지품 수정 API",
            description="내일 소지품 수정 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<TodoResponseDTO.TomorrowResponse> updateTomorrowTodo (HttpServletRequest token, @PathVariable("productId") Long productId, String productName) {
        String accessToken = jwtManager.getToken(token);
        TodoResponseDTO.TomorrowResponse tomorrowResponse = todoService.updateTomorrow(accessToken, productId, productName);
        return ApiResponse.onSuccess(tomorrowResponse);
    }

//    @PostMapping("/today/{todoId}")
//    @Operation(summary="챙기기 소지품 등록 API",
//            description="챙기기 소지품 등록 API",security = {@SecurityRequirement(name="session-token")} )
//    public ApiResponse<TodoResponseDTO.TodayResponse> createTodayTodo (HttpServletRequest token, @PathVariable("todoId") Long todoId) {
//        String accessToken = jwtManager.getToken(token);
//        TodoResponseDTO.TodayResponse todayResponse = todoService.createToday(accessToken, todoId);
//        return ApiResponse.onSuccess(todayResponse);
//    }

    @GetMapping("/today/{todoId}")
    @Operation(summary="챙기기 소지품 목록 조회 API",
            description="챙기기 소지품 목록 조회 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<TodoResponseDTO.TodayResponse> getTodayList (HttpServletRequest token, @PathVariable("todoId") Long todoId) {
        String accessToken = jwtManager.getToken(token);
        TodoResponseDTO.TodayResponse todayResponse = todoService.getTodayList(accessToken, todoId);
        return ApiResponse.onSuccess(todayResponse);
    }

    @DeleteMapping("/today/{todoId}")
    @Operation(summary="챙기기 소지품 삭제 API",
            description="챙기기 소지품 삭제 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<String> deleteTodayTodo (HttpServletRequest token, @PathVariable("todoId") Long todoId) {
        String accessToken = jwtManager.getToken(token);
        todoService.deleteToday(accessToken, todoId);
        return ApiResponse.onSuccess("삭제 성공");
    }

    @PostMapping("/today/adds/{todoTodayId}")
    @Operation(summary="챙기기 소지품 추가 API",
            description="챙기기 소지품 추가 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<TodoResponseDTO.TodayResponse> addTodayTodo (HttpServletRequest token, @PathVariable("todoTodayId") Long todoTodayId, String productName) {
        String accessToken = jwtManager.getToken(token);
        TodoResponseDTO.TodayResponse todayResponse = todoService.addToday(accessToken, todoTodayId, productName);
        return ApiResponse.onSuccess(todayResponse);
    }

    @PatchMapping("/today/{todoTodayId}")
    @Operation(summary="챙기기 소지품 수정 API",
            description="챙기기 소지품 수정 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<TodoResponseDTO.TodayResponse> updateTodayTodo (HttpServletRequest token, @PathVariable("todoTodayId") Long todoTodayId, String productName) {
        String accessToken = jwtManager.getToken(token);
        TodoResponseDTO.TodayResponse todayResponse = todoService.updateToday(accessToken, todoTodayId, productName);
        return ApiResponse.onSuccess(todayResponse);
    }









}
