# `bootique-jooq` - Internal Dev Notes

To init and reverse-engineer a DB for `bootique-jooq` unit tests do this:

```bash
docker-compose up
mvn org.jooq:jooq-codegen-maven:generate
```