package io.bareun.base.common.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FormatUtilsTest {

    @Test
    void 가변인자_많을때_빈_문자열_처리_성공() {
        //given
        String message = "hello world %s, %s, %s, %s, %s             ";

        //when
        String formattedMessage = FormatUtils.formatMessage(message, "1", "2");

        //then
        assertThat(formattedMessage).isEqualTo("hello world 1, 2, , ,");
    }
}