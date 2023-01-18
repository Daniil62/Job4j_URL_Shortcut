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
public class Statistic {

    @Id
    private String url;
    private long total;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Statistic)) {
            return false;
        }
        Statistic statistic = (Statistic) o;
        return Objects.equals(url, statistic.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url) * 31;
    }
}
