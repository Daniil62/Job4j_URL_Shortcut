package ru.job4j.urlshortcut.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationRequest {

    @Id
    private String site;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RegistrationRequest)) {
            return false;
        }
        RegistrationRequest reg = (RegistrationRequest) o;
        return Objects.equals(site, reg.site);
    }

    @Override
    public int hashCode() {
        return Objects.hash(site) * 31;
    }
}