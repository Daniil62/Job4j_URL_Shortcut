package ru.job4j.urlshortcut.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.domain.model.Item;
import ru.job4j.urlshortcut.repository.ItemRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;
    private static final int CODE_LENGTH = 7;

    @Override
    public Optional<String> getUrl(String code) {
        Optional<Item> optionalItem = repository.findByEncodedUrl(code);
        Optional<String> result = Optional.empty();
        if (optionalItem.isPresent()) {
            result = Optional.of(optionalItem.get().getUrl());
            repository.incrementCount(code);
        }
        return result;
    }

    @Override
    public String getCodeForUrl() {
        return RandomStringUtils.random(CODE_LENGTH, true, false);
    }

    @Override
    public Optional<Item> putItem(Item item) throws DataIntegrityViolationException {
        try {
            return Optional.of(repository.save(item));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("This link already exists.");
        }
    }
}