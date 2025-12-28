CREATE TABLE employee
(
    id            UUID PRIMARY KEY,

    cui           CHAR(13)       NOT NULL UNIQUE,
    email         VARCHAR(100)   NOT NULL UNIQUE,
    first_name    VARCHAR(100)   NOT NULL,
    last_name     VARCHAR(100)   NOT NULL,
    full_name     VARCHAR(201)   NOT NULL,

    phone_number  CHAR(8)        NOT NULL,
    birth_date    DATE,

    position_id   UUID           NOT NULL,
    CONSTRAINT fk_employee_position
        FOREIGN KEY (position_id) REFERENCES position (id)
            ON DELETE RESTRICT,

    workload_type VARCHAR(20)    NOT NULL CHECK (
        workload_type IN ('FULL_TIME', 'PART_TIME')
    ) DEFAULT 'FULL_TIME',

    salary        NUMERIC(10, 2) NOT NULL DEFAULT 0.00,
    profile_image VARCHAR(300),
    status VARCHAR(20)    NOT NULL CHECK (
        status IN ('ACTIVE', 'SUSPENDED', 'TERMINATED')
    ) DEFAULT 'ACTIVE',
    created_at    TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE employment_history
(
    id          UUID PRIMARY KEY,
    employee_id UUID           NOT NULL,

    type        VARCHAR(20)    NOT NULL CHECK (
        type IN (
            'HIRING',
            'SALARY_INCREASE',
            'SUSPENSION',
            'REINSTATEMENT',
            'TERMINATION'
        )
    ),

    start_date  DATE           NOT NULL,
    end_date    DATE,
    salary      NUMERIC(10, 2) NOT NULL,
    notes       VARCHAR(255),

    created_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_employment_employee
        FOREIGN KEY (employee_id) REFERENCES employee (id)
            ON DELETE CASCADE,

    CONSTRAINT chk_employment_date_valid
        CHECK (end_date IS NULL OR end_date >= start_date)
);
