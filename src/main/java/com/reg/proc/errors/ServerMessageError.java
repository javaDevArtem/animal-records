package com.reg.proc.errors;

import java.time.LocalDateTime;

public record ServerMessageError(
        String message,
        String detailedMessage,
        LocalDateTime errorTime
) {
}
