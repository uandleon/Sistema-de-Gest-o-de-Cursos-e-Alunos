CREATE DATABASE paredb;

USE paredb;

CREATE TABLE Curso (
    idCurso INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cargaHoraria INT NOT NULL,
    limiteAlunos INT NOT NULL,
    -- Restrições
    CHECK (CHAR_LENGTH(nome) >= 3),
    CHECK (cargaHoraria >= 20),
    CHECK (limiteAlunos >= 1)
);


CREATE TABLE Aluno (
    idAluno INT AUTO_INCREMENT PRIMARY KEY,
    cpf CHAR(11) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    dataNascimento DATE NOT NULL,
    idCurso INT NOT NULL,
    -- Restrições
    CHECK (CHAR_LENGTH(nome) >= 3),
    CHECK (cpf REGEXP '^[0-9]{11}$'),
    CHECK (email REGEXP '^[^@\\s]+@[^@\\s]+\\.[^@\\s]+'),
    FOREIGN KEY (idCurso) REFERENCES Curso(idCurso)
);
