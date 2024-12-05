package capstone.checkIT.service.memberService;

import capstone.checkIT.DTO.MemberRequestDTO;
import capstone.checkIT.DTO.MemberResponseDTO;
import capstone.checkIT.entity.Member;
import org.springframework.transaction.annotation.Transactional;

public interface MemberCommandService {
    @Transactional
    MemberResponseDTO.JoinResultDTO joinMember(MemberRequestDTO.JoinDto request);

    @Transactional
    MemberResponseDTO.LoginResultDTO loginMember(MemberRequestDTO.LoginDto request);
}
