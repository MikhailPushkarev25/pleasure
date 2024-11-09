CREATE TABLE notifications
(
    id         SERIAL PRIMARY KEY,
    user_id    INT  NOT NULL,
    message    TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

COMMENT
ON TABLE notifications IS 'Таблица уведомлений';
COMMENT
ON COLUMN notifications.id IS 'Уникальный идентификатор уведомления';
COMMENT
ON COLUMN notifications.user_id IS 'Идентификатор пользователя, которому предназначено уведомление';
COMMENT
ON COLUMN notifications.message IS 'Текст уведомления';
COMMENT
ON COLUMN notifications.created_at IS 'Дата и время создания уведомления';