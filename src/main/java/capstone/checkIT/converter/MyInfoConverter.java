package capstone.checkIT.converter;

import capstone.checkIT.DTO.MemberDTO.MemberResponseDTO;
import capstone.checkIT.entity.Device;
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

        // isDevice가 true인 첫 번째 Device 찾기
        String deviceName = member.getDeviceList().stream()
                .filter(Device::getIsDevice) // isDevice == true 조건 필터링
                .findFirst() // 첫 번째 항목 찾기
                .map(Device::getName) // deviceName 추출
                .orElse(null); // 없으면 null 반환


        return MemberResponseDTO.MypageDTO.builder()
                .memberId(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .address(member.getAddress())
                .isStart(member.getIsStart())
                .deviceName(deviceName)
                .months(monthResponses) // 변환된 MonthResponse 리스트 추가
                .build();
    }
}
