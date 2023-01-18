package ru.job4j.urlshortcut.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.urlshortcut.domain.model.Item;

import java.util.Optional;

public interface ItemRepository extends CrudRepository<Item, Integer> {

    Optional<Item> findByEncodedUrl(String code);

    @Modifying
    @Transactional
    @Query("UPDATE Item i SET i.total = total + 1 WHERE i.encodedUrl = :code")
    void incrementCount(String code);
}