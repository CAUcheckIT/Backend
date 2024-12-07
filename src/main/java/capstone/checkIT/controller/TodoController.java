package capstone.checkIT.controller;

import capstone.checkIT.apipayLoad.ApiResponse;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.service.todoService.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;
    private final JwtManager jwtManager;

    @GetMapping("/tomorrow/list")
    @Operation(summary="내일 소지품 목록 조회 API",
            description="내일 소지품 목록 조회 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<>

}
