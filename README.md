Notes
=====================

 Проект для тестирования фреймворка Vaadin и Vaadin + Docker

# Фреймворки: Kotlin, Vaadin, Spring Boot, Spring MVC, Spring Date JPA, JPA(Hibernate), Postgres, Gradle

Для запуска необходимо (один из вариантов запуска)...

Задать переменные среды, или указать их явно в application.yml

*Переменные среды:
  local postgres
      DATASOURCE_POSTGRES_URL: jdbc:postgresql://localhost:5432
      DATASOURCE_POSTGRES_USERNAME: postgres
      DATASOURCE_POSTGRES_PASSWORD: postgres
      
После чего нажать в docker-compose.yml запуск контейнера pg 
(только его, целеком services предназначено для запуска в тестовый продакшн с другими переменными среды,
 заданные для удобства в Dockerfile. В реальном продакшнзадать их ручками !!! )
 
 После успешного запуска postgres контейнера, можно делать bootRun
 
 Поле небольшого ожидания приложение нас будет ждать по адресу http://localhost:8080/
 
 Всё приложение работает.
 
 Так же в конце адресной строки (справа перед добавить закладку) будет ссылка нажав на которую, 
 можно будет запусть приложение отдельно от браузера.
 
 Возможности:
 
 CRUD для Users и Notes в удобном виде.
 
 
