-- Drop tables if they exist
DROP TABLE IF EXISTS restaurant_cuisines CASCADE ;
DROP TABLE IF EXISTS restaurant_ratings CASCADE ;
DROP TABLE IF EXISTS dishes CASCADE ;
DROP TABLE IF EXISTS restaurants CASCADE ;
DROP TABLE IF EXISTS orders CASCADE ;
DROP TABLE IF EXISTS order_items CASCADE ;

-- Create tables
CREATE TABLE restaurants (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(255),
                             is_kosher BOOLEAN
);

CREATE TABLE restaurant_ratings (
                                    id SERIAL PRIMARY KEY,
                                    restaurant_id INTEGER,
                                    rating FLOAT,
                                    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);


CREATE TABLE restaurant_cuisines (
                                     restaurant_id INTEGER,
                                     cuisine VARCHAR(255),
                                     FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);

CREATE TABLE dishes (
                        id SERIAL PRIMARY KEY,
                        restaurant_id INT NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        description TEXT,
                        price DECIMAL(10, 2) NOT NULL,
                        FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);

CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        restaurant_id INT NOT NULL,
                        FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)

);

CREATE TABLE order_items (
                            id SERIAL PRIMARY KEY,
                            order_id INT NOT NULL,
                            dish_id INT NOT NULL,
                            amount INT NOT NULL,
                            FOREIGN KEY (order_id) REFERENCES orders(id),
                            FOREIGN KEY (dish_id) REFERENCES dishes(id)
);

-- Insert data
INSERT INTO restaurants (name, is_kosher) VALUES ('Sushi Place', FALSE);
INSERT INTO restaurants (name, is_kosher) VALUES ('Falafel Place', TRUE);

INSERT INTO restaurant_cuisines (restaurant_id, cuisine) VALUES (1, 'Japanese');
INSERT INTO restaurant_cuisines (restaurant_id, cuisine) VALUES (2, 'Middle Eastern');

INSERT INTO restaurant_ratings (restaurant_id, rating) VALUES (1, 4.5);
INSERT INTO restaurant_ratings (restaurant_id, rating) VALUES (2, 4.0);

INSERT INTO dishes (restaurant_id, name, description, price) VALUES (1, 'Sushi', 'Fresh fish and vinegared rice', 12.50);
INSERT INTO dishes (restaurant_id, name, description, price) VALUES (1, 'Sashimi', 'Just the fish', 15.00);
INSERT INTO dishes (restaurant_id, name, description, price) VALUES (2, 'Falafel', 'Deep-fried ground chickpeas', 8.50);

-- Query data
SELECT * FROM restaurants
                  INNER JOIN dishes d on restaurants.id = d.restaurant_id
                  INNER JOIN restaurant_ratings r on restaurants.id = r.restaurant_id
                  INNER JOIN restaurant_cuisines c on restaurants.id = c.restaurant_id;


SELECT order_id, amount, dish_id, d.restaurant_id FROM orders
                  INNER JOIN order_items oi on orders.id = oi.order_id
                  INNER JOIN dishes d on oi.dish_id = d.id
                  INNER JOIN restaurants r on orders.restaurant_id = r.id;