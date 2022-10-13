package com.epam.spring.testingapp.dto;

import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@Data
@Builder
public class TestDto {
    private int id;
    private int subjectId;
    private String name;
    private int duration;
    private Difficult difficult;

    public enum Difficult {
        VERY_EASY,
        EASY,
        MEDIUM,
        HARD,
        IMPOSSIBLE;

        private static final Logger log = LogManager.getLogger(Difficult.class);

        public static Difficult difficultOf(int difficult) {
            for (Difficult diff : values()) {
                if (diff.ordinal() == difficult) {
                    return diff;
                }
            }
            log.debug("Difficult with id= {} wasn't found. Instead used default - {}", difficult, VERY_EASY);
            return VERY_EASY;
        }

        @Override
        public String toString() {
            return this.name();
        }
    }

}
