CREATE TABLE message_status
(
    message_id INT         NOT NULL,
    status     VARCHAR(50) NOT NULL,
    timestamp  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (message_id, status),
    FOREIGN KEY (message_id) REFERENCES messages (id)
);

COMMENT
ON TABLE message_status IS 'Таблица статусов сообщений';
COMMENT
ON COLUMN message_status.message_id IS 'Идентификатор сообщения';
COMMENT
ON COLUMN message_status.status IS 'Статус сообщения';
COMMENT
ON COLUMN message_status.timestamp IS 'Время изменения статуса';