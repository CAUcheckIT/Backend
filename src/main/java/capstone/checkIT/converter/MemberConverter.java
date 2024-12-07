package capstone.checkIT.converter;

import capstone.checkIT.DTO.MemberDTO.MemberResponseDTO;
import capstone.checkIT.entity.Member;
import capstone.checkIT.DTO.MemberDTO.MemberRequestDTO;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class MemberConverter {

    public static MemberResponseDTO.JoinResultDTO toJoinResultDTO(Member member){
        return MemberResponseDTO.JoinResultDTO.builder()
                .memberId(member.getId())
                .name(member.getName())
                .createdAt(LocalDateTime.now())
                .months(
                        member.getMonthList().stream()
                                .map(month -> new MemberResponseDTO.JoinResultDTO.MonthResponse(
                                        month.getProductName(),
                                        month.getFrequency(),
                                        month.getSentence()))
                                .collect(Collectors.toList())
                )
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
