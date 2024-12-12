package capstone.checkIT.DTO.MemberDTO;

import capstone.checkIT.entity.enums.Role;
import capstone.checkIT.entity.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class MemberRequestDTO {
    @Setter
    @Getter
    @Builder
    public static class JoinDto{
        private String name;

        private String email;

        private String password;

        private String address;

        private Status status;

        private Role role;

        private Boolean isStart;


        @Builder.Default
        private List<MonthRequest> months = new ArrayList<>(); // 기본값 추가


        @Getter
        @Setter
        @Builder
        public static class MonthRequest {
            private String productName;

            private String productSpace;

            private List<String> week;
        }

    }

    @Getter
    @Setter
    @Builder
    public static class LoginDto{
        private String email; //사용자 이메일

        private String password; //비��번호
    }

    @Getter
    @Setter
    @Builder
    public static class MyDetailInfoDto{
        private String name;

        private String address;
    }
}
