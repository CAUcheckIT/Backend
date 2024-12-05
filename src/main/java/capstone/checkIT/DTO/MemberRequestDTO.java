package capstone.checkIT.DTO;

import capstone.checkIT.entity.enums.Role;
import capstone.checkIT.entity.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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

    }

    @Getter
    @Setter
    @Builder
    public static class LoginDto{
        private String email; //사용자 이메일

        private String password; //비��번호
    }
}
