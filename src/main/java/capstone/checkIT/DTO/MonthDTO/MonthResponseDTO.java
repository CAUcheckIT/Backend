package capstone.checkIT.DTO.MonthDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getMonthDto{

        private Long monthId;

        private Long memberId;

        private String productName;

        private String sentence;

        private List<String> checkDay;
    }

}
