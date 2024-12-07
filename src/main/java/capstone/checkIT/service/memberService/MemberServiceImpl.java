package capstone.checkIT.service.memberService;

import capstone.checkIT.DTO.MemberDTO.MemberResponseDTO;
import capstone.checkIT.apipayLoad.code.status.ErrorStatus;
import capstone.checkIT.apipayLoad.handler.TempHandler;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.converter.MyInfoConverter;
import capstone.checkIT.entity.Member;
import capstone.checkIT.entity.Month;
import capstone.checkIT.repository.MemberRepository;
import capstone.checkIT.repository.MonthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final JwtManager jwtManager;
    private final MonthRepository monthRepository;

    @Override
    public MemberResponseDTO.MypageDTO getMyPage(String accessToken) {

        Long memberId = jwtManager.validateJwt(accessToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new TempHandler(ErrorStatus.LOGIN_ERROR_EMAIL));

        List<Month> months = monthRepository.findByMemberId(memberId);
        //member의 lost_product = month의 productName 이어야함.

        return MyInfoConverter.toMyInfoResponseDTO(member);
    }





}
