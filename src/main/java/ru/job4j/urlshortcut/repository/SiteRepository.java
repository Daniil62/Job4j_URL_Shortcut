package ru.job4j.urlshortcut.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.domain.model.Site;

import java.util.Optional;

public interface SiteRepository extends CrudRepository<Site, Integer> {

    Optional<Site> findByLogin(String login);

    @Query("SELECT s FROM Site s JOIN FETCH s.items WHERE s.login = :login")
    Optional<Site> findByLoginWithItems(String login);
}