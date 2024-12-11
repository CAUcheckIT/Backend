package capstone.checkIT.service.memberService;

import capstone.checkIT.DTO.MemberDTO.MemberRequestDTO;
import capstone.checkIT.DTO.MemberDTO.MemberResponseDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface MemberCommandService {
    @Transactional
    MemberResponseDTO.JoinResultDTO joinMember(MemberRequestDTO.JoinDto request);
    String uploadImage (String directory, MultipartFile image);
    @Transactional
    MemberResponseDTO.LoginResultDTO loginMember(MemberRequestDTO.LoginDto request);
}
