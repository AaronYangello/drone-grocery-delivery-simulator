SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE customer;
TRUNCATE customer_favorite_orders;
TRUNCATE customer_price_alert_items;
TRUNCATE drone;
TRUNCATE employee;
TRUNCATE favorite_line_item;
TRUNCATE favorite_order;
TRUNCATE favorite_order_favorite_line_items;
TRUNCATE item;
TRUNCATE line_item;
TRUNCATE orders;
TRUNCATE pilot;
TRUNCATE pilot_vacation_dates;
TRUNCATE store;

-- Don't delete admin user
DELETE FROM user u WHERE u.account != 'admin';