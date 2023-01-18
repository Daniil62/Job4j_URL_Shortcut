package ru.job4j.urlshortcut.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.job4j.urlshortcut.domain.model.Item;
import ru.job4j.urlshortcut.domain.model.Site;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ItemServiceTest {

    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private SiteServiceImpl siteService;

    @Test
    public void whenItemAdded() {
        Optional<Site> optionalSite = siteService.save(
                new Site("something.ru", "login", "password"));
        Optional<Item> optionalItem =
                itemService.putItem(new Item("https://something/1", "AbYrVaLg",
                optionalSite.orElse(new Site())));
        assertTrue(optionalItem.isPresent());
        Item item = optionalItem.get();
        assertTrue(item.getId() > 0);
        assertThat(item.getTotal(), is(0L));
        assertThat(item.getUrl(), is("https://something/1"));
        assertThat(item.getSite(), is(optionalSite.orElseThrow()));
    }

    @Test
    public void whenItemWithSameLinkCanNotBeAdded() {
        Optional<Site> optionalSite = siteService.save(
                new Site("something.com", "login2", "password"));
        Optional<Item> optionalItemFirst = itemService.putItem(
                new Item("https://something/2", "aByRvAlG",
                        optionalSite.orElse(new Site())));
        assertTrue(optionalItemFirst.isPresent());
        assertThrows(DataIntegrityViolationException.class, () ->
                itemService.putItem(new Item("https://something/2", "code",
                optionalSite.orElse(new Site()))));
    }

    @Test
    public void whenItemAddedThenFoundByCodeTwiceAndCheckStatistic() {
        String login = "login25";
        Optional<Site> optionalSite = siteService.save(
                new Site("something.io", login, "password"));
        String code = "1010011010";
        Item item = new Item("https://something/3", code, optionalSite.orElse(new Site()));
        itemService.putItem(item);
        checkStatistic(login, 0L);
        String url = itemService.getUrl(code).orElseThrow();
        assertEquals("https://something/3", url);
        checkStatistic(login, 1L);
        itemService.getUrl(code);
        checkStatistic(login, 2L);
    }

    @Test
    public void whenHaveRequestsFromDifferentThreadsThenGetStatistic() throws InterruptedException {
        String login = "login26";
        Optional<Site> optionalSite = siteService.save(
                new Site("some_url.com", login, "password"));
        String code = "code12345";
        Item item = new Item("https://something/4", code, optionalSite.orElse(new Site()));
        itemService.putItem(item);
        checkStatistic(login, 0L);
        Thread first = new Thread(() -> attack(code, 14));
        Thread second = new Thread(() -> attack(code, 21));
        Thread third = new Thread(() -> attack(code, 11));
        first.start();
        second.start();
        third.start();
        first.join();
        second.join();
        third.join();
        checkStatistic(login, 46L);
    }

    private void checkStatistic(String login, Long expected) {
        assertThat(siteService.findByLoginWithItems(login)
                .map(s -> s.getItems()
                        .stream()
                        .findFirst()
                        .orElseThrow()
                        .getTotal()).orElseThrow(), is(expected));
    }

    private void attack(String code, int count) {
        for (int i = 0; i < count; i++) {
            itemService.getUrl(code);
        }
    }
}