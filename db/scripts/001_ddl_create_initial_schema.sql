CREATE TABLE IF NOT EXISTS site(
     id SERIAL PRIMARY KEY,
     name VARCHAR NOT NULL UNIQUE,
     login VARCHAR NOT NULL UNIQUE,
     password VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS item(
     id SERIAL PRIMARY KEY,
     url VARCHAR NOT NULL UNIQUE,
     code VARCHAR NOT NULL UNIQUE,
     total INT,
     site_id INT NOT NULL REFERENCES site(id)
);