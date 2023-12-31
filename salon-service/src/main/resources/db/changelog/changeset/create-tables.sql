CREATE TABLE IF NOT EXISTS address
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_time       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_time TIMESTAMP WITHOUT TIME ZONE,
    house_number       BIGINT,
    street             VARCHAR(255),
    city               VARCHAR(255),
    state              VARCHAR(255),
    CONSTRAINT pk_address PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS users
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_time       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_time TIMESTAMP WITHOUT TIME ZONE,
    name               VARCHAR(255),
    surname            VARCHAR(255),
    phone              VARCHAR(255),
    gender             SMALLINT,
    email              VARCHAR(255),
    tg_id              BIGINT,
    locale             VARCHAR(255) DEFAULT 'EN',
    CONSTRAINT pk_users PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS salon
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_time       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_time TIMESTAMP WITHOUT TIME ZONE,
    name               VARCHAR(255),
    phone              VARCHAR(255),
    email              VARCHAR(255),
    address_id         BIGINT,
    description        VARCHAR(1000),
    owner_id           BIGINT,
    CONSTRAINT pk_salon PRIMARY KEY (id)
);

ALTER TABLE salon
    ADD CONSTRAINT FK_SALON_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES address (id);

ALTER TABLE salon
    ADD CONSTRAINT FK_SALON_ON_OWNER FOREIGN KEY (owner_id) REFERENCES users (id);



CREATE TABLE IF NOT EXISTS master
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_time       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_time TIMESTAMP WITHOUT TIME ZONE,
    user_id            BIGINT,
    position           VARCHAR(255),
    experience_date    TIMESTAMP WITHOUT TIME ZONE,
    about              VARCHAR(255),
    salon_id           BIGINT,
    CONSTRAINT pk_master PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS treatment
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_time       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_time TIMESTAMP WITHOUT TIME ZONE,
    name               VARCHAR(255),
    salon_id           BIGINT,
    price              BIGINT,
    minutes            BIGINT,
    treatment_type     SMALLINT,
    CONSTRAINT pk_treatment PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS work_day
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_time       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_time TIMESTAMP WITHOUT TIME ZONE,
    week_day           SMALLINT,
    work_start_time    TIMESTAMP WITHOUT TIME ZONE,
    work_end_time      TIMESTAMP WITHOUT TIME ZONE,
    is_holiday         BOOLEAN,
    salon_id           BIGINT,
    master_id          BIGINT,
    CONSTRAINT pk_work_day PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS work_time
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    start_time  TIMESTAMP WITHOUT TIME ZONE,
    end_time    TIMESTAMP WITHOUT TIME ZONE,
    is_break    BOOLEAN,
    work_day_id BIGINT,
    CONSTRAINT pk_worktime PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS master_treatments
(
    master_id    BIGINT NOT NULL,
    treatment_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS master_work_times
(
    master_id     BIGINT NOT NULL,
    work_times_id BIGINT NOT NULL
);

ALTER TABLE master_work_times
    ADD CONSTRAINT uc_master_work_times_worktimes UNIQUE (work_times_id);

ALTER TABLE master
    ADD CONSTRAINT FK_MASTER_ON_SALON FOREIGN KEY (salon_id) REFERENCES salon (id);

ALTER TABLE master
    ADD CONSTRAINT FK_MASTER_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE master_work_times
    ADD CONSTRAINT fk_maswortim_on_master FOREIGN KEY (master_id) REFERENCES master (id);

ALTER TABLE master_work_times
    ADD CONSTRAINT fk_maswortim_on_work_time FOREIGN KEY (work_times_id) REFERENCES work_time (id);


CREATE TABLE IF NOT EXISTS master_treatments
(
    master_id    BIGINT NOT NULL,
    treatment_id BIGINT NOT NULL
);



ALTER TABLE work_time
    ADD CONSTRAINT FK_WORKTIME_ON_WORKDAY FOREIGN KEY (work_day_id) REFERENCES work_day (id);



ALTER TABLE work_day
    ADD CONSTRAINT FK_WORK_DAY_ON_MASTER FOREIGN KEY (master_id) REFERENCES master (id);

ALTER TABLE work_day
    ADD CONSTRAINT FK_WORK_DAY_ON_SALON FOREIGN KEY (salon_id) REFERENCES salon (id);

ALTER TABLE treatment
    ADD CONSTRAINT FK_TREATMENT_ON_SALON FOREIGN KEY (salon_id) REFERENCES salon (id);

ALTER TABLE master_treatments
    ADD CONSTRAINT fk_mastre_on_master FOREIGN KEY (master_id) REFERENCES master (id);

ALTER TABLE master_treatments
    ADD CONSTRAINT fk_mastre_on_treatment FOREIGN KEY (treatment_id) REFERENCES treatment (id);

CREATE TABLE IF NOT EXISTS appointment
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_time       TIMESTAMP WITHOUT TIME ZONE,
    last_modified_time TIMESTAMP WITHOUT TIME ZONE,
    treatment_id       BIGINT,
    user_id            BIGINT,
    master_id          BIGINT,
    work_time_id       BIGINT,
    status             SMALLINT,
    note               VARCHAR(255),
    CONSTRAINT pk_appointment PRIMARY KEY (id)
);

ALTER TABLE appointment
    ADD CONSTRAINT FK_APPOINTMENT_ON_MASTER FOREIGN KEY (master_id) REFERENCES master (id);

ALTER TABLE appointment
    ADD CONSTRAINT FK_APPOINTMENT_ON_TREATMENT FOREIGN KEY (treatment_id) REFERENCES treatment (id);

ALTER TABLE appointment
    ADD CONSTRAINT FK_APPOINTMENT_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE appointment
    ADD CONSTRAINT FK_APPOINTMENT_ON_WORKTIME FOREIGN KEY (work_time_id) REFERENCES work_time (id);


CREATE TABLE IF NOT EXISTS feedback
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_id        BIGINT,
    master_id      BIGINT,
    rating         INTEGER,
    appointment_id BIGINT,
    feedback_text  VARCHAR(255),
    CONSTRAINT pk_feedback PRIMARY KEY (id)
);

ALTER TABLE feedback
    ADD CONSTRAINT FK_FEEDBACK_ON_APPOINTMENT FOREIGN KEY (appointment_id) REFERENCES appointment (id);

ALTER TABLE feedback
    ADD CONSTRAINT FK_FEEDBACK_ON_MASTER FOREIGN KEY (master_id) REFERENCES master (id);

ALTER TABLE feedback
    ADD CONSTRAINT FK_FEEDBACK_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);