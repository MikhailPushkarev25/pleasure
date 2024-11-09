CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT
ON TABLE users IS 'Таблица пользователей';
COMMENT
ON COLUMN users.id IS 'Уникальный идентификатор пользователя';
COMMENT
ON COLUMN users.username IS 'Имя пользователя, уникальное';
COMMENT
ON COLUMN users.password IS 'Пароль пользователя';
COMMENT
ON COLUMN users.created_at IS 'Дата и время создания записи';