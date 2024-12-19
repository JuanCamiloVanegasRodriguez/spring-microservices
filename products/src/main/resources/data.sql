INSERT INTO categories (id, name) VALUES (1, 'Action');
INSERT INTO categories (id, name) VALUES (2, 'Horror');
INSERT INTO categories (id, name) VALUES (3, 'Survival');

INSERT INTO products (name, description, stock, price, status, created_at, category_id)
VALUES
('The Last of Us', 'A gripping survival action game', 50, 59.99, 'AVAILABLE', NOW(), 1),
('Resident Evil', 'Classic horror game with zombies', 30, 49.99, 'AVAILABLE', NOW(), 2),
('Silent Hill', 'Atmospheric survival horror', 25, 39.99, 'AVAILABLE', NOW(), 2),
('Uncharted', 'Action-adventure game with treasure hunting', 40, 29.99, 'AVAILABLE', NOW(), 1),
('Outlast', 'Intense first-person survival horror', 20, 19.99, 'AVAILABLE', NOW(), 3),
('Tomb Raider', 'Iconic action-adventure game', 35, 39.99, 'AVAILABLE', NOW(), 1),
('Dead Space', 'Sci-fi horror with survival elements', 15, 49.99, 'AVAILABLE', NOW(), 3),
('Dark Souls', 'Challenging survival-action RPG', 10, 59.99, 'AVAILABLE', NOW(), 3);
