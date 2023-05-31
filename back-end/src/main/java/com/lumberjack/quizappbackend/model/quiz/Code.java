package com.lumberjack.quizappbackend.model.quiz;

import java.util.*;

public final class Code {
    public static final Map<Integer, String> codes;

    static {
        codes = new HashMap<>();
        codes.put(0, "A");
        codes.put(1, "B");
        codes.put(2, "C");
        codes.put(3, "D");
        codes.put(4, "E");
        codes.put(5, "F");
    }

    public static Set<Option> assignCode(Set<Option> options) {
        Iterator<Option> iterator = options.stream().iterator();
        int i = 0;

        while (iterator.hasNext()) {
            Option next = iterator.next();
            next.setOptionId(i);
            i++;
        }


        return options;
    }
}
