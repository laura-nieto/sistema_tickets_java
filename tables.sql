CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    document VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    isAdmin BOOLEAN DEFAULT FALSE
);

CREATE TABLE estadios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    capacity INT NOT NULL,
    address VARCHAR(255)
);

CREATE TABLE espectaculos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    estadio_id INT NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    
    CONSTRAINT fk_espectaculo_estadio
        FOREIGN KEY (estadio_id) REFERENCES estadio(id)
);

INSERT INTO usuarios (name, lastname, document, username, password, isAdmin)
VALUES ('Laura', 'Nieto', '12345678', 'lnieto', '1234', TRUE);


INSERT INTO ESTADIOS (NAME, CAPACITY, ADDRESS) 
VALUES ('Luna Park', 8400, 'Av. Eduardo Madero 470');