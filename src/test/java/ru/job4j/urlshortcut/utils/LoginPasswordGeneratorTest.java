package ru.job4j.urlshortcut.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.urlshortcut.domain.dto.Credentials;
import ru.job4j.urlshortcut.utils.generator.RandomUtilsCredentialsGenerator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoginPasswordGeneratorTest {

    @Autowired
    private RandomUtilsCredentialsGenerator generator;

    @Test
    public void generate() {
        Credentials expected = generator.generate();
        assertNotNull(expected);
        assertThat(expected.getLogin().length(), is(8));
        assertThat(expected.getPassword().length(), is(11));
        assertFalse(expected.isRegistration());
    }
}