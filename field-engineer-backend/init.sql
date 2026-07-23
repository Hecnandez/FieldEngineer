CREATE TABLE IF NOT EXISTS assets (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    model_number VARCHAR(100) NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    schematic_url TEXT NOT NULL,
    updated_at BIGINT NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS part_catalog (
    id VARCHAR(36) PRIMARY KEY,
    part_number VARCHAR(100) NOT NULL,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    updated_at BIGINT NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS inventory (
    part_id VARCHAR(36) PRIMARY KEY REFERENCES part_catalog(id),
    quantity_available INT NOT NULL DEFAULT 0,
    updated_at BIGINT NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS work_orders (
    id VARCHAR(36) PRIMARY KEY,
    asset_id VARCHAR(36) REFERENCES assets(id),
    assigned_to VARCHAR(100) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL,
    technician_notes TEXT,
    updated_at BIGINT NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS consumed_parts (
    id VARCHAR(36) PRIMARY KEY,
    work_order_id VARCHAR(36) REFERENCES work_orders(id),
    part_id VARCHAR(36) REFERENCES part_catalog(id),
    quantity_used INT NOT NULL DEFAULT 1,
    updated_at BIGINT NOT NULL,
    is_deleted BOOLEAN DEFAULT FALSE
);

INSERT INTO assets (id, name, model_number, latitude, longitude, schematic_url, updated_at, is_deleted)
VALUES
    ('ast-001', 'Hydraulic Press Alpha', 'HP-5000-X', 19.4326, -99.1332, 'https://cdn.example.com/schematics/hp5000x.pdf', 1700000000000, FALSE),
    ('ast-002', 'Centrifugal Water Pump B', 'CWP-2200', 19.4350, -99.1410, 'https://cdn.example.com/schematics/cwp2200.pdf', 1700000000000, FALSE),
    ('ast-003', 'HVAC Roof Unit 04', 'TRANE-XL16i', 19.4270, -99.1670, 'https://cdn.example.com/schematics/trane16.pdf', 1700000000000, FALSE)
ON CONFLICT (id) DO NOTHING;

INSERT INTO part_catalog (id, part_number, name, category, updated_at, is_deleted)
VALUES
    ('prt-101', 'PN-9981', 'High-Pressure Rubber O-Ring', 'Seals', 1700000000000, FALSE),
    ('prt-102', 'PN-4402', 'Heavy-Duty Ball Bearing 6205', 'Bearings', 1700000000000, FALSE),
    ('prt-103', 'PN-1105', 'Synthetic Hydraulic Fluid (5L)', 'Fluids', 1700000000000, FALSE),
    ('prt-104', 'PN-8820', '24V DC Solenoid Valve', 'Electrical', 1700000000000, FALSE)
ON CONFLICT (id) DO NOTHING;

INSERT INTO inventory (part_id, quantity_available, updated_at, is_deleted)
VALUES
    ('prt-101', 45, 1700000000000, FALSE),
    ('prt-102', 12, 1700000000000, FALSE),
    ('prt-103', 8,  1700000000000, FALSE),
    ('prt-104', 5,  1700000000000, FALSE)
ON CONFLICT (part_id) DO NOTHING;

INSERT INTO work_orders (id, asset_id, assigned_to, title, description, status, technician_notes, updated_at, is_deleted)
VALUES
    (
        'wo-501',
        'ast-001',
        'tech-hector',
        '30,000 Cycle Hydraulic Service',
        'Replace worn main cylinder seals and check pressure lines for micro-fractures.',
        'IN_PROGRESS',
        'Initial inspection finished. Slight fluid leakage detected around primary valve.',
        1700000000000,
        FALSE
    ),
    (
        'wo-502',
        'ast-002',
        'tech-hector',
        'Pump Cavitation & Noise Repair',
        'Investigate grinding sound coming from primary impeller housing during peak run hours.',
        'OPEN',
        '',
        1700000000000,
        FALSE
    ),
    (
        'wo-503',
        'ast-003',
        'tech-sarah',
        'Quarterly HVAC Filter Change',
        'Replace intake air filters and verify thermostat call signals.',
        'COMPLETED',
        'Filters replaced. System functioning within normal spec limits.',
        1700000000000,
        FALSE
    )
ON CONFLICT (id) DO NOTHING;

INSERT INTO consumed_parts (id, work_order_id, part_id, quantity_used, updated_at, is_deleted)
VALUES
    ('cp-901', 'wo-501', 'prt-101', 2, 1700000000000, FALSE)
ON CONFLICT (id) DO NOTHING;