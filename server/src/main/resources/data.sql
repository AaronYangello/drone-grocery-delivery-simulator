delete from config_options;

INSERT INTO config_options (name, example_string, permissions) VALUES
	('show', 'config show [setting]', 'ADMIN,STORE_ADMIN,USER'),
	('error-message', 'config set error-message [error] [error-message-text]', 'ADMIN'),
	('pilot-probation', 'config set pilot-probation [store-name] [num-trips-to-complete]', 'ADMIN'),
	('pilot-vacation', 'config set pilot-vacation [pilot-account] [yyyy-mm-dd]', 'ADMIN'),
	('min-rating', 'config set min-rating [store-name] [min-rating-required]', 'ADMIN,STORE_ADMIN'),
	('min-credit', 'config set min-credit [store-name] [min-credit-required]', 'ADMIN,STORE_ADMIN'),
	('drone-alert', 'config set drone-alert [store-name] [number-of-trips]', 'ADMIN,STORE_ADMIN'),
	('favorite-order', 'config set favorite-order [customer-account] [order]', 'ADMIN,USER'),
	('price-alert', 'config set price-alert [customer-account] [store-name] [item]', 'ADMIN,USER');

INSERT IGNORE INTO system_error (name, error_text) VALUES
	('cmd-not-found', 'This command is not recognized. Please try again.'),
	('incorrect-num-args', 'Incorrect number of arguments. Please try again.'),
	('server-error', 'Something went wrong with your request.'),
	('incorrect-arg-type', 'One or more of your arguments are of the wrong type. Please try again.'),
	('generic', 'Something went wrong. Please try again');

INSERT IGNORE INTO user (account, password, role) VALUES
	('admin', 'admin', 'ADMIN');
