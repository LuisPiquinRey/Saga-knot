-- Crear base de datos si no existe
CREATE DATABASE IF NOT EXISTS product_slave;

-- Dar permisos al usuario root desde cualquier host
CREATE USER IF NOT EXISTS 'root'@'%' IDENTIFIED BY 'LuisPiquinRey';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;

-- Asegurar permisos específicos para la base de datos
GRANT ALL PRIVILEGES ON product_slave.* TO 'root'@'%';
FLUSH PRIVILEGES;
