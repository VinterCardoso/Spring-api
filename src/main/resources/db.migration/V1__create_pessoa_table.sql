CREATE TABLE IF NOT EXISTS pessoa (
    id                  BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nome                VARCHAR(255) NOT NULL,
    cpf                 VARCHAR(11) NOT NULL UNIQUE,
    data_nascimento     DATE NOT NULL,
    email               VARCHAR(255) NOT NULL
)