package capstone.checkIT.DTO.DeviceDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceRequestDTO {
    private String information; // 장치 정보
    private String name;        // 장치 이름
    private boolean isLogin;
}
