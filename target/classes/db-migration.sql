-- Блок обнуления (удалит БД и таблицы с такими именами, если существуют)
DROP DATABASE IF EXISTS `eventsHappy`;

-- Создание БД
CREATE DATABASE eventsHappy;

-- Переключает указатель на указанную БД
USE eventsHappy;

-- Таблица Мероприятия (имя и город)
CREATE TABLE events (
                        events_id BIGINT NOT NULL AUTO_INCREMENT,
                        events_name VARCHAR(45) NOT NULL,
                        events_city VARCHAR(45) NOT NULL,
                        PRIMARY KEY (events_id)
);

-- Таблица участиков
CREATE TABLE participants (
                              participants_id BIGINT NOT NULL AUTO_INCREMENT,
                              participants_name VARCHAR(45) NOT NULL,
                              participants_number varchar(45) not null,
                              events_id BIGINT,
                              PRIMARY KEY (participants_id),
                              FOREIGN KEY (events_id) REFERENCES events(events_id)
);

-- Таблица Тегов Мероприятий
CREATE TABLE event_tags (
                            event_tag_id BIGINT NOT NULL AUTO_INCREMENT,
                            event_tag_name VARCHAR(45) NOT NULL,
                            event_tag_author VARCHAR(45) NOT NULL,
                            PRIMARY KEY (event_tag_id)
);

-- Промежуточная таблица для связи Many-to-Many
CREATE TABLE events_event_tags (
                                   id BIGINT NOT NULL AUTO_INCREMENT,
                                   event_id BIGINT,
                                   event_tag_id BIGINT,
                                   PRIMARY KEY (id),
                                   FOREIGN KEY (event_id) REFERENCES events(events_id),
                                   FOREIGN KEY (event_tag_id) REFERENCES event_tags(event_tag_id)
);

-- Пример сгенерированных данных для таблицы events
INSERT INTO events (events_name, events_city) VALUES
                                                  ('IT конференция', 'Сан-Франциско'),
                                                  ('Фестиваль искусств', 'Париж'),
                                                  ('Выставка технологий', 'Токио');

-- Пример сгенерированных данных для таблицы participants
INSERT INTO participants (participants_name, participants_number) VALUES
                                                                      ('Александр Иванов', '123-456-7890'),
                                                                      ('Екатерина Петрова', '555-123-4567'),
                                                                      ('Дмитрий Сидоров', '777-888-9999');

-- Пример сгенерированных данных для таблицы event_tags
INSERT INTO event_tags (event_tag_name, event_tag_author) VALUES
                                                              ('Технологии', 'John Doe'),
                                                              ('Искусство', 'Jane Smith'),
                                                              ('Бизнес', 'Sam Johnson');

