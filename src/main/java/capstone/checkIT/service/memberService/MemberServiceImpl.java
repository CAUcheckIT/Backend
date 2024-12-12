package capstone.checkIT.service.memberService;

import capstone.checkIT.DTO.MemberDTO.MemberRequestDTO;
import capstone.checkIT.DTO.MemberDTO.MemberResponseDTO;
import capstone.checkIT.apipayLoad.code.status.ErrorStatus;
import capstone.checkIT.apipayLoad.handler.TempHandler;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.converter.MyInfoConverter;
import capstone.checkIT.entity.Device;
import capstone.checkIT.entity.Member;
import capstone.checkIT.exception.GeneralException;
import capstone.checkIT.repository.DeviceRepository;
import capstone.checkIT.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final JwtManager jwtManager;
    private final DeviceRepository deviceRepository;


    @Override
    public MemberResponseDTO.MypageDTO getMyPage(String accessToken) {

        Long memberId = jwtManager.validateJwt(accessToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ERROR_EMAIL));


        return MyInfoConverter.toMyInfoResponseDTO(member);
    }

    @Override
    public MemberResponseDTO.MypageDTO updateMyInfo(String accessToken, MemberRequestDTO.MyDetailInfoDto myDetailInfoDto){
        Long memberId = jwtManager.validateJwt(accessToken);
        Member member = memberRepository.findById(memberId)
               .orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ERROR_EMAIL));

        // update code
        member.setName(myDetailInfoDto.getName());
        member.setAddress(myDetailInfoDto.getAddress());

        memberRepository.save(member);

        return MyInfoConverter.toMyInfoResponseDTO(member);
    }

    @Override
    public void startButton(String accessToken, Long deviceId){
        Long memberId = jwtManager.validateJwt(accessToken);
        Member member = memberRepository.findById(memberId)
               .orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ERROR_EMAIL));

        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.DEVICE_NOT_FOUND));        // update code
        member.setIsStart(true);
        member.setStartTime(new Timestamp(System.currentTimeMillis()));
        device.setRecentStartTime(new Timestamp(System.currentTimeMillis()));
        memberRepository.save(member);
        deviceRepository.save(device);
    }





}
