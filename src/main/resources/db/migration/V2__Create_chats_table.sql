CREATE TABLE chats
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT
ON TABLE chats IS 'Таблица чатов';
COMMENT
ON COLUMN chats.id IS 'Уникальный идентификатор чата';
COMMENT
ON COLUMN chats.name IS 'Название чата (если есть)';
COMMENT
ON COLUMN chats.created_at IS 'Дата и время создания чата';