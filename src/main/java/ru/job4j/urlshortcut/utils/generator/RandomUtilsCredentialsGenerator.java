package ru.job4j.urlshortcut.utils.generator;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;
import ru.job4j.urlshortcut.domain.dto.Credentials;

@Component
public class RandomUtilsCredentialsGenerator implements CredentialsGenerator {

    private final static int LOGIN_LENGTH = 8;
    private final static int PASSWORD_LENGTH = 11;

    @Override
    public Credentials generate() {
        String login = RandomStringUtils.random(LOGIN_LENGTH, true, false);
        String password = RandomStringUtils.random(PASSWORD_LENGTH, true, true);
        return new Credentials(login, password);
    }
}