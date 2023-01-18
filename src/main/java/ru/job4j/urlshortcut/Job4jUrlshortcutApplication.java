package ru.job4j.urlshortcut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Job4jUrlshortcutApplication {

    public static void main(String[] args) {
        SpringApplication.run(Job4jUrlshortcutApplication.class, args);
        System.out.println("Go to http://localhost:8080/sites");
    }
}