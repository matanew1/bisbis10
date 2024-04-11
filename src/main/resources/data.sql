SELECT restaurants.id, restaurants.is_kosher, restaurants.name, restaurant_ratings.rating
FROM restaurants
INNER JOIN restaurant_ratings ON restaurants.id = restaurant_ratings.id;


SELECT* from restaurant_ratings;