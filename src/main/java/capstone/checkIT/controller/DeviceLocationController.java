package capstone.checkIT.controller;

import capstone.checkIT.apipayLoad.ApiResponse;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.service.deviceLocationService.DeviceLocationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequestMapping("/device-location")
@RequiredArgsConstructor
public class DeviceLocationController {

    private final DeviceLocationService deviceLocationService;
    private final JwtManager jwtManager;

    @PostMapping("/stop")
    public ApiResponse<String> stopLocation(
            HttpServletRequest token,
            @RequestParam Timestamp startTime) {

        String accessToken = jwtManager.getToken(token);
        deviceLocationService.stopLocation(accessToken, startTime);
        return ApiResponse.onSuccess("경로저장이 종료되었습니다.");
    }
}
