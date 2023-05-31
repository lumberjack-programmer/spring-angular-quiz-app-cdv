package com.lumberjack.quizappbackend.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class JwtRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -4354354354543543544L;
    private String username;
    private String password;

}
