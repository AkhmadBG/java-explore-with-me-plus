--таблица для запуска приложения, с пустым файлом не запускается
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255)
    );