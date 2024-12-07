package capstone.checkIT.service.locationService;

import capstone.checkIT.DTO.LocationDTO.LocationRequestDTO;

public interface LocationService {
    void saveLocation(String accessToken, LocationRequestDTO request);
}
