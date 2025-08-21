-- ==============================================
-- Insertar 20 películas adicionales
-- Usando datos de TMDB con movie_version y main_character personalizados
-- ==============================================

INSERT INTO movie (title, main_character, description, premiered_at, movie_version, image, image_bkd, video_id)
VALUES
    ('Avengers: Endgame', 'Tony Stark', 'Después de los eventos devastadores de Infinity War, el universo está en ruinas. Con la ayuda de los aliados restantes, los Vengadores se reúnen una vez más.', DATE '2019-04-26', 'DBOX', 'https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg', 'https://image.tmdb.org/t/p/original/7RyHsO4yDXtBv1zUU3mTpHeQ0d5.jpg', '299534'),
    
    ('Spider-Man: No Way Home', 'Peter Parker', 'Peter Parker busca la ayuda del Doctor Strange para restaurar su identidad secreta. Cuando un hechizo sale mal, peligrosos enemigos de otros mundos aparecen.', DATE '2021-12-17', 'R3D', 'https://image.tmdb.org/t/p/w500/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg', 'https://image.tmdb.org/t/p/original/14QbnygCuTO0vl7CAFmPf1fgZfV.jpg', '634649'),
    
    ('Black Panther', 'T''Challa', 'T''Challa regresa a casa para ser coronado Rey de Wakanda, pero se ve arrastrado a un conflicto que pone en peligro el destino de Wakanda y el mundo entero.', DATE '2018-02-16', 'D2D', 'https://image.tmdb.org/t/p/w500/uxzzxijgPIY7slzFvMotPv8wjKA.jpg', 'https://image.tmdb.org/t/p/original/b6ZJZHUdMEFECvGiDpJjlfUWela.jpg', '284054'),
    
    ('Joker', 'Arthur Fleck', 'En Gotham City, Arthur Fleck, un comediante fallido, se sumerge en la locura y se convierte en el criminal psicópata conocido como Joker.', DATE '2019-10-04', 'D2D', 'https://image.tmdb.org/t/p/w500/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg', 'https://image.tmdb.org/t/p/original/n6bUvigpRFqSwmPp1m2YADdbRBc.jpg', '475557'),
    
    ('Dune', 'Paul Atreides', 'Paul Atreides, un joven brillante y talentoso nacido para un gran destino, debe viajar al planeta más peligroso del universo para asegurar el futuro de su familia.', DATE '2021-10-22', 'DBOX', 'https://image.tmdb.org/t/p/w500/d5NXSklXo0qyIYkgV94XAgMIckC.jpg', 'https://image.tmdb.org/t/p/original/s6w2WOzTVPXMw2fBHfJqYeJdBiQ.jpg', '438631'),
    
    ('Top Gun: Maverick', 'Pete Mitchell', 'Después de más de treinta años de servicio como uno de los mejores aviadores de la Marina, Pete Mitchell está donde pertenece, superando los límites.', DATE '2022-05-27', 'R3D', 'https://image.tmdb.org/t/p/w500/62HCnUTziyWcpDaBO2i1DX17ljH.jpg', 'https://image.tmdb.org/t/p/original/odJ4hx6g6vBt4lBWKFD1tI8WS4x.jpg', '361743'),
    
    ('The Batman', 'Bruce Wayne', 'Cuando un asesino se dirige a la élite de Gotham con una serie de maquinaciones sádicas, un rastro de pistas crípticas envía a Batman a una investigación.', DATE '2022-03-04', 'D2D', 'https://image.tmdb.org/t/p/w500/b0PlHKgfUzJeVvvyaTBVadOHKBj.jpg', 'https://image.tmdb.org/t/p/original/u2HkNaVcFWefgB0dOzWozv79TEf.jpg', '414906'),
    
    ('Doctor Strange in the Multiverse of Madness', 'Stephen Strange', 'El Doctor Strange desata un mal indescriptible mientras viaja a través del multiverso para enfrentar a una nueva y misteriosa amenaza.', DATE '2022-05-06', 'DBOX', 'https://image.tmdb.org/t/p/w500/9Gtg2DzBhmYamXBS1hKAhiwbBKS.jpg', 'https://image.tmdb.org/t/p/original/wcKFYIiVDvRURrzglV9kGu7fpfY.jpg', '453395'),
    
    ('Thor: Love and Thunder', 'Thor Odinson', 'Thor emprende un viaje diferente a todo lo que ha enfrentado: una búsqueda de la paz interior. Pero su búsqueda es interrumpida por Gorr el Carnicero de Dioses.', DATE '2022-07-08', 'R3D', 'https://image.tmdb.org/t/p/w500/pIkRyD18kl4FhoCNQuWxWu5cBLM.jpg', 'https://image.tmdb.org/t/p/original/jsoz1HlxczSuTx0mDl2h0lxy36l.jpg', '616037'),
    
    ('Interstellar', 'Cooper', 'Un grupo de exploradores aprovecha un agujero de gusano recién descubierto para superar las limitaciones del vuelo espacial humano y conquistar las vastas distancias.', DATE '2014-11-07', 'DBOX', 'https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg', 'https://image.tmdb.org/t/p/original/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg', '157336'),
    
    ('The Dark Knight', 'Batman', 'Batman inicia su guerra contra el crimen con su teniente Jim Gordon y el fiscal de distrito Harvey Dent, pero un criminal psicópata conocido como Joker amenaza Gotham.', DATE '2008-07-18', 'D2D', 'https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg', 'https://image.tmdb.org/t/p/original/hqkIcbrOHL86UncnHIsHVcVmzue.jpg', '155'),
    
    ('Gladiator', 'Maximus', 'Un antiguo general romano se ve obligado a convertirse en gladiador después de ser traicionado por el corrupto hijo del emperador que asesinó a su familia.', DATE '2000-05-05', 'D2D', 'https://image.tmdb.org/t/p/w500/ty8TGRuvJLPUmAR1H1nRIsgwvim.jpg', 'https://image.tmdb.org/t/p/original/6WBIzCgmDCYrqh64yDREGeDk9d3.jpg', '98'),
    
    ('The Lord of the Rings: The Fellowship of the Ring', 'Frodo Baggins', 'Un hobbit reacio, Frodo Baggins, se embarca en una búsqueda para destruir un poderoso anillo y salvar la Tierra Media del Señor Oscuro Sauron.', DATE '2001-12-19', 'R3D', 'https://image.tmdb.org/t/p/w500/6oom5QYQ2yQTMJIbnvbkBL9cHo6.jpg', 'https://image.tmdb.org/t/p/original/x2RS3uTcsJJ9IfjNPcgDmukoEcQ.jpg', '120'),
    
    ('Pulp Fiction', 'Vincent Vega', 'Las vidas de dos asesinos de la mafia, un boxeador, la esposa de un gángster y dos ladrones de restaurantes se entrelazan en cuatro historias de violencia y redención.', DATE '1994-10-14', 'D2D', 'https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg', 'https://image.tmdb.org/t/p/original/4cDFJr4HnXN5AdPw4AKrmLlMWdO.jpg', '680'),
    
    ('Forrest Gump', 'Forrest Gump', 'Las presidencias de Kennedy y Johnson, Vietnam, Watergate y otros eventos históricos se desarrollan desde la perspectiva de un hombre de Alabama con un coeficiente intelectual de 75.', DATE '1994-07-06', 'D2D', 'https://image.tmdb.org/t/p/w500/arw2vcBveWOVZr6pxd9XTd1TdQa.jpg', 'https://image.tmdb.org/t/p/original/7c9UVPPiTPltouxRVY6N9uugaVA.jpg', '13'),
    
    ('The Shawshank Redemption', 'Andy Dufresne', 'Dos hombres encarcelados se unen a lo largo de varios años, encontrando consuelo y eventual redención a través de actos de decencia común.', DATE '1994-09-23', 'D2D', 'https://image.tmdb.org/t/p/w500/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg', 'https://image.tmdb.org/t/p/original/SBprfK2ZbUjz0QD4LGq5BzplSDa.jpg', '278'),
    
    ('Mad Max: Fury Road', 'Max Rockatansky', 'En un futuro apocalíptico, Max se une a una rebelde guerrera para huir de un señor de la guerra tiránico en busca de su tierra natal perdida.', DATE '2015-05-15', 'DBOX', 'https://image.tmdb.org/t/p/w500/hA2ple9q4qnwxp3hKVNhroipsir.jpg', 'https://image.tmdb.org/t/p/original/tbhdm8UJAb4ViCTsulYFL3lxMCd.jpg', '76341'),
    
    ('Blade Runner 2049', 'Officer K', 'Un joven blade runner descubre un secreto enterrado hace mucho tiempo que lo lleva a buscar a Rick Deckard, quien ha estado desaparecido durante 30 años.', DATE '2017-10-06', 'R3D', 'https://image.tmdb.org/t/p/w500/gajva2L0rPYkEWjzgFlBXCAVBE5.jpg', 'https://image.tmdb.org/t/p/original/ilKBHrJkFCgwmLvMlRECNSPWd7i.jpg', '335984'),
    
    ('Parasite', 'Ki-taek', 'Una familia pobre esquema para convertirse en empleados de una familia rica infiltrándose en su hogar y posándose como trabajadores domésticos no relacionados.', DATE '2019-05-30', 'D2D', 'https://image.tmdb.org/t/p/w500/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg', 'https://image.tmdb.org/t/p/original/TU9NIjwzjoKPwQHoHshkFcQUCG.jpg', '496243'),
    
    ('Everything Everywhere All at Once', 'Evelyn Wang', 'Una mujer china-estadounidense atrapada en una auditoría del IRS debe conectar con versiones paralelas de ella misma para prevenir que un poderoso ser destruya el multiverso.', DATE '2022-03-25', 'DBOX', 'https://image.tmdb.org/t/p/w500/w3LxiVYdWWRvEVdn5RYq6jIqkb1.jpg', 'https://image.tmdb.org/t/p/original/AcoVfiv1rrWOmAdpnAMnM56ki19.jpg', '545611');
