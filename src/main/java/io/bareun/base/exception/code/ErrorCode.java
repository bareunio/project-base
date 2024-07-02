package io.bareun.base.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * ErrorCode는 예외 처리에 사용되는 오류 코드를 정의하는 열거형 클래스입니다.
 * 각 열거 상수는 오류 코드와 메시지를 가지고 있습니다.
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /**
     * HTTP 40x 코드 기반 5자리
     */
    BAD_REQUEST(40000, "잘못된 요청 값 입니다."),
    REQUIRED(40001, "%s 값은 필수입니다."),
    VALIDATE(40002, "%s 값이 올바르지 않습니다."),

    /**
     * HTTP 50x 코드 기반 5자리
     */
    UNKNOWN(50000, "알 수 없는 에러입니다."),
    ;

    private final int code;
    private final String message;
}
