-- =============================================================================
-- Clínica Médica SaludVital - Schema DDL (MySQL 8)
-- 3FN, PKs, FKs con ON DELETE/UPDATE coherentes, índices
-- =============================================================================

DROP DATABASE IF EXISTS saludvital_db;
CREATE DATABASE saludvital_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE saludvital_db;

-- -----------------------------------------------------------------------------
-- 1. USUARIO (autenticación y roles)
-- -----------------------------------------------------------------------------
CREATE TABLE usuario (
    id              INT             AUTO_INCREMENT PRIMARY KEY,
    username        VARCHAR(50)     NOT NULL UNIQUE,
    email           VARCHAR(120)    NOT NULL UNIQUE,
    first_name      VARCHAR(80)     NOT NULL,
    last_name       VARCHAR(80)     NOT NULL,
    password_hash   VARCHAR(255)    NOT NULL,
    rol             ENUM('ADMIN','MEDICO','RECEPCIONISTA') NOT NULL DEFAULT 'RECEPCIONISTA',
    activo          TINYINT(1)      NOT NULL DEFAULT 1,
    fecha_creacion  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_usuario_rol (rol),
    INDEX idx_usuario_activo (activo)
) ENGINE=InnoDB;

-- -----------------------------------------------------------------------------
-- 2. ESPECIALIDAD
-- -----------------------------------------------------------------------------
CREATE TABLE especialidad (
    id              INT             AUTO_INCREMENT PRIMARY KEY,
    nombre          VARCHAR(100)    NOT NULL UNIQUE,
    descripcion     VARCHAR(255)    NULL,
    activo          TINYINT(1)      NOT NULL DEFAULT 1
) ENGINE=InnoDB;

-- -----------------------------------------------------------------------------
-- 3. MEDICO (vinculado a usuario)
-- -----------------------------------------------------------------------------
CREATE TABLE medico (
    id              INT             AUTO_INCREMENT PRIMARY KEY,
    usuario_id      INT             NOT NULL UNIQUE,
    colegiado       VARCHAR(30)     NOT NULL UNIQUE,
    especialidad_id INT             NOT NULL,
    telefono        VARCHAR(20)     NULL,
    activo          TINYINT(1)      NOT NULL DEFAULT 1,
    CONSTRAINT fk_medico_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuario(id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_medico_especialidad
        FOREIGN KEY (especialidad_id) REFERENCES especialidad(id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_medico_especialidad (especialidad_id)
) ENGINE=InnoDB;

-- -----------------------------------------------------------------------------
-- 4. PACIENTE
-- -----------------------------------------------------------------------------
CREATE TABLE paciente (
    id                  INT             AUTO_INCREMENT PRIMARY KEY,
    dpi                 VARCHAR(20)     NOT NULL UNIQUE,
    nombre              VARCHAR(80)     NOT NULL,
    apellido            VARCHAR(80)     NOT NULL,
    fecha_nacimiento    DATE            NOT NULL,
    sexo                ENUM('M','F','O') NOT NULL,
    telefono            VARCHAR(20)     NULL,
    email               VARCHAR(120)    NULL,
    direccion           VARCHAR(255)    NULL,
    fecha_registro      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    activo              TINYINT(1)      NOT NULL DEFAULT 1,
    INDEX idx_paciente_nombre (apellido, nombre),
    INDEX idx_paciente_activo (activo)
) ENGINE=InnoDB;

-- -----------------------------------------------------------------------------
-- 5. CITA (proceso crítico / transaccional)
-- -----------------------------------------------------------------------------
CREATE TABLE cita (
    id              INT             AUTO_INCREMENT PRIMARY KEY,
    paciente_id     INT             NOT NULL,
    medico_id       INT             NOT NULL,
    fecha_hora      DATETIME        NOT NULL,
    estado          ENUM('PROGRAMADA','CONFIRMADA','ATENDIDA','CANCELADA') NOT NULL DEFAULT 'PROGRAMADA',
    motivo          VARCHAR(255)    NULL,
    notas           TEXT            NULL,
    fecha_creacion  TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_cita_paciente
        FOREIGN KEY (paciente_id) REFERENCES paciente(id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_cita_medico
        FOREIGN KEY (medico_id) REFERENCES medico(id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX idx_cita_fecha (fecha_hora),
    INDEX idx_cita_estado (estado),
    INDEX idx_cita_paciente (paciente_id),
    INDEX idx_cita_medico (medico_id),
    -- Evitar doble booking del mismo médico en la misma hora
    UNIQUE KEY uk_medico_fecha (medico_id, fecha_hora)
) ENGINE=InnoDB;
