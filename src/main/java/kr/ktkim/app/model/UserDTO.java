package kr.ktkim.app.model;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Keumtae Kim
 */
public class UserDTO {

    private Long id;

    @Size(max = 50)
    private String login;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    @Size(max = 50)
    private String name;

    private Set<String> authorities;

    private boolean activated = false;

    private String createdBy;

    private Date createdDate;

    private String lastModifiedBy;

    private Date lastModifiedDate;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this(user.getId(), user.getLogin(), user.getEmail(), user.getName(),
                user.getAuthorities().stream().map(Authority::getName)
                        .collect(Collectors.toSet()),
                user.getActivated(), user.getCreatedBy(), user.getCreatedDate(), user.getLastModifiedBy(), user.getLastModifiedDate());
    }

    public UserDTO(Long id, String login, String email, String name, Set<String> authorities,
                   boolean activated, String createdBy, Date createdDate, String lastModifiedBy, Date lastModifiedDate) {

        this.id = id;
        this.login = login;
        this.email = email;
        this.name = name;
        this.authorities = authorities;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.activated = activated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = createdDate;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                " email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", activated=" + activated +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", authorities=" + authorities +
                "}";
    }
}