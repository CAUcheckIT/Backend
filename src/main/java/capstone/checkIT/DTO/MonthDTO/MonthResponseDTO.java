package capstone.checkIT.DTO.MonthDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MonthResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateMonthDto {
        private Long id;

        private Long memberId;

        private String productName;

        private Integer frequency;
    }
}
