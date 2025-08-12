FROM mysql:9.4

ENV MYSQL_ROOT_PASSWORD=super-secret

ADD scripts/create_db.sql /docker-entrypoint-initdb.d/

