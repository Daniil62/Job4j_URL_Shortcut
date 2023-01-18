package ru.job4j.urlshortcut.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ConvertResponse {

    @Id
    private String code;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ConvertResponse)) {
            return false;
        }
        ConvertResponse that = (ConvertResponse) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code) * 31;
    }
}