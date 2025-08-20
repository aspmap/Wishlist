# Wishlist
Wishlist - социальная сеть, ориентированная на обмен фотографиями, видео, создание вишлистов, TODO-задач, прослушивание музыки и многое другое (веб-версия)

Адрес в сети интернет: https://handsapp.top

**Старт проекта:** 19 августа 2025 г.

:computer: **Требования к софту:**
1. Spring Framework
2. Apache Maven 3.8.3
3. Apache Tomcat 9.0.37
4. Java 15.0.1
5. PostgreSQL 12
6. Apache Kafka 3.3.1
7. Логирование Slf4j-log4j

:computer: **Разворачивание и запуск проекта:**
1. Клонируем проект: git clone https://github.com/aspmap/HandsApp.git
2. Создаем БД в PostgreSQL и запускаем скрипт HandsAppDB.sql для создания всех необходимых таблиц и их первичного наполнения
3. Открываем проект в IntelliJ IDEA и в переменных окружения указываем имя и пароль от вашей БД, имя БД, параметры Oauth2.0 (переменные окружения указаны ниже)
4. Настраиваем Tomcat
5. Указываем переменные окружения
6. Запускаем приложение

:computer: **Переменные окружения:**

Oauth2.0 (application.properties)

spring.security.oauth2.client.registration.google.client-id = ${CLIENT_ID}

spring.security.oauth2.client.registration.google.client-secret = ${CLIENT_SECRET}

spring.security.oauth2.client.registration.google.scope = ${SCOPE}


DB (application.properties)

db.postgres.url = ${DB_URL}

db.postgres.login = ${DB_LOGIN}

db.postgres.password = ${DB_PASSWORD}

S3 (Yandex) (application.properties)

application.bucket.name = ${BUCKET_NAME_S3}

cloud.aws.credentials.access-key = ${ACCESS_KEY_S3}

cloud.aws.credentials.secret-key = ${SECRET_KEY_S3}

cloud.aws.region.static = ${REGION_S3}



:white_check_mark: **Что реализовано:**
1. Регистрация пользователя
2. Авторизация и аутентификация пользователя
3. Создание поста
4. Редактирование поста
5. Удаление поста
6. Добавление/удаление комментариев к постам
7. Просмотр постов (3 варианта): плитка, список, просмотр одного поста
8. Редактирование профиля
9. Добавление аватарки в профиле
10. Подсчет количества постов у пользователя
11. Изменение размера изображения
12. Обрезка изображения
13. Форма обратной связи
14. Аватарки пользователей в комментариях
15. Просмотр страницы другого пользователя
16. Подписка на пользователей
17. Отписка от пользователей
18. Страница с постами по подписке
19. Тексты по умолчанию при отсутствии подписок и постов
20. Лайки
21. Добавление видео в формате mp4, mov
22. Поиск пользователей
23. Поиск постов по тегам
24. QR-код
25. REST-API
26. Реализована интернационализация (локализация) - Русский и Английский языки
27. Удаление профиля пользователя
28. Подписка на пользователей в рекомендациях
29. Форма обратной связи (переделана отправка сообщений через Kafka + отправка через REST API в Kafka)
30. Простой чат между пользователями
31. Просмотр постов, которым были поставлены лайки
32. Поиск связей, основанный на теории шести рукопожатий (применение алгоритма поиска в ширину). Осуществляется поиск пользователя и выводится наглядная визуализация связей на странице
33. Авторизация через Google
34. Работа приложения по протоколу https
35. Создание поста с использованием хранилища S3 (Yandex)
36. Поиск пользователей по E-Mail
37. Сообщение о неверно введенных логине и пароле
38. Проверка длины пароля при регистрации
39. Заголовки страниц
40. Сообщение о превышение допустимого размера файла при загрузке
41. Несколько простых пробных Unit-тестов
42. Пробная собственная библиотека CheckObjectsForNull
43. Закрытый и активный профиль
44. Время последнего и предыдущего посещения
45. Получение JWT-токена, авторизация и аутентификация пользователя с помощью полученного токена, получение постов текущего пользователя через HandsApp API (https://github.com/aspmap/HandsApp_API) с помощью полученного токена
46. Пагинация постов
47. Бесконечная прокрутка постов
48. Представление (View) в БД для списка рекомендаций 
49. Галочка о прочтении в чате, количество непрочитанных сообщений и чатов
50. Карта смайлов
51. Формирование архива пользователя (многопоточность)
52. Отправка фото в чате
53. Динамическое обновление счетчика непрочитанных чатов в верхнем меню
54. Сортировка диалогов по дате последнего сообщения
55. Дата последнего сообщения в списке диалогов
56. Раздел "Моя музыка"
57. Раздел "Мой TODO-лист"
58. Раздел "Мой вишлист"

:abcd: **REST-API:**

Используется Basic-Auth

> Выборка постов по ключевому слову

GET https://handsapp.top/api/post?search=москва


> Выборка всех постов

GET https://handsapp.top/api/post


> Поиск поста по ID

GET http://handsapp.top/api/post/284


> Создание поста

POST https://handsapp.top/api/post/

> Пример запроса

```json
{

    "photo": "gfpdohum.png",
    
    "extFile": "png",
    
    "content": "Test API create",
    
    "createdAt": "2022-06-12 02:18:58"
}
```

> Редактирование поста

PUT https://handsapp.top/api/post/294

Пример запроса

```json
{

    "photo": "gfpdohum.png",
    
    "extFile": "png",
    
    "content": "Test API edit",
    
    "createdAt": "2022-06-12 02:18:59"
}
```

> Удаление поста

DELETE https://handsapp.top/api/post/295


:abcd: **CI/CD:**
Настроено автоматическое обновление, начиная с коммита и заканчивая деплоем приложения на хостинге с помощью Jenkins. 


:iphone: **Внешний вид приложения на текущий момент:**

> Страница авторизации

![login](https://github.com/user-attachments/assets/ebec37c4-0ba8-4613-a5c4-f29895360173)



> Страница регистрации

![error_3](https://github.com/user-attachments/assets/df5cb42b-2f40-4cf5-a77a-cf9c53e70d12)



> Страница пользователя без подписок

![pod_3](https://github.com/user-attachments/assets/f00a153c-c085-4425-8108-1404128c83b2)



> Создание и публикация поста

![post_1](https://github.com/user-attachments/assets/d21164aa-560e-4136-b573-21d1a243357c)

![post_2](https://github.com/user-attachments/assets/4275e825-b872-45fd-95c8-f0fe3c751593)

![post_3](https://github.com/user-attachments/assets/f9bdf759-783c-4cca-8a62-c3f875924255)


> Страница с постами текущего пользователя

![main](https://github.com/user-attachments/assets/0c660d91-bc0f-4c39-b4c1-e361a1ba9ed1)



> Изменение поста

![edit_post](https://github.com/user-attachments/assets/37e79c32-48a3-41ca-a4fe-3dcea49c7e30)




> Страница пользователя на которого можно подписаться или отписаться

![profile_2](https://github.com/user-attachments/assets/2811011f-0a89-43e8-ae03-ceb8ceeeeb47)



> Страница с подписчиками

![pod_2](https://github.com/user-attachments/assets/88066005-7afa-4b17-a967-64a00e351b47)



> Страница с подписками

![pod_1](https://github.com/user-attachments/assets/b8870b57-3761-4b37-a315-667590a85252)



> Поиск пользователей и постов по тегам

![search](https://github.com/user-attachments/assets/80b575e9-1b4c-4177-855e-6b464348aa11)



> Страница c результатами поиска связей

![handshakes_search_1](https://github.com/user-attachments/assets/f286392c-2719-4234-829d-8ab9f452e6af)

![handshakes_search_2](https://github.com/user-attachments/assets/223e5a9b-a734-468f-9e39-b9c12ba52f29)

![handshakes_search_3](https://github.com/user-attachments/assets/8c716e7f-86fc-4fe0-aca2-f61f69782964)


> Редактирование профиля

![profile](https://github.com/user-attachments/assets/5b46318c-1537-4b72-9482-fba67d322740)


> Архив профиля

![archive](https://github.com/user-attachments/assets/f40c83bb-aac4-443f-91f2-9e8c62821375)


> Удаление профиля

![delete_profile](https://github.com/user-attachments/assets/cdb0443e-171c-4f5c-b590-afa5a4b7e43b)



> QR-код страницы пользователя

![qr](https://github.com/user-attachments/assets/1b73bab6-c16d-43bb-ac22-61a69391da4b)



> Обмен сообщениями

![dialogs_2](https://github.com/user-attachments/assets/d49a2505-d343-431f-8291-852b6d98bbe4)



> Уведомление о непрочтенных сообщениях и чатах

![dialogs_1](https://github.com/user-attachments/assets/ce772261-7d48-4f8e-940b-5c718be3c4f3)



> Отправка фото в чате

![dialogs_3](https://github.com/user-attachments/assets/a3a31cb9-ce56-44d5-8934-13b8a073be0c)



> Раздел "Мои лайки"

![mylikes](https://github.com/user-attachments/assets/c613ea7d-51bb-49cf-ba4b-42100915cc4b)



> Закрытый профиль

![close_profile](https://github.com/user-attachments/assets/e0598655-2b8f-4b23-b1f6-9b3cd0c22698)



> Пагинация постов

![pagination](https://github.com/user-attachments/assets/ff264d26-12e8-486d-b59a-f53fb9906216)



> Карта смайлов

![smiles](https://github.com/user-attachments/assets/7f2041a6-6351-4b4f-a079-c5ca562ad276)



> TODO-задачи

![todo_1](https://github.com/user-attachments/assets/151dbc95-adda-4590-a23e-1c69419ee531)

![todo_2](https://github.com/user-attachments/assets/6f3ec7a3-cd99-4764-bea4-e87fe7c59db5)

![todo_3](https://github.com/user-attachments/assets/646360e1-5156-4fd1-b4be-5fe167ff5119)


> Вишлисты

![wishlist_1](https://github.com/user-attachments/assets/ce131983-0d0a-4022-91ba-4d7d59ce1216)

![wishlist_2](https://github.com/user-attachments/assets/f8424a2a-11d7-4791-9b41-14703a793708)

![wishlist_3](https://github.com/user-attachments/assets/fd013d23-0c4b-408c-adc5-ce7b524a519a)

![wishlist_4](https://github.com/user-attachments/assets/3e3c2601-6a3b-4485-ab48-0bf2c548d36a)

![wishlist_5](https://github.com/user-attachments/assets/eb12030c-ef2f-405a-8a6f-8c09b3a3e350)

![wishlist_6](https://github.com/user-attachments/assets/1de7fc42-47e4-4cb6-8a38-dfd37c7a58c8)



> Музыка

![music_1](https://github.com/user-attachments/assets/d79fb708-9387-4d24-86ec-8e48d48a0b21)

![music_2](https://github.com/user-attachments/assets/c60aaea2-886c-490e-b726-52394cd2d075)

![music_3](https://github.com/user-attachments/assets/1390f06f-5b30-460b-a1bb-442ed9a48af5)

![music_4](https://github.com/user-attachments/assets/7d67019b-2bb9-451d-bb29-ce59f4971678)

![music_5](https://github.com/user-attachments/assets/ba6c2e79-ee2c-4b14-af14-81685bc8721a)



> Информационные страницы

![error_1](https://github.com/user-attachments/assets/2746bfcd-e86e-4214-b268-15bb0325b11b)

![error_2](https://github.com/user-attachments/assets/6b5f9308-9b2f-4eec-b56b-c9c43799229d)


