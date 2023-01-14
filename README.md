# Job4j_URL_Shortcut
## Тестовый проект курса **[Job4j](https://job4j.ru/)**, уровня "Middle", раздела Spring.
___
### Функционал приложения:
   - Регистрация сайта.
     - Сервисом могут пользоваться разные сайты. Каждому сайту выдается пару пароль и логин.
     
       ![x](https://github.com/Daniil62/Job4j_URL_Shortcut/blob/master/readme-img/job4j_url_shortcut_registration.png)
___
   - Авторизация.
     - JWT. Пользователь отправляет POST запрос с login и password и получает ключ.
     
       ![x](https://github.com/Daniil62/Job4j_URL_Shortcut/blob/master/readme-img/job4j_url_shortcut_login.png)
___
   - Регистрация URL.
     - Поле того, как пользователь зарегистрировал свой сайт он может отправлять на сайт ссылки и получать преобразованные ссылки.
     
       ![x](https://github.com/Daniil62/Job4j_URL_Shortcut/blob/master/readme-img/job4j_url_shortcut_convert.png)
       ![x](https://github.com/Daniil62/Job4j_URL_Shortcut/blob/master/readme-img/job4j_url_shortcut_convert2.png)
___
   - Переадресация (выполняется без авторизации).
     - Сайт отправляет ссылку с кодом в ответ возвращается ассоциированный адрес и статус 302.
     
       ![x](https://github.com/Daniil62/Job4j_URL_Shortcut/blob/master/readme-img/job4j_url_shortcut_redirect.png)
       ![x](https://github.com/Daniil62/Job4j_URL_Shortcut/blob/master/readme-img/job4j_url_shortcut_redirect2.png)
___
   - Статистика.
     - В сервисе считается количество вызовов каждого адреса. Увеличение счетчика производится в базе данных. По сайту можно получить статистку всех адресов и количество вызовов этого адреса.
     
       ![x](https://github.com/Daniil62/Job4j_URL_Shortcut/blob/master/readme-img/job4j_url_shortcut_statistic.png)
___
### Требования:
- Java 17
- Maven 3.8.1
- PostrgeSQL 14.4
___
### Запуск:
- ```mvn clean package -DskipTests=true```
- ```java -jar job4j_urlshortcut-0.0.1```
### Используемые библиотеки и технологии:
- Production:
  - Spring Boot
    - Web
    - Security
    - JPA
  - PostgreSQL
  - JWT
  - Liquibase
  - Lombok
___
- Test:       
   - Spring Boot Test
     - Security Test
   - H2 Database
___
   