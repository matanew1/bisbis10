-- Drop tables if they exist
DROP TABLE IF EXISTS restaurant_cuisines CASCADE ;
DROP TABLE IF EXISTS restaurant_ratings CASCADE ;
DROP TABLE IF EXISTS dishes CASCADE ;
DROP TABLE IF EXISTS restaurants CASCADE ;

-- Create tables
CREATE TABLE restaurants (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(255),
                             is_kosher BOOLEAN,
                             UNIQUE (name),
                             CHECK (name IS NOT NULL),
                             CHECK (is_kosher IS NOT NULL)
);

CREATE TABLE restaurant_ratings (
                                    id SERIAL PRIMARY KEY,
                                    restaurant_id INTEGER,
                                    rating FLOAT,
                                    CHECK (rating >= 0 AND rating <= 5),
                                    CHECK (rating IS NOT NULL),
                                    CHECK (restaurant_id IS NOT NULL)
);


CREATE TABLE restaurant_cuisines (
                                     restaurant_id INTEGER,
                                     cuisine VARCHAR(255),
                                     FOREIGN KEY (restaurant_id) REFERENCES restaurants(id),
                                     UNIQUE (restaurant_id, cuisine),
                                     CHECK (restaurant_id IS NOT NULL),
                                     CHECK (cuisine IS NOT NULL)
);

CREATE TABLE dishes (
                        id SERIAL PRIMARY KEY,
                        restaurant_id INT NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        description TEXT,
                        price DECIMAL(10, 2) NOT NULL,
                        FOREIGN KEY (restaurant_id) REFERENCES restaurants(id),
                        UNIQUE (restaurant_id, name),
                        CHECK (restaurant_id IS NOT NULL),
                        CHECK (name IS NOT NULL),
                        CHECK (price IS NOT NULL)
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
