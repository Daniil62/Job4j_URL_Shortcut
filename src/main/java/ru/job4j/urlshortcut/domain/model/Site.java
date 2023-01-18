package ru.job4j.urlshortcut.domain.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Site implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String login;
    private String password;
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "site", fetch = FetchType.LAZY)
    private Set<Item> items = new HashSet<>();

    public Site(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public Site(String name, String login, String password, Set<Item> items) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.items = items;
    }

    public Site(Long id, String name, String login, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public Set<Item> getItems() {
        return Set.copyOf(items);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Site)) {
            return false;
        }
        Site site = (Site) o;
        return Objects.equals(name, site.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name) * 31;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}