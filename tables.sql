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

CREATE TABLE estadio_ubicaciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    estadio_id INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    capacidad INT NOT NULL,
    precio DOUBLE NOT NULL,

    CONSTRAINT fk_ubicaciones_estadio
        FOREIGN KEY (estadio_id) REFERENCES estadios(id)
);

CREATE TABLE espectaculos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    estadio_id INT NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    
    CONSTRAINT fk_espectaculo_estadio
        FOREIGN KEY (estadio_id) REFERENCES estadios(id)
);

CREATE TABLE entradas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    buyer_document VARCHAR(50) NOT NULL,
    buyer_name VARCHAR(100) NOT NULL,
    espectaculo_id INT NOT NULL,
    ubicacion_id INT NOT NULL,
    vendedor_id INT NOT NULL,
    soldAt TIMESTAMP NOT NULL,
    
    CONSTRAINT fk_entrada_espectaculo
        FOREIGN KEY (espectaculo_id) REFERENCES espectaculos(id),

    CONSTRAINT fk_entrada_ubicacion
        FOREIGN KEY (ubicacion_id) REFERENCES estadio_ubicaciones(id),

    CONSTRAINT fk_entrada_vendedor
        FOREIGN KEY (vendedor_id) REFERENCES usuarios(id)
);

INSERT INTO usuarios (name, lastname, document, username, password, isAdmin)
VALUES ('Laura', 'Nieto', '12345678', 'lnieto', '1234', TRUE);
    
INSERT INTO estadios (NAME, CAPACITY, ADDRESS) 
VALUES ('Luna Park', 8400, 'Av. Eduardo Madero 470');

INSERT INTO estadio_ubicaciones (estadio_id, nombre, capacidad, precio) 
VALUES (1, 'Platea Alta Derecha', 700, 3500);

INSERT INTO estadio_ubicaciones (estadio_id, nombre, capacidad, precio) 
VALUES (1, 'Platea Media Derecha', 700, 5500);

INSERT INTO estadio_ubicaciones (estadio_id, nombre, capacidad, precio) 
VALUES (1, 'Campo Delantero', 700, 15500);