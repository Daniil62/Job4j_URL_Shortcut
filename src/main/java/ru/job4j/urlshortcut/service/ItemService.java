package ru.job4j.urlshortcut.service;

import org.springframework.dao.DataIntegrityViolationException;
import ru.job4j.urlshortcut.domain.model.Item;

import java.util.Optional;

public interface ItemService {

    public Optional<String> getUrl(String code);

    public String getCodeForUrl();

    public Optional<Item> putItem(Item item) throws DataIntegrityViolationException;
}
