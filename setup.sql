GRANT ALL ON dezsys07.* TO 'dezsys07'@'localhost' IDENTIFIED BY 'dezsys07';
GRANT ALL ON dezsys07.* TO 'dezsys07'@'%' IDENTIFIED BY 'dezsys07';

DROP DATABASE IF EXISTS dezsys07;
CREATE DATABASE dezsys07;