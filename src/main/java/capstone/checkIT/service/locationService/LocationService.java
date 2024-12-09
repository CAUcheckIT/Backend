package capstone.checkIT.service.locationService;

import capstone.checkIT.DTO.LocationDTO.LocationRequestDTO;
import capstone.checkIT.DTO.LocationDTO.LocationResponseDTO;

import java.util.List;

public interface LocationService {
    void saveLocation(String accessToken, LocationRequestDTO request);

    List<LocationResponseDTO> getLatestRoute(String accessToken, Long deviceId);
}
