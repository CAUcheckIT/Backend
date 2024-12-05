package capstone.checkIT.service.memberService;

import capstone.checkIT.entity.Member;

import java.util.Optional;

public interface MemberQueryService {
    Optional<Member> findMember(Long id);
}
