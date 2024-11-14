package io.bareun.base.exception.code;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorCodeTest {

    @ParameterizedTest
    @MethodSource
    void 포맷팅_문자를_무시한다(ErrorCode errorCode) {
        //when
        String message = errorCode.getFormattedMessage();

        //then
        System.out.println(message);
        assertThat(message).doesNotContain("%s");
    }

    static Stream<ErrorCode> 포맷팅_문자를_무시한다() {
        return Stream.of(BaseErrorCode.REQUIRED, BaseErrorCode.VALIDATE);
    }
}