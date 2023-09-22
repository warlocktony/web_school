--liquibase formatted sql

--changeset warlocktony:2
create index faculty_name_color_index on faculty(name, color)