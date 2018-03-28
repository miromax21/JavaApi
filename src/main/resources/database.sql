-- Table: users
CREATE DATABASE IF NOT EXISTS mydbtest;
USE mydbtest;
CREATE TABLE users (
  id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL
)
  ENGINE = InnoDB;

-- Table: roles
CREATE TABLE IF NOT EXISTS roles (
  id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL
)
  ENGINE = InnoDB;

-- Table for mapping user and roles: user_roles
CREATE TABLE IF NOT EXISTS user_roles (
  user_id INT NOT NULL,
  role_id INT NOT NULL,

  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id),

  UNIQUE (user_id, role_id)
)
  ENGINE = InnoDB;

-- Table: articles
CREATE TABLE IF NOT EXISTS articles (
  article_id int(5) NOT NULL AUTO_INCREMENT,
  title varchar(200) NOT NULL,
  category varchar(100) NOT NULL,
  PRIMARY KEY (article_id)
)
  ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
-- Insert data

INSERT INTO users VALUES (1, 'proselyte', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG');

INSERT INTO roles (id, name) VALUES
  (1, 'ROLE_USER'),
  (2, 'ROLE_ADMIN');

# INSERT INTO roles VALUES (1, 'ROLE_USER');
# INSERT INTO roles VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_roles VALUES (1, 2);

INSERT INTO articles (article_id, title, category) VALUES
  (1, 'Angular 2 Tutorial using CLI', 'Angular'),
  (2, 'Spring Boot Getting Started', 'Spring Boot'),
  (3, 'Lambda Expressions Java 8 Example', 'Java 8'),
  (4, 'Android AsyncTask Example', 'Android');