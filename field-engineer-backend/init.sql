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