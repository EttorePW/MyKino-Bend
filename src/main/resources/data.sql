-- Insert sample cinemas first
INSERT INTO cinema (cinema_id, name, address, manager, max_halls) VALUES 
(1, 'CinePlex Wien', 'Wien, Österreich', 'Max Manager', 5),
(2, 'Mega Kino Berlin', 'Berlin, Deutschland', 'Anna Schmidt', 8),
(3, 'Star Cinema München', 'München, Deutschland', 'Peter Wagner', 6);

-- Insert sample movies (usando el orden correcto de las columnas)
INSERT INTO movie (movie_version, premiered_at, movie_id, image, image_bkd, main_character, title, video_id, description) VALUES 
(0, '2022-12-16', 1, 'https://image.tmdb.org/t/p/w500/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg', 'https://image.tmdb.org/t/p/w1920_and_h800_multi_faces/s16H6tpK2utvwDtzZ8Qy4qm5Emw.jpg', 'Jake Sully', 'Avatar: The Way of Water', 'o_WdFm9VuE4', 'Jake Sully lives with his newfound family formed on the extrasolar moon Pandora.'),
(2, '2022-05-27', 2, 'https://image.tmdb.org/t/p/w500/62HCnUTziyWcpDaBO2i1DX17ljH.jpg', 'https://image.tmdb.org/t/p/w1920_and_h800_multi_faces/odJ4hx6g6vBt4lBWKFD1tI8WS4x.jpg', 'Pete Mitchell', 'Top Gun: Maverick', 'qSqVVswa420', 'After thirty years, Maverick is still pushing the envelope as a top naval aviator.'),
(1, '2021-12-17', 3, 'https://image.tmdb.org/t/p/w500/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg', 'https://image.tmdb.org/t/p/w1920_and_h800_multi_faces/14QbnygCuTO0vl7CAFmPf1fgZfV.jpg', 'Peter Parker', 'Spider-Man: No Way Home', 'JfVOs4VSpmA', 'Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero.');

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
(4, 'Lisa', 'Bauer', 'lisa.bauer@email.com', true),
(5, 'Carlos', 'Rodriguez', 'carlos.rodriguez@email.com', true),
(6, 'Sophie', 'Mueller', 'sophie.mueller@email.com', true),
(7, 'Marco', 'Rossi', 'marco.rossi@email.com', true),
(8, 'Emma', 'Johnson', 'emma.johnson@email.com', false);

-- Insert sample seats (reservierte Plätze)
-- Avatar: The Way of Water in Hall 1 (CinePlex Wien)
INSERT INTO seat (seat_id, row_number, col_number, hall_id, movie_id, premiered_at, customer_id, cinema_name, movie_name, movie_version, reservation_date, reservation_time) VALUES 
(1, 5, 8, 1, 1, '2022-12-16', 1, 'CinePlex Wien', 'Avatar: The Way of Water', 'D2D', '2025-08-22', '19:00'),
(2, 5, 9, 1, 1, '2022-12-16', 1, 'CinePlex Wien', 'Avatar: The Way of Water', 'D2D', '2025-08-22', '19:00'),
(3, 6, 12, 1, 1, '2022-12-16', 2, 'CinePlex Wien', 'Avatar: The Way of Water', 'D2D', '2025-08-22', '19:00'),
(4, 6, 13, 1, 1, '2022-12-16', 2, 'CinePlex Wien', 'Avatar: The Way of Water', 'D2D', '2025-08-22', '19:00'),

-- Top Gun: Maverick in Hall 2 (CinePlex Wien)
(5, 3, 5, 2, 2, '2022-05-27', 3, 'CinePlex Wien', 'Top Gun: Maverick', 'DBOX', '2025-08-23', '20:00'),
(6, 3, 6, 2, 2, '2022-05-27', 3, 'CinePlex Wien', 'Top Gun: Maverick', 'DBOX', '2025-08-23', '20:00'),
(7, 7, 10, 2, 2, '2022-05-27', 4, 'CinePlex Wien', 'Top Gun: Maverick', 'DBOX', '2025-08-23', '17:00'),

-- Spider-Man in Hall 3 (CinePlex Wien)
(8, 4, 7, 3, 3, '2021-12-17', 5, 'CinePlex Wien', 'Spider-Man: No Way Home', 'R3D', '2025-08-24', '15:30'),
(9, 4, 8, 3, 3, '2021-12-17', 5, 'CinePlex Wien', 'Spider-Man: No Way Home', 'R3D', '2025-08-24', '15:30'),
(10, 8, 15, 3, 3, '2021-12-17', 6, 'CinePlex Wien', 'Spider-Man: No Way Home', 'R3D', '2025-08-24', '18:30'),
(11, 8, 16, 3, 3, '2021-12-17', 6, 'CinePlex Wien', 'Spider-Man: No Way Home', 'R3D', '2025-08-24', '18:30'),

-- Avatar in Hall 4 (Mega Kino Berlin)
(12, 10, 5, 4, 1, '2022-12-16', 7, 'Mega Kino Berlin', 'Avatar: The Way of Water', 'D2D', '2025-08-25', '19:30'),
(13, 10, 6, 4, 1, '2022-12-16', 7, 'Mega Kino Berlin', 'Avatar: The Way of Water', 'D2D', '2025-08-25', '19:30'),
(14, 10, 7, 4, 1, '2022-12-16', 7, 'Mega Kino Berlin', 'Avatar: The Way of Water', 'D2D', '2025-08-25', '19:30'),

-- Top Gun in Hall 5 (Mega Kino Berlin)
(15, 6, 10, 5, 2, '2022-05-27', 8, 'Mega Kino Berlin', 'Top Gun: Maverick', 'DBOX', '2025-08-26', '20:30');

-- Insert sample bills
INSERT INTO bill (bill_id, customer_id, customer_name, bill_date, total_price) VALUES 
(1, 1, 'Max Mustermann', '2025-08-22', 25.00),
(2, 2, 'Anna Schmidt', '2025-08-22', 25.00),
(3, 3, 'Peter Wagner', '2025-08-23', 28.00),
(4, 4, 'Lisa Bauer', '2025-08-23', 14.00),
(5, 5, 'Carlos Rodriguez', '2025-08-24', 23.00),
(6, 6, 'Sophie Mueller', '2025-08-24', 23.00),
(7, 7, 'Marco Rossi', '2025-08-25', 39.00),
(8, 8, 'Emma Johnson', '2025-08-26', 15.00);

-- Update sequences to continue from the inserted values
SELECT setval('cinema_cinema_id_seq', (SELECT MAX(cinema_id) FROM cinema));
SELECT setval('movie_movie_id_seq', (SELECT MAX(movie_id) FROM movie));
SELECT setval('hall_hall_id_seq', (SELECT MAX(hall_id) FROM hall));
SELECT setval('customer_customer_id_seq', (SELECT MAX(customer_id) FROM customer));
SELECT setval('seat_seat_id_seq', (SELECT MAX(seat_id) FROM seat));
SELECT setval('bill_bill_id_seq', (SELECT MAX(bill_id) FROM bill));
