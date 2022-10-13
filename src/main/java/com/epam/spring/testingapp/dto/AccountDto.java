package com.epam.spring.testingapp.dto;

import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;

@Data
@Builder
public class AccountDto {
    private int id;
    private String email;
    private String password;
    private Role firstname;
    private String surname;
    private Date birthdate;
    private boolean active;
    private boolean banned;

    public enum Role {
        USER(0), ADMIN(1);
        int index;

        Role(int index) {
            this.index = index;
        }

        private static final Logger log = LogManager.getLogger(Role.class);

        public static Role roleOf(int index) {
            for (Role role : values()) {
                if (role.index == index) {
                    return role;
                }
            }
            log.debug("Role with index= {} wasn't found. Instead used default - {}", index, USER);
            return USER;
        }

        public int getIndex() {
            return index;
        }

        @Override
        public String toString() {
            return this.name();
        }
    }
}
