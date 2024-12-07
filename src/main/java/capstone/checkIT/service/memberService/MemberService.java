package capstone.checkIT.service.memberService;

import capstone.checkIT.DTO.MemberDTO.MemberResponseDTO;

public interface MemberService {
    public MemberResponseDTO.MypageDTO getMyPage(String accessToken);

}
