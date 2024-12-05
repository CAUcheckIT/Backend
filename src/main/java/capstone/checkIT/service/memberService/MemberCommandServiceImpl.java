package capstone.checkIT.service.memberService;

import capstone.checkIT.DTO.MemberResponseDTO;
import capstone.checkIT.apipayLoad.code.status.ErrorStatus;
import capstone.checkIT.apipayLoad.handler.TempHandler;
import capstone.checkIT.config.JwtManager;
import capstone.checkIT.converter.MemberConverter;
import capstone.checkIT.entity.Member;
import capstone.checkIT.DTO.MemberRequestDTO;
import capstone.checkIT.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService{

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final JwtManager jwtManager;

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
                .isStart(request.getIsStart())
                .status(request.getStatus())
                .role(request.getRole())
                .build();
        Member SavedMember= memberRepository.save(member);

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
