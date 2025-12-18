CREATE TABLE payment
(
    id            UUID PRIMARY KEY,

    employee_id   CHAR(13)       NOT NULL,
    date          DATE           NOT NULL DEFAULT CURRENT_DATE,
    base_salary   NUMERIC(10, 2) NOT NULL,
    bonus         NUMERIC(10, 2) NOT NULL DEFAULT 0.00,
    deduction     NUMERIC(10, 2) NOT NULL DEFAULT 0.00,
    total         NUMERIC(10, 2) NOT NULL,
    notes         VARCHAR(255),
    created_at    TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_payment_employee
        FOREIGN KEY (employee_id) REFERENCES employee (cui)
            ON DELETE CASCADE
);
