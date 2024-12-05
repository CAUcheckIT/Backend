package capstone.checkIT.web.DTO;

import capstone.checkIT.entity.enums.Role;
import capstone.checkIT.entity.enums.Status;
import lombok.Getter;
import lombok.Setter;

public class MemberRequestDTO {
    @Setter
    @Getter
    public static class JoinDto{
        String name;

        String password;

        String address;

        Status status;

        Role role;

        Boolean isStart;

    }
}
