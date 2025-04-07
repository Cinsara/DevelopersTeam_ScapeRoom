USE `escape_room`;

-- Insert into room
INSERT INTO room (room_name,room_theme, room_difficulty) VALUES
('In the mood for love','Love Affair', 'Easy'),
('Lord of the Rings','Fantastic', 'Medium'),
('It','Mystery', 'Hard'),
('Star Trek Adventure','Sci-Fi', 'Medium');

-- Insert into costumer (customers)
INSERT INTO customer (customer_name, customer_lastname, customer_dob, customer_mail, customer_phone_number, customer_notifications, customer_dateRegistration) VALUES
('Alice', 'Johnson', '1990-05-14', 'alice.johnson@example.com', '123-456-7890', 1, '2024-03-10'),
('Bob', 'Smith', '1985-09-23', 'bob.smith@example.com', '987-654-3210', 0, '2024-03-12'),
('Charlie', 'Brown', '1995-02-07', 'charlie.brown@example.com', '456-789-1234', 1, '2024-03-12'),
('Diana', 'Prince', '1992-07-19', 'diana.prince@example.com', '321-654-9870', 1, '2024-03-15');

-- Insert into game
INSERT INTO game (game_date, game_success, game_lengthInSec, room_room_id, captain_customer_id) VALUES
('2024-03-16', 1, 3600, 1, 1),
('2024-03-17', 0, 4200, 2, 2),
('2024-03-18', 1, 3900, 3, 3),
('2024-03-19', 0, 4000, 4, 4);

-- Insert into game_has_costumer (Many-to-Many relationship)
INSERT INTO game_has_customer (game_game_id, customer_customer_id) VALUES
(1, 1), (1, 2), (1, 3),
(2, 2), (2, 3), 
(3, 1), (3, 3), (3, 4),
(4, 2), (4, 4);

-- Insert into tiquet
INSERT INTO ticket (ticket_price, ticket_saleDate, captain_customer_id, game_game_id) VALUES
(25.00, '2024-03-15', 1, 1),
(30.00, '2024-03-16', 2, 2),
(22.50, '2024-03-17', 3, 3),
(28.00, '2024-03-18', 4, 4);

-- Insert into certificate
INSERT INTO certificate (game_game_id, customer_customer_id) VALUES
(1, 1), (1, 2), (1, 3),
(3, 1), (3, 3), (3, 4);

-- Insert into reward
INSERT INTO reward (customer_customer_id, game_game_id) VALUES
(1, 1), 
(3, 3);

-- Insert into notification
INSERT INTO notification (notification_content, notification_dateSent) VALUES
('Your escape room session is booked for tomorrow!','2024-03-16 14:00:00'),
('Congratulations! You successfully completed your escape room challenge.','2024-04-16 14:00:00'),
('New rooms added! Try our Mystery and Sci-Fi escape rooms.','2025-03-16 14:00:00');

-- Insert into clue
INSERT INTO clue (clue_type, room_room_id) VALUES
('Enigma', 1),
('Indication', 2),
('Enigma', 3),
('Indication', 4);

INSERT INTO game_uses_clue (game_game_id,clue_clue_id) VALUES
(1,3),
(1,2),
(2,4);

-- Insert into prop
INSERT INTO prop (prop_type, prop_value, room_room_id) VALUES
('Spade', 2, 1),
('Closet', 1, 2),
('Mountain', 3, 3),
('Spade', 1, 4);
