package capstone.checkIT.converter;

import capstone.checkIT.DTO.MemberResponseDTO;
import capstone.checkIT.entity.Member;
import capstone.checkIT.DTO.MemberRequestDTO;

import java.time.LocalDateTime;

public class MemberConverter {

    public static MemberResponseDTO.JoinResultDTO toJoinResultDTO(Member member){
        return MemberResponseDTO.JoinResultDTO.builder()
                .memberId(member.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Member toMember(MemberRequestDTO.JoinDto request){

        return Member.builder()
                .name(request.getName())
                .password(request.getPassword())
                .email(request.getEmail())
                .address(request.getAddress())
                .isStart(request.getIsStart())
                .status(request.getStatus())
                .role(request.getRole())
                .build();
    }
}
