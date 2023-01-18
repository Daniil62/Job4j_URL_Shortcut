package ru.job4j.urlshortcut.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.urlshortcut.Job4jUrlshortcutApplication;
import ru.job4j.urlshortcut.domain.model.Item;
import ru.job4j.urlshortcut.domain.model.Site;
import ru.job4j.urlshortcut.domain.dto.ConvertResponse;
import ru.job4j.urlshortcut.domain.dto.Credentials;
import ru.job4j.urlshortcut.domain.dto.RegistrationRequest;
import ru.job4j.urlshortcut.domain.dto.Statistic;
import ru.job4j.urlshortcut.service.ItemServiceImpl;
import ru.job4j.urlshortcut.service.SiteServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Job4jUrlshortcutApplication.class)
@AutoConfigureMockMvc
public class SiteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SiteServiceImpl siteService;
    @MockBean
    private ItemServiceImpl itemService;

    @Test
    public void whenRegistrationSuccessful() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = writer.writeValueAsString(new RegistrationRequest("job4j.ru"));
        when(siteService.generateCredentials()).thenReturn(
                new Credentials("login", "password"));
        mockMvc.perform(post("/sites/registration")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestJson))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath(".login", containsInAnyOrder("login")))
                .andExpect(jsonPath(".password", containsInAnyOrder("password")));
    }

    @Test
    @WithMockUser
    public void whenConvert() throws Exception {
        String url = new ObjectMapper().writeValueAsString("https://job4j.ru/profile/exercise");
        String code = "AbYrVaLg";
        String login = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        when(siteService.findByLogin(login))
                .thenReturn(Optional.of(new Site("job4j.ru", "login,", "password")));
        when(itemService.getCodeForUrl()).thenReturn(code);
        String respConvert = new ObjectMapper().writeValueAsString(new ConvertResponse(code));
        mockMvc.perform(post("/sites/convert")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(url))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(content().json(respConvert));
    }

    @Test
    @WithMockUser
    public void whenRedirect() throws Exception {
        String url = "https://job4j.ru/profile/exercise";
        String code = "AbYrVaLg";
        when(itemService.getUrl(code)).thenReturn(Optional.of(url));
        mockMvc.perform(get("/sites/redirect/{code}", code))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("REDIRECT", url));
    }

    @Test
    @WithMockUser
    public void getStatisticAfterRequestsToDifferentURLs() throws Exception {
        Item firstItem = new Item("https://job4j.ru/profile/exercise/1", 123);
        Item secondItem = new Item("https://job4j.ru/profile/exercise/2", 110);
        String statisticJson = new ObjectMapper()
                .writeValueAsString(List.of(
                        new Statistic(firstItem.getUrl(), firstItem.getTotal()),
                        new Statistic(secondItem.getUrl(), secondItem.getTotal())));
        Site site = new Site("job4j.ru", "login", "password",
                Set.of(firstItem, secondItem));
        String login = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        when(siteService.findByLoginWithItems(login)).thenReturn(Optional.of(site));
        mockMvc.perform(get("/sites/statistic"))
                .andDo(print())
                .andExpect(content().json(statisticJson));
    }
}