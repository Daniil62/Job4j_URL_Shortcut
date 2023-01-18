package ru.job4j.urlshortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.urlshortcut.domain.model.Site;
import ru.job4j.urlshortcut.domain.dto.Credentials;
import ru.job4j.urlshortcut.repository.SiteRepository;
import ru.job4j.urlshortcut.utils.generator.CredentialsGenerator;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SiteServiceImpl implements SiteService, UserDetailsService {

    private final SiteRepository siteRepository;
    private final CredentialsGenerator credentialsGenerator;

    @Override
    public Optional<Site> save(Site site) throws DataIntegrityViolationException {
        try {
            return Optional.of(siteRepository.save(site));
        } catch (Exception e) {
            throw new DataIntegrityViolationException(
                            String.format("Site with name %s already registered", site.getName()));
        }
    }

    @Override
    public Credentials generateCredentials() {
        Credentials result = credentialsGenerator.generate();
        if (siteRepository.findByLogin(result.getLogin()).isPresent()) {
            generateCredentials();
        }
        return result;
    }

    @Override
    public Optional<Site> findByLogin(String login) {
        return siteRepository.findByLogin(login);
    }

    @Override
    public Optional<Site> findByLoginWithItems(String login) {
        return siteRepository.findByLoginWithItems(login);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Site> result = siteRepository.findByLogin(login);
        if (result.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Site with login %s not found", login));
        }
        return result.get();
    }
}