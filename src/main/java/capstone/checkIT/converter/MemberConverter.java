package capstone.checkIT.converter;

import capstone.checkIT.entity.Member;
import capstone.checkIT.web.DTO.MemberRequestDTO;

public class MemberConverter {
    public static Member toMember(MemberRequestDTO.JoinDto request){

        return Member.builder()
                .name(request.getName())
                .password(request.getPassword())
                .address(request.getAddress())
                .isStart(request.getIsStart())
                .status(request.getStatus())
                .role(request.getRole())
                .build();
    }
}
