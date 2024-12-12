package capstone.checkIT.controller;

import capstone.checkIT.DTO.DeviceDTO.DeviceRequestDTO;
import capstone.checkIT.DTO.DeviceDTO.DeviceResponseDTO;
import capstone.checkIT.apipayLoad.ApiResponse;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.service.deviceService.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
public class DeviceController {
    private final JwtManager jwtManager;
    private final DeviceService deviceService;

    // Device 생성
    @PutMapping("/")
    @Operation(summary="디바이스 생성 API",
            description="디바이스 생성 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<DeviceResponseDTO> createDevice(HttpServletRequest token, @RequestBody DeviceRequestDTO request) {
        String accessToken = jwtManager.getToken(token);
        DeviceResponseDTO response = deviceService.createDevice(accessToken, request);
        return ApiResponse.onSuccess(response);
    }

    // Device 조회 한 멤버가 가지고있는 모든 디바이스 정보 반환
    @GetMapping("/member/{memberId}")
    @Operation(summary="멤버의 디바이스들 조회 API",
            description="멤버의 디바이스들 조회 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<List<DeviceResponseDTO>> getDevicesByMemberId(@PathVariable("memberId") Long memberId) {
        List<DeviceResponseDTO> response = deviceService.getDevicesByMemberId(memberId);
        return ApiResponse.onSuccess(response);
    }

    // 단일 Device 정보 조회
    @GetMapping("/{deviceId}")
    @Operation(summary="단일 디바이스 정보 조회 API",
            description="단일 디바이스 정보 조회 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<DeviceResponseDTO> getDeviceById(HttpServletRequest token, @PathVariable("deviceId") Long deviceId) {
        String accessToken = jwtManager.getToken(token);
        DeviceResponseDTO response = deviceService.getDeviceById(accessToken, deviceId);
        return ApiResponse.onSuccess(response);
    }

    // Device 수정 현재 접속 디바이스만
    @PutMapping("/{deviceId}")
    @Operation(summary="디바이스 수정 API",
            description="디바이스 수정 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<DeviceResponseDTO> updateDevice(HttpServletRequest token, @PathVariable("deviceId") Long deviceId, @RequestBody DeviceRequestDTO request) {
        String accessToken = jwtManager.getToken(token);
        DeviceResponseDTO response = deviceService.updateDevice(accessToken, deviceId, request);
        return ApiResponse.onSuccess(response);
    }

    // Device 삭제
    @DeleteMapping("/{deviceId}")
    @Operation(summary="디바이스 삭제 API",
            description="디바이스 삭제 API",security = {@SecurityRequirement(name="session-token")} )
    public ApiResponse<String> deleteDevice(HttpServletRequest token, @PathVariable("deviceId") Long deviceId) {
        String accessToken = jwtManager.getToken(token);
        deviceService.deleteDevice(accessToken, deviceId);
        return ApiResponse.onSuccess("Device 삭제 완료");
    }
}
