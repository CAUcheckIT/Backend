package capstone.checkIT.service.memberService;

import capstone.checkIT.DTO.MemberDTO.MemberResponseDTO;
import capstone.checkIT.apipayLoad.code.status.ErrorStatus;
import capstone.checkIT.apipayLoad.handler.TempHandler;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.converter.MemberConverter;
import capstone.checkIT.entity.Member;
import capstone.checkIT.DTO.MemberDTO.MemberRequestDTO;
import capstone.checkIT.entity.Month;
import capstone.checkIT.repository.MemberRepository;
import capstone.checkIT.repository.MonthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static capstone.checkIT.entity.enums.Role.USER;
import static capstone.checkIT.entity.enums.Status.ACTIVE;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService{

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final JwtManager jwtManager;
    private final MonthRepository monthRepository;

    @Override
    @Transactional
    public MemberResponseDTO.JoinResultDTO joinMember(MemberRequestDTO.JoinDto request) {

        memberRepository.findByEmail(request.getEmail())
                .ifPresent(member-> {throw new TempHandler(ErrorStatus.MEMBER_DUPLICATE);
                });

        Member member=Member.builder()
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .address(request.getAddress())
                .isStart(false)
                .status(ACTIVE)
                .role(USER)
                .build();



        request.getMonths().forEach(monthRequest -> {
            Month month = Month.builder()
                    .productName(monthRequest.getProductName())
                    .frequency(monthRequest.getFrequency())
                    .build();
            member.addMonth(month); // Member와 Month 간의 연관관계 설정
        });

        Member SavedMember= memberRepository.save(member);

        // 명시적 초기화
        SavedMember.getMonthList().size(); // 강제 초기화

        return MemberConverter.toJoinResultDTO(SavedMember);


    }

    public MemberResponseDTO.LoginResultDTO loginMember(MemberRequestDTO.LoginDto request){
        Member member=memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new TempHandler(ErrorStatus.LOGIN_ERROR_EMAIL));

        if(!passwordEncoder.matches(request.getPassword(), member.getPassword())){
            throw new TempHandler(ErrorStatus.LOGIN_ERROR_PW);
        }

        String accessToken=jwtManager.createAccessToken(member.getId());

        return MemberResponseDTO.LoginResultDTO.builder()
                .memberId(member.getId())
                .name(member.getName())
                .access_token(accessToken)
                .build();
    }
}
