INSERT INTO User(userName, password, firstName, lastName, email, role)
VALUES
    # --Usuario con rol espectador
    ('viewer', '$2a$10$8o34vbwlRURkBGETvQzr8OCuPrk52E.j2ilm4KGKPrwNR89eNV/YG', 'Jane', 'Smith',
     'jane.smith@example.com', 0),
    # --Usuario con rol taquillero
    ('ticketseller', '$2a$10$8o34vbwlRURkBGETvQzr8OCuPrk52E.j2ilm4KGKPrwNR89eNV/YG', 'Tom', 'Davis',
     'tom.davis@example.com', 1);

INSERT INTO MovieTheater(name, capacity)
VALUES
    # --Sala con 9 localidades
    ('Regal Cinemas', 40),
    # --Sala con más de 10 localidades
    ('Cineplex', 40);

INSERT INTO Movie(Title, Summary, Duration)
VALUES
    # --Película 1
    ('The Godfather',
     'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.',
     175),
    # --Película 2
    ('Forrest Gump',
     'The presidencies of Kennedy and Johnson, the events of Vietnam, Watergate, and other history unfold through the perspective of an Alabama man with an IQ of 75.',
     142);

INSERT INTO Session(movieId, movieTheaterId, date, price, seatsAvailable, version)
VALUES
    # --Today Sesion 1
    (1, 2, DATE_FORMAT(DATE_SUB(NOW(), INTERVAL SECOND(NOW()) SECOND), '%Y-%m-%d 00:05:00'), 10.50, 20, 0),
    # --Today Sesion 2
    (2, 1, DATE_FORMAT(DATE_SUB(NOW(), INTERVAL SECOND(NOW()) SECOND), '%Y-%m-%d 23:55:00'), 12.00, 9, 0),
    # --Tomorrow
    (2, 1, DATE_FORMAT(DATE_SUB(DATE_ADD(CURRENT_DATE, INTERVAL 2 DAY), INTERVAL SECOND(NOW()) SECOND), '%Y-%m-%d 00:05:00'), 12.00, 20, 0),
    (1, 2, DATE_FORMAT(DATE_SUB(DATE_ADD(CURRENT_DATE, INTERVAL 2 DAY), INTERVAL SECOND(NOW()) SECOND), '%Y-%m-%d 23:55:00'), 12.00, 9, 0),
    # --Today + 2 days
    (1, 1, DATE_FORMAT(DATE_SUB(DATE_ADD(CURRENT_DATE, INTERVAL 3 DAY), INTERVAL SECOND(NOW()) SECOND), '%Y-%m-%d 00:05:00'), 12.00, 20, 0),
    (2, 2, DATE_FORMAT(DATE_SUB(DATE_ADD(CURRENT_DATE, INTERVAL 3 DAY), INTERVAL SECOND(NOW()) SECOND), '%Y-%m-%d 23:55:00'), 12.00, 9, 0),
    # --Today + 3 days
    (2, 2, DATE_FORMAT(DATE_SUB(DATE_ADD(CURRENT_DATE, INTERVAL 4 DAY), INTERVAL SECOND(NOW()) SECOND), '%Y-%m-%d 00:05:00'), 12.00, 20, 0),
    (1, 1, DATE_FORMAT(DATE_SUB(DATE_ADD(CURRENT_DATE, INTERVAL 4 DAY), INTERVAL SECOND(NOW()) SECOND), '%Y-%m-%d 23:55:00'), 12.00, 9, 0),
    # --Today + 4 days
    (1, 2, DATE_FORMAT(DATE_SUB(DATE_ADD(CURRENT_DATE, INTERVAL 5 DAY), INTERVAL SECOND(NOW()) SECOND), '%Y-%m-%d 00:05:00'), 12.00, 20, 0),
    (2, 1, DATE_FORMAT(DATE_SUB(DATE_ADD(CURRENT_DATE, INTERVAL 5 DAY), INTERVAL SECOND(NOW()) SECOND), '%Y-%m-%d 23:55:00'), 12.00, 9, 0),
    # --Today + 5 days
    (2, 1, DATE_FORMAT(DATE_SUB(DATE_ADD(CURRENT_DATE, INTERVAL 6 DAY), INTERVAL SECOND(NOW()) SECOND), '%Y-%m-%d 00:05:00'), 12.00, 20, 0),
    (1, 2, DATE_FORMAT(DATE_SUB(DATE_ADD(CURRENT_DATE, INTERVAL 6 DAY), INTERVAL SECOND(NOW()) SECOND), '%Y-%m-%d 23:55:00'), 12.00, 9, 0),
    # --Today + 6 days
    (1, 1, DATE_FORMAT(DATE_SUB(DATE_ADD(CURRENT_DATE, INTERVAL 7 DAY), INTERVAL SECOND(NOW()) SECOND), '%Y-%m-%d 00:05:00'), 12.00, 20, 0),
    (2, 2, DATE_FORMAT(DATE_SUB(DATE_ADD(CURRENT_DATE, INTERVAL 7 DAY), INTERVAL SECOND(NOW()) SECOND), '%Y-%m-%d 23:55:00'), 12.00, 9, 0);

INSERT INTO Purchase(reservedSeats, sessionId, userId, cardNumber, purchaseDate, used)
VALUES
    # --Compra 1 de la sesion 1
    (2, 1, 1, '12345678901234', NOW(), false),
    # --Compra 2 de la sesion 1
    (3, 1, 1, '23456778901234', NOW(), false),
    # --Compra 3 de la sesión 1 de mañana
    (1, 3, 1, '12345678901234', NOW(), false);
