CREATE TABLE attachments
(
    id         SERIAL PRIMARY KEY,
    message_id INT          NOT NULL,
    file_path  VARCHAR(255) NOT NULL,
    file_type  VARCHAR(50)  NOT NULL,
    FOREIGN KEY (message_id) REFERENCES messages (id)
);

COMMENT
ON TABLE attachments IS 'Таблица вложений';
COMMENT
ON COLUMN attachments.id IS 'Уникальный идентификатор вложения';
COMMENT
ON COLUMN attachments.message_id IS 'Идентификатор сообщения, к которому относится вложение';
COMMENT
ON COLUMN attachments.file_path IS 'Путь к файлу вложения';
COMMENT
ON COLUMN attachments.file_type IS 'Тип файла вложения';