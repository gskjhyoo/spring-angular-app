INSERT INTO user (id, login, name, email, activated, created_by, created_date) VALUES
 ( 1, 'admin', 'admin@email.com', 'admin', true, 'system', now());

 INSERT INTO user (id, login, name, email, activated, created_by, created_date) VALUES
 ( 2, 'user', 'user1@email.com', 'kkt', true, 'system', now());


INSERT INTO authority (name) VALUES
 ('ROLE_ADMIN'), ('ROLE_USER' );

INSERT INTO user_authority (user_id,authority_name) VALUES
 ( 1, 'ROLE_ADMIN'), (1, 'ROLE_USER'), (2, 'ROLE_USER');
