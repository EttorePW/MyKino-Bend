-- Script para resetear la base de datos PostgreSQL en Render
-- Ejecutar este script completo en tu consola de PostgreSQL

-- ====================================
-- PASO 1: LIMPIAR BASE DE DATOS
-- ====================================

-- Eliminar todas las tablas existentes (en orden correcto para evitar errores de FK)
DROP TABLE IF EXISTS movie_plays_in CASCADE;
DROP TABLE IF EXISTS bill CASCADE;
DROP TABLE IF EXISTS seat CASCADE;
DROP TABLE IF EXISTS hall_screening_times CASCADE;  -- Tabla antigua que ya no necesitamos
DROP TABLE IF EXISTS hall CASCADE;
DROP TABLE IF EXISTS cinema CASCADE;
DROP TABLE IF EXISTS customer CASCADE;
DROP TABLE IF EXISTS movie CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- ====================================
-- PASO 2: CREAR TABLAS CON NUEVA ESTRUCTURA
-- ====================================

-- Crear tabla movie
CREATE TABLE movie (
    movie_id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    main_character VARCHAR(255),
    description TEXT,
    premiered_at DATE,
    movie_version VARCHAR(50),
    image VARCHAR(255),
    image_bkd VARCHAR(255),
    video_id VARCHAR(255)
);

-- Crear tabla customer
CREATE TABLE customer (
    customer_id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(50),
    address VARCHAR(255),
    an_adult BOOLEAN DEFAULT FALSE
);

-- Crear tabla users
CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    user_first_name VARCHAR(255),
    user_last_name VARCHAR(255),
    user_email VARCHAR(255),
    user_password VARCHAR(255),
    confirm_password VARCHAR(255)
);

-- Crear tabla cinema
CREATE TABLE cinema (
    cinema_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255),
    manager VARCHAR(255),
    max_halls INTEGER
);

-- Crear tabla hall CON NUEVA COLUMNA JSON
CREATE TABLE hall (
    hall_id BIGSERIAL PRIMARY KEY,
    capacity INTEGER,
    occupied_seats INTEGER,
    supported_movie_version VARCHAR(50),
    seat_price DECIMAL(10,2),
    screening_times JSONB DEFAULT '[]'::jsonb,  -- ✅ NUEVA COLUMNA JSON
    cinema_id BIGINT,
    CONSTRAINT fk_hall_cinema FOREIGN KEY (cinema_id) REFERENCES cinema(cinema_id)
);

-- Crear tabla seat
CREATE TABLE seat (
    seat_id BIGSERIAL PRIMARY KEY,
    cinema_name VARCHAR(255),
    hall_id INTEGER,
    movie_id INTEGER,
    movie_version VARCHAR(50),
    movie_name VARCHAR(255),
    col_number INTEGER,
    row_number INTEGER,
    reservation_date VARCHAR(50),
    reservation_time VARCHAR(50),
    premiered_at DATE,
    customer_id BIGINT,
    CONSTRAINT fk_seat_customer FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

-- Crear tabla bill
CREATE TABLE bill (
    bill_id BIGSERIAL PRIMARY KEY,
    customer_name VARCHAR(255),
    total_price DECIMAL(10,2),
    bill_date DATE,
    customer_id BIGINT,
    CONSTRAINT fk_bill_customer FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

-- Crear tabla movie_plays_in (relación many-to-many)
CREATE TABLE movie_plays_in (
    hall_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    PRIMARY KEY (hall_id, movie_id),
    CONSTRAINT fk_mpi_hall FOREIGN KEY (hall_id) REFERENCES hall(hall_id),
    CONSTRAINT fk_mpi_movie FOREIGN KEY (movie_id) REFERENCES movie(movie_id)
);

-- ====================================
-- PASO 3: INSERTAR DATOS DE EJEMPLO
-- ====================================

-- Insertar movies
INSERT INTO movie (title, main_character, description, premiered_at, movie_version, image, image_bkd, video_id)
VALUES
    ('Inception', 'Dom Cobb', 'Un ladrón que roba secretos a través de los sueños debe realizar la tarea imposible: plantar una idea.', '2010-07-16', 'D2D', 'https://image.tmdb.org/t/p/w500/inception.jpg', 'https://image.tmdb.org/t/p/original/inception_bkd.jpg', '27205'),
    ('The Matrix', 'Neo', 'Un hacker descubre la verdad detrás de la realidad y lucha contra las máquinas.', '1999-03-31', 'D2D', 'https://image.tmdb.org/t/p/w500/matrix.jpg', 'https://image.tmdb.org/t/p/original/matrix_bkd.jpg', '603'),
    ('Avatar', 'Jake Sully', 'Un ex-marine se convierte en parte de la tribu Na''vi en Pandora.', '2009-12-18', 'R3D', 'https://image.tmdb.org/t/p/w500/avatar.jpg', 'https://image.tmdb.org/t/p/original/avatar_bkd.jpg', '19995');

-- Insertar customers
INSERT INTO customer (first_name, last_name, email, phone, address, an_adult)
VALUES
    ('Juan', 'Pérez', 'juan.perez@example.com', '555-1234', 'Calle 123, Ciudad A', TRUE),
    ('María', 'Gómez', 'maria.gomez@example.com', '555-5678', 'Avenida 45, Ciudad B', TRUE),
    ('Pedro', 'López', 'pedro.lopez@example.com', '555-8765', 'Boulevard 89, Ciudad C', FALSE);

-- Insertar users
INSERT INTO users (user_first_name, user_last_name, user_email, user_password, confirm_password)
VALUES
    ('Admin', 'Cine', 'admin@mykino.com', 'admin123', 'admin123'),
    ('Carlos', 'Mendoza', 'carlos.mendoza@mykino.com', 'carlos123', 'carlos123');

-- Insertar cinemas
INSERT INTO cinema (name, address, manager, max_halls)
VALUES
    ('Cinepolis Centro', 'Av. Principal 100', 'Laura Sánchez', 5),
    ('Cinemark Norte', 'Calle Secundaria 200', 'José Ramírez', 8);

-- Insertar halls CON SCREENING TIMES EN JSON
INSERT INTO hall (capacity, occupied_seats, supported_movie_version, seat_price, screening_times, cinema_id)
VALUES
    (100, 20, 'D2D', 80.00, '["14:00", "16:30", "19:00", "21:30"]'::jsonb, 1),
    (150, 50, 'R3D', 120.00, '["15:00", "17:30", "20:00", "22:30"]'::jsonb, 1),
    (200, 75, 'D2D', 90.00, '["13:30", "16:00", "18:30", "21:00"]'::jsonb, 2);

-- ====================================
-- PASO 4: VERIFICAR LA ESTRUCTURA
-- ====================================

-- Mostrar estructura de la tabla hall
SELECT 
    column_name, 
    data_type, 
    column_default,
    is_nullable
FROM information_schema.columns 
WHERE table_name = 'hall' 
ORDER BY ordinal_position;

-- Mostrar datos de ejemplo
SELECT 
    hall_id,
    capacity,
    supported_movie_version,
    screening_times,
    jsonb_array_length(screening_times) as screening_count,
    screening_times->>0 as first_time,
    screening_times->>1 as second_time
FROM hall;

-- Mostrar resumen
SELECT 
    'Tablas creadas:' as info,
    count(*) as total_tables
FROM information_schema.tables 
WHERE table_schema = 'public';

SELECT 
    'Halls con screening times:' as info,
    count(*) as total_halls,
    avg(jsonb_array_length(screening_times)) as avg_screening_times
FROM hall;
