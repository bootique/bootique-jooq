package io.bootique.jooq;


import com.nhl.bootique.jdbc.DataSourceFactory;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Objects;

public class DefaultJooqFactory implements JooqFactory {

    private DataSourceFactory dataSourceFactory;
    private SQLDialect dialect;
    private Settings defaultSettings;

    public DefaultJooqFactory(DataSourceFactory dataSourceFactory, SQLDialect dialect, Settings defaultSettings) {
        this.dataSourceFactory = dataSourceFactory;
        this.dialect = Objects.requireNonNull(dialect);
        this.defaultSettings = defaultSettings;
    }

    @Override
    public DSLContext newContext() {

        Collection<String> allNames = dataSourceFactory.allNames();

        switch (allNames.size()) {
            case 0:
                throw new IllegalStateException("DataSource is not configured. " +
                        "Configure it via 'bootique-jdbc'");
            case 1:
                return newContext(allNames.iterator().next());
            default:
                throw new IllegalStateException("Default DataSource ambiguity. " +
                        "More then one DataSource is provided by 'bootique-jdbc'");
        }
    }

    @Override
    public DSLContext newContext(String dataSource) {

        DataSource ds = dataSourceFactory.forName(dataSource);
        return DSL.using(ds, dialect, defaultSettings);
    }
}
