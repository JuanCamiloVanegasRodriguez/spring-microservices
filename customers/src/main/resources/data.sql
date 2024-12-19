-- Create 'regions' table and insert data
INSERT INTO regions (id, name) VALUES
(1, 'North America'),
(2, 'Europe'),
(3, 'Asia'),
(4, 'South America'),
(5, 'Africa');

-- Create 'customers' table and insert data
INSERT INTO customers (id, number_id, first_name, last_name, email, photo_url, region_id, state) VALUES
(1, '12345678', 'John', 'Doe', 'john.doe@example.com', 'http://example.com/photos/johndoe.jpg', 1, 'CREATED'),
(2, '87654321', 'Jane', 'Smith', 'jane.smith@example.com', 'http://example.com/photos/janesmith.jpg', 2, 'CREATED'),
(3, '11223344', 'Carlos', 'Ramirez', 'carlos.ramirez@example.com', 'http://example.com/photos/carlosramirez.jpg', 4, 'CREATED'),
(4, '44332211', 'Yuki', 'Tanaka', 'yuki.tanaka@example.com', 'http://example.com/photos/yukitanaka.jpg', 3, 'CREATED'),
(5, '55667788', 'Amina', 'Diallo', 'amina.diallo@example.com', 'http://example.com/photos/aminadiallo.jpg', 5, 'DELETED');
