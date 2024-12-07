package capstone.checkIT.service.memberService;

import capstone.checkIT.DTO.MemberDTO.MemberRequestDTO;
import capstone.checkIT.DTO.MemberDTO.MemberResponseDTO;

public interface MemberService {
    public MemberResponseDTO.MypageDTO getMyPage(String accessToken);
    public MemberResponseDTO.MypageDTO updateMyInfo(String accessToken, MemberRequestDTO.MyDetailInfoDto myDetailInfoDto);

}
