CREATE TABLE messages
(
    id         SERIAL PRIMARY KEY,
    user_id    INT  NOT NULL,
    chat_id    INT  NOT NULL,
    content    TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (chat_id) REFERENCES chats (id)
);

COMMENT
ON TABLE messages IS 'Таблица сообщений';
COMMENT
ON COLUMN messages.id IS 'Уникальный идентификатор сообщения';
COMMENT
ON COLUMN messages.user_id IS 'Идентификатор пользователя, который отправил сообщение';
COMMENT
ON COLUMN messages.chat_id IS 'Идентификатор чата, к которому относится сообщение';
COMMENT
ON COLUMN messages.content IS 'Содержимое сообщения';
COMMENT
ON COLUMN messages.created_at IS 'Дата и время создания сообщения';