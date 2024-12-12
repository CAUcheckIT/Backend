package capstone.checkIT.DTO.MemberDTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class MemberResponseDTO {
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinResultDTO{
        private Long memberId;
        private String name;
        private String userUrl;
        private LocalDateTime createdAt;
        private List<MonthResponse> months;

        @Getter
        @Setter
        @AllArgsConstructor
        public static class MonthResponse {
            private String productName;

            private Integer frequency;

            private String sentence;
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResultDTO{

        private Long memberId;

        private String name;

        private String access_token;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MypageDTO{
        private Long memberId;

        private String name;

        private String email;

        private String address;

        private Boolean isStart;

        private String deviceName;

        private List<MonthResponse> months;

        @Getter
        @Setter
        @AllArgsConstructor
        public static class MonthResponse {
            private String productName;

            private Integer frequency;

            private String sentence;
        }
    }
}
