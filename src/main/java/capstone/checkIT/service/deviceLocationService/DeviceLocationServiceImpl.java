package capstone.checkIT.service.deviceLocationService;

import capstone.checkIT.apipayLoad.code.status.ErrorStatus;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.entity.Location;
import capstone.checkIT.entity.Member;
import capstone.checkIT.exception.GeneralException;
import capstone.checkIT.repository.DeviceLocationRepository;
import capstone.checkIT.repository.LocationRepository;
import capstone.checkIT.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceLocationServiceImpl implements DeviceLocationService{

    private final DeviceLocationRepository deviceLocationRepository;
    private final LocationRepository locationRepository;
    private final JwtManager jwtManager;
    private final MemberRepository memberRepository;

    public void stopLocation(String accessToken, Timestamp startTime) {
        log.info("Stopping location tracking for startTime: {}", startTime);

        // 1. JWT 토큰에서 memberId 추출
        Long memberId = jwtManager.validateJwt(accessToken);

        // 2. 해당 memberId의 Location과 DeviceLocation 조회
        List<Location> locations = locationRepository.findByStartTime(startTime);
        if (locations.isEmpty()) {
            throw new GeneralException(ErrorStatus.START_TIME_NOT_FOUND); // 시작 시간에 해당하는 경로가 없는 경우
        }

        // 3. Location과 연결된 DeviceLocation의 isFinished 업데이트
        int updatedRows = deviceLocationRepository.updateIsFinishedByStartTimeAndMemberId(startTime, memberId);

        // 4. member의 isStart true -> false
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ERROR_EMAIL));

        if (member.getIsStart() != null && member.getIsStart()) {
            member.setIsStart(false);
            memberRepository.save(member);
            log.info("Member's isStart updated to false for memberId: {}", memberId);
        }

        log.info("Updated {} rows for startTime: {}", updatedRows, startTime);
    }
}
