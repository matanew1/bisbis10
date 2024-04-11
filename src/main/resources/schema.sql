-- Drop tables if they exist
DROP TABLE IF EXISTS restaurants CASCADE ;
DROP TABLE IF EXISTS restaurant_cuisines CASCADE ;
DROP TABLE IF EXISTS restaurant_ratings CASCADE ;


-- restaurant table can have multiple cuisines
CREATE TABLE restaurant_ratings (
   id SERIAL PRIMARY KEY,
   rating FLOAT
);

CREATE TABLE restaurants (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    is_kosher BOOLEAN,
    rating_id INTEGER,
    FOREIGN KEY (rating_id) REFERENCES restaurant_ratings(id)
);

CREATE TABLE restaurant_cuisines (
    restaurant_id INTEGER,
    cuisine VARCHAR(255),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);


-- Insert data
INSERT INTO restaurant_ratings (rating) VALUES (4.5);
INSERT INTO restaurant_ratings (rating) VALUES (3.5);
INSERT INTO restaurant_ratings (rating) VALUES (5.0);

INSERT INTO restaurants (name, is_kosher, rating_id) VALUES ('Miznon', TRUE, 1);
INSERT INTO restaurants (name, is_kosher, rating_id) VALUES ('Hummus HaCarmel', FALSE, 2);
INSERT INTO restaurants (name, is_kosher, rating_id) VALUES ('Falafel Gabai', TRUE, 3);

INSERT INTO restaurant_cuisines (restaurant_id, cuisine) VALUES (1, 'Israeli');
INSERT INTO restaurant_cuisines (restaurant_id, cuisine) VALUES (1, 'Street Food');
INSERT INTO restaurant_cuisines (restaurant_id, cuisine) VALUES (2, 'Israeli');

