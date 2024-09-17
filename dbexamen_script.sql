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
