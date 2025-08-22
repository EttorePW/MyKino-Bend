-- Insert sample cinemas
INSERT INTO cinema (name, address, manager, max_halls) VALUES 
('CinePlex Wien', 'Wien, Österreich', 'Max Manager', 5),
('Mega Kino Berlin', 'Berlin, Deutschland', 'Anna Schmidt', 8),
('Star Cinema München', 'München, Deutschland', 'Peter Wagner', 6);

-- Insert sample movies
INSERT INTO movie (title, main_character, description, premiered_at, movie_version, image, image_bkd, video_id) VALUES 
('Avatar: The Way of Water', 'Jake Sully', 'Jake Sully lives with his newfound family formed on the extrasolar moon Pandora.', '2022-12-16', 'D2D', 'https://image.tmdb.org/t/p/w500/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg', 'https://image.tmdb.org/t/p/w1920_and_h800_multi_faces/s16H6tpK2utvwDtzZ8Qy4qm5Emw.jpg', 'o_WdFm9VuE4'),
('Top Gun: Maverick', 'Pete Mitchell', 'After thirty years, Maverick is still pushing the envelope as a top naval aviator.', '2022-05-27', 'DBOX', 'https://image.tmdb.org/t/p/w500/62HCnUTziyWcpDaBO2i1DX17ljH.jpg', 'https://image.tmdb.org/t/p/w1920_and_h800_multi_faces/odJ4hx6g6vBt4lBWKFD1tI8WS4x.jpg', 'qSqVVswa420'),
('Spider-Man: No Way Home', 'Peter Parker', 'Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero.', '2021-12-17', 'R3D', 'https://image.tmdb.org/t/p/w500/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg', 'https://image.tmdb.org/t/p/w1920_and_h800_multi_faces/14QbnygCuTO0vl7CAFmPf1fgZfV.jpg', 'JfVOs4VSpmA');

-- Insert sample halls for each cinema
INSERT INTO hall (capacity, occupied_seats, supported_movie_version, seat_price, cinema_id, screening_times) VALUES 
-- CinePlex Wien halls
(120, 0, 'D2D', 12.50, 1, '["10:00", "13:00", "16:00", "19:00", "22:00"]'),
(80, 0, 'DBOX', 14.00, 1, '["11:00", "14:00", "17:00", "20:00"]'),
(150, 0, 'R3D', 11.50, 1, '["09:30", "12:30", "15:30", "18:30", "21:30"]'),

-- Mega Kino Berlin halls  
(200, 0, 'D2D', 13.00, 2, '["10:30", "13:30", "16:30", "19:30", "22:30"]'),
(100, 0, 'DBOX', 15.00, 2, '["11:30", "14:30", "17:30", "20:30"]'),
(90, 0, 'R3D', 12.50, 2, '["10:15", "13:15", "16:15", "19:15", "22:15"]'),

-- Star Cinema München halls
(110, 0, 'D2D', 12.00, 3, '["10:45", "13:45", "16:45", "19:45", "22:45"]'),
(75, 0, 'DBOX', 14.50, 3, '["11:45", "14:45", "17:45", "20:45"]');

-- Insert movie-hall relationships (Movie_plays_in)
INSERT INTO movie_plays_in (movie_id, hall_id) VALUES 
-- Avatar in D2D halls
(1, 1), (1, 4), (1, 7),
-- Top Gun in DBOX halls  
(2, 2), (2, 5), (2, 8),
-- Spider-Man in R3D halls
(3, 3), (3, 6);

-- Insert sample customers
INSERT INTO customer (first_name, last_name, email, an_adult) VALUES 
('Max', 'Mustermann', 'max.mustermann@email.com', true),
('Anna', 'Schmidt', 'anna.schmidt@email.com', true),
('Peter', 'Wagner', 'peter.wagner@email.com', false),
('Lisa', 'Bauer', 'lisa.bauer@email.com', true);
