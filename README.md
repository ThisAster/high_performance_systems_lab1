user

```
admin
```

password

```
jkhprtrnwxwtyftvxbjplbljcsjlycgayjekqpuhclykhgrntropngzwkwzr
```

Перед запуском нужно будет очистить старые волюмы, что бы не мешали устаревшие базы данных

```bash
docker volume rm $(docker volume ls --format "{{.ID}}")
```

Эта команда удалит все волюмы на Linux машине где была запущена

Для запуска нужно собрать проект из корня мавном, а затем сбилдить и поднять образы докер композом
```bash
mvn clean package -DskipTests
docker compose up -d
```
`-DskipTests` флаг, который игнорирует тесты, можно запустить с тестами, они должны пройти все

Вторая команда собирает и поднимает все образы

Для демонтсрации можно использовать Postman сделать там запросы в соответствии с входными моделями на контроллерах

Что бы получить токен авторизации нужно залогиниться под админом, он сразу с правами супервизора, что бы добавлять пользователей

Если есть старые образы их нужно удалить
```bash
docker rmi $(docker images --format "{{.ID}}")
```
Эта комадна удаляет ВСЕ образы

### Тесты

Запуск тестов и генерация общего Jacoco-репорта происходит в 2 этапа:

1) Компиляция кода, запуск тестов и генерация Jacoco репортов для каждого модуля (`target/jacoco.exec`)

2) Слияние всех репортов в один (`target/site/jacoco-aggregate/index.html`). На этапе слияния (`mvn verify -DskipTests`) важно **не** вызывать команду clean чтобы она не удалила репорты в каждом модуле

```bash
cd lab2_rus
mvn clean package
mvn verify -DskipTests
```

Общий репорт генерируется в модуле `code-coverage`, его можно открыть в браузере, например:

`/home/username/lab2_rus/code-coverage/target/site/jacoco-aggregate/index.html`
