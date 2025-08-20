-- ==============================================
-- Inserts para tabla movie
-- (video_id corresponde al ID de la API MovieDB)
-- ==============================================
INSERT INTO movie (title, main_character, description, premiered_at, movie_version, image, image_bkd, video_id)
VALUES
    ('Inception', 'Dom Cobb', 'Un ladrón que roba secretos a través de los sueños debe realizar la tarea imposible: plantar una idea.', '2010-07-16', '2D', 'https://image.tmdb.org/t/p/w500/inception.jpg', 'https://image.tmdb.org/t/p/original/inception_bkd.jpg', '27205'),
    ('The Matrix', 'Neo', 'Un hacker descubre la verdad detrás de la realidad y lucha contra las máquinas.', '1999-03-31', '2D', 'https://image.tmdb.org/t/p/w500/matrix.jpg', 'https://image.tmdb.org/t/p/original/matrix_bkd.jpg', '603'),
    ('Avatar', 'Jake Sully', 'Un ex-marine se convierte en parte de la tribu Na’vi en Pandora.', '2009-12-18', '3D', 'https://image.tmdb.org/t/p/w500/avatar.jpg', 'https://image.tmdb.org/t/p/original/avatar_bkd.jpg', '19995');

-- ==============================================
-- Inserts para tabla customer
-- ==============================================
INSERT INTO customer (first_name, last_name, email, phone, address, an_adult)
VALUES
    ('Juan', 'Pérez', 'juan.perez@example.com', '555-1234', 'Calle 123, Ciudad A', TRUE),
    ('María', 'Gómez', 'maria.gomez@example.com', '555-5678', 'Avenida 45, Ciudad B', TRUE),
    ('Pedro', 'López', 'pedro.lopez@example.com', '555-8765', 'Boulevard 89, Ciudad C', FALSE);

-- ==============================================
-- Inserts para tabla users
-- ==============================================
INSERT INTO users (user_first_name, user_last_name, user_email, user_password, confirm_password)
VALUES
    ('Admin', 'Cine', 'admin@mykino.com', 'admin123', 'admin123'),
    ('Carlos', 'Mendoza', 'carlos.mendoza@mykino.com', 'carlos123', 'carlos123');

-- ==============================================
-- Inserts para tabla cinema
-- ==============================================
INSERT INTO cinema (name, address, manager, max_halls)
VALUES
    ('Cinepolis Centro', 'Av. Principal 100', 'Laura Sánchez', 5),
    ('Cinemark Norte', 'Calle Secundaria 200', 'José Ramírez', 8);

-- ==============================================
-- Inserts para tabla hall
-- ==============================================
INSERT INTO hall (capacity, occupied_seats, supported_movie_version, seat_price, cinema_id)
VALUES
    (100, 20, '2D', 80.00, 1),
    (150, 50, '3D', 120.00, 1),
    (200, 75, '2D', 90.00, 2);

-- ==============================================
-- Inserts para tabla seat
-- ==============================================
INSERT INTO seat (cinema_name, hall_id, movie_id, movie_version, movie_name, col_number, row_number, reservation_date, reservation_time, premiered_at, customer_id)
VALUES
    ('Cinepolis Centro', 1, 1, '2D', 'Inception', 5, 7, '2025-08-21', '18:00', '2010-07-16', 1),
    ('Cinepolis Centro', 1, 2, '2D', 'The Matrix', 6, 8, '2025-08-21', '21:00', '1999-03-31', 2),
    ('Cinemark Norte', 3, 3, '3D', 'Avatar', 10, 12, '2025-08-22', '20:30', '2009-12-18', 3);

-- ==============================================
-- Inserts para tabla movie_plays_in
-- ==============================================
INSERT INTO movie_plays_in (hall_id, movie_id)
VALUES
    (1, 1),  -- Inception en sala 1
    (1, 2),  -- Matrix en sala 1
    (2, 3);  -- Avatar en sala 2
