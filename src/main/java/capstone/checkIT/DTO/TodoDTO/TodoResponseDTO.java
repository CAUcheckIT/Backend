package capstone.checkIT.DTO.TodoDTO;

import capstone.checkIT.entity.Product;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


public class TodoResponseDTO {

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TomorrowResponse {

        private Long id;

        private Long memberId;

        private LocalDate date;

        private List<ProductResponse> products;

        @Getter
        @Setter
        @AllArgsConstructor
        public static class ProductResponse {

            private Long productId;

            private String productName;
        }
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TodayResponse {
        private Long id;
        private Long memberId;
        private LocalDate date;
        private List<Product> productList;
    }
}
