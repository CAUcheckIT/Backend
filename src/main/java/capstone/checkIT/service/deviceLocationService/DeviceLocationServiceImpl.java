package capstone.checkIT.service.deviceLocationService;

import capstone.checkIT.apipayLoad.code.status.ErrorStatus;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.entity.Device;
import capstone.checkIT.entity.Location;
import capstone.checkIT.entity.Member;
import capstone.checkIT.exception.GeneralException;
import capstone.checkIT.repository.DeviceLocationRepository;
import capstone.checkIT.repository.DeviceRepository;
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
    private final DeviceRepository deviceRepository;

    public void stopLocation(String accessToken, Long deviceId) {

        // 1. JWT 토큰에서 memberId 추출
        Long memberId = jwtManager.validateJwt(accessToken);

        Device device = deviceRepository.findById(deviceId).orElse(null);
        Timestamp startTime = device.getRecentStartTime();
        // 3. Location과 연결된 DeviceLocation의 isFinished 업데이트
        int updatedRows = deviceLocationRepository.updateIsFinishedByStartTimeAndMemberId(startTime, memberId);

        // 4. member의 isStart true -> false
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ERROR_EMAIL));

        if (member.getIsStart() != null && member.getIsStart()) {
            member.setIsStart(false);
            memberRepository.save(member);
            member.setEndTime(new Timestamp(System.currentTimeMillis())); // 현재 시간 설정
            log.info("Member's isStart updated to false for memberId: {}", memberId);
        }

        log.info("Updated {} rows for startTime: {}", updatedRows, startTime);
    }
}
