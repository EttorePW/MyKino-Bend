-- Insert sample cinemas first
INSERT INTO cinema (cinema_id, name, address, manager, max_halls) VALUES 
(1, 'CinePlex Wien', 'Wien, Österreich', 'Max Manager', 5),
(2, 'Mega Kino Berlin', 'Berlin, Deutschland', 'Anna Schmidt', 8),
(3, 'Star Cinema München', 'München, Deutschland', 'Peter Wagner', 6);

-- Insert sample movies
INSERT INTO movie (movie_id, title, main_character, description, premiered_at, movie_version, image, image_bkd, video_id) VALUES 
(1, 'Avatar: The Way of Water', 'Jake Sully', 'Jake Sully lives with his newfound family formed on the extrasolar moon Pandora.', '2022-12-16', 'D2D', 'https://image.tmdb.org/t/p/w500/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg', 'https://image.tmdb.org/t/p/w1920_and_h800_multi_faces/s16H6tpK2utvwDtzZ8Qy4qm5Emw.jpg', 'o_WdFm9VuE4'),
(2, 'Top Gun: Maverick', 'Pete Mitchell', 'After thirty years, Maverick is still pushing the envelope as a top naval aviator.', '2022-05-27', 'DBOX', 'https://image.tmdb.org/t/p/w500/62HCnUTziyWcpDaBO2i1DX17ljH.jpg', 'https://image.tmdb.org/t/p/w1920_and_h800_multi_faces/odJ4hx6g6vBt4lBWKFD1tI8WS4x.jpg', 'qSqVVswa420'),
(3, 'Spider-Man: No Way Home', 'Peter Parker', 'Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero.', '2021-12-17', 'R3D', 'https://image.tmdb.org/t/p/w500/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg', 'https://image.tmdb.org/t/p/w1920_and_h800_multi_faces/14QbnygCuTO0vl7CAFmPf1fgZfV.jpg', 'JfVOs4VSpmA');

-- Insert sample halls (one by one to ensure foreign key constraints)
INSERT INTO hall (hall_id, capacity, occupied_seats, supported_movie_version, seat_price, cinema_id, screening_times) VALUES 
(1, 120, 0, 'D2D', 12.50, 1, '["10:00", "13:00", "16:00", "19:00", "22:00"]');
INSERT INTO hall (hall_id, capacity, occupied_seats, supported_movie_version, seat_price, cinema_id, screening_times) VALUES 
(2, 80, 0, 'DBOX', 14.00, 1, '["11:00", "14:00", "17:00", "20:00"]');
INSERT INTO hall (hall_id, capacity, occupied_seats, supported_movie_version, seat_price, cinema_id, screening_times) VALUES 
(3, 150, 0, 'R3D', 11.50, 1, '["09:30", "12:30", "15:30", "18:30", "21:30"]');
INSERT INTO hall (hall_id, capacity, occupied_seats, supported_movie_version, seat_price, cinema_id, screening_times) VALUES 
(4, 200, 0, 'D2D', 13.00, 2, '["10:30", "13:30", "16:30", "19:30", "22:30"]');
INSERT INTO hall (hall_id, capacity, occupied_seats, supported_movie_version, seat_price, cinema_id, screening_times) VALUES 
(5, 100, 0, 'DBOX', 15.00, 2, '["11:30", "14:30", "17:30", "20:30"]');
INSERT INTO hall (hall_id, capacity, occupied_seats, supported_movie_version, seat_price, cinema_id, screening_times) VALUES 
(6, 90, 0, 'R3D', 12.50, 2, '["10:15", "13:15", "16:15", "19:15", "22:15"]');
INSERT INTO hall (hall_id, capacity, occupied_seats, supported_movie_version, seat_price, cinema_id, screening_times) VALUES 
(7, 110, 0, 'D2D', 12.00, 3, '["10:45", "13:45", "16:45", "19:45", "22:45"]');
INSERT INTO hall (hall_id, capacity, occupied_seats, supported_movie_version, seat_price, cinema_id, screening_times) VALUES 
(8, 75, 0, 'DBOX', 14.50, 3, '["11:45", "14:45", "17:45", "20:45"]');

-- Insert movie-hall relationships
INSERT INTO movie_plays_in (movie_id, hall_id) VALUES (1, 1);
INSERT INTO movie_plays_in (movie_id, hall_id) VALUES (1, 4);
INSERT INTO movie_plays_in (movie_id, hall_id) VALUES (1, 7);
INSERT INTO movie_plays_in (movie_id, hall_id) VALUES (2, 2);
INSERT INTO movie_plays_in (movie_id, hall_id) VALUES (2, 5);
INSERT INTO movie_plays_in (movie_id, hall_id) VALUES (2, 8);
INSERT INTO movie_plays_in (movie_id, hall_id) VALUES (3, 3);
INSERT INTO movie_plays_in (movie_id, hall_id) VALUES (3, 6);

-- Insert sample customers
INSERT INTO customer (customer_id, first_name, last_name, email, an_adult) VALUES 
(1, 'Max', 'Mustermann', 'max.mustermann@email.com', true),
(2, 'Anna', 'Schmidt', 'anna.schmidt@email.com', true),
(3, 'Peter', 'Wagner', 'peter.wagner@email.com', false),
(4, 'Lisa', 'Bauer', 'lisa.bauer@email.com', true);

-- Update sequences to continue from the inserted values
SELECT setval('cinema_cinema_id_seq', (SELECT MAX(cinema_id) FROM cinema));
SELECT setval('movie_movie_id_seq', (SELECT MAX(movie_id) FROM movie));
SELECT setval('hall_hall_id_seq', (SELECT MAX(hall_id) FROM hall));
SELECT setval('customer_customer_id_seq', (SELECT MAX(customer_id) FROM customer));
