package capstone.checkIT.service.memberService;

import capstone.checkIT.DTO.MemberDTO.MemberRequestDTO;
import capstone.checkIT.DTO.MemberDTO.MemberResponseDTO;

public interface MemberService {
    MemberResponseDTO.MypageDTO getMyPage(String accessToken);
    MemberResponseDTO.MypageDTO updateMyInfo(String accessToken, MemberRequestDTO.MyDetailInfoDto myDetailInfoDto);
    void startButton(String accessToken);

}
