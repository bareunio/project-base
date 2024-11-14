package io.bareun.base.exception.code;

public interface ErrorCode {

    int getCode();

    String getMessage();

    default String getFormattedMessage(Object... args) {
        if (args == null || args.length == 0) {
            return getMessage().replaceAll("%[a-zA-Z]", "").strip();
        }

        return String.format(getMessage(), args);
    }
}
