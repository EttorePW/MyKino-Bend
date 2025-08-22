-- Sample data for MyKino PostgreSQL Database

-- Insert sample cinemas
INSERT INTO cinema (name, location) VALUES 
('CinePlex Wien', 'Wien, Österreich'),
('Mega Kino Berlin', 'Berlin, Deutschland'),
('Star Cinema München', 'München, Deutschland')
ON CONFLICT DO NOTHING;

-- Insert sample movies
INSERT INTO movie (title, main_character, description, premiered_at, movie_version, image, image_bkd, video_id) VALUES 
('Avatar: The Way of Water', 'Jake Sully', 'Jake Sully lives with his newfound family formed on the extrasolar moon Pandora. Once a familiar threat returns to finish what was previously started, Jake must work with Neytiri and the army of the Navi race to protect their home.', '2022-12-16', 'DUBBED', 'https://image.tmdb.org/t/p/w500/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg', 'https://image.tmdb.org/t/p/w1920_and_h800_multi_faces/s16H6tpK2utvwDtzZ8Qy4qm5Emw.jpg', 'o_WdFm9VuE4'),

('Top Gun: Maverick', 'Pete Mitchell', 'After thirty years, Maverick is still pushing the envelope as a top naval aviator, but must confront ghosts of his past when he leads TOP GUN''s elite graduates on a mission that demands the ultimate sacrifice from those chosen to fly it.', '2022-05-27', 'ORIGINAL', 'https://image.tmdb.org/t/p/w500/62HCnUTziyWcpDaBO2i1DX17ljH.jpg', 'https://image.tmdb.org/t/p/w1920_and_h800_multi_faces/odJ4hx6g6vBt4lBWKFD1tI8WS4x.jpg', 'qSqVVswa420'),

('Spider-Man: No Way Home', 'Peter Parker', 'Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange, the stakes become even more dangerous.', '2021-12-17', 'DUBBED', 'https://image.tmdb.org/t/p/w500/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg', 'https://image.tmdb.org/t/p/w1920_and_h800_multi_faces/14QbnygCuTO0vl7CAFmPf1fgZfV.jpg', 'JfVOs4VSpmA'),

('The Batman', 'Bruce Wayne', 'In his second year of fighting crime, Batman uncovers corruption in Gotham City that connects to his own family while facing a serial killer known as the Riddler.', '2022-03-04', 'ORIGINAL', 'https://image.tmdb.org/t/p/w500/74xTEgt7R36Fpooo50r9T25onhq.jpg', 'https://image.tmdb.org/t/p/w1920_and_h800_multi_faces/b0PlSFdDwbyK0cf5RxwDpaOJQvQ.jpg', 'mqqft2x_Aa4')
ON CONFLICT DO NOTHING;

-- Insert sample halls for each cinema
INSERT INTO hall (capacity, occupied_seats, supported_movie_version, seat_price, cinema_id, screening_times) VALUES 
-- CinePlex Wien halls
(120, 0, 'DUBBED', 12.50, 1, '["10:00", "13:00", "16:00", "19:00", "22:00"]'),
(80, 0, 'ORIGINAL', 14.00, 1, '["11:00", "14:00", "17:00", "20:00"]'),
(150, 0, 'DUBBED', 11.50, 1, '["09:30", "12:30", "15:30", "18:30", "21:30"]'),

-- Mega Kino Berlin halls  
(200, 0, 'DUBBED', 13.00, 2, '["10:30", "13:30", "16:30", "19:30", "22:30"]'),
(100, 0, 'ORIGINAL', 15.00, 2, '["11:30", "14:30", "17:30", "20:30"]'),

-- Star Cinema München halls
(90, 0, 'DUBBED', 12.00, 3, '["10:15", "13:15", "16:15", "19:15", "22:15"]'),
(110, 0, 'ORIGINAL', 14.50, 3, '["11:45", "14:45", "17:45", "20:45"]')
ON CONFLICT DO NOTHING;

-- Insert movie-hall relationships (Movie_plays_in)
INSERT INTO movie_plays_in (movie_id, hall_id) VALUES 
-- Avatar in various halls
(1, 1), (1, 3), (1, 4), (1, 6),
-- Top Gun in original language halls  
(2, 2), (2, 5), (2, 7),
-- Spider-Man in dubbed halls
(3, 1), (3, 3), (3, 4), (3, 6),
-- The Batman in original halls
(4, 2), (4, 5), (4, 7)
ON CONFLICT DO NOTHING;

-- Insert sample customers
INSERT INTO customer (first_name, last_name, email, date_of_birth, reservations) VALUES 
('Max', 'Mustermann', 'max.mustermann@email.com', '1990-05-15', '[]'),
('Anna', 'Schmidt', 'anna.schmidt@email.com', '1985-12-03', '[]'),
('Peter', 'Wagner', 'peter.wagner@email.com', '1992-08-22', '[]'),
('Lisa', 'Bauer', 'lisa.bauer@email.com', '1988-03-10', '[]')
ON CONFLICT DO NOTHING;

-- Insert sample users (for admin purposes)
INSERT INTO users (username, email, password) VALUES 
('admin', 'admin@mykino.com', 'admin123'),
('manager', 'manager@mykino.com', 'manager123')
ON CONFLICT DO NOTHING;
