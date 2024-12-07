package capstone.checkIT.converter;

import capstone.checkIT.DTO.MemberDTO.MemberResponseDTO;
import capstone.checkIT.entity.Member;
import capstone.checkIT.entity.Month;

import java.util.List;
import java.util.stream.Collectors;

public class MyInfoConverter {
    public static MemberResponseDTO.MypageDTO toMyInfoResponseDTO(Member member){

        // member.getMonthList()를 순회하여 MonthResponse로 변환
        List<MemberResponseDTO.MypageDTO.MonthResponse> monthResponses = member.getMonthList().stream()
                .map(month -> new MemberResponseDTO.MypageDTO.MonthResponse(
                        month.getProductName(), // Month의 productName
                        month.getFrequency(),   // Month의 frequency
                        month.getSentence()     // Month의 sentence
                ))
                .toList();

        return MemberResponseDTO.MypageDTO.builder()
                .memberId(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .address(member.getAddress())
                .isStart(member.getIsStart())
                .months(monthResponses) // 변환된 MonthResponse 리스트 추가
                .build();
    }
}
