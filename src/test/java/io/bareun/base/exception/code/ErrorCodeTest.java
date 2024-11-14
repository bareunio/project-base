package io.bareun.base.exception.code;

import io.bareun.base.exception.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class ErrorCodeTest {

    @ParameterizedTest
    @MethodSource
    void 포맷팅_문자를_무시한다(ErrorCode errorCode, String expectedMessage) {
        //when
        Assertions.assertThatThrownBy(() -> {
            throw new BusinessException(errorCode);
        }).hasMessage(expectedMessage);
    }

    static Stream<Arguments> 포맷팅_문자를_무시한다() {
        return Stream.of(
                Arguments.of(BaseErrorCode.REQUIRED, "값은 필수입니다."),
                Arguments.of(BaseErrorCode.VALIDATE, "값이 올바르지 않습니다.")
        );
    }
}