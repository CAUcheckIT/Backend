package capstone.checkIT.service.memberService;

import capstone.checkIT.DTO.MemberDTO.MemberRequestDTO;
import capstone.checkIT.DTO.MemberDTO.MemberResponseDTO;
import capstone.checkIT.apipayLoad.code.status.ErrorStatus;
import capstone.checkIT.apipayLoad.handler.TempHandler;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.converter.MyInfoConverter;
import capstone.checkIT.entity.Member;
import capstone.checkIT.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final JwtManager jwtManager;

    @Override
    public MemberResponseDTO.MypageDTO getMyPage(String accessToken) {

        Long memberId = jwtManager.validateJwt(accessToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new TempHandler(ErrorStatus.LOGIN_ERROR_EMAIL));


        return MyInfoConverter.toMyInfoResponseDTO(member);
    }

    @Override
    public MemberResponseDTO.MypageDTO updateMyInfo(String accessToken, MemberRequestDTO.MyDetailInfoDto myDetailInfoDto){
        Long memberId = jwtManager.validateJwt(accessToken);
        Member member = memberRepository.findById(memberId)
               .orElseThrow(() -> new TempHandler(ErrorStatus.LOGIN_ERROR_EMAIL));

        // update code
        member.setName(myDetailInfoDto.getName());
        member.setAddress(myDetailInfoDto.getAddress());

        memberRepository.save(member);

        return MyInfoConverter.toMyInfoResponseDTO(member);
    }





}
