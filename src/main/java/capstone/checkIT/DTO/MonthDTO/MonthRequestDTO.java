package capstone.checkIT.DTO.MonthDTO;

import capstone.checkIT.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MonthRequestDTO {
    @Setter
    @Getter
    @Builder
    public static class CreateMonthDto {

        private String productName;

        private String productSpace;

        private List<String> week;


    }
}
