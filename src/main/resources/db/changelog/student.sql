--liquibase formatted sql

--changeset warlocktony:1
create index student_name_index on student(name)
