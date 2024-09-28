CREATE DATABASE IF NOT EXISTS examendb;
USE examendb;

CREATE TABLE UnidadDidactica (
    id INT AUTO_INCREMENT PRIMARY KEY,
    acronimo VARCHAR(10) NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    evaluacion VARCHAR(255) NOT NULL,
    descripcion TEXT
);

CREATE TABLE Enunciado (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descripcion TEXT NOT NULL,
    nivel VARCHAR(10) CHECK (nivel IN ('ALTA', 'MEDIA', 'BAJA')) NOT NULL,
    disponible BOOLEAN NOT NULL,
    ruta VARCHAR(255)
);

CREATE TABLE ConvocatoriaExamen (
    convocatoria VARCHAR(50) PRIMARY KEY,
    descripcion TEXT,
    fecha DATE NOT NULL, 
    curso VARCHAR(50) NOT NULL,
    enunciado_id INT,
    FOREIGN KEY (enunciado_id) REFERENCES Enunciado(id)
);

CREATE TABLE UnidadDidactica_Enunciado (
    enunciado_id INT,
    unidad_didactica_id INT,
    PRIMARY KEY (enunciado_id, unidad_didactica_id),
    FOREIGN KEY (enunciado_id) REFERENCES Enunciado(id),
    FOREIGN KEY (unidad_didactica_id) REFERENCES UnidadDidactica(id)
);

INSERT INTO UnidadDidactica (acronimo, titulo, evaluacion, descripcion) 
    VALUES
        ('UD1', 'Introducción a la Programación', 'Examen Final', 'Conceptos básicos de programación.'),
        ('UD2', 'Estructuras de Datos', 'Examen Final', 'Estudio de las estructuras de datos más comunes.'),
        ('UD3', 'Bases de Datos', 'Examen Final', 'Diseño y manipulación de bases de datos.'),
        ('UD4', 'Redes de Computadoras', 'Examen Final', 'Fundamentos y protocolos de redes de computadoras.'),
        ('UD5', 'Sistemas Operativos', 'Examen Final', 'Conceptos básicos sobre sistemas operativos.');

INSERT INTO Enunciado (descripcion, nivel, disponible, ruta) 
    VALUES
        ('Describe el funcionamiento de un bucle for en Python.', 'MEDIA', TRUE, 'src/resources/enunciado1.txt'),
        ('Explica los diferentes tipos de joins en SQL.', 'ALTA', TRUE, 'src/ruta/enunciado2.txt'),
        ('Dibuja un diagrama de una red local.', 'BAJA', FALSE, 'src/ruta/enunciado3.txt'),
        ('Explica la diferencia entre RAM y ROM.', 'BAJA', TRUE, 'src/ruta/enunciado4.txt'),
        ('Describe el algoritmo de búsqueda binaria.', 'MEDIA', TRUE, 'src/ruta/enunciado5.txt');

INSERT INTO ConvocatoriaExamen (convocatoria, descripcion, fecha, curso, enunciado_id) 
    VALUES
        ('Convocatoria 2024-1', 'Primera convocatoria del curso 2024.', '2024-05-10', '2023-2024', 1),
        ('Convocatoria 2024-2', 'Segunda convocatoria del curso 2024.', '2024-09-20', '2023-2024', 2),
        ('Convocatoria 2025-1', 'Primera convocatoria del curso 2025.', '2025-05-12', '2024-2025', 3);

INSERT INTO UnidadDidactica_Enunciado (enunciado_id, unidad_didactica_id) 
    VALUES
        (1, 1),
        (2, 3),
        (3, 4),
        (4, 5),
        (5, 2);
