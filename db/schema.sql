#Create database

CREATE DATABASE if NOT EXISTS products;
GRANT ALL PRIVILEGES ON products.* TO 'centric'@'%';
FLUSH PRIVILEGES;

use products;



