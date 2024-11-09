CREATE TABLE user_profile
(
    user_id    INT PRIMARY KEY,
    avatar_url VARCHAR(255),
    bio        TEXT,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

COMMENT
ON TABLE user_profile IS 'Таблица профилей пользователей';
COMMENT
ON COLUMN user_profile.user_id IS 'Идентификатор пользователя';
COMMENT
ON COLUMN user_profile.avatar_url IS 'URL-адрес аватара пользователя';
COMMENT
ON COLUMN user_profile.bio IS 'Краткая информация о пользователе';