package com.lumberjack.quizappbackend.model;

import java.io.Serial;
import java.io.Serializable;

public record JwtResponse(String jwtToken) implements Serializable {
    @Serial
    private static final long serialVersionUID = -4354354354543543545L;
}
