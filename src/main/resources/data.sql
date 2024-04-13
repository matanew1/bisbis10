select * FROM restaurants
JOIN public.orders o on restaurants.id = o.restaurant_id;