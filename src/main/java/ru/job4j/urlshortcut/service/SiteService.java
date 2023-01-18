package ru.job4j.urlshortcut.service;

import org.springframework.dao.DataIntegrityViolationException;
import ru.job4j.urlshortcut.domain.dto.Credentials;
import ru.job4j.urlshortcut.domain.model.Site;

import java.util.Optional;

public interface SiteService {

    Optional<Site> save(Site site) throws DataIntegrityViolationException;

    Credentials generateCredentials();

    Optional<Site> findByLogin(String login);

    Optional<Site> findByLoginWithItems(String login);
}
