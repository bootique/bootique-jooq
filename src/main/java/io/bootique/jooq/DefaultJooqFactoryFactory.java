package io.bootique.jooq;

import com.nhl.bootique.jdbc.DataSourceFactory;
import org.jooq.SQLDialect;

import java.util.Objects;

public class DefaultJooqFactoryFactory {

    private SQLDialect dialect = SQLDialect.DEFAULT;

    /**
     * Sets an optional dialect for the Jooq DSL.
     *
     * @param dialect a Jooq SQL dialect object.
     */
    public void setDialect(SQLDialect dialect) {
        this.dialect = dialect;
    }

    public DefaultJooqFactory createFactory(DataSourceFactory dataSourceFactory) {

        // TODO: guess the dialect based on the connection info

        Objects.requireNonNull(dialect);

        return new DefaultJooqFactory(dataSourceFactory, dialect);
    }
}
