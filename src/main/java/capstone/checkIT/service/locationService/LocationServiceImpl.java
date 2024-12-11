package capstone.checkIT.service.locationService;

import capstone.checkIT.apipayLoad.code.status.ErrorStatus;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.entity.Device;
import capstone.checkIT.entity.DeviceLocation;
import capstone.checkIT.entity.Location;
import capstone.checkIT.entity.Member;
import capstone.checkIT.exception.GeneralException;
import capstone.checkIT.repository.DeviceLocationRepository;
import capstone.checkIT.repository.DeviceRepository;
import capstone.checkIT.repository.LocationRepository;
import capstone.checkIT.DTO.LocationDTO.*;
import capstone.checkIT.repository.MemberRepository;
import capstone.checkIT.service.deviceService.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final JwtManager jwtManager;
    private final MemberRepository memberRepository;
    private final DeviceService deviceService;
    private final DeviceRepository deviceRepository;
    private final DeviceLocationRepository deviceLocationRepository;

    private static final int MIN_DATA_POINTS = 3;
    private static final int MIN_STATIONARY_POINTS = 5; // stationary 판단 기준 데이터 개수
    private static final double BUS_SPEED_MIN = 10.0; // 버스 속도 최소값 (km/h)
    private static final double BUS_SPEED_MAX = 40.0; // 버스 속도 최대값 (km/h)
    private static final double SUBWAY_SPEED_MIN = 20.0; // 지하철 속도 최소값 (km/h)
    private static final double SUBWAY_SPEED_MAX = 70.0; // 지하철 속도 최대값 (km/h)
    private static final double SUBWAY_VELOCITY_THRESHOLD = 10.0; // 지하철 속도값 평균 기준
    private static final double LOCATION_TOLERANCE = 0.0005; // 위치 오차 범위 (위도/경도 차이)

    @Override
    public List<LocationResponseDTO> saveLocation(String accessToken, LocationRequestDTO request) {
        log.info("Saving location: {}", request);

        // 1. JWT 토큰에서 memberId 추출
        Long memberId = jwtManager.validateJwt(accessToken);

        // 2. 멤버 검증
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ERROR_EMAIL));

        // 3. 디바이스 검증 및 조회
        Device device = deviceRepository.findById(request.getId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.DEVICE_NOT_FOUND));

        // 4. 디바이스 소유자 검증
        if (!device.getMember().getId().equals(memberId)) {
            throw new GeneralException(ErrorStatus.DEVICE_UNVALID); // 디바이스가 멤버 소유가 아닌 경우
        }

        // 5. DTO → Entity 변환
        Location location = Location.builder()
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .velocity(request.getVelocity())
                .time(request.getTime())
                .startTime(member.getStartTime())
                .build();

        // 6. Device와 Location의 연관 관계 설정
        DeviceLocation deviceLocation = DeviceLocation.builder()
                .device(device)
                .location(location)
                .isFinished(false) // 초기값은 false로 설정, stop누르면 true 변경
                .build();

        // 7. 데이터 저장
        locationRepository.save(location);
        deviceLocationRepository.save(deviceLocation);

        log.info("Location data saved successfully for deviceId: {}", request.getId());

        // 8. 최신 위치 데이터를 기반으로 판단 로직 실행 및 결과 반환
        return processLocationData(request.getId());// deviceId
    }

    public List<LocationResponseDTO> processLocationData(Long deviceId) {
        // 최근 위치 데이터 가져오기: 10개
        Pageable pageable = PageRequest.of(0, 10); // 첫 페이지, 최대 10개
        List<Location> recentLocations = locationRepository.findLatestLocationsByDeviceId(deviceId, pageable);

        // 데이터가 부족하면 모든 데이터에 "unknown" 태그를 유지
        if (recentLocations.size() < MIN_DATA_POINTS) {
            log.info("Not enough location data. Keeping all tags as unknown.");
            return recentLocations.stream()
                    .map(location -> mapToResponse(location, location.getTag())) // 기존 태그 유지
                    .collect(Collectors.toList());
        }

        // 판단 로직 실행
        String tag = processRecentLocations(recentLocations);

        // 판단된 태그를 업데이트할 데이터만 필터링
        List<Location> locationsToUpdate;
        if ("bus".equals(tag) || "subway".equals(tag)) {
            // 최근 3개의 태그만 변경
            locationsToUpdate = recentLocations.stream()
                    .sorted(Comparator.comparing(Location::getTime).reversed()) // 최신순으로 정렬
                    .limit(MIN_DATA_POINTS) // 상위 3개만 가져옴
                    .collect(Collectors.toList());
        } else if ("stationary".equals(tag)) {
            // 최근 10개의 태그를 변경 (stationary의 경우)
            locationsToUpdate = recentLocations;
        } else {
            // 다른 경우는 태그를 변경하지 않음
            locationsToUpdate = List.of();
        }

        // 판단된 태그를 각 Location 엔티티에 설정 및 저장
        locationsToUpdate.forEach(location -> {
            location.setTag(tag); // 판단된 태그 설정
            locationRepository.save(location); // 저장
        });

        // 결과 데이터를 LocationResponseDTO로 변환 및 반환
        return recentLocations.stream()
                .map(location -> mapToResponse(location, location.getTag())) // 기존 태그 사용
                .collect(Collectors.toList());
    }

    public String processRecentLocations(List<Location> recentLocations) {
        List<Location> sortedLocations = recentLocations.stream()
                .sorted(Comparator.comparing(Location::getTime)) // 시간순 정렬
                .collect(Collectors.toList());

        Location first = recentLocations.get(0);
        Location second = recentLocations.get(1);
        Location third = recentLocations.get(2);

        // 1. 버스 판단
        if (isBus(first, second, third)) {
            log.info("Bus detected based on recent locations.");
            return "bus";
        }

        // 2. 지하철 판단
        if (isSubway(recentLocations)) {
            log.info("Subway detected based on recent locations.");
            return "subway";
        }

        // 3. 정지 상태 판단
        if (isStationary(recentLocations)) {
            log.info("Stationary detected based on recent locations.");
            return "stationary";
        }

        // 4. 기본값 (걸어가는 상태)
        log.info("Walking detected. Defaulting to unknown.");
        return "unknown";
    }

    public boolean isBus(Location first, Location second, Location third) {
        double distance1 = calculateDistance(first, second);
        double distance2 = calculateDistance(second, third);
        double totalDistance = distance1 + distance2;

        long totalTimeMillis = first.getTime().getTime() - third.getTime().getTime();
        //
        log.info("thirdtime: {}, firsttime: {}", third.getTime(), first.getTime());
        log.info("thirdtime: {}, firsttime: {}", third.getTime().getTime(), first.getTime().getTime());
        //
        double averageSpeed = (totalDistance / (totalTimeMillis / 1000.0)) * 3.6; // m/s -> km/h

        // 속도 평균 계산
        double averageVelocity = (first.getVelocity().doubleValue() +
                second.getVelocity().doubleValue() +
                third.getVelocity().doubleValue()) / 3;

        // 디버깅 출력
        log.info("Distance1: {}, Distance2: {}, TotalDistance: {}", distance1, distance2, totalDistance);
        log.info("TotalTimeMillis: {}, AverageSpeed: {}", totalTimeMillis, averageSpeed);

        // 버스 조건 확인
        return BUS_SPEED_MIN <= averageSpeed && averageSpeed <= BUS_SPEED_MAX &&
                averageVelocity >= 10.0; // 속도가 10km/h 이상이어야 버스
         }

    public boolean isSubway(List<Location> recentLocations) {
        Location first = recentLocations.get(0);
        Location second = recentLocations.get(1);
        Location third = recentLocations.get(2);

        double distance1 = calculateDistance(first, second);
        double distance2 = calculateDistance(second, third);
        double totalDistance = distance1 + distance2;

        long totalTimeMillis = first.getTime().getTime() - third.getTime().getTime();
        double averageSpeed = (totalDistance / (totalTimeMillis / 1000.0)) * 3.6; // m/s -> km/h

        double averageVelocity = (first.getVelocity().doubleValue() +
                second.getVelocity().doubleValue() +
                third.getVelocity().doubleValue()) / 3;

        return SUBWAY_SPEED_MIN <= averageSpeed && averageSpeed <= SUBWAY_SPEED_MAX &&
                averageVelocity <= SUBWAY_VELOCITY_THRESHOLD; // 속도가 10km/h 이하이어야 지하철
    }

    public boolean isStationary(List<Location> recentLocations) {
        // 데이터가 최소 5개 이상일 때만 stationary 판단 가능
        if (recentLocations.size() < MIN_STATIONARY_POINTS) {
            log.info("Not enough data points for stationary check.");
            return false;
        }

        // 기준 위치(reference)를 첫 번째 데이터로 설정
        Location reference = recentLocations.get(0);

        // 기준 위치와 비교하여 tolerance 범위에 포함된 데이터 개수 계산
        long stationaryCount = recentLocations.stream()
                .filter(location -> isWithinTolerance(reference, location)) // 기준 위치와 비교
                .count();

        // stationaryCount가 5개 이상이면 stationary로 판단
        boolean isStationary = stationaryCount >= MIN_STATIONARY_POINTS;

        log.info("Stationary check: TotalCount={}, StationaryCount={}, IsStationary={}",
                recentLocations.size(), stationaryCount, isStationary);

        return isStationary;
    }

    public double calculateDistance(Location a, Location b) {
        double latDiff = a.getLatitude().subtract(b.getLatitude()).doubleValue();
        double lonDiff = a.getLongitude().subtract(b.getLongitude()).doubleValue();
        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff) * 111320; // 위도/경도 차이 -> 거리 (m)
    }

    public boolean isWithinTolerance(Location a, Location b) {
        BigDecimal latDiff = a.getLatitude().subtract(b.getLatitude()).abs();
        BigDecimal lonDiff = a.getLongitude().subtract(b.getLongitude()).abs();
        return latDiff.doubleValue() <= LOCATION_TOLERANCE && lonDiff.doubleValue() <= LOCATION_TOLERANCE;
    }

    public LocationResponseDTO mapToResponse(Location location, String tag) {
        return new LocationResponseDTO(
                location.getLatitude(),
                location.getLongitude(),
                location.getTime(),
                tag
        );
    }


    public List<LocationResponseDTO> getLatestRoute(String accessToken, Long deviceId) {
        log.info("Fetching latest route for deviceId: {}", deviceId);

        // 1. JWT 토큰에서 memberId 추출 및 검증
        Long memberId = jwtManager.validateJwt(accessToken);

        // 2. 디바이스 검증
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.DEVICE_NOT_FOUND));

        // 3. 디바이스 소유자 검증
        if(!deviceService.isOwner(accessToken, memberId)){
            throw new GeneralException(ErrorStatus.DEVICE_UNVALID);
        }

        // 4. 가장 최신 startTime 조회
        Timestamp latestStartTime = locationRepository.findLatestStartTimeByDeviceId(deviceId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LOCATION_NOT_FOUND));

        log.info("Latest startTime found: {}", latestStartTime);

        // 5. 최신 startTime과 관련된 Location 리스트 조회
        List<Location> locations = locationRepository.findByStartTime(latestStartTime);

        // 6. Location → LocationResponseDTO 변환 및 반환
//        return locations.stream()
//                .map(location -> new LocationResponseDTO(
//                        location.getId(),
//                        location.getLatitude(),
//                        location.getLongitude(),
//                        location.getVelocity(),
//                        location.getTime(),
//                        location.getStartTime()
//                ))
//                .collect(Collectors.toList());
        return locations.stream()
                .map(location -> new LocationResponseDTO(
                        location.getLatitude(),
                        location.getLongitude(),
                        location.getTime(),
                        location.getTag()
                ))
                .collect(Collectors.toList());
    }
}