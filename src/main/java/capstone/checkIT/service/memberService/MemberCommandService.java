package capstone.checkIT.service.memberService;

import capstone.checkIT.DTO.MemberDTO.MemberRequestDTO;
import capstone.checkIT.DTO.MemberDTO.MemberResponseDTO;
import org.springframework.transaction.annotation.Transactional;

public interface MemberCommandService {
    @Transactional
    MemberResponseDTO.JoinResultDTO joinMember(MemberRequestDTO.JoinDto request);

    @Transactional
    MemberResponseDTO.LoginResultDTO loginMember(MemberRequestDTO.LoginDto request);
}
