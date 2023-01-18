package ru.job4j.urlshortcut.controller;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshortcut.domain.model.Item;
import ru.job4j.urlshortcut.domain.model.Site;
import ru.job4j.urlshortcut.domain.dto.*;
import ru.job4j.urlshortcut.service.*;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sites")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class SiteController {

    private final SiteService siteService;
    private final ItemService itemService;
    private final PasswordEncoder encoder;

    @PostMapping("/registration")
    public ResponseEntity<Credentials> registration(@RequestBody RegistrationRequest request)
            throws DataIntegrityViolationException {
        Credentials credentials = siteService.generateCredentials();
        siteService.save(new Site(request.getSite(), credentials.getLogin(),
                encoder.encode(credentials.getPassword())));
        credentials.setRegistration(true);
        return ResponseEntity.status(HttpStatus.CREATED).body(credentials);
    }

    @PostMapping("/convert")
    public ResponseEntity<ConvertResponse> convert(
            @RequestBody String url,
            @CurrentSecurityContext(expression = "authentication") Authentication authentication)
            throws DataIntegrityViolationException, NoSuchObjectException {
        Optional<Site> optionalSite = siteService.findByLogin(authentication.getPrincipal().toString());
        Site site = optionalSite.orElseThrow(() -> new NoSuchObjectException("Site is invalid"));
        String code = itemService.getCodeForUrl();
        itemService.putItem(new Item(url, code, site));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ConvertResponse(code));
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<String> redirect(@PathVariable String code) {
        Optional<String> optionalUrl = itemService.getUrl(code);
        return ResponseEntity
                .status(optionalUrl.isPresent() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND)
                .header("REDIRECT", optionalUrl.orElse("")).body("");
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<Statistic>> statistic(
            @CurrentSecurityContext(expression = "authentication") Authentication authentication) {
        Optional<Site> optionalSite =
                siteService.findByLoginWithItems(authentication.getPrincipal().toString());
        return ResponseEntity.ok().body(optionalSite.map(site -> site.getItems()
                .stream()
                .map(i -> new Statistic(i.getUrl(), i.getTotal()))
                .collect(Collectors.toList())).orElseGet(List::of));
    }
}