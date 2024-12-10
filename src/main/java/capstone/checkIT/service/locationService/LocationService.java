package capstone.checkIT.service.locationService;

import capstone.checkIT.DTO.LocationDTO.LocationRequestDTO;
import capstone.checkIT.DTO.LocationDTO.LocationResponseDTO;
import capstone.checkIT.entity.Location;

import java.util.List;

public interface LocationService {
    List<LocationResponseDTO> saveLocation(String accessToken, LocationRequestDTO request);

    List<LocationResponseDTO> getLatestRoute(String accessToken, Long deviceId);

    List<LocationResponseDTO> processLocationData(Long deviceId);

    String processRecentLocations(List<Location> recentLocations);

    boolean isBus(Location first, Location second, Location third);

    boolean isSubway(List<Location> recentLocations);

    boolean isStationary(List<Location> recentLocations);

    double calculateDistance(Location a, Location b);

    boolean isWithinTolerance(Location a, Location b);

    LocationResponseDTO mapToResponse(Location location, String tag);
}
