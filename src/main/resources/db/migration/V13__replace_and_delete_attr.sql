-- Переименовать столбец username в full_name
ALTER TABLE users RENAME COLUMN username TO full_name;

-- Удалить столбец surname
ALTER TABLE users DROP COLUMN surname;