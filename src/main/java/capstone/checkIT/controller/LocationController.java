package capstone.checkIT.controller;

import capstone.checkIT.DTO.LocationDTO.LocationRequestDTO;
import capstone.checkIT.DTO.LocationDTO.LocationResponseDTO;
import capstone.checkIT.apipayLoad.ApiResponse;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.service.deviceLocationService.DeviceLocationService;
import capstone.checkIT.service.locationService.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @Operation(summary="위치데이터 저장 API",
            description="위치데이터 저장 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<List<LocationResponseDTO>> saveLocation(HttpServletRequest token, @RequestBody LocationRequestDTO request) {
        String accessToken = jwtManager.getToken(token);
        log.info("Location save request received: {}", request);

        // Service에서 반환된 LocationResponseDTO 리스트를 가져옴
        List<LocationResponseDTO> response = locationService.saveLocation(accessToken, request);

        log.info("Location data saved and processed successfully.");
        return ApiResponse.onSuccess(response); // 결과 데이터를 반환
    }

    // 가장 최신 경로 반환
    @GetMapping("/latest/{deviceId}")
    @Operation(summary="디바이스의 최긴경로 반환 API",
            description="디바이스의 최신경로 반환 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<List<LocationResponseDTO>> getLatestRoute(
            HttpServletRequest token,
            @PathVariable("deviceId") Long deviceId) {
        String accessToken = jwtManager.getToken(token);
        List<LocationResponseDTO> response = locationService.getLatestRoute(accessToken, deviceId);
        return ApiResponse.onSuccess(response);
    }



}