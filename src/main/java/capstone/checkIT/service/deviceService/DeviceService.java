package capstone.checkIT.service.deviceService;

import capstone.checkIT.DTO.DeviceDTO.DeviceRequestDTO;
import capstone.checkIT.DTO.DeviceDTO.DeviceResponseDTO;

import java.util.List;

public interface DeviceService {
    DeviceResponseDTO createDevice(String accessToken, DeviceRequestDTO request);
    List<DeviceResponseDTO> getDevicesByMemberId(Long memberId);
    DeviceResponseDTO updateDevice(String accessToken, Long deviceId, DeviceRequestDTO request);
    void deleteDevice(String accessToken, Long deviceId);
    boolean isOwner(String accessToken, Long memberId);
    DeviceResponseDTO getDeviceById(String accessToken, Long deviceId);
}
