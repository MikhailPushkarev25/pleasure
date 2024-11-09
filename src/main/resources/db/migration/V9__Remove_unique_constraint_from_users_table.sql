-- Удаление уникального ограничения на колонке username
ALTER TABLE users
DROP CONSTRAINT IF EXISTS users_username_key;