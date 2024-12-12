package capstone.checkIT.DTO.ProductDTO;

import capstone.checkIT.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class ProductRequestDTO {

    private Member member;

    private LocalDate date;

    private String tomorrowImg;

}
