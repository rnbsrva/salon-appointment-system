--liquibase formatted sql

--changeset akerke:create-table-image_metadata
CREATE TABLE image_metadata
(
    id       BIGSERIAL primary key ,
    image_id VARCHAR(255)
);
--rollback drop table image_metadata;

--changeset akerke:create-table-master_image_metadata
CREATE TABLE master_image_metadata
(
    id        BIGSERIAL primary key,
    image_metadata_id  bigint not null ,
    master_id INT,
    FOREIGN KEY (master_id) REFERENCES master (id),
    foreign key (image_metadata_id) references image_metadata(id)
);
--rollback drop table master_image_metadata;


--changeset akerke:create-table-salon_image_metadata
CREATE TABLE salon_image_metadata
(
    id       BIGSERIAL primary key,
    image_metadata_id bigint not null ,
    salon_id INT,
    FOREIGN KEY (salon_id) REFERENCES salon (id),
    foreign key (image_metadata_id) references image_metadata(id)
);
--rollback drop table master_image_metadata;
