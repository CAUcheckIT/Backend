package capstone.checkIT.DTO.LocationDTO;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class LocationRequestDTO {
    private BigDecimal latitude;  // 위도
    private BigDecimal longitude; // 경도
    private BigDecimal velocity;  // 속도
    private Timestamp time;  // 기록 시간
    private Timestamp startTime;  // 시작 시간
}