package capstone.checkIT.controller;

import capstone.checkIT.DTO.LocationDTO.LocationRequestDTO;
import capstone.checkIT.apipayLoad.ApiResponse;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.service.locationService.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService locationService;
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

}