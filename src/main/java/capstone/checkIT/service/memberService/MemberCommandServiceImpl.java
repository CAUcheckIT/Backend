package capstone.checkIT.service.memberService;

import capstone.checkIT.converter.MemberConverter;
import capstone.checkIT.entity.Member;
import capstone.checkIT.web.DTO.MemberRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService{

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void joinMember(MemberRequestDTO.JoinDto request) {

        Member newMember = MemberConverter.toMember(request);

        newMember.encodePassword(passwordEncoder.encode(request.getPassword()));
    }
}
