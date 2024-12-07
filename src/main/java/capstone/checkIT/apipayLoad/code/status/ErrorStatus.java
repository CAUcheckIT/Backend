package capstone.checkIT.apipayLoad.code.status;


import capstone.checkIT.apipayLoad.code.BaseCode;
import capstone.checkIT.apipayLoad.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseCode {

    example(HttpStatus.BAD_REQUEST, "example4001", "예시 에러코드입니다."),

    // Member
    LOGIN_ERROR_EMAIL(HttpStatus.BAD_REQUEST, "LOGIN4001", "해당 사용자가 존재하지 않습니다."),
    MEMBER_DUPLICATE(HttpStatus.BAD_REQUEST, "LOGIN4002", "이미 로그인 하셨습니다."),
    LOGIN_ERROR_PW(HttpStatus.BAD_REQUEST, "LOGIN4003", "올바르지 않은 비밀번호입니다"),
    TOKEN_UNVALID(HttpStatus.UNAUTHORIZED, "TOKEN4001", "유효하지 않은 토큰입니다"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN4002", "토큰이 만료되었습니다"),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "TOKEN4003", "인증이 필요한 요청입니다"),
    TOKEN_UNKNOWN_ERROR(HttpStatus.UNAUTHORIZED, "TOKEN500", "토큰이 존재하지 않습니다.");

    // Device

    // Location

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .isSuccess(false)
                .httpStatus(httpStatus)
                .code(code)
                .message(message)
                .build();
    }
}
