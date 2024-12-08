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
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

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

    // 위치 데이터 저장 로직
    @Override
    public void saveLocation(String accessToken, LocationRequestDTO request) {
        log.info("Saving location: {}", request);

        // 토큰에서 memberId 추출
        Long memberId = jwtManager.validateJwt(accessToken);

        // 멤버 검증
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ERROR_EMAIL));

        // 디바이스 검증 및 조회
        Device device = deviceRepository.findById(request.getId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.DEVICE_NOT_FOUND));

        // 디바이스 멤버 검증
        if (!device.getMember().getId().equals(memberId)) {
            throw new GeneralException(ErrorStatus.DEVICE_UNVALID); // 디바이스가 멤버 소유가 아닌 경우
        }

        // DTO → Entity 변환
        Location location = Location.builder()
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .velocity(request.getVelocity())
                .time(request.getTime())
                .startTime(request.getStartTime())
                .build();

        // Device와 Location의 연관 관계 설정
        DeviceLocation deviceLocation = DeviceLocation.builder()
                .device(device)
                .location(location)
                .isFinished(false) // 초기값은 false로 설정 stop누르면 true 변경
                .build();

        // 데이터 저장
        locationRepository.save(location);
        deviceLocationRepository.save(deviceLocation);

        log.info("Location data saved successfully for deviceId: {}", request.getId());
    }
}

