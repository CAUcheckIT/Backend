package capstone.checkIT.service.deviceService;

import capstone.checkIT.DTO.DeviceDTO.DeviceRequestDTO;
import capstone.checkIT.DTO.DeviceDTO.DeviceResponseDTO;
import capstone.checkIT.apipayLoad.code.status.ErrorStatus;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.entity.Device;
import capstone.checkIT.entity.Member;
import capstone.checkIT.exception.GeneralException;
import capstone.checkIT.repository.DeviceRepository;
import capstone.checkIT.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final MemberRepository memberRepository;
    private final JwtManager jwtManager;

    // Device 생성
    public DeviceResponseDTO createDevice(String accessToken, DeviceRequestDTO request) {
        // 토큰 검증 및 멤버 ID 추출
        Long memberId = jwtManager.validateJwt(accessToken);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ERROR_EMAIL));

        // Device 생성
        Device device = Device.builder()
                .information(request.getInformation())
                .name(request.getName())
                //.isLogin(request.getIsLogin())
                .member(member)
                .build();

        deviceRepository.save(device);

        return DeviceResponseDTO.builder()
                .id(device.getId())
                .information(device.getInformation())
                .name(device.getName())
                //.isLogin(device.getIsLogin())
                .memberId(member.getId())
                .build();
    }


    // 멤버가 가지고있는 Device 조회
    public List<DeviceResponseDTO> getDevicesByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ERROR_EMAIL));

        // 해당 멤버의 디바이스 조회
        List<Device> devices = deviceRepository.findByMemberId(memberId);

        // 응답 DTO로 변환
        return devices.stream()
                .map(device -> DeviceResponseDTO.builder()
                        .id(device.getId())
                        .information(device.getInformation())
                        .name(device.getName())
                        .memberId(memberId)
                        .build())
                .collect(Collectors.toList());
    }

    // 단일 device 정보
    public DeviceResponseDTO getDeviceById(String accessToken, Long deviceId) {
        // Device 조회
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.DEVICE_NOT_FOUND));

        // Member 추출
        Member member = device.getMember();

        // 토큰에서 추출한 멤버 ID와 입력받은 멤버 ID 비교
        if (!isOwner(accessToken, member.getId())) {
            throw new GeneralException(ErrorStatus.DEVICE_UNVALID); // 예외 처리
        }

        // DTO 변환
        return DeviceResponseDTO.builder()
                .id(device.getId())
                .information(device.getInformation())
                .name(device.getName())
                //.isLogin(device.getIsLogin())
                .memberId(device.getMember().getId())
                .build();
    }

    // Device 수정
    public DeviceResponseDTO updateDevice(String accessToken, Long deviceId, DeviceRequestDTO request) {

        // Device 조회
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.DEVICE_NOT_FOUND));

        // Member 추출
        Member member = device.getMember();

        // 토큰에서 추출한 멤버 ID와 입력받은 멤버 ID 비교
        if (!isOwner(accessToken, member.getId())) {
            throw new GeneralException(ErrorStatus.DEVICE_UNVALID); // 예외 처리
        }

        // 수정
        device.setInformation(request.getInformation());
        device.setName(request.getName());
        //device.setIsLogin(request.getIsLogin());
        deviceRepository.save(device);

        return DeviceResponseDTO.builder()
                .id(device.getId())
                .information(device.getInformation())
                .name(device.getName())
                //.isLogin(device.getIsLogin())
                .memberId(device.getMember().getId())
                .build();
    }

    // Device 삭제
    public void deleteDevice(String accessToken, Long deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.DEVICE_NOT_FOUND));

        // Member 추출
        Member member = device.getMember();

        // 토큰에서 멤버 ID 추출 및 검증
        if (!isOwner(accessToken, member.getId())) {
            throw new GeneralException(ErrorStatus.DEVICE_UNVALID); // 권한 없음 예외
        }
        deviceRepository.delete(device);
    }

    // 디바이스 검증
    public boolean isOwner(String accessToken, Long memberId) {

        Long tokenMemberId = jwtManager.validateJwt(accessToken);

        return memberId.equals(tokenMemberId);
    }
}
