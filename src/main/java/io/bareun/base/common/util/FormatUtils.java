package io.bareun.base.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class FormatUtils {

    private static final Pattern FORMAT_SPECIFIER_PATTERN = Pattern.compile("%[a-zA-Z]");

    /**
     * 메시지와 가변인자를 받아 부족한 경우 빈 문자열로 채운 후 포맷팅된 메시지를 반환합니다.
     *
     * @param message 포맷팅할 메시지
     * @param args    가변인자
     * @return 포맷팅된 메시지
     */
    public static String formatMessage(String message, Object... args) {
        int specifierCount = countFormatSpecifiers(message);

        Object[] filledArgs = fillMissingArgs(args, specifierCount);

        return String.format(message, filledArgs).strip();
    }

    /**
     * 메시지에서 포맷팅 문자의 개수를 계산합니다.
     *
     * @param message 포맷팅할 메시지
     * @return 포맷팅 문자의 개수
     */
    private static int countFormatSpecifiers(String message) {
        Matcher matcher = FORMAT_SPECIFIER_PATTERN.matcher(message);
        int count = 0;

        while (matcher.find()) {
            count++;
        }

        return count;
    }

    /**
     * 가변인자를 채워 부족한 값을 빈 문자열로 채웁니다.
     *
     * @param args           기존 가변인자
     * @param requiredLength 필요한 가변인자 개수
     * @return 채워진 가변인자 배열
     */
    private static Object[] fillMissingArgs(Object[] args, int requiredLength) {
        return IntStream.range(0, requiredLength)
                .mapToObj(i -> getObject(args, i))
                .toArray();
    }

    private static Object getObject(Object[] args, int i) {
        if (i < args.length) {
            return args[i];
        }

        return "";
    }
}
