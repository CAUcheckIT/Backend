package capstone.checkIT.controller;

import capstone.checkIT.DTO.LocationDTO.LocationRequestDTO;
import capstone.checkIT.DTO.LocationDTO.LocationResponseDTO;
import capstone.checkIT.apipayLoad.ApiResponse;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.service.deviceLocationService.DeviceLocationService;
import capstone.checkIT.service.locationService.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;
    private final DeviceLocationService deviceLocationService;
    private final JwtManager jwtManager;

    // 위치 데이터 저장
    @PostMapping
    public ApiResponse<String> saveLocation(HttpServletRequest token, @RequestBody LocationRequestDTO request) {
        String accessToken = jwtManager.getToken(token);
        log.info("Location save request received: {}", request);
        locationService.saveLocation(accessToken, request);
        log.info("Location data saved successfully.");
        return ApiResponse.onSuccess("위치 저장 완료");
    }

    // 가장 최신 경로 반환
    @GetMapping("/latest/{deviceId}")
    public ApiResponse<List<LocationResponseDTO>> getLatestRoute(
            HttpServletRequest token,
            @PathVariable("deviceId") Long deviceId) {
        String accessToken = jwtManager.getToken(token);
        List<LocationResponseDTO> response = locationService.getLatestRoute(accessToken, deviceId);
        return ApiResponse.onSuccess(response);
    }



}