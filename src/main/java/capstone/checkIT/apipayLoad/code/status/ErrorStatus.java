package capstone.checkIT.apipayLoad.code.status;


import capstone.checkIT.apipayLoad.code.BaseCode;
import capstone.checkIT.apipayLoad.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseCode {

    example(HttpStatus.BAD_REQUEST, "example4001", "예시 에러코드입니다.");




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
