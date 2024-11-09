CREATE TABLE chat_user
(
    chat_id INT NOT NULL,
    user_id INT NOT NULL,
    role    VARCHAR(50) DEFAULT 'member',
    PRIMARY KEY (chat_id, user_id),
    FOREIGN KEY (chat_id) REFERENCES chats (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

COMMENT
ON TABLE chat_user IS 'Таблица пользователей в чатах';
COMMENT
ON COLUMN chat_user.chat_id IS 'Идентификатор чата';
COMMENT
ON COLUMN chat_user.user_id IS 'Идентификатор пользователя';
COMMENT
ON COLUMN chat_user.role IS 'Роль пользователя в чате (по умолчанию - участник)';