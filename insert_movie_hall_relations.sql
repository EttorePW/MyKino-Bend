-- ==============================================
-- Insertar relaciones movie_plays_in para las nuevas películas
-- Asignar películas a salas basándose en movie_version
-- ==============================================

-- Salas disponibles:
-- Hall 1: D2D (capacity: 100)
-- Hall 2: R3D (capacity: 150) 
-- Hall 3: D2D (capacity: 200)

-- Insertar relaciones para películas D2D (asignar a hall 1 y hall 3)
INSERT INTO movie_plays_in (hall_id, movie_id)
SELECT 1, movie_id FROM movie 
WHERE movie_version = 'D2D' AND movie_id NOT IN (SELECT DISTINCT movie_id FROM movie_plays_in)
UNION ALL
SELECT 3, movie_id FROM movie 
WHERE movie_version = 'D2D' AND movie_id NOT IN (SELECT DISTINCT movie_id FROM movie_plays_in);

-- Insertar relaciones para películas R3D (asignar a hall 2)
INSERT INTO movie_plays_in (hall_id, movie_id)
SELECT 2, movie_id FROM movie 
WHERE movie_version = 'R3D' AND movie_id NOT IN (SELECT DISTINCT movie_id FROM movie_plays_in);

-- Insertar relaciones para películas DBOX (asignar a todas las salas para máxima compatibilidad)
INSERT INTO movie_plays_in (hall_id, movie_id)
SELECT 1, movie_id FROM movie 
WHERE movie_version = 'DBOX' AND movie_id NOT IN (SELECT DISTINCT movie_id FROM movie_plays_in)
UNION ALL
SELECT 2, movie_id FROM movie 
WHERE movie_version = 'DBOX' AND movie_id NOT IN (SELECT DISTINCT movie_id FROM movie_plays_in)
UNION ALL
SELECT 3, movie_id FROM movie 
WHERE movie_version = 'DBOX' AND movie_id NOT IN (SELECT DISTINCT movie_id FROM movie_plays_in);

-- Verificar las relaciones insertadas
SELECT 
    m.movie_id,
    m.title,
    m.movie_version,
    h.hall_id,
    h.supported_movie_version,
    c.name as cinema_name
FROM movie m
JOIN movie_plays_in mpi ON m.movie_id = mpi.movie_id
JOIN hall h ON mpi.hall_id = h.hall_id
JOIN cinema c ON h.cinema_id = c.cinema_id
ORDER BY m.movie_id, h.hall_id;
