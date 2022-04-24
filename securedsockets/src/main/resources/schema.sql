DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id INT(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(45) NOT NULL ,
  UNIQUE KEY uni_user_username (username),
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS role;
CREATE TABLE role (
  id INT(11) NOT NULL AUTO_INCREMENT,
  role VARCHAR(45) NOT NULL,
  UNIQUE KEY uni_role_role (role),
  PRIMARY KEY (id)
);

/** JOIN TABLES */

DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role(
  id INT(11) NOT NULL AUTO_INCREMENT,
  user_id INT(11) NOT NULL,
  role_id INT(11) NOT NULL,
  PRIMARY KEY (id)
);