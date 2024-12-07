package capstone.checkIT.service.locationService;

import capstone.checkIT.apipayLoad.code.status.ErrorStatus;
import capstone.checkIT.apipayLoad.handler.TempHandler;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.entity.Location;
import capstone.checkIT.entity.Member;
import capstone.checkIT.exception.GeneralException;
import capstone.checkIT.repository.LocationRepository;
import capstone.checkIT.DTO.LocationDTO.*;
import capstone.checkIT.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final JwtManager jwtManager;
    private final MemberRepository memberRepository;


    // 위치 데이터 저장 로직
    @Override
    public void saveLocation(String accessToken, LocationRequestDTO request) {
        log.info("Saving location: {}", request);

        // 검증
        Long memberId = jwtManager.validateJwt(accessToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ERROR_EMAIL));

        // DTO → Entity 변환
        Location location = Location.builder()
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .velocity(request.getVelocity())
                .time(request.getTime())
                .startTime(request.getStartTime())
                .build();

        // 데이터 저장
        locationRepository.save(location);
    }
}

