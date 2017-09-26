package kr.ktkim.app.model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Keumtae Kim
 */
public class UserDto {

    @Data
    public static class Create {
        private Long id;
        private String login;
        private String password;
        private String email;
        private String name;
        private Set<String> authorities;
        private boolean activated = false;
        private String createdBy;
        private Date createdDate;
        private String lastModifiedBy;
        private Date lastModifiedDate;
    }

    @Data
    public static class Update {
        private Long id;
        private String login;
        private String email;
        private String name;
        private Set<String> authorities;
        private boolean activated;
    }

    @Data
    public static class Response {
        private Long id;
        private String login;
        private String email;
        private String name;
        private Set<String> authorities;
        private boolean activated;

        public void setAuthorities(Set<Authority> authorities) {
            this.authorities = authorities.stream().map(Authority::getName).collect(Collectors.toSet());
        }
    }
}