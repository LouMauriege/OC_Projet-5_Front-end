INSERT INTO USERS (last_name, first_name, admin, email, password, created_at, updated_at)
VALUES ('userDBLastName', 'userDBLastName', true, 'emailDB@mail.com', 'passwdDB', '2024-08-17T17:33:46.105399', '2025-01-17T17:33:46.105399');
INSERT INTO USERS (last_name, first_name, admin, email, password, created_at, updated_at)
VALUES ('Gumble', 'Barney', false, 'barneygumbel@mail.com', 'passwd2', '2024-11-17T17:33:46.105399', '2025-01-17T17:33:46.105399');

INSERT INTO TEACHERS (first_name, last_name, created_at, updated_at)
VALUES ('teacherDBFirstName', 'teacherDBLastName', '2024-08-17T17:33:46.105399', '2025-01-17T17:33:46.105399');

INSERT INTO SESSIONS (name, description, date, teacher_id)
VALUES ('Session From DB', 'Session From Database', now(), 1);