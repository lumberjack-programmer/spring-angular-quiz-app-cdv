package com.lumberjack.quizappbackend.model.quiz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "categories")
public class Category implements Serializable {
    @Serial
    private static final long serialVersionUID = -4354354354543543549L;

    @Id
    private String id;
    private String categoryName;
}
