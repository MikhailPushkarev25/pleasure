ALTER TABLE messages
DROP CONSTRAINT messages_user_id_fkey,
    DROP CONSTRAINT messages_chat_id_fkey;

ALTER TABLE messages
    ADD CONSTRAINT messages_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    ADD CONSTRAINT messages_chat_id_fkey FOREIGN KEY (chat_id) REFERENCES chats (id) ON DELETE CASCADE;

ALTER TABLE chat_user
DROP CONSTRAINT chat_user_chat_id_fkey,
    DROP CONSTRAINT chat_user_user_id_fkey;

ALTER TABLE chat_user
    ADD CONSTRAINT chat_user_chat_id_fkey FOREIGN KEY (chat_id) REFERENCES chats (id) ON DELETE CASCADE,
    ADD CONSTRAINT chat_user_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE message_status
DROP CONSTRAINT message_status_message_id_fkey;

ALTER TABLE message_status
    ADD CONSTRAINT message_status_message_id_fkey FOREIGN KEY (message_id) REFERENCES messages (id) ON DELETE CASCADE;

ALTER TABLE attachments
DROP CONSTRAINT attachments_message_id_fkey;

ALTER TABLE attachments
    ADD CONSTRAINT attachments_message_id_fkey FOREIGN KEY (message_id) REFERENCES messages (id) ON DELETE CASCADE;

ALTER TABLE user_profile
DROP CONSTRAINT user_profile_user_id_fkey;

ALTER TABLE user_profile
    ADD CONSTRAINT user_profile_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE notifications
DROP CONSTRAINT notifications_user_id_fkey;

ALTER TABLE notifications
    ADD CONSTRAINT notifications_user_id_fkey FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;