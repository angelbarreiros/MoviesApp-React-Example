FROM mysql:latest
COPY ./src/sql/4-MySQLCreateTablesDocker.sql /docker-entrypoint-initdb.d/a.sql
COPY src/sql/2-MySQLCreateData.sql /docker-entrypoint-initdb.d/b.sql

ENV MYSQL_ROOT_PASSWORD=pa
ENV MYSQL_DATABASE=paproject
ENV MYSQL_USER=pa
ENV MYSQL_PASSWORD=pa
