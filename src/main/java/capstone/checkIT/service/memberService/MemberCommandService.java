package capstone.checkIT.service.memberService;

import capstone.checkIT.web.DTO.MemberRequestDTO;
import org.springframework.transaction.annotation.Transactional;

public interface MemberCommandService {
    @Transactional
    void joinMember(MemberRequestDTO.JoinDto request);
}
