package ru.job4j.urlshortcut.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String url;
    @Column(name = "code", unique = true)
    private String encodedUrl;
    private long total;
    @JoinColumn(name = "site_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Site site;

    public Item(String url, long total) {
        this.url = url;
        this.total = total;
    }

    public Item(String url, String encodedUrl, Site site) {
        this.url = url;
        this.encodedUrl = encodedUrl;
        this.site = site;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Item)) {
            return false;
        }
        Item item = (Item) o;
        return Objects.equals(url, item.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url) * 31;
    }
}