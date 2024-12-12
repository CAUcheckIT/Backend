package capstone.checkIT.controller;

import capstone.checkIT.apipayLoad.ApiResponse;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.service.deviceLocationService.DeviceLocationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/device-location")
@RequiredArgsConstructor
public class DeviceLocationController {

    private final DeviceLocationService deviceLocationService;
    private final JwtManager jwtManager;

    @PostMapping("/stop/{deviceId}")
    public ApiResponse<String> stopLocation(
            HttpServletRequest token,
            @PathVariable("deviceId") Long deviceId) {

        String accessToken = jwtManager.getToken(token);
        deviceLocationService.stopLocation(accessToken, deviceId);
        return ApiResponse.onSuccess("경로저장이 종료되었습니다.");
    }
}
