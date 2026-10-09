-- =============================================================================
-- Clínica Médica SaludVital - Datos de prueba (mínimo 5 registros por tabla)
-- Password de ejemplo (texto plano para demo académica): admin123
-- =============================================================================
USE saludvital_db;

-- Usuarios
INSERT INTO usuario (username, email, first_name, last_name, password_hash, rol, activo) VALUES
('admin',       'admin@saludvital.com',       'Carlos',  'Ramírez',  'admin123', 'ADMIN', 1),
('dr.garcia',   'garcia@saludvital.com',      'Ana',     'García',   'admin123', 'MEDICO', 1),
('dr.lopez',    'lopez@saludvital.com',       'Luis',    'López',    'admin123', 'MEDICO', 1),
('dr.martinez', 'martinez@saludvital.com',    'María',   'Martínez', 'admin123', 'MEDICO', 1),
('recep1',      'recepcion@saludvital.com',   'Sofía',   'Hernández','admin123', 'RECEPCIONISTA', 1),
('recep2',      'recepcion2@saludvital.com',  'Pedro',   'Castillo', 'admin123', 'RECEPCIONISTA', 1);

-- Especialidades
INSERT INTO especialidad (nombre, descripcion) VALUES
('Medicina General', 'Atención primaria y chequeos generales'),
('Pediatría',        'Atención médica a niños y adolescentes'),
('Cardiología',      'Enfermedades del corazón y sistema circulatorio'),
('Dermatología',     'Enfermedades de la piel'),
('Ginecología',      'Salud reproductiva de la mujer'),
('Traumatología',    'Lesiones óseas y musculares');

-- Médicos (usuario_id 2,3,4)
INSERT INTO medico (usuario_id, colegiado, especialidad_id, telefono) VALUES
(2, 'COL-1001', 1, '5555-1001'),
(3, 'COL-1002', 3, '5555-1002'),
(4, 'COL-1003', 2, '5555-1003');

-- Pacientes
INSERT INTO paciente (dpi, nombre, apellido, fecha_nacimiento, sexo, telefono, email, direccion) VALUES
('1234567890101', 'Juan',    'Pérez',     '1985-03-15', 'M', '5555-2001', 'juan.perez@email.com',    'Zona 1, Ciudad'),
('1234567890102', 'Laura',   'Méndez',    '1990-07-22', 'F', '5555-2002', 'laura.mendez@email.com',  'Zona 10, Ciudad'),
('1234567890103', 'Roberto', 'Salazar',   '1978-11-05', 'M', '5555-2003', 'r.salazar@email.com',     'Zona 4, Ciudad'),
('1234567890104', 'Carmen',  'Flores',    '2001-01-30', 'F', '5555-2004', 'carmen.f@email.com',      'Zona 15, Ciudad'),
('1234567890105', 'Diego',   'Ortega',    '1995-09-12', 'M', '5555-2005', 'diego.ortega@email.com',  'Zona 7, Ciudad'),
('1234567890106', 'Elena',   'Vásquez',   '1982-04-18', 'F', '5555-2006', 'elena.v@email.com',       'Zona 11, Ciudad');

-- Citas
INSERT INTO cita (paciente_id, medico_id, fecha_hora, estado, motivo, notas) VALUES
(1, 1, '2026-10-10 09:00:00', 'PROGRAMADA',  'Chequeo general',           NULL),
(2, 2, '2026-10-10 10:00:00', 'CONFIRMADA',  'Dolor de pecho',            'Paciente con antecedentes'),
(3, 1, '2026-10-11 08:30:00', 'PROGRAMADA',  'Control de presión',        NULL),
(4, 3, '2026-10-11 11:00:00', 'ATENDIDA',    'Consulta pediátrica',       'Niño de 5 años acompañado'),
(5, 2, '2026-10-12 14:00:00', 'PROGRAMADA',  'Revisión cardiológica',     NULL),
(6, 1, '2026-10-12 15:30:00', 'CANCELADA',   'Consulta general',          'Paciente canceló por viaje');
