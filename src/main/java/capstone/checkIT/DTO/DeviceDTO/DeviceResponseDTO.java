package capstone.checkIT.DTO.DeviceDTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeviceResponseDTO {
    private Long id;            // 장치 ID
    private String information; // 장치 정보
    private String name;        // 장치 이름
    private Long memberId;      // 사용자 ID
}
