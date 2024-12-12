package capstone.checkIT.service.deviceLocationService;

import java.sql.Timestamp;

public interface DeviceLocationService {
    void stopLocation(String accessToken, Long deviceId);
}
