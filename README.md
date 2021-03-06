# Web-prac2022
### Практикум по WEB от ИСП РАН 6 семестр
### Схема взаимодействия страниц:
![изображение](https://user-images.githubusercontent.com/37417579/160045212-61631edb-af64-44e4-9106-0949f6d4eeee.png)

### Главная страница:
  - ссылки на разделы
  - ссылка на список пользователей
  - страница для входа
  - страница для регистрации
  - ссылки на разделы
  - функция поиска раздела по имени
  - у модераторов имеется возможность удаления и добавления разделов
### Список пользователей:
  - таблица с логинами
  - функция бана пользователя для модератора
  - получение списка пользователей, в т.ч. по участию в различных разделах и по активности
### Раздел:
  - ссылки на темы
  - поиск темы по имени
  - функция добавления и удаления темы (добавлять может любой, а удалять модераторы и создатель темы)
### Тема:
  - к теме прикреплён УПОРЯДОЧЕННЫЙ по времени создания список сообщений
  - у незабаненных пользователей имеется возможность добавлять и удалять свои сообщения
  - у сообщений могут быть прикреплены файлы
### Схема базы данных:
![изображение](https://user-images.githubusercontent.com/37417579/166102666-69931d77-5ad4-4e25-b3af-3e5a351119f2.png)

### Сценарии использования:
  - Регистрация:
    - перейти на главную стр. и нажать на "регистрация"
    - заполнить необходимые поля и подтвердить изменения в БД
  - Вход:
    - перейти на главную стр. и нажать на "войти"
    - заполнить необходимые поля и подтвердить себя в БД
  - Получение списка пользователей, в т.ч. по участию в различных разделах и по активности
      - перейти на главную страницу
      - перейти к списку пользователей
      - выбрать фильтр
            - по разделам
            - по активности
  - Оставить сообщение:
      - перейти на главную страницу
      - найти нужный раздел по имени
      - в разделе найти нужную тему по имени
      - или создать тему в разделе
      - оставить сообщение в теме
  - Получение списка разделов:
      - перейти на главную страницу
      - выбрать фильтр
            - по дате создания
            - по имени
  - Получение сообщений в теме:
      - перейти на главную страницу
      - найти нужный раздел по имени
      - в разделе найти нужную тему по имени
      - получить сообщения в теме
