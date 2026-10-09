-- =============================================================================
-- Clínica Médica SaludVital - Stored Procedures CRUD
-- =============================================================================
USE saludvital_db;

DELIMITER //

-- ======================== USUARIO ========================
CREATE PROCEDURE sp_usuario_insertar(
    IN p_username VARCHAR(50),
    IN p_email VARCHAR(120),
    IN p_first_name VARCHAR(80),
    IN p_last_name VARCHAR(80),
    IN p_password_hash VARCHAR(255),
    IN p_rol VARCHAR(20)
)
BEGIN
    INSERT INTO usuario (username, email, first_name, last_name, password_hash, rol)
    VALUES (p_username, p_email, p_first_name, p_last_name, p_password_hash, p_rol);
END //

CREATE PROCEDURE sp_usuario_actualizar(
    IN p_id INT,
    IN p_username VARCHAR(50),
    IN p_email VARCHAR(120),
    IN p_first_name VARCHAR(80),
    IN p_last_name VARCHAR(80),
    IN p_rol VARCHAR(20),
    IN p_activo TINYINT
)
BEGIN
    UPDATE usuario
    SET username = p_username,
        email = p_email,
        first_name = p_first_name,
        last_name = p_last_name,
        rol = p_rol,
        activo = p_activo
    WHERE id = p_id;
END //

CREATE PROCEDURE sp_usuario_eliminar(IN p_id INT)
BEGIN
    DELETE FROM usuario WHERE id = p_id;
END //

CREATE PROCEDURE sp_usuario_listar()
BEGIN
    SELECT id, username, email, first_name, last_name, password_hash, rol, activo, fecha_creacion
    FROM usuario
    ORDER BY id;
END //

CREATE PROCEDURE sp_usuario_buscar_por_id(IN p_id INT)
BEGIN
    SELECT id, username, email, first_name, last_name, password_hash, rol, activo, fecha_creacion
    FROM usuario WHERE id = p_id;
END //

CREATE PROCEDURE sp_usuario_login(IN p_username VARCHAR(50), IN p_password_hash VARCHAR(255))
BEGIN
    SELECT id, username, email, first_name, last_name, password_hash, rol, activo, fecha_creacion
    FROM usuario
    WHERE username = p_username AND password_hash = p_password_hash AND activo = 1;
END //

CREATE PROCEDURE sp_usuario_cambiar_password(IN p_id INT, IN p_password_hash VARCHAR(255))
BEGIN
    UPDATE usuario SET password_hash = p_password_hash WHERE id = p_id;
END //

-- ======================== ESPECIALIDAD ========================
CREATE PROCEDURE sp_especialidad_insertar(
    IN p_nombre VARCHAR(100),
    IN p_descripcion VARCHAR(255)
)
BEGIN
    INSERT INTO especialidad (nombre, descripcion) VALUES (p_nombre, p_descripcion);
END //

CREATE PROCEDURE sp_especialidad_actualizar(
    IN p_id INT,
    IN p_nombre VARCHAR(100),
    IN p_descripcion VARCHAR(255),
    IN p_activo TINYINT
)
BEGIN
    UPDATE especialidad
    SET nombre = p_nombre, descripcion = p_descripcion, activo = p_activo
    WHERE id = p_id;
END //

CREATE PROCEDURE sp_especialidad_eliminar(IN p_id INT)
BEGIN
    DELETE FROM especialidad WHERE id = p_id;
END //

CREATE PROCEDURE sp_especialidad_listar()
BEGIN
    SELECT id, nombre, descripcion, activo FROM especialidad ORDER BY nombre;
END //

CREATE PROCEDURE sp_especialidad_buscar_por_id(IN p_id INT)
BEGIN
    SELECT id, nombre, descripcion, activo FROM especialidad WHERE id = p_id;
END //

-- ======================== MEDICO ========================
CREATE PROCEDURE sp_medico_insertar(
    IN p_usuario_id INT,
    IN p_colegiado VARCHAR(30),
    IN p_especialidad_id INT,
    IN p_telefono VARCHAR(20)
)
BEGIN
    INSERT INTO medico (usuario_id, colegiado, especialidad_id, telefono)
    VALUES (p_usuario_id, p_colegiado, p_especialidad_id, p_telefono);
END //

CREATE PROCEDURE sp_medico_actualizar(
    IN p_id INT,
    IN p_colegiado VARCHAR(30),
    IN p_especialidad_id INT,
    IN p_telefono VARCHAR(20),
    IN p_activo TINYINT
)
BEGIN
    UPDATE medico
    SET colegiado = p_colegiado,
        especialidad_id = p_especialidad_id,
        telefono = p_telefono,
        activo = p_activo
    WHERE id = p_id;
END //

CREATE PROCEDURE sp_medico_eliminar(IN p_id INT)
BEGIN
    DELETE FROM medico WHERE id = p_id;
END //

CREATE PROCEDURE sp_medico_listar()
BEGIN
    SELECT m.id, m.usuario_id, m.colegiado, m.especialidad_id, m.telefono, m.activo,
           u.first_name, u.last_name, u.email, e.nombre AS especialidad_nombre
    FROM medico m
    INNER JOIN usuario u ON m.usuario_id = u.id
    INNER JOIN especialidad e ON m.especialidad_id = e.id
    ORDER BY m.id;
END //

CREATE PROCEDURE sp_medico_buscar_por_id(IN p_id INT)
BEGIN
    SELECT m.id, m.usuario_id, m.colegiado, m.especialidad_id, m.telefono, m.activo,
           u.first_name, u.last_name, u.email, e.nombre AS especialidad_nombre
    FROM medico m
    INNER JOIN usuario u ON m.usuario_id = u.id
    INNER JOIN especialidad e ON m.especialidad_id = e.id
    WHERE m.id = p_id;
END //

-- ======================== PACIENTE ========================
CREATE PROCEDURE sp_paciente_insertar(
    IN p_dpi VARCHAR(20),
    IN p_nombre VARCHAR(80),
    IN p_apellido VARCHAR(80),
    IN p_fecha_nacimiento DATE,
    IN p_sexo CHAR(1),
    IN p_telefono VARCHAR(20),
    IN p_email VARCHAR(120),
    IN p_direccion VARCHAR(255)
)
BEGIN
    INSERT INTO paciente (dpi, nombre, apellido, fecha_nacimiento, sexo, telefono, email, direccion)
    VALUES (p_dpi, p_nombre, p_apellido, p_fecha_nacimiento, p_sexo, p_telefono, p_email, p_direccion);
END //

CREATE PROCEDURE sp_paciente_actualizar(
    IN p_id INT,
    IN p_dpi VARCHAR(20),
    IN p_nombre VARCHAR(80),
    IN p_apellido VARCHAR(80),
    IN p_fecha_nacimiento DATE,
    IN p_sexo CHAR(1),
    IN p_telefono VARCHAR(20),
    IN p_email VARCHAR(120),
    IN p_direccion VARCHAR(255),
    IN p_activo TINYINT
)
BEGIN
    UPDATE paciente
    SET dpi = p_dpi,
        nombre = p_nombre,
        apellido = p_apellido,
        fecha_nacimiento = p_fecha_nacimiento,
        sexo = p_sexo,
        telefono = p_telefono,
        email = p_email,
        direccion = p_direccion,
        activo = p_activo
    WHERE id = p_id;
END //

CREATE PROCEDURE sp_paciente_eliminar(IN p_id INT)
BEGIN
    DELETE FROM paciente WHERE id = p_id;
END //

CREATE PROCEDURE sp_paciente_listar()
BEGIN
    SELECT id, dpi, nombre, apellido, fecha_nacimiento, sexo, telefono, email, direccion, fecha_registro, activo
    FROM paciente
    ORDER BY apellido, nombre;
END //

CREATE PROCEDURE sp_paciente_buscar_por_id(IN p_id INT)
BEGIN
    SELECT id, dpi, nombre, apellido, fecha_nacimiento, sexo, telefono, email, direccion, fecha_registro, activo
    FROM paciente WHERE id = p_id;
END //

-- ======================== CITA ========================
CREATE PROCEDURE sp_cita_insertar(
    IN p_paciente_id INT,
    IN p_medico_id INT,
    IN p_fecha_hora DATETIME,
    IN p_estado VARCHAR(20),
    IN p_motivo VARCHAR(255),
    IN p_notas TEXT
)
BEGIN
    INSERT INTO cita (paciente_id, medico_id, fecha_hora, estado, motivo, notas)
    VALUES (p_paciente_id, p_medico_id, p_fecha_hora, p_estado, p_motivo, p_notas);
END //

CREATE PROCEDURE sp_cita_actualizar(
    IN p_id INT,
    IN p_paciente_id INT,
    IN p_medico_id INT,
    IN p_fecha_hora DATETIME,
    IN p_estado VARCHAR(20),
    IN p_motivo VARCHAR(255),
    IN p_notas TEXT
)
BEGIN
    UPDATE cita
    SET paciente_id = p_paciente_id,
        medico_id = p_medico_id,
        fecha_hora = p_fecha_hora,
        estado = p_estado,
        motivo = p_motivo,
        notas = p_notas
    WHERE id = p_id;
END //

CREATE PROCEDURE sp_cita_eliminar(IN p_id INT)
BEGIN
    DELETE FROM cita WHERE id = p_id;
END //

CREATE PROCEDURE sp_cita_listar()
BEGIN
    SELECT c.id, c.paciente_id, c.medico_id, c.fecha_hora, c.estado, c.motivo, c.notas, c.fecha_creacion,
           CONCAT(p.nombre, ' ', p.apellido) AS paciente_nombre,
           CONCAT(u.first_name, ' ', u.last_name) AS medico_nombre
    FROM cita c
    INNER JOIN paciente p ON c.paciente_id = p.id
    INNER JOIN medico m ON c.medico_id = m.id
    INNER JOIN usuario u ON m.usuario_id = u.id
    ORDER BY c.fecha_hora DESC;
END //

CREATE PROCEDURE sp_cita_buscar_por_id(IN p_id INT)
BEGIN
    SELECT c.id, c.paciente_id, c.medico_id, c.fecha_hora, c.estado, c.motivo, c.notas, c.fecha_creacion,
           CONCAT(p.nombre, ' ', p.apellido) AS paciente_nombre,
           CONCAT(u.first_name, ' ', u.last_name) AS medico_nombre
    FROM cita c
    INNER JOIN paciente p ON c.paciente_id = p.id
    INNER JOIN medico m ON c.medico_id = m.id
    INNER JOIN usuario u ON m.usuario_id = u.id
    WHERE c.id = p_id;
END //

DELIMITER ;
