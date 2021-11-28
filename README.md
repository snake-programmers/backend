# Бег
Технология - `Spring Boot`

Хочет для работы БД PostGIS

## Конфига (`application.yaml`)
```yaml
spring:
  datasource:
    url: jdbc:postgresql://172.17.0.3:5432/postgres # Сюда креды к постгису
    username: postgres
    password: mysecretpassword
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    database-platform: org.hibernate.spatial.dialect.postgis.PostgisDialect
here:
  api_key: # Сюда ключ HERE API
```
