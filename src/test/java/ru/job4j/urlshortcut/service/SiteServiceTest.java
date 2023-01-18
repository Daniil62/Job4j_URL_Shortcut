package ru.job4j.urlshortcut.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.job4j.urlshortcut.domain.model.Site;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SiteServiceTest {

    @Autowired
    private SiteServiceImpl service;

    @Test
    public void whenSiteSaved() {
        Site site = new Site("something.edu", "login11", "password");
        assertNull(site.getId());
        Optional<Site> optionalSite = service.save(site);
        assertTrue(optionalSite.isPresent());
        site = optionalSite.get();
        assertThat(optionalSite.get().getId(), is(site.getId()));
    }

    @Test
    public void whenSiteWithSameNameCanNotBeSaved() {
        Site firstSite = new Site("name.com", "login12", "password");
        Optional<Site> optionalFirst = service.save(firstSite);
        assertTrue(optionalFirst.isPresent());
        assertThrows(DataIntegrityViolationException.class, () ->
                service.save(new Site("name.com", "something", "123456789")));
    }

    @Test
    public void whenSiteSavedThenFoundByLogin() {
        String login = "login13";
        service.save(new Site("something.br", login, "password"));
        Optional<Site> optionalSite = service.findByLogin(login);
        assertTrue(optionalSite.isPresent());
        Site site = optionalSite.get();
        assertThat(site.getLogin(), is(login));
        assertThat(site.getName(), is("something.br"));
    }
}