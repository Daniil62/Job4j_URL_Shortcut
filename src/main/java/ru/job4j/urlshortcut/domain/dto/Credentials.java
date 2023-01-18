package ru.job4j.urlshortcut.domain.dto;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Credentials {

    @Id
    private String login;
    private String password;
    private boolean registration;

    public Credentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Credentials)) {
            return false;
        }
        Credentials credentials = (Credentials) o;
        return Objects.equals(login, credentials.login)
                && Objects.equals(password, credentials.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login) * 31 + Objects.hash(password);
    }
}