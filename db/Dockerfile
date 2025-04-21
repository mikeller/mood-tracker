FROM mysql:9.3

ENV MYSQL_ROOT_PASSWORD=super-secret

ADD scripts/create_db.sql /docker-entrypoint-initdb.d/

